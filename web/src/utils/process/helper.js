
import request from '@/utils/request'

export function validatenull(val) {
  if (typeof val === 'boolean') {
    return false
  }
  if (typeof val === 'number') {
    return false
  }
  if (val instanceof Array) {
    if (val.length == 0) return true
  } else if (val instanceof Object) {
    if (JSON.stringify(val) === '{}') return true
  } else {
    if (val == 'null' || val == null || val == 'undefined' || val == undefined || val == '') return true
    return false
  }
  return false
}
// 表单序列化
export const serialize = data => {
  let list = []
  Object.keys(data).forEach(ele => {
    list.push(`${ele}=${data[ele]}`)
  })
  return list.join('&')
}
export const getObjType = obj => {
  var toString = Object.prototype.toString
  var map = {
    '[object Boolean]': 'boolean',
    '[object Number]': 'number',
    '[object String]': 'string',
    '[object Function]': 'function',
    '[object Array]': 'array',
    '[object Date]': 'date',
    '[object RegExp]': 'regExp',
    '[object Undefined]': 'undefined',
    '[object Null]': 'null',
    '[object Object]': 'object'
  }
  if (obj instanceof Element) {
    return 'element'
  }
  return map[toString.call(obj)]
}
/**
 * 对象深拷贝
 */
export const deepClone = data => {
  var type = getObjType(data)
  var obj
  if (type === 'array') {
    obj = []
  } else if (type === 'object') {
    obj = {}
  } else {
    // 不再具有下一层次
    return data
  }
  if (type === 'array') {
    for (var i = 0, len = data.length; i < len; i++) {
      obj.push(deepClone(data[i]))
    }
  } else if (type === 'object') {
    for (var key in data) {
      obj[key] = deepClone(data[key])
    }
  }
  return obj
}
/**
 * 判断路由是否相等
 */
export const diff = (obj1, obj2) => {
  delete obj1.close
  var o1 = obj1 instanceof Object
  var o2 = obj2 instanceof Object
  if (!o1 || !o2) { /*  判断不是对象  */
    return obj1 === obj2
  }

  if (Object.keys(obj1).length !== Object.keys(obj2).length) {
    return false
    // Object.keys() 返回一个由对象的自身可枚举属性(key值)组成的数组,例如：数组返回下表：let arr = ["a", "b", "c"];console.log(Object.keys(arr))->0,1,2;
  }

  for (var attr in obj1) {
    var t1 = obj1[attr] instanceof Object
    var t2 = obj2[attr] instanceof Object
    if (t1 && t2) {
      return diff(obj1[attr], obj2[attr])
    } else if (obj1[attr] !== obj2[attr]) {
      return false
    }
  }
  return true
}
/**
 * 设置灰度模式
 */
export const toggleGrayMode = (status) => {
  if (status) {
    document.body.className = document.body.className + ' grayMode'
  } else {
    document.body.className = document.body.className.replace(' grayMode', '')
  }
}
/**
 * 设置主题
 */
export const setTheme = (name) => {
  document.body.className = name
}

/**
 *加密处理
 */
export const encryption = (params) => {
  let {
    data,
    type,
    param,
    key
  } = params
  const result = JSON.parse(JSON.stringify(data))
  if (type === 'Base64') {
    param.forEach(ele => {
      result[ele] = btoa(result[ele])
    })
  } else {
    param.forEach(ele => {
      var data = result[ele]
      key = CryptoJS.enc.Latin1.parse(key)
      var iv = key
      // 加密
      var encrypted = CryptoJS.AES.encrypt(
        data,
        key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
      })
      result[ele] = encrypted.toString()
    })
  }
  return result
}

/**
 * 递归寻找子类的父类
 */

export const findParent = (menu, id) => {
  for (let i = 0; i < menu.length; i++) {
    if (menu[i].children.length != 0) {
      for (let j = 0; j < menu[i].children.length; j++) {
        if (menu[i].children[j].id == id) {
          return menu[i]
        } else {
          if (menu[i].children[j].children.length != 0) {
            return findParent(menu[i].children[j].children, id)
          }
        }
      }
    }
  }
}

/**
 * 动态插入css
 */

