<template>
  <div class="app-container" ref="content">
    <el-tabs class="tab-content" v-model="activeIndex">
      <el-tab-pane label="基础设置" name="1"></el-tab-pane>
      <el-tab-pane label="表单设计" name="2">
        <process-form></process-form>
      </el-tab-pane>
      <el-tab-pane label="流程设计" name="3">
        <div class="body-content">
          <div class="toolbox">
            <el-button size="medium" @click="createNew" type="primary">新建流程</el-button>
            <el-button size="medium" @click="downloadXML" type="primary">下载BPMN</el-button>
            <el-button size="medium" @click="uploadXML" type="primary">上传BPMN</el-button>
            <el-upload class="" action="" :limit="3" :on-change="handleChange" :auto-upload="false" :show-file-list="false">
              <el-button size="medium" type="primary">选择本地文件</el-button>
            </el-upload>
          </div>
          <div class="canvas" ref="canvas"></div>
          <div id="js-properties-panel" class="panel">
            <el-form label-width="auto" size="mini" label-position="top">
              <component :is="propsComponent" :element="element" :key="key"></component>
            </el-form>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="高级设置" name="4"></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// 引入相关的依赖
// import BpmnViewer from 'bpmn-js'
import "bpmn-js/dist/assets/diagram-js.css"; // 左边工具栏以及编辑节点的样式
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";
import "bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css"; // 右边工具栏样式
import BpmnModeler from "bpmn-js/lib/Modeler";
import propertiesPanelModule from "bpmn-js-properties-panel";
import propertiesProviderModule from "bpmn-js-properties-panel/lib/provider/camunda";
// import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import { xmlStr } from "@/api/process/xmlStr";
import activitiModdleDescriptor from "@/utils/process/activiti";
import StartEventProps from "@/components/business/process/nodeprops/StartEventProps.vue";
import CommonProps from "@/components/business/process/nodeprops/CommonProps.vue";
import UserTaskProps from "@/components/business/process/nodeprops/UserTaskProps.vue";
import SequenceFlowProps from "@/components/business/process/nodeprops/SequenceFlowProps.vue";
import bpmnHelper from "@/utils/process/bpmn-helper";
import { update } from "@/api/process/ehrProcess";
import processForm from "@/components/business/process/processform";
export default {
  name: "",
  components: {
    StartEventProps,
    CommonProps,
    UserTaskProps,
    SequenceFlowProps,
    processForm,
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  // 生命周期 - 载入后, Vue 实例挂载到实际的 DOM 操作完成，一般在该过程进行 Ajax 交互
  mounted() {
    this.init();
  },
  provide: function () {
    return {
      bpmnModeler: this.getBpmnModeler,
    };
  },
  data() {
    return {
      // bpmn建模器
      bpmnModeler: null,
      container: null,
      canvas: null,
      file: null,
      fileName: "test.bpmn",
      propsComponent: "CommonProps",
      key: "1",
      element: null,
      defaultData: {
        "bpmn:StartEvent": "流程开始",
        "bpmn:EndEvent": "流程结束",
        "bpmn:IntermediateThrowEvent": "交易终止",
      },
      activeIndex: "1",
      procNum: "",
      procId: "",
      processXml: "",
    };
  },
  methods: {
    init() {
      // 获取到属性ref为“content”的dom节点
      this.container = this.$refs.content;
      // 获取到属性ref为“canvas”的dom节点
      const canvas = this.$refs.canvas;
      // 建模
      this.bpmnModeler = new BpmnModeler({
        container: canvas,
        //添加控制板
        propertiesPanel: {
          parent: "#js-properties-panel",
        },
        additionalModules: [
          // 左边工具栏以及节点
          propertiesProviderModule,
          // 右边的工具栏
          // propertiesPanelModule,
        ],
        moddleExtensions: {
          // camunda: camundaModdleDescriptor,
          activiti: activitiModdleDescriptor,
        },
      });
      const context = this.$route.params.context;
      this.procNum = this.$route.params.procNum;
      this.procId = this.$route.params.procId;
      debugger;
      if (!!context) {
        this.createNewDiagram(context);
      }
    },
    createNew() {
      this.createNewDiagram(xmlStr);
    },
    createNewDiagram(xml) {
      // 将字符串转换成图显示出来
      this.bpmnModeler.importXML(xml, (err) => {
        if (err) {
          // console.error(err)
        } else {
          // 这里是成功之后的回调, 可以在这里做一系列事情
          this.success();
        }
      });
    },
    success() {
      console.log("创建成功!");
      this.addEventBusListener();
      this.addBpmnListener();
      this.addModelerListener();
    },
    // 添加绑定事件
    addBpmnListener() {
      const that = this;
      // 获取a标签dom节点
      // 给图绑定事件，当图有发生改变就会触发这个事件
      this.bpmnModeler.on("commandStack.changed", function () {
        that.saveDiagram(function (err, xml) {
          that.setEncoded(err ? null : xml);
        });
      });
    },
    addModelerListener() {
      // 监听 modeler
      const bpmnjs = this.bpmnModeler;
      const that = this;
      // 'shape.removed', 'connect.end', 'connect.move'
      const events = ["shape.added", "shape.move.end", "shape.removed"];
      events.forEach(function (event) {
        that.bpmnModeler.on(event, (e) => {
          var elementRegistry = bpmnjs.get("elementRegistry");
          var shape = e.element ? elementRegistry.get(e.element.id) : e.shape;
          // console.log(shape)
          if (event === "shape.added") {
            // 展示新增图形的属性
            that.key = e.element.id.replace("_label", "");
            that.propsComponent = bpmnHelper.getComponentByEleType(shape.type);
            that.element = e.element;
          } else if (event === "shape.move.end") {
            // 展示新增图形的属性
            that.key = shape.id;
            that.propsComponent = bpmnHelper.getComponentByEleType(shape.type);
            that.element = e.shape;
          } else if (event === "shape.removed") {
            // 展示默认的属性
            that.propsComponent = "CommonProps";
          }
        });
      });
    },
    addEventBusListener() {
      // 监听 element
      let that = this;
      const eventBus = this.bpmnModeler.get("eventBus");
      const eventTypes = ["element.click", "element.changed", "element.updateLabel"];
      eventTypes.forEach(function (eventType) {
        eventBus.on(eventType, function (e) {
          if (eventType === "element.changed") {
            that.elementChanged(e);
          } else if (eventType === "element.click") {
            if (!e || e.element.type == "bpmn:Process") {
              that.key = "1";
              that.propsComponent = "CommonProps";
              that.element = e.element;
            } else {
              // 展示新增图形的属性
              that.key = e.element.id;
              that.propsComponent = bpmnHelper.getComponentByEleType(e.element.type);
              that.element = e.element;
            }
          }
        });
      });
    },
    isInvalid(param) {
      // 判断是否是无效的值
      return param === null || param === undefined || param === "";
    },
    isSequenceFlow(type) {
      // 判断是否是线
      return type === "bpmn:SequenceFlow";
    },
    elementChanged(e) {
      const id = e.element.id.replace("_label", "");
      var shape = this.getShape(id);
      this.element = shape;
      const that = this;
      console.log(shape);
      console.log(e);
      if (!shape) {
        // 若是shape为null则表示删除, 无论是shape还是connect删除都调用此处
        console.log("无效的shape");
        // 上面已经用 shape.removed 检测了shape的删除, 要是删除shape的话这里还会被再触发一次
        console.log("删除了shape和connect");
        return;
      }
      if (!this.isInvalid(shape.type)) {
        if (this.isSequenceFlow(shape.type)) {
          console.log("改变了线");
        } else {
          that.setDefaultProperties();
        }
      }
    },
    getShape(id) {
      var elementRegistry = this.bpmnModeler.get("elementRegistry");
      return elementRegistry.get(id);
    },
    setDefaultProperties() {
      const that = this;
      const { element } = that;
      if (element) {
        // 这里可以拿到当前点击的节点的所有属性
        const { type, businessObject } = element;
        const { name } = businessObject;
        if (that.verifyIsEvent(type)) {
          const eventType = businessObject.eventDefinitions ? businessObject.eventDefinitions[0]["$type"] : "";
          console.log(eventType);
        } else if (this.verifyIsTask(type)) {
          const taskType = type;
          console.log(taskType);
        }
        that.element["name"] = name || that.defaultData[element.type];
      }
    },
    verifyIsEvent(type) {
      return type.includes("Event");
    },
    verifyIsTask(type) {
      return type.includes("Task");
    },
    // 下载为bpmn格式,done是个函数，调用的时候传入的
    saveDiagram(done) {
      // 把传入的done再传给bpmn原型的saveXML函数调用
      this.bpmnModeler.saveXML({ format: true }, function (err, xml) {
        done(err, xml);
      });
    },
    // 当图发生改变的时候会调用这个函数，这个data就是图的xml
    setEncoded(data) {
      // 把xml转换为URI，下载要用到的
      this.processXml = data;
      this.file = encodeURIComponent(data);
    },
    downloadXML() {
      this.$prompt("请输入模板名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          // 下载图的具体操作,改变a的属性，className令a标签可点击，href令能下载，download是下载的文件的名字
          let save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
          if (!!this.file) {
            save_link.className = "active";
            save_link.href = "data:application/bpmn20-xml;charset=UTF-8," + this.file;
            save_link.download = value + ".bpmn";
            var ev = document.createEvent("MouseEvents");
            ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
            save_link.dispatchEvent(ev);
          }
        })
        .catch(() => {});
    },
    uploadXML() {
      let data = {
        procId: this.procId,
        context: this.processXml,
      };
      update(data)
        .then((res) => {
          this.$message({
            message: "保存成功",
            type: "success",
          });
        })
        .catch((err) => {
          this.$message.error("保存失败");
        });
    },
    handleChange(file, fileList) {
      let reader = new FileReader();
      let content = reader.readAsText(file.raw);
      let _this = this;
      reader.onloadend = function (argument) {
        _this.createNewDiagram(argument.target.result);
      };
    },
    getBpmnModeler() {
      return this.bpmnModeler;
    },
    stringToByte(str) {
      var bytes = new Array();
      var len, c;
      len = str.length;
      for (var i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if (c >= 0x010000 && c <= 0x10ffff) {
          bytes.push(((c >> 18) & 0x07) | 0xf0);
          bytes.push(((c >> 12) & 0x3f) | 0x80);
          bytes.push(((c >> 6) & 0x3f) | 0x80);
          bytes.push((c & 0x3f) | 0x80);
        } else if (c >= 0x000800 && c <= 0x00ffff) {
          bytes.push(((c >> 12) & 0x0f) | 0xe0);
          bytes.push(((c >> 6) & 0x3f) | 0x80);
          bytes.push((c & 0x3f) | 0x80);
        } else if (c >= 0x000080 && c <= 0x0007ff) {
          bytes.push(((c >> 6) & 0x1f) | 0xc0);
          bytes.push((c & 0x3f) | 0x80);
        } else {
          bytes.push(c & 0xff);
        }
      }
      return bytes;
    },
  },
  // 计算属性
  computed: {},
};
</script>
<style scoped>
.canvas {
  width: 100%;
  height: calc(100vh - 90px);
}

.panel {
  position: absolute;
  right: 0;
  top: 0;
  width: 500px;
  height: 100%;
}

.toolbox {
  position: absolute;
  top: 5px;
  left: 125px;
  z-index: 1;
  display: flex;
  justify-content: space-evenly;
  width: 500px;
}

.tab-content {
  height: calc(100vh - 90px);
}
</style>
