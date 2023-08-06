package com.zhong.action.kl01_4k;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;

import android.widget.Button;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import netty.trans.entity.MaApp;
import netty.trans.tools.FileUtils;
import netty.trans.util.TransUtil;
import netty.trans.entity.MaPictruePath;
import netty.trans.util.TransUtil;
import android.content.DialogInterface.OnClickListener;

import com.game.*;
import com.game.yangtechplatform.net.Control;

public class MainActivity extends UnityPlayerActivity  
{
	
	/* 服务器上获取的信息 */
	private MaApp ma;

	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
//	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	public Context mContext=null;
	/* 更新进度条 */
//	private ProgressBar mProgress;
	private ProgressDialog mProgress;
	
	private Dialog mDownloadDialog;
	
	private Button button;
	
	//private UpLoadPic m_uploadpic=new UpLoadPic();
	public int uploadstate=0;

    String m_serialID;
    String m_curclassID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.main);
        mContext = this;
       Log.e("start==", "onCreate==============000============");
      // getWebAppVersion() ;
        Bundle bundle = new Bundle();
        try{
        	bundle = this.getIntent().getExtras();
        	String str=bundle.getString("showPic");
        	if(!str.isEmpty())
        	UnityPlayer.UnitySendMessage("Main Camera","showpic_huanshan","");
        }catch(Exception e)
        {
        	//UnityPlayer.UnitySendMessage("Main Camera","showpic_huanshan","");
        }
      
        
