<template>
  <div style="height: 100%">
    <el-tabs class="selfTab" v-model="activeName" type="border-card" style="height: 100%">
      <el-tab-pane label="流程表单" name="1" style="height: 100%">
        <div style="height: 100%">
          <div>
            <processWithdrawComponent :processWithdrawInfo="processWithdrawInfo"></processWithdrawComponent>
          </div>
          <div ref="osComponentRef" :options="options" class="scrollContent">
            <div class="ibox-content" id="processComponent">
              <div class="process-print" style="font-size: 16px; text-align: center; font-weight: bold">{{ processInfo.procName }}</div>
              <div v-if="processInfo.statusType == 'WITHDRAW'">
                <el-form ref="form" :model="processInfo" :rules="rules" label-width="100px" class="demo-form-inline" size="small" style="text-align: center">
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="申请部门" prop="applyDepartmentName">
                        <el-input placeholder="请选择" v-model="processInfo.applyDepartmentName" :disabled="true"> </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="申请人" prop="applyUserName">
                        <el-input placeholder="请选择" v-model="processInfo.applyUserName" :disabled="true"> </el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="测试内容" prop="remark">
                        <el-input v-model="processInfo.remark"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </div>
              <div v-else>
                <el-form :model="processInfo" :rules="rules" label-width="100px" class="demo-form-inline" size="small" style="text-align: center">
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="申请部门" prop="applyDepartmentName">
                        <span>{{ processInfo.applyDepartmentName }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="申请人" prop="applyUserName">
                        <span>{{ processInfo.applyUserName }}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="测试内容" prop="remark">
                        <span>{{ processInfo.remark }}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </div>
              <div>
                <processFootManage :processApproval="processApproval" :processApprovalList="processApproval.processApprovalList"></processFootManage>
                <processCirculationOpinion :processCommentList="processCommentList"></processCirculationOpinion>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="流程图" name="2" style="height: 100%">
        <div ref="osComponentRef" :options="options" class="scrollContent">
          <processImageCompoment :processImageUrl="processImageUrl"></processImageCompoment>
        </div>
      </el-tab-pane>
      <el-tab-pane label="流程状态" name="3" style="height: 100%">
        <div ref="osComponentRef" :options="options" class="scrollContent">
          <processStatusComponent :processStatus="processStatus.processStatusInfo"></processStatusComponent>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { Prop } from "@/api/prop";

import { hasProcessStrUrlResolve, hasProcessStrUrl, defaultProcessApplyInfo, downFileByFileName } from "@/utils/process/helper";
import { startEhrProcess, getDingDingDetail, listDict, getProcImage } from "@/api/process/ehrProcess";
import processStatusComponent from "@/components/business/process/processStatusComponent";
import processWithdrawComponent from "@/components/business/process/processWithdrawComponent";
import processFootManage from "@/components/business/process/processFootManage";
import processImageCompoment from "@/components/business/process/processImageCompoment";
import processCirculationOpinion from "@/components/business/process/processCirculationOpinion";
import processSubmitCommentComponent from "@/components/business/process/processSubmitCommentComponent";

export default {
  name: "procxz01cslcdetail",
  components: {
    processStatusComponent,
    processImageCompoment,
    processFootManage,
    processWithdrawComponent,
    processSubmitCommentComponent,
    processCirculationOpinion,
  },
  data() {
    return {
      uploadParams: {
        bucketName: "processfile",
      },
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token,
      },
      activeName: "1",
      procTestPermission: false,
      procDisabled: false,
      processApproval: {
        comment: "",
        fileShowList: [],
        fileList: [],
        commentFile: "",
      },
      interviewLocationList: [],
      userVisible: false,
      deptVisible: false,
      processVisible: false,
      processCode: "",
      processImageUrl: "",
      type: "",
      processInstanceId: "",
      chooseUserType: "",
      chooseUserIndex: "",
      options: {},
      processCommentList: [],
      processStatus: {
        processStatusInfo: [],
      },
      processWithdrawInfo: {},
      processInfo: {
        procName: "",
        procNum: "",
        procCode: "",
        applyDepartmentName: "",
        applyDepartmentId: "",
        applyUserId: "",
        applyUserName: "",
        remark: "",
      },
      // 表单校验
      rules: {
        remark: [{ required: true, message: "测试内容不能为空", trigger: "blur" }],
      },
    };
  },
  mounted() {
    Prop.$on("rs01detail", function (val, callback) {
      let flag = true;
      let message = "";
      callback(flag, message);
    });
  },
  watch: {
    processInfo: {
      deep: true, //深度监听设置为 true
      handler: function (newV, oldV) {
        Prop.$emit("sendFormDate", this.processInfo, "rs01detail");
      },
    },
  },
  computed: {
    ...mapGetters(["user", "dept"]),
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      //判断是否有id
      let obj = this.$route.query;
      this.processInfo.procNum = obj.procNum;
      this.processInfo.procCode = obj.procCode;
      this.processInfo.procName = obj.procName;
      this.processWithdrawInfo = obj;
      this.processInfo.statusType = obj.statusType;
      this.processApproval = obj;
      if (obj.processInstanceId) {
        this.type = obj.type;
        this.processInstanceId = obj.processInstanceId;
        this.processInfo.processInstanceId = obj.processInstanceId;
        this._getDingDingDetail20();
        this.getProcImage20();
      } else {
        defaultProcessApplyInfo(this.processInfo, this.userInfo, this.deptInfo);
      }
    },

    //获取流程图
    getProcImage20() {
      getProcImage({ processInstanceId: this.$route.query.processInstanceId }).then((res) => {
        if (typeof window.navigator.msSaveBlob !== "undefined") {
        } else {
          let imageUrl = window.URL.createObjectURL(res);
          this.processImageUrl = imageUrl;
        }
      });
    },
    downloadFile() {
      window.open(this.contractInfo.fileShow);
    },
    _startBzProcess: function (startType) {
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
      _this.processInfo.startType = startType;
      startEhrProcess(_this.processInfo)
        .then((res) => {
          if (res.data.msg == "success") {
            if (startType == "1") {
              _this.$notify({
                title: "成功",
                message: "保存成功",
                type: "success",
              });
            }
            if (startType == "2") {
              _this.$notify({
                title: "成功",
                message: "提交成功",
                type: "success",
              });
            }
            _this.$router.go(-1);
            _this.procDisabled = false;
          }
        })
        .catch(() => {
          _this.procDisabled = false;
        });
    },
    handleRemove(file) {
      for (var i = 0; i < this.processInfo.contractFileList.length; i++) {
        if (file.uid == this.processInfo.contractFileList[i].uid) {
          this.processInfo.contractFileList.splice(i, 1);
        }
      }
    },
    //上传成功回调
    handleSuccess(res, file) {
      const files = {
        uid: file.uid,
        fileName: res.data.fileName,
        fileOriginName: res.data.fileOriginName,
        context: res.data.context,
        url: res.data.fileOriginName + "," + res.data.context,
      };
      this.processInfo.contractFileList.push(files);
      this.processInfo.contractFileShowList.push(files.fileName);
    },
    /**
     *保存/提交时评论参数
     *
     * */
    cacheParams(data) {
      this.processInfo.comment = data.comment;
      this.processInfo.commentFile = data.commentFile;
    },
    downFile(file) {
      const fileArray = file.split(",");
      const downName = fileArray[0];
      const fileName = fileArray[1];
      downFileByFileName(fileName, downName);
    },
    userClose() {
      this.userVisible = false;
    },
    deptClose() {
      this.deptVisible = false;
    },
    //人员回调函数
    chooseInnerUser(_param) {
      if (this.chooseUserType == "1") {
        this.processInfo.applyUserId = _param.id;
        this.processInfo.applyUserName = _param.name;
      }
      if (this.chooseUserType == "2") {
        this.processInfo.interviewUserId = _param.id;
        this.processInfo.interviewUserName = _param.name;
      }
      this.userVisible = false;
    },
    //选择部门
    deptConfirm(_param) {
      this.processInfo.applyDepartmentId = _param.id;
      this.processInfo.applyDepartmentName = _param.name;

      this.deptVisible = false;
    },
    _goBack: function () {
      if (this.type == "0") {
        //0跳转到待办
        this.$router.push({ name: "我的待办" });
      } else if (this.type == "2") {
        //2跳转到我的请求
        this.$router.push({ name: "我的请求" });
      } else {
        this.$router.push({ name: "新建流程" });
      }
    },
    _chooseApplyUser: function (type, index) {
      this.chooseUserType = type;
      this.chooseUserIndex = index;
      this.userVisible = true;
    },
    _selectAttendDeptment: function (type, index) {
      this.processInfo.chooseDepartmentType = type;
      this.processInfo.chooseDepartmentIndex = index;
      this.deptVisible = true;
    },
    //搜索
    querySearch(queryString, cb) {
      var restaurants = this.workPlaceList;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
      };
    },
    handleSelect(item) {
      this.processInfo.workPlace = item.name;
    },

    /**
     * 获取详情
     */
    _getDingDingDetail20() {
      let _this = this;
      getDingDingDetail({
        processInstanceId: this.$route.query.processInstanceId,
        taskId: this.$route.query.taskId,
      }).then((res) => {
        var processInfo = res.data;
        _this.processApproval.processApprovalList = processInfo.processApprovalList;
        _this.processCommentList = processInfo.processCommentList;
        _this.processStatus.processStatusInfo = processInfo.processStatusInfo;
        sessionStorage.setItem("process-file", processInfo.fileUrl);
        let list = processInfo.formComonent;
        if (!!list) {
          _this.matchingInfo(list);
        }
        let formPermissionList = processInfo.actFormPermissionList;
        if (!!formPermissionList && formPermissionList.length > 0) {
          _this.formPermissionSet(formPermissionList);
        }
      });
    },
    formPermissionSet: function (list) {
      if (list.length > 0) {
        list.forEach((item) => {
          if (item.detailId == "editFlag") {
            this.processInfo.editFlag = item.readOnly == "true" && item.approvalSign != "意见征询";
          }
        });
      }
    },
    /**
     * 处理数据
     */
    matchingInfo(info) {
      this.processInfo = info;
      this.processApproval.comment = info.comment;
      this.processInfo.comment = info.comment;
      this.processApproval.fileShowList = hasProcessStrUrlResolve(info.commentFile);
      this.processApproval.fileList = hasProcessStrUrlResolve(info.commentFile);
      this.processApproval.commentFile = info.commentFile;
      this.processInfo.commentFile = info.commentFile;
    },
  },
};
</script>
<style scoped>
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 100%;
}

.el-select-dropdown__item {
  text-align: left !important;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.process-print {
  visibility: hidden;
}
</style>
