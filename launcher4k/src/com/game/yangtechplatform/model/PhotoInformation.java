package com.game.yangtechplatform.model;

import android.graphics.Bitmap;

public class PhotoInformation {
	private String photoId;// 图片的id
	private String localImagePath;// 图片的本地路径
	private String imageName;// 图片的名称
	private String imageSize;// 图片的大小
	private String wetImagePath;// 图片的网络路径
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getLocalImagePath() {
		return localImagePath;
	}

	public void setLocalImagePath(String localImagePath) {
		this.localImagePath = localImagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getWetImagePath() {
		return wetImagePath;
	}

	public void setWetImagePath(String wetImagePath) {
		this.wetImagePath = wetImagePath;
	}

}
