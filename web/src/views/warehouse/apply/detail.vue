<template>
  <div class="app-container" style="height: 100%">
    <el-card>
      <div slot="header" class="clearfix">
        <span>要货单明细</span>
        <el-button style="float: right" type="warning" @click="close()">返回</el-button>
      </div>
      <el-form ref="applyForm" :model="applyForm" :rules="rules" label-width="100px">
        <el-form-item label="要货单号" prop="documentNumber">
          <el-input v-model="applyForm.documentNumber" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="申请人" prop="applyUserName">
          <el-input v-model="applyForm.applyUserName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="要货仓库" prop="applyWarehouseName">
          <el-input v-model="applyForm.applyWarehouseName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="要货时间" prop="applyTime">
          <el-input v-model="applyForm.applyTime" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="applyForm.remark" type="textarea" placeholder="" disabled />
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>要货物品明细</span>
      </div>
      <el-table :data="applyGoodsList">
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
        <el-table-column label="重点物资" align="center" prop="snValidate">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
          </template>
        </el-table-column>
        <el-table-column label="数量" align="center" prop="goodsNumber"> </el-table-column>
      </el-table>
    </el-card>
    <el-card style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>出库物品明细</span>
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
      <el-table v-loading="loading" :data="goodsList" @row-dblclick="chooseGoods" row-class-name="not-select">
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
      <el-table :data="selectGoodsList" @selection-change="handleGoodsSelectionChange" @row-dblclick="removeGoods">
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
      </el-table>

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
import { getApply, updateApply } from "@/api/warehouse/apply";
export default {
  name: "ApplyEdit",
  dicts: ["goods_specification", "goods_color", "goods_unit", "outreceipt_type", "sys_yes_no"],
  components: {},
  data() {
    return {
      //要货单
      applyForm: {},
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
      //要货物品明细
      applyGoodsList: [],
      //物料明细
      receiptGoodsList: [],
      selectGoodsList: [],
      selectionList: [],
      // 表单校验
      rules: {
        outWarehouseId: [{ required: true, message: "出库仓库不能为空", trigger: "blur" }],
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
    const receiptId = this.$route.params && this.$route.params.applyId;
    if (receiptId != "0") {
      getApply(receiptId).then((res) => {
        this.applyForm = res.data;
        this.applyGoodsList = res.data.receiptGoodsList;
      });
    }
  },
  watch: {
    "applyForm.outWarehouseId": {
      handler: function (newval, oldval) {
        this.getStockGoods();
        this.oldWarehouseId = oldval;
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
          this.applyForm.outWarehouseId = this.oldWarehouseId;
        });
    },
    /**
     * 提交
     */
    submitForm(type) {
      this.$refs["applyForm"].validate((valid) => {
        if (valid) {
          this.$modal
            .confirm("是否确认提交？提交之后将不可修改！")
            .then((t) => {
              this.applyForm.state = type;
              this.applyForm.receiptGoodsList = this.receiptGoodsList;
              updateApply(this.applyForm).then((response) => {
                this.$modal.msgSuccess("生成出库单成功！请进行后续出库操作！");
                this.close();
              });
            })
            .catch();
        }
      });
    },
    //查询物品库存列表
    getStockGoods() {
      this.receiptGoodsList.forEach((t) => {
        listStockGoods({ goodsId: t.goodsId, warehouseId: this.applyForm.outWarehouseId }).then((res) => {
          let count = 0;
          res.rows.forEach((tt) => {
            count += tt.stockNumber;
          });
          this.$set(t, "stockNumber", count);
        });
      });
    },
    /**
     * 添加
     * @param { } row
     */
    handleAdd(row) {
      if (!this.applyForm.outWarehouseId) {
        this.$message({
          type: "error",
          message: "请选择出库仓库",
        });
        return;
      }
      this.queryParams.goodsId = row.goodsId;
      this.queryParams.warehouseId = this.applyForm.outWarehouseId;
      this.getGoodsList();
      this.open = true;
    },
    selectableHandler(row, index) {
      return row.stockNumber > 0;
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
    /** 查询物品列表 */
    getGoodsList() {
      this.loading = true;
      listStockGoods(this.queryParams).then((response) => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    removeGoods(row) {
      if (!!row.goodsId) {
        let index = this.selectGoodsList.findIndex((t) => (t.goodsId = row.goodsId && t.goodsPrice == row.goodsPrice && t.goodsDeadlineTime == row.goodsDeadlineTime));
        this.selectGoodsList.splice(index, 1);
      } else {
        this.selectGoodsList.pop();
      }
    },
    // 弹窗事件
    handleGoodsSelectionChange(selection) {
      this.selectionList = selection;
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
    /** 关闭按钮 */
    close() {
      const obj = { path: "/apply/apply" };
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
.diver-line {
  width: 2px;
  margin-left: 2px;
  margin-right: 2px;
}
</style>
