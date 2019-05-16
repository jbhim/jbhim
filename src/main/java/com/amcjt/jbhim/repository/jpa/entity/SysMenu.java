package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_menu")
public class SysMenu extends EntityImpl {

    @Column
    private String title;

    @Column
    private String path;

    @Column
    private String parentId;

    @Column
    private String icon;

    @Column
    private Integer sort;
}
