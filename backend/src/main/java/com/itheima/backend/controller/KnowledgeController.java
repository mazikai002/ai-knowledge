package com.itheima.backend.controller;

import com.itheima.backend.common.PageQuery;
import com.itheima.backend.common.PageResult;
import com.itheima.backend.common.Result;
import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.vo.KnowledgeVO;
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
} 