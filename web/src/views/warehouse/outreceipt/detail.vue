<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>单据明细</span>
        <dict-tag style="display: inline; margin-left: 5px" :options="dict.type.receipt_state" :value="this.form.state" />
        <el-button style="float: right; margin-left: 10px" type="plain" @click="close">返回</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-printer" type="primary" @click="printOutReceipt">打印出库单</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-s-check" type="primary" v-if="form.state == 3" v-hasPermi="['warehouse:inreceipt:approve']" @click="approveOutReceipt">审核</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="出库类别" prop="outWarehouseType">
          <el-radio-group v-model="form.outWarehouseType">
            <el-radio-button v-if="form.outWarehouseType == dict.value" v-for="dict in dict.type.outreceipt_type" :key="dict.value" :label="dict.value" :value="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出库仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.outWarehouseType == 99 || form.outWarehouseType == 100" label="接收仓库" prop="receiveId">
          <el-select v-model="form.receiveId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-else label="接收门店" prop="receiveId">
          <el-select v-model="form.receiveId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in storeList" :key="item.storeId" :label="item.storeName" :value="item.storeId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出库时间" prop="receiveId">
          <el-input v-model="form.outWarehouseTime" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" disabled type="textarea" placeholder="" />
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
        <el-table-column label="有效期" align="center" prop="goodsDeadlineTime">
          <template slot-scope="scope">
            <span>{{ showDeadLineTime(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="重点物资" align="center" prop="snValidate">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.snValidate" />
          </template>
        </el-table-column>
        <el-table-column label="单价" align="center" prop="goodsPrice" width="140"> </el-table-column>
        <el-table-column label="数量" align="center" prop="goodsNumber" width="140"> </el-table-column>
        <el-table-column label="金额" align="center" prop="goodsAmount"> </el-table-column>
        <el-table-column label="溯源码" align="center" prop="snCode"></el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="查看溯源码" :visible.sync="open">
      <div class="flex flex-wrap">
        <img v-for="(item, index) in snCodeList" :key="index" :src="item" alt="" />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { listStore } from "@/api/warehouse/store";
import { listWarehouse } from "@/api/warehouse/warehouse";
// import { snPreview } from "@/api/warehouse/goods";
import { getOutreceipt, outReceiptPdf, approveOutreceipt } from "@/api/warehouse/outreceipt";
import { saveAs } from "file-saver";
export default {
  name: "OutreceiptView",
  dicts: ["goods_specification", "goods_color", "goods_unit", "outreceipt_type", "sys_yes_no", "receipt_state"],
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
      snCodeList: [],
      // 非单个禁用
      single: true,
      goodsIds: [],
      // 表单校验
      rules: {
        warehouseId: [{ required: true, message: "仓库不能为空", trigger: "blur" }],
        inWarehouseType: [{ required: true, message: "入库类别不能为空", trigger: "blur" }],
        documentNumber: [{ required: true, message: "单据编号不能为空", trigger: "blur" }],
      },
      title: undefined,
      loading: false,
      open: undefined,
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
    }
  },
  methods: {
    init() {
      listStore().then((res) => {
        this.storeList = res.rows;
      });
      listWarehouse().then((res) => {
        this.warehouseList = res.rows;
      });
    },
    showDeadLineTime(row) {
      if (new Date(row.goodsDeadlineTime).getTime() > new Date("2099-01-01").getTime()) {
        return "长期有效";
      } else {
        return this.parseTime(row.goodsDeadlineTime, "{y}-{m}-{d}");
      }
    },

    /** 关闭按钮 */
    close() {
      const obj = { path: "/out/outreceipt" };
      this.$tab.closeOpenPage(obj);
    },
    printOutReceipt() {
      outReceiptPdf(this.form.warehouseOutReceiptId).then((res) => {
        const blob = new Blob([res], { type: "application/pdf" });
        saveAs(blob, `出库单${this.form.documentNumber}.pdf`);
        let url = URL.createObjectURL(blob);
        window.open(url, "_blank", "fullscreen=yes");
      });
    },
    //审核
    approveOutReceipt() {
      this.$modal
        .confirm("是否确认提交审核？提交之后将不可撤回！")
        .then((t) => {
          approveOutreceipt(this.form).then((res) => {
            this.$modal.msgSuccess("审核成功");
            this.close();
          });
        })
        .catch();
    },
    //判断是否可选
    selectableHandler(row, index) {
      return row.snValidate == "Y";
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
