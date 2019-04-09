//package com.amcjt.jbhim.config;
//
//import org.activiti.engine.*;
//import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//
//@Configuration
//public class ActivitiConfig {
//    @Resource
//    private DataSource dataSource;
//    @Resource
//    private ResourcePatternResolver resourceLoader;
//
//    /**
//     * 初始化配置，将创建28张表
//     *
//     * @return StandaloneProcessEngineConfiguration
//     */
//    @Bean
//    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
//        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
//        configuration.setDataSource(dataSource);
//        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        configuration.setAsyncExecutorActivate(false);
//        return configuration;
//    }
//
//    /**
//     * 创建引擎方式二(未使用)
//     *
//     * @return ProcessEngineConfiguration
//     */
//    @Bean
//    public ProcessEngineConfiguration processEngineConfigurationVOne() {
//        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
//        engineConfiguration.setDataSource(dataSource);
//        engineConfiguration.setDatabaseSchemaUpdate("drop-create");
//        engineConfiguration.setAsyncExecutorActivate(false);
//        return engineConfiguration;
//    }
//
//
//    @Bean
//    public ProcessEngine processEngine() {
//        return processEngineConfiguration().buildProcessEngine();
//    }
//
//    /**
//     * 管理流程定义
//     *
//     * @return RepositoryService
//     */
//    @Bean
//    public RepositoryService repositoryService() {
//        return processEngine().getRepositoryService();
//    }
//
//    /**
//     * 执行管理, 包括启动,推进,删除,流程实例
//     *
//     * @return RuntimeService
//     */
//    @Bean
//    public RuntimeService runtimeService() {
//        return processEngineConfiguration().buildProcessEngine().getRuntimeService();
//    }
//
//    /**
//     * 任务管理
//     *
//     * @return TaskService
//     */
//    @Bean
//    public TaskService taskService() {
//        return processEngineConfiguration().buildProcessEngine().getTaskService();
//    }
//
//    /**
//     * 组织机构管理
//     *
//     * @return IdentityService
//     */
//    @Bean
//    public IdentityService identityService() {
//        return processEngineConfiguration().buildProcessEngine().getIdentityService();
//    }
//
//    /**
//     * 历史管理
//     *
//     * @return HistoryService
//     */
//    @Bean
//    public HistoryService historyService() {
//        return processEngineConfiguration().buildProcessEngine().getHistoryService();
//    }
//
//}
