import request from '@/utils/request'
// 盘点单
export function listCheck(query) {
  return request({
    url: `/warehouse/check/list`,
    method: 'get',
    params: query
  })
}

// 新增盘点单
export function addCheckReceipt(data) {
  return request({
    url: '/warehouse/check',
    method: 'post',
    data: data
  })
}

// 修改盘点单
export function updateCheckReceipt(data) {
  return request({
    url: '/warehouse/check',
    method: 'put',
    data: data
  })
}
// 修改盘点单
export function delCheck(ids) {
  return request({
    url: '/warehouse/check/'+ids,
    method: 'delete'
  })
}
// 查询盘点单
export function getCheckReceipt(checkReceiptId) {
  return request({
    url: '/warehouse/check/' + checkReceiptId,
    method: 'get'
  })
}