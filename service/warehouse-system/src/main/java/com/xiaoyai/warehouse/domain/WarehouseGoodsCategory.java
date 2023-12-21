package com.xiaoyai.warehouse.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.TreeEntity;

/**
 * 物料分类对象 warehouse_goods_category
 *
 * @author junyang
 * @date 2023-09-08
 */
public class WarehouseGoodsCategory extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 物品类别主键 */
    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /** 物品类别编号 */
    @Excel(name = "物品类别编号")
    private String categoryCode;

    /** 物品类别名称 */
    @Excel(name = "物品类别名称")
    private String categoryName;

    /** 类别所处等级 */
    @TableField(exist = false)
    private Integer level;

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode()
    {
        return categoryCode;
    }
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }
    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getLevel()
    {
        return level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("categoryId", getCategoryId())
                .append("categoryCode", getCategoryCode())
                .append("categoryName", getCategoryName())
                .append("ancestors", getAncestors())
                .append("parentId", getParentId())
                .append("orderNum", getOrderNum())
                .append("level", getLevel())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
