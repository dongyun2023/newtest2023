package com.zhong.action.kl01_4k.entity;

/**
 * app版本信息
 * 
 * @author sunger
 * 
 */
public class AppVersion {
	// apk下载url
	private String apkUrl;
	// apk最新版本
	private String versionCode;
	
	/**
	 * 文件名字
	 */
	private String fileName;
	
	/**
	 * 文件在sd卡的相对路径
	 */
	private String filePath;

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
}