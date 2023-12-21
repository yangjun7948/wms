// @ts-nocheck
/** 未选 */
export const unCheckedStatus = 0
/** 半选 */
export const halfCheckedStatus = 1
/** 选中 */
export const isCheckedStatus = 2

/**
 * 深拷贝内容
 * @param originData 拷贝对象
 * @author crlang(https://crlang.com)
 */
export function deepClone(originData) {
  const type = Object.prototype.toString.call(originData)
  let data
  if (type === '[object Array]') {
    data = []
    for (let i = 0; i < originData.length; i++) {
      data.push(deepClone(originData[i]))
    }
  } else if (type === '[object Object]') {
    data = {}
    for (const prop in originData) {
      // eslint-disable-next-line no-prototype-builtins
      if (originData.hasOwnProperty(prop)) { // 非继承属性
        data[prop] = deepClone(originData[prop])
      }
    }
  } else {
    data = originData
  }
  return data
}

/**
 * 获取所有指定的节点
 * @param type
 * @param value
 * @author crlang(https://crlang.com)
 */
export function getAllNodes(list, type, value, packDisabledkey = true) {
  if (!list || list.length === 0) {
    return []
  }

  const res = []
  for (let i = 0; i < list.length; i++) {
    const item = list[i]
    if (item[type] === value) {
      if ((packDisabledkey && item.disabled) || !item.disabled) {
        res.push(item)
      }
    }
  }

  return res
}

/**
 * 获取所有指定的key值
 * @param type
 * @param value
 * @author crlang(https://crlang.com)
 */
export function getAllNodeKeys(list, type, value, packDisabledkey = true) {
  if (!list || list.length === 0) {
    return null
  }

  const res = []
  for (let i = 0; i < list.length; i++) {
    const item = list[i]
    if (item[type] === value) {
      if ((packDisabledkey && item.disabled) || !item.disabled) {
        res.push(item.key)
      }
    }
  }

  return res.length ? res : null
}

/**
 * 错误输出
 *
 * @param msg
 */
export function logError(msg, ...args) {
  console.error(`DaTree: ${msg}`, ...args)
}

const toString = Object.prototype.toString

export function is(val, type) {
  return toString.call(val) === `[object ${type}]`
}

/**
 * 是否对象(Object)
 * @param val

 */
export function isObject(val) {
  return val !== null && is(val, 'Object')
}

/**
 * 是否数字(Number)
 * @param val

 */
export function isNumber(val) {
  return is(val, 'Number')
}

/**
 * 是否字符串(String)
 * @param val

 */
export function isString(val) {
  return is(val, 'String')
}

/**
 * 是否函数方法(Function)
 * @param val

 */
export function isFunction(val) {
  return typeof val === 'function'
}

/**
 * 是否布尔(Boolean)
 * @param val

 */
export function isBoolean(val) {
  return is(val, 'Boolean')
}

/**
 * 是否数组(Array)
 * @param val

 */
export function isArray(val) {
  return val && Array.isArray(val)
}
