import React, { useState } from 'react';
import { Form, Input, Button, Card, message, Typography } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import styles from './Auth.module.css';
import { userService } from '../../service/userService';

const { Title, Text } = Typography;

const RegisterPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      // 调用注册接口
      const response = await userService.register({
        username: values.username,
        password: values.password
      });

      if (response.success) {
        message.success('注册成功！');
        // 注册成功后跳转到登录页
        navigate('/auth/login', { replace: true });
      } else {
        message.error(response.message || '注册失败，请重试！');
      }
    } catch (error) {
      message.error('注册失败，请重试！');
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
            <Title level={2}>用户注册</Title>
            <Text type="secondary">创建一个新账号</Text>
          </div>
          <Form
            name="register"
            onFinish={onFinish}
            autoComplete="off"
            layout="vertical"
          >
            <Form.Item
              name="username"
              rules={[
                { required: true, message: '请输入用户名！' },
                { min: 3, message: '用户名长度不能小于3位！' }
              ]}
            >
              <Input
                prefix={<UserOutlined />}
                placeholder="用户名"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[
                { required: true, message: '请输入密码！' },
                { min: 8, message: '密码长度不能小于8位！' }
              ]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="密码"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="confirmPassword"
              dependencies={['password']}
              rules={[
                { required: true, message: '请确认密码！' },
                ({ getFieldValue }) => ({
                  validator(_, value) {
                    if (!value || getFieldValue('password') === value) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('两次输入的密码不一致！'));
                  },
                }),
              ]}
            >
              <Input.Password
                prefix={<LockOutlined />}
                placeholder="确认密码"
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
                注册
              </Button>
            </Form.Item>

            <div className={styles.authFooter}>
              <Text>已有账号？</Text>
              <Link to="/auth/login">
                <Button type="link" size="large">
                  立即登录
                </Button>
              </Link>
            </div>
          </Form>
        </Card>
      </motion.div>
    </div>
  );
};

export default RegisterPage; 