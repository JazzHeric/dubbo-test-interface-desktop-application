package service;

import java.io.Serializable;

/**
 * @author: Jazz.Heric
 * @date: created in 2021/5/8 16:39
 * @description:
 */
public class PageReq<T> implements Serializable {


    private static final long serialVersionUID = -6720015744986291779L;

    private int pageSize;

    private int pageNum;

    private T queryParam;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public T getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(T queryParam) {
        this.queryParam = queryParam;
    }
}
