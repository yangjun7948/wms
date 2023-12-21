<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">{{flowInfo.procName}}</block>
		</cu-custom>
		<view class="">
			<scroll-view scroll-y="true" class="swiper-con">
				<view class="content">
					<view class="img-title">
						<!-- <image src="../../../../static/images/ic/ic_flow_logo.png"></image> -->
					</view>

					<u-gap height="10"></u-gap>
					<view class="flex padding bg-white">
						<view class="text-xl  text-black" style="width: 30%;">
							申请部门
						</view>
						<view class="text-lg">
							{{form.applyDepartmentName}}
						</view>
					</view>
					<u-gap height="10" bgColor="#f3f4f6"></u-gap>
					<view class="flex padding bg-white">
						<view class="text-xl  text-black" style="width: 30%;">
							申请人
						</view>
						<view class="text-lg">
							{{form.applyUserName}}
						</view>
					</view>
					<u-gap height="10" bgColor="#f3f4f6"></u-gap>
					<view class="flex padding bg-white">
						<view class=" text-xl  text-black" style="width: 30%;">
							备注
						</view>
						<view class="text-lg " style="width: 60%;white-space: normal;word-break:break-word;">
							{{form.remark}}
						</view>
					</view>
					<u-gap height="10" bgColor="#f3f4f6"></u-gap>
					
					<view class="sub-title">
						<text class="title">流转意见</text>
					</view>
					<view>
						<YSteps :infoList='processCommentList'></YSteps>
					</view>
				</view>

				<view style="height:170rpx;"></view>
			</scroll-view>

			<view class="botton-view" v-if="viewShow">
				<process-submit :submitAuthority="submitAuthority" :data="flowInfo"
					:showWithdrawFlag="showWithdrawFlag" :processInstanceId="flowInfo.processInstanceId"
					:procCode="flowInfo.processCode" :taskId="flowInfo.taskId" :flowOption="FLOW_OPTIONS"
					:delegation="delegation"></process-submit>
			</view>
		</view>
	</view>


</template>

<script>
	import {
		getUserInfo,
	} from "@/utils/auth";
	import request from '@/utils/request.js';
	import processSubmit from '@/components/process-submit/index.vue'
	import YSteps from '@/components/Y-Steps/Y-Steps.vue'
	import DIC from '@/utils/dic_flow.js'
	export default {
		components: {
			processSubmit,
			YSteps
		},
		data() {
			return {
				delegation: "",
				loading: false,
				flowInfo: null,
				statusType: '', // statusType  流程当前状态 SAVE：保存 RUNNING：运行中 WITHDRAW：退回
				viewShow:false,
				submitAuthority: false, //是否提交和退回的权限 true代表有权限，false就是审批权限
				showWithdrawFlag: false, //是否可以撤回标识。true:可以撤回 false:不可以撤回
				FLOW_OPTIONS: [], //流程操作viewShow:false,
				form: {
					applyDepartmentId: "",
					applyDepartmentName: "",
					applyUserId: '',
					applyUserName: "",
					comment: "",
					commentFile: "",
					remark: "",
					startType: "",
				},
				processCommentList:[]
			}
		},
		onReady() {

		},
		onLoad(options) {
			let data = JSON.parse(options.params)
			this.flowInfo = JSON.parse(data.data);
			this.statusType = this.flowInfo.statusType;
			this.delegation = data.delegation;
			this.showWithdrawFlag=this.flowInfo.showWithdrawFlag
			this.getDetail();
		},

		methods: {
			getDetail() {
				let opts = {
					url: 'act/process/processNewDetail',
				};
				
				let params = {
						processInstanceId: this.flowInfo.processInstanceId,
						taskId: this.flowInfo.taskId,
						userId: getUserInfo().userId
				}
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						let data = res.data;
						this.submitAuthority = data.submitAuthority;
						this.processCommentList = data.processCommentList ? data.processCommentList : [];
						this.viewShow = true;
						
						this.form = data.formComonent;
						if (this.submitAuthority || this.showWithdrawFlag) {
							if (this.showWithdrawFlag) { //是否可以撤回标识。true:可以撤回 false:不可以撤回
								this.FLOW_OPTIONS = DIC.FLOW_OPTIONS_04
							} else {
								if (data.processOperationAuthority.suspensionState != '1') {
									this.FLOW_OPTIONS = DIC.FLOW_OPTIONS_01
								} else {
									if (data.processOperationAuthority.delegation == "PENDING") {
										this.FLOW_OPTIONS = DIC.FLOW_OPTIONS_01;
									} else if (data.processOperationAuthority.delegation != "PENDING") {
										this.FLOW_OPTIONS = DIC.FLOW_OPTIONS_02;
									}
								}
							}
						} else {
							this.FLOW_OPTIONS = DIC.FLOW_OPTIONS_05;
						}
						// if (this.data.type == "myRequest" && this.data.nodeName != "已结束") {
						// 	this.FLOW_OPTIONS = this.FLOW_OPTIONS.concat([{
						// 		text: '提醒办理'
						// 	}])
						// }
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
		}
	}
</script>
<style lang="scss">
	page{
		background: #fff;
	}
	
	//流程下拉框
	.botton-view {
		bottom: 0px;
		position: fixed;
		width: 100%;
		background-color: #fff;
		z-index: 50;

	}
	.sub-title {
		padding-top: 24rpx;
		padding-bottom: 24rpx;
		color: #fff;
	
		.title {
			background: #13B5F5;
			font-size: 32rpx;
			padding: 4rpx 48rpx 4rpx 18rpx;
			border-radius: 0 8rpx 8rpx 0;
		}
	}
</style>