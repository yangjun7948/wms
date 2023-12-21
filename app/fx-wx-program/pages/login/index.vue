<!-- 蓝色登录页面2 -->
<template>
	<view style="height:100vh;background: #fff;">
		<view class="img-a">
			<view class="t-b">
				您好，
				<br />
				欢迎使用，飞修仓储
			</view>
		</view>
		<view class="login-view" style="">
			<view class="t-login">
				<form class="cl">
					<view class="t-a">
						<text class="txt">用户名</text>
						<input type="text" placeholder="请输入您的用户名" maxlength="32" v-model="username" />
					</view>
					<view class="t-a">
						<text class="txt">密码</text>
						<input type="password" maxlength="18" placeholder="请输入您的密码" v-model="password" />
					</view>
					<button @tap="login()">登 录</button>
					<!-- <view class="reg" @tap="reg()">注 册</view> -->
				</form>
				<view class="t-f"><text>—————— 第三方账号登录 ——————</text></view>
				<view class="t-e cl">
					<view class="t-g" @tap="wxLogin()">
						<image src="https://zhoukaiwen.com/img/loginImg/wx.png"></image>
					</view>
					<view class="t-g" @tap="zfbLogin()">
						<image src="https://zhoukaiwen.com/img/loginImg/qq.png"></image>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	import {
		setToken,
		setUserInfo,
		setPermissions,
		setRoles
	} from "@/utils/auth";
	import request from '@/utils/request.js';
	export default {
		data() {
			return {
				username: '', //用户名
				password: '' //密码
			};
		},
		onLoad() {},
		methods: {
			//当前登录按钮操作
			login() {
				var that = this;
				if (!that.username) {
					uni.showToast({
						title: '请输入您的用户名',
						icon: 'none'
					});
					return;
				}
				if (!that.password) {
					uni.showToast({
						title: '请输入您的密码',
						icon: 'none'
					});
					return;
				}
				let opts = {
					url: 'warehouse/app/login',
				};
				let params = {
					'username': that.username,
					'password': that.password
				}
				uni.showLoading({
					title: '加载中'
				});
				request.httpRequest(opts, params).then(res => {
					uni.hideLoading();
					if (res.code == 200) {
						//将token存入缓存
						setToken(res.token);
						//获取用户信息
						that.getInfo();
					} else {
						uni.hideLoading();
						this.mes(res.msg)
					}
				});
			},
			//注册按钮点击
			// reg() {
			// 	uni.showToast({ title: '注册跳转', icon: 'none' });
			// },
			getInfo() {
				var that = this;
				let opts = {
					url: 'warehouse/app/getInfo',
					method:'get'
				};
				uni.showLoading({
					title: '加载中'
				});
				request.httpTokenRequest(opts).then(res => {
					uni.hideLoading();
					if (res.code == 200) {
						setUserInfo(res.user)
						setPermissions(res.permissions);
						setRoles(res.roles);
						//跳转至首页
						uni.reLaunch({
							url: '/pages/index/index'
						});
					} else {
						uni.hideLoading();
						this.mes(res.msg)
					}
				});

			},
			//等三方微信登录
			wxLogin() {
				uni.showToast({
					title: '暂未开放',
					icon: 'none'
				});
			},
			//第三方支付宝登录
			zfbLogin() {
				uni.showToast({
					title: '暂未开放',
					icon: 'none'
				});
			}
		}
	};
</script>
<style>
	.txt {
		font-size: 32rpx;
		font-weight: bold;
		color: #333333;
	}

	.img-a {
		width: 100%;
		height: 450rpx;
		background-image: url(https://zhoukaiwen.com/img/loginImg/head.png);
		background-size: 100%;
	}

	.reg {
		font-size: 28rpx;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		font-weight: bold;
		background: #f5f6fa;
		color: #000000;
		text-align: center;
		margin-top: 30rpx;
	}

	.login-view {
		width: 100%;
		position: relative;
		margin-top: -120rpx;
		background-color: #ffffff;
		border-radius: 8% 8% 0% 0;
	}

	.t-login {
		width: 600rpx;
		margin: 0 auto;
		font-size: 28rpx;
		padding-top: 80rpx;
	}

	.t-login button {
		font-size: 28rpx;
		background: #2796f2;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		font-weight: bold;
	}

	.t-login input {
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 50rpx;
		border-bottom: 1px solid #e9e9e9;
		font-size: 28rpx;
	}

	.t-login .t-a {
		position: relative;
	}

	.t-b {
		text-align: left;
		font-size: 42rpx;
		color: #ffffff;
		padding: 130rpx 0 0 70rpx;
		font-weight: bold;
		line-height: 70rpx;
	}

	.t-login .t-c {
		position: absolute;
		right: 22rpx;
		top: 22rpx;
		background: #5677fc;
		color: #fff;
		font-size: 24rpx;
		border-radius: 50rpx;
		height: 50rpx;
		line-height: 50rpx;
		padding: 0 25rpx;
	}

	.t-login .t-d {
		text-align: center;
		color: #999;
		margin: 80rpx 0;
	}

	.t-login .t-e {
		text-align: center;
		width: 250rpx;
		margin: 80rpx auto 0;
	}

	.t-login .t-g {
		float: left;
		width: 50%;
	}

	.t-login .t-e image {
		width: 50rpx;
		height: 50rpx;
	}

	.t-login .t-f {
		text-align: center;
		margin: 150rpx 0 0 0;
		color: #666;
	}

	.t-login .t-f text {
		margin-left: 20rpx;
		color: #aaaaaa;
		font-size: 27rpx;
	}

	.t-login .uni-input-placeholder {
		color: #aeaeae;
	}

	.cl {
		zoom: 1;
	}

	.cl:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}
</style>