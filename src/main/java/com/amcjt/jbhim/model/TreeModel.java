package com.amcjt.jbhim.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class TreeModel {
    private String id;
    private String title;
    private String parentId;
    private String path;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TreeModel> children;
}
