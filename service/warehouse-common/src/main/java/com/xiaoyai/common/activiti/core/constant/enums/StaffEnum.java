package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2021-03-26
 */
public class StaffEnum {
    @AllArgsConstructor
    @Getter
    public enum StaffBaseInformationStateEnum {
        NEED_APPROVE("0", "待审核"),
        NEED_IN("1", "待入职"),
        NOT_PASS("2", "未通过"),
        HAS_IN("3", "已入职"),
        ID_CARD_NEED_CHECK("0", "未检验"),
        ID_CARD_CHECK("1", "校验通过"),
        ID_CARD_NO_CHECK("2", "校验未通过");
        private final String type;

        private final String description;
    }
    @AllArgsConstructor
    @Getter
    public enum StaffOnJobStaffStateEnum {
        PROBATION("1", "试用期"),
        REGULAR("2", "正式"),
        ON_LEAVE("3", "待离职");
        private final String type;
        private final String description;
    }
    // 性别
    @Getter
    @AllArgsConstructor
    public enum SexEnum {
        MAN("0", "男"),
        WOMAN("1", "女");
        private String type;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum ModifyType {
        ADD("1", "新增"),
        DELETE("2", "删除"),
        UPDATE("3", "修改");
        private String num;
        private String value;
    }

    // 员工信息修改审核状态
    @Getter
    @AllArgsConstructor
    public enum ModifyApprovalStatus {
        UN_APPROVAL("0", "未审核"),
        APPROVALED("1", "已审核"),
        UN_PASS("2", "未通过");
        private String num;
        private String value;
    }

    // 房屋类别
    @Getter
    @AllArgsConstructor
    public enum StaffHomeEnum {
        MAN("0", "租赁"),
        WOMAN("1", "自有");
        private String type;
        private String description;
    }

    /**
     * 花名册更新类型
     * */
    @Getter
    @AllArgsConstructor
    public enum StaffRosterEditTypeEnum {
        STAFFEDUCATIONALEXPERIENCELIST("staffEducationalExperienceList", "教育经历"),
        STAFFFAMILYMEMBERINFORMATIONLIST("staffFamilyMemberInformationList", "家庭成员"),
        STAFFWORKEXPERIENCELIST("staffWorkExperienceList", "工作经历"),
        STAFFCERTIFICATEINFORMATIONLIST("staffCertificateInformationList", "证书信息"),
        NATIVEPLACE("nativePlace","籍贯"),
        STAFFINSURANCEBENEFITS("staff_insurance_benefits","保险福利"),
        AVATAR("avatar","头像"),
        STAFF_BASE_INFORMATION("staff_base_information","基本信息"),
        STAFF_COMMUNICATION_INFORMATION("staff_communication_information","通讯信息"),
        STAFF_JOB_CHANGE("staffJobChangeList","工作变动"),
        STAFF_CONTRACT("staffContract","员工合同"),
        STAFF_HONOR_PUNISHMENT("staffHonorPunishmentList","荣誉惩处"),
        STAFF_PERFORMANCE_RECORD("staffPerformanceRecordList","员工绩效"),
        STAFF_SALARY_ADJUSTMENT_RECORD("staffSalaryAdjustmentRecordList","员工薪资调整"),
        STAFF_TRAINING_RECORD("staffTrainingRecordList","员工培训"),
        STAFF_CERTIFICATE("staffCertificate","员工证件");
        private String value;
        private String description;
    }

    /**
     * 福利保险缴纳记录
     * */
    @Getter
    @AllArgsConstructor
    public enum StaffInsuranceBenefits {
        NOPAYMENTRECORDS("0", "无缴纳记录"),
        HASPAYMENTRECORDS("1", "有缴纳记录");
        private String value;
        private String description;
    }

    /**
     * 花名册编辑类型
     * */
    @Getter
    @AllArgsConstructor
    public enum UpdateRosterType {
        ADMIN_UPDATE("0", "管理员修改花名册"),
        STAFF_UPDATE("1", "员工申请修改花名册");
        private String value;
        private String description;
    }


    @Getter
    @AllArgsConstructor
    public enum StaffContractType {
        LABOR_CONTRACT("1", "劳动合同"),
        LABOR_AGREEMENT("2", "劳动协议"),
        REEMPLOYMENT_AGREEMENT("3", "退休返聘协议"),
        INTERNSHIP_AGREEMENT("4", "实习协议");
        private String value;
        private String description;
    }


    @Getter
    @AllArgsConstructor
    public enum StaffDegreeType {
        FIRST_DEGREE("101", "第一学历"),
        HIGHT_DEGREE("202", "最高学历"),
        CONTINUING_DEGREE("303", "继续教育学历");
        private String value;
        private String description;
    }

    // 健康状况
    @Getter
    @AllArgsConstructor
    public enum HealthStatusEnum {
        YES(0, "是"),
        NO(1, "否");

        private Integer value;

        private String description;
    }

    // 员工福利保险
    @Getter
    @AllArgsConstructor
    public enum InsuranceBenefitsEnum {
        YES(1, "有"),
        NO(0, "无");

        private Integer value;

        private String description;
    }

    // 证件类型
    @Getter
    @AllArgsConstructor
    public enum StaffCertificateType {
        ID_CARD(1, "身份证"),
        MARRIAGE_CERTIFICATE(2, "结婚证"),
        GRADUATION_CERTIFICATE(3, "毕业证"),
        DEGREE_CERTIFICATE(4, "学位证"),
        PROFESSIONAL_QUALIFICATION(5, "职业资格证"),
        TITLE_CERTIFICATE(6, "职称证"),
        PRACTICING_CERTIFICATE(7, "职业证"),
        JOB_CERTIFICATE(8, "岗位证");

        private Integer value;

        private String description;
    }

    // 证书状态
    @Getter
    @AllArgsConstructor
    public enum StaffJobCertificateStatus {
        USED_IN_CORP("1", "在司使用"),
        USED_OUTSIDE("2", "在外挂靠"),
        UNUSED_IN_CORP("3", "在司未使用"),
        STAFF_SUSTAINING("4", "员工自持");

        private String value;

        private String description;

        public static StaffJobCertificateStatus getByDesc(String desc) {
            for (StaffJobCertificateStatus status : values()) {
                if (status.getDescription().equals(desc)) {
                    //获取指定的枚举
                    return status;
                }
            }
            return null;
        }
    }
}
