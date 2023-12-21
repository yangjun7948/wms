<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" filterable placeholder="请选择">
          <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="盘点单号" prop="documentNumber">
        <el-input v-model="queryParams.documentNumber" placeholder="请输入盘点单号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="盘点时间">
        <el-date-picker v-model="daterangeCheckTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.check_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['warehouse:check:add']">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkList">
      <el-table-column label="盘点单号" align="center" prop="documentNumber" />
      <el-table-column label="盘点人" align="center" prop="checkUserName" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="盘点时间" align="center" prop="checkTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.check_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" v-if="scope.row.state == 1" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:check:edit']">修改</el-button>
          <el-button size="mini" type="text" v-if="scope.row.state == 1" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:check:remove']">删除</el-button>
          <el-button size="mini" type="text" v-if="scope.row.state == 2" icon="el-icon-edit" @click="handleView(scope.row)" v-hasPermi="['warehouse:check:list']">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listCheck, getCheck, delCheck, addCheck, updateCheck } from "@/api/warehouse/check";
import { listWarehouse } from "@/api/warehouse/warehouse";

export default {
  name: "Check",
  dicts: ["check_state"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存盘点表格数据
      checkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCheckTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        state: null,
        checkUserId: null,
        warehouseId: null,
        documentNumber: null,
        checkTime: null,
      },
      warehouseList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.init();
    this.getList();
  },
  methods: {
    init() {
      listWarehouse({ warehouseType: 1 }).then((res) => {
        this.warehouseList = res.rows;
      });
    },
    /** 查询库存盘点列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckTime && "" != this.daterangeCheckTime) {
        this.queryParams.params["beginCheckTime"] = this.daterangeCheckTime[0];
        this.queryParams.params["endCheckTime"] = this.daterangeCheckTime[1];
      }
      listCheck(this.queryParams).then((response) => {
        this.checkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCheckTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({
        path: "/report/check-edit/edit/0",
      });
    },
    handleView(row) {
      this.$router.push({
        path: `/report/check-view/view/${row.checkReceiptId}`,
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({
        path: `/report/check-edit/edit/${row.checkReceiptId}`,
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const documentNmber = row.documentNumber;

      this.$modal
        .confirm('是否确认删除库存盘点编号为"' + documentNmber + '"的数据项？')
        .then(function () {
          return delCheck([row.checkReceiptId]);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "warehouse/check/export",
        {
          ...this.queryParams,
        },
        `check_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
