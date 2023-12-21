<template>
  <div>
    <el-form-item label="编号">
      <el-input v-model="id"></el-input>
    </el-form-item>
    <el-form-item label="名称">
      <el-input v-model="name"></el-input>
    </el-form-item>
    <el-form-item label="跳转条件">
      <el-input v-model="condition"></el-input>
    </el-form-item>
  </div>
</template>
<script>
import bpmnHelper from "@/utils/process/bpmn-helper";
import elementHelper from "@/utils/process/element-helper";

export default {
  props: ["element"],
  inject: ["bpmnModeler"],
  data() {
    return {
      modeling: null,
      id: this.element.id || "",
      name: "",
      condition: "",
      // defaultflow:false,
    };
  },

  mounted() {
    const bpmnModeler = this.bpmnModeler();
    this.modeling = bpmnModeler.get("modeling");
  },

  methods: {},
  watch: {
    element: {
      deep: true,
      immediate: true,
      handler(element, oldVal) {
        this.id = element.id;
        this.name = element.businessObject.name;
        //显示条件配置选项
        if (!!element.businessObject.conditionExpression) {
          this.condition = element.businessObject.conditionExpression.body;
          // this.defaultflow = false;
        }
      },
    },
    name: {
      handler(newVal, oldVal) {
        this.modeling.updateProperties(this.element, {
          name: newVal,
        });
      },
    },
    condition: {
      handler(newVal, oldVal) {
        const bpmnModeler = this.bpmnModeler();
        const bpmnFactory = bpmnModeler.get("bpmnFactory");
        let express = bpmnFactory.create("bpmn:FormalExpression");
        express[" xsi:type"] = "tFormalExpression";
        express.body = newVal;
        this.modeling.updateProperties(this.element, {
          conditionExpression: express,
        });
      },
    },
  },
};
</script>
<style></style>
