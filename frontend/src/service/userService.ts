import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// 创建axios实例
const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,  // 允许跨域请求携带cookie
  headers: {
    'Content-Type': 'application/json'
  }
});

export interface LoginRequest {
  username: string;
  password: string;
}

export interface User {
  id: number;
  username: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T | null;
}

export const userService = {
  async login(loginData: LoginRequest): Promise<ApiResponse<User>> {
    try {
      const response = await axiosInstance.post('/user/login', loginData);
      return response.data;
    } catch (error: any) {
      return {
        success: false,
        message: error.response?.data?.message || '登录失败，请重试',
        data: null
      };
    }
  },

  async register(registerData: LoginRequest): Promise<ApiResponse<User>> {
    try {
      const response = await axiosInstance.post('/user/register', registerData);
      return response.data;
    } catch (error: any) {
      return {
        success: false,
        message: error.response?.data?.message || '注册失败，请重试',
        data: null
      };
    }
  }
}; 