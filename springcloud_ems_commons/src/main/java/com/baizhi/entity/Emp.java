package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {
    private String id;
    private String name;
    private String path;
    private Double salary;
    private Integer age;
}
