<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="mb20 outside-shadow-10" shadow="never">
          <div slot="header">
            <span>库存预警</span>
          </div>
          <div>
            <el-table :data="stockwarn.warnGoodsList">
              <el-table-column label="物料" prop="goodsName"></el-table-column>
              <el-table-column label="仓库" prop="warehouseName"></el-table-column>
              <el-table-column label="当前库存" prop="stockNumber">
                <template slot-scope="scope">
                  <span style="color: red">{{ scope.row.stockNumber }}</span>
                  <i v-if="scope.row.stockNumber > scope.row.maxStock" class="el-icon-top" style="color: red"></i>
                  <i v-else class="el-icon-bottom" style="color: red"></i>
                </template>
              </el-table-column>
              <el-table-column label="最小库存" prop="minStock"></el-table-column>
              <el-table-column label="最大库存" prop="maxStock"></el-table-column>
            </el-table>
            <pagination v-show="stockwarn.total > 0" :total="stockwarn.total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getStockWarnList" />
          </div>
        </el-card>
        <el-card class="mb20 outside-shadow-10" shadow="never">
          <div slot="header">
            <span>过保提示</span>
          </div>
          <div>
            <el-table :data="overGoodsList">
              <el-table-column label="物料" prop="goodsName"></el-table-column>
              <el-table-column label="门店" prop="storeName"></el-table-column>
              <el-table-column label="安装日期" prop="fixTime"></el-table-column>
              <el-table-column label="质保期" prop="grantTime"></el-table-column>
              <el-table-column label="过保日期" prop="overGrantDate"></el-table-column>
            </el-table>
          </div>
        </el-card>
        <el-card class="mb20 outside-shadow-10" shadow="never">
          <div slot="header">
            <span>超期配件</span>
          </div>
          <div>
            <el-table :data="expireGoodsList">
              <el-table-column label="物料" prop="goodsName"></el-table-column>
              <el-table-column label="仓库" prop="warehouseName"></el-table-column>
              <el-table-column label="入库日期" prop="inTime"></el-table-column>
              <el-table-column label="有效期" prop="grantTime"></el-table-column>
              <el-table-column label="过期日期" prop="expireDate"></el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="mb20 outside-shadow-10" shadow="never">
          <div slot="header">
            <span>工作台快捷入口</span>
          </div>
          <div class="work-space">
            <div @click="go('inreceipt')" v-hasPermi="['warehouse:inreceipt:add']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-ruku"></use>
                </svg>
              </div>
              <span>物品入库</span>
            </div>
            <div @click="go('outreceipt')" v-hasPermi="['warehouse:outreceipt:add']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-chukudan"></use>
                </svg>
              </div>
              <span>物品出库</span>
            </div>
            <div @click="go('transfer')" v-hasPermi="['warehouse:transfer:add']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-churuku-s"></use>
                </svg>
              </div>
              <span>物品调拨</span>
            </div>
            <div @click="go('apply')" v-hasPermi="['warehouse:apply:list']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-caigouruku"></use>
                </svg>
              </div>
              <span>要货管理</span>
            </div>
            <div @click="go('warehousestock')" v-hasPermi="['warehouse:warehousestock:query']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-kucun"></use>
                </svg>
              </div>
              <span>库存查询</span>
            </div>
            <div @click="go('trace')" v-hasPermi="['warehouse:trace:query']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-huowu"></use>
                </svg>
              </div>
              <span>物品溯源</span>
            </div>
            <div @click="go('inreceiptApprove')" v-hasPermi="['warehouse:inreceipt:approve']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-tiaobodan"></use>
                </svg>
              </div>
              <span>单据审核</span>
            </div>
            <div @click="go('check')" v-hasPermi="['warehouse:report:check']" class="work-space-item" :style="'--theme:' + theme">
              <div :style="'--theme:' + theme" class="work-space-item-icon">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-tiaobodan"></use>
                </svg>
              </div>
              <span>库存盘点</span>
            </div>
          </div>
        </el-card>
        <el-card class="mb20 outside-shadow-10" shadow="never">
          <div slot="header">
            <span>待办中心</span>
          </div>
          <div>
            <el-radio-group v-model="receptType" size="small">
              <el-radio-button label="1">入库单</el-radio-button>
              <el-radio-button label="2">出库单</el-radio-button>
              <el-radio-button label="3">调拨单</el-radio-button>
              <el-radio-button label="4">要货单</el-radio-button>
            </el-radio-group>
            <ul class="todo-list">
              <li @click="goApprove(item)" v-for="item in receiptList" :key="item.receiptId">
                <span>{{ item.documentNumber }} {{ item.updateTime }} 待审核</span>
              </li>
            </ul>
          </div>
        </el-card></el-col
      >
    </el-row>
  </div>
</template>

<script>
import { stockwarnList } from "@/api/warehouse/stockwarn";
import { listTodoreceipt } from "@/api/warehouse/inreceipt";

