package com.game.yangtechplatform.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhong.action.kl01_4k.R;
import com.game.yangtechplatform.globle.Constant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

public class AsyncImageClass {
	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(5); // 锟教讹拷锟斤拷锟斤拷叱锟斤拷锟街达拷锟斤拷锟斤拷锟�
	private Handler handler = new Handler();

	// SD卡上图片储存地址
	private String path = null;
	private String imgUrl = null;

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	private Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据

		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		} else if (useTheImage(imageUrl) != null) {
			return useTheImage(imageUrl);
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(imageUrl).openStream(), "image.png");
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(drawable);
						}
					});
					saveFile(drawable, imageUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return null;
	}

	// 从网络上取数据方法
	public Drawable loadImageFromUrl(String imageUrl) {
		try {

			return Drawable.createFromStream(new URL(imageUrl).openStream(),
					"image.png");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 对外界开放的回调接口
	private interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Drawable imageDrawable);
	}

	// 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	public void LoadImage(String url, final ImageView iv, Context context,
			String imgUrl) {
		this.path = Environment.getExternalStorageDirectory().getPath() + "/"
				+ Constant.baseUrl;
		this.imgUrl = imgUrl;
		iv.setImageDrawable(context.getResources().getDrawable(
				R.drawable.image_loading));
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageClass.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}

	/**
	 * 保存图片到SD卡上
	 * 
	 * @param bm
	 * @param fileName
	 * 
	 */
	private void saveFile(Drawable dw, String url) {
		try {
			BitmapDrawable bd = (BitmapDrawable) dw;
			Bitmap bm = bd.getBitmap();
			String fileNa = "";
			if (url.indexOf("?") != -1) {
				// 获得文件名字
				fileNa = url.substring(url.lastIndexOf("/") + 1,
						url.lastIndexOf("?")).toLowerCase();
			} else {
				// 获得文件路径
				// 获得文件名字
				fileNa = url.substring(url.lastIndexOf("/") + 1, url.length())
						.toLowerCase();
			}
			File file = new File(path + "/" + imgUrl + "/" + fileNa);
			// 创建图片缓存文件夹
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
			if (sdCardExist) {
				File maiduo = new File(path);
				File ad = new File(path + "/" + imgUrl);
				// 如果文件夹不存在
				if (!maiduo.exists()) {
					// 按照指定的路径创建文件夹
					maiduo.mkdir();
					// 如果文件夹不存在
				}
				if (!ad.exists()) {
					// 按照指定的路径创建文件夹
					ad.mkdirs();
				}
				// 检查图片是否存在
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 使用SD卡上的图片
	 * 
	 */
	private Drawable useTheImage(String imageUrl) {

		Bitmap bmpDefaultPic = null;
		String imageSDCardPath = "";
		if (imageUrl.indexOf("?") != -1) {
			// 获得文件路径
			imageSDCardPath = path
					+ "/image/"
					+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1,
							imageUrl.lastIndexOf("?")).toLowerCase();
		} else {
			// 获得文件路径
			imageSDCardPath = path
					+ "/"
					+ imgUrl
					+ "/"
					+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1,
							imageUrl.length()).toLowerCase();
		}
		File file = new File(imageSDCardPath);
		// 检查图片是否存在
		if (!file.exists()) {
			return null;
		}
		 bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);
//		bmpDefaultPic = getimage(imageSDCardPath);

		if (bmpDefaultPic != null && bmpDefaultPic.toString().length() > 3) {
			Drawable drawable = new BitmapDrawable(bmpDefaultPic);
			return drawable;
		} else
			return null;
	}

	/**
	 * 根据路径对图片进行压缩
	 * 
	 * @param srcPath
	 * @return
	 */
	private Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private Bitmap compressImage(Bitmap image) {
		if (image != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			return bitmap;
		}
		return image;

	}

}
