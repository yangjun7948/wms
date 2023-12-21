<template>
	<!-- v-if="showOptions" -->
	<view class="bot-con">
		<view class="" style="width: 60%;" @click="curOptions=null;qpShow = true">
			<u--input class="input" v-model="comment" :disabled="true" placeholder="请签批" :custom-style="style"
				prefixIcon="edit-pen" prefixIconStyle="font-size: 32px;color: #ddd" />
		</view>
		<view class="" style="width: 40%;display: flex;justify-content: space-around;align-items: center;">
			<view style="width: 76px;" v-if="submitAuthority||showWithdrawFlag">
				<u-button type="primary" size="medium" shape="circle" v-if="showWithdrawFlag" text="撤回"
					@click="withdrawProcess">
				</u-button>
				<u-button type="primary" size="medium" shape="circle" v-else text="批准" @click="examinationApproval">
				</u-button>
			</view>
			<!--  已办，撤回功能    -->
			<view style="width: 76px;" v-else-if="data.statusType == 'DONE' && data.owner == ''">
				<u-button type="primary" size="medium" shape="circle" text="撤回" @click="approvalWithdraw">
				</u-button>
			</view>
			<view style="width: 76px;" v-else>
				<u-button type="primary" shape="circle" text="转发" @click="curOptions=null;qpShow = true"></u-button>
			</view>
			<u-icon name="more-circle" size="48" @click="actionShow = true"></u-icon>
		</view>

		<!-- 请签批 -->
		<u-popup height="auto" mode="bottom" :show="qpShow" @close="qpShow=false">
			<view class="pop-con">
				<view style="position: absolute;right: 10px;top: 3px;" class="photo-icon">
					<view @click="chooseImg">
						<u-icon class="icon" name="plus" size="44" color="#606266"></u-icon>
					</view>
				</view>

				<view class="title">
					请签批
				</view>
				<view class="textarea">
					<textarea style="padding: 24rpx;" v-model="comment" placeholder="请输入签字意见"></textarea>
					<view v-if="fileShowList.length>0" class="display-area">
						<scroll-view scroll-x="true">
							<view class="uploadWrap">
								<view v-for="(item, index) in fileShowList" :key="index">
									<view class="weui-uploader__file q-image-wrap">
										<image class="weui-uploader__img  question-img" :src="item" mode="aspectFill"
											@click="preview(index)" />
										<view class="q-image-remover" @click="removeImage(index)">
											<u-icon class="icon" name="minus-circle" size="40" color="#fa3534b5">
											</u-icon>
										</view>
									</view>
								</view>
							</view>
						</scroll-view>
					</view>
				</view>

				<view class="pop-btn">
					<view class="cancel" @click="qpShow = false" v-if="!curOptions">返回</view>
					<view class="cancel" v-if="!showWithdrawFlag&&curOptions" @click="curOptionsClick">{{curOptions}}
					</view>
					<view class="right">
						<view v-if="submitAuthority||showWithdrawFlag">
							<view class="text " style="min-width: 30%;text-align: center;" v-if="showWithdrawFlag"
								@click="withdrawProcess">撤回</view>
							<view class="text " style="min-width: 30%;text-align: center;" v-else
								@click="examinationApproval">批准</view>
						</view>
						<!--  已办，撤回功能    -->
						<view v-else-if="data.statusType == 'DONE' && data.owner == ''">
							<view class="text " style="min-width: 30%;text-align: center;" @click="approvalWithdraw">撤回
							</view>
						</view>
						<view v-else>
							<view class="text" style="min-width: 30%;text-align: center;" @click="onProcessForward">转发
							</view>
						</view>

						<!-- <view class="text" style="min-width: 30%;text-align: center;" -->
						<view class="border-left" style="padding-left: 40rpx;">
							<u-icon name='more-dot-fill' size="40" @click="actionShow = true"></u-icon>
						</view>
					</view>
				</view>
			</view>
		</u-popup>

		<!-- 退回节点选择 -->
		<u-popup border-radius="8" mode="center" :show="backProcessShow" :safeAreaInsetBottom="false" @close="backProcessShow=false" closeable>
			<view class="pop-con" style="width: 38vh;height: 40vh;">
				<view class="title">
					请选择退回节点
				</view>
				<scroll-view scroll-y="true" class=" radio ">
					<u-radio-group v-model="backProcessSelect" size="40" :wrap="true" @change="radioGroupChange">
						<u-radio v-for="(item, index) in radioList" :key="index" :label="item.taskDefinitionName" :name="item.taskDefinitionKey">
							
						</u-radio>
					</u-radio-group>
				</scroll-view>

				<view class="pop-btn">
					<view class="cancel" @click="backProcessShow = false" style="width: 50% !important; ">取消</view>
					<view class="confirm colorMain" @click="backProcess">确定</view>
				</view>
			</view>
		</u-popup>

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

		<!-- 操作action -->
		<u-action-sheet :actions="flowOption" :show="actionShow" @select="actionShowClick" :safeAreaInsetBottom="true" cancelText="取消" @close="actionShow=false"
			:closeOnClickOverlay="true"></u-action-sheet>

	</view>
