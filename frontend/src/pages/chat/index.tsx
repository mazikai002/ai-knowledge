import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

const ChatPage: React.FC = () => {
  return (
    <div style={{ padding: '24px' }}>
      <Title level={2}>聊天页面</Title>
      <p>这是一个受保护的聊天页面，只有登录后才能访问。</p>
    </div>
  );
};

export default ChatPage; 