export const loadStyle = url => {
  const link = document.createElement('link')
  link.type = 'text/css'
  link.rel = 'stylesheet'
  link.href = url
  const head = document.getElementsByTagName('head')[0]
  head.appendChild(link)
}
/**
 * 判断路由是否相等
 */
export const isObjectValueEqual = (a, b) => {
  let result = true
  Object.keys(a).forEach(ele => {
    const type = typeof (a[ele])
    if (type === 'string' && a[ele] !== b[ele]) result = false
    else if (type === 'object' && JSON.stringify(a[ele]) !== JSON.stringify(b[ele])) result = false
  })
  return result
}
/**
 * 根据字典的value显示label
 */
export const findByvalue = (dic, value) => {
  let result = ''
  if (validatenull(dic)) return value
  if (typeof (value) === 'string' || typeof (value) === 'number' || typeof (value) === 'boolean') {
    let index = 0
    index = findArray(dic, value)
    if (index != -1) {
      result = dic[index].label
    } else {
      result = value
    }
  } else if (value instanceof Array) {
    result = []
    let index = 0
    value.forEach(ele => {
      index = findArray(dic, ele)
      if (index != -1) {
        result.push(dic[index].label)
      } else {
        result.push(value)
      }
    })
    result = result.toString()
  }
  return result
}
/**
 * 根据字典的value查找对应的index
 */
export const findArray = (dic, value) => {
  for (let i = 0; i < dic.length; i++) {
    if (dic[i].value == value) {
      return i
    }
  }
  return -1
}
/**
 * 生成随机len位数字
 */
export const randomLenNum = (len, date) => {
  let random = ''
  random = Math.ceil(Math.random() * 100000000000000).toString().substr(0, len || 4)
  if (date) random = random + Date.now()
  return random
}
/**
 * 打开小窗口
 */
export const openWindow = (url, title, w, h) => {
  // Fixes dual-screen position                            Most browsers       Firefox
  const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left
  const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top

  const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width
  const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height

  const left = ((width / 2) - (w / 2)) + dualScreenLeft
  const top = ((height / 2) - (h / 2)) + dualScreenTop
  const newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left)

  // Puts focus on the newWindow
  if (window.focus) {
    newWindow.focus()
  }
}

/**
 *  <img> <a> src 处理
 * @returns {PromiseLike<T | never> | Promise<T | never>}
 */
export function handleImg(fileName, id) {
  return validatenull(fileName) ? null : request({
    url: '/file/file/' + fileName,
    method: 'get',
    responseType: 'blob'
  }).then((response) => { // 处理返回的文件流
    let blob = response.data;
    let img = document.getElementById(id);
    img.src = URL.createObjectURL(blob);
    window.setTimeout(function () {
      window.URL.revokeObjectURL(blob)
    }, 0)
  })
}

/**
 *  <img> <a> src 处理
 * @returns {PromiseLike<T | never> | Promise<T | never>}
 */
export function getImgURL(fileName) {
  return new Promise(resolve => {
    validatenull(fileName) ? null : request({
      url: '/file/file/' + fileName,
      method: 'get',
      responseType: 'blob'
    }).then((response) => { // 处理返回的文件流
      let blob = response.data;
      resolve(URL.createObjectURL(blob));
    })
  })
}

/**
 * 根据文件名下载文件
 * @param fileName 文件名：桶名+连接
 * @param downName  下载的名字
 */
export function download(fileName, downName) {
  return validatenull(fileName) ? null : request({
    url: '/file/file/' + fileName,
    method: 'get',
    responseType: 'blob'
  }).then((response) => { // 处理返回的文件流
    let blob = response.data;
    var a = document.createElement('a');
    var url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = downName;
    a.click();
    window.URL.revokeObjectURL(url);
  })
}


export function getFileLink(fileName) {
  return request({
    url: '/file/file/link/' + fileName,
    method: 'get',
  })
}

// 根据对象数组 信息获取链接数组
export function getFileLinkList(data) {
  return request({
    url: '/file/file/linklist',
    method: 'post',
    data: data,
  })
}

