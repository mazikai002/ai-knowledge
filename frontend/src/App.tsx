import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/home/index';
import ChatPage from './pages/chat/index';
import LoginPage from './pages/auth/login';
import RegisterPage from './pages/auth/register';
import AuthGuard from './components/AuthGuard';
import Background from './components/Background';

const App: React.FC = () => {
  return (
    <>
      <Background />
      <Router>
        <Routes>
          {/* 公开路由 */}
          <Route path="/auth/login" element={<LoginPage />} />
          <Route path="/auth/register" element={<RegisterPage />} />

          {/* 受保护的路由 */}
          <Route
            path="/home"
            element={
              <AuthGuard>
                <HomePage />
              </AuthGuard>
            }
          />
          <Route
            path="/chat"
            element={
              <AuthGuard>
                <ChatPage />
              </AuthGuard>
            }
          />

          {/* 根路径重定向到登录页面 */}
          <Route path="/" element={<Navigate to="/auth/login" replace />} />

          {/* 404 页面重定向到登录页面 */}
          <Route path="*" element={<Navigate to="/auth/login" replace />} />
        </Routes>
      </Router>
    </>
  );
};

export default App;
