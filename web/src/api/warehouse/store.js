import request from '@/utils/request'

// 查询门店信息列表
export function listStore(query) {
  return request({
    url: '/warehouse/store/list',
    method: 'get',
    params: query
  })
}

// 查询门店信息详细
export function getStore(storeId) {
  return request({
    url: '/warehouse/store/' + storeId,
    method: 'get'
  })
}

// 新增门店信息
export function addStore(data) {
  return request({
    url: '/warehouse/store',
    method: 'post',
    data: data
  })
}

// 修改门店信息
export function updateStore(data) {
  return request({
    url: '/warehouse/store',
    method: 'put',
    data: data
  })
}

// 删除门店信息
export function delStore(storeId) {
  return request({
    url: '/warehouse/store/' + storeId,
    method: 'delete'
  })
}
