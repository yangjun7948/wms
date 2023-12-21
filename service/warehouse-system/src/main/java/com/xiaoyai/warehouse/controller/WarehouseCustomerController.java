package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xiaoyai.common.annotation.Log;
import com.xiaoyai.common.core.controller.BaseController;
import com.xiaoyai.common.core.domain.AjaxResult;
import com.xiaoyai.common.enums.BusinessType;
import com.xiaoyai.warehouse.domain.WarehouseCustomer;
import com.xiaoyai.warehouse.service.IWarehouseCustomerService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 客户信息Controller
 * 
 * @author junyang
 * @date 2023-09-09
 */
@RestController
@RequestMapping("/warehouse/warehousecustomer")
public class WarehouseCustomerController extends BaseController
{
    @Autowired
    private IWarehouseCustomerService warehouseCustomerService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseCustomer warehouseCustomer)
    {
        startPage();
        List<WarehouseCustomer> list = warehouseCustomerService.selectWarehouseCustomerList(warehouseCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:export')")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseCustomer warehouseCustomer)
    {
        List<WarehouseCustomer> list = warehouseCustomerService.selectWarehouseCustomerList(warehouseCustomer);
        ExcelUtil<WarehouseCustomer> util = new ExcelUtil<WarehouseCustomer>(WarehouseCustomer.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:query')")
    @GetMapping(value = "/{customerId}")
    public AjaxResult getInfo(@PathVariable("customerId") Long customerId)
    {
        return AjaxResult.success(warehouseCustomerService.selectWarehouseCustomerByCustomerId(customerId));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:add')")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseCustomer warehouseCustomer)
    {
        return toAjax(warehouseCustomerService.insertWarehouseCustomer(warehouseCustomer));
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:edit')")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseCustomer warehouseCustomer)
    {
        return toAjax(warehouseCustomerService.updateWarehouseCustomer(warehouseCustomer));
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehousecustomer:remove')")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds)
    {
        return toAjax(warehouseCustomerService.deleteWarehouseCustomerByCustomerIds(customerIds));
    }
}