//        if(!TextUtils.isEmpty(m_serialID))
//        {
//        	AppIconTypeSerial.GetTypeSerialID(this, m_serialID);
//        }
   //   mRandom = new Random(System.currentTimeMillis());
    }
    /////////////////
    /**
	 * 获取服务器上最新的app版本号
	 * 
	 */
	public void getWebAppVersion() {
		
		AsyncTask<Void, Void, MaApp> getWebAppVersionTask = new AsyncTask<Void, Void, MaApp>() {
			protected void onPreExecute() {
			};
			@Override
			protected MaApp doInBackground(Void... params) {
				try{
					List<MaApp> all = TransUtil.getAppByPackage(mContext.getPackageName());
					ma = all.get(0);
				}catch(Exception e){
					e.printStackTrace();
					//Log.i("wg","getWebAppVersion===2");
			}
				//  m_serialID=Control.instance(MainActivity.this).getSerialID();
				return ma;
			}
		
			protected void onPostExecute(MaApp result) {
				if (result != null)
				{
					if (isUpdate(result))
					{
						// 显示提示对话框
						showNoticeDialog();
					}
					else
						getWebSerialID();
				}
				else
				{
					//m_serialID=AppAllSerialInfo.GetSerialID(mContext);
					getWebSerialID();
				}
			}
		};
		getWebAppVersionTask.execute();
	}

	/**
	 * 获取服务器上的apk文件
	 * 
	 */
	private void downloadApk() {
		cancelUpdate = false;

		AsyncTask<Void, Void, String> getWebAppTask = new AsyncTask<Void, Void, String>() {
			protected void onPreExecute() {
//				// 构造软件下载对话框
//				AlertDialog.Builder builder = new Builder(mContext);
//				builder.setTitle("正在下载");
//				// 给下载对话框增加进度条
//				final LayoutInflater inflater = LayoutInflater.from(mContext);         
//				View v = inflater.inflate(R.layout.progress, null);         
//				mProgress = (ProgressBar)v.findViewById(R.id.down_progress);                   
//				builder.setView(v); 	
//				mDownloadDialog = builder.create();
//				mDownloadDialog.setCancelable(false);
//				mDownloadDialog.show();
				
				mProgress = new ProgressDialog(mContext);  
				mProgress.setTitle("正在下载...");  
				mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
				mProgress.setCancelable(false);
				mProgress.setButton(DialogInterface.BUTTON_NEGATIVE,"取消下载", new DialogInterface.OnClickListener() {            
					@Override  
					public void onClick(DialogInterface dialog, int which) {  
						cancelUpdate = true;
						mProgress.dismiss();   
						getWebSerialID();
					}  
				});  	
				mProgress.show();
			};

			InputStream is = null;
			FileOutputStream fos = null;
			@Override
			protected String doInBackground(Void... params) {

				try {
					// 获得存储卡的路径
					String sdpath = "";
					// 判断SD卡是否存在，并且是否具有读写权限
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						// 获得存储卡的路径
						sdpath = Environment.getExternalStorageDirectory().getPath() + "/";
						
					}else{
						// 获得内部存储的路径
						sdpath = Environment.getDownloadCacheDirectory().getPath() + "/";
					}
					
					mSavePath = sdpath + "download";
					URL url = new URL(ma.getAddress());
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, ma.getName());
					fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[5120];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						teststr="正在下载... "+count/1000+" KB/"+length/1000+" KB";
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							/////////////
							
							if(fos != null){
								try {
									fos.close();
								} catch (IOException e) {
								}
							}
							if(is != null){
								try {
									is.close();
								} catch (IOException e) {
								}
							}		
							////////////////////
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(fos != null){
						try {
							fos.close();
						} catch (IOException e) {
						}
					}
					if(is != null){
						try {
							is.close();
						} catch (IOException e) {
						}
					}		
				}

				return "";
			}

			protected void onPostExecute(String result) {
				// 取消下载对话框显示
				mProgress.dismiss();
				/////////////
				
				if(fos != null){
					try {
						fos.close();
					} catch (IOException e) {
					}
				}
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
					}
				}		
			}
		};
		getWebAppTask.execute();
	}

	String teststr="";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				mProgress.setTitle(teststr);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate(MaApp result) {
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		int serviceCode = Integer.parseInt(result.getVersion_code());
		if (serviceCode > versionCode) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			// versionCode =
			// context.getPackageManager().getPackageInfo("com.szy.update",
			// 0).versionCode;
			versionCode = context.getPackageManager().getPackageInfo(
					getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("提示");
		builder.setMessage("发现新版本，是否更新?");
		// 更新
		builder.setPositiveButton("马上更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 显示下载对话框
				downloadApk();
//				showDownloadDialog();
			}
		});
		// 稍后更新
		builder.setNegativeButton("稍后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getWebSerialID();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.setCancelable(false);
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
//	private void showDownloadDialog() {
//		// 构造软件下载对话框
//		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle("正在下载");
//		// 给下载对话框增加进度条
//		mDownloadDialog = builder.create();
//		mDownloadDialog.show();
//		// 现在文件
//		downloadApk();
//	}

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, ma.getName());
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
    /////////////////////////////////////////////////////////********************
	   ////
    void upLoadPicToNet(String uploadpath,String upname,String twocodepath)
    {
    	//Log.i("wg","tonet"+uploadpath+"="+upname);
    	m_savetwocodepath=twocodepath;
    	StartUpLoad_Pic(uploadpath, upname);
    	///displayBriefMemory();
    }
    void GetUpLoadState()
    {
    	UnityPlayer.UnitySendMessage("Main Camera","GetUpLoadState0",""+uploadstate);
    }
    ////
    void DeletePicToNet(String uploadpath)
    {
    	Log.i("wg","DeletePicToNet"+uploadpath);
    	try
    	{
    		TransUtil.deletePictrue(uploadpath,"");
    	} 
    	catch (Exception e) 
    	{
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    
    }

    public long m_memroy=0;
	public  void displayBriefMemory() {    
        final ActivityManager activityManager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);    
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();   
        activityManager.getMemoryInfo(info);    
        Log.i("tag","系统剩余内存:"+(info.availMem >> 20)+"m");  
        m_memroy=info.availMem >> 20;
        UnityPlayer.UnitySendMessage("Main Camera","displayBriefMemory",""+m_memroy);
//        Log.i("tag","系统是否处于低内存运行："+info.lowMemory);
//        Log.i("tag","系统剩余内存低于"+info.threshold+"时就看成低内存运行");
    } 
	
	  	String  testpath;
	    String m_getcodepath;
	    String m_upname;
	    public String m_savetwocodepath="/mnt/sdcard/Download/twocode";
	    private void StartUpLoad_Pic(String uploadpath,String upname) {
			cancelUpdate = false;
			testpath=uploadpath;
			m_upname=upname;
			uploadstate=1;
			AsyncTask<Void, Void, String> getWebAppTask = new AsyncTask<Void, Void, String>() {
				protected void onPreExecute() {
				
				};

				@Override
				protected String doInBackground(Void... params) {
					
					try
					{
						MaPictruePath uploadPictrue = TransUtil.uploadPictrue("zhy4k", new File(testpath));
						Log.i("wg","mRunnable="+testpath);		
						m_getcodepath=uploadPictrue.getCode_path();
						uploadstate=2;
						byte[] twoCode =null;
						if(m_getcodepath!=null)
						{
							//传入二维码图片路径，返回二维码byte数组
							twoCode=TransUtil.getTwoCode(m_getcodepath);	
							uploadstate=3;
						//	Log.i("wg","mRunnable=1="+m_savetwocodepath);
							//获取到二维码byte数组，将之写入文件，参数：1.二维码byte数组。2.二维码路径（文件夹）。3.二维码名称
							if(twoCode!=null)
							{								
								//FileUtils.getFile(twoCode, "/mnt/sdcard/Download/zhongyou/xlg", m_upname+"_2.jpg");	
								Log.i("wg","mRunnable=2="+m_savetwocodepath);	
								FileUtils.getFile(twoCode, m_savetwocodepath, m_upname+"_2.jpg");				
								uploadstate=4;
								UnityPlayer.UnitySendMessage("Main Camera","GetTwoCode",m_upname+"_2");												
								uploadstate=0;
							}
							
						}
						
					}
					catch (Exception e) 
					{
						Log.i("wg","mRunnable="+"Exception");
						UnityPlayer.UnitySendMessage("Main Camera","ShowNetLink","网络错误异常！!!!");
						e.printStackTrace();
						uploadstate=0;
					}
					return "";
				}

				protected void onPostExecute(String result) {
		
				}
			};
			getWebAppTask.execute();
		}
	
	/////////////////////////////////**********************
		  /**
			 * 获取服务器上app系列id 1
			 */
			public  void getWebSerialID() {
		
				AsyncTask<Void, Void, String> getWebSerialIDTask = new AsyncTask<Void, Void, String>() {
					protected void onPreExecute() {

					};
					@Override
					protected String doInBackground(Void... params) {
						  m_serialID=Control.instance(mContext).getSerialID();
						return m_serialID;
					}
					protected void onPostExecute(String result) {
							
						if(!TextUtils.isEmpty(result))
						{
							getWebTypeSerialID() ;
						}
						
					}
				};
				getWebSerialIDTask.execute();
			}
	
			  /**
			 * 获取服务器上系列分类 2
			 * 
			 */
			public  void getWebTypeSerialID() {
		
				AsyncTask<Void, Void, List<AppIconTypeSerial> > getWebTypeSerialIDTask = new AsyncTask<Void, Void, List<AppIconTypeSerial> >() {
					protected void onPreExecute() {

					};

					@Override
					protected List<AppIconTypeSerial>  doInBackground(Void... params) {
		
						List<AppIconTypeSerial> tlist=Control.instance(mContext).getAllTypeBySerial(m_serialID);
						// AppVersion um = null;
						// um = Control.instance(MainActivity.this).versionCheck("1");
						return tlist;
					}
				
					protected void onPostExecute(List<AppIconTypeSerial> result) {
						
						if(result.size()>0)
						{
							//AppIconTypeSerial t=result.get(0);
							//m_curclassID=t.id;
							//getAllAppByType();
							SendAppIconClassInfo(result);
						}
					}
				};
				getWebTypeSerialIDTask.execute();
			}		
