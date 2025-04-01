package com.itheima.backend.service;

import com.itheima.backend.common.PageQuery;
import com.itheima.backend.common.PageResult;
import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.vo.KnowledgeVO;

/**
 * 知识库服务接口
 */
public interface KnowledgeService {
    
    /**
     * 创建知识
     *
     * @param knowledgeDTO 知识信息
     * @return 创建的知识
     */
    KnowledgeVO createKnowledge(KnowledgeDTO knowledgeDTO);
    
    /**
     * 更新知识
     *
     * @param id 知识ID
     * @param knowledgeDTO 知识信息
     * @return 更新后的知识
     */
    KnowledgeVO updateKnowledge(Long id, KnowledgeDTO knowledgeDTO);
    
    /**
     * 删除知识
     *
     * @param id 知识ID
     */
    void deleteKnowledge(Long id);
    
    /**
     * 获取知识详情
     *
     * @param id 知识ID
     * @return 知识详情
     */
    KnowledgeVO getKnowledge(Long id);
    
    /**
     * 分页查询知识列表
     *
     * @param pageQuery 分页查询参数
     * @return 知识列表
     */
    PageResult<KnowledgeVO> pageKnowledge(PageQuery pageQuery);
} 