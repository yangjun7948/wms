<template>
  <div style="text-align: center">
    <img style="margin-top: 4%" :src="processImageUrl" />
  </div>
</template>
<script>
export default {
  props: {
    processImageUrl: String,
  },
  data() {
    return {
      processStatusList: [],
    };
  },
  created() {},
};
</script>
<style></style>
