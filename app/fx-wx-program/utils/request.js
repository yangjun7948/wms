import { getToken} from "./auth";
import { baseUrl,uploadUrl,AppId} from "./apiconfig";

// 不带token请求
const httpRequest = (opts, data) => {
	uni.onNetworkStatusChange(function(res) {
		if (!res.isConnected) {
			uni.showToast({
				title: '网络连接不可用！',
				icon: 'none'
			});
		}
		return false
	});
	let httpDefaultOpts = {
		url: baseUrl + opts.url,
		data: data,
		method: opts.method == 'get'? 'GET':'POST',
		header: opts.method == 'get' ? {
			'X-Requested-With': 'XMLHttpRequest',
			"AppId": AppId,
			"Accept": "application/json",
			"Content-Type": "application/json; charset=UTF-8"
		} : {
			"AppId": AppId,
			'X-Requested-With': 'XMLHttpRequest',
			'Content-Type': 'application/json; charset=UTF-8'
		},
		dataType: 'json',
	}
	let promise = new Promise(function(resolve, reject) {
		uni.request(httpDefaultOpts).then(
			(res) => {
				resolve(res[1].data)
			}
		).catch(
			(response) => {
				reject(response.data)
			}
		)
	})
	return promise
};

//带Token请求
const httpTokenRequest = (opts, data) => {
	uni.onNetworkStatusChange(function(res) {
		if (!res.isConnected) {
			uni.showToast({
				title: '网络连接不可用！',
				icon: 'none'
			});
		}
		return false
	});
	let token = getToken();
	// hadToken()
	if (token == '' || token == undefined || token == null) {
		uni.showToast({
			title: '账号已过期，请重新登录',
			icon: 'none',
			complete: function() {
				uni.reLaunch({
					url: '/pages/login/index'
				});
			}
		});
	} else {
		let httpDefaultOpts = {
			url: baseUrl + opts.url,
			data: data,
			method: opts.method == 'get'? 'GET': opts.method=="put" ? 'PUT':'POST',
			header: opts.method == 'get' ? {
				'Authorization': token,
				"AppId": AppId,
				'X-Requested-With': 'XMLHttpRequest',
				"Accept": "application/json",
				"Content-Type": "application/json; charset=UTF-8"
			} : {
				'Authorization': token,
				"AppId": AppId,
				'X-Requested-With': 'XMLHttpRequest',
				'Content-Type': 'application/json; charset=UTF-8'
			},
			dataType: 'json',
		}
		let promise = new Promise(function(resolve, reject) {
			uni.request(httpDefaultOpts).then(
				(res) => {
					if (res[1].data.code == 200) {
						resolve(res[1].data)
					} else {
						if (res[1].data.code == 5000|| res[1].data.code == 401) {
							uni.reLaunch({
								url: '/pages/login/index'
							});
							uni.clearStorageSync();
						} else {
							resolve(res[1].data)
						}
					}
				}
			).catch(
				(response) => {
					reject(response.data)
				}
			)
		})
		return promise
	}
};

//带Token请求
const uploadFile = (opts) => {
	let token = getToken();
	// hadToken()
	if (token == '' || token == undefined || token == null) {
		uni.showToast({
			title: '账号已过期，请重新登录',
			icon: 'none',
			complete: function() {
				uni.reLaunch({
					url: '/pages/login/index'
				});
			}
		});
	} else {
		let headers = {
			'Authorization': token,
			"AppId": AppId,
		}
		
		let httpDefaultOpts = {
			url: baseUrl + opts.url,
			filePath: opts.filePath,
			name: opts.name ? opts.name : 'file',
			header: headers,
			formData: opts.params,
		}
		let promise = new Promise(function(resolve, reject) {
			uni.uploadFile(httpDefaultOpts).then(
				(res) => {
					if (res[1].data.code == 200) {
						resolve(res[1].data)
					} else {
						if (res[1].data.code == 5000|| res[1].data.code == 401) {
							uni.reLaunch({
								url: '/pages/login/index'
							});
							uni.clearStorageSync();
						} else {
							resolve(res[1].data)
						}
					}
				}
			).catch(
				(response) => {
					reject(response.data)
				}
			)
		})
		return promise
	}
};

// 拦截器
const hadToken = () => {
	let token = uni.getStorageSync('token');

	if (token == '' || token == undefined || token == null) {
		uni.showToast({
			title: '账号已过期，请重新登录',
			icon: 'none',
			complete: function() {
				uni.reLaunch({
					url: '/pages/login/index'
				});
			}
		});
		return false;
	}
	return true
}
export default {
	httpRequest,
	httpTokenRequest,
	hadToken,
	uploadFile
};