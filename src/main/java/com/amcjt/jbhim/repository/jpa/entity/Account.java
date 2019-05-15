package com.amcjt.jbhim.repository.jpa.entity;

import com.amcjt.jbhim.repository.jpa.entity.impl.EntityImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author jbhim
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "account")
@SQLDelete(sql = "update accouet set enabled = false where id = ?")
public class Account extends EntityImpl {
    private String name;
    private String sex;
    private String image;
    private String jobNum;
    @Column(unique = true)
    private String phone;
    private String email;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String depId;
    @Transient
    private String depName;
    private String postId;
    @Transient
    private String postName;
    private boolean enabled;
    private String address;
    private String cardNo;
}
