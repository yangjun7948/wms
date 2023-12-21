package com.xiaoyai.common.activiti.core.constant.enums;

/**
 * @author xieyu
 * 花名册对应字段
 */
public enum RosterFiledEnum {

    PERSONALCONTACTNUMBER("staff_communication_information", "personalContactNumber", "本人联系号码"),
    EMERGENCYCONTACTNAME("staff_communication_information", "emergencyContactName", "紧急联系人"),
    EMERGENCYCONTACTRELATION("staff_communication_information", "emergencyContactRelation", "紧急联系人关系"),
    EMERGENCYCONTACTNUMBER("staff_communication_information", "emergencyContactNumber", "紧急联系人电话"),
    HOMEADDRESS("staff_communication_information", "homeAddress", "家庭住址"),
    CURRENTHOMEADDRESS("staff_communication_information", "currentHomeAddress", "现居住地址"),
    MAILINGADDRESS("staff_communication_information", "mailingAddress", "通讯送达地址"),

    QQNUMBER("staff_base_information", "qqNumber", "常用QQ号"),
    EMAIL("staff_base_information", "email", "邮箱"),
    DEPARTMENTNAME("staff_base_information", "departmentName", "部门名称"),
    POST("staff_base_information", "post", "岗位"),
    AVATAR("staff_base_information", "avatar", "头像"),
    ENTRYTIME("staff_base_information", "entryTime", "入职时间"),
    NAME("staff_base_information", "name", "员工名称"),
    SEX("staff_base_information", "sex", "性别"),
    JOBNUMBER("staff_base_information", "jobNumber", "工号"),
    AGE("staff_base_information", "age", "年龄"),
    NATION("staff_base_information", "nation", "民族"),
    NATIVEPLACE("staff_base_information", "nativePlace", "籍贯"),
    POLITICALSTATUS("staff_base_information", "politicalStatus", "政治面貌"),
    MARITALSTATUS("staff_base_information", "maritalStatus", "婚姻状况"),
    ERTILITYSTATUS("staff_base_information", "fertilityStatus", "生育状况"),
    FIRSTDEGREE("staff_base_information", "firstDegree", "第一学历"),
    GRADUATEDSCHOOL("staff_base_information", "graduatedSchool", "毕业学校"),
    PROFESSION("staff_base_information", "profession", "专业"),
    BIRTHDAY("staff_base_information", "birthday", "生日"),
    HEIGHT("staff_base_information", "height", "身高"),
    WEIGHT("staff_base_information", "weight", "体重"),
    PARTICIPATIONWORKTIME("staff_base_information", "participationWorkTime", "参加工作时间"),
    HOBBY("staff_base_information", "hobby", "爱好"),
    ADVANTAGE("staff_base_information", "advantage", "个人优点"),
    IDCARD("staff_base_information", "idCard", "身份证"),
    DOMICILE("staff_base_information", "domicile", "户籍所在地"),
    HEALTHSTATUS("staff_base_information", "healthStatus", "健康状况"),
    JOINPARTYTIME("staff_base_information", "joinPartyTime", "入党时间"),
    PARTYORGANIZATIONLOCATION("staff_base_information", "partyOrganizationLocation", "党组织关系所在地"),

    EDUCATIONSTARTENDTIME("staff_educational_experience", "startEndTime", "教育起止时间"),
    SCHOOL("staff_educational_experience", "school", "学校"),
    EDUCATIONPROFESSION("staff_educational_experience", "educationProfession", "教育经历专业"),
    DEGREE("staff_educational_experience", "degree", "学历"),
    EDUCATIONREMARK("staff_educational_experience", "remark", "教育经历备注"),

    WORKSTARTENDTIME("staff_work_experience", "startEndTime", "工作起止时间"),
    WORKUNIT("staff_work_experience", "workUnit", "工作单位"),
    WORKPOST("staff_work_experience", "workPost", "工作岗位"),
    SALARYLEVEL("staff_work_experience", "salaryLevel", "薪资水平"),
    WITNESSNAME("staff_work_experience", "witnessName", "证明人名称"),
    WITNESSPHONE("staff_work_experience", "witnessPhone", "证明人电话"),

    CERTIFICATENAME("staff_certificate_information", "certificateName", "证书名称"),
    CERTIFICATENUMBER("staff_certificate_information", "certificateNumber", "证书编号"),
    OBTAINCERTIFICATETIME("staff_certificate_information", "obtainCertificateTime", "证书获得时间"),
    CERTIFICATEREMARK("staff_certificate_information", "remark", "证书备注"),

    APPELLATION("staff_family_member_information", "appellation", "称谓"),
    FAMILYNAME("staff_family_member_information", "name", "家庭成员姓名"),
    FAMILYAGE("staff_family_member_information", "age", "家庭成员年龄"),
    WORKUNITPOST("staff_family_member_information", "workUnitPost", "工作单位及岗位"),
    CONTACTNUMBER("staff_family_member_information", "contactNumber", "联系方式");


    private String table;
    private String filed;
    private String value;

    RosterFiledEnum(String table, String filed, String value) {
        this.table = table;
        this.filed = filed;
        this.value = value;
    }

    public static String getRosterModifyType(String table, String filed) {

        for (RosterFiledEnum e : RosterFiledEnum.values()) {
            if (table.equals(e.getTable()) && filed.equals(e.getFiled())) {
                return e.getValue();
            }
        }
        return "";

    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
