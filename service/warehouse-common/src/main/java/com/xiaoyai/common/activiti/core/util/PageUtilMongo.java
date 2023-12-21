package com.xiaoyai.common.activiti.core.util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class PageUtilMongo<T> implements Serializable {
    private static final long serialVersionUID = 5760097915453738435L;
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 每页显示个数
     */
    private int pageSize;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 结果列表
     */
    private List<T> rows;

    public PageUtilMongo() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    public PageUtilMongo(int currentPage, int pageSize, int totalCount) {
        this.currentPage = currentPage <= 0 ? 1 : currentPage;
        this.pageSize = pageSize <= 0 ? 1 : pageSize;
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 设置结果 及总页数
     */
    public void build(List<T> rows) {
        this.setRows(rows);
        int count = this.getTotalCount();
        int divisor = count / this.getPageSize();
        int remainder = count % this.getPageSize();
        this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}