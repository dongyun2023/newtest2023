package com.game.yangtechplatform.net;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.game.yangtechplatform.globle.Constant;
import com.game.yangtechplatform.model.AppInfomation;
import com.game.yangtechplatform.model.User;
import com.game.yangtechplatform.utils.FileUtils;
import com.game.yangtechplatform.utils.L;
import com.game.yangtechplatform.utils.VersionManager.AppVersion;
import com.google.gson.Gson;

import com.zhong.action.kl01_4k.*;
/**
 * 连接界面与后台
 * 
 * @author Administrator
 * 
 */

public class Control {

	private static final String RECEVERINFO = "orderInfo";
	private static final String RECEVERINFODELETE = "receiInfoDelete";
	private static final String RECEVERINFOEDIT = "receiInfoEdit";
	private static final String PROCITYAREA = "procityarea";
	private static Control instance;
	private Context mContext;
	// private static String test1 = "http://120.25.223.215:9094/";// 电商正式
	// private static String test2 = "http://120.25.223.215:9092/";// 结算正式

	/**
	 * 服务器地址
	 */
	// public static String baseurl = "http://192.168.1.45:8099/";

	/**
	 * 云服务器地址///192.168.1.45:8788 登陆备用
	 */
	public static String baseurl = "http://112.74.215.184:9097/";///"http://192.168.1.45:9097/";//

	// public static String baseurl ="http://125.69.76.146:18080/rest/";
	public static String SDCARD_URL = "";

	private Control(Context context) {
		this.mContext = context;
		SDCARD_URL = Environment.getExternalStorageDirectory().getPath();
	}

	public static Control instance(Context context) {
		if (instance == null) {
			instance = new Control(context);
		}
		return instance;
	}

