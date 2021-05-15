package com.baizhi.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.entity.Emp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequestMapping("files")
@RestController
public class FilesController {

    @PostMapping(value = "uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//文件上传下载必须写的
    public String uploadFile(@RequestPart("photo") MultipartFile photo) throws IOException {
        log.info("进入emps服务,接受的文件名为：{}", photo.getOriginalFilename());
        log.info("进入emps服务,接受的文件大小为：{}", photo.getSize());
        log.info("进入emps服务,接受的文件类型为：{}", photo.getContentType());

        //调用文件上传方法

        //选择上传文件
        //获取上传文件的真实路径  获取的路径是死的（绝对路径）则获取不到真实路径
        //String realPath = session.getServletContext().getRealPath("/upload/photo");
        //获取上传的文件名
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());
        //获取文件名
        String newName = UUID.randomUUID().toString() + "." + extension;
        //将文件名添加到数据库

        //调用工具类读取
        photo.transferTo(new File("D:/精英班/5 SpringCloud ems 小练习/ems_vue/pthotos" + "/" + newName));

        String str = "D:/精英班/5 SpringCloud ems 小练习/ems_vue/pthotos/" + newName;
        return str;
    }


    // 删除文件
    @GetMapping("deleteFile")
    public void deleteFile(@RequestParam("path") String path) {
        System.out.println("path2 = " + path);

        File file = new File(path);
        System.out.println("file = " + file);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    @PostMapping(value = "uploadFileAliYun", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFileAliYun(@RequestPart("photo") MultipartFile photo) {
        byte[] bytes = null;

        try {
            //把头像文件转换为byte字节
            bytes = photo.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com/";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G1DovmoXxDQSHfV18sY";
        String accessKeySecret = "dg76OKBJYYup4Z4RIQcvlBD695OSDK";
        //获取前台传过来的文件名
        String filename = photo.getOriginalFilename();
        //阿里云储存空间的名字
        String bucketName = "ems-tk";
        //上传的文件名
        String objectName = "ems/"+UUID.randomUUID().toString()+filename;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//        // 填写Byte数组。
//        byte[] content = "Hello OSS".getBytes();
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
        //文件储存的阿里云地址
        return "http://ems-tk.oss-cn-beijing.aliyuncs.com"+"/"+objectName;
    }

    @GetMapping("deleteFileAliYun")
    public void deleteFileAliYun(@RequestParam("path") String path) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G1DovmoXxDQSHfV18sY";
        String accessKeySecret = "dg76OKBJYYup4Z4RIQcvlBD695OSDK";

        String bucketName = "ems-tk";

        String[] split = path.split("/");

        String objectName = "ems/"+split[split.length-1];
        System.out.println("objectName = " + objectName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
