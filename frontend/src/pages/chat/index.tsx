import React, { useState, useEffect } from 'react';
import { Layout, Input, Button, List, Typography, Avatar, Space, Upload, message, Select, Menu, Modal, Input as AntInput, Spin } from 'antd';
import { SendOutlined, RobotOutlined, UserOutlined, InboxOutlined, FileTextOutlined, DeleteOutlined, PlusOutlined, HistoryOutlined, SearchOutlined, ExportOutlined } from '@ant-design/icons';
import { conversationService, Conversation, Message, CreateConversationDTO, SendMessageDTO } from '../../service/conversationService';

const { Content, Sider } = Layout;
const { TextArea } = Input;
const { Title, Text } = Typography;
const { Dragger } = Upload;

// 定义可用的模型列表
const AI_MODELS = [
  { value: 'gpt-4', label: 'GPT-4' },
  { value: 'gpt-3.5-turbo', label: 'GPT-3.5 Turbo' },
  { value: 'claude-3', label: 'Claude 3' },
  { value: 'gemini-pro', label: 'Gemini Pro' },
  { value: 'llama-2', label: 'Llama 2' },
  { value: 'qwen', label: 'Qwen' },
];

interface UploadedFile {
  id: string;
  name: string;
  status: 'processing' | 'done' | 'error';
  progress: number;
}

