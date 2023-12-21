package com.xiaoyai.activiti.api.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.common.dto.process.ProcessCreateRequest;
import com.xiaoyai.activiti.common.dto.process.ProcessDeleteReqParam;
import com.xiaoyai.activiti.common.dto.process.ProcessWithdrawReqParam;
import com.xiaoyai.activiti.core.service.ProcessService;
import com.xiaoyai.common.activiti.api.model.activiti.ActAllProc;
import com.xiaoyai.common.activiti.api.model.activiti.dto.*;

import com.xiaoyai.common.activiti.core.constant.CommonConstants;
import com.xiaoyai.common.activiti.core.constant.ProcessConstant;
import com.xiaoyai.common.activiti.core.constant.SecurityConstants;
import com.xiaoyai.common.activiti.core.util.BeanConvertUtil;
import com.xiaoyai.common.activiti.core.util.R;
import com.xiaoyai.common.annotation.Anonymous;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author junyang
 * @date 2021-10-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/act/process")
@Tag(name = "仓库管理APP端接口")
public class ProcessController {

    ProcessService processService;



    /**
     * 获取所有流程
     */
    @ApiOperation("获取所有流程")
    @RequestMapping(value = "/queryProcesses", method = RequestMethod.POST)
    List<ProcessDto> queryProcesses(@RequestBody Map map) {
        return processService.queryProcesses(map);
    }

    /**
     * 获取我的待办
     */
    @ApiOperation("获取我的待办")
    @RequestMapping(value = "/listMyTodoFlow", method = RequestMethod.POST)
    MyProcessDto listMyTodoFlow(@RequestBody ProcessToDoListParamDto paramDto) {
        MyProcessDto myProcessDto;
        myProcessDto = processService.listMyTodoFlow(paramDto);
        return myProcessDto;
    }

    /**
     * 流程审批
     */

    @ApiOperation("流程审批")
    @RequestMapping(value = "/examinationApproval", method = RequestMethod.POST)
    String processApproval(@RequestBody ProcessCompleteParamDto processCompleteParamDto) {
        return processService.processApproval(processCompleteParamDto);
    }

    /**
     * 流程详情
     *
     * @param reqParam
     * @return
     */
    @ApiOperation("流程详情")
    @RequestMapping(value = "/processNewDetail", method = RequestMethod.POST)
    R processNewDetail(@RequestBody JSONObject reqParam) {
        return new R<>(processService.processNewDetail(reqParam));
    }

    /**
     * 获取流程图
     */
    @ApiOperation("获取流程图")
    @RequestMapping(value = "/showPicture", method = RequestMethod.POST)
    void searchProcessPicture(@RequestBody Map map, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream; charset=UTF-8");
        byte[] picByte = processService.searchProcessPicture(map);
        IoUtil.write(response.getOutputStream(), true, picByte);
    }

    /**
     * 获取我的已办
     */
    @ApiOperation("获取我的已办")
    @RequestMapping(value = "/donelist", method = RequestMethod.POST)
    MyProcessDto listMyDnoeProcess(@RequestBody ProcessDoneReqParamDto paramDto) {
        MyProcessDto myProcessDto;
        myProcessDto = processService.listMyDnoeProcess(paramDto);
        return myProcessDto;
    }

    /**
     * 开启流程
     */
    @ApiOperation("开启流程")
    @RequestMapping(value = "/saveFlow", method = RequestMethod.POST)
    String saveFlow(@RequestBody Map map) {
        return processService.saveFlow(map);
    }

    /**
     * 获取我的请求
     */
    @ApiOperation("获取我的请求")
    @RequestMapping(value = "/requestlist", method = RequestMethod.POST)
    MyProcessDto listMyApplyProcess(@RequestBody ProcessMyRequestListParamDto paramDto) {
        MyProcessDto myProcessDto = processService.listMyApplyProcess(paramDto);
        return myProcessDto;
    }

    /**
     * 回退流程
     */
    @ApiOperation("回退流程")
    @RequestMapping(value = "/backProgress", method = RequestMethod.POST)
    String backFlow(@RequestBody ProcessBackFlowParamDto processCompleteParamDto) {
        return processService.backFlow(processCompleteParamDto);
    }

    /**
     * 流程状态
     */
    @ApiOperation("流程状态")
    @RequestMapping(value = "/listProcessStatus", method = RequestMethod.POST)
    String listProcessStatus(@RequestBody Map map) {
        return processService.listProcessStatus(map);
    }

    /**
     * 流程发起或保存
     */
    @ApiOperation("流程发起或保存")
    @RequestMapping(value = "/startOrSaveProcess", method = RequestMethod.POST)
    String startOrSaveProcess(@RequestBody JSONObject request) {
        return processService.startOrSaveProcess(request);
    }

