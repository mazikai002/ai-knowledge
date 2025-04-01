# AI 知识库系统

基于 Spring Boot + Vue3 的智能知识库系统，支持文档管理、知识检索和 AI 对话等功能。

## 功能特性

- 📚 知识库管理
  - 支持多种格式文档上传和管理
  - 文档分类和标签管理
  - 知识检索和全文搜索
- 🤖 AI 对话
  - 支持多种 AI 模型（GPT-4、Claude-3等）
  - 上下文对话管理
  - 历史记录查看
- 👥 用户系统
  - 用户注册和登录
  - 权限管理
  - 个人中心

## 技术栈

### 后端
- Spring Boot 2.7.0
- MyBatis
- MySQL
- Maven
- Lombok
- Jakarta Validation

### 前端
- Vue 3
- TypeScript
- Element Plus
- Vite
- Pinia

## 项目结构

```
ai-knowledge/
├── backend/                # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/itheima/backend/
│   │   │   │       ├── common/          # 通用类
│   │   │   │       ├── config/          # 配置类
│   │   │   │       ├── controller/      # 控制器
│   │   │   │       ├── mapper/          # MyBatis映射
│   │   │   │       ├── model/           # 数据模型
│   │   │   │       │   ├── dto/         # 数据传输对象
│   │   │   │       │   ├── entity/      # 实体类
│   │   │   │       │   ├── vo/          # 视图对象
│   │   │   │       │   └── converter/   # 对象转换器
│   │   │   │       └── service/         # 服务层
│   │   │   └── resources/
│   │   │       ├── mapper/              # MyBatis映射文件
│   │   │       └── application.yml      # 配置文件
│   └── pom.xml                          # Maven配置
└── frontend/                # 前端项目
    ├── src/
    │   ├── api/           # API接口
    │   ├── assets/        # 静态资源
    │   ├── components/    # 组件
    │   ├── router/        # 路由配置
    │   ├── stores/        # 状态管理
    │   ├── types/         # 类型定义
    │   └── views/         # 页面
    └── package.json       # 项目配置
```

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.8+

### 后端启动
1. 创建数据库
```sql
CREATE DATABASE ai_knowledge DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改配置
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_knowledge
    username: your_username
    password: your_password
```

3. 启动项目
```bash
cd backend
mvn spring-boot:run
```

### 前端启动
1. 安装依赖
```bash
cd frontend
npm install
```

2. 启动开发服务器
```bash
npm run dev
```

## 开发计划

- [ ] 支持更多文档格式
- [ ] 优化知识检索算法
- [ ] 添加数据可视化功能
- [ ] 支持团队协作
- [ ] 添加文档版本控制

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情
