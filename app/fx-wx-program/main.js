import App from './App'
import share from '@/utils/share.js'
Vue.mixin(share)
// #ifndef VUE3
import Vue from 'vue'
// uview
import uView from '@/uni_modules/uview-ui'
Vue.use(uView)

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})


function Godetails(url, v) {
	let params ="";
	if(v!=undefined) params=JSON.stringify(v);
	uni.navigateTo({
		url: url + '?params=' + params
	})
}

function ZH(json) {
	return JSON.parse(json)
}

function mes(str) {
	uni.showToast({
		title: str,
		icon: 'none'
	})
}
Vue.prototype.mes = mes;
Vue.prototype.Godetails = Godetails;
Vue.prototype.ZH = ZH;

//使用colorUI自定义标题
import cuCustom from './colorui/components/cu-custom.vue'
Vue.component('cu-custom',cuCustom)
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import { param } from 'jquery'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif