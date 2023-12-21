// 该文件用来存储localStorage 本地缓存的方法
import { baseUrl,uploadUrl } from "./apiconfig";
/**
 * 操作用户token
 */
export function setToken(value) {
  uni.setStorageSync('token', value);
  // console.log('存储用户信息成功');
}
/**
 * 获取缓存的Openid
 */
export function getToken() {
  let token = uni.getStorageSync('token');
  return token;
}
/**
 * 移除用户token=openid
 */
export function removeToken() {
  uni.removeStorageSync('token');
}
/**
 * 操作用户信息
 * 缓存微信用户信息
 */
export function setUserInfo(value) {
  try {
    let newValue = JSON.stringify(value);
    uni.setStorageSync('userinfo', newValue);
  } catch (e) {
    return;
  }
}
/**
 * 获取缓存的微信用户信息
 */
export function getUserInfo() {
  let userinfo = uni.getStorageSync('userinfo');
  // console.log('userinfo', userinfo)
  if (userinfo) {
    return JSON.parse(userinfo);
  }
}
/**
 * 移除缓存的用户信息
 */
export function removeUserInfo(){
	uni.removeStorageSync('userinfo');
}

export function setPermissions(value) {
  try {
    uni.setStorageSync('permissions', value);
  } catch (e) {
    return;
  }
}

export function getPermissions() {
  return  uni.getStorageSync('permissions');
}

export function setRoles(value) {
  try {
    uni.setStorageSync('roles', newValue);
  } catch (e) {
    return;
  }
}

export function getRoles() {
  return  uni.getStorageSync('roles');
}

/**
 * 缓存用户分享ID
 * 登录时传到后端
 */
export function setShareID(value){
	try{
		uni.setStorageSync("shareid",value);
	}catch (e) {
    return;
  }
}
/**
 * 获取用户分享ID
 * 登录时传到后端
 */
export function getShareID(){
	let shareid = uni.getStorageSync("shareid")
	if(shareid){
		return shareid;
	}
}
export function getConfig(){
	let base = {
		baseUrl,
		uploadUrl,
	}
	return base;
}