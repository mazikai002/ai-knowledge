import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

const HomePage: React.FC = () => {
  return (
    <div style={{ padding: '24px' }}>
      <Title level={2}>欢迎来到首页</Title>
      <p>这是一个受保护的页面，只有登录后才能访问。</p>
    </div>
  );
};

export default HomePage; 