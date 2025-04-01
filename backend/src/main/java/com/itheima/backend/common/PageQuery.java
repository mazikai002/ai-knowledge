package com.itheima.backend.common;

import lombok.Data;

/**
 * 分页查询参数类
 */
@Data
public class PageQuery {
    
    /**
     * 当前页码
     */
    private int pageNum = 1;
    
    /**
     * 每页大小
     */
    private int pageSize = 10;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方式（asc/desc）
     */
    private String orderType = "desc";
} 