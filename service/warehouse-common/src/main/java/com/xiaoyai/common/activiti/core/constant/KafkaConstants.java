package com.xiaoyai.common.activiti.core.constant;

/**
 * @author junyang
 * @date 2019/12/9
 */
public interface KafkaConstants {
    String TOPIC_BILL = "bill";

    String READ = "2";
    /**
     * 已处理
     */
    String DONE = "1";
    /**
     * 未处理
     */
    String UNDO = "0";

    /**********用户相关的主题*****/
    /**
     * 用户组
     */
    String GROUP_USER ="user";
    /**
     * 人员入职
     */
    String TOPIC_USER_IN = "user.in";
    /**
     * 人员离职
     */
    String TOPIC_USER_OUT = "user.out";
    /**
     * 人员待离职
     */
    String TOPIC_USER_OUTING ="user.outing";

    /**
     * 人员转正
     */
    String TOPIC_USER_OFFICIAL = "user.official";
    /**
     * 部门组
     */
    String GROUP_DEPT ="dept";
    /**
     * 部门修改
     */
    String TOPIC_DEPT_MODIFY = "dept.modify";
    /**
     * 部门添加
     */
    String TOPIC_DEPT_ADD = "dept.add";
    /**
     * 部门修改
     */
    String TOPIC_DEPT_DEL = "dept.del";
    /**
     * 合同同步
     */
    String TOPIC_USER_CONTRACT = "user.contract";

    /**
     * 招聘需求同步
     */
    String TOPIC_HIRE_DEMAND = "hire.demand";

    /**
     * 岗位薪资变动同步
     */
    String TOPIC_USER_SALARY_CHANGE = "user.salary.change";

    /**
     * 培训申请同步
     */
    String TOPIC_USER_TRAIN_APPLY = "user.train.apply";

    /**
     * 岗位变动同步
     */
    String TOPIC_USER_JOB_CHANGE = "user.job.change";

    /**********物业用户相关的主题*****/
    /**
     * 用户组
     */
    String GROUP_WY_USER ="wy.user";
    /**
     * 添加用户
     */
    String TOPIC_WY_USER_ADD = "wy.user.add";
    /**
     * 人员入职
     */
    String TOPIC_WY_USER_IN = "wy.user.in";
    /**
     * 人员离职
     */
    String TOPIC_WY_USER_OUT = "wy.user.out";
    /**
     * 人员待离职
     */
    String TOPIC_WY_USER_OUTING ="wy.user.outing";
    /**
     * 人员转正
     */
    String TOPIC_WY_USER_OFFICIAL = "wy.user.official";
    /**
     * 合同同步
     */
    String TOPIC_WY_USER_CONTRACT = "wy.user.contract";

    /**
     * 人员同步
     */
    String TOPIC_ZHSQ_USER = "zhsq.user";

}
