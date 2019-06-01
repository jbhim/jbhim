/*
package com.amcjt.jbhim.service.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;

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

    */
/**
 * 初始化28张表
 * <p>
 * 部署流程
 * <p>
 * 开始任务
 * <p>
 * 查询个人任务
 * <p>
 * 完成任务
 * <p>
 * 流程变量设置与获取
 * <p>
 * 流程变量设置与获取
 * <p>
 * Class.getResource() and Class.getClassLoader()
 * <p>
 * 部署流程
 * <p>
 * 开始任务
 * <p>
 * 查询个人任务
 * <p>
 * 完成任务
 * <p>
 * 流程变量设置与获取
 * <p>
 * 流程变量设置与获取
 * <p>
 * Class.getResource() and Class.getClassLoader()
 *//*

    @Test
    public void dropOrCreate() {
        processEngine.getName();
        System.out.println("dropOrCreate");
    }

    */
/**
 * 部署流程
 *//*

    @Test
    public void deploy() {
        Deployment deploy = repositoryService.createDeployment()
                .name("并行网关")
                //.addClasspathResource("processes/LeaveBill/LeaveBill.bpmn20.xml")
                //.addClasspathResource("processes/LeaveBill/LeaveBill.bpmn20.png")
                //.addClasspathResource("processes/sequenceFlow/sequenceFlow.bpmn20.png")
                .addClasspathResource("processes/paralleGateWay/paralleGateWay.bpmn20.xml")
                .deploy();
        System.out.println("deploy.getName()=>" + deploy.getName());
        System.out.println("deploy.getId()=>" + deploy.getId());
        System.out.println("deploy.getKey()=>" + deploy.getKey());
        System.out.println("deploy.getCategory()=>" + deploy.getCategory());
        System.out.println("deploy.getDeploymentTime()=>" + deploy.getDeploymentTime());
        System.out.println("deploy.getTenantId()=>" + deploy.getTenantId());

    }

    */
/**
 * 开始任务
 *//*

    @Test
    public void processStart() {
        //runtimeService.startProcessInstanceById("amcjt:1:5");
        String key = "paralleGateWay";
        ProcessInstance amcjt = runtimeService.startProcessInstanceByKey(key);
        System.out.println("getId ===>" + amcjt.getId());
        System.out.println("getDeploymentId ===>" + amcjt.getDeploymentId());
        System.out.println("getDescription ===>" + amcjt.getDescription());
        System.out.println("getProcessDefinitionId ===>" + amcjt.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey ===>" + amcjt.getProcessDefinitionKey());
        System.out.println("getProcessDefinitionName ===>" + amcjt.getProcessDefinitionName());
        System.out.println("getProcessDefinitionVersion ===>" + amcjt.getProcessDefinitionVersion());
    }

    */
/**
 * 查询个人任务
 *//*

    @Test
    public void findUserTask() {
        //任务办理人
        String assignee = "卖家";

        Task task = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .singleResult();
        System.out.println("任务id====>" + task.getId());
        System.out.println("任务办理人====>" + task.getAssignee());
        System.out.println("流程执行id====>" + task.getExecutionId());
        System.out.println("任务名称====>" + task.getName());
        System.out.println("流程定义id====>" + task.getProcessDefinitionId());
        System.out.println("流程实例id====>" + task.getProcessInstanceId());
        System.out.println("任务创建时间====>" + task.getCreateTime());
    }

    */
/**
 * 完成任务
 *//*

    @Test
    public void completeTask() {
        String taskId = "22503";
        //完成任务时设置流程变量，${message=='重要'}来确定流程走向
        HashMap<String, Object> values = new HashMap<>();
        values.put("message", "重要");
        taskService.complete(taskId, values);
        System.out.println("任务完成");
    }


    */
/**
 * 流程变量设置与获取
 *//*

    @Test
    public void setVar() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("1")
                .singleResult();
        HashMap<String, Object> map = new HashMap<>();
        map.put("请假天数", 3);
        map.put("请假原因", "回家吃饭");
        map.put("请假时间", new Date());
        runtimeService.startProcessInstanceByKey(processDefinition.getKey(), map);
    }

    */
/**
 * 流程变量设置与获取
 *//*

    @Test
    public void getVar() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("李四")
                .singleResult();
        System.out.println(task.getExecutionId());
        Object day = runtimeService.getVariable(task.getExecutionId(), "请假天数");
        System.out.println(day);
        Object reason = taskService.getVariable(task.getId(), "请假原因");
        System.out.println(reason);
    }

    */
/**
 * Class.getResource() and Class.getClassLoader()
 *//*

    @Test
    public void resource(){
        System.out.println(this.getClass().getResource(""));
        System.out.println(this.getClass().getResource("/processes"));
        System.out.println(this.getClass().getClassLoader().getResource(""));
        System.out.println(this.getClass().getClassLoader().getResource("processes"));
    }


}
*/
