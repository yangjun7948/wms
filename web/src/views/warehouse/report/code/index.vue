<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="categoryName" placeholder="请输入类别名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree node-key="id" :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current show-checkbox />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
          <el-form-item label="溯源码" prop="snCode">
            <el-input v-model="queryParams.snCode" placeholder="请输入溯源码" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="物品编号" prop="goodsCode">
            <el-input v-model="queryParams.goodsCode" placeholder="请输入物品编号" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="物品名称" prop="goodsName">
            <el-input v-model="queryParams.goodsName" placeholder="请输入物品名称" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['warehouse:goods:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="goodsList">
          <el-table-column label="溯源码" align="center" prop="snCode" width="150">
            <template slot-scope="scope">
              <el-button type="text" @click="handleView(scope.row)">{{ scope.row.snCode }}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="物品状态" align="center" prop="traceState">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.trace_state" :value="scope.row.traceState" />
            </template>
          </el-table-column>
          <el-table-column label="物品类别" align="center" prop="categoryName" />
          <el-table-column label="物品编号" align="center" prop="goodsCode" />
          <el-table-column label="物品名称" align="center" prop="goodsName" />
          <el-table-column label="规格型号" align="center" prop="specificationType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.goods_specification" :value="scope.row.specificationType" />
            </template>
          </el-table-column>
          <el-table-column label="计量单位" align="center" prop="unitType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.goods_unit" :value="scope.row.unitType" />
            </template>
          </el-table-column>
          <el-table-column label="物品颜色" align="center" prop="colorType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.goods_color" :value="scope.row.colorType" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="pageParams.pageNum" :limit.sync="pageParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { categoryTreeSelect } from "@/api/warehouse/goodscategory";
import { listGoodsCode } from "@/api/warehouse/report";
export default {
  name: "",
  dicts: ["goods_specification", "goods_color", "goods_unit", "code_state", "trace_state"],
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物品表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      pageParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数
      queryParams: {
        goodsCode: null,
        goodsName: null,
        categoryIds: null,
      },
      categoryName: undefined,
      categoryOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label",
      },
    };
  },
  computed: {
    ...mapGetters([]),
  },
  mounted() {
    this.getList();
  },
  created() {
    categoryTreeSelect().then((res) => {
      this.categoryOptions = res.data;
      this.categoryOptions = [{ id: 0, label: "全部", children: res.data }];
      this.$refs.tree.setCheckedKeys([0]);
    });
  },
  methods: {
    /** 查询物品列表 */
    getList() {
      this.loading = true;
      this.queryParams.categoryIds = this.$refs.tree.getCheckedKeys();
      listGoodsCode(this.queryParams, this.pageParams).then((response) => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageParams.pageNum = 1;
      this.getList();
    },
    handleView(row) {
      const snCode = row.snCode;
      this.$router.push({
        path: `/report/code-view/view/${snCode}`,
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "warehouse/report/code/export",
        {
          ...this.queryParams,
        },
        `溯源码${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped></style>
