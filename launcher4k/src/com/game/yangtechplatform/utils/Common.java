package com.game.yangtechplatform.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Common {
	public static final byte SOF = (byte) 0xAA;
	public static final byte EOF = (byte) 0x55;

	public static final String CHARSETNAME = "GB2312";

	/**
	 * 转换short为byte 低位放在前,高位放在后
	 * 
	 * @param b
	 * @param s
	 *            需要转换的short
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index + 1] = (byte) (s >> 8);
		b[index + 0] = (byte) (s >> 0);
	}

	/**
	 * 通过byte数组取到short
	 * 
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
	}

	/**
	 * 转换int为byte数组 低位放在前,高位放在后
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putInt(byte[] bb, int x, int index) {
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	/**
	 * 通过byte数组取到int
	 * 
	 * @param bb
	 * @param index
	 *            第几位开始
	 * @param flag
	 *            0高位在前 1高位在后
	 * @return
	 */
	public static int getInt(byte[] bb, int index, int flag) {
		if (flag == 1)
			return (int) ((((bb[index + 3] & 0xff) << 24)
					| ((bb[index + 2] & 0xff) << 16)
					| ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
		else
			return (int) ((((bb[index + 0] & 0xff) << 24)
					| ((bb[index + 1] & 0xff) << 16)
					| ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
	}

	/**
	 * 转换long型为byte数组 低位放在前,高位放在后
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 7] = (byte) (x >> 56);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	/**
	 * 通过byte数组取到long
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56)
				| (((long) bb[index + 6] & 0xff) << 48)
				| (((long) bb[index + 5] & 0xff) << 40)
				| (((long) bb[index + 4] & 0xff) << 32)
				| (((long) bb[index + 3] & 0xff) << 24)
				| (((long) bb[index + 2] & 0xff) << 16)
				| (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
	}

	/**
	 * 字符到字节转换
	 * 
	 * @param ch
	 * @return
	 */
	public static void putChar(byte[] bb, char ch, int index) {
		int temp = (int) ch;
		for (int i = 0; i < 2; i++) {
			bb[index + i] = (byte) (temp & 0xff); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
	}

	/**
	 * 字节到字符转换
	 * 
	 * @param b
	 * @return
	 */
	public static char getChar(byte[] b, int index) {
		int s = 0;
		if (b[index + 1] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		s *= 256;
		if (b[index + 0] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		char ch = (char) s;
		return ch;
	}

	/**
	 * float转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putFloat(byte[] bb, float x, int index) {
		// byte[] b = new byte[4];
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = (byte) l;
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static float getFloat(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 * double转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putDouble(byte[] bb, double x, int index) {
		// byte[] b = new byte[8];
		long l = Double.doubleToLongBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = (byte) l;
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static double getDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

	/**
	 * 将一个单字节的byte转换成32位的int
	 * 
	 * @param b
	 *            byte
	 * @return convert result
	 */
	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}

	/**
	 * 将一个单字节的Byte转换成十六进制的数
	 * 
	 * @param b
	 *            byte
	 * @return convert result
	 */
	public static String byteToHex(byte b) {
		int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	/**
	 * 将一个3byte的数组转换成32位的int
	 * 
	 * @param buf
	 *            bytes buffer
	 * @param byte[]中开始转换的位置
	 * @return convert result
	 */
	public static int unsigned3BytesToInt(byte[] buf, int index) {
		return ((buf[index + 0] & 0xFF) << 16) | ((buf[index + 1] & 0xFF) << 8)
				| (buf[index + 2] & 0xFF);
	}

	/**
	 * 将16位的short转换成byte数组
	 * 
	 * @param s
	 *            short
	 * @return byte[] 长度为2
	 * */
	public static byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		/*
		 * targets[0]= (byte) ((s >>> 0) & 0xff); targets[1]= (byte) ((s >>> 8)
		 * & 0xff);
		 */
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	/**
	 * 将32位整数转换成长度为4的byte数组
	 * 
	 * @param s
	 *            int
	 * @return byte[]
	 * */
	public static byte[] intToByteArray(int s) {
		byte[] targets = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	/**
	 * long to byte[]
	 * 
	 * @param s
	 *            long
	 * @return byte[]
	 * */
	public static byte[] longToByteArray(long s) {
		byte[] targets = new byte[8];
		for (int i = 0; i < 8; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	/** 32位int转byte[] (高位保存在前) */
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		// targets[0] = (byte) (res & 0xff);// 最低位
		// targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		// targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		// targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		targets[3] = (byte) (res & 0xff);// 最低位
		targets[2] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[1] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[0] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

	/**
	 * 将长度为2的byte数组转换为16位int
	 * 
	 * @param res
	 *            byte[]
	 * @return int
	 * */
	// public static int byte2int(byte[] res) {
	// // res = InversionByte(res);
	// // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
	// int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或
	// return targets;
	// }

	/**
	 * 将长度为2的byte数组转换为16位int
	 * 
	 * @param res
	 *            byte[]
	 * @param start
	 *            起始位置
	 * @param flag
	 *            0 高在前
	 * @return int
	 * */
	public static int byte2int(byte[] res, int offset) {
		// res = InversionByte(res);
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		int targets = 0;

		targets = (res[offset + 1] & 0xff) | ((res[offset] << 8) & 0xff00);

		return targets;
	}

	public static void listAddAll(List<Byte> list, byte[] obj) {
		for (byte t : obj) {
			list.add(t);
		}
	}

	public static byte[] byteListToBytes(List<Byte> blst) {

		byte[] bytes = new byte[blst.size()];
		for (int i = 0; i < blst.size(); i++) {
			bytes[i] = blst.get(i).byteValue();
		}
		return bytes;
	}

	private static boolean copyBytes(byte[] src, int srcOffset, int length,
			byte[] dest, int desOffset) {
		if (srcOffset + length > src.length)
			return false;
		if (length > dest.length)
			return false;
		int index = desOffset;
		for (int i = srcOffset; i < length; i++) {
			dest[index] = src[i];
			index++;
		}
		return true;
	}

	public static char[] bytesToChars(byte[] src, int offset, int length) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(length);
		bb.put(src, offset, length);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}

	/**
	 * 取得byte的每一 bit,0或者1
	 * 
	 * @param b
	 * @param index
	 *            第几位 (0-7)位
	 * 
	 */
	public static int byteToBit(byte b, int index) {
		return (b >> index) & 0x01;
	}

	public static Date bytesToDate(byte[] bytes, int offset) {
		int year = byte2int(bytes, offset);
		Calendar c = Calendar.getInstance();
		c.set(year, bytes[offset + 2] - 1, bytes[offset + 3], bytes[offset + 4],
				bytes[offset + 5], bytes[offset + 6]);
		return c.getTime();
	}

	public static byte[] dateToBytes(Date dt) {
		byte[] buffer = new byte[7];
		Calendar c = Calendar.getInstance();
		if (dt != null) {
			c.setTime(dt);
		}
		byte[] temp = Common.shortToByteArray((short) c.get(Calendar.YEAR));
		buffer[0] = temp[0];
		buffer[1] = temp[1];
		// buffer[2] = (byte) (c.get(Calendar.MONTH) + 1);
		buffer[2] = (byte) (c.get(Calendar.MONTH) + 1);
		buffer[3] = (byte) c.get(Calendar.DAY_OF_MONTH);
		buffer[4] = (byte) c.get(Calendar.HOUR_OF_DAY);
		buffer[5] = (byte) c.get(Calendar.MINUTE);
		buffer[6] = (byte) c.get(Calendar.SECOND);
		return buffer;
	}
}