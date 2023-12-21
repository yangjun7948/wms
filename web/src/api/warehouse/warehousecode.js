import request from '@/utils/request'

// 获取入库单据号
export function inReceiptCode(query) {
  return request({
    url: '/warehouse/warehousecode/inReceiptCode',
    method: 'get',
    params: query
  })
}
// 获取出库单据号
export function outReceiptCode(query) {
  return request({
    url: '/warehouse/warehousecode/outReceiptCode',
    method: 'get',
    params: query
  })
}
// 获取调拨单据号
export function transferCode(query) {
  return request({
    url: '/warehouse/warehousecode/transferCode',
    method: 'get',
    params: query
  })
}
// 获取盘点单据号
export function checkCode(query) {
  return request({
    url: '/warehouse/warehousecode/checkCode',
    method: 'get',
    params: query
  })
}