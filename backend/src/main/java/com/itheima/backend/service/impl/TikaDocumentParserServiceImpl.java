package com.itheima.backend.service.impl;

import com.itheima.backend.service.DocumentParserService;
import com.itheima.backend.service.ex.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Tika文档解析服务实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Service
public class TikaDocumentParserServiceImpl implements DocumentParserService {
    
    /**
     * Tika解析器实例
     */
    private final Tika tika = new Tika();
    
    /**
     * 自动检测解析器
     */
    private final Parser parser = new AutoDetectParser();
    
    /**
     * 解析文档内容
     * 
     * @param file 上传的文件
     * @return 解析后的文本内容
     * @throws BusinessException 业务异常
     */
    @Override
    public String parseContent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }
        
        // 记录文件基本信息，方便调试
        log.info("开始解析文件: 文件名={}, 大小={}字节, 内容类型={}", 
                file.getOriginalFilename(), 
                file.getSize(), 
                file.getContentType());
        
        try (InputStream inputStream = file.getInputStream()) {
            // 使用Tika解析文档内容，-1表示无字符数限制
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            
            // 设置资源名称和内容类型
            String fileName = file.getOriginalFilename();
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);
            metadata.set(Metadata.CONTENT_TYPE, file.getContentType());
            
            // 创建解析上下文
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            
            // 记录开始解析
            log.info("Tika开始解析文件: {}", fileName);
            
            try {
                // 执行解析
                parser.parse(inputStream, handler, metadata, context);
                
                // 记录元数据信息
                log.info("文件元数据: {}", Arrays.toString(metadata.names()));
                
                // 获取解析后的内容
                String content = handler.toString();
                if (content == null || content.trim().isEmpty()) {
                    log.warn("文件解析内容为空: {}", fileName);
                    return "";
                }
                
                log.info("文档解析成功，文件名：{}，内容长度：{}", fileName, content.length());
                return content;
            } catch (Exception e) {
                // 详细记录异常堆栈
                log.error("文件解析过程异常: {}", e.getMessage(), e);
                throw e;
            }
        } catch (IOException e) {
            log.error("文件读取失败: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文件读取失败：" + e.getMessage());
        } catch (SAXException e) {
            log.error("XML解析失败: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文档格式解析失败：" + e.getMessage());
        } catch (TikaException e) {
            log.error("Tika解析失败: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文档内容解析失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("未预期的错误: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文档处理失败，未知错误：" + e.getMessage());
        }
    }
    
    /**
     * 获取文件类型
     * 
     * @param file 上传的文件
     * @return 文件类型标识
     * @throws BusinessException 业务异常
     */
    @Override
    public String getFileType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }
        
        log.info("开始检测文件类型: 文件名={}, 声明类型={}", file.getOriginalFilename(), file.getContentType());
        
        try (InputStream inputStream = file.getInputStream()) {
            // 使用Tika检测文件类型
            String detectedType = tika.detect(inputStream);
            String fileName = file.getOriginalFilename();
            log.info("文件类型检测结果: {}，文件名: {}", detectedType, fileName);
            
            // 将MIME类型转换为简化的文件类型表示
            String fileType = convertMimeTypeToFileType(detectedType, fileName);
            log.info("最终文件类型: {}", fileType);
            return fileType;
        } catch (IOException e) {
            log.error("文件类型检测失败: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文件类型检测失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("文件类型检测未知错误: {}，详情: {}", e.getMessage(), e.getClass().getName(), e);
            throw new BusinessException(500, "文件类型检测失败，未知错误：" + e.getMessage());
        }
    }
    
    /**
     * 将MIME类型转换为简化的文件类型表示
     * 
     * @param mimeType MIME类型
     * @param fileName 文件名
     * @return 简化的文件类型
     */
    private String convertMimeTypeToFileType(String mimeType, String fileName) {
        // PDF文件
        if (mimeType.contains("pdf")) {
            log.info("检测到PDF文件: {}", fileName);
            return "pdf";
        }
        
        // Word文档
        if (mimeType.contains("msword") || mimeType.contains("officedocument.wordprocessing")) {
            log.info("检测到Word文档: {}", fileName);
            return "doc";
        }
        
        // 纯文本
        if (mimeType.contains("text/plain")) {
            log.info("检测到纯文本文件: {}", fileName);
            return "txt";
        }
        
        // HTML文档
        if (mimeType.contains("text/html") || mimeType.contains("application/xhtml+xml")) {
            log.info("检测到HTML文档: {}", fileName);
            return "html";
        }
        
        // Excel表格
        if (mimeType.contains("spreadsheetml") || mimeType.contains("excel")) {
            log.info("检测到Excel表格: {}", fileName);
            return "excel";
        }
        
        // PowerPoint演示文稿
        if (mimeType.contains("presentationml") || mimeType.contains("powerpoint")) {
            log.info("检测到PowerPoint演示文稿: {}", fileName);
            return "ppt";
        }
        
        // 对于其他类型，返回文件扩展名
        if (fileName != null && fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            log.info("使用文件扩展名作为类型: {}", extension);
            return extension;
        }
        
        // 无法识别的类型
        log.warn("无法识别的文件类型: mimeType={}, fileName={}", mimeType, fileName);
        return "unknown";
    }
} 