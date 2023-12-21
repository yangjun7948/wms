<template>
	<view class="container">
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">要货申请详情页</block>
		</cu-custom>

		<view class="bg-top bg-cyan">
			<view class="top-box shadow">
				<view class="qh-pic cu-avatar xl"
					style="background-image:url(https://zhoukaiwen.com/img/kevinLogo.png)"></view>
				<view class="qh-title text-bold text-blue text-xl">
					<text>{{form.documentNumber}}</text>
				</view>

				<view class="cu-list menu">
					<view class="cu-item" style="padding: 0;">
						<view class="content">
							<text class="cuIcon-homefill text-green"></text>
							<text class="text-black">要货仓库</text>
						</view>
						<view class="action">
							<view class="cu-tag round bg-green light">{{form.applyWarehouseName}}</view>
						</view>
					</view>

					<view class="cu-item" style="padding: 0;">
						<view class="content">
							<text class="cuIcon-timefill text-blue  margin-right-xs"></text>
							<text class="text-black">申请时间</text>
						</view>
						<view class="action">
							<view class="cu-tag round bg-blue light">{{form.createTime}}</view>
						</view>
					</view>

					<view class="bxBox" style="border-top: 1rpx solid #eee;">
						<text class="bxImg cuIcon-selectionfill text-pink"></text>
						<view class="title text-black">备注</view>
					</view>

					<view class='padding-bottom-sm padding-left-sm bg-white'>
						{{form.remark}}
					</view>

				</view>

			</view>

			<!-- 物品列表 -->
			<view class="center-box shadow">
				<view class="cu-list menu">
					<view class="cu-bar bg-white margin-top-xs u-border-bottom">
						<view class="action sub-title">
							<text class="text-xl text-bold text-blue text-shadow">物品列表</text>
							<text class="text-ABC text-blue">LIST</text>
						</view>
					</view>
					<view style="min-height: 34vh">
						<uni-table ref="table"  border stripe emptyText="暂无更多数据">
							<uni-tr>
								<uni-th width="90" align="center">物品名称</uni-th>
								<uni-th width="70" align="center">要货数</uni-th>
								<uni-th v-if="form.state==1 && form.requestedUserId==userId" width="140" align="center">给予数量</uni-th>
							</uni-tr>
							<uni-tr v-for="(item, index) in dataList" :key="index">
								<uni-td align="center">{{ item.goodsName }}</uni-td>
								<uni-td align="center">{{ item.originalNumber}}</uni-td>
								<uni-td v-if="form.state==1 && form.requestedUserId==userId">
									<u-number-box v-model="item.goodsNumber" button-size="36" color="#ffffff"
										inputWidth="60" min="0" bgColor="#2979ff" iconStyle="color: #fff"
										@change="valChange()"></u-number-box>
								</uni-td>
							</uni-tr>
						</uni-table>
					</view>

				</view>
			</view>
			<!-- 加入按钮 -->
			<view v-if="form.state==1 && form.requestedUserId==userId" class="padding-lr flex margin-top-sm" style="justify-content: space-between;">
				<button class="cu-btn bg-red margin-top-sm lg" style="width: 46%;" @click="doSave(0)">拒绝申请</button>
				<button class="cu-btn bg-green margin-top-sm lg" style="width: 46%;"
					@click="doSave(1)">同意申请</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getUserInfo,
	} from "@/utils/auth";
	import request from '@/utils/request.js';
	export default {
		data() {
			return {
				userId:getUserInfo().userId,
				form:{},
				dataList: [],
			}
		},
		onLoad(option) {
			let that=this;
			let params = JSON.parse(option.params)
			this.form = params;
			 this._getInApplyDetail();
		
		},
		onUnload() {
		},
		methods: {
			_getInApplyDetail() {
				var that = this;
				let opts = {
					url: 'warehouse/app/applyReceipt/detail/' + that.form.applyReceiptId,
					method: 'get'
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						if (res.data.receiptGoodsList.length > 0) {
							res.data.receiptGoodsList.forEach(item => {
								item.originalNumber = item.goodsNumber+"";
							})
						}
						that.dataList = res.data.receiptGoodsList;
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			doSave(val) {
				let url = val==0 ? 'warehouse/app/applyReceipt/reject':'warehouse/app/applyReceipt/do';
				var that = this;
				let opts = {
					url: url,
				};
				
				that.form.receiptGoodsList=that.dataList;
				let params = that.form;
				request.httpTokenRequest(opts, params).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "提交成功",
							icon: 'success',
						})
						setTimeout(function(){
							uni.navigateBack()
						},1000)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})			}
				});
			}

		}
	}
</script>
<style lang="scss" scoped>
	.container {
		width: 750rpx;
		color: #333333;

		.bg-top {
			margin-top: -1rpx;
			width: 750rpx;
			height: 220rpx;
			padding-top: 50rpx;
			border-radius: 0 0 20% 20%;

			.top-box {
				width: 700rpx;
				background-color: #FFFFFF;
				margin: 0 auto;
				border-radius: 20rpx;
				padding: 20rpx 30rpx 0rpx;
				position: relative;

				.qh-pic {
					position: absolute;
					right: 64rpx;
					top: -50rpx;
					border-radius: 12rpx;
				}

				.qh-title {
					width: 100%;
					height: 60rpx;
					line-height: 65rpx;
					padding-right: 190rpx;
				}

				.bxBox {
					position: relative;
					display: flex;
					/* padding: 0 30rpx; */
					min-height: 100rpx;
					/* background-color: #ffffff; */
					/* justify-content: space-between; */
					align-items: center;
					font-size: 30rpx;
					line-height: 1.6em;
					flex: 1;

					.bxImg {
						display: inline-block;
						margin-right: 10rpx;
						width: 1.6em;
						text-align: center;
					}
				}

			}
		}

		.center-box {
			color: #333333;
			width: 700rpx;
			background-color: #FFFFFF;
			margin: 0 auto;
			border-radius: 20rpx;
			padding: 0rpx 30rpx 0rpx;
			position: relative;
			margin-top: 20rpx;
		}
	}
</style>