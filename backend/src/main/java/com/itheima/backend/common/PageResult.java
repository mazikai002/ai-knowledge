package com.itheima.backend.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 分页响应结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    
    /**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 每页大小
     */
    private int pageSize;
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 总页数
     */
    private int pages;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 是否有下一页
     */
    private boolean hasNext;
    
    /**
     * 是否有上一页
     */
    private boolean hasPrevious;
    
    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> create(int pageNum, int pageSize, long total, List<T> list) {
        int pages = (int) Math.ceil((double) total / pageSize);
        return new PageResult<>(
            pageNum,
            pageSize,
            total,
            pages,
            list,
            pageNum < pages,
            pageNum > 1
        );
    }
} 