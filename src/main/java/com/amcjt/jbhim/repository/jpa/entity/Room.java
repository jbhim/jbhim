package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "room")
public class Room extends EntityImpl {
    private String name;
    private String address;
    @Transient
    private boolean used;

    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    private String percent;
}
