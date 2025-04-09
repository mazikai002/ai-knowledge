package com.itheima.backend.repository.minio;

import com.itheima.backend.config.MinioConfig;
import com.itheima.backend.common.exception.BusinessException;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * MinIO对象存储仓库实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MinioObjectStorageRepositoryImpl implements ObjectStorageRepository {
    
    /**
     * MinIO客户端
     */
    private final MinioClient minioClient;
    
    /**
     * MinIO配置
     */
    private final MinioConfig minioConfig;
    
    /**
     * 上传文件到对象存储
     *
     * @param file 文件
     * @param objectName 对象名称
     * @return 文件访问路径
     * @throws BusinessException 业务异常
     */
    @Override
    public String uploadFile(MultipartFile file, String objectName) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }
        
        try {
            // 检查存储桶是否存在，不存在则创建
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .build());
            
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build());
                log.info("创建存储桶: {}", minioConfig.getBucketName());
            }
            
            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            
            log.info("文件上传成功: {}", objectName);
            return getFileUrl(objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new BusinessException(500, "文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取文件
     *
     * @param objectName 对象名称
     * @return 文件输入流
     * @throws BusinessException 业务异常
     */
    @Override
    public InputStream getFile(String objectName) {
        if (objectName == null || objectName.trim().isEmpty()) {
            throw new BusinessException(400, "对象名称不能为空");
        }
        
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("获取文件失败: {}", e.getMessage(), e);
            throw new BusinessException(500, "获取文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除文件
     *
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    @Override
    public boolean deleteFile(String objectName) {
        if (objectName == null || objectName.trim().isEmpty()) {
            log.warn("删除文件失败: 对象名称不能为空");
            return false;
        }
        
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .build());
            log.info("文件删除成功: {}", objectName);
            return true;
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 获取文件访问URL
     *
     * @param objectName 对象名称
     * @return 文件访问URL
     * @throws BusinessException 业务异常
     */
    @Override
    public String getFileUrl(String objectName) {
        if (objectName == null || objectName.trim().isEmpty()) {
            throw new BusinessException(400, "对象名称不能为空");
        }
        
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .method(Method.GET)
                    .expiry(7, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage(), e);
            throw new BusinessException(500, "获取文件URL失败: " + e.getMessage());
        }
    }
} 