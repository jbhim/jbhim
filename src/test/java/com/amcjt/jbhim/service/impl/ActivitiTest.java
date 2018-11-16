package com.amcjt.jbhim.service.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
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

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        Deployment deploy = repositoryService.createDeployment()
                .name("请假流程001")
                .addClasspathResource("processes/HelloWorld.bpmn")
                .addClasspathResource("processes/HelloWorld.png")
                .deploy();
        System.out.println("deploy.getName()=>" + deploy.getName());
        System.out.println("deploy.getId()=>" + deploy.getId());
        System.out.println("deploy.getKey()=>" + deploy.getKey());
        System.out.println("deploy.getCategory()=>" + deploy.getCategory());
        System.out.println("deploy.getDeploymentTime()=>" + deploy.getDeploymentTime());
        System.out.println("deploy.getTenantId()=>" + deploy.getTenantId());

    }

    @Test
    public void dropOrCreate() {
        processEngine.getName();
        System.out.println("dropOrCreate");
    }
}