    /**
     * 发起人自己流程撤回
     */
    @ApiOperation("发起人自己流程撤回")
    @RequestMapping(value = "/withdrawProcess", method = RequestMethod.POST)
    String processWithdraw(@RequestBody ProcessWithdrawReqParam request) {
        return processService.processWithdraw(request);
    }

    /**
     * 流程办结列表
     */
    @ApiOperation("流程办结列表")
    @RequestMapping(value = "/completedList", method = RequestMethod.POST)
    MyProcessDto completedList(@RequestBody ProcessCompletedListParamDto paramDto) {
        MyProcessDto myProcessDto = processService.listCompletedProcess(paramDto);
        return myProcessDto;
    }

    /**
     * 查询流程监控
     *
     * @param reqParam
     * @return
     */
    @RequestMapping(value = "/queryProcessMonitorList", method = RequestMethod.POST)
    ProcessMonitorVo queryProcessMonitorList(@RequestBody ProcessMonitorReqParam reqParam) {
        return processService.queryProcessMonitorList(reqParam);
    }

    /**
     * 流程提醒流程
     */
    @RequestMapping(value = "/startRemindProcess", method = RequestMethod.POST)
    String startRemindProcess(@RequestBody ProcessCreateRequest request) {
        return processService.startRemindProcess(request);
    }

    /**
     * 流程可退结点列表查询
     */
    @RequestMapping(value = "/queryProcessBackNode", method = RequestMethod.POST)
    String queryProcessBackNodeList(@RequestBody ProcessBackNodeListReqParam request) {
        return processService.queryProcessBackNodeList(request);
    }

    /**
     * 流程删除
     */
    @ApiOperation("流程删除")
    @RequestMapping(value = "/deleteProcess", method = RequestMethod.POST)
    String deleteProcess(@RequestBody ProcessDeleteReqParam request) {
        return processService.deleteProcess(request);
    }

    /**
     * 流程撤回审批
     */
    @ApiOperation("流程撤回审批")
    @RequestMapping(value = "/processApprovalWithdraw", method = RequestMethod.POST)
    String processApprovalWithdraw(@RequestBody ProcessApprovalWithdrawReqParam request) {
        return processService.processApprovalWithdraw(request);
    }

    /**
     * 流程转发
     */
    @ApiOperation("流程转发")
    @RequestMapping(value = "/processForward", method = RequestMethod.POST)
    String processForward(@RequestBody ProcessForwardReqParam request) {
        return processService.processForward(request);
    }

    /**
     * 流程部署
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/deployProcess", method = RequestMethod.POST)
    boolean deployProcess(@RequestBody ProcessDeployReqParam request) {
        return processService.deployProcess(request);
    }

    /**
     * 流程意见征询
     */
    @ApiOperation("流程意见征询")
    @RequestMapping(value = "/opinionSeek", method = RequestMethod.POST)
    String opinionSeek(@RequestBody ProcessOpinionSeekReqParam request) {
        return processService.opinionSeek(request);
    }

    /**
     * 流程转办
     */
    @ApiOperation("流程转办")
    @RequestMapping(value = "/processTransfer", method = RequestMethod.POST)
    String processTransfer(@RequestBody ProcessTransferReqParam request) {
        return processService.processTransfer(request);
    }


    /**
     * 流程提醒处理
     */
    @RequestMapping(value = "/remindProcessHandle", method = RequestMethod.POST)
    String remindProcessHandle(@RequestBody String processInstanceId) {
        return processService.remindProcessHandle(processInstanceId);
    }

    /**
     * 判断用户是不是当前任务处理者
     */
    @ApiOperation("判断用户是不是当前任务处理者")
    @RequestMapping(value = "/getUserEditViewAuthority", method = RequestMethod.POST)
    List<ActFormPermission> getUserEditViewAuthority(@RequestBody Map info) {
        return processService.getUserEditViewAuthority(info);
    }

    /**
     * 流程转发
     */
    @ApiOperation("流程转发")
    @RequestMapping(value = "/resignationForwarding", method = RequestMethod.POST)
    void resignationForwarding(@RequestBody JSONObject object) {
        processService.resignationForwarding(object);
    }

    /**
     * 获取相关流程
     */
    @ApiOperation("获取相关流程")
    @RequestMapping(value = "/getEhrProcessTaskList", method = RequestMethod.POST)
    String getEhrProcessTaskList(@RequestBody JSONObject object) {
        return processService.getEhrProcessTaskList(object);
    }