</template>

<script>
	import {
		getUserInfo,
	} from "@/utils/auth";
	import request from '@/utils/request.js';
	import DaTreeVue2 from '@/components/da-tree-vue2/index.vue'
	export default {
		components: {
			DaTreeVue2
		},
		props: {
			type: {
				type: String,
				default: 'submit'
			},
			procCode: {
				type: String,
				default: ''
			},
			inType: {
				type: String,
				default: ''
			},
			submitAuthority: {
				type: Boolean,
				default: false
			},
			showWithdrawFlag: {
				type: Boolean,
				default: false
			},
			seftClick: {
				type: Boolean,
				default: false
			},
			data: {
				type: Object,
				default: () => {
					return {}
				}
			},
			taskId: {
				type: String,
				default: ""
			},
			processInstanceId: {
				type: String,
				default: ""
			},
			delegation: {
				type: String,
				default: ""
			},
			flowOption: {
				type: Array,
				default: () => []
			},
		},
		data() {
			return {
				CustomBar: this.CustomBar,
				showAction: false,
				keyword: "",
				comment: "",
				current: [],
				fileUrls: [],
				qpShow: false,
				curOptions: null, //当前点击的操作
				radioList: [],
				personShow: false,
				backProcessSelect: "",
				style: {
					paddingLeft: '36rpx'
				},
				backProcessShow: false,
				actionShow: false,
				showUploadList: false,
				fileList: [],
				fileShowList: [],
				lists: [],
				treeData: [],
				allTreeData: []

			}
		},
		created() {},
		methods: {
			chooseImg() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					success: res => {

						let path = res.tempFilePaths[0];
						let lastIndex = path.lastIndexOf('/') + 1;
						let fileName = path.substring(lastIndex, path.length);

						let opts = {
							url: 'common/upload',
							filePath: path,
							params: {
								bucketName: 'profile',
								fileName: fileName,
							}
						}
						request.uploadFile(opts).then(res => {
							let resObj = JSON.parse(res);
							if (resObj.code == 200) {
								this.fileList.push(fileName + "," + resObj.fileName)
								this.fileShowList.push(path)
							} else {
								this.msg(resObj.msg)
							}

						})
					}
				});
			},
			// 移除上传图片
			removeImage(index) {
				this.fileList.splice(index, 1);
				this.fileShowList.splice(index, 1);
			},
			preview(index) {
				uni.previewImage({
					urls: this.fileShowList,
					current: index
				})
			},
			radioGroupChange(e) { //节点选择
				this.backProcessSelect = e;
			},
			actionShowClick(e) {
				let str = e.name;
				this.optionClick(str)
				this.actionShow = false
			},
			optionClick(type) {
				if (type == '退回') {
					this.qpShow = true;
					this.curOptions = "退回"
				} else if (type == '转发') {
					this.personUse = '0';
					if (this.treeData.length == 0) this.getPersonTree();
					this.personShow = true;
				} else if (type == '转办') {
					this.personUse = '1';
					if (this.treeData.length == 0) this.getPersonTree();
					this.personShow = true;
				} else if (type == '意见征询') {
					this.personUse = '2';
					if (this.treeData.length == 0) this.getPersonTree();
					this.personShow = true;
				} else if (type == '撤回') {
					this.qpShow = true;
				} else if (type == '批准') {
					this.qpShow = true;
				}
			},
			//转发
			onProcessForward() {
				this.personUse = '0';
				if (this.treeData.length == 0) this.getPersonTree();
				this.personShow = true;
			},
			getPersonTree() {
				let that = this
				let opts = {
					url: 'warehouse/app/user/list',
					method: 'get'
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code === 200) {
						that.treeData = res.data;
						that.allTreeData = JSON.parse(JSON.stringify(res.data));
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
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
					return item.nickName.indexOf(that.keyword) >= 0
				})));
			},
			handleTreeChange(values, item) {
				//关闭弹框
				this.personShow = false;
				if (this.personUse == '0') {
					this.processForward(item)
				} else if (this.personUse == '1') {
					this.processTransfer(item)
				} else if (this.personUse == '2') {
					this.opinionSeek(item);
				}
			},
			//退回
			curOptionsClick() {
				if (this.curOptions == '退回') {
					if (this.$u.test.isEmpty(this.comment)) {
						this.$u.toast('退回时请输入签字意见');
						return;
					}

					this.queryProcessBackNode();
					this.qpShow=false;
				}
			},
			// 获取回退节点
			queryProcessBackNode() {
				let opts = {
					url: 'act/process/queryProcessBackNode',
				};
				
				let params = {
					processInstanceId: this.processInstanceId,
				}
				//
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						this.radioList = JSON.parse(res.data);
						this.backProcessShow = true;
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			//撤回
			withdrawProcess() {
				let opts = {
					url: 'act/process/withdrawProcess',
				};

				let params = {
					taskId: this.taskId,
					processInstanceId: this.processInstanceId,
					procCode: this.procCode,
					comment: this.comment,
					employeeId: getUserInfo().userId
				}
				//
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '撤回成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			//撤回
			approvalWithdraw() {
				let opts = {
					url: 'act/process/processApprovalWithdraw',
				};
				
				let params = {
					processInstanceId: this.processInstanceId,
					employeeId: getUserInfo().userId
				}
				//
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '撤回成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			//  批准
			examinationApproval() {
				if (this.seftClick) {
					this.$emit("submit", {
						"comment": this.comment,
						"commentFile": this.fileList.join(";")
					});
					//标记为已经处理
					return;
				}
				let opts = {
					url: 'act/process/examinationApproval',
				};

				let params = {
					processInstanceId: this.processInstanceId,
					userId: uni.getStorageSync('userinfo').userId,
					formDate: null,
					comment: this.comment,
					taskId: this.taskId,
					delegation: this.delegation,
					commentFile: this.fileList.join(";"),
					procCode: this.procCode
				}
				//
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '审批成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			//  转办
			processTransfer(user) {
				let opts = {
					url: 'act/process/processTransfer',
				};

				let params = {
					processInstanceId: this.processInstanceId,
					employeeId: getUserInfo().userId,
					transferEmployeeId: user.userId,
					transferEmployeeName: user.nickName,
					transferComment: this.comment,
					transferCommentFile: this.fileList.join(";")
				}

				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '转办成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			// 流程转发
			processForward(user) {
				let that = this;
				let opts = {
					url: 'act/process/processTransfer',
				};

				let params = {
					processInstanceId: this.processInstanceId,
					employeeId: getUserInfo().userId,
					forwardEmployeeId: user.userId,
					forwardComment: this.comment,
					forwardCommentFile: this.fileList.join(";"),
					forwardEmployeeName: user.nickName
				}
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '转发成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			// 意见征询
			opinionSeek(user) {
				let that = this;
				let opts = {
					url: 'act/process/opinionSeek',
				};

				let params = {
					processInstanceId: this.processInstanceId,
					employeeId: getUserInfo().userId,
					opinionSeekerId: user.userId,
					opinionSeekerName: user.nickName,
					opinionSeekComment: this.comment
				}

				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '征询成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
			},
			// 流程回退
			backProcess() {
				debugger
				if (this.$u.test.isEmpty(this.backProcessSelect)) {
					this.$u.toast('请选择要退回的节点');
					return;
				}
				this.backProcessShow = false;
				let select = this.radioList.find(i => i.taskDefinitionKey == this.backProcessSelect);
				let params = {
					taskId: this.taskId,
					processInstanceId: this.processInstanceId,
					comment: this.comment,
					taskDefinitionId: select.taskDefinitionKey,
					taskDefinitionName: select.taskDefinitionName,
					userId: getUserInfo().userId,
					commentFile: this.fileList.join(";")
				}
				
				let opts = {
					url: 'act/process/backProgress',
				};
				request.httpTokenRequest(opts, params).then(res => {
					if (res.msg == "success") {
						uni.showToast({
							title: '退回成功',
							icon: 'success'
						});
						setTimeout(res => {
							uni.navigateBack({});
						}, 1500)
					} else {
						uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					}
				});
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

	.swiper-con {
		height: 93vh;
	}

	.pop-con {
		height: 100%;

		.title {
			text-align: center;
			font-weight: bold;
			height: 80rpx;
			line-height: 90rpx;
			font-size: 32rpx;
		}

		.textarea {
			height: calc(100% - 224rpx);
			margin: 24rpx;
			border: 1rpx solid #eeeeee;
		}

		.radio {
			height: calc(100% - 172rpx);
			padding: 24rpx 32rpx;
		}

		.border-left {
			border-left: 1rpx solid #eeeeee;
		}

		.pop-btn {
			display: flex;
			justify-content: space-between;
			align-items: center;
			height: 96rpx;
			border-top: 1rpx solid #eeeeee;
			padding-left: 30rpx;
			padding-right: 30rpx;
			font-weight: bold;
			font-size: 30rpx;

			.cancel {
				width: 40%;
				text-align: center;
				border-right: 1rpx solid #eeeeee;
			}

			.confirm {
				width: 50%;
				text-align: center;
			}

			.right {
				width: 40%;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
		}

	}

	.bot-con {
		position: fixed;
		width: 100%;
		bottom: 0rpx;
		padding-top: 20rpx;
		padding-right: 24rpx;
		padding-bottom: 20rpx;
		padding-bottom: 20rpx+constant(safe-area-inset-bottom);
		padding-bottom: 20rpx+env(safe-area-inset-bottom);
		padding-left: 24rpx;
		z-index: 999;
		background-color: #f7f7f7;
		display: flex;
		justify-content: space-between;
		align-items: center;

		.input {
			position: relative;
			left: 0;
			float: left;
			width: 100%;
		}

		.icon {
			position: absolute;
			left: 36rpx;
			top: 36rpx;
			float: left;
			z-index: 2;
		}
	}

	.content {
		padding: 24rpx;
		padding-bottom: 100rpx;

		.img-title {
			text-align: right;
			padding: 0 24rpx 0 24rpx;
			border-bottom: 2rpx solid #FB4D23;

			image {
				width: 240rpx;
				height: 60rpx;
			}
		}

		.title {
			font-size: 30rpx;
			font-weight: bold;
			text-align: center;
			padding: 24rpx;
		}

		.table {}

		.file-list {
			width: 100%;

			.file-list-cell {
				font-size: 24rpx;
				display: flex;
				color: #999;
				justify-content: space-between;
				align-items: center;
				line-height: 32rpx;
				width: 100%;

				.file {
					width: 70%;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}

				.right {
					display: flex;
					justify-content: space-around;
					align-items: center;
					width: 20%;
				}
			}
		}

		.sub-title {
			padding-top: 24rpx;
			padding-bottom: 24rpx;
			color: #fff;

			.title {
				background: #13B5F5;
				font-size: 24rpx;
				padding: 4rpx 48rpx 4rpx 4rpx;
				border-radius: 0 8rpx 8rpx 0;
			}
		}
	}

	/*图片展示 */
	.display-area {
		height: 150rpx;
		overflow: hidden;
		white-space: nowrap;
	}

	.uploadWrap {
		height: 160rpx;
		width: 600rpx;
		display: flex;
		display: -webkit-box !important;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.q-image-wrap {
		width: 150rpx;
		height: 150rpx;
		margin-right: 34rpx;
		display: inline-block;
		position: relative;
		margin-bottom: 20rpx;
	}

	.q-image-remover {
		position: absolute;
		top: -6rpx;
		left: 76rpx;
	}

	.question-img {
		width: 150rpx;
		height: 150rpx;
	}
</style>