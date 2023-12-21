<template>
	<view class="contioner">
		<view v-if="searchFalg">
			<view v-if="mesList.length>0">
				<view class="cu-list menu-avatar no-border">
					<view v-for="(item,index) in mesList" :key="index" class="cu-item" @tap="goDetail(item)">
						<view class="cu-avatar round lg" style="background-image:url('static/common/message.png');">
						</view>
						<view class="content  solid-bottom" style="width: calc(100% - 96rpx - 60rpx - 20rpx);">
							<view class="">
								<view class="text-cut text-xl text-black">{{item.documentNumber}}</view>
								<view class="cu-tag round bg-orange sm">未处理</view>
							</view>
							<u-gap height="8"></u-gap>
							<view class="text-gray text-sm flex">
								<view class="text-cut margin-bottom-xs">
									{{item.createTime}}
								</view>
							</view>
						</view>
					</view>
				</view>
				<view v-show="mesList.length>0">
					<u-loadmore :status="loadStatus"></u-loadmore>
				</view>
			</view>
			<view v-else class="flex justify-center align-center">
				<view style="margin-top: 200rpx;">
					<u-empty mode="list" text="暂无消息"></u-empty>
				</view>
			</view>
		</view>
		<view v-else>
			<view class="cu-load load-modal">
				<image src="/static/logo.png" mode="aspectFit"></image>
				<view class="gray-text">加载中...</view>
			</view>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request.js';
	import {
		getPermissions
	} from "@/utils/auth";
	export default {
		data() {
			return {
				searchFalg: false,
				pageNum: 1,
				pageSize: 10,
				loadStatus: "loadmore",
				mesList: [],
				managerFlag: false
			}
		},
		onLoad() {
			this.initPermissions();
		},
		onShow() {
			this.resetList();
		},
		onPullDownRefresh() {
			uni.stopPullDownRefresh();
		},
		onReachBottom() {
			if (this.managerFlag) {
			
			} else {
				this._getApplyList();
			}
		},
		methods: {
			resetList() {
				this.pageNum = 1,
					this.loadStatus = "loadmore";
				this.mesList = [];
				if (this.managerFlag) {

				} else {
					this._getApplyList();
				}
			},
			_getApplyList() {
				var that = this;
				if (that.loadStatus == "loadmore") {
					let opts = {
						url: 'warehouse/app/applyReceipt/list?pageNum=' + that.pageNum + '&pageSize=' + that
							.pageSize,
						method: 'get'
					};
					that.loadStatus = "loading";
					request.httpTokenRequest(opts).then(res => {
						that.searchFalg=true;
						if (res.code == 200) {
							that.mesList = that.mesList.concat(res.rows);
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
			initPermissions() {
				let permissions = getPermissions();
				this.managerFlag = !permissions.indexOf("manager:index") == -1
			},
			goDetail(item){
				if (this.managerFlag) {
				
				} else {
					this._getApplyDetail(item);
				}
				
			},
			_getApplyDetail(item){
				this.Godetails("/pages/workspace/apply/detail/index", item);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.contioner {
		padding-top: 10rpx;
		background-size: cover;
		width: 100vw;
		height: 100vh;
		background-color: #fff;
	}

	.cu-avatar {
		background-color: #FFF;
	}
</style>