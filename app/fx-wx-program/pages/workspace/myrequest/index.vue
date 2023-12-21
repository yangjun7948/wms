<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">我的请求</block>
		</cu-custom>
		<view class="bg-white">
			<view v-if="dataList.length>0" class="list">
				<view class="item u-border-bottom margin-top-sm" v-for="(item, index) in dataList" :key="index"
					@click="goPage(item)">
					<!-- :style="{color: item.isView === '0' ? '#FF0000' : ''}" -->
					<view class="item-text u-line-1" >
						{{item.taskDescriptionSupplement}}
					</view>
					<view>
						<view class="item-time margin-bottom-sm">{{item.createTime}}
						</view>
					</view>
				</view>
				<view v-show="dataList.length>0">
					<u-loadmore :status="loadStatus" bgColor="#fff"></u-loadmore>
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
		debounce,
		gotoUrl
	} from '@/utils/util.js';
	export default {
		components: {},
		data() {
			return {
				pageNum: 1,
				pageSize: 10,
				loadStatus: "loadmore",
				dataList: [],
				getList: null
			}
		},
		onLoad() {
			let that = this
			this.getList = debounce(() => {
				that._getList();
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
			this.getList();
		},
		methods: {
			resetList() {
				this.pageNum = 1,
				this.loadStatus = "loadmore";
				this.dataList = [];
				this._getList();
			},
			_getList() {
				var that = this;
				if (that.loadStatus == "loadmore") {
					let opts = {
						url: 'act/process/requestlist',
					};
					let params={
						page:that.pageNum,
						row:that.pageSize,
						userId:getUserInfo().userId
					}
					
					that.loadStatus = "loading";
					request.httpTokenRequest(opts,params).then(res => {
						if (res.msg == "success") {
							let data=res.data;
							that.dataList = data.myProcessApplyDtos !=null ? that.dataList.concat(data.myProcessApplyDtos):that.dataList;
							that.total = data.total;
							that.loadStatus = that.pageNum * that.pageSize < data.total ? "loadmore" : "nomore";
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
			goPage(item) {
				let page = gotoUrl(item);
				item.type="myRequest";
				if (item.nodeName == "已结束") {
					item.statusType = "DONE"
				}
				// 修改查看状态为已读
				// statusType  流程当前状态 SAVE：保存 RUNNING：运行中 WITHDRAW：退回
				this.Godetails(page, {
					data: JSON.stringify(item),
					fromPage: 'todo'
				});
			},
		}
	}
</script>

<style >
	page {
		background-color: #fff;
	}
	.list {
		padding: 0 30rpx 30rpx 30upx;
		width: 100%;
		height: auto;
	}

	.item {
		width: 100%;
		padding-bottom: 15rpx;
		padding-top: 15rpx;
		border-bottom: 1upx solid #ccc;
	}

	.item-icon {
		width: 50upx;
		height: 50upx;
	}

	.item-text {
		font-size: 35rpx;
	}

	.item-time {
		margin-top: 15rpx;
		color: grey
	}

	.item-nodename {
		float: right;
	}
</style>