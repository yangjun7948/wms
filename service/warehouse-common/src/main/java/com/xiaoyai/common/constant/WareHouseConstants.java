package com.xiaoyai.common.constant;

/**
 * 仓库管理常量
 * @author junyang
 * @date 2023/9/8
 */
public class WareHouseConstants {
    /**
     * 物品编号类型 {物品编号3位}{-}{流水号3位}
     */
    public static final String GOODS_CODE_TYPE= "warehouse_goods_code";

    /**
     * 入库单编号类型 规则：{Rk}{-}{yyyyMMdd}{1234}
     */
    public static final String IN_RECEIPT_CODE_TYPE= "in_receipt_code";

    /**
     * 入库单编号名称
     */
    public static final String IN_RECEIPT_CODE_NAME= "RK";
    /**
     * 入库单编号类型 {CK}{-}{yyyyMMdd}{1234}
     */
    public static final String OUT_RECEIPT_CODE_TYPE= "out_receipt_code";

    /**
     * 出库单编号名称
     */
    public static final String OUT_RECEIPT_CODE_NAME= "CK";

    /**
     * 调拨单编号名称
     */
    public static final String TRANSFER_RECEIPT_CODE_NAME= "DB";
    /**
     * 入库单编号类型 {CK}{-}{yyyyMMdd}{1234}
     */
    public static final String TRANSFER_RECEIPT_CODE_TYPE= "transfer_receipt_code";

    /**
     * 物品溯源码 {物品类别编号}{yyyyMMdd}{四位流水号}
     */
    public static final String GOODS_SN_CODE = "goods_sn_code";

    /**
     *  调拨出库
     */
    public static final String TRANSFER_OUT_TYPE_CODE = "99";
    /**
     *  调拨入库
     */
    public static final String TRANSFER_IN_TYPE_CODE = "98";

    /**
     * 要货出库
     */
    public static final String APPLY_OUT_TYPE_CODE = "100";

    /**
     * 要货入库
     */
    public static final String APPLY_IN_TYPE_CODE = "101";

    /**
     * 要货单编号名称
     */
    public static final String APPLY_RECEIPT_CODE_NAME= "YH";
    /**
     * 要货单编号类型 {YH}{-}{yyyyMMdd}{1234}
     */
    public static final String APPLY_RECEIPT_CODE_TYPE= "apply_receipt_code";
    /**
     * 盘点
     */
    public static final String CHECK_RECEIPT_CODE_NAME = "PD";
    public static final String CHECK_RECEIPT_CODE_TYPE = "check_receipt_code";

    /**
     * 仓库编号
     */
    public static final String WAREHOUSE_CODE_TYPE = "warehouse_code";

    public static final String WAREHOUSE_CODE_NAME = "CKCODE";
}
