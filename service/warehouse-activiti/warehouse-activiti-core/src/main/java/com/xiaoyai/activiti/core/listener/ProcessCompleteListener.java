package com.xiaoyai.activiti.core.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-03-26
 */
@Component
public class ProcessCompleteListener implements Serializable, TaskListener {

    private Expression value;

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    private static Logger logger = LoggerFactory.getLogger(ProcessCompleteListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("==============流程处理完成通知==============" + value.getExpressionText());
        switch (value.getExpressionText()) {
//            case KafkaConstants.TOPIC_USER_IN:
//                doUserIn(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_OUT:
//                doUserOut(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_OUTING:
//                doUserOuting(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_OFFICIAL:
//                doUserOfficial(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_CONTRACT:
//                doUserContract(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_HIRE_DEMAND:
//                doHireDemand(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_JOB_CHANGE:
//                doJobChange(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_SALARY_CHANGE:
//                doSalaryChange(delegateTask);
//                break;
//            case KafkaConstants.TOPIC_USER_TRAIN_APPLY:
//                doTrainApply(delegateTask);
//                break;
        }
    }
}
