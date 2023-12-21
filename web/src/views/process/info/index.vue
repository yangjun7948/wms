<template>
  <div class="app-container">
    <component v-bind:is="currentTabComponent"></component>
  </div>
</template>

<script>
import procxz01cslcdetail from "@/components/business/process/procxz01-cslc-detail";

export default {
  name: "index",
  components: {
    procxz01cslcdetail,
  },
  data() {
    return {
      currentTabComponent: "",
    };
  },
  created() {
    if (this.$route.query.procNum) {
      let procNum = this.$route.query.procNum.replaceAll("-", "");
      this.currentTabComponent = procNum;
    }
  },
};
</script>

<style scoped></style>
