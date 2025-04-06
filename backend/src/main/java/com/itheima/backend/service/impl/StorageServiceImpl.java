package com.itheima.backend.service.impl;

import com.itheima.backend.repository.minio.ObjectStorageRepository;
import com.itheima.backend.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 存储服务实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    
    /**
     * 对象存储仓库
     */
    private final ObjectStorageRepository objectStorageRepository;
    
    /**
     * 上传文件
     *
     * @param file 文件
     * @param objectName 对象名称
     * @return 文件访问路径
     */
    @Override
    public String uploadFile(MultipartFile file, String objectName) {
        log.info("开始上传文件，对象名称: {}", objectName);
        return objectStorageRepository.uploadFile(file, objectName);
    }
    
    /**
     * 获取文件
     *
     * @param objectName 对象名称
     * @return 文件输入流
     */
    @Override
    public InputStream getFile(String objectName) {
        log.info("开始获取文件，对象名称: {}", objectName);
        return objectStorageRepository.getFile(objectName);
    }
    
    /**
     * 删除文件
     *
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFile(String objectName) {
        log.info("开始删除文件，对象名称: {}", objectName);
        return objectStorageRepository.deleteFile(objectName);
    }
    
    /**
     * 获取文件访问URL
     *
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    @Override
    public String getFileUrl(String objectName) {
        log.info("开始获取文件URL，对象名称: {}", objectName);
        return objectStorageRepository.getFileUrl(objectName);
    }
} 