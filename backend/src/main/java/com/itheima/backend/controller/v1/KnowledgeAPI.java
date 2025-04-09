package com.itheima.backend.controller.v1;

import com.itheima.backend.common.PageResult;
import com.itheima.backend.common.Result;
import com.itheima.backend.model.vo.KnowledgeSearchVO;
import com.itheima.backend.service.KnowledgeSearchService;
import com.itheima.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 知识库API (V1)
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/knowledge")
@RequiredArgsConstructor
public class KnowledgeAPI {
    
    private final KnowledgeSearchService knowledgeSearchService;
    
    /**
     * 搜索知识
     * 
     * @param keyword 关键词
     * @param page 页码
     * @param size 每页数量
     * @return 搜索结果
     */
    @GetMapping("/search")
    public Result<PageResult<KnowledgeSearchVO>> searchKnowledge(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        log.info("搜索知识，关键词：{}，页码：{}，每页数量：{}", keyword, page, size);
        
        try {
            PageResult<KnowledgeSearchVO> result = knowledgeSearchService.searchKnowledge(keyword, page, size);
            log.info("搜索结果，总数：{}，页码：{}", result.getTotal(), result.getPageNum());
            return Result.success(result);
        } catch (BusinessException e) {
            log.error("搜索知识业务异常: code={}, message={}", e.getCode(), e.getMessage());
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("搜索知识未预期异常: {}", e.getMessage(), e);
            return Result.error(500, "搜索知识失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除知识
     * 
     * @param id 知识ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledge(@PathVariable Long id) {
        
        log.info("删除知识，ID：{}", id);
        
        try {
            knowledgeSearchService.deleteKnowledgeIndex(id);
            log.info("知识删除成功，ID：{}", id);
            return Result.success();
        } catch (BusinessException e) {
            log.error("删除知识业务异常: code={}, message={}", e.getCode(), e.getMessage());
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("删除知识未预期异常: {}", e.getMessage(), e);
            return Result.error(500, "删除知识失败: " + e.getMessage());
        }
    }
} 