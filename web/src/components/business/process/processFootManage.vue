<template>
  <div>
    <div class="ibox-title">
      <h5>审批意见</h5>
    </div>
    <div v-if="processApprovalList && processApprovalList.length > 0">
      <el-form :inline="true" label-width="100px" class="demo-form-inline" size="small" style="text-align: center" v-for="(item, index) in processApprovalList" v-bind:key="index">
        <el-row style="display: flex; align-items: center; text-align: center; margin-bottom: 6px">
          <el-col :span="24">
            <el-col :span="4">
              {{ item.nodeName }}
            </el-col>
            <el-col :span="19">
              <div v-html="item.processComment"></div>
            </el-col>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div v-if="processApproval.type == 0 || (processApproval.type == 2 && processApproval.curTaskHandler == 'true')">
      <div class="ibox-title">
        <h5>签字意见</h5>
      </div>
      <div>
        <el-row style="margin-bottom: 10px; display: flex; align-items: center; text-align: center">
          <el-col :span="24">
            <el-col :span="14" :offset="4">
              <el-input v-model="comment" type="textarea" :autosize="{ minRows: 4, maxRows: 4 }"></el-input>
            </el-col>

            <el-col :span="5" :offset="1">
              <el-upload :on-success="submitCommentFileSuccess" :data="uploadParams" :headers="headers" class="upload-demo" action="/file/file/upload" style="text-align: left" accept=".pdf,image/*" multiple :file-list="commentFileShowList" :on-remove="submitCommentFileRemove">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip" style="display: inline-block"><span class="el-icon-info"></span>只能上传pdf/png/jpg文件</div>
              </el-upload>
            </el-col>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import { Prop } from "@/api/prop";
export default {
  props: {
    processApproval: Object,
    processApprovalList: Array,
  },
  data() {
    return {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token,
      },
      uploadParams: {
        bucketname: "processfile",
      },
      type: "",
      userId: "",
      employeeId: "",
      processInstanceId: "",
      taskId: "",
      comment: "",
      commentFileShowList: [],
      commentFileList: [],
      commentFile: "",
      commentValue: "",
    };
  },
  watch: {
    comment: function (val) {
      Prop.$emit("sendComment", val);
    },
    commentFile: function (value) {
      Prop.$emit("sendCommentFile", value);
    },
  },
  methods: {
    //上传成功
    submitCommentFileSuccess(res, file) {
      const files = {
        uid: file.uid,
        fileName: res.data.fileName,
        fileOriginName: file.name,
        context: res.data.bucketName + "-" + res.data.fileName,
      };
      this.commentFileList.push(files);
      this.commentFileShowList = [];
      this.commentFileShowList.push(files.fileName);
      this._getSubmitCommentFile();
    },

    submitCommentFileRemove(file) {
      for (var i = 0; i < this.commentFileList.length; i++) {
        if (file.uid == this.commentFileList[i].uid) {
          this.commentFileList.splice(i, 1);
        }
      }
    },
    _getSubmitCommentFile: function () {
      var list = this.commentFileList;
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
      this.commentFile = fileStr;
    },
  },
};
</script>
<style scoped></style>
