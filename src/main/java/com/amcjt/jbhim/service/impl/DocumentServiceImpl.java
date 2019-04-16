package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.model.TreeModel;
import com.amcjt.jbhim.repository.mybatis.mapper.DocumentMapper;
import com.amcjt.jbhim.repository.mybatis.entity.DocumentDirectory;
import com.amcjt.jbhim.service.DocumentService;
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
            treeModel.setName(documentDirectory.getName());
            treeModel.setParentId(documentDirectory.getParentId());
            treeModels.add(treeModel);
        });
        //构建数
        return buildTree(treeModels);
    }

    private static List<TreeModel> buildTree(List<TreeModel> buildList) {
        List<TreeModel> treeList = new ArrayList<>();
        for (TreeModel childrenMenu : buildList) {
            boolean flag = false;
            for (TreeModel parentMenu : buildList) {
                if (parentMenu.getId().equals(childrenMenu.getParentId())) {
                    flag = true;
                    if (parentMenu.getChildren() == null) {
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(childrenMenu);
                    break;
                }
            }
            if (!flag) {
                treeList.add(childrenMenu);
            }
        }
        return treeList;
    }
}
