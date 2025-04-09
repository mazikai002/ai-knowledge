package com.itheima.backend.service.impl;

import com.itheima.backend.common.PageQuery;
import com.itheima.backend.common.PageResult;
import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.vo.KnowledgeVO;
import com.itheima.backend.service.KnowledgeSearchService;
import com.itheima.backend.service.KnowledgeService;
import com.itheima.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 知识库服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {
    
    private final KnowledgeSearchService knowledgeSearchService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeVO createKnowledge(KnowledgeDTO knowledgeDTO) {
        // TODO: 实现知识创建逻辑
        // 1. 参数校验
        // 2. 保存知识信息
        // 3. 处理标签
        // 4. 返回创建结果
        log.info("创建知识: {}", knowledgeDTO);
        KnowledgeVO vo = new KnowledgeVO();
        vo.setId(1L); // 示例ID，实际应该是DB生成的ID
        vo.setTitle(knowledgeDTO.getTitle());
        vo.setContent(knowledgeDTO.getContent());
        vo.setFileType(knowledgeDTO.getFileType());
        vo.setTags(knowledgeDTO.getTags());
        vo.setDescription(knowledgeDTO.getDescription());
        vo.setCreateTime(LocalDateTime.now());
        vo.setUpdateTime(LocalDateTime.now());
        vo.setCreateBy(1L); // 示例创建者ID，实际应该是当前用户ID
        vo.setCreateByName("管理员"); // 示例创建者名称，实际应该是当前用户名称
        
        // 索引到ES
        knowledgeSearchService.indexKnowledge(vo);
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeVO updateKnowledge(Long id, KnowledgeDTO knowledgeDTO) {
        if (id == null) {
            throw new BusinessException(400, "知识ID不能为空");
        }
        
        // TODO: 实现知识更新逻辑
        // 1. 检查知识是否存在
        // 2. 更新知识信息
        // 3. 更新标签
        // 4. 返回更新结果
        log.info("更新知识: id={}, knowledge={}", id, knowledgeDTO);
        KnowledgeVO vo = new KnowledgeVO();
        vo.setId(id);
        vo.setTitle(knowledgeDTO.getTitle());
        vo.setContent(knowledgeDTO.getContent());
        vo.setFileType(knowledgeDTO.getFileType());
        vo.setTags(knowledgeDTO.getTags());
        vo.setDescription(knowledgeDTO.getDescription());
        vo.setCreateTime(LocalDateTime.now().minusDays(1)); // 示例创建时间
        vo.setUpdateTime(LocalDateTime.now());
        vo.setCreateBy(1L); // 示例创建者ID
        vo.setCreateByName("管理员"); // 示例创建者名称
        
        // 索引到ES
        knowledgeSearchService.indexKnowledge(vo);
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteKnowledge(Long id) {
        if (id == null) {
            throw new BusinessException(400, "知识ID不能为空");
        }
        
        // TODO: 实现知识删除逻辑
        // 1. 检查知识是否存在
        // 2. 删除知识信息
        // 3. 删除相关标签
        log.info("删除知识: {}", id);
        
        // 删除ES索引
        knowledgeSearchService.deleteKnowledgeIndex(id);
    }
    
    @Override
    public KnowledgeVO getKnowledge(Long id) {
        if (id == null) {
            throw new BusinessException(400, "知识ID不能为空");
        }
        
        // TODO: 实现知识查询逻辑
        // 1. 查询知识信息
        // 2. 查询相关标签
        // 3. 返回查询结果
        log.info("查询知识: {}", id);
        KnowledgeVO vo = new KnowledgeVO();
        vo.setId(id);
        vo.setTitle("示例知识标题");
        vo.setContent("示例知识内容");
        vo.setFileType("txt");
        vo.setTags("示例,标签");
        vo.setDescription("示例知识描述");
        vo.setCreateTime(LocalDateTime.now().minusDays(1));
        vo.setUpdateTime(LocalDateTime.now());
        vo.setCreateBy(1L);
        vo.setCreateByName("管理员");
        return vo;
    }
    
    @Override
    public PageResult<KnowledgeVO> pageKnowledge(PageQuery pageQuery) {
        // TODO: 实现知识分页查询逻辑
        // 1. 构建查询条件
        // 2. 执行分页查询
        // 3. 处理查询结果
        // 4. 返回分页结果
        log.info("分页查询知识: {}", pageQuery);
        
        List<KnowledgeVO> list = Arrays.asList(
            createSampleKnowledge(1L, "示例知识1", "示例内容1"),
            createSampleKnowledge(2L, "示例知识2", "示例内容2"),
            createSampleKnowledge(3L, "示例知识3", "示例内容3")
        );
        
        // 使用静态工厂方法创建PageResult
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        return PageResult.create(pageNum, pageSize, list.size(), list);
    }
    
    private KnowledgeVO createSampleKnowledge(Long id, String title, String content) {
        KnowledgeVO vo = new KnowledgeVO();
        vo.setId(id);
        vo.setTitle(title);
        vo.setContent(content);
        vo.setFileType("txt");
        vo.setTags("示例,标签");
        vo.setDescription("示例知识描述");
        vo.setCreateTime(LocalDateTime.now().minusDays(1));
        vo.setUpdateTime(LocalDateTime.now());
        vo.setCreateBy(1L);
        vo.setCreateByName("管理员");
        return vo;
    }
} 