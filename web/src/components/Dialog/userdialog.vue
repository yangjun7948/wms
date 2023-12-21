<template>
  <el-dialog custom-class="user-dialog" :append-to-body="true" :title="title ? title : '选择人员'" :close-on-press-escape="true" :close-on-click-modal="false" :before-close="closeDialog" :visible.sync="visible">
    <div style="height: 5vh; z-index: 9999">
      <el-input placeholder="输入关键字进行过滤" v-model="filterUserText" style="padding-bottom: 10px" />
    </div>
    <div style="height: 55vh; overflow-y: auto; margin-top: 20px">
      <el-tree ref="userDialog" :data="orgDeptUserData" node-key="id" :default-checked-keys="checkedKeys" default-expand-all :props="userSelectProps" @node-click="ondeClick" highlight-current :check-strictly="true" :show-checkbox="!mutiCheck ? mutiCheck : true" :filter-node-method="filterNode">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span style="margin-left: 10px; margin-right: 10px">
            <span v-if="data.type == '2'"><svg-icon style="color: #0acd68" icon-class="people"></svg-icon></span>
            <span v-if="data.type == '1'"><svg-icon icon-class="tree"></svg-icon></span>
          </span>
          <span>{{ node.label }} </span>
        </span>
      </el-tree>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取 消</el-button>
      <el-button type="primary" @click="confirm">保 存</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { deptUserTree } from "@/api/system/user";
export default {
  name: "userDialog",
  props: ["checkedKeys", "visible", "type", "mutiCheck", "title", "initFlag"],
  data() {
    return {
      userSelectProps: {
        label: "name",
        value: "id",
        disabled: function (data, node) {
          return !node.data.userFlag;
        },
      },
      orgDeptUserData: [],
      filterUserText: "",
    };
  },
  watch: {
    filterUserText(val) {
      this.$refs.userDialog.filter(val);
    },
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      deptUserTree().then((response) => {
        this.orgDeptUserData = response.data;
      });
    },
    /**
     * 搜索过滤机构部门树
     */
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    closeDialog() {
      this.$nextTick(() => {
        this.$refs.userDialog.setCheckedKeys([]);
      });
      this.$emit("close");
    },
    ondeClick(_allData, _node, _tree) {
      if (_node.data.userFlag) {
        this.data = _node.data;
      } else {
        this.data = {};
      }
    },
    //确定
    confirm() {
      // 确定之后重置模糊搜索
      this.filterUserText = "";

      let checkedNodes = this.$refs.userDialog.getCheckedNodes();
      let currentNode = this.$refs.userDialog.getCurrentNode();
      if (this.mutiCheck) {
        this.$emit("confirm", checkedNodes);
      } else {
        this.$emit("confirm", this.data);
      }
      if (this.initFlag != false) {
        this.$refs.userDialog.setCheckedKeys([]);
      }
    },
  },
};
</script>

<style>
.user-dialog .el-dialog__body {
  overflow-y: hidden !important;
}
</style>
