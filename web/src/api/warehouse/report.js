import request from '@/utils/request'
// 物品溯源码
export function listGoodsCode(query, page) {
  return request({
    url: `/warehouse/report/code?pageNum=${page.pageNum}&pageSize=${page.pageSize}`,
    method: 'post',
    data: query
  })
}
// 物品溯源码
export function goodsCodeDetail(sncode) {
  return request({
    url: `/warehouse/report/code/${sncode}`,
    method: 'get'
  })
}
