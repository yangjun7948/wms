<template>
  <div class="app-container">
    <component v-bind:is="currentTabComponent"></component>
  </div>
</template>

<script>
import procxz01cslc from "@/components/business/process/procxz01-cslc";

export default {
  name: "precess-add",
  components: {
    procxz01cslc,
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
  methods: {},
};
</script>

<style scoped></style>
