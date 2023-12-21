<template>
  <div class="app-container">
    <div class="scroll-table">
      <div :key="item.procTypeName" v-for="item in processList">
        <span class="category-title">{{ item.procTypeName }}</span>
        <ul class="process-category">
          <li :key="info.procCode" v-for="info in item.processDtoList" class="process-item">
            <svg-icon icon-class="documentation" class="mr5" />
            <a @click="gotoCreate(info)">{{ info.procName }}</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { processList } from "@/api/process/ehrProcess";
import { mapGetters } from "vuex";

export default {
  name: "process-new",
  data() {
    return {
      processList: [],
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      let _this = this;
      processList({}).then((res) => {
        _this.processList = res.data;
      });
    },
    gotoCreate(info) {
      this.$router.push({
        path: "/flow/new-add/add",
        query: {
          procNum: info.procNum,
          procName: info.procName,
          procCode: info.procCode,
        },
      });
    },
  },
};
</script>

<style scoped>
.process-category {
  display: flex;
  flex-wrap: wrap;
}

.process-item {
  width: 25%;
  line-height: 40px;
  display: flex;
  align-items: center;
}

.process-item svg {
  color: #1296db;
  font-size: 22px;
}
.process-item a {
  text-decoration: none;
}
.category-title {
  font-weight: bold;
  font-size: 18px;
}
</style>
