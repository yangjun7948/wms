<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">要货管理</block>
		</cu-custom>
		<view v-if="TabCur==0" class="add" @click="addClick">
			<text class="cuIcon-add" style="font-size: 28px;"></text>
		</view>
		<scroll-view scroll-x class="bg-white nav"
			:style="[{'top':CustomBar + 'px','position':'fixed','z-index':'99'}]">
			<view class="flex text-center">
				<view class="cu-item flex-sub" :class="0==TabCur?'text-cyan cur':''" @tap="tabSelect" data-id="0">
					<text class="cuIcon-news"></text>我的请求
				</view>
				<view class="cu-item flex-sub" :class="1==TabCur?'text-cyan cur':''" @tap="tabSelect" data-id="1">
					<text class="cuIcon-edit"></text>要货申请
				</view>
			</view>
		</scroll-view>
		<view class="cu-list menu-avatar" style="padding-top: 112rpx;" v-if="orderList.length>0">
			<view class="cu-item" :class="modalName=='move-box-'+ index?'move-cur':''" v-for="(item,index) in orderList"
				:key="index" @touchstart="ListTouchStart" @touchmove="ListTouchMove" @touchend="ListTouchEnd"
				@tap="godetails(item)" :data-target="'move-box-' + index">
				<view class="content" style="left: 48rpx;">
					<view class="flex">
						<view class="good-title">{{item.documentNumber}}</view>
					</view>
					<view class="text-gray text-sm">
						<text class="cuIcon-infofill text-red  margin-right-xs text-df"></text> {{item.applyState}}
					</view>
				</view>
				<view class="action" style="width: 270rpx;">
					<view class="text-grey text-xs text-df">{{item.createTime}}</view>
					<view class="cu-tag round bg-blue ">{{item.requestedUserName}} </view>
				</view>
				<view class="move">
					<view class="bg-red" @tap.stop="del(item)">删除</view>
				</view>
			</view>
		</view>
		<view v-else>
			<view style="margin-top: 200rpx;">
				<u-empty mode="list" text="暂无单据"></u-empty>
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
				getInReceiptScanList: null,
				TabCur: 0,
				listTouchStart: 0,
				listTouchDirection: null,
				modalName: null,
				CustomBar: this.CustomBar,
			}
		},
		onLoad() {
			let that = this
			this.getInReceiptScanList = debounce(() => {
				that._getApplyReceiptList();
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
			this._getApplyReceiptList();
		},
		methods: {
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				this.resetList();
			},
			addClick() {
				this.Godetails("../order/index");
			},
			resetList() {
				this.pageNum = 1,
					this.loadStatus = "loadmore";
				this.orderList = [];
				this._getApplyReceiptList();
			},
			_getApplyReceiptList() {
				var that = this;
				let url = that.TabCur == 0 ? 'warehouse/app/applyReceipt/mylist' : 'warehouse/app/applyReceipt/list';
				if (that.loadStatus == "loadmore") {
					let opts = {
						url: url + '?pageNum=' + that.pageNum + '&pageSize=' + that
							.pageSize,
						method: 'get'
					};
					that.loadStatus = "loading";
					request.httpTokenRequest(opts).then(res => {
						if (res.code == 200) {
							if (res.rows.length > 0) {
								res.rows.forEach(item => {
									item.applyState = that.getState(item.state)
								})
							}
							that.orderList = that.orderList.concat(res.rows);
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
			getState(state) {
				let returnStr = "";
				switch (state) {
					case "0":
						returnStr = "草稿";
						break;
					case "1":
						returnStr = "待审核";
						break;
					case "2":
						returnStr = "待出库";
						break;
					case "3":
						returnStr = "待入库";
						break;
					case "4":
						returnStr = "已完成";
						break;
					default:
				}
				return returnStr;
			},
			godetails(item) {
				this.Godetails("./detail/index", item);
			},
			// ListTouch触摸开始
			ListTouchStart(e) {
				this.listTouchStart = e.touches[0].pageX
			},

			// ListTouch计算方向
			ListTouchMove(e) {
				this.listTouchDirection = e.touches[0].pageX - this.listTouchStart > 0 ? 'right' : 'left'
			},

			// ListTouch计算滚动
			ListTouchEnd(e) {
				if (this.listTouchDirection == 'left') {
					this.modalName = e.currentTarget.dataset.target
				} else {
					this.modalName = null
				}
				this.listTouchDirection = null
			},
			del(obj) {
				//规定 只有在草稿状态下才可以删除
				if (obj.state == '0') {
					var that = this;
					let applyReceiptId = obj.applyReceiptId;
					let opts = {
						url: 'warehouse/app/applyReceipt/delete/' + applyReceiptId,
						method: 'get'
					};
					request.httpTokenRequest(opts).then(res => {
						if (res.code == 200) {
							that.orderList = that.orderList.filter(item => {
								return item.applyReceiptId != applyReceiptId;
							})
						} else {
							uniowToast({
								title: res.msg,
								icon: 'none'
							})
						}
					});
				} else {
					uni.showToast({
						title: "非草稿状态单据，不可删除",
						icon: 'none'
					})
				}
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

	.add {
		position: fixed;
		z-index: 9999;
		background-color: #FFF;
		right: 28rpx;
		bottom: 364rpx;
		width: 130rpx;
		height: 130rpx;
		border-radius: 130rpx;
		display: -webkit-box;
		display: -webkit-flex;
		display: flex;
		-webkit-box-pack: center;
		-webkit-justify-content: center;
		justify-content: center;
		-webkit-box-align: center;
		-webkit-align-items: center;
		align-items: center;
		-webkit-box-shadow: 0 0 20rpx #dddee1;
		box-shadow: 1px 10px 40rpx 2rpx #dddee1;
		border: 1px solid #dddee1;
	}
</style>