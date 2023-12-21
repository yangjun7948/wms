<template>
	<view>
		<cu-custom bgColor="bg-cyan">
			<block slot="content">飞修仓储</block>
		</cu-custom>
		<view class="" style="width: 100%;height: 100%;margin-top: -1px;">
			<view class=""
				style="position: absolute;width: 100%;height: 50%; background-image: linear-gradient(#1fca7c,rgba(255,0,0,0));">
			</view>
			<view class="" style="width: 100%;height: 100%;position: relative; padding: 12px 12px 0 12px;">
				<view class="shadow bg-white" style="width: 100%;border-radius: 10px;">
					<view class="cu-bar">
						<view class="action text-lg text-black">
							常用服务
						</view>
					</view>
					<view class="cu-list grid no-border row-3 col-3">
						<view v-if="item.permissions" class="cu-item flex align-center"
							style="padding: 20rpx 20rpx 30rpx;" v-for="(item,index) in serviceList" :key="index"
							@click="Godetails(item.path)">
							<image class="image-icon" :src="item.icon"></image>
							<view style="text-align: left;">{{item.title}}</view>
						</view>
					</view>
					<u-gap height="20"></u-gap>
				</view>
			</view>

			<view v-if="reportShow" style="width: 100%;height: 100%;position: relative; padding: 12px 12px 0 12px;">
				<view class="radius shadow bg-white" style="width: 100%;border-radius: 10px;">
					<view class="cu-bar">
						<view class="action text-lg text-black">
							库存报表
						</view>
					</view>
					<view class="cu-list grid no-border row-3 col-3">
						<view v-if="item.permissions" class="cu-item flex align-center"
							style="padding: 20rpx 20rpx 30rpx;border-bottom: none;" v-for="(item,index) in reportList"
							:key="index" @click="Godetails(item.path)">
							<image class="image-icon" :src="item.icon"></image>
							<view style="text-align: left;">{{item.title}}</view>
						</view>
					</view>
					<u-gap height="20"></u-gap>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getPermissions
	} from "@/utils/auth";
	export default {
		components: {},
		data() {
			return {
				defaultPage: 1,
				defaultRow: 10,
				reportShow: false,
				serviceList: [{
						icon: "/static/workspace/in-examine.png",
						title: '入库审核',
						path: "/pages/workspace/inexamine/index",
						permissions: true,
						permissionsKey: 'inreceipt:approve'
					}, {
						icon: "/static/workspace/out-examine.png",
						title: '出库审核',
						path: "/pages/workspace/outexamine/index",
						permissions: false,
						permissionsKey: 'outreceipt:approve'
					},
					{
						icon: "/static/workspace/in-sao.png",
						title: '入库扫码',
						path: "/pages/workspace/insao/index",
						permissions: false,
						permissionsKey: 'approve:scan'
					},
					{
						icon: "/static/workspace/out-sao.png",
						title: '出库扫码',
						path: "/pages/workspace/outsao/index",
						permissions: false,
						permissionsKey: 'outreceipt:scan'
					},
					// {
					// 	icon: "/static/workspace/order.png",
					// 	title: '下单要货',
					// 	path: "/pages/workspace/order/index",
					// 	permissions: false,
					// 	permissionsKey: 'worker:index'
					// },
					{
						icon: "/static/workspace/apply.png",
						title: '要货管理',
						path: "/pages/workspace/apply/index",
						permissions: false,
						permissionsKey: 'apply:index'
					},
					{
						icon: "/static/workspace/daiban.png",
						title: '待办',
						path: "/pages/workspace/daiban/index",
						permissions: false,
						permissionsKey: 'progress:index'
					},
					{
						icon: "/static/workspace/yiban.png",
						title: '已办',
						path: "/pages/workspace/yiban/index",
						permissions: false,
						permissionsKey: 'progress:index'
					},
					{
						icon: "/static/workspace/myrequest.png",
						title: '我的请求',
						path: "/pages/workspace/myrequest/index",
						permissions: false,
						permissionsKey: 'progress:index'
					},
				],
				reportList: [{
					icon: "/static/workspace/inventory.png",
					title: '库存查询',
					path: "/pages/workspace/stockcheck/index",
					permissions: false,
					permissionsKey: 'stock:query'
				}],
				loginShow: false,
				informations: [],
				records: 0,
			}
		},
		onLoad() {
			this.initPermissions();
		},

		methods: {
			initPermissions() {
				this.reportShow = false;
				let permissions = getPermissions();

				let that = this;
				if ("*:*:*" == permissions[0]) {
					//更新list数据
					that.serviceList.forEach(function(item, index) {
						item.permissions = true;
					});
					that.reportList.forEach(function(item, index) {
						item.permissions = true;
						that.reportShow = true;
					});
				} else {
					//更新list数据
					that.serviceList.forEach(function(item, index) {
						try {
							permissions.forEach(pItem => {
								if (item.permissionsKey == pItem) {
									item.permissions = true;
									throw new Error("EndIterative");
								}
							})
						} catch (error) {}
					});
					that.reportList.forEach(function(item, index) {
						try {
							permissions.forEach(pItem => {
								if (item.permissionsKey == pItem) {
									item.permissions = true;
									that.reportShow = true;
									throw new Error("EndIterative");
								}
							})
						} catch (error) {}
					});
				}
			},
		}
	}
</script>

<style scoped>
	.container {
		width: 100%;

	}

	.image-icon {
		width: 100rpx;
		height: 100rpx;
	}

	.cu-list.grid.col-3>.cu-item:nth-last-child(-n+2):after {
		border-bottom-width: 0
	}

	.notice {
		height: 670rpx;
	}

	.community-search {
		margin-bottom: 10px;
		padding-left: 10px;
	}

	.city-name {
		width: 50px;
		line-height: 34px;
		font-weight: bolder;
		text-align: center;
		margin-right: 10px;
	}

	.select-item {
		flex: 1;
		padding: 0 5px 0 5px;
		justify-content: space-between;
		align-items: center;
	}

	.select-item-title {
		width: 100px;
	}

	.u-divider {
		margin-top: 0px;
		margin-bottom: 0px;
	}

	.house-info {
		padding: 5px 10px;
	}

	.house-item {
		padding-bottom: 5px;
	}

	.highlight {
		color: #19be6b;
	}

	.font-12 {
		font-size: 12px;
	}
</style>