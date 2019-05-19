package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.repository.mybatis.entity.DocumentDirectory;
import com.amcjt.jbhim.repository.mybatis.mapper.DocumentMapper;
import com.amcjt.jbhim.service.DocumentService;
import com.amcjt.jbhim.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentServiceImpl(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Override
    public List<DocumentDirectory> getDocumentDirTree() {
        return documentMapper.getDocumentDir();
    }

    @Override
    public List<TreeModel> getDocDirTree() {
        List<TreeModel> treeModels = new ArrayList<>();
        getDocumentDirTree().forEach(documentDirectory -> {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(String.valueOf(documentDirectory.getId()));
            treeModel.setTitle(documentDirectory.getName());
            treeModel.setParentId(documentDirectory.getParentId());
            treeModels.add(treeModel);
        });
        //构建数
        return TreeUtil.buildTree(treeModels);
    }


}
