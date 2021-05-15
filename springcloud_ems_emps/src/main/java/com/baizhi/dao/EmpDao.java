package com.baizhi.dao;

import com.baizhi.entity.Emp;

import java.util.List;

public interface EmpDao {

    void insertEmp(Emp emp);

    void deleteById(String id);

    Emp selectById(String id);

    List<Emp> selectAllEmp();

    void updateById(Emp emp);

}
