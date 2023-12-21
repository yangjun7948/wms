<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>单据明细</span>
        <dict-tag style="display: inline; margin-left: 5px" :options="dict.type.receipt_state" :value="this.form.state" />
        <el-button style="float: right; margin-left: 10px" type="plain" @click="close">返回</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-printer" type="primary" @click="printInReceipt">打印入库单</el-button>
        <el-button style="float: right; margin-left: 10px" icon="el-icon-s-check" type="primary" v-if="form.state == 3" v-hasPermi="['warehouse:inreceipt:approve']" @click="approveInReceipt">审核</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="入库类别" prop="inWarehouseType">
          <el-radio-group v-model="form.inWarehouseType">
            <el-radio-button v-if="form.inWarehouseType == dict.value" v-for="dict in dict.type.inreceipt_type" :key="dict.value" :label="dict.value" :value="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入库仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入库时间" prop="inWarehouseTime">
          <el-input v-model="form.inWarehouseTime" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in supplierList" :key="item.supplierId" :label="item.supplierName" :value="item.supplierId"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单号" prop="billNo">
          <el-input v-model="form.billNo" placeholder="" disabled />
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
        <el-button style="float: right; margin-left: 10px" type="primary" :disabled="single" icon="el-icon-printer" @click="printSnCodeList">批量打印溯源码</el-button>
      </div>
      <el-table :data="receiptGoodsList" @selection-change="handleSelectionChange">
        <el-table-column :selectable="selectableHandler" type="selection" width="55" align="center" />
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
        <el-table-column label="单价" align="center" prop="goodsPrice" width="140"> </el-table-column>
        <el-table-column label="数量" align="center" prop="goodsNumber" width="140"> </el-table-column>
        <el-table-column label="金额" align="center" prop="goodsAmount"> </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="scope.row.snValidate == 'Y'" size="mini" type="text" icon="el-icon-view" @click="previewSnCode(scope.row)">查看溯源码</el-button>
            <el-button v-if="scope.row.snValidate == 'Y'" size="mini" type="text" icon="el-icon-delete" @click="printSnCode(scope.row)">打印</el-button>
          </template>
        </el-table-column>
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
import { listSupplier } from "@/api/warehouse/supplier";
import { listWarehouse } from "@/api/warehouse/warehouse";
import { listGoods, snPreview, snCodePdf } from "@/api/warehouse/goods";
import { categoryTreeSelect } from "@/api/warehouse/goodscategory";
import { addInreceipt, updateInreceipt, getInreceipt, inReceiptPdf, approveInreceipt } from "@/api/warehouse/inreceipt";
import { inReceiptCode } from "@/api/warehouse/warehousecode";
import { calculateDate } from "@/utils/index";
import JSZip from "jszip";
import { saveAs } from "file-saver";
export default {
  name: "InreceiptView",
  dicts: ["goods_specification", "goods_color", "goods_unit", "inreceipt_type", "sys_yes_no", "receipt_state"],
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
    const receiptId = this.$route.params && this.$route.params.inreceiptId;
    if (receiptId != "0") {
      getInreceipt(receiptId).then((res) => {
        this.form = res.data;
        this.receiptGoodsList = res.data.receiptGoodsList;
      });
    }
  },
  methods: {
    init() {
      listSupplier().then((res) => {
        this.supplierList = res.rows;
      });
      listWarehouse().then((res) => {
        this.warehouseList = res.rows;
      });
    },
    handleSelectionChange(selection) {
      this.goodsIds = selection.map((item) => item.goodsId);
      this.single = selection.length == 0;
    },

    /**
     * 计算有效期
     */
    calcDeadLine(row) {
      let dataStr = "长期";
      if (!!row.expirationDate && !!row.expirationDateType) {
        if (row.expirationDateType == 1) {
          let data = calculateDate(this.form.inWarehouseTime, row.expirationDate, 0, 0);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else if (row.expirationDateType == 2) {
          let data = calculateDate(this.form.inWarehouseTime, 0, row.expirationDate, 0);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else if (row.expirationDateType == 3) {
          let data = calculateDate(this.form.inWarehouseTime, 0, 0, row.expirationDate);
          return this.parseTime(data, "{y}-{m}-{d}");
        } else {
          return dataStr;
        }
      }
      return dataStr;
    },
    /**预览SN */
    previewSnCode(row) {
      this.open = true;
      this.snCodeList = [];
      snPreview(this.form.warehouseInReceiptId, row.goodsId)
        .then((res) => {
          return JSZip.loadAsync(res).then();
        })
        .then((zip) => {
          zip.forEach((relativePath, file) => {
            if (!file.dir) {
              // file.async("uint8array") 用于读取文件内容
              file.async("uint8array").then((content) => {
                // 创建一个 <img> 元素并显示图片
                this.snCodeList.push(URL.createObjectURL(new Blob([content])));
              });
            }
          });
        });
    },
    /**打印溯源码 */
    printSnCode(row) {
      snCodePdf(this.form.warehouseInReceiptId, row.goodsId).then((res) => {
        const blob = new Blob([res]);
        saveAs(blob, `${row.goodsName}溯源码.pdf`);
      });
    },
    //批量打印
    printSnCodeList() {
      for (let i = 0; i < this.goodsIds.length; i++) {
        const goodsId = this.goodsIds[i];
        const goods = this.receiptGoodsList.filter((t) => t.goodsId == goodsId).length > 0 ? this.receiptGoodsList.filter((t) => t.goodsId == goodsId)[0] : undefined;

        snCodePdf(this.form.warehouseInReceiptId, goodsId).then((res) => {
          const blob = new Blob([res]);
          saveAs(blob, `${goods.goodsName}溯源码.pdf`);
        });
      }
    },
    printInReceipt() {
      inReceiptPdf(this.form.warehouseInReceiptId).then((res) => {
        const blob = new Blob([res], { type: "application/pdf" });
        saveAs(blob, `入库单${this.form.documentNumber}.pdf`);
        let url = URL.createObjectURL(blob);
        window.open(url, "_blank", "fullscreen=yes");
      });
    },
    //审核
    approveInReceipt() {
      this.$modal
        .confirm("是否确认提交审核？提交之后将不可撤回！")
        .then((t) => {
          approveInreceipt(this.form).then((res) => {
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
