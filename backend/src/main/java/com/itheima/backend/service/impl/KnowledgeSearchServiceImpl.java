package com.itheima.backend.service.impl;

import com.itheima.backend.common.PageResult;
import com.itheima.backend.model.document.KnowledgeDocument;
import com.itheima.backend.model.vo.KnowledgeSearchVO;
import com.itheima.backend.model.vo.KnowledgeVO;
import com.itheima.backend.repository.es.KnowledgeDocumentRepository;
import com.itheima.backend.service.KnowledgeSearchService;
import com.itheima.backend.service.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 知识库搜索服务实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeSearchServiceImpl implements KnowledgeSearchService {
    
    private final KnowledgeDocumentRepository knowledgeRepository;
    private final ElasticsearchRestTemplate elasticsearchTemplate;
    
    @Override
    public KnowledgeDocument indexKnowledge(KnowledgeVO knowledgeVO) {
        if (knowledgeVO == null || knowledgeVO.getId() == null) {
            throw new BusinessException(400, "知识信息不能为空");
        }
        
        log.info("开始索引知识文档: {}", knowledgeVO.getId());
        KnowledgeDocument document = new KnowledgeDocument();
        BeanUtils.copyProperties(knowledgeVO, document);
        return knowledgeRepository.save(document);
    }
    
    @Override
    public void deleteKnowledgeIndex(Long id) {
        if (id == null) {
            throw new BusinessException(400, "知识ID不能为空");
        }
        
        log.info("开始删除知识文档索引: {}", id);
        knowledgeRepository.deleteById(id);
    }
    
    @Override
    public PageResult<KnowledgeSearchVO> searchKnowledge(String keyword, int page, int size) {
        if (!StringUtils.hasText(keyword)) {
            throw new BusinessException(400, "搜索关键词不能为空");
        }
        
        log.info("开始搜索知识: keyword={}, page={}, size={}", keyword, page, size);
        
        // 构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("content").field("tags").field("description");
        highlightBuilder.preTags("<em class=\"highlight\">");
        highlightBuilder.postTags("</em>");
        
        // 构建查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content", "tags", "description"))
                .withHighlightBuilder(highlightBuilder)
                .withPageable(PageRequest.of(page - 1, size))
                .build();
        
        // 执行搜索
        SearchHits<KnowledgeDocument> searchHits = elasticsearchTemplate.search(
                searchQuery, KnowledgeDocument.class);
        
        // 处理搜索结果
        List<KnowledgeSearchVO> resultList = new ArrayList<>();
        
        for (SearchHit<KnowledgeDocument> hit : searchHits.getSearchHits()) {
            KnowledgeDocument document = hit.getContent();
            KnowledgeSearchVO vo = new KnowledgeSearchVO();
            BeanUtils.copyProperties(document, vo);
            
            // 处理高亮字段
            Map<String, String> highlightFields = new HashMap<>();
            Map<String, List<String>> highlightMap = hit.getHighlightFields();
            for (Map.Entry<String, List<String>> entry : highlightMap.entrySet()) {
                String fieldName = entry.getKey();
                List<String> highlightValues = entry.getValue();
                if (highlightValues != null && !highlightValues.isEmpty()) {
                    String highlightText = highlightValues.get(0);
                    highlightFields.put(fieldName, highlightText);
                    
                    // 替换原始字段值为高亮内容
                    switch (fieldName) {
                        case "title":
                            vo.setTitle(highlightText);
                            break;
                        case "content":
                            vo.setContent(highlightText);
                            break;
                        case "tags":
                            vo.setTags(highlightText);
                            break;
                        case "description":
                            vo.setDescription(highlightText);
                            break;
                    }
                }
            }
            vo.setHighlightFields(highlightFields);
            
            resultList.add(vo);
        }
        
        // 构建分页结果
        return PageResult.create(page, size, searchHits.getTotalHits(), resultList);
    }
    
    /**
     * 根据ID获取知识详情
     *
     * @param id 知识ID
     * @return 知识详情VO
     */
    @Override
    public KnowledgeVO getKnowledge(Long id) {
        if (id == null) {
            throw new BusinessException(400, "知识ID不能为空");
        }
        
        log.info("开始获取知识详情: {}", id);
        
        // 从ES中查询文档
        Optional<KnowledgeDocument> documentOptional = knowledgeRepository.findById(id);
        
        // 判断文档是否存在
        if (!documentOptional.isPresent()) {
            log.warn("知识文档不存在: {}", id);
            throw new BusinessException(404, "知识文档不存在");
        }
        
        // 将文档转换为VO
        KnowledgeDocument document = documentOptional.get();
        KnowledgeVO knowledgeVO = new KnowledgeVO();
        BeanUtils.copyProperties(document, knowledgeVO);
        
        log.info("成功获取知识详情: {}", id);
        return knowledgeVO;
    }
} 