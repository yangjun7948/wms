import request from '@/utils/request'

// 查询物品列表
export function listGoods(query) {
  return request({
    url: '/warehouse/goods/list',
    method: 'get',
    params: query
  })
}
// 查询仓库物品列表
export function listStockGoods(query) {
  return request({
    url: '/warehouse/goods/listStockGoods',
    method: 'get',
    params: query
  })
}

// 查询物品详细
export function getGoods(goodsId) {
  return request({
    url: '/warehouse/goods/' + goodsId,
    method: 'get'
  })
}

// 新增物品
export function addGoods(data) {
  return request({
    url: '/warehouse/goods',
    method: 'post',
    data: data
  })
}

// 修改物品
export function updateGoods(data) {
  return request({
    url: '/warehouse/goods',
    method: 'put',
    data: data
  })
}

// 删除物品
export function delGoods(goodsId) {
  return request({
    url: '/warehouse/goods/' + goodsId,
    method: 'delete'
  })
}
// 获取物品SN图片
export function snPreview(receiptId, goodsId) {
  return request({
    url: `/warehouse/goods/snPreview/${receiptId}/${goodsId}`,
    method: 'get',
    responseType: 'arraybuffer'
  })
}
// 获取物品SN图片
export function snCodePdf(receiptId, goodsId) {
  return request({
    url: `/warehouse/goods/snCodePdf/${receiptId}/${goodsId}`,
    method: 'get',
    responseType: 'arraybuffer'
  })
}
// 查询库存
export function listStockGoodsReport(query, page) {
  return request({
    url: `/warehouse/report/stock?pageNum=${page.pageNum}&pageSize=${page.pageSize}`,
    method: 'post',
    data: query
  })
}

