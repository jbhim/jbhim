package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公告实体
 *
 * @author jbhim
 * @date 2019/5/21/021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "bulletin")
public class Bulletin extends EntityImpl {
    private String title;
    private String content;
    private String attachment;
    private String userId;
    private String userName;
    private boolean publish;
}
