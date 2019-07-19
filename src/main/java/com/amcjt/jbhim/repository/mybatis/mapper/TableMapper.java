package com.amcjt.jbhim.repository.mybatis.mapper;

import com.amcjt.jbhim.repository.mybatis.entity.Column;
import com.amcjt.jbhim.repository.mybatis.entity.Table;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableMapper {
    Table findTableById(@Param("tableId") String tableId);

    List<Column> findColumnById(@Param("tableId") String tableId);
}
