import request from '@/utils/request'

// 查询物料分类列表
export function listGoodscategory(query) {
  return request({
    url: '/warehouse/goodscategory/list',
    method: 'get',
    params: query
  })
}

// 查询物料分类详细
export function getGoodscategory(categoryId) {
  return request({
    url: '/warehouse/goodscategory/' + categoryId,
    method: 'get'
  })
}

// 新增物料分类
export function addGoodscategory(data) {
  return request({
    url: '/warehouse/goodscategory',
    method: 'post',
    data: data
  })
}

// 修改物料分类
export function updateGoodscategory(data) {
  return request({
    url: '/warehouse/goodscategory',
    method: 'put',
    data: data
  })
}

// 删除物料分类
export function delGoodscategory(categoryId) {
  return request({
    url: '/warehouse/goodscategory/' + categoryId,
    method: 'delete'
  })
}
// 物料分类树
export function categoryTreeSelect() {
  return request({
    url: '/warehouse/goodscategory/tree',
    method: 'get'
  })
}
