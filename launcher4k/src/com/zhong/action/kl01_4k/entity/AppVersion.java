package com.zhong.action.kl01_4k.entity;

/**
 * app�汾��Ϣ
 * 
 * @author sunger
 * 
 */
public class AppVersion {
	// apk����url
	private String apkUrl;
	// apk���°汾
	private String versionCode;
	
	/**
	 * �ļ�����
	 */
	private String fileName;
	
	/**
	 * �ļ���sd�������·��
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