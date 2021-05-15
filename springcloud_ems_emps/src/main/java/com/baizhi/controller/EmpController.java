package com.baizhi.controller;

import com.baizhi.entity.Emp;
import com.baizhi.feignclient.FileClient;
import com.baizhi.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequestMapping("emp")
@RestController
@Slf4j
public class EmpController {

    @Resource
    EmpService empService;

    //注入要调用的伪客户端的接口
    @Resource
    FileClient fileClient;


    @GetMapping("/findAll")
    public List<Emp> findAll() {

        HashMap<String, Object> map = new HashMap<>();

        List<Emp> emp = empService.selectAllEmp();
        map.put("status", "success");

        return emp;

    }

    @GetMapping("/delete")
    public HashMap<String, Object> delete(String id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //创建一个对象
        Object o = new Object();
        try {
            Emp emp = empService.selectById(id);
            String path = emp.getPath();
            //删除本地服务器内遗留的文件
            //fileClient.deleteFile(emp.getPath());
            fileClient.deleteFileAliYun(path);

            empService.deleteById(id);
            map.put("msg", "删除员工信息成功!");
            map. put("state", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "删除员工信息失败!");
            map.put("state", false);
        }

        return map;
    }


    /**
     * @param emp
     * @param photo
     * @return
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HashMap<String, Object> save(Emp emp, MultipartFile photo) {  //@RequestBody 不能加 415

        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            String uuid = UUID.randomUUID().toString();
            emp.setId(uuid);

            /*String file = fileClient.uploadFile(photo);
            emp.setPath(file);*/
            String file = fileClient.uploadFileAliYun(photo);
            emp.setPath(file);

            empService.insertEmp(emp);
            map.put("msg", "员工信息保存成功!");
            map.put("state", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "员工信息保存失败!");
            map.put("state", false);
        }

        return map;

    }


    @GetMapping("/findOne")
    public Emp findOne(String id) {

        Emp emp = empService.selectById(id);
        return emp;

    }

    @PostMapping("/update")
    public HashMap<String, Object> update(Emp emp, MultipartFile photo,String path) {  //@RequestBody 不能加 415

        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            if (photo!=null) {

                /*String file = fileClient.uploadFile(photo);
                emp.setPath(file);*/
                String file = fileClient.uploadFileAliYun(photo);
                emp.setPath(file);
                //删除本地服务器内遗留的文件
                //fileClient.deleteFile(emp.getPath());
                fileClient.deleteFileAliYun(path);
            }

            empService.updateById(emp);
            map.put("msg", "员工信息修改成功!");
            map.put("state", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "员工信息修改失败!");
            map.put("state", false);
        }
        return map;

    }



}
