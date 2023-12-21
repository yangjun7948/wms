import request from '@/utils/request'

// 查询供应商库列表
export function listSupplier(query) {
  return request({
    url: '/warehouse/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商库详细
export function getSupplier(supplierId) {
  return request({
    url: '/warehouse/supplier/' + supplierId,
    method: 'get'
  })
}

// 新增供应商库
export function addSupplier(data) {
  return request({
    url: '/warehouse/supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商库
export function updateSupplier(data) {
  return request({
    url: '/warehouse/supplier',
    method: 'put',
    data: data
  })
}

// 删除供应商库
export function delSupplier(supplierId) {
  return request({
    url: '/warehouse/supplier/' + supplierId,
    method: 'delete'
  })
}
