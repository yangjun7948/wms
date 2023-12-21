<template>
  <basic-container>
    <div class="process-container">
      <div class="process-container-left">
        <process-tree @nodeClick="handleNodeClick"></process-tree>
      </div>
      <div @click="expand" class="process-container-split">
        <i v-if="isExpand" class="el-icon-arrow-right"></i>
        <i v-else class="el-icon-arrow-left"></i>
      </div>
      <div class="process-container-right">
        <div>
          <el-form label-width="80px" :inline="true" label-position="left">
            <el-form-item label="">
              <el-input v-model="processName" clearable placeholder="请输入流程名称"></el-input>
            </el-form-item>
            <el-button @click="search" type="primary" size="small">查询</el-button>
          </el-form>
        </div>
        <overlay-scrollbars class="scroll-table">
          <el-table :data="tableData" tooltip-effect="dark" style="width: 100%">
            <el-table-column prop="taskDescriptionSupplement" label="请求标题" width="400">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.taskDescriptionSupplement" placement="bottom" effect="light">
                  <div style="display: block; word-break: keep-all; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
                    {{ scope.row.taskDescriptionSupplement }}
                  </div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="userName" label="创建人" align="center"> </el-table-column>
            <el-table-column label="创建时间" align="center" width="200" prop="createTime"> </el-table-column>
            <el-table-column label="当前节点" align="center" prop="nodeName"> </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="medium" v-on:click="handleEdit(scope.row)"> 详情 </el-button>
              </template>
            </el-table-column>
          </el-table>
        </overlay-scrollbars>
        <el-pagination align="right" @current-change="gotoPage" :current-page="this.page.currentPage" :page-size="page.pageSize" layout="total, prev, pager, next, jumper" :total="this.page.total" />
      </div>
    </div>
  </basic-container>
</template>
<script>
import { mapGetters } from "vuex";
import processTree from "@/components/business/process/processTree";
import { processMonitor } from "@/api/process/ehrProcess";

export default {
  name: "process-request",
  data() {
    return {
      processName: "",
      tableData: [],
      procType: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      },
      isExpand: false,
    };
  },
  components: {
    processTree,
  },
  computed: {
    ...mapGetters(["userInfo", "permissions"]),
  },
  created() {
    this.init();
  },
  mounted() {
    this.initScroll();
  },
  methods: {
    init() {
      this.search();
    },
    search() {
      this.page = {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      };
      this.procType = "";
      this.getProcessList(this.page);
    },
    getProcessList(page) {
      let _this = this;
      processMonitor({
        procTitle: _this.processName,
        procCode: _this.procType,
        row: page.pageSize,
        page: page.currentPage,
      }).then((res) => {
        let result = res.data.data;
        _this.tableData = result.processApplyDtoList;
        _this.page.total = result.total;
      });
    },
    gotoPage(value) {
      this.page.currentPage = value;
      this.getProcessList(this.page);
    },
    handleNodeClick(data) {
      this.procType = [];
      if (!!data.processDtoList) {
        this.procType = "";
      } else {
        this.procType = data.procCode;
      }
      this.page.currentPage = 1;
      this.getProcessList(this.page);
    },

    //跳转页面
    handleEdit(rows) {
      let type = "2";
      let statusType = rows["statusType"];
      let procCode = rows["processCode"];
      if (!statusType || statusType == "RUNNING") {
        this.$router.push({
          name: "流程详情",
          query: {
            procNum: rows.procNum + "-detail",
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            statusType: statusType,
            procCode: procCode,
            showWithdrawFlag: rows.showWithdrawFlag,
            curTaskHandler: rows.curTaskHandler,
            delegation: rows.delegation,
            suspensionState: rows.suspensionState,
          },
        });
      }
      if (statusType == "WITHDRAW") {
        this.$router.push({
          name: "流程详情",
          query: {
            procNum: rows.procNum + "-detail",
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            statusType: statusType,
            procCode: procCode,
            showWithdrawFlag: rows.showWithdrawFlag,
            curTaskHandler: rows.curTaskHandler,
            delegation: rows.delegation,
            suspensionState: rows.suspensionState,
          },
        });
      }
      if (statusType == "SAVE") {
        this.$router.push({
          name: "流程新建",
          query: {
            procNum: rows.procNum,
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            statusType: statusType,
            procCode: procCode,
            showWithdrawFlag: rows.showWithdrawFlag,
          },
        });
      }
    },
    expand() {
      if (!this.isExpand) {
        $(".process-container-left").css({ "-webkit-animation": "hideLeftContainer 0.5s", "animation-fill-mode": "forwards" });
        $(".process-container-right").css({ "-webkit-animation": "expRightContainer 0.5s", "animation-fill-mode": "forwards" });
      } else {
        $(".process-container-left").css({ "-webkit-animation": "showLeftContainer 0.5s", "animation-fill-mode": "forwards" });
        $(".process-container-right").css({ "-webkit-animation": "cosRightContainer 0.5s", "animation-fill-mode": "forwards" });
      }
      this.isExpand = !this.isExpand;
    },
  },
};
</script>
<style scoped>
.scroll-table {
  height: calc(100% - 100px);
}

.process-container {
  height: 100%;
  display: flex;
}

.process-container-split {
  width: 2%;
  cursor: pointer;
  display: flex;
  align-items: center;
  border-left: 1px solid silver;
}

.process-container-left {
  height: 100%;
  width: 23%;
}

.process-container-right {
  height: 100%;
  width: 75%;
}
</style>
