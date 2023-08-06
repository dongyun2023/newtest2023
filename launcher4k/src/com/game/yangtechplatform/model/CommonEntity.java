package com.game.yangtechplatform.model;

import java.io.Serializable;

public class CommonEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer Status;
	private String ErrorInfo;
	private String data;
	private String systemTime;// 系统时间年月日时分秒
	private String currentYMD;// 当前时间年月日

	public String getCurrentYMD() {
		return currentYMD;
	}

	public void setCurrentYMD(String currentYMD) {
		this.currentYMD = currentYMD;
	}

	public String getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

}
