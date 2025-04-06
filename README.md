# AI 知识库系统

基于 Spring Boot + React 的智能知识库系统，支持文档管理、知识检索和 AI 对话等功能。

## 功能特性

- 📚 知识库管理
  - 支持多种格式文档上传和管理（PDF、Word、TXT等）
  - 文档分类和标签管理
  - 知识检索和全文搜索
  - 文档向量化与语义搜索
- 🤖 AI 对话
  - 支持多种 AI 模型（GPT-4、Claude-3、Gemini等）
  - 上下文对话管理
  - 知识库增强的问答能力
  - 历史记录查看与导出
- 👥 用户系统
  - 用户注册和登录
  - 权限管理
  - 个人中心
- 🔄 API版本化
  - 标准化API路径（/api/v1/*）
  - 版本化API管理

## 技术栈

### 后端
- Spring Boot 2.7.0
- MyBatis
- MySQL 8.0
- Maven
- Elasticsearch
- MinIO对象存储
- Apache Tika文档解析
- JWT认证

### 前端
- React 18
- TypeScript
- Ant Design
- Vite
- Redux Toolkit

## 项目结构

```
ai-knowledge/
├── backend/                # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/itheima/backend/
│   │   │   │       ├── common/          # 通用类
│   │   │   │       │   ├── constant/    # 常量类
│   │   │   │       │   ├── enums/       # 枚举类
│   │   │   │       │   └── utils/       # 工具类
│   │   │   │       ├── config/          # 配置类
│   │   │   │       ├── controller/      # 控制器
│   │   │   │       │   └── v1/          # v1版本API接口
│   │   │   │       ├── repository/      # 数据访问层
│   │   │   │       │   ├── mybatis/     # MyBatis数据访问实现
│   │   │   │       │   ├── es/          # Elasticsearch数据访问实现
│   │   │   │       │   └── minio/       # MinIO对象存储实现
│   │   │   │       ├── model/           # 数据模型
│   │   │   │       │   ├── dto/         # 数据传输对象
│   │   │   │       │   ├── entity/      # 实体类
│   │   │   │       │   ├── vo/          # 视图对象
│   │   │   │       │   └── document/    # ES文档模型
│   │   │   │       └── service/         # 服务层
│   │   │   │           └── impl/        # 服务实现类
│   │   │   └── resources/
│   │   │       ├── mapper/              # MyBatis映射文件
│   │   │       └── application.yml      # 配置文件
│   └── pom.xml                          # Maven配置
└── frontend/                # 前端项目
    ├── src/
    │   ├── api/           # API接口
    │   ├── assets/        # 静态资源
    │   ├── components/    # 组件
    │   ├── pages/         # 页面
    │   ├── store/         # 状态管理
    │   ├── types/         # 类型定义
    │   └── utils/         # 工具函数
    └── package.json       # 项目配置
```

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.8+
- Elasticsearch 7.x
- MinIO

### 后端启动
```bash
cd backend
mvn spring-boot:run
```

### 前端启动
```bash
cd frontend
npm install
npm run dev
```

## 最新更新
查看 [CHANGELOG.md](CHANGELOG.md) 了解最新版本变更。

## 架构设计

本项目遵循阿里巴巴Java开发手册规范，采用传统单体应用架构设计：

- **控制层(Controller)**: 处理HTTP请求，负责参数校验和结果封装
- **服务层(Service)**: 处理业务逻辑，聚合多个数据访问操作
- **数据访问层(Repository)**: 封装对各种数据源的访问，包括：
  - MySQL数据库(MyBatis)
  - Elasticsearch搜索引擎
  - MinIO对象存储

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情
