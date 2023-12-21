import request from '@/utils/request'

// 查询 物品出库明细列表
export function listOutReceiptDetail(query) {
  return request({
    url: '/warehouse/outReceiptDetail/list',
    method: 'get',
    params: query
  })
}

// 查询 物品出库明细详细
export function getOutReceiptDetail(outReceiptDetailId) {
  return request({
    url: '/warehouse/outReceiptDetail/' + outReceiptDetailId,
    method: 'get'
  })
}

// 新增 物品出库明细
export function addOutReceiptDetail(data) {
  return request({
    url: '/warehouse/outReceiptDetail',
    method: 'post',
    data: data
  })
}

// 修改 物品出库明细
export function updateOutReceiptDetail(data) {
  return request({
    url: '/warehouse/outReceiptDetail',
    method: 'put',
    data: data
  })
}

// 删除 物品出库明细
export function delOutReceiptDetail(outReceiptDetailId) {
  return request({
    url: '/warehouse/outReceiptDetail/' + outReceiptDetailId,
    method: 'delete'
  })
}
