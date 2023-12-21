<template>
	<view class="container">
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">入库扫码详情页</block>
		</cu-custom>

		<view class="bg-top bg-cyan">
			<view class="top-box shadow">
				<view class="qh-pic cu-avatar xl"
					style="background-image:url(https://zhoukaiwen.com/img/kevinLogo.png)"></view>
				<view class="qh-title text-bold text-blue text-xl">
					<text>{{documentNumber}}</text>
				</view>

				<view class="cu-list menu">
					<view class="cu-item" style="padding: 0;">
						<view class="content">
							<text class="cuIcon-homefill text-green"></text>
							<text class="text-black">仓库名称</text>
						</view>
						<view class="action">
							<view class="cu-tag round bg-green light">{{warehouseName}}</view>
						</view>
					</view>

					<view class="cu-item" style="padding: 0;">
						<view class="content">
							<text class="cuIcon-timefill text-blue  margin-right-xs"></text>
							<text class="text-black">申请时间</text>
						</view>
						<view class="action">
							<view class="cu-tag round bg-blue light">{{inWarehouseTime}}</view>
						</view>
					</view>

					<view class="cu-item" style="padding: 0;">
						<view class="content">
							<text class="cuIcon-myfill text-red"></text>
							<text class="text-black">入库类型</text>
						</view>
						<view class="action">
							<text class="text-grey text-sm">{{inWarehouseTypeName}}</text>
						</view>
					</view>

					<view class="bxBox" style="border-top: 1rpx solid #eee;">
						<text class="bxImg cuIcon-selectionfill text-pink"></text>
						<view class="title text-black">备注</view>
					</view>

					<view class='padding-bottom-sm padding-left-sm bg-white'>
						{{remark}}
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
						<zb-table :columns="column" :stripe="true" :border="true" :data="dataList"></zb-table>
					</view>

				</view>
			</view>

			<!-- 加入按钮 -->
			<view class="padding-lr flex margin-top-sm" style="justify-content: space-between;">
				<button class="cu-btn bg-blue margin-top-sm lg" style="width: 46%;" @click="godetails()">物品扫码</button>
				<button class="cu-btn bg-green margin-top-sm lg" style="width: 46%;"
					@click="inReceiptScanSave()">确认完成</button>
			</view>

			<view class="padding-lr" style="margin-top: 15rpx; margin-bottom: 30rpx;">
				<text class="text-grey text-sm">"扫码" 即可对物品进行扫码</text>
			</view>

		</view>
	</view>
</template>

<script>
	import request from '@/utils/request.js';
	export default {
		data() {
			return {
				documentNumber: '',
				inWarehouseTime: '',
				remark: '',
				inWarehouseTypeName: '',
				warehouseInReceiptId: '',
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
					}, {
						name: 'snValidate',
						label: '是否需要扫码',
						filters: {
							'Y': '需扫描',
							'N': '无需'
						},
						align: "center"
					}, {
						name: 'scanSuccess',
						label: '扫码状态',
						filters: {
							true: '已扫码',
							'': '未扫码'
						},
						align: "center"
					},
				],
				noScanList: [],
				dataList: [],
			}
		},
		onLoad(option) {
			let that=this;
			let params = JSON.parse(option.params)
			this.documentNumber = params.documentNumber;
			this.inWarehouseTime = params.inWarehouseTime;
			this.remark = params.remark;
			this.inWarehouseTypeName = params.inWarehouseTypeName;
			this.warehouseInReceiptId = params.warehouseInReceiptId;
			this._getInReceiptScanDetail();
			uni.$on('scanList', res => {
				that.dataList = res
			})
		},
		onUnload() {
			uni.$off("scanList")
		},
		methods: {
			_getInReceiptScanDetail() {
				var that = this;
				let opts = {
					url: 'warehouse/app/inReceiptScanDetail/' + that.warehouseInReceiptId,
					method: 'get'
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						that.warehouseName = res.data.warehouseName;
						that.dataList = res.data.goodsVoList;
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			godetails() {
				let that = this;
				if (that.dataList.length <= 0) {
					that.mes("无‘待扫码物品’")
					return
				} else {
					let snValidate = false
					that.dataList.forEach(item => {
						if (item.snValidate) snValidate = true
					})
					if (!snValidate) {
						that.mes("无‘待扫码物品’")
						return
					}

				}
				uni.setStorageSync('scanList', JSON.stringify(that.dataList))
				that.Godetails("/pages/workspace/barcode/index",{
					fromPage: 'in' 
				});
			},
			inReceiptScanSave() {
				var that = this;
				let num = 0;
				let shouldNum = 0
				that.dataList.forEach(item => {
					if (item.scanSuccess) num++
					if (item.snValidate) shouldNum++
				})
				if (shouldNum > num) {
					uni.showToast({
						title: "存在部分‘须扫码’物品未进行扫码",
						icon: 'none',
					})
					return;
				}
					
				let opts = {
					url: 'warehouse/app/inReceiptScanSave',
				};
				let params = {
					warehouseInReceiptId:that.warehouseInReceiptId,
					goodsVoList: that.dataList
				}
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