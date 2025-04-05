import React from 'react';
import { Layout, theme } from 'antd';
import KnowledgeSearch from '../../components/KnowledgeSearch';

const { Header, Content } = Layout;

const KnowledgePage: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <Layout style={{ height: '100vh' }}>
      <Header>
        <div style={{ color: 'white', fontSize: 'large' }}>企业AI知识库</div>
      </Header>
      <Layout>
        <Content
          style={{
            padding: 24,
            margin: 0,
            minHeight: 280,
            background: colorBgContainer,
            overflow: 'auto'
          }}
        >
          <KnowledgeSearch />
        </Content>
      </Layout>
    </Layout>
  );
};

export default KnowledgePage; 