export default {
  name: "Index",
  components: {},
  computed: {
    theme() {
      return this.$store.state.settings.theme;
    },
  },
  mounted() {
    this.init();
  },
  watch: {
    receptType: {
      handler: function (val) {
        this.getReceiptList();
      },
    },
  },
  data() {
    return {
      stockwarn: {
        total: 0,
        warnGoodsList: [],
      },
      receptType: "1",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      receiptList: [],
      overGoodsList: [
        { goodsName: "油烟机", storeName: "一号店", fixTime: "2022-09-26", grantTime: "1年", overGrantDate: "2023-09-26" },
        { goodsName: "电饭锅", storeName: "二号店", fixTime: "2022-09-26", grantTime: "1年", overGrantDate: "2023-09-26" },
      ],
      expireGoodsList: [
        { goodsName: "油墨", warehouseName: "一号仓库", inTime: "2022-09-26", grantTime: "1年", expireDate: "2023-09-26" },
        { goodsName: "电饭锅", warehouseName: "二号店", inTime: "2022-09-26", grantTime: "1年", expireDate: "2023-09-26" },
      ],
    };
  },
  methods: {
    init() {
      this.handlerStockWarn();
      this.getReceiptList();
    },
    handlerStockWarn() {
      this.queryParams.pageNum = 1;
      this.getStockWarnList();
    },
    getReceiptList() {
      this.receiptList = [];
      listTodoreceipt(this.receptType).then((res) => {
        this.receiptList = res.rows;
      });
    },
    getStockWarnList() {
      stockwarnList(this.queryParams).then((res) => {
        this.stockwarn.warnGoodsList = res.rows;
        this.stockwarn.total = res.total;
      });
    },
    go(type) {
      if (type == "inreceipt") {
        this.$router.push({
          path: "/in/inreceipt-edit/edit/0",
        });
      } else if (type == "outreceipt") {
        this.$router.push({
          path: "/out/outreceipt-edit/edit/0",
        });
      } else if (type == "transfer") {
        this.$router.push({
          path: "/transfer/transfer-edit/edit/0",
        });
      } else if (type == "apply") {
        this.$router.push({
          path: "/apply/apply",
        });
      } else if (type == "warehousestock") {
        this.$router.push({
          path: "/report/warehousestock",
        });
      } else if (type == "trace") {
        this.$router.push({
          path: "/report/trace",
        });
      } else if (type == "inreceipt") {
        this.$router.push({
          path: "/out/outreceipt-edit/edit/0",
        });
      } else if (type == "check") {
        this.$router.push({
          path: "/report/check",
        });
      }
    },
    goApprove(item) {
      if (this.receptType == "1") {
        const warehouseInReceiptId = item.warehouseInReceiptId;
        this.$router.push({
          path: `/in/inreceipt-view/view/${warehouseInReceiptId}`,
        });
      } else if (this.receptType == "2") {
        const warehouseoutReceiptId = item.warehouseOutReceiptId;
        this.$router.push({
          path: `/out/outreceipt-view/view/${warehouseoutReceiptId}`,
        });
      } else if (this.receptType == "3") {
        const warehouseTransferReceiptId = item.warehouseTransferReceiptId;
        this.$router.push({
          path: `/transfer/transfer-view/view/${warehouseTransferReceiptId}`,
        });
      } else if (this.receptType == "4") {
        const applyReceiptId = item.applyReceiptId;
        this.$router.push({
          path: `/apply/apply-view/view/${applyReceiptId}`,
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 15px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
::v-deep .el-card__header span {
  font-weight: bold;
}
.work-space {
  display: flex;
  justify-content: space-between;
  flex-flow: wrap;
}
.work-space-item {
  border-radius: 5px;
  margin-left: 10px;
  margin-bottom: 20px;
  padding-bottom: 10px;
  background-color: #f0f2f5;
  box-shadow: 10px 10px 10px rgba(123, 147, 172, 0.44), -10px -10px 10px #fff;
  width: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.5s ease, color 0.5s ease; /* 添加过渡效果 */
}
.work-space-item-icon {
  color: var(--theme);
  padding: 10px;
  border-radius: 10px;
  svg {
    font-size: 40px;
  }
}
.work-space-item span {
  // font-size: 10px;
  color: var(--theme);
}
.work-space-item-icon:hover {
  background-color: var(--theme);
  opacity: 0.8;
  color: white;
}
.todo-list {
  list-style-type: none;
  padding-left: 0px;

  li {
    border-bottom: 1px dashed;
    cursor: pointer;
    line-height: 30px;
    color: #606266;
    &:hover {
      color: #1890ff;
    }
  }
}
.outside-shadow-10 {
  box-shadow: 10px 10px 10px rgba(123, 147, 172, 0.44), -10px -10px 10px #fff;
  background-color: #f0f2f5;
  border-radius: 10px;
}
</style>
