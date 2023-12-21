import request from '@/utils/request'

// 查询要货申请列表
export function listApply(query) {
  return request({
    url: '/warehouse/apply/list',
    method: 'get',
    params: query
  })
}

// 查询要货申请详细
export function getApply(applyReceiptId) {
  return request({
    url: '/warehouse/apply/' + applyReceiptId,
    method: 'get'
  })
}

// 新增要货申请
export function addApply(data) {
  return request({
    url: '/warehouse/apply',
    method: 'post',
    data: data
  })
}

// 修改要货申请
export function updateApply(data) {
  return request({
    url: '/warehouse/apply',
    method: 'put',
    data: data
  })
}
//  同意要货申请
export function agreeApply(data) {
  return request({
    url: '/warehouse/apply/agree',
    method: 'put',
    data: data
  })
}
//  拒绝要货申请
export function rejectApply(data) {
  return request({
    url: '/warehouse/apply/reject',
    method: 'put',
    data: data
  })
}
// 删除要货申请
export function delApply(applyReceiptId) {
  return request({
    url: '/warehouse/apply/' + applyReceiptId,
    method: 'delete'
  })
}
