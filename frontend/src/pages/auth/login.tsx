import React, { useState } from 'react';
import { Form, Input, Button, Card, message, Typography } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { motion } from 'framer-motion';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import styles from './Auth.module.css';
import { userService } from '../../service/userService';

const { Title, Text } = Typography;

const LoginPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      const response = await userService.login({
        username: values.username,
        password: values.password
      });

      if (response.success) {
        // 登录成功，存储用户信息
        localStorage.setItem('userInfo', JSON.stringify(response.data));
        message.success(response.message || '登录成功！');
        // 直接跳转到聊天页面（AI对话框界面）
        navigate('/chat', { replace: true });
      } else {
        message.error(response.message || '登录失败，请重试！');
      }
    } catch (error) {
      message.error('登录失败，请重试！');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.authContainer}>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        <Card className={styles.authCard}>
          <div className={styles.authHeader}>
            <Title level={2}>欢迎登录</Title>
            <Text type="secondary">登录您的账号以继续</Text>
          </div>
          <Form
            name="login"
            onFinish={onFinish}
            autoComplete="off"
            layout="vertical"
          >
            <Form.Item
              name="username"
              rules={[{ required: true, message: '请输入用户名！' }]}
            >
              <Input
                prefix={<UserOutlined />}
                placeholder="用户名"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{ required: true, message: '请输入密码！' }]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="密码"
                size="large"
              />
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                block
                size="large"
              >
                登录
              </Button>
            </Form.Item>

            <div className={styles.authFooter}>
              <Text>还没有账号？</Text>
              <Link to="/auth/register">
                <Button type="link" size="large">
                  立即注册
                </Button>
              </Link>
            </div>
          </Form>
        </Card>
      </motion.div>
    </div>
  );
};

export default LoginPage; 