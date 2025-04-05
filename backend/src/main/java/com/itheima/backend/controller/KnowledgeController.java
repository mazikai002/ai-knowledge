package com.itheima.backend.controller;

import com.itheima.backend.common.PageQuery;
import com.itheima.backend.common.PageResult;
import com.itheima.backend.common.Result;
import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.vo.KnowledgeSearchVO;
import com.itheima.backend.model.vo.KnowledgeVO;
import com.itheima.backend.service.KnowledgeSearchService;
import com.itheima.backend.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 知识库控制器
 */
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {
    
    private final KnowledgeService knowledgeService;
    private final KnowledgeSearchService knowledgeSearchService;
    
    /**
     * 创建知识
     */
    @PostMapping
    public Result<KnowledgeVO> createKnowledge(@RequestBody KnowledgeDTO knowledgeDTO) {
        return Result.success(knowledgeService.createKnowledge(knowledgeDTO));
    }
    
    /**
     * 更新知识
     */
    @PutMapping("/{id}")
    public Result<KnowledgeVO> updateKnowledge(@PathVariable Long id, @RequestBody KnowledgeDTO knowledgeDTO) {
        return Result.success(knowledgeService.updateKnowledge(id, knowledgeDTO));
    }
    
    /**
     * 删除知识
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledge(@PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
        return Result.success();
    }
    
    /**
     * 获取知识详情
     */
    @GetMapping("/{id}")
    public Result<KnowledgeVO> getKnowledge(@PathVariable Long id) {
        return Result.success(knowledgeService.getKnowledge(id));
    }
    
    /**
     * 分页查询知识列表
     */
    @GetMapping("/page")
    public Result<PageResult<KnowledgeVO>> pageKnowledge(PageQuery pageQuery) {
        return Result.success(knowledgeService.pageKnowledge(pageQuery));
    }
    
    /**
     * 搜索知识
     */
    @GetMapping("/search")
    public Result<PageResult<KnowledgeSearchVO>> searchKnowledge(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(knowledgeSearchService.searchKnowledge(keyword, page, size));
    }
} 