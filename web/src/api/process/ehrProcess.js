/**
 * Created by junyang4 on 2021-03-18.
 */
import request from '@/utils/request'



/**
 * 新建流程列表
 * @param query
 */
export function processList(query) {
  return request({
    url: '/act/process/list',
    method: 'post',
    data: query
  })
}

/**
 * 流程树
 * @param query
 */
export function processTree(query) {
  return request({
    url: '/act/process/tree',
    method: 'post',
    data: query
  })
}

/**
 * 待办列表
 * @param query
 */
export function processTodo(query) {
  return request({
    url: '/act/process/listMyTodoFlow',
    method: 'post',
    data: query
  })
}

/**
 * 已办列表
 * @param query
 */
export function processDone(query) {
  return request({
    url: '/act/process/donelist',
    method: 'post',
    data: query
  })
}

/**
 * 办结列表
 * @param query
 */
export function processComplete(query) {
  return request({
    url: '/act/process/completedList',
    method: 'post',
    data: query
  })
}

/**
 * 我的请求
 * @param query
 */
export function processRequest(query) {
  return request({
    url: '/act/process/requestlist',
    method: 'post',
    data: query
  })
}

/**
 * 获取人员树
 * @param query
 */
export function getEmployeeTree(query) {
  return request({
    url: '/act/process/employeeTree',
    method: 'post',
    data: query
  })
}
/**
 * 获取部门树
 * @param query
 */
export function getDeptTree(query) {
  return request({
    url: '/act/process/deptTree',
    method: 'post',
    data: query
  })
}

/**
 * 发起流程
 * @param data
 */
export function startEhrProcess(data) {
  return request({
    url: '/act/process/startOrSaveProcess',
    method: 'post',
    data: data
  })
}

/**
 * 文件上传
 * @param data
 */
export function upload(data) {
  return request({
    url: '/ehr/community/process/upload',
    method: 'post',
    data: data
  })
}

/**
 * 获取流程详情
 * @param data
 */
export function getDingDingDetail(data) {
  return request({
    url: '/act/process/processNewDetail',
    method: 'post',
    data: data
  })
}

/**
 * 获取流程图
 * @param data
 */
export function getProcImage(data) {
  return request({
    url: '/act/process/showPicture',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
/**
 * 获取流程图
 * @param data
 */
export function getObjUrl(data) {
  return request({
    url: '/act/process/getObjUrl',
    method: 'post',
    data: data
  })
}

/**
 * 我的请求
 * @param query
 */
export function test(query) {
  return request({
    url: '/act/process/test',
    method: 'post',
    data: query
  })
}

/**
 * 流程撤回
 */
export function withdrawProcess(query) {
  return request({
    url: '/act/process/withdrawProcess',
    method: 'post',
    data: query
  })
}

/**
 * 提醒办理
 * @param data
 */
export function remindProcessHandle(data) {
  return request({
    url: '/act/process/remindProcessHandle',
    method: 'post',
    data: data
  })
}

/**
 *流程意见征询
 * @param data
 */
export function opinionSeek(data) {
  console.log(data)
  return request({
    url: '/act/process/opinionSeek',
    method: 'post',
    data: data
  })
}
/**
 *流程转发
 * @param data
 */
export function processForward(data) {
  return request({
    url: '/act/process/processForward',
    method: 'post',
    data: data
  })
}
/**
 *流程转办
 * @param data
 */
export function processTransfer(data) {
  return request({
    url: '/act/process/processTransfer',
    method: 'post',
    data: data
  })
}

/**
 * 流程审批
 */
export function examinationApproval(data) {
  return request({
    url: '/act/process/examinationApproval',
    method: 'post',
    data: data
  })
}

/**
 * 获取流程节点
 */
export function queryProcessBackNode(data) {
  return request({
    url: '/act/process/queryProcessBackNode',
    method: 'post',
    data: data
  })
}

/**
 * 退回至那个节点
 */
export function backProgress(data) {
  return request({
    url: '/act/process/backProgress',
    method: 'post',
    data: data
  })
}

/**
 * 删除流程
 */
export function deleteProcess(data) {
  return request({
    url: '/act/process/deleteProcess',
    method: 'post',
    data: data
  })
}

/**
 * 相关流程
 */
export function getProcessTaskList(data) {
  return request({
    url: '/act/process/getProcessTaskList',
    method: 'post',
    data: data
  })
}

/**
 * 获取字典表数据
 */
export function listDict(data) {
  return request({
    url: '/act/process/listDict',
    method: 'post',
    data: data
  })
}

/**
 * 获取职位数据
 */
export function getJobList() {
  return request({
    url: '/act/process/getJobList',
    method: 'post',
  })
}

/**
 * 审批人撤回
 */
export function approvalWithdraw(data) {
  return request({
    url: '/act/process/processApprovalWithdraw',
    method: 'post',
    data: data
  })
}

/**
 * 根据roleId获取流程code列表
 */
export function getProcessCodeByRoleId(roleId) {
  return request({
    url: '/act/process/getProcessCodeByRoleId/' + roleId,
    method: 'get'
  })
}

/**
 * 修改保存角色流程信息
 */
export function updateRoleProcessCode(data) {
  return request({
    url: '/act/process/updateRoleProcessCode',
    method: 'post',
    data: data
  })
}


export function downFileOrOpenFile(fileName, downName, token) {
  let arr = ['jpg', 'png', 'jpeg', 'pdf'];
  let fileExtension = fileName.split(".")[1];

  if (arr.indexOf(fileExtension) === -1) {
    downFileByFileName(fileName, downName);
  } else {
    let contentType = '';
    if ("pdf" === fileExtension) {
      contentType = "application/pdf; charset=UTF-8";
    } else {
      contentType = "image/" + fileExtension + "; charset=UTF-8";
    }

    let name = encodeURIComponent(downName);
    let url = `${process.env.VUE_APP_BASE_API}${fileName}`;
    window.open(url);
  }

}
//流程部署
export function deploy(data) {
  return request({
    url: '/act/process/deployProcess',
    method: 'post',
    data
  })
}
//更新流程
export function update(data) {
  return request({
    url: '/act/process/update',
    method: 'post',
    data
  })
}

export function processMonitor(data) {
  return request({
    url: '/act/process/processMonitor',
    method: 'post',
    data: data
  })
}
