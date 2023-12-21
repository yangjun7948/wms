export default {
	data() {
		return {
			// 默认的全局分享内容
			share: {
				title: '我在使用小程序，你也要不要试试呢~',//分享时的标题
				path: '/pages/index/index?shareid='+ uni.getStorageSync('token'), // 全局分享的路径，比如 首页
				imageUrl: '/static/img/share.jpg', // 全局分享的图片(可本地可网络)
			}
		}
	},
	// 定义全局分享
	// 1.发送给朋友
    onShareAppMessage(res) {
        return {
			title: this.share.title,
			path: this.share.path,
			imageUrl: this.share.imageUrl,
		}
    },
	//2.分享到朋友圈
    onShareTimeline(res) {
        return {
			title: this.share.title,
			path: this.share.path,
			imageUrl: this.share.imageUrl,
		}
    },
}

