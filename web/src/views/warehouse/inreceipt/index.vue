<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据编号" prop="documentNumber">
        <el-input v-model="queryParams.documentNumber" placeholder="请输入单据编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="入库时间">
        <el-date-picker v-model="daterangeInWarehouseTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="入库类别" prop="inWarehouseType">
        <el-select v-model="queryParams.inWarehouseType" placeholder="请选择入库类别" clearable>
          <el-option v-for="dict in dict.type.inreceipt_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.receipt_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="state">
      <el-tab-pane v-for="item in dict.type.receipt_state" :label="item.label" :key="item.value" :name="item.value"> </el-tab-pane>
      <el-tab-pane label="全部" name="all"></el-tab-pane>
    </el-tabs>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['warehouse:inreceipt:add']">创建入库单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inreceiptList">
      <el-table-column label="单据编号" align="center" prop="documentNumber" />
      <el-table-column label="入库时间" align="center" prop="inWarehouseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inWarehouseTime, "{y}-{m}-{d} {h}:{i}:{s}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库类别" align="center" prop="inWarehouseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inreceipt_type" :value="scope.row.inWarehouseType" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.receipt_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- 1-草稿，2-待扫码，3-待审核，4-已完成 -->
          <el-button size="mini" type="text" icon="el-icon-edit" v-if="scope.row.state == 1" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:inreceipt:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" v-else @click="handleView(scope.row)" v-hasPermi="['warehouse:inreceipt:query']">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-camera-solid" v-if="scope.row.state >= 2" @click="handleView(scope.row)" v-hasPermi="['warehouse:inreceipt:query']">打印</el-button>
          <el-button size="mini" type="text" icon="el-icon-s-check" v-if="scope.row.state == 3" @click="handleApprove(scope.row)" v-hasPermi="['warehouse:inreceipt:approve']">审核</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" v-if="scope.row.state < 3" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:inreceipt:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listInreceipt, getInreceipt, delInreceipt } from "@/api/warehouse/inreceipt";

export default {
  name: "Inreceipt",
  dicts: ["receipt_state", "inreceipt_type"],
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
      // 入库单据表格数据
      inreceiptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeInWarehouseTime: [],
      state: "1",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseId: null,
        documentNumber: null,
        inWarehouseTime: null,
        inWarehouseType: null,
        auditTime: null,
        auditId: null,
        auditName: null,
        state: "1",
      },
    };
  },
  created() {
    this.getList();
  },
  watch: {
    state: {
      handler: function (val) {
        if (val == "all") {
          this.queryParams.state = "";
        } else {
          this.queryParams.state = val;
        }
        this.getList();
      },
    },
  },
  methods: {
    /** 查询入库单据列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeInWarehouseTime && "" != this.daterangeInWarehouseTime) {
        this.queryParams.params["beginInWarehouseTime"] = this.daterangeInWarehouseTime[0];
        this.queryParams.params["endInWarehouseTime"] = this.daterangeInWarehouseTime[1];
      }
      listInreceipt(this.queryParams).then((response) => {
        this.inreceiptList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        warehouseInReceiptId: null,
        warehouseId: null,
        documentNumber: null,
        inWarehouseTime: null,
        inWarehouseType: null,
        auditTime: null,
        auditId: null,
        auditName: null,
        state: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeInWarehouseTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({
        path: "/in/inreceipt-edit/edit/0",
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const warehouseInReceiptId = row.warehouseInReceiptId;
      this.$router.push({
        path: `/in/inreceipt-edit/edit/${warehouseInReceiptId}`,
      });
    },
    handleView(row) {
      const warehouseInReceiptId = row.warehouseInReceiptId;
      this.$router.push({
        path: `/in/inreceipt-view/view/${warehouseInReceiptId}`,
      });
    },
    handleApprove(row) {
      const warehouseInReceiptId = row.warehouseInReceiptId;
      this.$router.push({
        path: `/in/inreceipt-view/view/${warehouseInReceiptId}`,
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const documentNumber = row.documentNumber;
      this.$modal
        .confirm("是否确认删除入库单据编号为【" + documentNumber + "】的入库单？")
        .then(function () {
          return delInreceipt(row.warehouseInReceiptId);
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
        "warehouse/inreceipt/export",
        {
          ...this.queryParams,
        },
        `inreceipt_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
