<!-- 课程班级 -->
<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">库存查询</block>
		</cu-custom>
		<view class="zIndex bg-white">
			<view class="padding">
				<u-search v-model="keyword" @search="actionClick" @custom="actionClick" :show-action="true"
					placeholder="输入物品名或规格名进行搜索" action-text="搜索" @clear="clearClick"></u-search>
			</view>
			<view class="cu-form-group">
				<view class="title">仓库类型</view>
				<radio-group @change="applyTypeChange">
					<label class="margin-left-sm">
						<radio class="blue radio" :checked="applyType=='1'?true:false" value="1"></radio>
						<text class="margin-left-sm">公司总仓</text>
					</label>
					<label class="margin-left-sm">
						<radio class="blue radio" :checked="applyType=='2'?true:false" value="2"></radio>
						<text class="margin-left-sm">个人仓库</text>
					</label>
				</radio-group>
			</view>
			<view v-show="applyType==='1'" class="cu-form-group" @click="show =true">
				<view class="title">查询仓库</view>
				<view class="">
					<view v-if="!!warehouseName">{{warehouseName}}</view>
					<text v-else class="cuIcon-right text-gray text-lg"></text>
				</view>
			</view>
		</view>

		<u-picker :show="show" :columns="columns" keyName="warehouseName" :closeOnClickOverlay="true"
			@cancel="show=false" @confirm="confirm"></u-picker>

		<zb-table :columns="column" :stripe="true" :border="true" :data="goodsList"></zb-table>
		<uni-pagination :current="1" :total="total" :pageSize="pageSize" @change="change" />
	</view>
</template>

<script>
	import request from '@/utils/request.js';
	import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
	import {
		debounce
	} from '@/utils/util.js';
	import {
		baseUrl
	} from "@/utils/apiconfig.js";
import row from '../../../uni_modules/uview-ui/libs/config/props/row';
	export default {
		components: {
			uniPagination
		},
		data() {
			return {
				keyword: "",
				TabCur: 0,
				goodsList: [],
				show: false,
				warehouseId: "",
				warehouseName: "",
				column: [{
						name: 'goodsName',
						fixed: true,
						label: '物品名称',
						width: 80,
						align: "center"
					},
					{
						name: 'categoryName',
						label: '物品类别',
						align: "center"
					}
				],
				columns: [],
				pageNum: 1,
				pageSize: 10,
				total: 0,
				getGoodsList: null,
				applyType: "1",
			};
		},
		onLoad() {
			this.getWarehouse();
			let that = this
			this.getGoodsList = debounce(() => {
				that._getGoodsList();
			}, 1000, true);
		},
		computed: {

		},
		methods: {
			getWarehouse() {
				var that = this;
				let opts = {
					url: 'warehouse/app/warehouseList',
				};
				let params = {
					"warehouseType": "1"
				}
				request.httpTokenRequest(opts, params).then(res => {
					if (res.code == 200) {
						that.columns.push(res.rows);
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			resetList() {
				this.pageNum = 1,
					this.goodsList = [];
				this._getGoodsList();
			},
			_getGoodsList() {
				var that = this;
				if (that.applyType == "1" && that.warehouseId == "") {
					this.mes("请选择查询的仓库")
					return;
				}
				let arr = []
				if (!!that.warehouseId) arr.push(that.warehouseId);
				let params = {
					//showDeadLineTime:"", //是否区分有效期
					//	showPrice:"",	//是否区分单价
					warehouseIds: arr, //仓库id
					goodsName: that.keyword,
					pageNum: that.pageNum,
					pageSize: that.pageSize,
				}
				
				let opts = {
					url: 'warehouse/app/stock?pageNum=' + that.pageNum + '&pageSize=' + that
							.pageSize+ '&warehouseIds=' + arr+ '&goodsName=' + that.keyword,
					method:'get'
				};

				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						/**
						 * 处理数据
						 * 1.将时间格式化程年月日
						 * 2.将时间为2099年的修改成 "无期限"
						 */
						let rows=res.rows;
						rows.forEach(item=>{
							for (let key in item) {
								if(that.isNumber(key)){
									if('4' == key.substring(key.length-1,key.length)){
										let year = key.substring(0,4)
										if(year =='2099'){
											item[key]="无期限";
										}else{
											item[key]=!!item[key] ? item[key].substring(0,10):"";
										}
									}
								}
							}
						})
						that.goodsList =rows;
						
						
						that.total = res.total;
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})			}
				});
			},
			isNumber(val){
				var regPos=/^[0-9]+.?[0-9]*/;
				return regPos.test(val)
			},
			actionClick() {
				//触发查询
				this.resetList();
			},
			clearClick() {
				//触发查询
				this.resetList();
			},
			applyTypeChange(e) {
				this.applyType = e.detail.value;
				this.resetList();
			},
			change(e) {
				this.pageNum = e.current
				this._getGoodsList();
			},
			confirm(e) {
				this.warehouseName = e.value[0].warehouseName
				this.warehouseId = e.value[0].warehouseId
				//计算表头
				this.column = [{
							name: 'goodsName',
							fixed: true,
							label: '物品名称',
							width: 80,
							align: "center"
						},
						{
							name: 'categoryName',
							label: '物品类别',
							align: "center"
						},
						{
							name: this.warehouseId + '1',
							label: '库存',
							align: "center"
						},
						{
							name: this.warehouseId + '2',
							label: '单价',
							align: "center"
						},
						{
							name: this.warehouseId + '3',
							label: '金额',
							align: "center"
						},
						{
							name: this.warehouseId + '4',
							label: '有效期',
							align: "center"
						},
					],
					this.resetList()
				this.show = false
			}
		}
	};
</script>

<style lang="scss" scoped>
	.zIndex {
		z-index: 99;
	}
</style>