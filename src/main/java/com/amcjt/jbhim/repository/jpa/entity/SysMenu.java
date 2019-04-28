package com.amcjt.jbhim.repository.jpa.entity;

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
    private String name;

    @Column
    private String link;

    @Column
    private String parentId;

    @Column()
    private Integer sort;
}
