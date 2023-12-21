<template>
  <div class="app-container">
    <el-descriptions class="goods-desc" title="物品信息" :column="5" border>
      <template slot="extra">
        <el-button type="primary" @click="close" size="small">返回</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label"> 物品名称 </template>
        {{ goodsInfo.goodsName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"> 溯源码 </template>
        <span class="code-style">{{ goodsInfo.snCode }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"> 规格型号 </template>
        <dict-tag :options="dict.type.goods_specification" :value="goodsInfo.specificationType" />
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">计量单位 </template>
        <dict-tag :options="dict.type.goods_unit" :value="goodsInfo.unitType" />
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label"> 当前状态 </template>
        <dict-tag :options="dict.type.trace_state" :value="goodsInfo.traceState" />
      </el-descriptions-item>
    </el-descriptions>
    <div class="time-title">流转记录</div>
    <el-timeline>
      <el-timeline-item :key="item.traceRecordId" v-for="item in traceList" :timestamp="item.recordTime" placement="top">
        <el-card>
          <div v-if="item.recordType == '1'" style="display: flex; align-items: center">
            <el-tag>入库</el-tag>
            <span style="margin-left: 5px">入库单号：</span>
            <el-button @click="goInReceipt(item)" type="text">{{ item.documentNumber }}</el-button>
            <span style="margin-left: 5px">类型：</span>
            <el-tag><dict-tag :options="dict.type.inreceipt_type" :value="item.warehouseType" /></el-tag>
            <span style="margin-left: 5px">入库仓库：</span>
            <el-tag>{{ item.warehouseName }}</el-tag>
          </div>
          <div v-else-if="item.recordType == '2'" style="display: flex; align-items: center">
            <el-tag type="success">出库</el-tag>
            <span style="margin-left: 5px">出库单号：</span>
            <el-button @click="goInReceipt(item)" type="text">{{ item.documentNumber }}</el-button>
            <span style="margin-left: 5px">类型：</span>
            <el-tag><dict-tag :options="dict.type.outreceipt_type" :value="item.warehouseType" /></el-tag>
            <span style="margin-left: 5px">出仓仓库：</span>
            <el-tag>{{ item.warehouseName }}</el-tag>
          </div>
          <p>{{ item.createBy }} 提交于 {{ item.recordTime }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { goodsCodeDetail } from "@/api/warehouse/report";
export default {
  name: "",
  dicts: ["goods_specification", "goods_color", "goods_unit", "code_state", "trace_state", "inreceipt_type", "outreceipt_type"],
  components: {},
  data() {
    return {
      goodsInfo: {},
      traceList: [],
    };
  },
  computed: {
    ...mapGetters([]),
  },
  mounted() {},
  created() {
    const traceId = this.$route.params && this.$route.params.traceId;
    goodsCodeDetail(traceId).then((res) => {
      this.goodsInfo = res.data;
      this.traceList = res.data.traceRelDtoList;
    });
  },
  methods: {
    goInReceipt(item) {
      if (item.recordType == "1") {
        this.$router.push({
          path: `/in/inreceipt-view/view/${item.relId}`,
        });
      } else {
        this.$router.push({
          path: `/out/outreceipt-view/view/${item.relId}`,
        });
      }
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/report/trace" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
<style scoped>
.goods-desc {
  margin-left: 40px;
  margin-bottom: 20px;
}
.time-title {
  font-size: 16px;
  font-weight: 700;
  margin-left: 40px;
  margin-bottom: 20px;
}
.code-style {
  background: blue;
  color: white;
  padding: 5px;
  border-radius: 5px;
}
</style>
