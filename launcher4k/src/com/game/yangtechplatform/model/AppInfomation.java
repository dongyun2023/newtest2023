package com.game.yangtechplatform.model;

import java.util.List;

import com.ab.download.DownFile;

import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class AppInfomation extends DownFile {
	private String id;// 应用的id
	private String appTypeId;// 应用的类型id
	private String imageUrl;// 小图图标地址
	private String imageBigUrl;// 大图图标地址

	private String appName;// 应用名称
	private float starLevel;// 星级
	private int downLoadNum;// 下载次数
	private String versionName = "";// 版本名称
	private int versionCode = 0;// 版本号
	private Drawable appIcon = null;// 应用图标
	private String appActivityName = null;// 启动actitvity的类名称
	private String appSize;// app的大小
	private String appSort;// 应用的排名

	/**
	 * 图片下载地址
	 */
	private String appDownUrl;

	/**
	 * 应用的描述
	 */
	private String describe;

	/**
	 * 应用积分
	 */
	private String Jifen;

	private String downFileState;// 应用安装的状态

	private List<ResolveInfo> mathes;

	private String zTId;// 专题id
	private String zTpicture;// 专题图片
	private String zTzhuantiName;// 专题名称

	public String getzTId() {
		return zTId;
	}

	public void setzTId(String zTId) {
		this.zTId = zTId;
	}

	public String getzTpicture() {
		return zTpicture;
	}

	public void setzTpicture(String zTpicture) {
		this.zTpicture = zTpicture;
	}

	public String getzTzhuantiName() {
		return zTzhuantiName;
	}

	public void setzTzhuantiName(String zTzhuantiName) {
		this.zTzhuantiName = zTzhuantiName;
	}

	public List<ResolveInfo> getMathes() {
		return mathes;
	}

	public void setMathes(List<ResolveInfo> mathes) {
		this.mathes = mathes;
	}

	public String getDownFileState() {
		return downFileState;
	}

	public void setDownFileState(String downFileState) {
		this.downFileState = downFileState;
	}

	public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

	public String getImageBigUrl() {
		return imageBigUrl;
	}

	public void setImageBigUrl(String imageBigUrl) {
		this.imageBigUrl = imageBigUrl;
	}

	public String getAppSort() {
		return appSort;
	}

	public void setAppSort(String appSort) {
		this.appSort = appSort;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public String getJifen() {
		return Jifen;
	}

	public void setJifen(String jifen) {
		Jifen = jifen;
	}

	public String getAppActivityName() {
		return appActivityName;
	}

	public void setAppActivityName(String appActivityName) {
		this.appActivityName = appActivityName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDownLoadNum() {
		return downLoadNum;
	}

	public void setDownLoadNum(int downLoadNum) {
		this.downLoadNum = downLoadNum;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getAppDownUrl() {
		return appDownUrl;
	}

	public void setAppDownUrl(String appDownUrl) {
		this.appDownUrl = appDownUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public float getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(float starLevel) {
		this.starLevel = starLevel;
	}

}
