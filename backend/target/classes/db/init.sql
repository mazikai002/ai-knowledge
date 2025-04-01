-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_knowledge DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ai_knowledge;

-- 创建对话表
CREATE TABLE IF NOT EXISTS conversations (
    id BIGINT AUTO_INCREMENT COMMENT '主键ID',
    title VARCHAR(255) NOT NULL COMMENT '对话标题',
    last_message TEXT COMMENT '最后一条消息',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    updated_at DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_created_at (created_at),
    INDEX idx_updated_at (updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话表';

-- 创建消息表
CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT COMMENT '主键ID',
    content TEXT NOT NULL COMMENT '消息内容',
    type VARCHAR(10) NOT NULL COMMENT '消息类型：USER/AI',
    model VARCHAR(50) COMMENT 'AI模型名称',
    created_at DATETIME NOT NULL COMMENT '创建时间',
    conversation_id BIGINT NOT NULL COMMENT '对话ID',
    PRIMARY KEY (id),
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_created_at (created_at),
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- 添加表注释
ALTER TABLE conversations COMMENT = '对话表';
ALTER TABLE messages COMMENT = '消息表';

-- 添加字段注释
ALTER TABLE conversations MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT '主键ID';
ALTER TABLE conversations MODIFY COLUMN title VARCHAR(255) NOT NULL COMMENT '对话标题';
ALTER TABLE conversations MODIFY COLUMN last_message TEXT COMMENT '最后一条消息';
ALTER TABLE conversations MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '创建时间';
ALTER TABLE conversations MODIFY COLUMN updated_at DATETIME NOT NULL COMMENT '更新时间';

ALTER TABLE messages MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT '主键ID';
ALTER TABLE messages MODIFY COLUMN content TEXT NOT NULL COMMENT '消息内容';
ALTER TABLE messages MODIFY COLUMN type VARCHAR(10) NOT NULL COMMENT '消息类型：USER/AI';
ALTER TABLE messages MODIFY COLUMN model VARCHAR(50) COMMENT 'AI模型名称';
ALTER TABLE messages MODIFY COLUMN created_at DATETIME NOT NULL COMMENT '创建时间';
ALTER TABLE messages MODIFY COLUMN conversation_id BIGINT NOT NULL COMMENT '对话ID';

-- 创建索引
CREATE INDEX idx_conversation_title ON conversations(title);
CREATE INDEX idx_conversation_last_message ON conversations(last_message(100));
CREATE INDEX idx_message_type ON messages(type);
CREATE INDEX idx_message_model ON messages(model);

-- 添加测试数据
INSERT INTO conversations (title, last_message, created_at, updated_at)
VALUES ('测试对话1', '你好，这是一个测试对话', NOW(), NOW());

INSERT INTO messages (content, type, model, created_at, conversation_id)
VALUES ('你好，这是一个测试对话', 'USER', 'gpt-4', NOW(), 1);

INSERT INTO messages (content, type, model, created_at, conversation_id)
VALUES ('你好！我是AI助手，很高兴为你服务。', 'AI', 'gpt-4', NOW(), 1); 