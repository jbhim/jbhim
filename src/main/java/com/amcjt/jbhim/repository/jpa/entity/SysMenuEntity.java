package com.amcjt.jbhim.repository.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_menu")
public class SysMenuEntity {
    @Id
    @Column(length = 64)
    private String id;

    @Column
    private String title;

    @Column
    private String url;

    @Column
    private String parentId;

}
