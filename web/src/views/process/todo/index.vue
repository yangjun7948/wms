<template>
  <div class="app-container">
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
        <!-- <pagination v-show="page.total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" /> -->
        <pagination :total="page.total" :limit.sync="page.pageSize" :page.sync="page.currentPage" @pagination="gotoPage"> </pagination>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import processTree from "@/components/business/process/processTree";
import { processTodo } from "@/api/process/ehrProcess";

export default {
  name: "process-todo",
  data() {
    return {
      processName: "",
      tableData: [],
      procType: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        records: 0,
      },
      isExpand: false,
    };
  },
  components: {
    processTree,
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.init();
  },
  mounted() {},
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
      this.procType = [];
      this.getProcessList(this.page);
    },
    getProcessList(page) {
      let _this = this;
      processTodo({
        keyWord: _this.processName,
        procTypeList: _this.procType,
        row: page.pageSize,
        page: page.currentPage,
      }).then((res) => {
        let result = res.data;
        _this.tableData = result.myProcessApplyDtos;
        _this.page.total = result.total;
        _this.page.records = result.records;
      });
    },
    gotoPage() {
      // this.page.currentPage = value;
      this.getProcessList(this.page);
    },
    handleEdit(rows) {
      let type = "0";
      let statusType = rows["statusType"];
      let delegation = rows["delegation"];
      let suspensionState = rows["suspensionState"];
      if (!statusType || statusType == "RUNNING") {
        this.$router.push({
          path: "/flow/new-view/view",
          query: {
            procNum: rows.procNum + "-detail",
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            delegation: delegation,
            suspensionState: suspensionState,
          },
        });
      }
      if (statusType == "WITHDRAW") {
        this.$router.push({
          path: "/flow/new-view/view",
          query: {
            procNum: rows.procNum + "-detail",
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            statusType: statusType,
            procCode: rows.processCode,
            delegation: delegation,
            suspensionState: suspensionState,
          },
        });
      }
      if (statusType == "SAVE") {
        this.$router.push({
          path: "/flow/new-add/add",
          query: {
            procNum: rows.procNum,
            procName: rows.taskDescription,
            processInstanceId: rows.processInstanceId,
            taskId: rows.taskId,
            type: type,
            procCode: rows.processCode,
            delegation: delegation,
            suspensionState: suspensionState,
          },
        });
      }
    },
    handleNodeClick(data) {
      console.log(data);
      this.procType = [];
      if (!!data.processDtoList) {
        this.procType = Array.from(data.processDtoList, (t) => t.procCode);
      } else {
        this.procType.push(data.procCode);
      }
      this.page.currentPage = 1;
      this.getProcessList(this.page);
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
  margin-left: 5px;
  width: 1%;
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
