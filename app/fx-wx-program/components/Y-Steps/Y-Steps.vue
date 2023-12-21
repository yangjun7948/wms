<template>
	<!-- 获取一个数组，结构为[{date:'时间',info:'内容内容'}] -->
	<!-- @click事件返回点击标签元素的索引值 第一项为0 -->
	<view class="bg">
		<view class="steps">
			<view class="steps_item" v-for="(i, index) in infoList" :key="index">
				<view class="s_r">
					<view class="line" :style="{backgroundColor:index != 0?backgroundColor:'rgba(0,0,0,0)'}"></view>
					<view class="done" v-if="index!=infoList.length-1 && infoList.length != 1"></view>
					<view class="index" v-else :style="{backgroundColor:backgroundColor,color:color}">
						{{ index + 1 }}
					</view>
					<view class="line"
						:style="{backgroundColor:index != infoList.length-1?backgroundColor:'rgba(0,0,0,0)'}"></view>
				</view>
				<view class="s_l">
					<view class="info_item">
						<view class="flex-" style="font-size: 26rpx;font-weight: bold;">
							<text>{{i.userName}}</text>
							<text>{{i.processNodeName}}</text>
						</view>
						<view :style="{WebkitLineClamp:lineNum!=0?lineNum:''}">
							<view class="flex-" style="font-size: 26rpx;color: #909399;">
								<text>接收人：{{i.receiver}}</text>
								<text>{{timeformat(i.approvalTime)}}</text>
							</view>
							<view style="font-size: 26rpx;color: #909399;">{{i.comment}}</view>
							<view>
								<view v-for="(item,index1) in i.commentFileList" :key="index1">
									<view @tap.stop='previewImage(item.fileAddress)' class="text-blue text-cut"  style="width: 285px;">{{item.fileName}}</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		baseUrl
	} from "@/utils/apiconfig.js";
	export default {
		name: 'YSteps',
		props: {
			infoList: {
				type: Array,
				default: () => []
			},
			color: {
				type: String,
				default: '#fff'
			},
			backgroundColor: {
				type: String,
				default: '#2D8CF0'
			},
			lineNum: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {};
		},
		onLoad(e) {},
		methods: {
			timeformat(time) {
				return time.replace("T", " ");
			},
			//预览
			previewImage(url) {
				let file = baseUrl+ url;
				var imgReg = /^(\s|\S)+(png|jpeg|jpg|bmp)+$/;
				if (imgReg.test(file)) {
					uni.previewImage({
						urls: [file]
					})
				} else {
					var httpReg = /(http|https):\/\/([\w.]+\/?)\S*/;
					if (httpReg.test(file)) {
						uni.showLoading({
							title: '文件加载中...',
							mask: false
						});
						uni.downloadFile({
							url: file,
							success: function(res) {
								uni.hideLoading()
								uni.openDocument({
									filePath: res.tempFilePath,
									success: function(res) {}
								});
							},
							fail: (e) => {
								console.log(e);
							}
						});
					}

				}
			}
		}
	};
</script>

<style lang="scss" scoped>
	.flex- {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.bg {
		margin: 20upx 0;
		background: #fff;
		display: flex;
		width: 100%;
	}

	.steps {
		width: 100%;
		display: flex;
		flex-direction: column;

		.steps_item {
			display: flex;

			.s_r {
				padding: 0 20rpx;
				display: flex;
				flex-direction: column;
				align-items: center;

				.line {
					flex: 1;
					width: 2rpx;
					background-color: #fff;
				}

				.index {
					width: 40rpx;
					height: 40rpx;
					font-size: 12px;
					text-align: center;
					line-height: 40rpx;
					border-radius: 50rpx;
				}

				.done {
					position: relative;
					display: inline-block;
					width: 40rpx;
					height: 40rpx;
					background: red;
					border-radius: 40rpx;
					border: 1px solid #2D8CF0;
					background-color: #FFFFFF;

					&::after {
						content: "";
						position: absolute;
						left: 10rpx;
						top: 10rpx;
						width: 50%;
						height: 25%;
						border: 2px solid #2D8CF0;
						border-radius: 1px;
						border-top: none;
						border-right: none;
						background: transparent;
						transform: rotate(-45deg);
					}
				}
			}

			.s_l {
				display: flex;
				flex-direction: column;
				background: #fff;
				flex: 1;

				.info_item {
					background-color: #FFFFFF;
					margin-right: 0upx;
					border-radius: 10upx;
					display: flex;
					flex-direction: column;
					justify-content: center;
					padding: 30upx;
				}

				.info_item:active {
					background-color: #f4f4f4;
				}
			}
		}
	}

	.file {
		width: 100%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>