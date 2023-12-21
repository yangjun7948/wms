package com.xiaoyai.common.activiti.core.constant;

public class ReportConstants {


    // JasperReport xml 编译前源文件模板文件格式
    public static String JRXML_EXT = ".jrxml";
    // JasperReport 编译后模板文件格式
    public static String JASPER_EXT = ".jasper";

    /**
     * 模板文件路径名称
     */
    public static String TPL_FOLDER_NAME = "jasper";
    /**
     * 通知单模板文件位置
     */
    public static String WS_BILL_TEMPLATE_PATH ="template-billtemplate.jasper";
    /**
     * 通知单模板文件位置
     */
    public static String WS_REPLY_BILL_TEMPLATE_PATH ="template-replybill.jasper";
    /**
     * 通知单模板文件位置
     */
    public static String WS_BILL_ATTACH_PATH ="template-attachmenttemplate.jasper";

    /**
     * 通知单标题名称
     */
    public static String NOTICE_BILL_TITLE = "监理工程师通知单";
    /**
     * 通知单标题名称
     */
    public static String CONTACT_BILL_TITLE = "监理工程师联系单";
    /**
     * 处罚单标题名称
     */
    public static String PUNISH_BILL_TITLE = "监理工程师处罚单";


    /**
     * 打印单据桶名
     */
    public static String WS_BUCKET_NAME ="printbill";
    /**
     * 应聘登记表
     */
    public static String APPLY_REGISTRATION = "apply_registration";
    /**
     * 应聘登记表子报表
     */
    public static String APPLY_REGISTRATION_SUB = "apply_registration_sub1";
}
