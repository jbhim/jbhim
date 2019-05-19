package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author jbhim
 * @date 2019/5/19/019.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "task")
public class Task extends EntityImpl {
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String createUserId;
    private String createUserName;
    private Date startTime;
    private Date endTime;
    private Date realEndTime;
    private boolean isFinish;
}
