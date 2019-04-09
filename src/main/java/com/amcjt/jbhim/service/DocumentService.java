package com.amcjt.jbhim.service;

import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.mybatis.entity.DocumentDirectory;

import java.util.List;

public interface DocumentService {
    List<DocumentDirectory> getDocumentDirTree();

    List<TreeModel> getDocDirTree();
}
