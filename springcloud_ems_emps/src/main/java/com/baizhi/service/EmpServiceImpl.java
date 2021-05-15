package com.baizhi.service;

import com.baizhi.dao.EmpDao;
import com.baizhi.entity.Emp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service //声明给类为Service层
@Transactional(propagation = Propagation.SUPPORTS)
public class EmpServiceImpl implements EmpService {

    @Resource
    EmpDao empDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insertEmp(Emp emp) {
        empDao.insertEmp(emp);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(String id) {
        empDao.deleteById(id);
    }

    @Override
    public Emp selectById(String id) {
        return empDao.selectById(id);
    }

    @Override
    public List<Emp> selectAllEmp() {
        return empDao.selectAllEmp();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateById(Emp emp) {
        empDao.updateById(emp);
    }
}
