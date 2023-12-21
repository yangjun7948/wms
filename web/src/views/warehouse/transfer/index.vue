<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据编号" prop="documentNumber">
        <el-input v-model="queryParams.documentNumber" placeholder="请输入单据编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="调拨时间">
        <el-date-picker v-model="daterangeTransferWarehouseTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="调拨类别" prop="transferType">
        <el-select v-model="queryParams.transferType" placeholder="请选择调拨类别" clearable>
          <el-option v-for="dict in dict.type.transfer_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="单据状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择单据状态" clearable>
          <el-option v-for="dict in dict.type.receipt_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="state">
      <el-tab-pane v-for="item in dict.type.transfer_state" :label="item.label" :key="item.value" :name="item.value"> </el-tab-pane>
      <el-tab-pane label="全部" name="all"></el-tab-pane>
    </el-tabs>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['warehouse:transfer:add']">新增调拨单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList">
      <el-table-column label="单据编号" align="center" prop="documentNumber" />
      <el-table-column label="调出仓库" align="center" prop="transferOutWarehouseName" />
      <el-table-column label="调入仓库" align="center" prop="transferInWarehouseName" />
      <el-table-column label="调拨时间" align="center" prop="transferWarehouseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transferWarehouseTime, "{y}-{m}-{d} {h}:{i}:{s}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="调拨类别" align="center" prop="transferType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_type" :value="scope.row.transferType" />
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="关联出库单" align="center" prop="state" width="140">
        <template slot-scope="scope">
          <el-button @click="goOutReceipt(scope.row)" v-if="!!scope.row.outDocumentNumber" size="mini" type="text" icon="el-icon-view">{{ scope.row.outDocumentNumber }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="关联入库单" align="center" prop="state" width="140">
        <template slot-scope="scope">
          <el-button @click="goInReceipt(scope.row)" v-if="!!scope.row.inDocumentNumber" size="mini" type="text" icon="el-icon-view">{{ scope.row.inDocumentNumber }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- 1-草稿，2-调拨中，3-已完成 -->
          <el-button size="mini" type="text" icon="el-icon-edit" v-if="scope.row.state == 1" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:transfer:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" v-else @click="handleView(scope.row)" v-hasPermi="['warehouse:transfer:query']">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-camera-solid" v-if="scope.row.state >= 2" @click="handleView(scope.row)" v-hasPermi="['warehouse:transfer:query']">打印</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" v-if="scope.row.state < 2" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:transfer:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listTransfer, delTransfer } from "@/api/warehouse/transfer";

export default {
  name: "Transfer",
  dicts: ["transfer_type", "transfer_state"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调拨单据表格数据
      transferList: [],
      // 备注时间范围
      daterangeTransferWarehouseTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        transferOutWarehouseId: null,
        transferInWarehouseId: null,
        documentNumber: null,
        transferWarehouseTime: null,
        transferType: null,
        state: "1",
        auditTime: null,
        auditId: null,
        auditName: null,
      },
      state: "1",
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
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
  created() {
    this.getList();
  },
  methods: {
    /** 查询调拨单据列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeTransferWarehouseTime && "" != this.daterangeTransferWarehouseTime) {
        this.queryParams.params["beginTransferWarehouseTime"] = this.daterangeTransferWarehouseTime[0];
        this.queryParams.params["endTransferWarehouseTime"] = this.daterangeTransferWarehouseTime[1];
      }
      listTransfer(this.queryParams).then((response) => {
        this.transferList = response.rows;
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
        warehouseTransferReceiptId: null,
        transferOutWarehouseId: null,
        transferInWarehouseId: null,
        documentNumber: null,
        transferWarehouseTime: null,
        transferType: null,
        state: null,
        auditTime: null,
        auditId: null,
        auditName: null,
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
      this.daterangeTransferWarehouseTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({
        path: "/transfer/transfer-edit/edit/0",
      });
    },
    /**
     * 详情
     * @param {} row
     */
    handleView(row) {
      const warehouseTransferReceiptId = row.warehouseTransferReceiptId;
      this.$router.push({
        path: `/transfer/transfer-view/view/${warehouseTransferReceiptId}`,
      });
    },
    goOutReceipt(row) {
      const outId = row.outReceiptId;
      this.$router.push({
        path: `/out/outreceipt-view/view/${outId}`,
      });
    },
    goInReceipt(row) {
      const inId = row.inReceiptId;
      this.$router.push({
        path: `/in/inreceipt-view/view/${inId}`,
      });
    },
    /**
     * 审核
     * @param {} row
     */
    handleApprove(row) {
      const warehouseTransferReceiptId = row.warehouseTransferReceiptId;
      this.$router.push({
        path: `/transfer/transfer-view/view/${warehouseTransferReceiptId}`,
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const warehouseTransferReceiptId = row.warehouseTransferReceiptId;
      this.$router.push({
        path: `/transfer/transfer-edit/edit/${warehouseTransferReceiptId}`,
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const warehouseTransferReceiptIds = row.warehouseTransferReceiptId || this.ids;
      this.$modal
        .confirm('是否确认删除调拨单据编号为"' + warehouseTransferReceiptIds + '"的数据项？')
        .then(function () {
          return delTransfer(warehouseTransferReceiptIds);
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
        "warehouse/transfer/export",
        {
          ...this.queryParams,
        },
        `transfer_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
