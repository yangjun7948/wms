<template>
	<view>
		<cu-custom bgColor="bg-cyan" :isBack="true">
			<block slot="backText">返回</block>
			<block slot="content">修改密码</block>
		</cu-custom>
		<view class="form">
			<u-form :model="form" ref="uForm" label-width="160">
				<u-form-item label="旧密码" prop="oldpassword">
					<u-input v-model="form.oldpassword" :password-icon="true" type="password" />
				</u-form-item>
				<u-form-item label="新密码" prop="password">
					<u-input v-model="form.password" :password-icon="true" type="password" />
				</u-form-item>
				<u-form-item label="确认密码" prop="conpassword">
					<u-input v-model="form.conpassword" :password-icon="true" type="password" />
				</u-form-item>
			</u-form>
			<u-button style="margin-top: 80upx;" type="primary" @click="sumbit">确认</u-button>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request.js';
	export default {
		data() {
			return {
				form: {
					oldpassword: '',
					password: '',
					conpassword: ''
				}
			}
		},
		methods: {
			sumbit() {
				var that=this;
				if(!this.form.oldpassword){
					this.mes('请输入旧密码')
					return;
				}else if(!this.form.password){
					this.mes('请输入新密码')
					return;
				}else if(!this.form.conpassword){
					this.mes('请输入《确认新密码》')
					return;
				}else if (this.form.password !== this.form.conpassword) {
					this.$u.toast('两次输入密码不一致')
					return
				}
				
				let opts = {
					url: 'warehouse/app/updatePwd?oldPassword='+ that.form.oldpassword + '&newPassword=' + that.form.password,
					method:'put'
				};
				request.httpTokenRequest(opts).then(res => {
					if (res.code == 200) {
							uni.showToast({
								title:"修改成功",
								icon:'success'
							})
							setTimeout(function(){
								uni.navigateBack()
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
	.form {
		padding: 30upx;
	}
</style>