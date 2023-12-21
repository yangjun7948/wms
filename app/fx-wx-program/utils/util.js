const formatTime = date => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':');
};

const formatNumber = n => {
  n = n.toString();
  return n[1] ? n : '0' + n;
};
const getNowdate = date => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();
  return year+'-'+month+'-'+day;
};

/**
 * @param {Function} fn 需要执行的方法，因this指向问题，建议不使用箭头函数，
 * @param {Number} delay 间隔时间，默认值100
 * @param {Boolean} promptly 是否立即执行，默认false
 * **/
const debounce = (fn,delay = 1000,promptly) => {
    let timer = null;
    return function(...args) {
        // 立即执行
        if(promptly) {
            // 当timer为null时执行
            if(!timer) fn.apply(this,args);
            if(timer) {
                clearTimeout(timer)
            }
            timer = setTimeout(() => {
                timer = null;
            },delay)
        }else {
            if(timer) {
                clearTimeout(timer)
            }
            timer = setTimeout(() => {
                fn.apply(this,args);
            },delay)
        }
    }
}
/**
 * 获取权限
 * @param {string} params.permission  对应权限的scope，例如 scope.bluetooth
 * @param {String} params.permissionName  提示框显示的权限名称，例如 蓝牙
 * @param {String} params.customPopup  将实现自定义的弹窗代替系统弹窗
 * @param {Function} params.success  授权成功回调函数
 * @param {Function} params.fail  授权失败回调函数
 * @param {Function} params.cancel  取消继续授权回调函数
 */
function getPermission(params) {

	const _permission = `scope.${params.permission}`
	const _tipsContent = `您拒绝了${params.permissionName}权限，将导致部分功能不能正常使用，去设置权限？`

	uni.getSetting({
		success(res) {
			// 判断是否有相关权限属性
			if (res.authSetting.hasOwnProperty(_permission)) {
				// 属性存在，且为false
				if (!res.authSetting[_permission]) {
					uni.showModal({
						title: '提示',
						content: _tipsContent,
						success: (res)=> {
							if(res.confirm) {
								uni.openSetting()
							}else {
								if(params.cancel) {
									setTimeout(()=> {
										params.cancel()
									}, 200)
								}
							}
						}
					})
				}else {
					params.success && params.success()
				}
			} else {
				// 属性不存在，需要授权
				uni.authorize({
					scope: _permission,
					success() {
						// 授权成功
						params.success && params.success()
					},
					fail() {
						uni.showModal({
							title: '提示',
							content: _tipsContent,
							success: (res)=> {
								if(res.confirm) {
									uni.openSetting()
								}else {
									if(params.cancel) {
										setTimeout(()=> {
											params.cancel()
										}, 200)
									}
								}
							}
						})
					}
				})
			}
		}
	})
}

//控制流程跳转到制定页面
function gotoUrl(item) {
	//let statusType = item.statusType;
	return '/pages/workspace/flow/'+item.processCode+"/index";
}

module.exports = {
  formatTime: formatTime,
  getnowTime:getNowdate,
  debounce:debounce,
  gotoUrl:gotoUrl,
  getPermission
};