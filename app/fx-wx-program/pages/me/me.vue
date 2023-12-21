<template>
	<view>
		<view v-if="islogin" style="width: 100%;height: 100%;margin-top: -1px;">
			<view class=""
				style="position: absolute;width: 100%;height: 36%; background-image: linear-gradient(#1fca7c,rgba(255,0,0,0));">
			</view>
			<view class="" style="width: 100%;height: 100%;position: relative; padding: 12px 12px 0 12px;">
				<view class="flex text-white align-center" style="margin-top: 90px;margin-left: 10px;">
					<view class="avator">
						<img :src="avatar">
					</view>
					<view class="margin-left-sm">
						<view class="text-xl text-bold">
							{{userinfo.userName}}
							<!-- {{!!userinfo.name ?userinfo.name:""}} -->
						</view>
						<view class="text-lg" style="margin-top: 10rpx;">
							您好{{!!userinfo.remark? userinfo.remark:""}}，欢迎使用
						</view>
					</view>
				</view>
				<u-gap></u-gap>
				<view class="box">
					<view class="box-bd padding-lg">
						<view class="flex align-center justify-between">
							<view class="flex align-center">
								<view class="text-xl text-bold text-black">
									我的仓库
								</view>
								<view class="margin-left-sm margin-right-sm">
									|
								</view>
								<view class="text-gray">
									仓库信息
								</view>
							</view>
						</view>
						<u-gap height="10px"></u-gap>
						<view class="solid radius padding-sm"
							style="overflow: hidden;;height: 130px;box-shadow: 0 0rpx 2rpx 0rpx rgba(0, 0, 150, 0.1);background-image: url('static/user/room.png'); background-size: 42%; background-repeat: no-repeat; background-position: right;">
							<view class="">
								<view class="text-lg" style="width: 80%;height: 42px;">
									{{userinfo.userName}}  的仓库
								</view>
								<u-gap></u-gap>
								<button class="cu-btn bg-green round"
									@click="Godetails('/pages/workspace/stockcheck/index')">查看详情</button>
							</view>
						</view>
					</view>
				</view>
				<u-gap></u-gap>
				<view class="text-xl bg-white"
					style="width: 680rpx;height: 138px;line-height: 40px;margin: 0 auto;border-radius: 5px;padding: 10px;">
					<view class="flex solid-bottom" style="justify-content: space-between;"
						@tap="Godetails('/pages/me/update-password')">
						<view class="">
							修改密码
						</view>
						<view class="">
							<text class="cuIcon-right"></text>
						</view>
					</view>
					<view class="flex solid-bottom" style="justify-content: space-between;" @tap="">
						<view class="">
							用户协议
						</view>
						<view class="">
							<text class="cuIcon-right"></text>
						</view>
					</view>
					<view class="flex " style="justify-content: space-between;" @tap="loginOut()">
						<view class="">
							退出登录
						</view>
						<view class="">
							<text class="cuIcon-right"></text>
						</view>
					</view>
				</view>
				<u-gap height="50px"></u-gap>
				<!-- <view class="flex justify-center">
					<view class="bg-white text-center text-xl"
						style="height: 40px;line-height: 40px;width: 680rpx;border-radius: 10rpx;">
						退出登录
					</view>
				</view> -->
			</view>
		</view>
		<view v-else class="page bg-white">

		</view>
	</view>
</template>

<script>
	import {
		getUserInfo
	} from '@/utils/auth';
	import request from '@/utils/request.js';
	export default {
		data() {
			return {
				avatar: '/static/user/default-user.png',
				userinfo: {},
				islogin: true,
			}
		},
		onShow() {
			this.userinfo = getUserInfo() || {};
		},
		methods: {
			loginOut(){
				let that =this;
				let opts = {
					url: 'warehouse/app/logout',
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "退出登录成功",
							icon: 'success',
						})
						uni.clearStorageSync();
						setTimeout(function(){
							uni.reLaunch({
								url: '/pages/login/index'
							});
						},1000)
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

<style lang="scss">
	page {
		background-color: #f1f1f1;
		font-size: 28rpx;
	}

	.avator {
		width: 140rpx;
		height: 140rpx;
		background: #fff;
		border: 5rpx solid #f1f1f1;
		border-radius: 50%;
		overflow: hidden;

		img {
			width: 100%;
			height: 100%;
		}
	}

	.header {
		background: #fff;
		height: 770rpx;
		padding-bottom: 110rpx;

		.bg {
			width: 100%;
			height: 100%;
			padding-top: 310rpx;
			background: linear-gradient(to bottom, #f37b1d, #f3f3f3);
		}
	}

	.box {
		width: 680rpx;
		height: 400rpx;
		border-radius: 20rpx;
		margin: 0 auto;
		background: #fff;
		box-shadow: 0 5rpx 20rpx 0rpx rgba(0, 0, 150, .2);

		.box-hd {
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			margin-top: 15rpx;

			.avator {
				width: 160rpx;
				height: 160rpx;
				background: #fff;
				border: 5rpx solid #f1f1f1;
				border-radius: 50%;
				margin-top: -80rpx;
				overflow: hidden;
				box-shadow: 0 5rpx 20rpx 0rpx rgba(0, 0, 150, 0.2);

				img {
					width: 100%;
					height: 100%;
				}
			}

			.phone-number {
				width: 100%;
				text-align: center;
				margin: 20rpx auto;
			}
		}

		.box-bd {}
	}
</style>