//			public String id;
//			public String typeSmall;
//			public String typeName;
//			public String typeBig;
//			public String seriesId;
//			public String isShow;	
			void SendAppIconClassInfo( List<AppIconTypeSerial> lt)
			{
				int len=lt.size();
				UnityPlayer.UnitySendMessage("Main Camera","GetdAppIconClassInfo","$"+","+len);	
				int i;
				for(i=0;i<len;i++)
				{
					String str;
					AppIconTypeSerial at=lt.get(i);
					str=at.id+","+at.typeSmall+","+at.typeName+","+at.typeBig+","+at.seriesId+","+at.isShow+","+at.logPath+","+at.photoGraph;
					UnityPlayer.UnitySendMessage("Main Camera","GetdAppIconClassInfo","#"+","+i+","+str);		
				}	
			}
			  /**
			 * 获取服务器上分类app 3
			 * 
			 */
			
			public void CallAllAppByType(String classid)
			{
				m_curclassID=classid;
				getAllAppByType();
			}
//			public String id;
//			public String app_package;
//			public String details;
//			public String app_link;
//			public String name;
//			public String series_id;
//			public String pictrue_big;
			public  void getAllAppByType() {
		
				AsyncTask<Void, Void, List<GameAppInfo> > getWebTypeSerialIDTask = new AsyncTask<Void, Void,List<GameAppInfo> >() {
					protected void onPreExecute() {

					};

					@Override
					protected List<GameAppInfo>  doInBackground(Void... params) {
		
						List<GameAppInfo> tlist=Control.instance(mContext).getAllAppByType(m_curclassID);
						// AppVersion um = null;
						// um = Control.instance(MainActivity.this).versionCheck("1");
						return tlist;
					}
				
					protected void onPostExecute(List<GameAppInfo> result) {
						
						int len=result.size();
						UnityPlayer.UnitySendMessage("Main Camera","GetAllAppByClass","$"+","+len);	
						int i;
						for(i=0;i<len;i++)
						{
							String str;
							GameAppInfo at=result.get(i);
							str=at.id+","+at.app_package+","+at.details+","+at.app_link+","+at.name+","+at.series_id+","+at.pictrue_big+","+at.pictrue_small+","+at.photoGraph;;
							UnityPlayer.UnitySendMessage("Main Camera","GetAllAppByClass","#"+","+i+","+str);		
						}	
			
					}
				};
				getWebTypeSerialIDTask.execute();
			}		
					
			
	//////////////////////////////
    public void CheckApkUpdate()
    {
    	///Log.i("wg","==========1234");
    	UnityPlayer.UnitySendMessage("Main Camera","messgae","aa");	
    }
 
    private String getVersionName() throws Exception{  
        //获取packagemanager的实例    
        PackageManager packageManager = getPackageManager();  
        //getPackageName()是你当前类的包名，0代表是获取版本信息   
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);  
        return packInfo.versionName;   
    }  
   
    void NeedinstallApp_name(String str)
    {
    	if(str.length()==0) return ;
    	String[] packagename=str.split(",");
    	int len=packagename.length;
    	String sendstr="";
    	PackageManager t=this.getPackageManager(); 
    	int i;
    	for(i=0;i<len;i++)
    	{
    	  	try
    		{
    			 PackageInfo packInfo = t.getPackageInfo(packagename[i], 0);  
    			 sendstr+="1";
    		}
    		catch(Exception e)
    		{
    			sendstr+="0";
    		}
        	UnityPlayer.UnitySendMessage("Main Camera","GetInstallApp",sendstr);	
    	}
    }
    
    void NeedinstallApp2()
    {
    	String sendstr="";
    	PackageManager t=this.getPackageManager(); 
    	try
		{
			 PackageInfo packInfo = t.getPackageInfo("com.BNU.Big.Huangshan", 0);  
			 sendstr+="1";
		}
		catch(Exception e)
		{
			sendstr+="0";
		}
    	UnityPlayer.UnitySendMessage("Main Camera","GetInstallApp2",sendstr);	
    }
    void NeedinstallApp1()
    {
    	//Log.i("wg","NeedinstallApp0==");
    	int i;
    	String sendstr="";
    	PackageManager t=this.getPackageManager(); 
    	for(i=0;i<15;i++)
    	{
    		try
    		{
    			 PackageInfo packInfo = t.getPackageInfo(appstr1[i], 0);  
    		//	 Log.i("wg","NeedinstallApp0=="+i);
    			 sendstr+="1";
    		}
    		catch(Exception e)
    		{
    			if(i==4)
    			{
    				try
    				{
    					PackageInfo packInfo = t.getPackageInfo("com.cpsoft.yoc", 0);
    					 sendstr+="1";
    				}
    				catch(Exception e0)
    	    		{
    					sendstr+="0";
    	    		}
    			}
    			else
    			sendstr+="0";
    		}

    	}
    
    ///Log.i("wg","NeedinstallApp0=11="+sendstr);
    	UnityPlayer.UnitySendMessage("Main Camera","GetInstallApp1",sendstr);	
    }
    
    void NeedinstallApp0()
    {
    	//Log.i("wg","NeedinstallApp0==");
    	int i;
    	String sendstr="";
    	PackageManager t=this.getPackageManager(); 
    	for(i=0;i<10;i++)
    	{
    		try
    		{
    			 PackageInfo packInfo = t.getPackageInfo(appstr[i], 0);  
    		//	 Log.i("wg","NeedinstallApp0=="+i);
    			 sendstr+="1";
    		}
    		catch(Exception e)
    		{
    			sendstr+="0";
    		}
    		///String apkstr=t.getInstallerPackageName(appstr[i]);
//    		if(apkstr==null)
//    			sendstr+="0";
//    		else
//    			sendstr+="1";
    	}
    	UnityPlayer.UnitySendMessage("Main Camera","GetInstallApp",sendstr);	
    }
    
    void NeedinstallApp(String packagename)
    {
    	//Log.i("wg","NeedinstallApp0==");
    	int i;
    	String sendstr="";
    	PackageManager t=this.getPackageManager(); 
    	try
    	{
    		PackageInfo packInfo = t.getPackageInfo(packagename, 0);  
    		sendstr="1";
    	}
    	catch(Exception e)
    	{
    		sendstr="0";
    	}
    	UnityPlayer.UnitySendMessage("Main Camera","GetInstallApp_ShowDown",packagename);	
    }
    
    public void StarAppFromPackage(String app_package)
    {
    	try{
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName(app_package,app_package+".MainActivity");
        mIntent.setComponent(comp);      
        mIntent.setAction("android.intent.action.MAIN");
        startActivity(mIntent);
    	}
    	catch(Exception e)
        {
    	//	Log.e("starapp","=="+app_package);
    		try
    		{
    			Intent mIntent1 = new Intent();
	    		mIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		ComponentName comp1 = new ComponentName(app_package,"com.unity3d.player.UnityPlayerNativeActivity");
	    		mIntent1.setComponent(comp1);      
	    		mIntent1.setAction("android.intent.action.MAIN");
	    		startActivity(mIntent1);
    		}
    		catch(Exception e1)
    		{
    			Log.e("starapp","=11="+app_package);
    			//NeedinstallApp(app_package);
    		}
        }
    }
    
    String appstr[]={"com.BNU.Small.FruitRiddle","com.BNU.Big.Measurement","com.BNU.Middle.TeaHouse","com.BNU.Middle.Resturant","com.BNU.Small.FindFruit",
    "com.BNU.Big.ToolsBox","com.BNU.Middle.Dining","com.BNU.Small.FruitJuice","com.BNU.Middle.LeavesJuice","com.BNU.Middle.EvergreenTree"};

    public void StarApp(int n)
    {
    	try{
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName(appstr[n%10],appstr[n%10]+".MainActivity");
        mIntent.setComponent(comp);      
        mIntent.setAction("android.intent.action.MAIN");
        startActivity(mIntent);
    	}
    	catch(Exception e)
        {Intent mIntent1 = new Intent();
        mIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp1 = new ComponentName(appstr[n%10],"com.unity3d.player.UnityPlayerNativeActivity");
        mIntent1.setComponent(comp1);      
        mIntent1.setAction("android.intent.action.MAIN");
        startActivity(mIntent1);
        }
    }
    
    String appstr1[]={"com.cpsoft.youjiao.at02","com.cpsoft.youjiao.pe01","com.cpsoft.youjiao.sc02","com.cpsoft.youjiao.en01","com.cpsoft.yoc",
    "com.cpsoft.youjiao.cc01","com.cpsoft.youjiao.mo01","com.cpsoft.youjiao.ma02","com.cpsoft.youjiao.pe02","com.cpsoft.youjiao.ma01",
    "com.cpsoft.youjiao.ch01","com.cpsoft.youjiao.at01","com.cpsoft.youjiao.ch02","com.cpsoft.youjiao.sc01","com.cpsoft.youjiao.cc02"};
    public void StarApp1(int m)
    {
        
    	try{
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // ComponentName comp = new ComponentName("com.BNU.Middle.PickupLeaves","com.unity3d.player.UnityPlayerNativeActivity");
        ComponentName comp = new ComponentName(appstr1[m%15],appstr1[m%15]+".MainActivity");
        mIntent.setComponent(comp);      
        mIntent.setAction("android.intent.action.MAIN");
        startActivity(mIntent);
    	}catch(Exception e)
    	{       	
	        Intent mIntent1 = new Intent();
	        mIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        ComponentName comp1;	     	
	        comp1 = new ComponentName(appstr1[m%15],"com.unity3d.player.UnityPlayerNativeActivity");
	        mIntent1.setComponent(comp1);      
	        mIntent1.setAction("android.intent.action.MAIN");
	        startActivity(mIntent1);
	     
    	}

    }
    
    public void StarApp2(int n)
    {
        
    	try{
	        Intent mIntent = new Intent();
	        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        ComponentName comp = new ComponentName("com.BNU.Big.Huangshan","com.BNU.Big.Huangshan.MainActivity");
	        mIntent.setComponent(comp);    
	        mIntent.setAction("android.intent.action.MAIN");
	        startActivity(mIntent);
    	}
    	catch(Exception e)
    	{
            Intent mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.BNU.Big.Huangshan","com.unity3d.player.UnityPlayerNativeActivity");
            mIntent.setComponent(comp);    
            mIntent.setAction("android.intent.action.MAIN");
            startActivity(mIntent);
    	}
        
//		int nPid = android.os.Process.myPid();
//		android.os.Process.killProcess(nPid);
//		finish();
//		System.exit(0);
    }
    
    private void RunApp(String packageName) {  
        PackageInfo pi;  
        try {  
            pi = getPackageManager().getPackageInfo(packageName, 0);  
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);  
         //   Intent resolveIntent = new Intent();  
          //  resolveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            resolveIntent.setPackage(pi.packageName);  
            PackageManager pManager = getPackageManager();  
            List apps = pManager.queryIntentActivities(  
                    resolveIntent, 0);  
  
            ResolveInfo ri = (ResolveInfo)apps.iterator().next();  
            if (ri != null) {  
                packageName = ri.activityInfo.packageName;  
                String className = ri.activityInfo.name;  
                Intent intent = new Intent(Intent.ACTION_MAIN);  
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packageName, className);  
                intent.setComponent(cn);  
                startActivity(intent);  
            }  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
  
    }
    
    public void StarApp3()
    {
        
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //  ComponentName comp = new ComponentName("com.BNU.Middle.Dining","com.unity3d.player.UnityPlayerNativeActivity");
        ComponentName comp = new ComponentName("com.Company.Dino_World_3","com.unity3d.player.UnityPlayerNativeActivity");
        mIntent.setComponent(comp);      
        mIntent.setAction("android.intent.action.MAIN");
        startActivity(mIntent);
        
    }

	 void onDestoy() {

		 super.onDestroy();
		
	}
		@Override
		protected void onPause() {
			super.onPause();
		}
	@Override
	protected void onResume() {
		super.onResume();
	}
	   
	     @Override
	     public boolean onKeyDown(int keyCode, KeyEvent msg){ 
	    //	 key_code0=keyCode;
			Log.i("wg","handred=down="+keyCode);
	    	 return super.onKeyDown(keyCode, msg);
	     }
	     @Override
	     public boolean onKeyUp(int keyCode, KeyEvent msg){ 
	    
	    	// key_code0=0;;
	    	 return super.onKeyUp(keyCode, msg);
	     }	     

	     public void GetSensorData_one(SensorEvent sensorEvent)
	     {   
	       
	     }

	     public void GetSensorData_two(SensorEvent sensorEvent)
	     {    	
	     
	     }

   

}

    


