package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.JbhimApplicationTests;
import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.repository.mybatis.entity.DocumentDirectory;
import com.amcjt.jbhim.service.DocumentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DocumentServiceImplTest extends JbhimApplicationTests {

    @Autowired
    private DocumentService documentService;

    @Test
    public void getDocumentDirTree() {
        List<DocumentDirectory> documentDirTree = documentService.getDocumentDirTree();
        System.out.println(documentDirTree);
    }

    @Test
    public void getDocDirTree() {
        List<TreeModel> docDirTree = documentService.getDocDirTree();
        System.out.println(docDirTree);
    }
}