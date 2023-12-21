<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="categoryName" placeholder="请输入类别名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree node-key="id" ref="tree" show-checkbox :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" default-expand-all highlight-current />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form ref="queryForm" :model="queryParams" :inline="true" size="small" v-show="showSearch" label-width="80px">
          <el-form-item label="物品编号" prop="goodsCode">
            <el-input v-model="queryParams.goodsCode" placeholder="请输入物品编号" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="物品名称" prop="goodsName">
            <el-input v-model="queryParams.goodsName" placeholder="请输入物品名称" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="仓库" prop="warehouseIds">
            <el-select v-model="queryParams.warehouseIds" multiple placeholder="请选择仓库">
              <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="" prop="showDeadLineTime">
            <el-checkbox v-model="queryParams.showDeadLineTime" @change="handleQuery">显示物品有效期</el-checkbox>
          </el-form-item> -->
          <el-form-item label="" prop="showDeadLineTime">
            <el-checkbox v-model="queryParams.showPrice" @change="handleQuery">显示单价</el-checkbox>
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
        <el-table stripe v-loading="loading" :data="stockGoodsList" row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
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
          <el-table-column label="最小库存" align="center" prop="minStock" />
          <el-table-column label="最大库存" align="center" prop="maxStock" />
          <el-table-column label="物品图片" align="center" prop="goodsPicture" width="100">
            <template slot-scope="scope">
              <image-preview :src="scope.row.goodsPicture" :width="50" :height="50" />
            </template>
          </el-table-column>
          <el-table-column label=" 重点物资" align="center" prop="snValidate">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
            </template>
          </el-table-column>
          <el-table-column :key="item.warehouseId" v-for="item in selectWarehouseList" :label="item.warehouseName">
            <el-table-column label="数量" :prop="item.warehouseId + '1'"> </el-table-column>
            <el-table-column label="单价" :prop="item.warehouseId + '2'" v-if="queryParams.showPrice"> </el-table-column>
            <el-table-column label="金额" :prop="item.warehouseId + '3'" v-if="queryParams.showPrice"> </el-table-column>
            <el-table-column v-if="queryParams.showDeadLineTime" label="有效期" :prop="item.warehouseId + '4'">
              <template slot-scope="scope">
                <span>{{ showDeadLineTime(scope.row, item.warehouseId) }}</span>
              </template>
            </el-table-column>
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
import { listWarehouse } from "@/api/warehouse/warehouse";
import { listStockGoodsReport } from "@/api/warehouse/goods";
export default {
  name: "",
  dicts: ["goods_specification", "goods_color", "goods_unit", "sys_yes_no", "sys_date_type"],
  components: {},
  data() {
    return {
      // 遮罩层
      loading: false,
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
      pageParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数
      queryParams: {
        goodsCode: null,
        goodsName: null,
        categoryId: null,
        warehouseIds: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      warehouseList: [],
      selectWarehouseList: [],
      //类别名称
      categoryName: undefined,
      categoryOptions: undefined,
      stockGoodsList: [],
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
    this.init();
  },
  methods: {
    init() {
      let _this = this;
      categoryTreeSelect().then((res) => {
        _this.categoryOptions = [{ id: 0, label: "全部", children: res.data }];
        _this.$refs.tree.setCheckedKeys([0]);
      });
      listWarehouse().then((res) => {
        this.warehouseList = res.rows;
      });
    },
    /** 查询物品列表 */
    getList() {
      this.loading = true;
      let _this = this;
      if (_this.queryParams.warehouseIds.length == 0) {
        this.selectWarehouseList = this.warehouseList;
      } else {
        this.selectWarehouseList = this.warehouseList.filter((t) => _this.queryParams.warehouseIds.indexOf(t.warehouseId) >= 0);
      }

      this.queryParams.categoryIds = this.$refs.tree.getCheckedKeys();
      listStockGoodsReport(this.queryParams, this.pageParams).then((response) => {
        this.stockGoodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.categoryId = data.id;
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    showDeadLineTime(row, warehouseId) {
      if (new Date(row[warehouseId + "4"]).getTime() > new Date("2099-01-01").getTime()) {
        return "长期有效";
      } else {
        return this.parseTime(row[warehouseId + "4"], "{y}-{m}-{d}");
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "warehouse/report/stock/export",
        {
          ...this.queryParams,
        },
        `库存记录_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped></style>
