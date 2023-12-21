<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据编号" prop="documentNumber">
        <el-input v-model="queryParams.documentNumber" placeholder="请输入单据编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出库时间">
        <el-date-picker v-model="daterangeOutWarehouseTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="出库类别" prop="outWarehouseType">
        <el-select v-model="queryParams.outWarehouseType" placeholder="请选择出库类别" clearable>
          <el-option v-for="dict in dict.type.outreceipt_type" :key="dict.value" :label="dict.label" :value="dict.value" />
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
      <el-tab-pane v-for="item in dict.type.receipt_state" :label="item.label" :key="item.value" :name="item.value"> </el-tab-pane>
      <el-tab-pane label="全部" name="all"></el-tab-pane>
    </el-tabs>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['warehouse:outreceipt:add']">创建出库单</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outreceiptList">
      <el-table-column label="单据编号" align="center" prop="documentNumber" />
      <el-table-column label="出库时间" align="center" prop="outWarehouseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outWarehouseTime, "{y}-{m}-{d} {h}:{i}:{s}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库类别" align="center" prop="outWarehouseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.outreceipt_type" :value="scope.row.outWarehouseType" />
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.receipt_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- 1-草稿，2-待扫码，3-待审核，4-已完成 -->
          <el-button size="mini" type="text" icon="el-icon-edit" v-if="scope.row.state == 1" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:outreceipt:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" v-else @click="handleView(scope.row)" v-hasPermi="['warehouse:outreceipt:query']">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-camera-solid" v-if="scope.row.state >= 2" @click="handleView(scope.row)" v-hasPermi="['warehouse:outreceipt:query']">打印</el-button>
          <el-button size="mini" type="text" icon="el-icon-s-check" v-if="scope.row.state == 3" @click="handleApprove(scope.row)" v-hasPermi="['warehouse:outreceipt:approve']">审核</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" v-if="scope.row.state < 3" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:outreceipt:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listOutreceipt, delOutreceipt } from "@/api/warehouse/outreceipt";

export default {
  name: "Outreceipt",
  dicts: ["receipt_state", "outreceipt_type"],
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
      // 出库单据表格数据
      outreceiptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeOutWarehouseTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseId: null,
        documentNumber: null,
        outWarehouseTime: null,
        outWarehouseType: null,
        receiveTime: null,
        receiveId: null,
        receiveName: null,
        auditTime: null,
        auditId: null,
        auditName: null,
        state: "1",
      },
      state: "1",
      // 表单参数
      form: {},
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
    /** 查询出库单据列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOutWarehouseTime && "" != this.daterangeOutWarehouseTime) {
        this.queryParams.params["beginOutWarehouseTime"] = this.daterangeOutWarehouseTime[0];
        this.queryParams.params["endOutWarehouseTime"] = this.daterangeOutWarehouseTime[1];
      }
      listOutreceipt(this.queryParams).then((response) => {
        this.outreceiptList = response.rows;
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
        warehouseOutReceiptId: null,
        warehouseId: null,
        documentNumber: null,
        outWarehouseTime: null,
        outWarehouseType: null,
        receiveTime: null,
        receiveId: null,
        receiveName: null,
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
      this.daterangeOutWarehouseTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({
        path: "/out/outreceipt-edit/edit/0",
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const warehouseoutReceiptId = row.warehouseOutReceiptId;
      this.$router.push({
        path: `/out/outreceipt-edit/edit/${warehouseoutReceiptId}`,
      });
    },
    /**
     * 详情
     * @param {} row
     */
    handleView(row) {
      const warehouseoutReceiptId = row.warehouseOutReceiptId;
      this.$router.push({
        path: `/out/outreceipt-view/view/${warehouseoutReceiptId}`,
      });
    },
    /**
     * 审核
     * @param {} row
     */
    handleApprove(row) {
      const warehouseoutReceiptId = row.warehouseOutReceiptId;

      this.$router.push({
        path: `/out/outreceipt-view/view/${warehouseoutReceiptId}`,
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const documentNumber = row.documentNumber;
      this.$modal
        .confirm("是否确认删除出库单据编号为【" + documentNumber + "】的数据项？")
        .then(function () {
          return delOutreceipt(row.warehouseoutReceiptId);
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
        "warehouse/outreceipt/export",
        {
          ...this.queryParams,
        },
        `outreceipt_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
