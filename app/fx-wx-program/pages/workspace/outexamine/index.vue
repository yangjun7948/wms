<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">出库单审核</block>
		</cu-custom>
		<view class="padding">
			<view v-if="orderList.length>0">
				<view class="order" v-for="(item, index) in orderList" :key="index" @click="godetails(item)">
					<view class="padding-sm shadow bg-white margin-top" style="border-radius: 10px;">
						<view class="flex align-center">
							<view style="flex:9;">
								<view class="text-lg margin-bottom-xl">
									<view class="flex align-center">
										<uni-text class="cuIcon-titles text-green"></uni-text>
										<view class="good-title">单据编号:{{item.documentNumber}}</view>
									</view>
								</view>
								<view class="text-gray margin-left-sm">
									{{item.outWarehouseTime}}
								</view>
							</view>
							<view style="flex:1;">
								<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
							</view>
						</view>

					</view>
				</view>
				<view v-show="orderList.length>0">
					<u-loadmore :status="loadStatus" bgColor="#f2f2f2"></u-loadmore>
				</view>
			</view>
			<view v-else>
				<view style="margin-top: 200rpx;">
					<u-empty mode="list" text="暂无单据"></u-empty>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	import {
		getUserInfo,
	} from "@/utils/auth";
	import request from '@/utils/request.js';
	import {
		debounce
	} from '@/utils/util.js';
	export default {
		components: {},
		data() {
			return {
				pageNum: 1,
				pageSize: 10,
				loadStatus: "loadmore",
				orderList: [],
				getOutReceiptList: null
			}
		},
		onLoad() {
			let that = this
			this.getOutReceiptList = debounce(() => {
				that._getOutReceiptList();
			}, 1000, true);
		},
		created() {

		},
		onShow() {
			this.resetList();
		},
		onPullDownRefresh() {
			this.resetList();
			uni.stopPullDownRefresh();
		},
		onReachBottom() {
			this.getOutReceiptList();
		},
		methods: {
			resetList() {
				this.pageNum = 1,
					this.loadStatus = "loadmore";
				this.orderList = [];
				this._getOutReceiptList();
			},
			_getOutReceiptList() {
				var that = this;
				if (that.loadStatus == "loadmore") {
					let opts = {
						url: 'warehouse/app/outReceiptApproveList?pageNum=' + that.pageNum + '&pageSize=' + that
							.pageSize,
						method: 'get'
					};
					that.loadStatus = "loading";
					request.httpTokenRequest(opts).then(res => {
						if (res.code == 200) {
							that.orderList =that.orderList.concat(res.rows);
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
			godetails(item) {
				let params = {
					receiptId: item.warehouseOutReceiptId,
				}
				this.Godetails("./detail/index", params);
			}
		}
	}
</script>

<style scoped>
	.good-title {
		margin: 0 10rpx;
		font-size: 34rpx;
		font-weight: bold;
	}
</style>