<template>
  <div style="height: 100%">
    <div class="ibox-title">
      <h3 style="text-align: center">{{ sbgjjProcessInfo.procName }}</h3>
      <div class="procwy-btn notice-sidebar" style="right: 10px !important">
        <el-button type="primary" :disabled="procDisabled" class="btn btn-primary btn-sm" v-on:click="_startBzProcess(1)"> 保存 </el-button>
        <!-- <el-button v-if="procTestPermission" type="warning" class="btn btn-primary btn-sm" style="margin-right: 8px" v-on:click="_startTestProcess(2)">测试 </el-button> -->
        <el-button type="primary" :disabled="procDisabled" class="btn btn-primary btn-sm" style="margin-right: 8px" v-on:click="_startBzProcess(2)"> 提交 </el-button>
        <div>
          <processDeleteComponent :processInstanceId="processInstanceId" :type="type"></processDeleteComponent>
        </div>
      </div>
    </div>
    <div ref="osComponentRef" :options="options" style="height: 100%">
      <div style="width: 100%" class="ibox-content">
        <el-form ref="form" :model="sbgjjProcessInfo" :rules="rules" label-width="100px" class="demo-form-inline" size="small" style="text-align: center">
          <el-row>
            <el-col :span="12">
              <el-form-item label="申请部门" prop="applyDepartmentName">
                <el-input placeholder="请选择" v-model="sbgjjProcessInfo.applyDepartmentName" :disabled="true"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="申请人" prop="applyUserName">
                <el-input placeholder="请选择" v-model="sbgjjProcessInfo.applyUserName" :disabled="true"> </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="测试内容" prop="remark">
                <el-input v-model="sbgjjProcessInfo.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <processSubmitCommentComponent :processApproval="processApproval" @cacheParams="cacheParams"></processSubmitCommentComponent>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { hasProcessStrUrlResolve, hasProcessStrUrl, defaultProcessApplyInfo, validateFile, generateUUID } from "@/utils/process/helper";
import { startEhrProcess, getDingDingDetail, listDict, downFileOrOpenFile } from "@/api/process/ehrProcess";
import { Prop } from "@/api/prop";
import processSubmitCommentComponent from "@/components/business/process/processSubmitCommentComponent";
import processDeleteComponent from "@/components/business/process/processDeleteComponent";

export default {
  name: "procxz01cslc",
  components: {
    processDeleteComponent,
    processSubmitCommentComponent,
  },
  data() {
    return {
      procTestPermission: false,
      jobList: [],
      procDisabled: false,
      options: {},
      processApproval: {
        comment: "",
        fileShowList: [],
        fileList: [],
        commentFile: "",
      },
      userTitle: "",
      userVisible: false,
      deptVisible: false,
      showCheckbox: false,
      processVisible: false,
      type: "",
      processInstanceId: "",
      // 表单校验
      rules: {
        remark: [{ required: true, message: "测试内容不能为空", trigger: "blur" }],
      },
      sbgjjProcessInfo: {
        procName: "",
        procNum: "",
        procCode: "",
        applyDepartmentName: "",
        applyDepartmentId: "",
        applyUserId: "",
        applyUserName: "",
        remark: "",
      },
    };
  },
  watch: {},

  computed: {
    ...mapGetters(["user", "dept"]),
  },

  mounted() {
    let _this = this;
    Prop.$on("sendComment", function (val) {
      _this.sbgjjProcessInfo.comment = val;
    });
    Prop.$on("sendCommentFile", function (val) {
      _this.sbgjjProcessInfo.commentFile = val;
    });
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      //判断是否有id
      let obj = this.$route.query;
      this.sbgjjProcessInfo.procNum = obj.procNum;
      this.sbgjjProcessInfo.procCode = obj.procCode;
      this.sbgjjProcessInfo.procName = obj.procName;
      this.sbgjjProcessInfo.applyDepartmentName = this.dept.deptName;
      this.sbgjjProcessInfo.applyDepartmentId = this.dept.deptId;
      this.sbgjjProcessInfo.applyUserId = this.user.userId;
      this.sbgjjProcessInfo.applyUserName = this.user.nickname;
      if (obj.processInstanceId) {
        this.type = obj.type;
        this.processInstanceId = obj.processInstanceId;
        this.sbgjjProcessInfo.processInstanceId = obj.processInstanceId;
        this._getDingDingDetail09();
      } else {
        defaultProcessApplyInfo(this.sbgjjProcessInfo, this.user, this.dept);
      }
    },

    // 保存/提交时评论参数
    cacheParams(data) {
      this.sbgjjProcessInfo.comment = data.comment;
      this.sbgjjProcessInfo.commentFile = data.commentFile;
    },

    //发起流程
    _startBzProcess(startType) {
      let _this = this;
      let _success = true;
      if (startType === 2) {
        this.$refs["form"].validate((valid) => {
          _success = valid;
        });
      }
      if (!_success) {
        return;
      }
      _this.procDisabled = true;
      _this.sbgjjProcessInfo.startType = startType;
      startEhrProcess(this.sbgjjProcessInfo)
        .then((res) => {
          if (res.msg === "success") {
            if (startType === 1) {
              _this.$notify({
                title: "成功",
                message: "保存成功",
                type: "success",
              });
            }
            if (startType === 2) {
              _this.$notify({
                title: "成功",
                message: "提交成功",
                type: "success",
              });
            }
            this.$router.go(-1);
            _this.procDisabled = false;
          }
        })
        .catch(() => {
          _this.$message.warning("请求出错！");
          _this.procDisabled = false;
        });
    },

    /**
     * 发起测试
     */
    //发起测试流程
    _startTestProcess: function (startType) {
      let _this = this;
      _this.sbgjjProcessInfo.startType = startType;
      let param = _this.sbgjjProcessInfo;
      param.testFlag = "1";
      startEhrProcess(param)
        .then((res) => {
          if (res.data.msg === "success") {
            if (startType === 1) {
              _this.$notify({
                title: "成功",
                message: "保存成功",
                type: "success",
              });
            }
            if (startType === 2) {
              _this.$notify({
                title: "成功",
                message: "提交成功",
                type: "success",
              });
            }
            _this.$router.go(-1);
          }
        })
        .catch(() => {});
    },
    _getDingDingDetail09() {
      getDingDingDetail({
        processInstanceId: this.$route.query.processInstanceId,
        taskId: this.$route.query.taskId,
      }).then((res) => {
        let info = res.data;
        sessionStorage.setItem("process-file", info.fileUrl);

        // 赋值
        this.sbgjjProcessInfo = info.formComonent;
      });
    },
  },
};
</script>
<style scoped>
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 100%;
}

.goodsImg {
  text-align: left;
}

.process-line {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #e7f3fc;
  height: 32px;
  line-height: 32px;
  margin: 10px 20px;
}

.process-item-title {
  font-weight: bold;
  text-align: left;
  padding: 0px 0px 0px 85px;
}

.notice-fixed {
  position: fixed !important;
  top: 130px !important;
  right: 330px !important;
  z-index: 100;
}

.ibox-title {
  position: relative;
  height: 55px;
}

.el-select-dropdown__item {
  text-align: left !important;
}

.div-card {
  text-align: left;
  color: #ff0000;
}
</style>
