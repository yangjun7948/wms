<template>
  <div>
    <div>
      <table data-page-size="15" style="width: 100%; margin-top: 2%; text-align: center">
        <thead>
          <tr>
            <th class="text-center" style="width: 120px">序号</th>
            <th class="text-center" style="width: 150px">节点</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody v-for="(item, index) in processStatus" v-bind:key="index">
          <tr style="background-color: #def3ff">
            <td class="text-center">{{ index + 1 }}</td>
            <td class="text-center">{{ item.nodeName }}</td>
            <td class="text-center"></td>
            <td class="text-center"></td>
            <td class="text-center"></td>
          </tr>
          <tr>
            <th class="text-center">操作人</th>
            <th class="text-center">操作状态</th>
            <th class="text-center">接收时间</th>
            <th class="text-center">操作时间</th>
            <th class="text-center">操作耗时</th>
          </tr>
          <tr v-for="(item1, index) in item.processStatusDtoList" v-bind:key="index">
            <td class="text-center">{{ item1.operateUserName }}</td>
            <td class="text-center">{{ item1.operateStatus }}</td>
            <td class="text-center">{{ item1.startTime }}</td>
            <td class="text-center">{{ item1.endTime }}</td>
            <td class="text-center">{{ item1.duration }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    processStatus: Array,
  },
  data() {
    return {
      processStatusList: [],
    };
  },
};
</script>
<style>
.text-center {
  border-bottom: 1px solid #e7eaec;
  line-height: 1.42857;
  padding: 8px;
  vertical-align: top;
}
</style>
