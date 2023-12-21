import request from '@/utils/request'

// 查询调拨单据列表
export function listTransfer(query) {
  return request({
    url: '/warehouse/transfer/list',
    method: 'get',
    params: query
  })
}

// 查询调拨单据详细
export function getTransfer(warehouseTransferReceiptId) {
  return request({
    url: '/warehouse/transfer/' + warehouseTransferReceiptId,
    method: 'get'
  })
}

// 新增调拨单据
export function addTransfer(data) {
  return request({
    url: '/warehouse/transfer',
    method: 'post',
    data: data
  })
}

// 修改调拨单据
export function updateTransfer(data) {
  return request({
    url: '/warehouse/transfer',
    method: 'put',
    data: data
  })
}

// 删除调拨单据
export function delTransfer(warehouseTransferReceiptId) {
  return request({
    url: '/warehouse/transfer/' + warehouseTransferReceiptId,
    method: 'delete'
  })
}

// 审核入库单据
export function approveTransferreceipt(data) {
  return request({
    url: '/warehouse/transfer/approve',
    method: 'post',
    data: data
  })
}
// 打印入库单据
export function transferReceiptPdf(receiptId) {
  return request({
    url: '/warehouse/transfer/pdf/' + receiptId,
    method: 'get',
    responseType: 'arraybuffer'
  })
}