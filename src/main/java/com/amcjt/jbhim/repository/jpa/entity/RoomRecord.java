package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "room_record")
public class RoomRecord extends EntityImpl {
    private String roomId;
    private String roomName;
    private Date startTime;
    private Date endTime;
}
