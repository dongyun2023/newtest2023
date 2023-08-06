package com.game.yangtechplatform.model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pictrue;// 头像
	private String nickName;// 昵称
	private String userId;// 用户编号
	private String vipType;// VIP类型（0：不是；1：铂金VIP；2：黄金VIP）
	private String integral;// 积分
	private String loginName;// 登陆名称
	private String password;// 登陆密码

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPictrue() {
		return pictrue;
	}

	public void setPictrue(String pictrue) {
		this.pictrue = pictrue;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

}
