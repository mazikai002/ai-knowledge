package com.itheima.backend.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文档解析服务接口
 */
public interface DocumentParserService {
    
    /**
     * 解析文档内容
     * 
     * @param file 文件
     * @return 文档内容
     */
    String parseContent(MultipartFile file);
    
    /**
     * 获取文件类型
     * 
     * @param file 文件
     * @return 文件类型
     */
    String getFileType(MultipartFile file);
} 