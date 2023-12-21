package com.xiaoyai.web.controller.api;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.annotation.Anonymous;
import com.xiaoyai.common.annotation.CheckHeaderAuth;
import com.xiaoyai.common.annotation.Log;
import com.xiaoyai.common.constant.Constants;
import com.xiaoyai.common.core.controller.BaseController;
import com.xiaoyai.common.core.domain.AjaxResult;
import com.xiaoyai.common.core.domain.R;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.core.domain.model.LoginBody;
import com.xiaoyai.common.core.domain.model.LoginUser;
import com.xiaoyai.common.core.page.TableDataInfo;
import com.xiaoyai.common.enums.BusinessType;
import com.xiaoyai.common.enums.warehouse.ApplyState;
import com.xiaoyai.common.enums.warehouse.ReceiptState;
import com.xiaoyai.common.utils.SecurityUtils;
import com.xiaoyai.framework.web.service.SysLoginService;
import com.xiaoyai.framework.web.service.SysPermissionService;
import com.xiaoyai.framework.web.service.TokenService;
import com.xiaoyai.system.service.ISysConfigService;
import com.xiaoyai.system.service.ISysUserService;
import com.xiaoyai.warehouse.domain.*;
import com.xiaoyai.warehouse.domain.dto.*;
import com.xiaoyai.warehouse.domain.vo.WarehouseInReceiptVo;
import com.xiaoyai.warehouse.domain.vo.WarehouseOutReceiptVo;
import com.xiaoyai.warehouse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author junyang
 * @date 2023/9/17
 */
@Tag(name = "仓库管理APP端接口")
@RestController
@RequestMapping("/warehouse/app")
@CheckHeaderAuth()
public class WarehouseAppController extends BaseController {

    @Autowired
    private SysLoginService loginService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IWarehouseInReceiptService iWarehouseInReceiptService;
    @Autowired
    private IWarehouseOutReceiptService iWarehouseOutReceiptService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private IWarehouseGoodsService iWarehouseGoodsService;
    @Autowired
    private IWarehouseApplyReceiptService iWarehouseApplyReceiptService;
    @Autowired
    private IWarehouseCodeService iWarehouseCodeService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IWarehouseService warehouseService;
    @Autowired
    IWarehouseReportService iWarehouseReportService;
    @Autowired
    ISysConfigService iSysConfigService;
    @Autowired
    private IWarehouseApplyReceiptService warehouseApplyReceiptService;
    @Autowired
    private IWarehouseManagerService iWarehouseManagerService;
    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    @Anonymous()
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.appLogin(loginBody.getUsername(), loginBody.getPassword(), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 退出登录
     *
     * @param
     * @return 结果
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    @Anonymous()
    public AjaxResult logout(HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success();
        loginService.appLogout(tokenService.getToken(request));
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation("用户信息")
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        user.setPassword("");
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 重置密码
     */
    @ApiOperation("修改密码密码")
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 获取待扫码单据
     *
     * @return
     */
    @ApiOperation("获取待扫码入库单")
    @GetMapping("/inReceiptScanList")
    public TableDataInfo getInReceiptScanList() {
        startPage();
        WarehouseInReceipt warehouseInReceipt = new WarehouseInReceipt();
        warehouseInReceipt.setState(ReceiptState.SCAN.getCode());
        PageInfo<WarehouseInReceiptVo> list = iWarehouseInReceiptService.getInReceiptScanList(warehouseInReceipt);
        return getDataTable(list);
    }

    /**
     * 获取待扫码单据
     *
     * @return
     */
    @ApiOperation("获取待扫码入库单据明细")
    @GetMapping("/inReceiptScanDetail/{receiptId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiptId", value = "入库单ID", required = true, paramType = "path")
    })
    public AjaxResult getInReceiptScanDetail(@PathVariable("receiptId") Long receiptId) {

        return AjaxResult.success(iWarehouseInReceiptService.getInReceiptScanDetail(receiptId));

    }

    /**
     * 物品扫码入库提交
     *
     * @return
     */
    @ApiOperation("物品扫码入库提交")
    @PostMapping("/inReceiptScanSave")
    public AjaxResult saveInReceiptScanDetail(@RequestBody WarehouseInReceiptVo warehouseInReceiptVo) throws Exception {
        return AjaxResult.success(iWarehouseInReceiptService.saveInReceiptScanDetail(warehouseInReceiptVo));
    }

    @ApiOperation("获取待扫码出库单")
    @GetMapping("/outReceiptScanList")
    public TableDataInfo getOutReceiptScanList() {
        startPage();
        WarehouseOutReceipt outReceipt  =new WarehouseOutReceipt();
        outReceipt.setState(ReceiptState.SCAN.getCode());
        PageInfo<WarehouseOutReceiptVo> list = iWarehouseOutReceiptService.getOutReceiptScanList(outReceipt);
        return getDataTable(list);
    }

