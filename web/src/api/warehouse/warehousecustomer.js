import request from '@/utils/request'

// 查询客户信息列表
export function listWarehousecustomer(query) {
  return request({
    url: '/warehouse/warehousecustomer/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getWarehousecustomer(customerId) {
  return request({
    url: '/warehouse/warehousecustomer/' + customerId,
    method: 'get'
  })
}

// 新增客户信息
export function addWarehousecustomer(data) {
  return request({
    url: '/warehouse/warehousecustomer',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateWarehousecustomer(data) {
  return request({
    url: '/warehouse/warehousecustomer',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delWarehousecustomer(customerId) {
  return request({
    url: '/warehouse/warehousecustomer/' + customerId,
    method: 'delete'
  })
}
