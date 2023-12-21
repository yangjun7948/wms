<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请人" prop="applyUserName">
        <el-input v-model="queryParams.applyUserName" placeholder="请输入申请人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker v-model="daterangeApplyTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="要货仓库" prop="applyWarehouseId">
        <el-select v-model="queryParams.applyWarehouseId" placeholder="请选择要货仓库" clearable>
          <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs v-model="state">
      <el-tab-pane v-for="item in dict.type.apply_state" :label="item.label" :key="item.value" :name="item.value"> </el-tab-pane>
      <el-tab-pane label="全部" name="all"></el-tab-pane>
    </el-tabs>

    <el-table v-loading="loading" :data="applyList">
      <el-table-column label="单据编号" align="center" prop="documentNumber" />
      <el-table-column label="要货人" align="center" prop="applyUserName" />
      <el-table-column label="要货仓库" align="center" prop="applyWarehouseName" />
      <el-table-column label="要货时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, "{y}-{m}-{d} {h}:{i}:{s}") }}</span>
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
      <el-table-column label="单据状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.apply_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" v-if="scope.row.state == '1'" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:apply:edit']">生成出库单</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)" v-hasPermi="['warehouse:apply:list']">查看</el-button>
          <el-button size="mini" type="text" v-if="scope.row.state == '1'" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:apply:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listApply, getApply, delApply, addApply, updateApply } from "@/api/warehouse/apply";
import { listWarehouse } from "@/api/warehouse/warehouse";
export default {
  name: "Apply",
  dicts: ["apply_state"],
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
      // 要货申请表格数据
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeApplyTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyUserId: null,
        documentNumber: null,
        applyTime: null,
        state: "1",
      },
      state: "1",
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      warehouseList: [],
    };
  },
  created() {
    this.init();
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
    init() {
      listWarehouse({ warehouseType: 2 }).then((res) => {
        this.warehouseList = res.rows;
      });
    },
    /** 查询要货申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeApplyTime && "" != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      listApply(this.queryParams).then((response) => {
        this.applyList = response.rows;
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
        applyReceiptId: null,
        applyUserId: null,
        documentNumber: null,
        applyTime: null,
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
      this.daterangeApplyTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const applyReceiptId = row.applyReceiptId;
      this.$router.push({
        path: `/apply/apply-edit/edit/${applyReceiptId}`,
      });
    },
    handleView(row) {
      const applyReceiptId = row.applyReceiptId;
      this.$router.push({
        path: `/apply/apply-view/view/${applyReceiptId}`,
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.applyReceiptId != null) {
            updateApply(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApply(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const applyReceiptIds = row.applyReceiptId || this.ids;
      this.$modal
        .confirm('是否确认删除要货申请编号为"' + applyReceiptIds + '"的数据项？')
        .then(function () {
          return delApply(applyReceiptIds);
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
        "warehouse/apply/export",
        {
          ...this.queryParams,
        },
        `apply_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
