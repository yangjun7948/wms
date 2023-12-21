import request from '@/utils/request'

// 查询出库单据列表
export function listOutreceipt(query) {
  return request({
    url: '/warehouse/outreceipt/list',
    method: 'get',
    params: query
  })
}

// 查询出库单据详细
export function getOutreceipt(warehouseOutReceiptId) {
  return request({
    url: '/warehouse/outreceipt/' + warehouseOutReceiptId,
    method: 'get'
  })
}

// 新增出库单据
export function addOutreceipt(data) {
  return request({
    url: '/warehouse/outreceipt',
    method: 'post',
    data: data
  })
}

// 修改出库单据
export function updateOutreceipt(data) {
  return request({
    url: '/warehouse/outreceipt',
    method: 'put',
    data: data
  })
}
// 审核出库单据
export function approveOutreceipt(data) {
  return request({
    url: '/warehouse/outreceipt/approve',
    method: 'post',
    data: data
  })
}
// 删除出库单据
export function delOutreceipt(warehouseOutReceiptId) {
  return request({
    url: '/warehouse/outreceipt/' + warehouseOutReceiptId,
    method: 'delete'
  })
}
// 打印入库单据
export function outReceiptPdf(warehouseInReceiptId) {
  return request({
    url: '/warehouse/outreceipt/pdf/' + warehouseInReceiptId,
    method: 'get',
    responseType: 'arraybuffer'
  })
}

