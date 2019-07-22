package com.amcjt.jbhim.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author jbhim
 */
@Data
public class AccountModel {

    private String id;
    private String name;
    private String jobNum;
    private String phone;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private boolean enabled;
}
