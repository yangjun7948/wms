<!-- 课程班级 -->
<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">物品选择</block>
		</cu-custom>
		<view class="zIndex bg-white">
			<view class="padding">
				<u-search v-model="keyword" @search="actionClick" @custom="actionClick" :show-action="true"
					placeholder="输入物品名进行搜索" action-text="搜索" @clear="clearClick"></u-search>
			</view>
		</view>


		<scroll-view scroll-y style="height: 71vh;width: 100%;" @scrolltolower="reachBottom">
			<view v-for="(item,index) in goodsList" :key="index" :data-id="index"
				class="margin-top bg-white flex padding-sm align-center " style="justify-content: space-between;">
				<view class="flex align-center">
					<u--image :showLoading="true" :src="item.goodsPicture" width="70px" height="70px"></u--image>
					<view class="margin-left">
						<view class="text-lg text-black text-cut" style="max-width: 360rpx;">
							{{item.goodsName}} 【 {{!!item.specificationName? item.specificationName :""}} 】
						</view>
						<view class="flex justify-between" style="width: calc(100vw - 70px - 35px );">
							<view class="margin-top-sm text-df text-green">
								单位: {{item. unitName}}
							</view>
							<view class="">
								<u-number-box v-model="item.goodsNumber" button-size="36" color="#ffffff"
									inputWidth="60" min="0" bgColor="#2979ff" iconStyle="color: #fff"
									@change="valChange()"></u-number-box>
							</view>
						</view>

					</view>
				</view>

			</view>
			<u-loadmore :status="loadStatus" bgColor="#f2f2f2"></u-loadmore>
		</scroll-view>

		<!-- 已选列表 -->
		<view class="cu-modal bottom-modal" :class="selectGoodsShow?'show':''" @tap="selectGoodsShow=false">
			<view class="cu-dialog" @tap.stop="">
				<view class="cu-bar bg-white">
					<view class="action">已选物品列表</view>
					<view class="action text-green padding-right text-xl" @tap="truncateGoods"><text
							class="cuIcon-delete"></text>清空</view>
				</view>
				<view style="height: 50vh;">
					<view class="goodlist">
						<scroll-view scroll-y style="height: 100%;width: 100%;">
							<view v-for="(item,index) in selectedGoods" :key="index" :data-id="index">
								<view v-show="item.goodsNumber>0"
									class="margin-top bg-white flex padding-sm align-center "
									style="justify-content: space-between;">
									<view class="flex align-center">
										<u--image :showLoading="true" :src="item.goodsPicture" width="70px"
											height="70px"></u--image>
										<view class="margin-left">
											<view class="text-lg text-black text-cut text-left"
												style="max-width: 360rpx;">
												{{item.goodsName}} 【
												{{!!item.specificationName? item.specificationName :""}} 】
											</view>
											<view class="flex justify-between"
												style="width: calc(100vw - 70px - 35px );">
												<view class="margin-top-sm text-df text-green">
													单位: {{item. unitName}}
												</view>
												<view class="">
													<u-number-box v-model="item.goodsNumber" button-size="36"
														color="#ffffff" inputWidth="60" min="0" bgColor="#2979ff"
														iconStyle="color: #fff" @change="valChange()"></u-number-box>
												</view>
											</view>

										</view>
									</view>
								</view>

							</view>
						</scroll-view>
					</view>
					<view class="flex margin-top">
						<view class="flex-sub text-lg">
							合计：<text class="text-orange">1</text>
						</view>
						<view class="flex-twice">
							<button style="width: 200px;" @click="selectGoodsShow=false"
								class="cu-btn bg-orange">关闭</button>
						</view>
					</view>
				</view>
			</view>
		</view>

		<view style="position: fixed;bottom: 0px;width: 100%;" class="padding-sm bg-white">
			<view class="cu-bar bg-white tabbar border shop">
				<view class="action" style="width: 40%;" @click="selectGoodsShow=true">
					<view class="cuIcon-cart">
						<view class="cu-tag badge">{{selectedGoods.filter(item=>{return item.goodsNumber>0}).length}}
						</view>
					</view>
					已选
				</view>
				<view class="bg-red submit" @click="submitGoods">确定</view>
			</view>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request.js';
	import {
		debounce
	} from '@/utils/util.js';
	import {
		baseUrl
	} from "@/utils/apiconfig.js";
	import formItem from '../../../../uni_modules/uview-ui/libs/config/props/formItem';
	export default {
		data() {
			return {
				keyword: "",
				TabCur: 0,
				goodsList: [],
				selectedGoods: [],
				selectGoodsShow: false,
				pageNum: 1,
				pageSize: 10,
				loadStatus: "loadmore",
				getGoodsList: null
			};
		},
		onLoad(options) {
			var that = this;
			this.selectedGoods=JSON.parse(JSON.parse(options.params));
			this.getGoodsList = debounce(() => {
				that._getGoodsList();
			}, 1000, true);
		},
		onShow() {
			this.getGoodsList();
		},
		computed: {

		},
		methods: {
			actionClick() {
				this.selectedGoods = this.selectedGoods.filter(item => {
					return item.goodsNumber > 0
				})
				//触发查询
				this.resetList();
			},
			clearClick() {
				this.selectedGoods = this.selectedGoods.filter(item => {
					return item.goodsNumber > 0
				})
				//触发查询
				this.resetList();
			},
			resetList() {
				this.pageNum = 1,
					this.loadStatus = "loadmore";
				this.goodsList = [];
				this.getGoodsList();
			},
			reachBottom() {
				this.getGoodsList();
			},
			_getGoodsList() {
				var that = this;
				if (that.loadStatus == "loadmore") {
					let opts = {
						url: 'warehouse/app/getGoodsList'
					};
					let params = {
						goodsName: that.keyword,
						pageNum: that.pageNum,
						pageSize: that.pageSize,
					}

					that.loadStatus = "loading";
					request.httpTokenRequest(opts, params).then(res => {
						if (res.code == 200) {
							let oldGoodsList = []
							for (var k = 0, length = res.rows.length; k < length; k++) {
								let newgoods = res.rows[k];
								newgoods.goodsPicture = baseUrl + newgoods.goodsPicture
								for (var i = 0, length2 = that.selectedGoods.length; i < length2; i++) {
									if (newgoods.goodsId == that.selectedGoods[i].goodsId) {
										newgoods.goodsNumber = that.selectedGoods[i].goodsNumber
									}
								}
								let index = that.selectedGoods.findIndex(item => {
									return item.goodsId == newgoods.goodsId
								});
								if (index == -1) {
									that.selectedGoods.push(newgoods)
								} else {
									that.selectedGoods.splice(index, 1, newgoods);
								}
							}
							that.goodsList = that.goodsList.concat(res.rows);
							that.total = res.total;
							that.loadStatus = that.pageNum * that.pageSize < res.total ? "loadmore" : "nomore";
							that.pageNum++;
						} else {
							uni.showToast({
						title: res.msg,
						icon: 'none'
					})
							that.loadStatus = 'loadmore';
						}
					});
				}

			},
			valChange(e) {
			},
			truncateGoods() {
				this.selectedGoods.forEach(item => {
					item.goodsNumber = 0
				});
				this.selectGoodsShow = false
			},
			submitGoods() {
				let arr=[];
				this.selectedGoods.forEach(item => {
					if(item.goodsNumber>0) arr.push(item)  
				});
				
				uni.$emit('select', arr)
				uni.navigateBack();
			}
		}
	};
</script>

<style lang="scss" scoped>
	.zIndex {
		z-index: 99;
	}

	.minus {
		width: 22px;
		height: 22px;
		border-width: 1px;
		border-color: #E6E6E6;
		border-style: solid;
		border-top-left-radius: 100px;
		border-top-right-radius: 100px;
		border-bottom-left-radius: 100px;
		border-bottom-right-radius: 100px;
		@include flex;
		justify-content: center;
		align-items: center;
	}

	.input {
		padding: 0 10px;
	}

	.plus {
		width: 22px;
		height: 22px;
		background-color: #FF0000;
		border-radius: 50%;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		justify-content: center;
		align-items: center;
	}

	.goodlist {
		height: calc(100% - 120rpx);
	}
</style>