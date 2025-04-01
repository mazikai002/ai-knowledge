import axios, { AxiosError } from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
});

// 添加请求拦截器
axiosInstance.interceptors.request.use(
    (config) => {
        console.log('发送请求:', config.url);
        return config;
    },
    (error) => {
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

// 添加响应拦截器
axiosInstance.interceptors.response.use(
    (response) => {
        console.log('收到响应:', response.config.url, response.status);
        return response;
    },
    (error: AxiosError) => {
        console.error('响应错误:', {
            url: error.config?.url,
            status: error.response?.status,
            data: error.response?.data,
            message: error.message
        });
        return Promise.reject(error);
    }
);

export interface Conversation {
    id: number;
    title: string;
    lastMessage: string;
    createdAt: string;
    updatedAt: string;
    messages: Message[];
}

export interface Message {
    id: number;
    content: string;
    type: 'USER' | 'AI';
    model: string;
    createdAt: string;
}

export interface CreateConversationDTO {
    title: string;
}

export interface SendMessageDTO {
    content: string;
    model: string;
}

export const conversationService = {
    // 获取所有对话
    getConversations: async (): Promise<Conversation[]> => {
        try {
            const response = await axiosInstance.get('/conversation/list');
            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error)) {
                const axiosError = error as AxiosError;
                if (axiosError.response?.status === 401) {
                    throw new Error('未登录或登录已过期，请重新登录');
                } else if (axiosError.response?.status === 403) {
                    throw new Error('没有权限访问对话列表');
                } else if (axiosError.response?.status === 404) {
                    throw new Error('对话列表接口不存在');
                } else if (axiosError.response?.status === 500) {
                    throw new Error('服务器内部错误，请稍后重试');
                }
            }
            throw new Error('获取对话列表失败，请检查网络连接');
        }
    },

    // 创建新对话
    createConversation: async (dto: CreateConversationDTO): Promise<Conversation> => {
        const response = await axiosInstance.post('/conversation/create', dto);
        return response.data;
    },

    // 删除对话
    deleteConversation: async (id: number): Promise<void> => {
        await axiosInstance.delete(`/conversation/${id}`);
    },

    // 发送消息
    sendMessage: async (conversationId: number, dto: SendMessageDTO): Promise<Message> => {
        const response = await axiosInstance.post(`/conversation/${conversationId}/message`, dto);
        return response.data;
    },

    // 搜索对话
    searchConversations: async (keyword: string): Promise<Conversation[]> => {
        const response = await axiosInstance.get('/conversation/search', {
            params: { keyword }
        });
        return response.data;
    },

    // 导出对话
    exportConversation: async (id: number): Promise<string> => {
        const response = await axiosInstance.get(`/conversation/${id}/export`);
        return response.data;
    }
}; 