export const filterForm = (form) => {
  let obj = {};
  Object.keys(form).forEach(ele => {
    if (!validatenull(form[ele])) {
      obj[ele] = form[ele]
    }
  });
  return obj;
}

export const vaildData = (val, dafult) => {
  if (typeof val === 'boolean') {
    return val;
  }
  return !validatenull(val) ? val : dafult;
};

export const getFilelogoByType = (val) => {
  let base = "/img/filelogo/";
  if (val == null) return base + "folder.png";
  else val = val.replace(".", "")
  if (val == 'jpg' || val == 'jpeg') base += "jpeg";
  else if (val == 'png') base += "png";
  else if (val == 'gif') base += "gif";
  else if (val == 'exe') base += "exe";

  else if (val == 'mp3') base += "mp3";
  else if (val == 'wav') base += "wav";

  else if (val == 'mkv') base += "mkv";
  else if (val == 'mp4') base += "mp4";
  else if (val == 'avi') base += "avi";
  else if (val == 'rmvb') base += "rmvb";

  else if (val == 'pdf') base += "pdf";
  else if (val == 'doc' || val == 'docx') base += "doc";
  else if (val == 'xls' || val == 'xlsx') base += "xls";
  else if (val == 'ppt' || val == 'pptx') base += "ppt";

  else if (val == 'rar' || val == 'zip') base += "rar";
  else if (val == 'psd' || val == 'psd') base += "psd";
  else if (val == 'txt' || val == 'md') base += "text";
  else base += "other";
  base += ".png";
  return base;
};

export const getUUID = () => {
  let now = new Date();
  let uuid = "zykj-" + now.getDay() + now.getHours() + now.getMinutes() +
    now.getSeconds() + now.getMilliseconds() + Math.round(Math.random() * 10000);
  return uuid;
}


export const timeStamp2String = timestamp => {
  let add0 = function (m) {
    return m < 10 ? '0' + m : m
  }

  var time = new Date(timestamp) //先将时间戳转为Date对象，然后才能使用Date的方法
  var year = time.getFullYear(),
    month = time.getMonth() + 1, //月份是从0开始的
    day = time.getDate(),
    hour = time.getHours(),
    minute = time.getMinutes(),
    second = time.getSeconds()
  //add0()方法在后面定义
  return year + '-' + add0(month) + '-' + add0(day) + ' ' + add0(hour) + ':' + add0(minute) + ':' + add0(second)
};

export const String2timeStamp = dateStr => {
  dateStr = dateStr.substring(0, 19);
  dateStr = dateStr.replace(/-/g, '/');
  var timestamp = new Date(dateStr).getTime();
  return timestamp;
};
/**
 * 删除数组元素
 * @param item,arr
 * @returns arr
 */
export const spliceArray = (item, arr) => {
  let index = arr.findIndex(_item => _item === item);
  if (index > -1) {
    arr.splice(index, 1);
  }
  return arr;
};

/**
 * 打印预览
 * @param filename 文件名称
 * @param contentType contentType
 * @param token token
 */
export function printPreview(filename, contentType, token) {
  return new Promise((resolve, reject) => {
    if (!filename) {
      reject("error");
    } else {
      let url = `file/file/preview/打印预览?filename=${filename}&contenttype=${contentType}&token=${token}`;
      window.open(url);
      resolve()
    }
  });
};

export function initScroll() {
  var instances = $("*").overlayScrollbars();
  $.each(instances, function (index, instance) {
    //check for validity
    if (instance !== undefined) {
      //鼠标离开自动隐藏
      instance.options("scrollbars.autoHide", 'leave')
    }
  });
}
/**
 * 拼接流程链接  不拼接域名
 */
export function hasProcessStrUrl(fileList) {
  if (fileList.length == 0) {
    return "";
  }
  //处理文件
  let str = "";
  if (fileList.length > 0) {
    for (let i = 0; i < fileList.length; i++) {
      if (i == fileList.length - 1) {
        str += fileList[i].fileOriginName + "," + fileList[i].context;
        return str;
      }
      str += fileList[i].fileOriginName + "," + fileList[i].context + ";"
    }
  }
  return str;
}
/**
 * 解析流程链接 没有域名
 */
