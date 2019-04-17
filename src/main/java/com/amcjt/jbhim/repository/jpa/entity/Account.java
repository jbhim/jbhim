package com.amcjt.jbhim.repository.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    private String id;
    private String name;
    private String jobNum;
    private String phone;
    private String email;
    private String username;
    private String password;
    private boolean enabled;
}
