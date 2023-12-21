<template>
  <div class="app-container">
    <div class="btn-container">
      <el-button type="primary" @click="addTemplate" size="small">新增流程</el-button>
    </div>
    <el-tabs>
      <el-tab-pane v-bind:key="item.procTypeName" v-for="item in processList" :label="item.procTypeName">
        <el-table :data="item.processDtoList">
          <el-table-column prop="procName" label="流程名称"></el-table-column>
          <el-table-column prop="procNum" label="流程编号"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row)" type="text">修改</el-button>
              <el-button type="text">删除</el-button>
              <el-button @click="deploy(scope.row)" type="text">重新部署</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { processTree, deploy } from "@/api/process/ehrProcess";
export default {
  name: "",
  computed: {},
  data() {
    return {
      processList: [],
    };
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  // 生命周期 - 载入后, Vue 实例挂载到实际的 DOM 操作完成，一般在该过程进行 Ajax 交互
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let _this = this;
      processTree()
        .then((res) => {
          _this.processList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    edit(row) {
      this.$router.push({
        name: "ProcessTemplateEdit",
        params: { context: row.context, procNum: row.procNum, procId: row.procId },
      });
    },
    addTemplate() {
      this.$router.push({
        name: "ProcessTemplateAdd",
      });
    },
    deploy(row) {
      deploy({ procId: row.procId })
        .then((res) => {
          this.$message({
            message: "保存成功",
            type: "success",
          });
        })
        .catch((err) => {
          this.$message.error("保存失败" + err);
        });
    },
  },
};
</script>
<style lang="scss" scoped>
.btn-container {
  top: 20px;
  right: 0px;
  z-index: 1;
}
</style>
