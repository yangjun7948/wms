import request from '@/utils/request'

// 查询库存预警
export function stockwarnList(query) {
  return request({
    url: '/warehouse/warehouseStock/warn',
    method: 'get',
    params: query
  })
}