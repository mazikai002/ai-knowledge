package com.itheima.backend.common.enums;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    
    USER("user", "用户消息"),
    ASSISTANT("assistant", "AI助手消息");
    
    private final String code;
    private final String desc;
    
    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public static MessageTypeEnum getByCode(String code) {
        for (MessageTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 