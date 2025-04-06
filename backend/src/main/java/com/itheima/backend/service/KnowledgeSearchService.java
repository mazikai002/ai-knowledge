package com.itheima.backend.service;

import com.itheima.backend.common.PageResult;
import com.itheima.backend.model.document.KnowledgeDocument;
import com.itheima.backend.model.vo.KnowledgeSearchVO;
import com.itheima.backend.model.vo.KnowledgeVO;

/**
 * 知识库搜索服务接口
 * 
 * @author developer
 * @date 2025/04/06
 */
public interface KnowledgeSearchService {
    
    /**
     * 索引知识文档
     *
     * @param knowledgeVO 知识VO
     * @return 索引文档
     */
    KnowledgeDocument indexKnowledge(KnowledgeVO knowledgeVO);
    
    /**
     * 删除知识文档索引
     *
     * @param id 知识ID
     */
    void deleteKnowledgeIndex(Long id);
    
    /**
     * 搜索知识
     *
     * @param keyword 关键词
     * @param page 页码
     * @param size 每页数量
     * @return 知识搜索结果
     */
    PageResult<KnowledgeSearchVO> searchKnowledge(String keyword, int page, int size);
    
    /**
     * 根据ID获取知识详情
     *
     * @param id 知识ID
     * @return 知识详情VO
     */
    KnowledgeVO getKnowledge(Long id);
} 