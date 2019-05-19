package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jbhim
 * @date 2019/5/18/018.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role_member")
public class RoleMember extends EntityImpl {
    @Column
    private String roleId;
    @Column
    private String userId;
}
