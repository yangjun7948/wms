<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane class="task-pane" label="常规" name="first">
        <el-form-item label="编号" required>
          <el-input v-model="id"></el-input>
        </el-form-item>
        <el-form-item label="名称" required>
          <el-input v-model="name"></el-input>
        </el-form-item>
        <el-form-item label="任务派遣">
          <el-select v-model="candidateType" filterable placeholder="请选择">
            <el-option label="指定人" value="assignee"></el-option>
            <el-option label="指定多人" value="candidateUsers"></el-option>
            <el-option label="指定组" value="candidateGroups"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="表达式" v-if="candidateType == 'assignee'">
          <el-input v-model="assigneeExpress"></el-input>
        </el-form-item>
        <el-form-item label="表达式" v-if="candidateType == 'candidateUsers'">
          <el-input v-model="usersExpress"></el-input>
        </el-form-item>
        <el-form-item label="表达式" v-if="candidateType == 'candidateGroups'">
          <el-input v-model="groupExpress"></el-input>
        </el-form-item>
        <el-form-item label="是否会签">
          <el-select v-model="multiinstance_type">
            <!--相当于bpmn2.0标准中的isSequential-->
            <el-option label="非会签" value="None"></el-option>
            <el-option label="同时进行会签" value="Parallel"></el-option>
            <el-option label="顺序进行会签" value="Sequential"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="matrixType" style="width: 100%">
            <el-option label="部门矩阵" value="dept"></el-option>
            <el-option label="分部矩阵" value="org"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="matrixType == 'dept'">
          <el-select v-model="deptJob" style="width: 100%">
            <el-option v-for="item in dict.type.dept_job_type" :label="item.label" :key="item.label" :value="item.value">
              {{ item.label }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="matrixType == 'org'">
          <el-select v-model="orgJob" multiple style="width: 100%">
            <el-option v-for="item in dict.type.org_job_type" :label="item.label" :key="item.label" :value="item.value">
              {{ item.label }}
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item>
          <el-select v-model='curRole' multiple @change='roleChange'>
            <el-option v-for='(item,index) in roleList' :key="item.name" :value="item.name">
              {{item.name}}
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="集合" v-if="multiinstance_type != 'none'">
          <el-input v-model="collection"></el-input>
        </el-form-item>
        <el-form-item label="参数" v-if="multiinstance_type != 'none'">
          <el-input v-model="elementVariable"></el-input>
        </el-form-item>
        <el-form-item label="基数 (会签)" v-if="multiinstance_type != 'none'">
          <!--后端暂时没用到-->
          <el-input v-model="multiinstance_cardinality" type="number"></el-input>
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane class="task-pane" label="监听" name="second">
        <el-form-item label="事件类型">
          <el-select v-model="eventType" placeholder="请选择">
            <el-option label="创建" value="create"></el-option>
            <el-option label="开始" value="assignment"></el-option>
            <el-option label="结束" value="complete"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="监听器类型" v-show="isShow">
          <el-select v-model="listenerType" placeholder="请选择">
            <el-option label="java类" value="class"></el-option>
            <el-option label="表达式" value="expression"></el-option>
            <el-option label="代理表达式" value="delegateExpression"></el-option>
            <el-option label="脚本" value="script"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-bind:label="listenerValueLabel" v-show="isShow">
          <el-input v-model="listenerValue"></el-input>
        </el-form-item>
        <el-form-item label="属性值" v-show="isShow">
          <el-input v-model="classField" placeholder="请输入属性值"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="cancel" type="primary">取消</el-button>
        </el-form-item>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
const forEach = require("lodash/forEach");
// import { findObjFromArrayByField, isNeedUpdate } from "../../utils/common-utils";
import bpmnHelper from "@/utils/process/bpmn-helper";
import elementHelper from "@/utils/process/element-helper";
import { NodeTypeMap, TxTypeMap } from "@/utils/process/activiti-const";
// 用户任务属性组件
export default {
  props: ["element"],
  inject: ["bpmnModeler"],
  dicts: ["org_job_type", "dept_job_type"],
  data() {
    return {
      modeling: null,
      id: this.element.id || "",
      name: "",
      documentation: "",
      multiinstance_type: "None",
      // 原子节点属性
      elementVariable: "",
      multiinstance_cardinality: "",
      collection: "",
      candidateType: "assignee",
      assigneeExpress: "",
      usersExpress: "",
      groupExpress: "",
      activeName: "first",
      //监听
      listenerType: "class",
      eventType: "create",
      isShow: "true",
      listenerValueLabel: "",
      listenerValue: "",
      classField: "",
      roleList: [
        {
          expression: "${zonghebujingli}",
          params: "zonghebujingli",
          collection: "${processUserService.getUserByRole('1087711817')}",
          name: "行政经理会签",
        },
        {
          expression: "${yunyingbujingli}",
          params: "yunyingbujingli",
          collection: "${processUserService.getUserByRole('1088562004')}",
          name: "运营部经理会签",
        },
      ],
      curRole: "综合部经理会签",
      deptJob: "",
      orgJob: "",
      matrixType: "",
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    roleChange(item) {
      let role = this.roleList.filter((t) => t.name == item)[0];
      this.collection = role.collection;
      this.elementVariable = role.params;
      this.assigneeExpress = role.expression;
      this.name = role.name;
    },
    cancel() {
      const bpmnModeler = this.bpmnModeler();
      const modeling = bpmnModeler.get("modeling");
      this.listenerValue = "";
      this.classField = "";
      modeling.updateProperties(this.element, {
        extensionElements: null,
      });
    },
  },
  mounted() {
    const bpmnModeler = this.bpmnModeler();
    this.modeling = bpmnModeler.get("modeling");
  },
  watch: {
    name: {
      handler(newVal, oldVal) {
        this.modeling.updateProperties(this.element, {
          name: newVal,
        });
      },
    },

    //监视元素变化
    element: {
      deep: true,
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal.type == "bpmn:UserTask") {
          let that = this;
          //会签属性
          if (!!newVal.businessObject.loopCharacteristics) {
            if (newVal.businessObject.loopCharacteristics.isSequential) {
              that.multiinstance_type = "Sequential";
            } else {
              that.multiinstance_type = "Parallel";
            }
            that.collection = newVal.businessObject.loopCharacteristics.collection;
            that.elementVariable = newVal.businessObject.loopCharacteristics.elementVariable;
            debugger;
            if (that.collection.indexOf("getDeptUserList") > 0) {
              that.matrixType = "dept";
              that.deptJob = that.collection.split('"')[1];
            } else if (that.collection.indexOf("getOrgUserList") > 0) {
              that.matrixType = "org";
              that.orgJob = that.collection.split('"')[1].split(",");
            }
          } //
          that.name = newVal.businessObject.name;
          that.curRole = newVal.businessObject.name;
          if (!!newVal.businessObject.assignee) {
            that.assigneeExpress = newVal.businessObject.assignee;
            that.candidateType = "assignee";
          }
          if (!!newVal.businessObject.candidateGroups) {
            that.groupExpress = newVal.businessObject.candidateGroups;
            that.candidateType = "candidateGroups";
          }
          if (!!newVal.businessObject.candidateUsers) {
            that.usersExpress = newVal.businessObject.candidateUsers;
            that.candidateType = "candidateUsers";
          }
          if (!!newVal.businessObject.extensionElements) {
            that.isShow = true;
            let _executionListener = newVal.businessObject.extensionElements.values[0];
            if (!!_executionListener.$attrs["event"]) {
              that.eventType = _executionListener.$attrs["event"];
            } else {
              that.eventType = _executionListener.event;
            }

            if (!!_executionListener.$attrs["class"]) {
              that.listenerValue = _executionListener.$attrs["class"];
            } else {
              that.listenerValue = _executionListener.class;
            }
            let _fields = _executionListener.fields;
            if (!!_fields[0].$attrs["stringValue"]) {
              that.classField = _fields[0].$attrs["stringValue"];
            } else {
              that.classField = _fields[0].stringValue;
            }
            that.listenerType = "class";
          }
        }
      },
    },
    multiinstance_type: {
      handler(newVal, oldVal) {
        const bpmnModeler = this.bpmnModeler();
        const bpmnFactory = bpmnModeler.get("bpmnFactory");
        let multiInstanceLoopCharacteristics = undefined;
        if (newVal == "None") {
          multiInstanceLoopCharacteristics = undefined;
        } else if (newVal == "Parallel") {
          multiInstanceLoopCharacteristics = bpmnFactory.create("bpmn:MultiInstanceLoopCharacteristics");
        } else if (newVal == "Sequential") {
          multiInstanceLoopCharacteristics = bpmnFactory.create("bpmn:MultiInstanceLoopCharacteristics", { isSequential: true });
        }
        this.modeling.updateProperties(this.element, {
          loopCharacteristics: multiInstanceLoopCharacteristics,
        });
      },
    },
    collection: {
      handler(newVal, oldVal) {
        let loopCharacteristics = this.element.businessObject.loopCharacteristics;
        loopCharacteristics["collection"] = newVal;
        this.modeling.updateProperties(this.element, { loopCharacteristics: loopCharacteristics });
      },
    },
    elementVariable: {
      handler(newVal, oldVal) {
        let loopCharacteristics = this.element.businessObject.loopCharacteristics;
        loopCharacteristics["elementVariable"] = newVal;
        this.modeling.updateProperties(this.element, { loopCharacteristics: loopCharacteristics });
      },
    },
    candidateType: {
      handler(newVal, oldVal) {
        this.modeling.updateProperties(this.element, { candidateGroups: null });
        this.modeling.updateProperties(this.element, { candidateUsers: null });
        this.modeling.updateProperties(this.element, { assignee: null });
      },
    },
    assigneeExpress: {
      handler(newVal, oldVal) {
        if (!!newVal) {
          this.modeling.updateProperties(this.element, { assignee: newVal });
        }
      },
    },
    groupExpress: {
      handler(newVal, oldVal) {
        if (!!newVal) {
          this.modeling.updateProperties(this.element, { candidateGroups: newVal });
        }
      },
    },
    usersExpress: {
      handler(newVal, oldVal) {
        if (!!newVal) {
          this.modeling.updateProperties(this.element, { candidateUsers: newVal });
        }
      },
    },
    deptJob: {
      handler(newVal, oldVal) {
        console.log(newVal);
        this.collection = '${processUserService.getDeptUserList(deptId,"' + newVal + '")}';
        this.elementVariable = "P" + newVal;
        this.assigneeExpress = "${P" + newVal + "}";
      },
    },
    orgJob: {
      handler(newVal, oldVal) {
        console.log(newVal);
        let id = newVal.join(",");
        this.collection = '${processUserService.getOrgUserList(orgId,"' + id + '")}';
        this.elementVariable = "P" + newVal[0];
        this.assigneeExpress = "${P" + newVal[0] + "}";
      },
    },
    eventType(newVal, oldVal) {
      if (newVal) {
        this.isShow = true;
      }
    },
    listenerType(newVal, oldVal) {
      if (newVal === "class") {
        this.listenerValueLabel = "java类";
      } else if (newVal === "expression") {
        this.listenerValueLabel = "表达式";
      } else if (newVal === "delegateExpression") {
        this.listenerValueLabel = "代理表达式";
      }
    },
    classField(newVal, oldVal) {
      if (newVal) {
        const bpmnModeler = this.bpmnModeler();
        const bpmnFactory = bpmnModeler.get("bpmnFactory");
        let extensionElements = this.element.businessObject.get("extensionElements");
        if (!extensionElements) {
          extensionElements = elementHelper.createElement("bpmn:ExtensionElements", null, this.element, bpmnFactory);
        }
        const length = extensionElements.get("values").length;
        for (let i = 0; i < length; i++) {
          // 清除旧值
          extensionElements.get("values").pop();
        }
        const executionListener = elementHelper.createElement("activiti:TaskListener", null, this.element, bpmnFactory);
        executionListener.$attrs["event"] = this.eventType;
        executionListener.$attrs[this.listenerType] = this.listenerValue;
        const field = elementHelper.createElement("activiti:Field", null, executionListener, bpmnFactory);
        field.$attrs["name"] = "value";
        field.$attrs["stringValue"] = this.classField;
        executionListener.fields = [];
        executionListener.fields.push(field);
        extensionElements.get("values").push(executionListener);
        const modeling = bpmnModeler.get("modeling");
        modeling.updateProperties(this.element, {
          extensionElements: extensionElements,
        });
      }
    },
  },
};
</script>
<style scoped>
.task-pane {
  overflow: auto;
  height: calc(100vh - 200px);
}
</style>
