<template>
  <div>
    <div class="title-content ibox-title">
      <div style="font-size: 16px; font-weight: bold">{{ processWithdrawInfo.procName }}</div>
      <div style="position: absolute; top: 10px; right: 10px" class="wy31-btn notice-sidebar">
        <el-button type="primary" size="mini" :disabled="procDisabled" v-if="processWithdrawInfo.type == '0' || (processWithdrawInfo.type == '2' && processWithdrawInfo.curTaskHandler == 'true')" v-on:click="_approvalProcess()">提交 </el-button>
        <el-button type="primary" size="mini" v-if="processWithdrawInfo.type == '0' && processWithdrawInfo.suspensionState == '1' && processWithdrawInfo.delegation != 'PENDING'" v-on:click="_opinionSeek()">意见征询 </el-button>
        <el-button type="primary" size="mini" v-on:click="_forwardProcess()">转发 </el-button>
        <el-button v-if="processWithdrawInfo.type == '0' && processWithdrawInfo.suspensionState == '1' && processWithdrawInfo.delegation != 'PENDING'" type="primary" size="small" v-on:click="_chooseTransferInfo()">转办 </el-button>
        <el-dropdown trigger="click" @command="moreActions" style="line-height: 30px; margin-left: 10px" placement="bottom-start">
          <span class="el-dropdown-link"> 更多<i class="el-icon-arrow-down el-icon--right"></i> </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-if="processWithdrawInfo.type == '0' && processWithdrawInfo.statusType !== 'WITHDRAW' && processWithdrawInfo.delegation != 'PENDING' && processWithdrawInfo.suspensionState == '1'" command="1">退回 </el-dropdown-item>
            <!--发起人撤回 -->
            <el-dropdown-item v-if="processWithdrawInfo.type == '2' && processWithdrawInfo.showWithdrawFlag == true && processWithdrawInfo.delegation != 'PENDING'" command="2">撤回 </el-dropdown-item>
            <!--审批人撤回 -->
            <el-dropdown-item v-if="processWithdrawInfo.type == '1' && processWithdrawInfo.owner == ''" command="4">撤回 </el-dropdown-item>
            <!-- <el-dropdown-item command="5">打印 </el-dropdown-item> -->
            <el-dropdown-item command="3">返回 </el-dropdown-item>
            <el-dropdown-item v-if="processWithdrawInfo.withdrawEditStatus == 'WITHDRAW'" command="6">删除 </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-dialog title="选择流程退回结点" :visible.sync="taskDefinitionVisible" append-to-body>
        <div v-for="(item, index) in taskDefinitionList" v-bind:key="index">
          <el-row>
            <el-radio v-model="taskDefinitionId" :label="item.taskDefinitionKey">
              {{ item.taskDefinitionName }}
            </el-radio>
          </el-row>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="taskDefinitionVisible = false">取 消</el-button>
          <el-button type="primary" @click="sureBackProcess()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="意见征询" :visible.sync="processGetOpinionVisible" append-to-body>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 流程标题 </el-col>
          <el-col :span="16">
            {{ processWithdrawInfo.procName }}
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 选择征询人 </el-col>
          <el-col :span="16">
            <span @click="chooseForwardUser(1)">
              <el-input disabled v-model="opinionSeekerName"></el-input>
            </span>
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 签字意见 </el-col>
          <el-col :span="16">
            <el-input type="textarea" v-model="opinionSeekComment" :rows="4"> </el-input>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button id="cancelOpinionSeek" @click="processGetOpinionVisible = false">取 消</el-button>
          <el-button id="sureOpinionSeek" type="primary" @click="submitOpinionSeek()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="流程转发" :visible.sync="processForwardVisible" append-to-body>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 流程标题 </el-col>
          <el-col :span="16">
            {{ processWithdrawInfo.procName }}
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 选择转发人 </el-col>
          <el-col :span="16">
            <span @click="chooseForwardUser(2)">
              <el-input readonly v-model="forwardEmployeeName"></el-input>
            </span>
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 签字意见 </el-col>
          <el-col :span="16">
            <el-input type="textarea" v-model="forwardComment" :rows="4"> </el-input>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button id="cancelForward" @click="processForwardVisible = false">取 消</el-button>
          <el-button id="sureForward" type="primary" @click="submitForwordRequest()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="流程转办" :visible.sync="processTransferVisible" append-to-body>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 流程标题 </el-col>
          <el-col :span="16">
            {{ processWithdrawInfo.procName }}
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 选择转办人 </el-col>
          <el-col :span="16">
            <span @click="chooseForwardUser(3)">
              <el-input disabled v-model="transferEmployeeName"></el-input>
            </span>
          </el-col>
          <el-col :span="4"> </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="4"> 签字意见 </el-col>
          <el-col :span="16">
            <el-input type="textarea" v-model="transferComment" :rows="4"> </el-input>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button id="cancelTransfer" @click="processTransferVisible = false">取 消</el-button>
          <el-button id="sureTransfer" type="primary" @click="_processTransfer()">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 人员组件-->
      <userdialog :visible="userVisible" :mutiCheck="false" @confirm="chooseInnerUser" @close="userClose"></userdialog>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { Prop } from "@/api/prop";
