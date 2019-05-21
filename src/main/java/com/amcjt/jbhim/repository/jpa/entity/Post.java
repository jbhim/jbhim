package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jbhim
 * @date 2019/5/1/001.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "post")
public class Post extends EntityImpl {
    @Column
    private String postName;
    @Column
    private String postDesc;
}