package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.entity.SysMenuEntity;
import com.amcjt.jbhim.service.SysMenuService;
import com.amcjt.jbhim.utils.UUIDGenerator;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceImplTest {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void findAllMenu() {
        List<SysMenuEntity> allMenu = sysMenuService.findAllMenu();
        allMenu.forEach(System.out::println);
    }

    @Test
    public void saveMenu() {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        sysMenuEntity.setId(UUIDGenerator.sequentialUUIDString());
        sysMenuEntity.setTitle("主页");
        sysMenuEntity.setUrl("http://www.amcjt.com");
        sysMenuEntity.setParentId("0");
        SysMenuEntity saveMenu = sysMenuService.saveMenu(sysMenuEntity);
        System.out.println(saveMenu);

    }

    /**
     * 流程部署
     */
    @Test
    public void deploy() {
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .name("HelloWorld")
                .addClasspathResource("processes/HelloWorld.bpmn")
                .addClasspathResource("processes/HelloWorld.png")
                .deploy();
        System.out.println("部署流程===>  流程id:" + deploy.getId() + "     流程名:" + deploy.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .asc()
                .list();

        if (list.size() > 0 && list != null) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out.println("#########################################################");
            }
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition() {
        String deploymentId = "1";
        processEngine.getRepositoryService()
                .deleteDeployment(deploymentId, true);
    }

    /**
     * 查看流程附件
     */
    @Test
    public void viewImage() throws IOException {
        String deploymentId = "2501";
        List<String> deploymentResourceNames = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        String imageName = null;
        for (String name : deploymentResourceNames) {
            System.out.println("name:" + name);
            if (name.indexOf(".png") > 0) {
                imageName = name;
            }
        }
        System.out.println("imageName:" + imageName);

        InputStream inputStream = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, imageName);
        File target = new File("D:\\develop\\" + imageName);
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        Files.copy(inputStream, target.toPath());
        System.out.println("copy successful");

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:/develop/imageName/a/b/c/d");
        //Files.createDirectory(path);
        Path directories = Files.createDirectories(path);
    }

}