import React, { useState } from 'react';
import { Input, Button, Card, Tag, Empty, Spin, Pagination, Typography, Divider } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import axios from 'axios';

const { Title, Text, Paragraph } = Typography;

interface KnowledgeSearchResult {
  id: number;
  title: string;
  content: string;
  fileType: string;
  tags: string;
  description: string;
  createTime: string;
  updateTime: string;
  createBy: number;
  createByName: string;
  highlightFields: Record<string, string>;
}

interface SearchResponse {
  code: number;
  message: string;
  data: {
    total: number;
    list: KnowledgeSearchResult[];
  };
}

const KnowledgeSearch: React.FC = () => {
  const [keyword, setKeyword] = useState('');
  const [searchResults, setSearchResults] = useState<KnowledgeSearchResult[]>([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [total, setTotal] = useState(0);
  const [hasSearched, setHasSearched] = useState(false);

  const handleSearch = async () => {
    if (!keyword.trim()) {
      return;
    }
    
    setLoading(true);
    setHasSearched(true);
    
    try {
      const response = await axios.get<SearchResponse>('/api/knowledge/search', {
        params: {
          keyword: keyword.trim(),
          page: currentPage,
          size: pageSize
        }
      });
      
      if (response.data.code === 200) {
        setSearchResults(response.data.data.list);
        setTotal(response.data.data.total);
      } else {
        console.error('搜索失败:', response.data.message);
      }
    } catch (error) {
      console.error('搜索请求失败:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
    handleSearch();
  };

  const formatTags = (tags: string) => {
    if (!tags) return [];
    return tags.split(',').filter(tag => tag.trim());
  };

  const formatDate = (dateString: string) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  // 直接渲染HTML内容（包含高亮标记）
  const renderHTML = (content: string) => {
    return { __html: content };
  };

  return (
    <div className="knowledge-search" style={{ padding: '20px' }}>
      <Title level={2}>知识库搜索</Title>
      <Divider />
      
      <div className="search-box" style={{ marginBottom: '20px' }}>
        <Input
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="输入关键词搜索知识库..."
          onPressEnter={handleSearch}
          suffix={
            <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>
              搜索
            </Button>
          }
          style={{ width: '100%' }}
        />
      </div>
      
      {loading && (
        <div style={{ textAlign: 'center', padding: '40px 0' }}>
          <Spin size="large" />
        </div>
      )}
      
      {!loading && searchResults.length === 0 && hasSearched && (
        <Empty description="没有找到匹配的知识文档" />
      )}
      
      {!loading && searchResults.length > 0 && (
        <div className="search-results">
          {searchResults.map(item => (
            <Card 
              key={item.id}
              style={{ marginBottom: '16px' }}
              hoverable
            >
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
                <Title level={4} style={{ margin: 0 }}>
                  <div dangerouslySetInnerHTML={renderHTML(item.title || '无标题')} />
                </Title>
                <Tag color="blue">{item.fileType}</Tag>
              </div>
              
              <Paragraph>
                <div dangerouslySetInnerHTML={renderHTML(item.content)} />
              </Paragraph>
              
              {item.tags && (
                <div style={{ marginBottom: '12px' }}>
                  {formatTags(item.tags).map(tag => (
                    <Tag key={tag} color="green">{tag}</Tag>
                  ))}
                </div>
              )}
              
              <div style={{ display: 'flex', fontSize: '12px', color: '#8c8c8c' }}>
                <Text type="secondary" style={{ marginRight: '16px' }}>创建者: {item.createByName}</Text>
                <Text type="secondary" style={{ marginRight: '16px' }}>创建时间: {formatDate(item.createTime)}</Text>
                {item.updateTime && (
                  <Text type="secondary">更新时间: {formatDate(item.updateTime)}</Text>
                )}
              </div>
            </Card>
          ))}
          
          <div style={{ textAlign: 'center', marginTop: '24px' }}>
            <Pagination
              current={currentPage}
              pageSize={pageSize}
              total={total}
              onChange={handlePageChange}
              showSizeChanger={false}
            />
          </div>
        </div>
      )}
      
      <style>
        {`
          .highlight {
            color: #f5222d;
            font-weight: bold;
          }
        `}
      </style>
    </div>
  );
};

export default KnowledgeSearch; 