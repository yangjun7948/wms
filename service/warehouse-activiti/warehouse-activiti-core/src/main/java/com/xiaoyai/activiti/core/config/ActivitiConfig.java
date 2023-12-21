package com.xiaoyai.activiti.core.config;

import com.xiaoyai.activiti.core.listener.GlobalActivitiEventListener;
import lombok.Data;
import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ActivitiConfig
 * @Description TODO
 * @Author wuxw
 * @Date 2019/10/22 21:55
 * @Version 1.0
 * add by wuxw 2019/10/22
 **/
@Configuration
@Data
public class ActivitiConfig  {


    @Autowired
    private GlobalActivitiEventListener globalActivitiEventListener;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

   /* @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        springProcessEngineConfiguration.setIdGenerator(new ActivityIdGenerator());
    }*/
    /**
     *
     */
//    @Value("${com.xiaoyai.activiti.fileUrl}")
    private String fileUrl;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate("true");
        List<ActivitiEventListener> activitiEventListenerList = new ArrayList<>();
        activitiEventListenerList.add(globalActivitiEventListener);
        spec.setEventListeners(activitiEventListenerList);

        return spec;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }


    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean
    public IdentityService identityService() throws Exception {
        return processEngine().getObject().getIdentityService();
    }

}
