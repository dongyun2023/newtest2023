package com.game.yangtechplatform.globle;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ab.download.DownFile;
import com.game.yangtechplatform.model.AppInfomation;

import android.os.Environment;

public class Constant {

	public static final boolean DEBUG = true;
	public static final String sharePath = "andbase_share";
	public static final String USERSID = "user";

	// cookies
	public static final String USERNAMECOOKIE = "cookieName";
	public static final String USERPASSWORDCOOKIE = "cookiePassword";
	public static final String USERPASSWORDREMEMBERCOOKIE = "cookieRemember";
	public static final String ISFIRSTSTART = "isfirstStart";

	// 连接超时
	public static final int timeOut = 12000;
	// 建立连接
	public static final int connectOut = 12000;
	// 获取数据
	public static final int getOut = 60000;

	// 1表示已下载完成
	public static final int downloadComplete = 1;
	// 1表示未开始下载
	public static final int undownLoad = 0;
	// 2表示已开始下载
	public static final int downInProgress = 2;
	// 3表示下载暂停
	public static final int downLoadPause = 3;

	public static final String BASEURL = "http://www.amsoft.cn/";

	// jfa97P4HIhjxrAgfUdq1NoKC
	public final static String APIKEY = "jfa97P4HIhjxrAgfUdq1NoKC";

	public final static String baseUrl = "youngTechPlatform";
	public final static String fileUrl = "file";
	public final static String imgAppUrl = "image/app";
	public final static String paiHangSaveUrl = "image/paihang";
	public static final String BEIANCODE = "beiancode";// 备案号
	public static final String MERCHANTNAME = "merchantname";// 经营户名称

	/**
	 * 应用所耗流量值
	 */
	public static Long AppWebTraficc = 0l;

	// 应用相关参数
	public static final String APP_ID = "appId";// 应用id
	public static final String APP_TYPE = "appType";// 应用类型

	public static String Project_SDcard_Save_Path = null;// 工程路径

	/**
	 * 存放所有的下载的应用的downfile
	 */
	public static Map<String, AppInfomation> downFileMaps = new HashMap<String, AppInfomation>();

	/**
	 * 全局当前选中的应用
	 */
	public static AppInfomation currentAppInformation = new AppInfomation();

	/**
	 * 当前选中的viewpager的索引
	 */
	public static int currentViewpagerPostion = 0;

	// 消息推送相关
	public static final String DEVICE_ID_KEY = "deviceId";
	public static final String USER_ID_KEY = "userId";
	public static final String CHANNEL_ID_KEY = "channelId";
	public static final String APP_ID_KEY = "appId";

}
