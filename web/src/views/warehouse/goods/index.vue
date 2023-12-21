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
          <el-form-item label="" prop="categoryId"> </el-form-item>
          <el-form-item label="物品编号" prop="goodsCode">
            <el-input v-model="queryParams.goodsCode" placeholder="请输入物品编号" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="物品名称" prop="goodsName">
            <el-input v-model="queryParams.goodsName" placeholder="请输入物品名称" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="重点物资" prop="snValidate">
            <el-select v-model="queryParams.snValidate" placeholder="请选择" clearable>
              <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['warehouse:goods:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['warehouse:goods:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['warehouse:goods:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['warehouse:goods:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
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
          <el-table-column label="物品图片" align="center" prop="goodsPicture" width="100">
            <template slot-scope="scope">
              <image-preview :src="scope.row.goodsPicture" :width="50" :height="50" />
            </template>
          </el-table-column>
          <!-- <el-table-column label="有效期" align="center" prop="expirationDate" />
          <el-table-column label="类型" align="center" prop="expirationDateType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_date_type" :value="scope.row.expirationDateType" />
            </template>
          </el-table-column> -->
          <el-table-column label="质保时间" align="center" prop="warrantyDate" />
          <el-table-column label="类型" align="center" prop="warrantyDateType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_date_type" :value="scope.row.warrantyDateType" />
            </template>
          </el-table-column>
          <el-table-column label=" 重点物资" align="center" prop="snValidate">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['warehouse:goods:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['warehouse:goods:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改物品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="类别编号" prop="categoryCode">
              <treeselect v-model="form.categoryId" :options="categoryOptions" :show-count="true" placeholder="请选择物品类别" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物品编号" prop="goodsCode">
              <el-input v-if="!!form.goodsId" disabled v-model="form.goodsCode" />
              <el-input v-else disabled placeholder="系统自动生成" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品名称" prop="goodsName"> <el-input v-model="form.goodsName" placeholder="请输入物品名称" /> </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="specificationType">
              <el-select style="width: 100%" v-model="form.specificationType" placeholder="请选择规格型号">
                <el-option v-for="dict in dict.type.goods_specification" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物品颜色" prop="colorType">
              <el-select style="width: 100%" v-model="form.colorType" placeholder="请选择物品颜色">
                <el-option v-for="dict in dict.type.goods_color" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量单位" prop="unitType">
              <el-select style="width: 100%" v-model="form.unitType" placeholder="请选择计量单位">
                <el-option v-for="dict in dict.type.goods_unit" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最小库存" prop="minStock"> <el-input-number style="width: 100%" v-model="form.minStock" placeholder="请输入最小库存" /> </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最大库存" prop="maxStock"> <el-input-number style="width: 100%" v-model="form.maxStock" placeholder="请输入最大库存" /> </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="有效期" prop="expirationDate"> <el-input-number style="width: 100%" v-model="form.expirationDate" placeholder="请输入有效期" /> </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="expirationDateType">
              <el-select style="width: 100%" v-model="form.expirationDateType" placeholder="请选择类型">
                <el-option v-for="dict in dict.type.sys_date_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="质保时间" prop="warrantyDate">
              <el-input-number style="width: 100%" v-model="form.warrantyDate" placeholder="请输入质保时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="warrantyDateType">
              <el-select style="width: 100%" v-model="form.warrantyDateType" placeholder="请选择类型">
                <el-option v-for="dict in dict.type.sys_date_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="重点物资">
              <el-radio-group v-model="form.snValidate">
                <el-radio v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="损耗状态">
              <el-radio-group v-model="form.useState">
                <el-radio v-for="dict in dict.type.goods_use_state" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="物品图片"> <image-upload v-model="form.goodsPicture" /> </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGoods, getGoods, delGoods, addGoods, updateGoods } from "@/api/warehouse/goods";
import { categoryTreeSelect } from "@/api/warehouse/goodscategory";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Goods",
  dicts: ["goods_specification", "goods_color", "goods_unit", "sys_yes_no", "sys_date_type", "goods_use_state"],
  components: { Treeselect },
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
      // 物品表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsCode: null,
        goodsName: null,
        categoryIds: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //类别名称
      categoryName: undefined,
      categoryOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label",
      },
    };
  },
  created() {
    categoryTreeSelect().then((res) => {
      this.categoryOptions = res.data;
      this.categoryOptions = [{ id: 0, label: "全部", children: res.data }];
      this.$refs.tree.setCheckedKeys([0]);
    });
  },
  mounted() {
    this.getList();
  },
  methods: {
    /** 查询物品列表 */
    getList() {
      this.loading = true;
      this.queryParams.categoryIds = this.$refs.tree.getCheckedKeys();
      listGoods(this.queryParams).then((response) => {
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        goodsId: null,
        categoryId: null,
        categoryCode: null,
        goodsCode: null,
        goodsName: null,
        area: null,
        specificationType: null,
        unitType: null,
        colorType: null,
        minStock: null,
        maxStock: null,
        goodsPicture: null,
        expirationDateType: null,
        expirationDate: null,
        warrantyDateType: null,
        useState: null,
        warrantyDate: null,
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.goodsId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const goodsId = row.goodsId || this.ids;
      getGoods(goodsId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.goodsId != null) {
            updateGoods(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then((response) => {
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
      const goodsIds = row.goodsId || this.ids;
      const notice = row.goodsIds ? `是否确认删除物品【${row.goodsName}】？` : `是否确认删除选中的${this.ids.length}个物品？`;
      this.$modal
        .confirm(notice)
        .then(function () {
          return delGoods(goodsIds);
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
        "warehouse/goods/export",
        {
          ...this.queryParams,
        },
        `goods_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
