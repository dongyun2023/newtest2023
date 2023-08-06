package com.zhong.action.kl01_4k;

import java.util.List;

import netty.trans.entity.MaApp;
import android.content.Context;
import android.os.AsyncTask;

import com.game.yangtechplatform.net.Control;

public class AppIconTypeSerial {

	public String id;
	public String typeSmall;
	public String typeName;
	public String typeBig;
	public String seriesId;
	public String isShow;
	public String logPath;///class log图片路径
	public String photoGraph;
	public static Context mcontext;
	public static String m_serialID;
	public void Clear()
	{
		id="";
		typeSmall="";
		typeName="";
		typeBig="";
		seriesId="";
		isShow="";
		logPath="";
		photoGraph="";
	}
	
	public static String GetTypeSerialID(Context context, String id)
	{
		mcontext=context;
		m_serialID=id;
		getWebTypeSerialID();
		return "";
	}
	
	  /**
		 * 获取服务器上最新的app版本号
		 * 
		 */
		public static void getWebTypeSerialID() {
	
			AsyncTask<Void, Void, List<AppIconTypeSerial> > getWebTypeSerialIDTask = new AsyncTask<Void, Void, List<AppIconTypeSerial> >() {
				protected void onPreExecute() {

				};

				@Override
				protected List<AppIconTypeSerial>  doInBackground(Void... params) {
	
					List<AppIconTypeSerial> tlist=Control.instance(mcontext).getAllTypeBySerial(m_serialID);
					// AppVersion um = null;
					// um = Control.instance(MainActivity.this).versionCheck("1");
					return tlist;
				}
			
				protected void onPostExecute(MaApp result) {
		
				}
			};
			getWebTypeSerialIDTask.execute();
		}
	
	
}




