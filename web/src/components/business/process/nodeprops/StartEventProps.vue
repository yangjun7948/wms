<template>
  <div>
    <el-form-item label="编号" v-show="false">
      <el-input v-model="id"></el-input>
    </el-form-item>
    <el-form-item label="名称">
      <el-input v-model="name"></el-input>
    </el-form-item>
    <el-form-item label="表单标识" v-show="false">
      <el-input v-model="formKey"></el-input>
    </el-form-item>
    <el-form-item label="文档" v-show="false">
      <el-input type="textarea" v-model="documentation"></el-input>
    </el-form-item>
    <el-form-item label="启动流程的人的id" v-show="false">
      <el-input v-model="initiator"></el-input>
    </el-form-item>
  </div>
</template>
<script>
// import bpmnHelper from '../js/helper/bpmn-helper';
export default {
  props: ['element'],
  inject: ['bpmnModeler'],
  data() {
    return {
      id: this.element.id || '',
      name: '',
      formKey: '',
      formFields: '',
      documentation: '',
      initiator: ''
    }
  },

  watch: {
    id(newVal, oldVal) {
      const bpmnModeler = this.bpmnModeler();
      const modeling = bpmnModeler.get('modeling')
      modeling.updateProperties(this.element, {
        id: newVal
      })
    },
    name(newVal, oldVal) {
      const bpmnModeler = this.bpmnModeler();
      const modeling = bpmnModeler.get('modeling')
      modeling.updateProperties(this.element, {
        name: newVal
      })
    },
    element: {
      deep: true,
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal != oldVal) {
          if (newVal.type === 'bpmn:StartEvent') { // 防止修改其他子组件的属性
            this.name = newVal.name;
          }
        }
      },

    },
  }
}

</script>
<style>
</style>