const ChatPage: React.FC = () => {
  const [conversations, setConversations] = useState<Conversation[]>([]);
  const [currentConversationId, setCurrentConversationId] = useState<number | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputValue, setInputValue] = useState('');
  const [loading, setLoading] = useState(false);
  const [selectedModel, setSelectedModel] = useState('gpt-4');
  const [isNewConversationModalVisible, setIsNewConversationModalVisible] = useState(false);
  const [newConversationTitle, setNewConversationTitle] = useState('');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [isSearching, setIsSearching] = useState(false);
  const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([]);
  const [isLoadingConversations, setIsLoadingConversations] = useState(false);

  // 加载对话列表
  useEffect(() => {
    loadConversations();
  }, []);

  const loadConversations = async () => {
    setIsLoadingConversations(true);
    try {
      const data = await conversationService.getConversations();
      setConversations(data);
    } catch (error) {
      if (error instanceof Error) {
        message.error(error.message);
        // 如果是未登录错误，可以在这里处理重定向到登录页面
        if (error.message.includes('未登录')) {
          // TODO: 重定向到登录页面
        }
      } else {
        message.error('加载对话列表失败，请稍后重试');
      }
    } finally {
      setIsLoadingConversations(false);
    }
  };

  // 创建新对话
  const handleCreateConversation = async () => {
    if (!newConversationTitle.trim()) {
      message.error('请输入对话标题');
      return;
    }

    try {
      const dto: CreateConversationDTO = { title: newConversationTitle };
      const conversation = await conversationService.createConversation(dto);
      setConversations(prev => [conversation, ...prev]);
      setCurrentConversationId(conversation.id);
      setMessages([]);
      setNewConversationTitle('');
      setIsNewConversationModalVisible(false);
    } catch (error) {
      message.error('创建对话失败');
    }
  };

  // 删除对话
  const handleDeleteConversation = async (conversationId: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个对话吗？',
      async onOk() {
        try {
          await conversationService.deleteConversation(conversationId);
          setConversations(prev => prev.filter(c => c.id !== conversationId));
          if (currentConversationId === conversationId) {
            setCurrentConversationId(null);
            setMessages([]);
          }
        } catch (error) {
          message.error('删除对话失败');
        }
      }
    });
  };

  // 搜索对话
  const handleSearch = async () => {
    if (!searchKeyword.trim()) {
      loadConversations();
      return;
    }

    setIsSearching(true);
    try {
      const data = await conversationService.searchConversations(searchKeyword);
      setConversations(data);
    } catch (error) {
      message.error('搜索对话失败');
    } finally {
      setIsSearching(false);
    }
  };

  // 导出对话
  const handleExport = async (conversationId: number) => {
    try {
      const content = await conversationService.exportConversation(conversationId);
      const blob = new Blob([content], { type: 'text/plain' });
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `conversation-${conversationId}.txt`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    } catch (error) {
      message.error('导出对话失败');
    }
  };

  // 发送消息
  const handleSend = async () => {
    if (!inputValue.trim()) return;

    // 如果没有当前对话，创建一个新对话
    if (!currentConversationId) {
      const dto: CreateConversationDTO = { title: inputValue.slice(0, 30) + (inputValue.length > 30 ? '...' : '') };
      try {
        const conversation = await conversationService.createConversation(dto);
        setConversations(prev => [conversation, ...prev]);
        setCurrentConversationId(conversation.id);
      } catch (error) {
        message.error('创建对话失败');
        return;
      }
    }

    setLoading(true);
    try {
      const dto: SendMessageDTO = {
        content: inputValue,
        model: selectedModel
      };
      const aiMessage = await conversationService.sendMessage(currentConversationId!, dto);
      setMessages(prev => [...prev, aiMessage]);
      setInputValue('');
    } catch (error) {
      message.error('发送消息失败');
    } finally {
      setLoading(false);
    }
  };

  // 切换对话
  const handleSwitchConversation = (conversationId: number) => {
    setCurrentConversationId(conversationId);
    const conversation = conversations.find(c => c.id === conversationId);
    if (conversation) {
      setMessages(conversation.messages);
    }
  };

  const uploadProps = {
    name: 'file',
    multiple: true,
    action: '/api/upload', // 后端上传接口
    onChange(info: any) {
      const { status, name, uid } = info.file;
      
      if (status === 'uploading') {
        // 更新上传进度
        setUploadedFiles(prev => {
          const existing = prev.find(f => f.id === uid);
          if (existing) {
            return prev.map(f => f.id === uid ? { ...f, progress: info.file.percent } : f);
          }
          return [...prev, { id: uid, name, status: 'processing', progress: info.file.percent }];
        });
      }
      
      if (status === 'done') {
        message.success(`${name} 上传成功，正在进行向量化处理...`);
        setUploadedFiles(prev => 
          prev.map(f => f.id === uid ? { ...f, status: 'done' } : f)
        );
      } else if (status === 'error') {
        message.error(`${name} 上传失败`);
        setUploadedFiles(prev => 
          prev.map(f => f.id === uid ? { ...f, status: 'error' } : f)
        );
      }
    },
    beforeUpload: (file: File) => {
      const isValidType = [
        'application/pdf',
        'application/msword',
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        'text/plain'
      ].includes(file.type);
      
      if (!isValidType) {
        message.error('只支持 PDF、Word 和 TXT 文件！');
        return false;
      }
      
      const isLt50M = file.size / 1024 / 1024 < 50;
      if (!isLt50M) {
        message.error('文件大小不能超过 50MB！');
        return false;
      }
      
      return true;
    }
  };

  const handleDeleteFile = (fileId: string) => {
    // TODO: 调用后端删除接口
    setUploadedFiles(prev => prev.filter(f => f.id !== fileId));
    message.success('文件已删除');
  };

  return (
    <Layout style={{ height: '100vh', background: '#fff' }}>
      {/* 左侧对话历史 */}
      <Sider width={300} style={{ background: '#fff', borderRight: '1px solid #f0f0f0' }}>
        <div style={{ padding: '16px', borderBottom: '1px solid #f0f0f0' }}>
          <Space direction="vertical" style={{ width: '100%' }}>
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={() => setIsNewConversationModalVisible(true)}
              block
            >
              新建对话
            </Button>
            <AntInput.Search
              placeholder="搜索对话"
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
              onSearch={handleSearch}
              loading={isSearching}
              style={{ width: '100%' }}
            />
          </Space>
        </div>
        <Menu
          mode="inline"
          selectedKeys={currentConversationId ? [currentConversationId.toString()] : []}
          style={{ height: 'calc(100vh - 120px)', overflow: 'auto' }}
        >
          {isLoadingConversations ? (
            <div style={{ padding: '16px', textAlign: 'center' }}>
              <Spin />
            </div>
          ) : conversations.length === 0 ? (
            <div style={{ padding: '16px', textAlign: 'center', color: '#999' }}>
              暂无对话记录
            </div>
          ) : (
            conversations.map(conversation => (
              <Menu.Item
                key={conversation.id}
                onClick={() => handleSwitchConversation(conversation.id)}
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  padding: '8px 16px'
                }}
              >
                <div style={{ flex: 1, overflow: 'hidden' }}>
                  <div style={{ fontWeight: 'bold', marginBottom: '4px' }}>{conversation.title}</div>
                  <div style={{ fontSize: '12px', color: '#999' }}>
                    {conversation.lastMessage.slice(0, 30)}
                    {conversation.lastMessage.length > 30 ? '...' : ''}
                  </div>
                </div>
                <Space>
                  <Button
                    type="text"
                    icon={<ExportOutlined />}
                    onClick={(e) => {
                      e.stopPropagation();
                      handleExport(conversation.id);
                    }}
                  />
                  <Button
                    type="text"
                    danger
                    icon={<DeleteOutlined />}
                    onClick={(e) => {
                      e.stopPropagation();
                      handleDeleteConversation(conversation.id);
                    }}
                  />
                </Space>
              </Menu.Item>
            ))
          )}
        </Menu>
      </Sider>

      {/* 中间聊天区域 */}
      <Content style={{ padding: '24px', display: 'flex', flexDirection: 'column' }}>
        <div style={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'center', 
          marginBottom: '24px'
        }}>
          <Title level={2} style={{ margin: 0 }}>AI 助手</Title>
          <Space>
            <Text>选择模型：</Text>
            <Select
              value={selectedModel}
              onChange={setSelectedModel}
              style={{ width: 180 }}
              options={AI_MODELS}
            />
          </Space>
        </div>
        
        <List
          style={{ 
            flex: 1, 
            overflow: 'auto',
            padding: '20px',
            background: '#f5f5f5',
            borderRadius: '8px',
            marginBottom: '24px'
          }}
          dataSource={messages}
          renderItem={(message) => (
            <List.Item style={{ padding: '8px 0' }}>
              <Space align="start" style={{ width: '100%' }}>
                <Avatar icon={message.type === 'USER' ? <UserOutlined /> : <RobotOutlined />} />
                <div style={{ flex: 1 }}>
                  <div style={{ marginBottom: '4px' }}>
                    <Typography.Text strong>
                      {message.type === 'USER' ? '用户' : 'AI'} ({message.model})
                    </Typography.Text>
                  </div>
                  <div style={{ whiteSpace: 'pre-wrap' }}>{message.content}</div>
                </div>
              </Space>
            </List.Item>
          )}
        />
        
        <div style={{ display: 'flex', gap: '8px' }}>
          <TextArea
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            placeholder={`使用 ${AI_MODELS.find(m => m.value === selectedModel)?.label || selectedModel} 回答您的问题...`}
            autoSize={{ minRows: 2, maxRows: 6 }}
            onPressEnter={(e) => {
              if (!e.shiftKey) {
                e.preventDefault();
                handleSend();
              }
            }}
            style={{ flex: 1 }}
          />
          <Button 
            type="primary" 
            icon={<SendOutlined />} 
            onClick={handleSend}
            loading={loading}
            style={{ height: 'auto' }}
          >
            发送
          </Button>
        </div>
      </Content>
      
      {/* 右侧文档上传区域 */}
      <Sider width={300} style={{ background: '#fff', padding: '24px', borderLeft: '1px solid #f0f0f0' }}>
        <Title level={4}>知识库文档</Title>
        <Dragger {...uploadProps} style={{ marginBottom: '24px' }}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或拖拽文件到此区域上传</p>
          <p className="ant-upload-hint">
            支持 PDF、Word、TXT 格式，单个文件最大 50MB
          </p>
        </Dragger>

        <List
          dataSource={uploadedFiles}
          renderItem={file => (
            <List.Item
              actions={[
                <Button 
                  type="text" 
                  danger 
                  icon={<DeleteOutlined />}
                  onClick={() => handleDeleteFile(file.id)}
                />
              ]}
            >
              <List.Item.Meta
                avatar={<FileTextOutlined style={{ fontSize: '24px', color: '#1890ff' }} />}
                title={file.name}
                description={
                  file.status === 'processing' ? (
                    <Text type="secondary">{`处理中 ${Math.round(file.progress || 0)}%`}</Text>
                  ) : file.status === 'done' ? (
                    <Text type="success">已完成向量化</Text>
                  ) : (
                    <Text type="danger">处理失败</Text>
                  )
                }
              />
            </List.Item>
          )}
        />
      </Sider>

      {/* 新建对话弹窗 */}
      <Modal
        title="新建对话"
        open={isNewConversationModalVisible}
        onOk={handleCreateConversation}
        onCancel={() => {
          setIsNewConversationModalVisible(false);
          setNewConversationTitle('');
        }}
      >
        <Input
          placeholder="请输入对话标题"
          value={newConversationTitle}
          onChange={(e) => setNewConversationTitle(e.target.value)}
        />
      </Modal>
    </Layout>
  );
};

export default ChatPage; 