	public List<AppAllSerialInfo>  getAllSerial() {
		List<AppAllSerialInfo>  tlist=null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "getAllSerial");
			String postData = new Gson().toJson(map);
			L.e("getAllSerial提交", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("getAllSerial返回", str);
			// 将返回的数据变成login对象、
			if (!TextUtils.isEmpty(str))
			{
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) 
				{
					String datas = resultJson.optString("series");
					if (!TextUtils.isEmpty(datas)) 
					{
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
						tlist = new ArrayList<AppAllSerialInfo>();
						for (int i = 0; i < iSize; i++) 
						{
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppAllSerialInfo t = new AppAllSerialInfo();
							t.id=jsonObj.optString("id");
							t.serial_name=jsonObj.optString("serial_name");
							t.serial_picture=jsonObj.optString("serial_picture");
							tlist.add(t);
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tlist;
	}
	
	public String  getSerialID() {
		String id="";
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "getAllSerial");
			String postData = new Gson().toJson(map);
			L.e("test更新提交数据1", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("test更新返回数据", str);
			// 将返回的数据变成login对象、
			if (!TextUtils.isEmpty(str))
			{
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) 
				{				
					String datas = resultJson.optString("series");
					if (!TextUtils.isEmpty(datas)) 
					{						
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();				
						for (int i = 0; i < iSize; i++) 
						{
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppAllSerialInfo t = new AppAllSerialInfo();
							t.id=jsonObj.optString("id");
							t.pId=jsonObj.optString("pId");
							
							if(t.pId.equals("79cf623935124864b919fa1b37d1c03a")) ///
							{
								//L.e("getpid111==", t.pId);
								return t.id;
							}
							t.serial_name=jsonObj.optString("serial_name");
							t.serial_picture=jsonObj.optString("serial_picture");
					
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return id;
	}
	
	public List<AppIconTypeSerial>  getAllTypeBySerial(String id) {
		List<AppIconTypeSerial>  tlist=null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("series_id", id);
			map.put("method", "getAllTypeBySerial");
			String postData = new Gson().toJson(map);
			L.e("getAllTypeBySerial提交", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("getAllTypeBySerial返回", str);
			// 将返回的数据变成login对象、
			if (!TextUtils.isEmpty(str))
			{
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) 
				{
					String datas = resultJson.optString("types");
					//L.e("type00=ok=", ""+datas);
					if (!TextUtils.isEmpty(datas)) 
					{
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
					//	L.e("type11=ok=", ""+iSize);
						tlist = new ArrayList<AppIconTypeSerial>();
						for (int i = 0; i < iSize; i++) 
						{
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppIconTypeSerial t = new AppIconTypeSerial();
							t.id=jsonObj.optString("id");
							t.typeSmall=jsonObj.optString("typeSmall");
							t.typeName=jsonObj.optString("typeName");
							t.typeBig=jsonObj.optString("typeBig");
							t.seriesId=jsonObj.optString("seriesId");
							t.isShow=jsonObj.optString("isShow");
							t.logPath=jsonObj.optString("logPath");
							t.photoGraph=jsonObj.optString("photoGraph");
						//	L.e("type=ok", ""+t);
							tlist.add(t);
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tlist;
	}
	

	public List<GameAppInfo>  getAllAppByType (String id) {
		List<GameAppInfo>  tlist=null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("type_id", id);
			map.put("method", "getAllAppByType");
			String postData = new Gson().toJson(map);
			L.e("getAllAppByType提交", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("getAllAppByType返回", str);
			// 将返回的数据变成login对象、
			if (!TextUtils.isEmpty(str))
			{
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) 
				{
					String datas = resultJson.optString("apps");
					//L.e("type00=ok=", ""+datas);
					if (!TextUtils.isEmpty(datas)) 
					{
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
					//	L.e("type11=ok=", ""+iSize);
						tlist = new ArrayList<GameAppInfo>();
						for (int i = 0; i < iSize; i++) 
						{
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							GameAppInfo t = new GameAppInfo();
							t.id=jsonObj.optString("id");
							t.app_package=jsonObj.optString("app_package");
							t.details=jsonObj.optString("details");
							t.app_link=jsonObj.optString("app_link");
							t.name=jsonObj.optString("name");
							t.series_id=jsonObj.optString("series_id");
							t.pictrue_big=jsonObj.optString("pictrue_big");
							t.pictrue_small=jsonObj.optString("pictrue_small");
							t.photoGraph=jsonObj.optString("photoGraph");
							//L.e("type=ok", ""+t);
							tlist.add(t);
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return tlist;
	}
	
	//////////////////////////////////////////////
	/**
	 * 版本检查更新
	 * 
	 * @param os
	 */
	public AppVersion versionCheck() {
		AppVersion version = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "getAppByPackage");
			String postData = new Gson().toJson(map);
			L.e("版本更新提交数据", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("版本更新返回数据", str);
			// 将返回的数据变成login对象、
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					String datas = resultJson.optString("app");
					if (!TextUtils.isEmpty(datas)) {
						version = new AppVersion();
						JSONObject jsonObject = new JSONObject(datas);
						// 设置文件url
						String apkUrl = jsonObject.optString("address");
						String apkName = apkUrl.substring(apkUrl
								.lastIndexOf("/") + 1);
						version.setApkUrl(apkUrl);
						version.setFilePath(Constant.baseUrl);
						// 设置文件名
						version.setFileName(apkName);
						// 设置app当前版本号
						version.setVersionCode(jsonObject
								.optString("versionCode"));
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取应用详情推荐列表信息
	 * 
	 * @return
	 */
	public List<AppInfomation> getAppInfomations(String type) {
		List<AppInfomation> result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("appTypeId", type + "");
			map.put("method", "getRankByAppType");
			String postData = new Gson().toJson(map);
			L.e("获取应用详情推荐列表提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取应用详情推荐列表返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					String datas = resultJson.optString("rankApps");
					if (!TextUtils.isEmpty(datas)) {
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
						result = new ArrayList<AppInfomation>();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setId(jsonObj.optString("appId"));
							appInfomation.setAppName(jsonObj
									.optString("appName"));
							appInfomation.setPakageName(jsonObj
									.optString("appPackage"));
							appInfomation.setImageUrl(jsonObj
									.optString("appIconSmall"));
							appInfomation.setStarLevel(jsonObj
									.optInt("appRank"));
							appInfomation.setDownLoadNum(jsonObj
									.optInt("downloadTime"));
							appInfomation.setJifen(jsonObj
									.optString("integral"));
							appInfomation.setVersionName(jsonObj
									.optString("versionName"));
							appInfomation.setVersionCode(jsonObj
									.optInt("versionCode"));
							appInfomation.setAppSize(jsonObj
									.optString("appSize"));
							result.add(appInfomation);
						}
					}
				} else {
					return result;
				}
			}

			// result = new ArrayList<AppInfomation>();
			// for (int i = 0; i < 2; i++) {
			// AppInfomation appInfomation = null;
			// switch (i) {
			// case 0:
			// appInfomation = new AppInfomation();
			// appInfomation.setId(i + 1);
			// appInfomation.setAppName("小说阅读");
			// appInfomation
			// .setImageUrl("http://img5.duitang.com/uploads/item/201309/17/20130917112349_AFym5.png");
			// appInfomation.setStarLevel(3);
			// appInfomation.setDownLoadNum(300);
			// break;
			// case 1:
			// appInfomation = new AppInfomation();
			// appInfomation.setId(i + 1);
			// appInfomation.setAppName("汽车头条");
			// appInfomation.setDownLoadNum(248);
			// appInfomation
			// .setImageUrl("http://img2.imgtn.bdimg.com/it/u=1713626760,3656959926&fm=21&gp=0.jpg");
			// appInfomation.setStarLevel(3);
			// break;
			// }
			// result.add(appInfomation);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取平台的应用列表的包名称
	 * 
	 * @return
	 */
	public List<AppInfomation> getPlatformPackageName() {
		List<AppInfomation> result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "getAllAppPackage");
			String postData = new Gson().toJson(map);
			L.e("获取平台的应用列表提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取平台的应用列表返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					String datas = resultJson.optString("result");
					if (!TextUtils.isEmpty(datas)) {
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
						result = new ArrayList<AppInfomation>();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setPakageName(jsonObj
									.optString("appPackage"));
							appInfomation.setAppActivityName(jsonObj
									.optString("activityName"));
							result.add(appInfomation);
						}
					}
				} else {
					return result;
				}
			}

			// result = new ArrayList<AppInfomation>();
			// for (int i = 0; i < 3; i++) {
			// AppInfomation appInfomation = new AppInfomation();
			// switch (i) {
			// case 0:
			// appInfomation.setPackageName("com.aikan");
			// appInfomation
			// .setAppActivityName("com.dzbook.activity.LogoActivity");
			// break;
			// case 1:
			// appInfomation.setPackageName("com.taoche.qctt");
			// appInfomation
			// .setAppActivityName("com.bjzy.qctt.WelComeActivity");
			// break;
			// case 2:
			// appInfomation.setPackageName("com.taoke.qr");
			// break;
			// }
			// result.add(appInfomation);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过id获取应用的详情
	 * 
	 * @param id
	 *            应用的id
	 * @return
	 */
	public AppInfomation getAppInfomationDetail(String id) {
		AppInfomation result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", id);
			map.put("method", "getAppMessage");
			String postData = new Gson().toJson(map);
			L.e("获取应用详情提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取应用详情返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = new AppInfomation();
					result.setId(id);
					result.setAppName(resultJson.optString("appName"));
					result.setImageUrl(resultJson.optString("appIconSmall"));
					result.setStarLevel(resultJson.optInt("appRank"));
					result.setDownLoadNum(resultJson.optInt("downloadTime"));
					result.setAppDownUrl(resultJson.optString("address"));
					// result.setAppDownUrl("http://p.gdown.baidu.com/4929ce73941f8f1b0f7fb7522d8813a3b92ede129cb37738fa13a73999c2c209f0c7055751f974881048f5fe4fd8e83d6c5ca9aa5d57817177b37bc7d4128630a081804a517c7e93a76e1488d0ae9e877d7cc38d819143f7911764f2be9fd50acae56370c449b4f5607f1dbb5b091a37f65320d6c81ca9e3707e362f7988aeeb9fd01649591e31bc931313816558caeb0bc79b0a56972dd75952024e4d0fd2b87ac3ff47dc85838bef91466761ed39f2f44f1c0feeca8ac7c4a3273986771ac31b331c2eb60575dabd8d6307ed93f12b2b54890ef97a63f4d8c9ba43eb39f253896090010c764ce9c9c9fb8f98d82efd0796ef8464ce6f5c764fb7e6041e0bab13809f8c37646b6b7a8e189367f86ed23d71993f19692d441bed5342a66d31d3e418ac0d1e889ebd7624c177c1472b2929b8aea61938d3f6814202083e32b088aa7d327c7e3d5c4c6c86212efcec139171a088f1d14a37e7047c1622b8b999f4");
					result.setDescribe(resultJson.optString("remarks"));
					result.setPakageName(resultJson.optString("appPackage"));
					result.setAppActivityName(resultJson
							.optString("activityName"));
					result.setAppSize(resultJson.optString("appSize"));
					result.setVersionName(resultJson.optString("version"));
					result.setVersionCode(resultJson.optInt("versionCode"));
				} else {
					return result;
				}
			}
			// switch (id) {
			// case 1:
			// result = new AppInfomation();
			// result.setAppName("小说阅读");
			// result.setImageUrl("http://img5.duitang.com/uploads/item/201309/17/20130917112349_AFym5.png");
			// result.setStarLevel(3);
			// result.setDownLoadNum(300);
			// result.setAppDownUrl("http://p.gdown.baidu.com/4929ce73941f8f1b0f7fb7522d8813a3b92ede129cb37738fa13a73999c2c209f0c7055751f974881048f5fe4fd8e83d6c5ca9aa5d57817177b37bc7d4128630a081804a517c7e93a76e1488d0ae9e877d7cc38d819143f7911764f2be9fd50acae56370c449b4f5607f1dbb5b091a37f65320d6c81ca9e3707e362f7988aeeb9fd01649591e31bc931313816558caeb0bc79b0a56972dd75952024e4d0fd2b87ac3ff47dc85838bef91466761ed39f2f44f1c0feeca8ac7c4a3273986771ac31b331c2eb60575dabd8d6307ed93f12b2b54890ef97a63f4d8c9ba43eb39f253896090010c764ce9c9c9fb8f98d82efd0796ef8464ce6f5c764fb7e6041e0bab13809f8c37646b6b7a8e189367f86ed23d71993f19692d441bed5342a66d31d3e418ac0d1e889ebd7624c177c1472b2929b8aea61938d3f6814202083e32b088aa7d327c7e3d5c4c6c86212efcec139171a088f1d14a37e7047c1622b8b999f4");
			// result.setDescribe("一款不错的阅读软件");
			// break;
			// case 2:
			// result = new AppInfomation();
			// result.setAppName("汽车头条");
			// result.setDownLoadNum(248);
			// result.setImageUrl("http://img2.imgtn.bdimg.com/it/u=1713626760,3656959926&fm=21&gp=0.jpg");
			// result.setStarLevel(3);
			// result.setAppDownUrl("http://p.gdown.baidu.com/c66036276456f2e94fec1cbcdef09a0a2093102d7e6c0d3e8788f7c4a5cce849d891a47d51433f975d95e5218fd78316c6f393210288a111ec24fd99cfe2dc55ddb414cb3186a840565079a1cf6969e8c326bc1b6ed2ba42c66e1d2bffa6eb2a343c4134a37f38bb");
			// result.setDescribe("一款不错的汽车新闻软件");
			// break;
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取首页信息
	 * 
	 * @return
	 */
	public Map<String, List<AppInfomation>> getIndexInfomations() {
		Map<String, List<AppInfomation>> appcationMap = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "getHomeMessage");
			String postData = new Gson().toJson(map);
			L.e("获取首页信息提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取首页信息返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					appcationMap = new HashMap<String, List<AppInfomation>>();
					// 获取排行数据列表
					String rankApps = resultJson.optString("ranks");
					if (!TextUtils.isEmpty(rankApps)) {
						List<AppInfomation> ranksInfomations = new ArrayList<AppInfomation>();
						JSONArray jsonArray = new JSONArray(rankApps);
						int iSize = jsonArray.length();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setId(jsonObj.optString("appId"));
							appInfomation.setAppName(jsonObj
									.optString("appName"));
							appInfomation.setImageUrl(jsonObj
									.optString("appIconSmall"));
							appInfomation.setStarLevel(jsonObj
									.optInt("appRank"));
							appInfomation.setAppSort(jsonObj.optString("sort"));
							appInfomation.setAppTypeId(jsonObj
									.optString("appTypeId"));
							ranksInfomations.add(appInfomation);
						}
						appcationMap.put("ranks", ranksInfomations);
					}

					// 获取推荐信息列表
					String recommends = resultJson.optString("recommends");
					if (!TextUtils.isEmpty(recommends)) {
						List<AppInfomation> recommendsInfomations = new ArrayList<AppInfomation>();
						JSONArray jsonArray = new JSONArray(recommends);
						int iSize = jsonArray.length();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setId(jsonObj.optString("appId"));
							appInfomation.setImageBigUrl(jsonObj
									.optString("pictrue"));
							appInfomation.setAppTypeId(jsonObj
									.optString("appTypeId"));
							recommendsInfomations.add(appInfomation);
						}
						appcationMap.put("recommends", recommendsInfomations);
					}

					// 获取首发信息列表
					String firstSends = resultJson.optString("firstSends");
					if (!TextUtils.isEmpty(firstSends)) {
						List<AppInfomation> firstSendsInfomations = new ArrayList<AppInfomation>();
						JSONArray jsonArray = new JSONArray(firstSends);
						int iSize = jsonArray.length();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setId(jsonObj.optString("appId"));
							appInfomation.setImageBigUrl(jsonObj
									.optString("pictrue"));
							appInfomation.setAppTypeId(jsonObj
									.optString("appTypeId"));
							firstSendsInfomations.add(appInfomation);
						}
						appcationMap.put("firstSends", firstSendsInfomations);
					}

					// 获取专题信息列表
					String specials = resultJson.optString("specials");
					if (!TextUtils.isEmpty(specials)) {
						List<AppInfomation> specialsInfomations = new ArrayList<AppInfomation>();
						JSONArray jsonArray = new JSONArray(specials);
						int iSize = jsonArray.length();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setzTId(jsonObj.optString("id"));
							appInfomation.setzTpicture(jsonObj
									.optString("pictrue"));
							appInfomation.setzTzhuantiName(jsonObj
									.optString("name"));
							specialsInfomations.add(appInfomation);
						}
						appcationMap.put("specials", specialsInfomations);
					}

					// 人气信息集合
					String renqiRankApps = resultJson.optString("rankApps");
					if (!TextUtils.isEmpty(renqiRankApps)) {
						List<AppInfomation> renqiAppInfomations = new ArrayList<AppInfomation>();
						JSONArray jsonArray = new JSONArray(renqiRankApps);
						int iSize = jsonArray.length();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setId(jsonObj.optString("appId"));
							appInfomation.setImageBigUrl(jsonObj
									.optString("appIconBig"));
							appInfomation.setAppTypeId(jsonObj
									.optString("appTypeId"));
							renqiAppInfomations.add(appInfomation);
						}
						appcationMap.put("renqiRankApps", renqiAppInfomations);
					}
				} else {
					return appcationMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appcationMap;
	}

	/**
	 * 获取专题下的所有app信息
	 * 
	 * @return
	 */
	public List<AppInfomation> getZhuantiList(String zTId) {
		List<AppInfomation> result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", zTId);
			map.put("method", "getAppBySpecial");
			String postData = new Gson().toJson(map);
			L.e("获取专题下的所有app信息提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取专题下的所有app信息返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					String datas = resultJson.optString("apps");
					if (!TextUtils.isEmpty(datas)) {
						JSONArray jsonArray = new JSONArray(datas);
						int iSize = jsonArray.length();
						result = new ArrayList<AppInfomation>();
						for (int i = 0; i < iSize; i++) {
							JSONObject jsonObj = (JSONObject) jsonArray.get(i);
							AppInfomation appInfomation = new AppInfomation();
							appInfomation.setAppName(jsonObj
									.optString("appName"));
							appInfomation.setDownLoadNum(jsonObj.optInt(
									"downloadTime", 0));
							appInfomation.setImageUrl(jsonObj
									.optString("appIconSmall"));
							appInfomation.setId(jsonObj.optString("id"));
							appInfomation.setAppTypeId(jsonObj
									.optString("appTypeId"));
							result.add(appInfomation);
						}
					}
				} else {
					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 提交下载数量到后台
	 * 
	 * @return
	 */
	public String submitLoadNum(String appId, String userId) {
		String result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("appId", appId);
			map.put("userId", userId);
			map.put("method", "resetDownloadCount");
			String postData = new Gson().toJson(map);
			L.e("提交下载数量提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("提交下载数量返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = resultJson.optString("success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传图片到后台
	 * 
	 * @return 微信扫码图片地址
	 */
	public String uploadFile(String imagePath) {
		String result = null;
		// String filePath = Constant.Project_SDcard_Save_Path + "/"
		// + Constant.imgAppUrl + "/zhuanti2.png";
		File s = new File(imagePath);
		if (!s.exists()) {
			return result;
		}
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			String imagString = new String(FileUtils.getBytes(imagePath),
					"ISO-8859-1");
			map.put("fileBytes", imagString);
			map.put("method", "uploadPictrue");
			int lastIndexOf = imagePath.lastIndexOf("/");
			map.put("fileName", imagePath.substring(lastIndexOf + 1));
			String postData = new Gson().toJson(map);
			L.e("上传图片提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("上传图片返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = resultJson.optString("codePath");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取注册二维码图片
	 * 
	 * @return 返回图片地址
	 */
	public String getRegistCodeImage(String channelId, String userId,
			String deviceId) {
		String result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("channelId", channelId);
			map.put("userId", userId);
			map.put("deviceId", deviceId);
			map.put("method", "getRegisterCode");
			String postData = new Gson().toJson(map);
			L.e("获取注册二维码提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取注册二维码返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = resultJson.optString("codePath");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取登陆二维码图片
	 * 
	 * @return 返回图片地址
	 */
	public String getLoginCodeImage(String channelId, String userId,
			String deviceId) {
		String result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("channelId", channelId);
			map.put("userId", userId);
			map.put("deviceId", deviceId);
			map.put("method", "getLoginCode");
			String postData = new Gson().toJson(map);
			L.e("获取登陆二维码提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取登陆二维码返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = resultJson.optString("codePath");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取登陆信息
	 * 
	 * @return 返回用户信息
	 */
	public User getUserInfo(String loginNmae, String password) {
		User result = null;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("loginName", loginNmae);
			map.put("password", password);
			map.put("method", "checkLogin");
			String postData = new Gson().toJson(map);
			L.e("获取用户信息提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取用户信息返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = new User();
					result.setPictrue(resultJson.optString("pictrue"));
					result.setNickName(resultJson.optString("nickName"));
					result.setUserId(resultJson.optString("userId"));
					result.setVipType(resultJson.optString("vipType"));
					result.setIntegral(resultJson.optString("integral"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取积分并扣除积分，添加消费记录
	 * 
	 * @return true可以下载 or false不能下载
	 */
	public boolean isDownLoad(String appId, String userId) {
		boolean result = false;
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("appId", appId);
			map.put("userId", userId);
			map.put("method", "getIntegral");
			String postData = new Gson().toJson(map);
			L.e("获取积分提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("获取积分返回数据：", str);
			if (!TextUtils.isEmpty(str)) {
				JSONObject resultJson = new JSONObject(str);
				String status = resultJson.optString("error");
				if (TextUtils.isEmpty(status)) {
					result = resultJson.optBoolean("success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传推送数据
	 * 
	 * @return
	 */
	public void uploadPushData(String deviceId, String userId, String channelId) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("channelId", channelId);
			map.put("userId", userId);
			map.put("deviceId", deviceId);
			map.put("method", "InsertSendParam");
			String postData = new Gson().toJson(map);
			L.e("上传推送数据提交数据：", postData);
			String str = HttpUtil.http(baseurl, "POST", postData);
			L.e("上传推送数据返回数据：", str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 版本检查更新
	 * 
	 * @param os
	 */
	public boolean testNet() {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("method", "test");
			String postData = new Gson().toJson(map);
			String str = HttpUtil.http("http://192.168.1.45:9099/", "POST",
					postData);
			L.e("测试返回数据：", str);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 加工返回回来的json字符串
	 * 
	 * @param str
	 * @return
	 */
	private String replaceJson(String str) {
		String replaceAll = str.replaceAll("\\\\", "");
		String substring = replaceAll.substring(1, replaceAll.length() - 1);
		return substring;
	}

}
