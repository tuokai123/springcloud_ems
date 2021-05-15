package com.baizhi.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


//指定调用服务的名称,大写不容易混淆  value:调用服务的实例名 fallback:为方法指定降级方法
@FeignClient(value="files")
public interface FileClient {

    @PostMapping(value = "/files/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//文件上传下载必须写的
    public String uploadFile(@RequestPart("photo") MultipartFile photo);

    @GetMapping("/files/deleteFile")
    public void deleteFile(@RequestParam("path") String path);

    @PostMapping(value = "/files/uploadFileAliYun", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFileAliYun(@RequestPart("photo") MultipartFile photo);

    @GetMapping("/files/deleteFileAliYun")
    public void deleteFileAliYun(@RequestParam("path") String path);

}