export function hasProcessStrUrlResolve(strUrl) {
  let backList = [];
  if (!strUrl) {
    return backList;
  }
  //处理文件
  let url = sessionStorage.getItem("process-file");
  let array = strUrl.split(";");
  if (array.length == 0) {
    return backList;
  }
  let filterList = array.filter(t => !!t);
  let obj = {};
  for (let i = 0; i < filterList.length; i++) {
    if (filterList[i]) {
      let arr = filterList[i].split(",");
      obj.name = arr[0];
      obj.value = url + arr[1];
      obj.uid = i + 1000000;
      obj.fileOriginName = arr[0];
      obj.context = arr[1];
      if (obj.name || obj.value) {
        backList.push(obj);
        obj = {}
      }
    }
  }
  return backList;
}

// 函数参数必须是字符串，因为二代身份证号码是十八位，
// 而在javascript中，十八位的数值会超出计算范围，
// 造成不精确的结果，导致最后两位和计算的值不一致，从而该函数出现错误。
export function checkIDCard(idcode) {
  // 加权因子
  var weight_factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
  // 校验码
  var check_code = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];

  var code = idcode + "";
  var last = idcode[17]; //最后一位
  if (last == 'x') {
    last = last.toUpperCase();
  }
  var seventeen = code.substring(0, 17);

  // ISO 7064:1983.MOD 11-2
  // 判断最后一位校验码是否正确
  var arr = seventeen.split("");
  var len = arr.length;
  var num = 0;
  for (var i = 0; i < len; i++) {
    num = num + arr[i] * weight_factor[i];
  }

  // 获取余数
  var resisue = num % 11;
  var last_no = check_code[resisue];
  // 格式的正则
  // 正则思路
  /*
  第一位不可能是0
  第二位到第六位可以是0-9
  第七位到第十位是年份，所以七八位为19或者20
  十一位和十二位是月份，这两位是01-12之间的数值
  十三位和十四位是日期，是从01-31之间的数值
  十五，十六，十七都是数字0-9
  十八位可能是数字0-9，也可能是X
  */
  var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X]|[x])$/;

  // 判断格式是否正确
  var format = idcard_patter.test(idcode);

  // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
  return last === last_no && format ? true : false;
}

/**
 * 按钮悬浮控制
 */
export function initNoticeSidebar(self) {
  let obj = self.$refs.osComponentRef;
  self.options = {
    callbacks: {
      onScroll: function () {
        let positionY = obj.osInstance().scroll().position.y; //内容滚动的距离
        if (positionY > 0) {
          $(".notice-sidebar").addClass('notice-fixed');
        } else {
          $(".notice-sidebar").removeClass('notice-fixed');
        }
      }
    }
  }
}

/**
 * 流程默认缓存
 */
export function defaultProcessApplyInfo(self, user, deptInfo) {
  self.applyUserName = user.nickName;
  self.applyUserId = user.userId;
  self.applyDepartmentId = deptInfo.deptId;
  self.applyDepartmentName = deptInfo.deptName;
  return self;
}
/**
 * 处理签字意见
 */
export function handleSubmitComment(processApproval, formComonent) {
  processApproval.comment = formComonent.comment;
  processApproval.commentFile = formComonent.commentFile;
  processApproval.fileShowList = hasProcessStrUrlResolve(formComonent.commentFile);
  processApproval.fileList = hasProcessStrUrlResolve(formComonent.commentFile);

  return processApproval;
}
//获取唯一ID
export function generateUUID() {
  var d = new Date().getTime();
  if (window.performance && typeof window.performance.now === "function") {
    d += performance.now(); //use high-precision timer if available
  }
  var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = (d + Math.random() * 16) % 16 | 0;
    d = Math.floor(d / 16);
    return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
  });
  return uuid;
}

export function validateFile(value) {
  var fileStr = "";

  if (value.length > 0) {
    for (var i = 0; i < value.length; i++) {
      if (fileStr === "") {
        fileStr = value[i].fileOriginName + "," + value[i].context;
      } else {
        fileStr = fileStr + ";" + value[i].fileOriginName + "," + value[i].context;
      }
    }
  }

  return fileStr;
}
