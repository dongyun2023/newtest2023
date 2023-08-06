package com.game.yangtechplatform.utils;

import java.util.List;

/**
 * CRC16����㷨
 * @author Administrator
 *
 */
public class CRC16 {
	 
	/**
	 * CRC16�Ķ���ʽ
	 */
    public static final short POLY = 0x1021;
 
    /**
     * ���16λCRC��֤���(0x1021)
     * @param data ���
     * @return
     */
    public static short Generate(byte[] data)
    {
        return Generate(data, 0, data.length, CRC16.POLY);
    }
 
    public static  short Generate(byte[] data,  short poly)
    {
        return Generate(data, 0, data.length , poly);
    }
 
    public static  short Generate(byte[] data, int start, int length)
    {
        return Generate(data, start, length, POLY);
    }
 
    public static  short Generate(byte[] data, int start, int length,  short poly)
    {
         short crc = 0;
        while (length-- > 0)
        {
            byte bt = data[start++];
            for (int i = 0; i < 8; i++)
            {
                boolean b1 = (crc & 0x8000) != 0;
                boolean b2 = (bt & 0x80) != 0;
                if (b1 != b2) crc = (short)((crc << 1) ^ poly);
                else crc <<= 1;
                bt <<= 1;
            }
        }
        return crc;
    }
 
    /**
     * ���PC�������֤���(0x1021)
     * @param data ����֤���
     * @param crc CRC
     * @return
     */
    public static boolean Validate(byte[] data,  short crc)
    {
        return Validate(data, crc, POLY);
    }
 
    /**
     * ���PC�������֤���
     * @param data ����֤���
     * @param crc CRC
     * @param poly ����ʽ
     * @return
     */
    public static boolean Validate(byte[] data,  short crc,  short poly)
    {
        return Validate(data, 0, data.length, crc, poly);
    }
 
    /**
     * ���PC�������֤���(0x1021)
     * @param data ����֤���
     * @param start ��ʼ��ַ
     * @param length ����
     * @param crc CRC
     * @return
     */
    public static boolean Validate(byte[] data, int start, int length,  short crc)
    {
        return Validate(data, start, length, crc, POLY);
    }
 
    /**
     * ���PC�������֤���
     * @param data ����֤���
     * @param start ��ʼ��ַ
     * @param length ����
     * @param crc CRC
     * @param poly ����ʽ
     * @return
     */
    public static boolean Validate(byte[] data, int start, int length,  short crc,  short poly)
    {
        try
        {
             short mCrc =  Generate(data, start, length, poly) ;

            if (crc == mCrc)
            {
                return true;
            }
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
