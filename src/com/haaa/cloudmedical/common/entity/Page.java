package com.haaa.cloudmedical.common.entity;

import java.util.List;

public class Page {
	private int recordCount; // 总记录数
	private int pageCount; // 总页数
	private int pageSize = 10; // 显示记录数
	private int pageNo = 1; // 当前页码
	private boolean flag = true;
	private List<?> data; // 分页结果集

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Page [recordCount=" + recordCount + ", pageCount=" + pageCount + ", pageSize=" + pageSize + ", pageNo="
				+ pageNo + ", flag=" + flag + ", data=" + data + "]";
	}

}
