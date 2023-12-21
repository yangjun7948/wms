import request from '@/utils/request'

// 查询入库单据列表
export function listInreceipt(query) {
  return request({
    url: '/warehouse/inreceipt/list',
    method: 'get',
    params: query
  })
}

// 查询入库单据详细
export function getInreceipt(warehouseInReceiptId) {
  return request({
    url: '/warehouse/inreceipt/' + warehouseInReceiptId,
    method: 'get'
  })
}

// 新增入库单据
export function addInreceipt(data) {
  return request({
    url: '/warehouse/inreceipt',
    method: 'post',
    data: data
  })
}

// 修改入库单据
export function updateInreceipt(data) {
  return request({
    url: '/warehouse/inreceipt',
    method: 'put',
    data: data
  })
}
// 审核入库单据
export function approveInreceipt(data) {
  return request({
    url: '/warehouse/inreceipt/approve',
    method: 'post',
    data: data
  })
}

// 删除入库单据
export function delInreceipt(warehouseInReceiptId) {
  return request({
    url: '/warehouse/inreceipt/' + warehouseInReceiptId,
    method: 'delete'
  })
}
// 打印入库单据
export function inReceiptPdf(warehouseInReceiptId) {
  return request({
    url: '/warehouse/inreceipt/pdf/' + warehouseInReceiptId,
    method: 'get',
    responseType: 'arraybuffer'
  })
}

export function listTodoreceipt(type) {
  return request({
    url: '/warehouse/inreceipt/listTodoReceipt/'+type,
    method: 'get',
  })
}
