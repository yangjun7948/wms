<template>
  <div>
    <div class="ibox-title"><h5>流转意见</h5></div>
    <div style="padding: 15px 90px 25px 15px">
      <div v-for="(item, index) in processCommentList" v-bind:key="index">
        <el-row style="display: flex; align-items: center">
          <el-col :span="6" :offset="2" style="margin-top: 20px">
            <div style="color: #0e9aef">{{ item.userName }}</div>
            <div>{{ item.department }}</div>
          </el-col>

          <el-col :span="8" style="margin-top: 20px">
            <div>
              <strong>{{ item.comment }}</strong>
            </div>
            <div v-for="(obj, idx) in item.commentFileList" v-bind:key="idx">
              <a style="color: #007bff" @click="_previewCommentFile(obj)">{{ obj.fileName }}</a>
            </div>
            <div style="margin-top: 10px">接收人：{{ item.receiver }}</div>
          </el-col>

          <el-col :span="8" style="margin-top: 20px">
            <div>{{ item.approvalTime }}</div>
            <div style="color: #0e9aef">
              {{ item.processNodeName }}
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import { downFileOrOpenFile } from "@/api/process/ehrProcess";

export default {
  props: {
    processCommentList: Array,
  },
  data() {
    return {};
  },

  methods: {
    _previewCommentFile: function (obj) {
      let fileName = obj.fileAddress;
      const downName = obj.fileName;
      let token = this.$store.getters.access_token;
      downFileOrOpenFile(fileName, downName, token);
    },
  },
};
</script>
<style scoped></style>
