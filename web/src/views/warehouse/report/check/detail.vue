<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>单据明细</span>
        <el-button style="float: right; margin-left: 10px" type="plain" @click="close">返回</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单据编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入单据编号" disabled />
        </el-form-item>
        <el-form-item label="盘点仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" filterable placeholder="请选择" disabled>
            <el-option v-for="item in warehouseList" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId"> </el-option>
          </el-select>
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
        <el-table-column label="账面库存" align="center" prop="goodsOldNumber" width="140"> </el-table-column>
        <el-table-column label="实际库存" align="center" prop="goodsNewNumber" width="140"> </el-table-column>
        <el-table-column label="说明" align="center" prop="remark"> </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { listWarehouse } from "@/api/warehouse/warehouse";
import { getCheckReceipt } from "@/api/warehouse/check";
export default {
  name: "CheckView",
  components: {},
  dicts: ["goods_specification", "goods_color", "goods_unit", "outreceipt_type", "sys_yes_no"],
  data() {
    return {
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
      // 表单校验
      rules: {
        warehouseId: [{ required: true, message: "仓库不能为空", trigger: "blur" }],
        documentNumber: [{ required: true, message: "单据编号不能为空", trigger: "blur" }],
      },
      warehouseList: [],
      goodsList: [],
      //入库单物料明细
      receiptGoodsList: [],
      selectionList: [],
      selectGoodsList: [],
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
    const receiptId = this.$route.params && this.$route.params.receiptId;
    if (receiptId != "0") {
      getCheckReceipt(receiptId).then((res) => {
        this.form = res.data;
        this.receiptGoodsList = res.data.receiptGoodsList;
      });
    }
  },
  methods: {
    init() {
      listWarehouse({ warehouseType: 1 }).then((res) => {
        this.warehouseList = res.rows;
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/report/check" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
<style scoped></style>