    @ApiOperation("获取待扫码出库单明细")
    @GetMapping("/outReceiptScanDetail/{receiptId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiptId", value = "出库单ID", required = true, paramType = "path")
    })
    public AjaxResult getOutReceiptScanList(@PathVariable("receiptId") Long receiptId) {

        return AjaxResult.success(iWarehouseOutReceiptService.getOutReceiptScanDetail(receiptId));

    }

    /**
     * 物品扫码出库提交
     *
     * @return
     */
    @ApiOperation("物品扫码出库提交")
    @PostMapping("/outReceiptScanSave")
    public AjaxResult saveOutReceiptScanDetail(@RequestBody WarehouseOutReceiptVo warehouseOutReceiptVo) throws Exception {
        return AjaxResult.success(iWarehouseOutReceiptService.saveOutReceiptScanDetail(warehouseOutReceiptVo));
    }

    /**
     * 获取物品列表
     *
     * @return
     */
    @ApiOperation("获取物品列表")
    @PostMapping("/getGoodsList")
    public TableDataInfo getGoodsList(@RequestBody WarehouseGoodsQueryDto warehouseGoods) {
        startPage();
        PageInfo<WarehouseGoodsDto> list = iWarehouseGoodsService.selectWarehouseGoodsVoList(warehouseGoods);
        return getDataTable(list);
    }

    /**
     * 物品扫码入库提交
     *
     * @return
     */
    @ApiOperation("生成要货单号")
    @GetMapping("/applyCode")
    public AjaxResult applyCode() {
        return AjaxResult.success(iWarehouseCodeService.getApplyCode());

    }

    /**
     * 物品扫码入库提交
     *
     * @return
     */
    @ApiOperation("要货申请")
    @PostMapping("/applyReceipt")
    public AjaxResult applyReceipt(@RequestBody WarehouseApplyReceiptDto applyReceiptDto) {
        applyReceiptDto.setApplyUserId(this.getUserId());
        applyReceiptDto.setApplyUserName(this.getLoginUser().getUser().getNickName());
        applyReceiptDto.setApplyTime(DateTime.now());
        return AjaxResult.success(iWarehouseApplyReceiptService.saveApplyReceiptDto(applyReceiptDto));

    }

    /**
     * 待审核出库单列表
     */
    @ApiOperation("待审核出库单列表")
    @GetMapping("/outReceiptApproveList")
    public TableDataInfo outReceiptApproveList() {
        startPage();
        WarehouseOutReceipt warehouseOutReceipt = new WarehouseOutReceipt();
        warehouseOutReceipt.setState(ReceiptState.APPROVE.getCode());
        List<WarehouseOutReceipt> list = iWarehouseOutReceiptService.selectWarehouseOutReceiptList(warehouseOutReceipt);
        return getDataTable(list);
    }

    /**
     * 待审核出库单明细
     */
    @ApiOperation("出库单明细")
    @GetMapping("/outReceiptApproveDetail/{receiptId}")
    public AjaxResult outReceiptApproveDetail(@PathVariable("receiptId") Long receiptId) {
        return AjaxResult.success(iWarehouseOutReceiptService.selectWarehouseOutReceiptByWarehouseOutReceiptId(receiptId));
    }

    /**
     * 待审核出库单审核
     */
    @ApiOperation("出库单审核")
    @GetMapping("/outReceiptApprove/{receiptId}")
    public AjaxResult outReceiptApprove(@PathVariable("receiptId") Long receiptId) throws Exception {
        WarehouseOutReceiptDto warehouseOutReceipt = new WarehouseOutReceiptDto();
        warehouseOutReceipt.setWarehouseOutReceiptId(receiptId);
        warehouseOutReceipt.setAuditId(getUserId());
        warehouseOutReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseOutReceipt.setAuditTime(DateTime.now());
        return AjaxResult.success(iWarehouseOutReceiptService.approveWarehouseOutReceipt(warehouseOutReceipt));
    }

    /**
     * 待审核入库单列表
     */
    @ApiOperation("待审核入库单列表")
    @GetMapping("/inReceiptApproveList")
    public TableDataInfo inReceiptApproveList() {
        startPage();
        WarehouseInReceipt warehouseinReceipt = new WarehouseInReceipt();
        warehouseinReceipt.setState(ReceiptState.APPROVE.getCode());
        List<WarehouseInReceipt> list = iWarehouseInReceiptService.selectWarehouseInReceiptList(warehouseinReceipt);
        return getDataTable(list);
    }

    /**
     * 待审核出库单明细
     */
    @ApiOperation("入库单明细")
    @GetMapping("/inReceiptApproveDetail/{receiptId}")
    public AjaxResult inReceiptApproveDetail(@PathVariable("receiptId") Long receiptId) {
        return AjaxResult.success(iWarehouseInReceiptService.selectWarehouseInReceiptByWarehouseInReceiptId(receiptId));
    }

    /**
     * 待审核出库单审核
     */
    @ApiOperation("入库单审核")
    @GetMapping("/inReceiptApprove/{receiptId}")
    public AjaxResult inReceiptApprove(@PathVariable("receiptId") Long receiptId) throws Exception {
        WarehouseInReceiptDto warehouseInReceipt = new WarehouseInReceiptDto();
        warehouseInReceipt.setWarehouseInReceiptId(receiptId);
        warehouseInReceipt.setAuditId(getUserId());
        warehouseInReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseInReceipt.setAuditTime(DateTime.now());
        return AjaxResult.success(iWarehouseInReceiptService.approveWarehouseInReceipt(warehouseInReceipt));
    }

    @ApiOperation("仓库列表")
    @PostMapping("/warehouseList")
    public TableDataInfo warehouseList(@RequestBody Warehouse warehouse) {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }

    @ApiOperation("库存查询")
    @PreAuthorize("@ss.hasPermi('warehouse:report:stock')")
    @GetMapping("/stock")
    public TableDataInfo list(WarehouseStockReportQueryDto reportQueryDto) {
        startPage();
        PageInfo<Map> list = iWarehouseReportService.selectStockReportList(reportQueryDto);
        return getDataTable(list);
    }

    @ApiOperation("维修师傅列表")
    @GetMapping("/fixedUser/list")
    public TableDataInfo list() {
        List<SysUser> list = userService.selectUserByRole((Long.valueOf(iSysConfigService.selectConfigByKey("sys.warehouse.roleId"))));
        return getDataTable(list);
    }

    @ApiOperation("待处理要货申请列表")
    @GetMapping("/applyReceipt/list")
    public TableDataInfo applyReceiptList() {
        WarehouseApplyReceiptDto warehouseApplyReceiptDto = new WarehouseApplyReceiptDto();
        warehouseApplyReceiptDto.setRequestedUserId(getLoginUser().getUserId());
        warehouseApplyReceiptDto.setState(ApplyState.APPROVE.getCode());
        List<WarehouseApplyReceiptDto> list = iWarehouseApplyReceiptService.selectWarehouseApplyReceiptList(warehouseApplyReceiptDto);
        return getDataTable(list);
    }

    @ApiOperation("我的要货申请列表")
    @GetMapping("/applyReceipt/mylist")
    public TableDataInfo applyReceiptMyList() {
        WarehouseApplyReceiptDto warehouseApplyReceiptDto = new WarehouseApplyReceiptDto();
        warehouseApplyReceiptDto.setApplyUserId(getLoginUser().getUserId());
        List<WarehouseApplyReceiptDto> list = iWarehouseApplyReceiptService.selectWarehouseApplyReceiptList(warehouseApplyReceiptDto);
        return getDataTable(list);
    }
    @ApiOperation("要货申请详情")
    @GetMapping("/applyReceipt/detail/{receiptId}")
    public AjaxResult applyReceiptDetail(@PathVariable("receiptId") Long receiptId) {
        return AjaxResult.success(iWarehouseApplyReceiptService.selectWarehouseApplyReceiptByApplyReceiptId(receiptId));
    }

    @ApiOperation("处理要货申请")
    @PostMapping("/applyReceipt/do")
    public AjaxResult applyReceiptDo(@RequestBody WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        warehouseApplyReceipt.setState(ApplyState.OUTING.getCode());
        warehouseApplyReceipt.setAuditId(getUserId());
        warehouseApplyReceipt.setAuditTime(DateTime.now());
        WarehouseManager warehouseManager =iWarehouseManagerService.getOne(Wrappers.<WarehouseManager>lambdaQuery().eq(WarehouseManager::getUserId,getUserId()));
        if(warehouseManager!=null){
            warehouseApplyReceipt.setOutWarehouseId(warehouseManager.getWarehouseId());
        }
        return toAjax(warehouseApplyReceiptService.updateWarehouseApplyReceipt(warehouseApplyReceipt));
    }
    @ApiOperation("拒绝要货申请")
    @PostMapping("/applyReceipt/reject")
    public AjaxResult applyReceiptReject(@RequestBody WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        warehouseApplyReceipt.setState(ApplyState.INIT.getCode());
        warehouseApplyReceipt.setAuditId(getUserId());
        warehouseApplyReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseApplyReceiptService.updateById(warehouseApplyReceipt));
    }
    @ApiOperation("删除要货申请")
    @GetMapping("/applyReceipt/delete/{receiptId}")
    public AjaxResult applyReceiptDelete(@PathVariable("receiptId") Long receiptId) {

        return toAjax(warehouseApplyReceiptService.deleteWarehouseApplyReceiptByApplyReceiptId(receiptId));
    }
    @ApiOperation("物品溯源码详情")
    @GetMapping("/goods/snCode/{code}")
    public AjaxResult applyReceiptDo(@PathVariable("code") String code) {
        return AjaxResult.success(iWarehouseGoodsService.getSnCodeDetail(code));
    }
}
