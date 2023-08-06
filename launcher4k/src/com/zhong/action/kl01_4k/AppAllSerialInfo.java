package com.zhong.action.kl01_4k;

import java.util.List;

import netty.trans.entity.MaApp;
import netty.trans.util.TransUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.game.yangtechplatform.net.*;

public class AppAllSerialInfo {

	public String id;
	public String serial_picture;
	public String serial_name;
	public String pId;
	static Context mcontext;
	public void Clear()
	{
		id="";
		serial_picture="";
		serial_name="";
		pId="";
	}
	
	
	public static String GetSerialID(Context context)
	{
		mcontext=context;
		getWebSerialID();
		return "";
	}
	
	  /**
		 * 获取服务器上最新的app版本号
		 * 
		 */
		public static void getWebSerialID() {
	
			AsyncTask<Void, Void, String> getWebSerialIDTask = new AsyncTask<Void, Void, String>() {
				protected void onPreExecute() {

				};

				@Override
				protected String doInBackground(Void... params) {
	
					 String m_serialID=Control.instance(mcontext).getSerialID();
					// AppVersion um = null;
					// um = Control.instance(MainActivity.this).versionCheck("1");
					return m_serialID;
				}
			
				protected void onPostExecute(MaApp result) {
		
				}
			};
			getWebSerialIDTask.execute();
		}
}
