<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span> 单据明细</span>
        <el-button style="float: right; margin-left: 10px" type="primary" @click="submitForm(2)">提交</el-button>
        <el-button style="float: right" type="warning" @click="submitForm(1)">草稿</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="入库类别" prop="inWarehouseType">
          <el-radio-group v-model="form.inWarehouseType">
            <el-radio-button v-if="dict.value != 98 && dict.value != 101" v-for="dict in dict.type.inreceipt_type" :key="dict.value" :label="dict.value" :value="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入库仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" filterable placeholder="请选择">
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" filterable placeholder="请选择">
            <el-option v-for="item in supplierList" :key="item.supplierId" :label="item.supplierName" :value="item.supplierId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单号" prop="billNo">
          <el-input v-model="form.billNo" placeholder="请输入单据编号" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-divider></el-divider>
    <el-card>
      <div slot="header" class="clearfix">
        <span>物品明细</span>
        <el-button style="float: right" type="primary" plain @click="addGoods">添加物品</el-button>
      </div>
      <el-table :data="receiptGoodsList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
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
        <el-table-column label="有效期" align="center">
          <template slot-scope="scope">
            <span>{{ calcDeadLine(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="重点物资" align="center" prop="snValidate">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="goodsPrice" width="140">
          <template slot-scope="scope">
            <el-input-number :precision="2" style="width: 120px" size="small" v-model="scope.row.goodsPrice" @change="goodsStateChange(scope.row)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="数量" align="center" prop="goodsNumber" width="140">
          <template slot-scope="scope">
            <el-input-number :precision="0" size="small" v-model="scope.row.goodsNumber" @change="goodsStateChange(scope.row)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="金额" align="center" prop="goodsAmount"> </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 选择物品 -->
    <el-dialog :title="title" :visible.sync="open" width="950px" :close-on-click-modal="false" :close-on-press-escape="false" :destroy-on-close="true">
      <el-row :gutter="20">
        <el-col :span="8" :xs="24">
          <div class="head-container">
            <el-input v-model="categoryName" placeholder="请输入类别名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
          </div>
          <div class="head-container">
            <el-tree :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current @node-click="handleNodeClick" />
          </div>
        </el-col>
        <el-col class="border-left" :span="16" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
            <el-form-item label="" prop="categoryId"> </el-form-item>
            <el-form-item label="" prop="goodsCode">
              <el-input v-model="queryParams.goodsCode" placeholder="请输入物品编号" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="" prop="goodsName">
              <el-input v-model="queryParams.goodsName" placeholder="请输入物品名称" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-table v-loading="loading" :data="goodsList" @selection-change="handleGoodsSelectionChange" @row-dblclick="chooseGoods" row-class-name="not-select">
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
          </el-table>
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getGoodsList" />
          <el-divider></el-divider>
          <div class="flex">
            <el-button type="primary" @click="chooseGoods">添加</el-button>
            <el-button type="warn" @click="removeGoods">移除</el-button>
          </div>
          <el-table :data="selectGoodsList" @row-dblclick="removeGoods">
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
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmChoose">确 定</el-button>
        <el-button @click="() => (this.open = false)">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { listSupplier } from "@/api/warehouse/supplier";
import { listWarehouse } from "@/api/warehouse/warehouse";
import { listGoods } from "@/api/warehouse/goods";
import { categoryTreeSelect } from "@/api/warehouse/goodscategory";
import { addInreceipt, updateInreceipt, getInreceipt } from "@/api/warehouse/inreceipt";
import { inReceiptCode } from "@/api/warehouse/warehousecode";
import { calculateDate } from "@/utils/index";
export default {
  name: "InreceiptEdit",
  dicts: ["goods_specification", "goods_color", "goods_unit", "inreceipt_type", "sys_yes_no"],
  components: {},
  data() {
    return {
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsCode: null,
        goodsName: null,
      },
      // 总条数
      total: 0,
      supplierList: [],
      warehouseList: [],
      goodsList: [],
      //入库单物料明细
      receiptGoodsList: [],
      selectGoodsList: [],
      selectionList: [],
      // 表单校验
      rules: {
        warehouseId: [{ required: true, message: "仓库不能为空", trigger: "blur" }],
        inWarehouseType: [{ required: true, message: "入库类别不能为空", trigger: "blur" }],
        documentNumber: [{ required: true, message: "单据编号不能为空", trigger: "blur" }],
      },
      title: undefined,
      loading: false,
      open: undefined,
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
  mounted() {},
  created() {
    this.init();
    const receiptId = this.$route.params && this.$route.params.inreceiptId;
    if (receiptId != "0") {
      getInreceipt(receiptId).then((res) => {
        this.form = res.data;
        this.receiptGoodsList = res.data.receiptGoodsList;
      });
    } else {
      inReceiptCode().then((res) => {
        this.$set(this.form, "documentNumber", res.msg);
      });
    }
  },
  methods: {
    init() {
      listSupplier().then((res) => {
        this.supplierList = res.rows;
      });
      listWarehouse({ warehouseType: 1 }).then((res) => {
        this.warehouseList = res.rows;
      });
      categoryTreeSelect().then((res) => {
        this.categoryOptions = res.data;
      });
    },
    handleSelectionChange() {},
    handleDelete(row) {
      let index = this.receiptGoodsList.findIndex((t) => t.goodsId == row.goodsId);
      this.receiptGoodsList.splice(index, 1);
    },
    /**
     * 计算物品价格
     */
    goodsStateChange(row) {
      row.goodsPrice = row.goodsPrice.toFixed(2);
      row.goodsNumber = row.goodsNumber.toFixed(2);
      row.goodsAmount = (row.goodsPrice * row.goodsNumber).toFixed(2);
    },
    /**
     * 计算有效期
     */
    calcDeadLine(row) {
      let dataStr = "长期";
      let currentTime = new Date();
      if (!!row.expirationDate && !!row.expirationDateType) {
        if (row.expirationDateType == 1) {
          let data = calculateDate(currentTime, row.expirationDate, 0, 0);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else if (row.expirationDateType == 2) {
          let data = calculateDate(currentTime, 0, row.expirationDate, 0);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else if (row.expirationDateType == 3) {
          let data = calculateDate(currentTime, 0, 0, row.expirationDate);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else {
          return dataStr;
        }
      }
      return dataStr;
    },
    /**
     * 提交
     */
    submitForm(type) {
      if (this.checkGoodsList() == false) {
        return;
      }
      this.form.state = type;
      if (type == 2) {
        this.$modal
          .confirm("是否确认提交？提交之后将不可修改！")
          .then((t) => {
            this.doSubmitForm();
          })
          .catch();
      } else {
        this.doSubmitForm();
      }
    },
    doSubmitForm() {
      this.form.receiptGoodsList = this.receiptGoodsList;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.warehouseInReceiptId != null) {
            updateInreceipt(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.close();
            });
          } else {
            addInreceipt(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.close();
            });
          }
        }
      });
    },
    /**
     * 校验物品
     */
    checkGoodsList() {
      if (this.receiptGoodsList.length == 0) {
        this.$message({
          type: "error",
          message: "物品明细不能为空",
        });
        return false;
      }
      let _res = this.receiptGoodsList.filter((t) => !t.goodsAmount);
      if (_res.length > 0) {
        this.$message({
          type: "error",
          message: "物品金额不能为空",
        });
        return false;
      }
      return true;
    },
    // 弹窗事件
    handleGoodsSelectionChange(selection) {
      this.selectionList = selection;
    },
    /**
     * 打开弹框
     */
    addGoods() {
      this.open = true;
      this.title = `选择物品`;
    },
    /**
     * 选择物品
     */
    chooseGoods(row) {
      if (!!row.goodsId) {
        let _temp = this.selectGoodsList.filter((t) => t.goodsId == row.goodsId);
        if (!!_temp && _temp.length > 0) {
          return;
        }
        this.selectGoodsList.push(row);
      } else {
        this.selectionList.forEach((t) => {
          let _temp = this.selectGoodsList.filter((tt) => t.goodsId == tt.goodsId);
          if (!!_temp && _temp.length > 0) {
            return;
          }
          this.selectGoodsList.push(t);
        });
      }
    },
    confirmChoose() {
      for (let i = 0; i < this.selectGoodsList.length; i++) {
        const element = this.selectGoodsList[i];
        let _temp = this.receiptGoodsList.filter((t) => t.goodsId == element.goodsId);
        if (!!_temp && _temp.length > 0) {
          continue;
        }
        this.receiptGoodsList.push(JSON.parse(JSON.stringify(element)));
      }
      this.selectGoodsList = [];
      this.selectionList = [];
      this.open = false;
    },
    removeGoods(row) {
      if (!!row.goodsId) {
        let index = this.selectGoodsList.findIndex((t) => (t.goodsId = row.goodsId));
        this.selectGoodsList.splice(index, 1);
      } else {
        this.selectGoodsList.pop();
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getGoodsList();
    },
    /** 查询物品列表 */
    getGoodsList() {
      this.loading = true;
      listGoods(this.queryParams).then((response) => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
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
    /** 关闭按钮 */
    close() {
      const obj = { path: "/in/inreceipt" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
<style scoped>
.border-left {
  border-left: 1px solid #dcdfe6;
}
::v-deep .not-select {
  cursor: pointer;
}
</style>
