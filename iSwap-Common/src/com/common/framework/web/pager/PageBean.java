package com.common.framework.web.pager;

import java.util.ArrayList;
import java.util.List;

import com.common.framework.web.Constant;

/**
 * 分页使用的对象
 * @author hudaowan
 * @version iSwap V5.0
 * @date 2009-2-4 下午05:44:43
 * @Team 数据交换平台研发小组
 */
public class PageBean {

    /**
     * 每页大小
     */
    private int perPage = Constant.DEFAULT_PAGE_SIZE;// Records per page;
    /**
     * 总记录数
     */
    private long total = 0;// Total record;
    /**
     * 当前页
     */
    private int index = 1;// The page num which is loaded from,one based;
    /**
     * 总页数
     */
    private long pageCount = 0;
    private List<String> pageList;
    /**
     * 上一页
     */
    private int upNo;
    /**
     * 下一页
     */
    private int nextNo;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getStart() {
        int startNum = Long.valueOf(this.getPageCount()).intValue();
        if (getIndex() > startNum) {
            setIndex(startNum);
        }
        return (getIndex() - 1) * perPage;
    }

    public int getIndex() {
        if (index <= 0) {
            index = 1;
        }
        return index;
    }

    public long getPageCount() {
        if (total % perPage == 0) {
            pageCount = total / perPage;
        } else {
            pageCount = total / perPage + 1;
        }

        return pageCount;
    }

    public int getNextNo() {
        if (this.pageCount == this.index) {
            nextNo = this.index;
        } else {
            nextNo = this.index + 1;
        }
        return nextNo;
    }

    public int getUpNo() {
        if (this.index == 1) {
            upNo = 1;
        } else {
            upNo = this.index - 1;
        }
        return upNo;
    }

    public List<String> getPageList() {
        pageList = new ArrayList<String>();
        for (int i = 1; i <= this.pageCount; i++) {
            pageList.add(String.valueOf(i));
        }
        return pageList;
    }

}
