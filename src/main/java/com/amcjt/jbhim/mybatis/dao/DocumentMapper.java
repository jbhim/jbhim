package com.amcjt.jbhim.mybatis.dao;

import com.amcjt.jbhim.mybatis.entity.DocumentDirectory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMapper {
    List<DocumentDirectory> getDocumentDir();
}
