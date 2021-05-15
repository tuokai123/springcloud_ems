package com.baizhi.service;

import com.baizhi.entity.Emp;

import java.util.List;

public interface EmpService {
    void insertEmp(Emp emp);

    void deleteById(String id);

    Emp selectById(String id);

    List<Emp> selectAllEmp();

    void updateById(Emp emp);

}