import { withdrawProcess, remindProcessHandle, opinionSeek, processForward, processTransfer, examinationApproval, queryProcessBackNode, backProgress, deleteProcess, approvalWithdraw } from "@/api/process/ehrProcess";
import userdialog from "@/components/Dialog/userdialog";

export default {
  props: {
    processWithdrawInfo: Object,
  },
  components: { userdialog },
  data() {
    return {
      procDisabled: false,
      userVisible: false,
      procName: "",
      processInstanceId: this.processWithdrawInfo.processInstanceId,
      delegation: this.processWithdrawInfo.delegation,
      statusType: this.processWithdrawInfo.statusType,
      taskId: this.processWithdrawInfo.taskId,
      type: this.processWithdrawInfo.type, //0-我的待办  2-我的请求
      showWithdrawFlag: "", //true:显示撤回 false:不显示撤回
      employeeId: "",
      actionType: "",
      taskDefinitionId: "",
      taskDefinitionName: "",
      taskDefinitionList: [],
      procCode: "",
      forwardEmployeeId: "",
      processGetOpinionVisible: false,
      taskDefinitionVisible: false,
      forwardEmployeeName: "",
      forwardComment: "",
      owner: "",
      processForwardVisible: false,
      opinionSeekerId: "",
      opinionSeekerName: "",
      opinionSeekComment: "",
      chooseUserType: "",
      processTransferVisible: false,
      transferEmployeeId: "",
      transferEmployeeName: "",
      transferComment: "",
      suspensionState: "",
      forwardEmployeeIds: [],
      comment: "",
      commentFile: "",
      validateVisible: "",
      approvalFlag: "",
      approvalMessage: "",
      formDate: {},
      relatedParameterData: {},
    };
  },
  computed: {
    ...mapGetters(["userInfo", "permissions"]),
  },
  mounted() {
    var _this = this;
    Prop.$on("sendComment", function (val) {
      _this.comment = val;
    });
    Prop.$on("sendCommentFile", function (val) {
      _this.commentFile = val;
    });
    Prop.$on("sendFormDate", function (val1, val2) {
      _this.formDate = val1;
      if (val1.flag == "1") {
        _this.relatedParameterData = _this.formDate.relatedParameterData;
      }
      _this.validateVisible = val2;
    });
  },
  created() {},
  methods: {
    //返回
    _backToProcessDetail() {
      let type = this.type;
      this.$router.back();
      // if (type == '1') {
      //   this.$router.push({ name: "已办事宜" });
      // }
      // if (type == '2') {
      //   this.$router.push({ name: "我的请求" });
      // }
      // if (type == '0') {
      //   this.$router.push({ name: "我的待办" });
      // }
      // if (type == '3') {}
    },
    //流程撤回
    _withdrawProcessMethod: function () {
      var param = {
        processInstanceId: this.processWithdrawInfo.processInstanceId,
        procCode: this.processWithdrawInfo.procCode,
      };
      withdrawProcess(param)
        .then((res) => {
          if (res.status == 200) {
            this.$notify({
              title: "成功",
              message: "撤回成功",
              type: "success",
              duration: 2000,
            });
            this.$router.push({ name: "我的请求" });
          } else {
            this.$message({
              message: "流程已被审批，撤回失败！",
              type: "warning",
            });
          }
        })
        .catch(() => {
          this.$message({
            message: "网络出错",
            type: "warning",
          });
        });
    },
    approvalCallBack(result, message) {
      this.approvalFlag = result;
      this.approvalMessage = message;
    },
    //审批提交
    _approvalProcess() {
      if (!this.processInstanceId) {
        return;
      }
      let obj = {
        userId: this.userId,
        processInstanceId: this.processInstanceId,
        taskId: this.taskId,
        comment: this.comment,
        commentFile: this.commentFile,
        formDate: this.formDate,
        relatedParameterData: this.relatedParameterData,
        delegation: this.delegation,
        withdrawEditStatus: this.statusType,
        employeeId: this.employeeId,
      };
      if (this.validateVisible != undefined && this.validateVisible != "") {
        if (this.validateVisible != "") {
          Prop.$emit(this.validateVisible, this.formDate, this.approvalCallBack);
        }
        if (!this.approvalFlag) {
          this.$message.error(this.approvalMessage);
          return;
        }
      }

      this.procDisabled = true;
      examinationApproval(obj)
        .then((res) => {
          if (res.msg == "success") {
            this._backToProcessDetail();
            this.$notify({
              title: "成功",
              message: "审批成功",
              type: "success",
            });
          } else {
            this.$notify.error({
              title: "错误",
              message: "审批失败",
            });
          }
          this.procDisabled = false;
        })
        .catch(() => {
          this.$message.error("网络出错");
          this.procDisabled = false;
        });
    },

    //退回流程
    _refuseProcessMethod() {
      var info = {};
      info.processInstanceId = this.processInstanceId;
      info.comment = this.comment;
      info.taskDefinitionId = this.taskDefinitionId;
      let list = this.taskDefinitionList;
      if (list.length > 0) {
        list.forEach((item) => {
          if (info.taskDefinitionId == item.taskDefinitionKey) {
            this.taskDefinitionName = item.taskDefinitionName;
            info.taskId = item.taskId;
          }
        });
      }
      info.taskDefinitionName = this.taskDefinitionName;

      backProgress(info)
        .then((res) => {
          if (res.msg == "success") {
            this.$notify({
              title: "成功",
              message: "回退成功",
              type: "success",
            });
            this.$router.push({ name: "我的待办" });
          }
        })
        .catch(() => {});
    },
    moreActions: function (val) {
      // let val = this.processWithdrawInfo.actionType;
      if (val == "1") {
        if (!this.comment) {
          this.$message({
            message: "退回操作前请先填写签字意见",
            type: "warning",
          });
          return;
        }
        //查找流程可退至的节点
        this.queryProcessBackTaskDefinition();
        this.taskDefinitionVisible = true;
      }
      if (val == "2") {
        this.$confirm("确定是否撤回?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this._withdrawProcessMethod();
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消撤回",
            });
          });
      }
      if (val == "3") {
        this._backToProcessDetail();
      }
      if (val == "4") {
        this._approvalWithdraw();
      }
      if (val == "5") {
        this.printProcess();
      }
      if (val == "6") {
        this.$confirm("确定是否删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this._deleteProcess();
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      }
    },
    sureBackProcess: function () {
      if (this.taskDefinitionId == "") {
        this.$message({
          message: "请选择退回至哪个任务结点",
          type: "warning",
        });
        return;
      }
      this.$confirm("确定是否退回?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.taskDefinitionVisible = false;
          this._refuseProcessMethod();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消退回",
          });
          this.taskDefinitionVisible = false;
        });
    },
    queryProcessBackTaskDefinition: function () {
      this.taskDefinitionList = [];
      var data = {};
      data.processInstanceId = this.processInstanceId;

      queryProcessBackNode(data)
        .then((res) => {
          if (res.data.msg == "success") {
            let list = res.data.data;
            this.taskDefinitionList = list;
          }
        })
        .catch(() => {});
    },
    _approvalWithdraw: function () {
      var data = {};
      data.processInstanceId = this.processInstanceId;
      data.employeeId = this.userInfo.userId;
      approvalWithdraw(data)
        .then((res) => {
          this.$notify({
            title: "成功",
            message: "撤回成功",
            type: "success",
          });
          this.$router.back();
        })
        .catch(() => {});
    },
    printProcess: function () {
      $(".process-print")[0].style.visibility = "visible";
      $("#approvalTitle").hide();
      $("#approvalContent").hide();
      $("#processComponent").jqprint({
        debug: false,
        importCSS: true,
        printContainer: true,
        operaSupport: false,
      });
      $("#approvalTitle").show();
      $("#approvalContent").show();
      $(".process-print")[0].style.visibility = "hidden";
    },
    _opinionSeek: function () {
      this.processGetOpinionVisible = true;
    },
    submitOpinionSeek: function () {
      let that = this;
      if (!this.opinionSeekerId) {
        this.$message({
          message: "请选择征询人",
          type: "warning",
        });
        return;
      }
      // document.getElementById("sureOpinionSeek").disabled = true;
      let obj = {
        processInstanceId: this.processInstanceId,
        opinionSeekerId: this.opinionSeekerId,
        opinionSeekerName: this.opinionSeekerName,
        opinionSeekComment: this.opinionSeekComment,
        employeeId: this.employeeId,
      };
      opinionSeek(obj)
        .then((res) => {
          this.processGetOpinionVisible = false;
          this._backToProcessDetail();
          this.$notify({
            title: "成功",
            message: "意见征询成功",
            type: "success",
          });
        })
        .catch(() => {
          console.log("网络异常");
        });
    },
    chooseForwardUser: function (type) {
      this.chooseUserType = type;
      this.userVisible = true;
    },
    userClose() {
      this.userVisible = false;
    },
    //人员回调函数
    chooseInnerUser(_param) {
      this.forwardEmployeeIds = [];
      let type = this.chooseUserType;
      if (type == 1) {
        this.opinionSeekerId = _param.id;
        this.opinionSeekerName = _param.name;
      }
      if (type == 2) {
        this.forwardEmployeeId = _param.id;
        this.forwardEmployeeName = _param.name;
      }
      if (type == 3) {
        this.transferEmployeeId = _param.id;
        this.transferEmployeeName = _param.name;
      }
      this.userVisible = false;
    },
    _forwardProcess: function () {
      this.forwardEmployeeId = "";
      this.forwardEmployeeName = "";
      this.processForwardVisible = true;
    },
    submitForwordRequest: function () {
      if (!this.forwardEmployeeId) {
        this.$message({
          message: "请选择转发人",
          type: "warning",
        });
        return;
      }
      document.getElementById("sureForward").disabled = true;
      let obj = {
        processInstanceId: this.processInstanceId,
        forwardEmployeeId: this.forwardEmployeeId,
        forwardEmployeeName: this.forwardEmployeeName,
        forwardComment: this.forwardComment,
        employeeId: this.employeeId,
      };
      processForward(obj)
        .then((res) => {
          this.$notify({
            title: "成功",
            message: "转发成功",
            type: "success",
          });
          this.processForwardVisible = false;
          setTimeout(function () {
            location.reload();
          }, 200);
        })
        .catch(() => {
          this.$message.error("网络出错");
        });
    },
    _chooseTransferInfo: function () {
      this.processTransferVisible = true;
    },
    //转办
    _processTransfer: function () {
      if (!this.transferEmployeeId) {
        this.$message({
          message: "请选择转办人",
          type: "warning",
        });
        return;
      }
      document.getElementById("sureTransfer").disabled = true;

      let obj = {
        processInstanceId: this.processInstanceId,
        transferEmployeeId: this.transferEmployeeId,
        transferEmployeeName: this.transferEmployeeName,
        transferComment: this.transferComment,
        employeeId: "",
      };

      processTransfer(obj)
        .then((res) => {
          this.processTransferVisible = false;
          let that = this;
          that._backToProcessDetail();
          this.$notify({
            title: "成功",
            message: "转办成功",
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("网络出错");
        });
    },
    _remindHandel: function () {
      var param = {
        processInstanceId: this.processWithdrawInfo.processInstanceId,
      };
      this.$confirm("确定是否提醒?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          remindProcessHandle(param)
            .then((res) => {
              this.$notify({
                title: "成功",
                message: "这是一条成功的提示消息",
                type: "success",
              });
            })
            .catch(() => {
              console.log("出错了");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消提醒",
          });
        });
    },
    _deleteProcess: function () {
      let that = this;
      var data = {};
      data.processInstanceId = this.processWithdrawInfo.processInstanceId;

      deleteProcess(data)
        .then((res) => {
          if (res.data.msg == "success") {
            this.$notify({
              title: "成功",
              message: "删除成功",
              type: "success",
            });
            //跳转页面
            that._backToProcessDetail();
          }
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped>
.title-content {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.ibox-title {
  position: relative;
  height: 55px;
}

.wy31-btn {
  display: flex;
  position: absolute;
  top: 6px;
  right: 50px;
}

.theme-white .el-dropdown {
  color: #0ca6fe;
}
</style>
