<template>
  <div class="processTree">
    <el-input placeholder="输入关键字进行过滤" v-model="filterText"> </el-input>
    <div class="tree-scroll">
      <el-tree class="filter-tree" :default-expand-all="true" node-key="id" accordion :filter-node-method="filterNode" @node-click="nodeClick" ref="processTree" :highlight-current="true" :props="defaultProps" :data="treeData"> </el-tree>
    </div>
  </div>
</template>

<script>
import { processTree } from "@/api/process/ehrProcess";

export default {
  name: "process-tree",
  data() {
    return {
      treeData: [],
      filterText: "",
      defaultProps: {
        children: "processDtoList",
        label: function (data, node) {
          return !!data.procName ? data.procName : data.procTypeName;
        },
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.processTree.filter(val);
    },
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      let _this = this;
      processTree({
        page: 1,
        row: 9999,
      }).then((res) => {
        _this.treeData = res.data;
      });
    },
    nodeClick(data) {
      this.$emit("nodeClick", data);
    },
    filterNode(value, data) {
      if (!value) return true;
      if (data.procName) {
        return data.procName.indexOf(value) !== -1;
      }
    },
  },
};
</script>

<style scoped>
.processTree {
  height: 100%;
  overflow-x: auto;
  width: 100%;
}

.filter-tree {
  height: 100%;
}
.tree-scroll {
  height: calc(100% - 40px);
}
</style>
