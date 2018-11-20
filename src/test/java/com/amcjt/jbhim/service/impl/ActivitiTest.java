package com.amcjt.jbhim.service.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 初始化28张表
     */
    @Test
    public void dropOrCreate() {
        processEngine.getName();
        System.out.println("dropOrCreate");
    }

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        Deployment deploy = repositoryService.createDeployment()
                .name("请假流程001")
                .addClasspathResource("processes/LeaveBill/LeaveBill.bpmn20.xml")
                .addClasspathResource("processes/LeaveBill/LeaveBill.bpmn20.png")
                .deploy();
        System.out.println("deploy.getName()=>" + deploy.getName());
        System.out.println("deploy.getId()=>" + deploy.getId());
        System.out.println("deploy.getKey()=>" + deploy.getKey());
        System.out.println("deploy.getCategory()=>" + deploy.getCategory());
        System.out.println("deploy.getDeploymentTime()=>" + deploy.getDeploymentTime());
        System.out.println("deploy.getTenantId()=>" + deploy.getTenantId());

    }

    /**
     * 开始任务
     */
    @Test
    public void processStart() {
        //runtimeService.startProcessInstanceById("amcjt:1:5");
        ProcessInstance amcjt = runtimeService.startProcessInstanceByKey("amcjt");
        System.out.println("getId ===>" + amcjt.getId());
        System.out.println("getDeploymentId ===>" + amcjt.getDeploymentId());
        System.out.println("getDescription ===>" + amcjt.getDescription());
        System.out.println("getProcessDefinitionId ===>" + amcjt.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey ===>" + amcjt.getProcessDefinitionKey());
        System.out.println("getProcessDefinitionName ===>" + amcjt.getProcessDefinitionName());
        System.out.println("getProcessDefinitionVersion ===>" + amcjt.getProcessDefinitionVersion());
    }

    /**
     * 查询个人任务
     */
    @Test
    public void findUserTask() {
        //任务办理人
        String assignee = "张三";

        Task task = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .singleResult();
        System.out.println("任务id====>" + task.getId());
        System.out.println("任务办理人====>" + task.getAssignee());
        System.out.println("流程实例id====>" + task.getExecutionId());
        System.out.println("任务名称====>" + task.getName());
        System.out.println("流程定义id====>" + task.getProcessDefinitionId());
        System.out.println("流程实例id====>" + task.getProcessInstanceId());
        System.out.println("任务创建时间====>" + task.getCreateTime());
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        String taskId = "7502";
        taskService.complete(taskId);
        System.out.println("任务完成");
    }

}
