<template>
  <div>
    <div>
      <el-button type="danger" style="margin-right: 10px" class="btn btn-primary btn-sm" v-on:click="_deleteProcess()"> 删除 </el-button>
    </div>
  </div>
</template>
<script>
import { deleteProcess } from "@/api/process/ehrProcess";
export default {
  props: {
    processInstanceId: String,
    type: String,
  },
  data() {
    return {};
  },
  methods: {
    _deleteProcess: function () {
      if (this.processInstanceId === "") {
        this.$message.warning("当前流程还未提交或保存，无法删除");
        return;
      }
      this.$confirm("确定是否删除此流程?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteProcessByInstanceId(this.processInstanceId);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    deleteProcessByInstanceId: function (processInstanceId) {
      var data = {};
      data.processInstanceId = processInstanceId;

      deleteProcess(data)
        .then((res) => {
          if (res.msg == "success") {
            this.$notify({
              title: "成功",
              message: "删除成功",
              type: "success",
            });
            //跳转页面
            if (this.type == "1") {
              this.$router.push({ name: "已办事宜" });
            } else if (this.type == "2") {
              this.$router.push({ name: "我的请求" });
            } else if (this.type == "0") {
              this.$router.push({ name: "我的待办" });
            }
          }
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped></style>
