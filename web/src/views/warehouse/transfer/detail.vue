<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>单据明细</span>
        <el-button style="float: right; margin-left: 10px" type="plain" @click="close">返回</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-printer" type="primary" @click="printTransferReceipt">打印调拨单</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-s-check" type="primary" v-if="form.state == 3" v-hasPermi="['warehouse:transfer:approve']" @click="approveTransferReceipt">审核</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="调出仓库" prop="transferOutWarehouseId">
          <el-select v-model="form.transferOutWarehouseId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in outWarehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调入仓库" prop="transferInWarehouseId">
          <el-select v-model="form.transferInWarehouseId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in inWarehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId">
              <div class="select-container">
                <span>{{ item.warehouseName }}</span>
                <span v-if="item.warehouseType == 1" class="company-item">公司</span>
                <span v-if="item.warehouseType == 2" class="person-item">个人</span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调拨类别" prop="transferType" disabled>
          <el-radio-group v-model="form.transferType">
            <el-radio-button v-for="dict in dict.type.transfer_type" :key="dict.value" :label="dict.value" :value="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="" disabled />
        </el-form-item>
      </el-form>
    </el-card>
    <el-divider></el-divider>
    <el-card>
      <div slot="header" class="clearfix">
        <span>物品明细</span>
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
        <el-table-column label="溯源码" align="center" prop="snCode"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { listWarehouse } from "@/api/warehouse/warehouse";
import { transferCode } from "@/api/warehouse/warehousecode";
import { getTransfer, approveTransferreceipt, transferReceiptPdf } from "@/api/warehouse/transfer";
export default {
  name: "TransferView",
  dicts: ["goods_specification", "goods_color", "goods_unit", "transfer_type", "sys_yes_no"],
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
      outWarehouseList: [],
      inWarehouseList: [],
      goodsList: [],
      //入库单物料明细
      receiptGoodsList: [],
      selectGoodsList: [],
      selectionList: [],
      snCodeList: [],
      // 非单个禁用
      single: true,
      goodsIds: [],
      // 表单校验
      rules: {
        transferOutWarehouseId: [{ required: true, message: "出仓仓库不能为空", trigger: "blur" }],
        transferInWarehouseId: [{ required: true, message: "入仓仓库不能为空", trigger: "blur" }],
        transferWarehouseType: [{ required: true, message: "调拨类别不能为空", trigger: "blur" }],
        documentNumber: [{ required: true, message: "单据编号不能为空", trigger: "blur" }],
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
    const transferId = this.$route.params && this.$route.params.transferId;
    if (transferId != "0") {
      getTransfer(transferId).then((res) => {
        this.form = res.data;
        this.receiptGoodsList = res.data.receiptGoodsList;
      });
    } else {
      transferCode().then((res) => {
        this.$set(this.form, "documentNumber", res.msg);
      });
    }
  },
  methods: {
    init() {
      listWarehouse().then((res) => {
        this.warehouseList = res.rows;
        this.outWarehouseList = res.rows.filter((t) => t.warehouseType == 1);
        this.inWarehouseList = res.rows;
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/transfer/transfer" };
      this.$tab.closeOpenPage(obj);
    },
    printTransferReceipt() {
      transferReceiptPdf(this.form.warehouseTransferReceiptId).then((res) => {
        const blob = new Blob([res], { type: "application/pdf" });
        saveAs(blob, `调拨单${this.form.documentNumber}.pdf`);
        let url = URL.createObjectURL(blob);
        window.open(url, "_blank", "fullscreen=yes");
      });
    },

    //审核
    approveTransferReceipt() {
      this.$modal
        .confirm("是否确认提交审核？提交之后将不可撤回！")
        .then((t) => {
          approveTransferreceipt(this.form).then((res) => {
            this.$modal.msgSuccess("审核成功");
            this.close();
          });
        })
        .catch();
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
.select-container {
  display: flex !important;
  align-items: center;
  justify-content: space-between;
}
.company-item {
  background: green;
  padding: 0px;
  color: white;
  float: right;
  border-radius: 5px;
  height: 20px;
  line-height: 20px;
}
.person-item {
  background: rgb(42, 2, 241);
  padding: 0px;
  color: white;
  float: right;
  border-radius: 5px;
  height: 20px;
  line-height: 20px;
}
</style>
