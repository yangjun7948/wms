<template>
  <div>
    <div>
      <div class="ibox-title">
        <el-form :inline="true" label-width="100px" class="demo-form-inline" size="small" style="text-align: center">
          <el-row style="margin-bottom: 10px">
            <el-col :span="4" style="margin-top: 8px"> 签字意见 </el-col>
            <el-col :span="14">
              <el-input v-model="processApproval.comment" type="textarea" :rows="4"></el-input>
            </el-col>
            <el-col :span="6"> </el-col>
          </el-row>
          <el-row>
            <el-col :span="4"> <div>附件</div></el-col>
            <el-col :span="12">
              <!-- <file-upload v-model="this.processApproval.fileList" @input="commentFileSuccess"></file-upload> -->
              <el-upload :on-success="commentFileSuccess" :data="uploadParams" :headers="headers" class="upload-demo" style="text-align: left" :action="uploadFileUrl" accept=".pdf,image/*" multiple :file-list="processApproval.fileShowList" :on-remove="commentFileRemove">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip" style="display: inline-block"><span class="el-icon-info"></span>只能上传pdf/png/jpg文件</div>
              </el-upload>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import { getToken } from "@/utils/auth";
export default {
  props: {
    processApproval: Object,
  },
  name: "processSubmitCommentComponent",
  data() {
    return {
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      uploadParams: {
        bucketname: "processfile",
      },
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传文件服务器地址
    };
  },
  watch: {
    "processApproval.comment": function (val) {
      this.$emit("cacheParams", this.processApproval);
    },
  },
  methods: {
    //上传成功
    commentFileSuccess(res, file) {
      const files = {
        uid: file.uid,
        fileName: res.fileName,
        fileOriginName: file.name,
        context: res.fileName,
      };
      this.processApproval.fileList.push(files);
      this.processApproval.fileShowList.push(files.fileName);
      this._getSubmitComment();
    },

    commentFileRemove(file) {
      for (var i = 0; i < this.processApproval.fileList.length; i++) {
        if (file.uid == this.processApproval.fileList[i].uid) {
          this.processApproval.fileList.splice(i, 1);
        }
      }
      this._getSubmitComment();
    },
    _getSubmitComment: function () {
      var list = this.processApproval.fileList;
      var fileStr = "";
      if (list.length > 0) {
        for (var i = 0; i < list.length; i++) {
          if (fileStr === "") {
            fileStr = list[i].fileOriginName + "," + list[i].context;
          } else {
            fileStr = fileStr + ";" + list[i].fileOriginName + "," + list[i].context;
          }
        }
      }
      this.processApproval.commentFile = fileStr;
      this.$emit("cacheParams", this.processApproval);
    },
  },
};
</script>
<style scoped>
.ibox-title {
  -moz-border-bottom-colors: none;
  -moz-border-left-colors: none;
  -moz-border-right-colors: none;
  -moz-border-top-colors: none;
  background-color: #ffffff;
  border-color: #e7eaec;
  border-image: none;
  border-style: solid solid none;
  border-width: 2px 0 0;
  color: inherit;
  margin-bottom: 0;
  padding: 15px 90px 8px 15px;
  min-height: 48px;
  position: relative;
  clear: both;
}
</style>
