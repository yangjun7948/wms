<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>单据明细</span>
        <el-button style="float: right; margin-left: 10px" type="primary" @click="submitForm(2)">提交</el-button>
        <el-button style="float: right" type="warning" @click="submitForm(1)">草稿</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="出库类别" prop="outWarehouseType">
          <el-radio-group v-model="form.outWarehouseType">
            <el-radio-button v-if="dict.value != 99 && dict.value != 100" v-for="dict in dict.type.outreceipt_type" :key="dict.value" :label="dict.value" :value="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出库仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" filterable placeholder="请选择" @change="warehouseChange">
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收门店" prop="receiveId">
          <el-select v-model="form.receiveId" filterable placeholder="请选择">
            <el-option v-for="item in storeList" :key="item.storeId" :label="item.storeName" :value="item.storeId"> </el-option>
          </el-select>
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
      <el-table :data="receiptGoodsList">
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
        <el-table-column label="到期日期" align="center">
          <template slot-scope="scope">
            <span>{{ formatterDeadLine(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="重点物资" align="center" prop="snValidate">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="goodsPrice"> </el-table-column>
        <el-table-column label="数量" align="center" prop="goodsNumber"> </el-table-column>
        <el-table-column label="金额" align="center" prop="goodsAmount"> </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 选择物品 -->
    <el-dialog :title="title" :visible.sync="open" width="1124px" :close-on-click-modal="false" :close-on-press-escape="false" :destroy-on-close="true">
      <el-row :gutter="20">
        <el-col :span="6" :xs="24">
          <div class="head-container">
            <el-input v-model="categoryName" placeholder="请输入类别名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
          </div>
          <div class="head-container">
            <el-tree :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current @node-click="handleNodeClick" />
          </div>
        </el-col>
        <el-col class="border-left" :span="18" :xs="24">
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
            <el-table-column type="selection" width="55" align="center" :selectable="selectableHandler" />
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
            <el-table-column label="库存数量" align="center" prop="stockNumber" />
            <el-table-column label="单价" align="center" prop="goodsPrice" />
            <el-table-column label="到期日期" width="120" align="center" prop="goodsDeadlineTime">
              <template slot-scope="scope">
                <span>{{ showDeadLineTime(scope.row) }}</span>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getGoodsList" />
          <el-divider></el-divider>
          <div class="flex">
            <el-button type="primary" @click="chooseGoods">添加</el-button>
            <el-button type="warn" @click="removeGoods">移除</el-button>
          </div>
          <el-table :data="selectGoodsList">
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
            <el-table-column label="出库数量" width="130" align="center" prop="goodsNumber">
              <template slot-scope="scope">
                <el-input-number :min="0" :max="scope.row.stockNumber" :precision="2" style="width: 120px" size="small" v-model="scope.row.goodsNumber" @change="goodsNumberChange(scope.row)"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="剩余库存" align="center" prop="stockLeftNumber" />
            <el-table-column>
              <template slot-scope="scope">
                <el-button @click="removeGoods(scope.row)" type="text">删除</el-button>
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
import { listStore } from "@/api/warehouse/store";
import { listWarehouse } from "@/api/warehouse/warehouse";
import { listStockGoods } from "@/api/warehouse/goods";
import { categoryTreeSelect } from "@/api/warehouse/goodscategory";
import { addOutreceipt, updateOutreceipt, getOutreceipt } from "@/api/warehouse/outreceipt";
import { outReceiptCode } from "@/api/warehouse/warehousecode";
import { calculateDate } from "@/utils/index";
export default {
  name: "OutreceiptEdit",
  dicts: ["goods_specification", "goods_color", "goods_unit", "outreceipt_type", "sys_yes_no"],
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
      storeList: [],
      warehouseList: [],
      goodsList: [],
      //入库单物料明细
      receiptGoodsList: [],
      selectGoodsList: [],
      selectionList: [],
      // 表单校验
      rules: {
        warehouseId: [{ required: true, message: "仓库不能为空", trigger: "blur" }],
        outWarehouseType: [{ required: true, message: "出库类别不能为空", trigger: "blur" }],
        receiveId: [{ required: true, message: "接收门店不能为空", trigger: "blur" }],
      },
      title: undefined,
      loading: false,
      open: undefined,
      categoryName: undefined,
      categoryOptions: undefined,
      oldWarehouseId: undefined,
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
    const receiptId = this.$route.params && this.$route.params.outreceiptId;
    if (receiptId != "0") {
      getOutreceipt(receiptId).then((res) => {
        this.form = res.data;
        this.receiptGoodsList = res.data.receiptGoodsList;
      });
    } else {
      outReceiptCode().then((res) => {
        this.$set(this.form, "documentNumber", res.msg);
      });
    }
  },
  watch: {
    "form.warehouseId": {
      handler: function (newval, oldval) {
        this.oldWarehouseId = oldval;
      },
    },
    "form.receiveId": {
      handler: function (newval, oldval) {
        let temp = this.storeList.filter((t) => t.storeId == newval);
        this.form.receiveName = temp[0].storeName;
      },
    },
  },
  methods: {
    init() {
      listStore().then((res) => {
        this.storeList = res.rows;
      });
      listWarehouse({ warehouseType: 1 }).then((res) => {
        this.warehouseList = res.rows;
      });
      categoryTreeSelect().then((res) => {
        this.categoryOptions = res.data;
      });
    },
    handleDelete(row) {
      let index = this.receiptGoodsList.findIndex((t) => t.goodsId == row.goodsId && t.goodsPrice == row.goodsPrice && t.goodsDeadlineTime == row.goodsDeadlineTime);
      this.receiptGoodsList.splice(index, 1);
    },
    /**
     * 计算物品数量
     */
    goodsNumberChange(row) {
      row.stockLeftNumber = row.stockNumber - row.goodsNumber;
    },
    selectableHandler(row, index) {
      return row.stockNumber > 0;
    },
    showDeadLineTime(row) {
      if (new Date(row.goodsDeadlineTime).getTime() > new Date("2099-01-01").getTime()) {
        return "长期有效";
      } else {
        return this.parseTime(row.goodsDeadlineTime, "{y}-{m}-{d}");
      }
    },
    /**
     * 切换仓库
     */
    warehouseChange(val) {
      if (this.receiptGoodsList.length == 0) {
        return;
      }
      this.$modal
        .confirm("切换仓库将清空已选物品，是否继续？")
        .then((t) => {
          this.receiptGoodsList = [];
        })
        .catch((err) => {
          this.form.warehouseId = this.oldWarehouseId;
        });
    },
    /**
     * 计算有效期
     */
    formatterDeadLine(row) {
      let dataStr = "长期";
      if (new Date(row.goodsDeadlineTime).getTime() > new Date("2099-01-01").getTime()) {
        return dataStr;
      } else {
        return this.parseTime(row.goodsDeadlineTime, "{y}-{m}-{d}");
      }
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
          if (this.form.warehouseOutReceiptId != null) {
            updateOutreceipt(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.close();
            });
          } else {
            addOutreceipt(this.form).then((response) => {
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
      let _res = this.receiptGoodsList.filter((t) => t.goodsNumber <= 0);
      if (_res.length > 0) {
        this.$message({
          type: "error",
          message: "物品数量错误",
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
      if (!this.form.warehouseId) {
        this.$message({
          type: "error",
          message: "请选择出库仓库",
        });
        return;
      }
      this.open = true;
      this.goodsList = [];
      this.title = `选择物品`;
    },
    /**
     * 选择物品
     */
    chooseGoods(row) {
      if (!!row.goodsId) {
        if (row.stockNumber <= 0) {
          return;
        }
        row.goodsNumber = 1;
        this.$set(row, "stockLeftNumber", row.stockNumber - row.goodsNumber);
        let _temp = this.selectGoodsList.filter((t) => t.goodsId == row.goodsId && t.goodsPrice == row.goodsPrice && t.goodsDeadlineTime == row.goodsDeadlineTime);
        if (!!_temp && _temp.length > 0) {
          return;
        }
        this.selectGoodsList.push(row);
      } else {
        this.selectionList.forEach((t) => {
          let _temp = this.selectGoodsList.filter((tt) => t.goodsId == tt.goodsId && t.goodsPrice == tt.goodsPrice && t.goodsDeadlineTime == row.goodsDeadlineTime);
          if (!!_temp && _temp.length > 0) {
            return;
          }
          t.goodsNumber = 1;
          this.$set(t, "stockLeftNumber", t.stockNumber - t.goodsNumber);
          this.selectGoodsList.push(t);
        });
      }
    },
    confirmChoose() {
      this.selectGoodsList = this.selectGoodsList.filter((t) => t.goodsNumber > 0);
      for (let i = 0; i < this.selectGoodsList.length; i++) {
        const element = this.selectGoodsList[i];
        let _temp = this.receiptGoodsList.filter((t) => t.goodsId == element.goodsId && t.goodsPrice == element.goodsPrice && t.goodsDeadlineTime == element.goodsDeadlineTime);
        element.goodsAmount = (element.goodsNumber * element.goodsPrice).toFixed(2);
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
      debugger;
      if (!!row.goodsId) {
        let index = this.selectGoodsList.findIndex((t) => t.goodsId == row.goodsId && t.goodsPrice == row.goodsPrice && t.goodsDeadlineTime == row.goodsDeadlineTime);
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
      this.queryParams.warehouseId = this.form.warehouseId;
      listStockGoods(this.queryParams).then((response) => {
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
      const obj = { path: "/out/outreceipt" };
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
