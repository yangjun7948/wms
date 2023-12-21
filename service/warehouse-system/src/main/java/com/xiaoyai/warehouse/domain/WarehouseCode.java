package com.xiaoyai.warehouse.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 *  仓库编号对象 warehouse_code
 * 
 * @author junyang
 * @date 2023-09-08
 */
public class WarehouseCode
{
    private static final long serialVersionUID = 1L;

    /** 编号类型 */
    @Excel(name = "编号类型")
    private String codeType;

    /** 编号名称 */
    @Excel(name = "编号名称")
    private String codeName;

    /** 编号序列 */
    @Excel(name = "编号序列")
    private Integer codeNumber;

    public void setCodeType(String codeType) 
    {
        this.codeType = codeType;
    }

    public String getCodeType() 
    {
        return codeType;
    }
    public void setCodeName(String codeName) 
    {
        this.codeName = codeName;
    }

    public String getCodeName() 
    {
        return codeName;
    }
    public void setCodeNumber(Integer codeNumber) 
    {
        this.codeNumber = codeNumber;
    }

    public Integer getCodeNumber() 
    {
        return codeNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("codeType", getCodeType())
            .append("codeName", getCodeName())
            .append("codeNumber", getCodeNumber())
            .toString();
    }
}
