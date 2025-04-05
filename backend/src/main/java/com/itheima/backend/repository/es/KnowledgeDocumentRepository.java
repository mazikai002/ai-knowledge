package com.itheima.backend.repository.es;

import com.itheima.backend.model.document.KnowledgeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 知识库文档ES仓库接口
 */
@Repository
public interface KnowledgeDocumentRepository extends ElasticsearchRepository<KnowledgeDocument, Long> {
} 