    /**
     * 添加新用户
     */
    @RequestMapping(value = "/addNewStaff", method = RequestMethod.POST)
    void addNewStaff(@RequestBody JSONObject object) {
        processService.addNewStaff(object);
    }

    /**
     * 流程超时未处理提醒
     */
    @RequestMapping(value = "/userProcessOvertimeHandleRemind", method = RequestMethod.POST)
    void userProcessOvertimeHandleRemind() {
        processService.userProcessOvertimeHandleRemind();
    }

    /**
     * 获取流程树
     *
     * @return
     */
    @ApiOperation("获取流程树")
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    R tree() {
        ProcessListReqParamDto processListReqParamDto = new ProcessListReqParamDto();
        processListReqParamDto.setTableName("act_all_proc");
        processListReqParamDto.setTableColumns("type");
        List<ProcessDto> processDtos = processService.queryProcesses(BeanConvertUtil.beanCovertMap(processListReqParamDto));
        Map<String, List<ProcessDto>> dataMap = processDtos.stream().collect(Collectors.groupingBy(ProcessDto::getType));
        Iterator<Map.Entry<String, List<ProcessDto>>> it = dataMap.entrySet().iterator();
        List<ProcessListDto> resultList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, List<ProcessDto>> entry = it.next();
            List<ProcessDto> vos = entry.getValue();
            ProcessListDto processListDto = new ProcessListDto();
            processListDto.setType(vos.get(0).getType());
            processListDto.setProcTypeName(vos.get(0).getProcTypeName());
            processListDto.setProcessDtoList(vos);
            resultList.add(processListDto);
        }
        return new R<>(resultList);
    }

    /**
     * 获取用户流程列表
     *
     * @return
     */
    @ApiOperation("获取用户流程列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    R list() {
        List<ProcessListDto> processListDto = processService.getUserProcessList();
        return new R<>(processListDto);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    R update(@RequestBody ActAllProc proc) {
        return new R<>(processService.updateProc(proc));
    }

    /**
     * 获取用户所有流程列表
     *
     * @return
     */
    @ApiOperation("获取用户所有流程列表")
    @RequestMapping(value = "/alllist", method = RequestMethod.POST)
    R alllist() {
        List<ProcessDto> processListDto = processService.getUserAllProcessList();
        return new R<>(processListDto);
    }

    /**
     * 根据角色id获取流程id列表
     *
     * @param roleId 角色id
     * @return 流程id列表
     */
    @ApiOperation("根据角色id获取流程id列表")
    @GetMapping("/getProcessCodeByRoleId/{roleId}")
    R getProcessCodeByRoleId(@PathVariable("roleId") String roleId) {
        return new R<>(processService.getProcessCodeByRoleId(roleId));
    }

    /**
     * 修改保存角色流程信息
     *
     * @param data 修改参数
     */
    @PostMapping("/updateRoleProcessCode")
    R updateRoleProcessCode(@RequestBody RoleProcessCodeDto data) {
        return new R<>(processService.updateRoleProcessCode(data));
    }

    /**
     * 流程监控
     *
     * @param reqParam
     * @return
     */
    @PostMapping("/processMonitor")
    R getProcessMonitor(@RequestBody ProcessMonitorReqParam reqParam) {
        return new R<>(processService.getProcessMonitor(reqParam));
    }

    /**
     * 组合智慧社区和ehr流程
     *
     * @param processDto
     * @param dto
     * @return
     */
    String combineProcess(MyProcessDto processDto, MyProcessDto dto) {
        if (dto.getMyProcessApplyDtos() == null) {
            dto.setMyProcessApplyDtos(new ArrayList<>());
        }
        processDto.getMyProcessApplyDtos().addAll(dto.getMyProcessApplyDtos());
        processDto.setTotal(processDto.getTotal() + dto.getTotal());
        processDto.setRecords(dto.getRecords() > processDto.getRecords() ? dto.getRecords() : processDto.getRecords());
        return JSONObject.toJSONString(processDto);
    }

    /**
     * 处理智慧社区流程返回对象
     *
     * @param r
     * @return
     */
    MyProcessDto doProcessResult(R r) {
        MyProcessDto myProcessDto;
        if (CommonConstants.SUCCESS.equals(r.getCode())) {
            String _j = (String) r.getData();
            myProcessDto = JSONObject.parseObject(_j, MyProcessDto.class);
        } else {
            myProcessDto = new MyProcessDto();
        }
        if (myProcessDto.getMyProcessApplyDtos() == null) {
            myProcessDto.setMyProcessApplyDtos(new ArrayList<>());
        }
        return myProcessDto;
    }
}
