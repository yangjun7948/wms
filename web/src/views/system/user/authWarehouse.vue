<template>
  <div class="app-container">
    <h4 class="form-header h4">基本信息</h4>
    <el-form ref="form" :model="form" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="form.nickName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="登录账号" prop="userName">
            <el-input v-model="form.userName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <h4 class="form-header h4">仓库信息</h4>
    <el-table v-loading="loading" :row-key="getRowKey" @row-click="clickRow" ref="table" @selection-change="handleSelectionChange" :data="warehouseList.slice((pageNum - 1) * pageSize, pageNum * pageSize)">
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column type="selection" :reserve-selection="true" width="55"></el-table-column>
      <el-table-column label="仓库编号" align="center" prop="warehouseId" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="仓库类型" align="center" prop="warehouseType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.warehouse_type" :value="scope.row.warehouseType" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />

    <el-form label-width="100px">
      <el-form-item style="text-align: center; margin-left: -120px; margin-top: 30px">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { getAuthWarehouse, updateAuthWarehouse } from "@/api/system/user";
export default {
  name: "AuthWarehouse",
  dicts: ["warehouse_type"],
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 分页信息
      total: 0,
      pageNum: 1,
      pageSize: 10,
      form: {},
      warehouseList: [],
      warehouseIds: [],
    };
  },
  computed: {},
  mounted() {},
  created() {
    const userId = this.$route.params && this.$route.params.userId;
    if (userId) {
      this.loading = true;
      getAuthWarehouse(userId).then((response) => {
        let _this = this;
        this.form = response.user;
        this.warehouseList = response.warehouseList;
        this.warehouseIds = response.userWarehouseIds;
        this.total = this.warehouseList.length;
        this.$nextTick(() => {
          this.warehouseList.forEach((row) => {
            if (response.userWarehouseIds.includes(row.warehouseId)) {
              this.$refs.table.toggleRowSelection(row);
            }
          });
        });
        this.loading = false;
      });
    }
  },
  methods: {
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.warehouseIds = selection.map((item) => item.warehouseId);
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.warehouseId;
    },
    /** 提交按钮 */
    submitForm() {
      const userId = this.form.userId;
      const warehouseIds = this.warehouseIds.join(",");
      updateAuthWarehouse({ userId: userId, warehouseIds: warehouseIds }).then((response) => {
        this.$modal.msgSuccess("授权成功");
        this.close();
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/system/user" };
      this.$tab.closeOpenPage(obj);
    },
  },
};
</script>
<style scoped></style>
