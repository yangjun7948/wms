<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">下单要货</block>
		</cu-custom>
		<view class="margin-top">
			<form>
				<view class="cu-form-group">
					<view class="title">要货日期</view>
					<picker mode="date" :value="form.applyTime" start="2023-09-01" end="2050-12-31"
						@change="DateChange">
						<view class="picker">
							{{form.applyTime}}
						</view>
					</picker>
				</view>

				<view class="cu-form-group">
					<view class="title">仓库类型</view>
					<radio-group @change="applyTypeChange">
						<label class="margin-left-sm">
							<radio class="blue radio" :checked="form.applyType=='1'?true:false" value="1"></radio>
							<text class="margin-left-sm">公司总仓</text>
						</label>
						<label class="margin-left-sm">
							<radio class="blue radio" :checked="form.applyType=='2'?true:false" value="2"></radio>
							<text class="margin-left-sm">个人仓库</text>
						</label>
					</radio-group>
				</view>
				<view v-show="form.applyType==='2'" class="cu-form-group" @click="personShow =true">
					<view class="title">目标人</view>
					<view class="">
						<view v-if="!!form.requestedUserName">{{form.requestedUserName}}</view>
						<text v-else class="cuIcon-right text-gray text-lg"></text>
					</view>
				</view>
				<view class="cu-form-group">
					<textarea maxlength="250" @input="textareaAInput" placeholder="可填写备注"></textarea>
				</view>
			</form>
		</view>
		<view class="margin-top bg-white flex justify-center">
			<button style="width: 60%;" class="cu-btn block bg-blue margin-tb-sm lg"
				@click="goGoods()">
				<text class="cuIcon-goodsfill"></text> 手动选择商品</button>
		</view>
		<!-- 人员选择 -->
		<view class="cu-modal drawer-modal justify-start" :class="personShow?'show':''" @tap="personShow=false">
			<view class="pop-con cu-dialog basis-lg" @tap.stop=""
				:style="[{top:CustomBar+'px',height:'calc(100vh - ' + CustomBar + 'px)'}]">
				<view class="top">
					<view class="pop-title">人员选择</view>
					<view class="search-box">
						<u-search v-model="keyword" @custom="actionClick" @focus="focus" :show-action="showAction"
							action-text="搜索" @change="change" @clear="clearClick"></u-search>
					</view>
				</view>

				<view style="margin-top: 180rpx;height: calc(100% - 180rpx);overflow-y: scroll;">
					<view class="magin-top">
						<DaTreeVue2 ref="DaTreeRef" :data="treeData" labelField="nickName" valueField="userId"
							expandChecked onlyRadioLeaf :showRadioIcon="false" @change="handleTreeChange"></DaTreeVue2>
					</view>
					<view style="height: 25px;"></view>
				</view>
			</view>

		</view>

		<view class="magin-top bg-white">
			<zb-table :columns="column" :stripe="true" :border="true" :data="form.receiptGoodsList"></zb-table>
		</view>

		<view v-show="form.receiptGoodsList.length>0" class="margin-top  flex justify-center">
			<button style="width: 60%;" class="cu-btn round block bg-green margin-tb-sm lg" @click="submitOrder()">
				<text class="cuIcon-check"></text> 确认提交</button>
		</view>

	</view>
</template>

<script>
	import {
		getUserInfo
	} from '@/utils/auth';
	import request from '@/utils/request.js';
	import DaTreeVue2 from '@/components/da-tree-vue2/index.vue'
	export default {
		components: {
			DaTreeVue2
		},
		data() {
			return {
				CustomBar: this.CustomBar,
				personShow: false,
				keyword: "",
				dataList: [],
				showAction: false,
				allTreeData: [],
				treeData: [],
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
						name: 'goodsNumber',
						label: '要货数量',
						align: "center"
					}
				],
				form: {
					applyTime: "",
					applyType: "1", //	要货类型,1向公司要货，2向个人要货	
					applyUserId: getUserInfo().userId, //	要货人ID	
					applyUserName: getUserInfo().nickName,
					applyWarehouseId: "", //要货仓库	
					applyWarehouseName: "",
					documentNumber: "", //要货单号
					receiptGoodsList: [], //要货物品明细	物品信息
					remark: "",
					requestedUserId: "",
					requestedUserName: "",
				}

			}
		},
		onLoad() {
			let that = this;
			uni.$on('select', res => {
				that.form.receiptGoodsList = res
			})
			this.init();
			//生成要货单
			this.getApplyCode();
		},
		onUnload() {
			uni.$off("select")
		},
		methods: {
			init() {
				this.form.applyTime = this.$u.timeFormat()
			},
			getApplyCode() {
				var that = this;
				let opts = {
					url: 'warehouse/app/applyCode',
					method: 'get',
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						that.form.documentNumber = res.msg;
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			DateChange(e) {
				this.form.applyTime = e.detail.value
			},
			applyTypeChange(e) {
				this.form.applyType = e.detail.value;
				if (this.form.applyType == "1") {
					this.form.requestedUserId = "";
					this.form.requestedUserName = "";
				} else {
					//获取人员
					if (this.treeData.length <= 0) this.getUser();
				}
			},
			getUser() {
				var that = this;
				let opts = {
					url: 'warehouse/app/fixedUser/list',
					method: 'get',
				};
				
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						that.treeData = res.rows;
						that.allTreeData = JSON.parse(JSON.stringify(res.rows));
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})			}
				});
			},
			submitOrder() {
				let that = this;
				uni.showModal({
					title: "提示",
					content: "是否确认提交",
					success: function(res) {
						if (res.confirm) {
							that.submit()
						}
					}
				})
			},
			submit() {
				var that = this;
				let opts = {
					url: 'warehouse/app/applyReceipt',
				};

				that.form.applyTime = that.form.applyTime + " 00:00:00"
				request.httpTokenRequest(opts, that.form).then(res => {
					if (res.code == 200) {
						that.mes("提交成功")
						setTimeout(function() {
							uni.navigateBack()
						}, 1000)
					} else {
						that.muni.showToast({
						title: res.msg,
						icon: 'none'
					})}
				});
			},
			textareaAInput(e) {
				this.form.remark = e.detail.value
			},
			focus() {
				this.showAction = true;
			},
			clearClick() {
				this.showAction = false;
				this.keyword = "";
				this.treeData = JSON.parse(JSON.stringify(this.allTreeData));
			},
			actionClick() {
				this.change();
			},
			change() {
				let that = this
				this.treeData = JSON.parse(JSON.stringify(this.allTreeData.filter(item => {
					return item.warehouseName.indexOf(that.keyword) >= 0
				})));
			},
			handleTreeChange(values, currentItem) {
				//数据赋值
				this.form.requestedUserId = values;
				this.form.requestedUserName = currentItem.label;
				//关闭弹框
				this.personShow = false;
			},
			goGoods(){
				this.Godetails('/pages/workspace/order/goods/index',JSON.stringify(this.form.receiptGoodsList));
			}


		}
	}
</script>


<style lang="scss" scoped>
	.pop-con {
		.top {
			position: fixed;
			top: 0;
			width: 100%;
			left: 0;
			z-index: 2;
			background-color: #FFf;

			.pop-title {
				text-align: center;
				height: 96rpx;
				line-height: 96rpx;
				background: #F8F8F8;
			}

			.search-box {
				padding: 12rpx;
			}
		}

		.bot-btn {
			background: #FFFFFF;
			position: fixed;
			text-align: center;
			z-index: 9;
			border-top: 1px solid #f7f7f7;
			width: 95%;
			bottom: 0;
			padding: 12rpx;
		}
	}
</style>