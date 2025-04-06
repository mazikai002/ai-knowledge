package com.itheima.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 存储服务接口
 * 
 * @author developer
 * @date 2025/04/06
 */
public interface StorageService {
    
    /**
     * 上传文件
     * 
     * @param file 文件
     * @param objectName 对象名称
     * @return 文件访问路径
     */
    String uploadFile(MultipartFile file, String objectName);
    
    /**
     * 获取文件
     * 
     * @param objectName 对象名称
     * @return 文件输入流
     */
    InputStream getFile(String objectName);
    
    /**
     * 删除文件
     * 
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    boolean deleteFile(String objectName);
    
    /**
     * 获取文件访问URL
     * 
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    String getFileUrl(String objectName);
} 