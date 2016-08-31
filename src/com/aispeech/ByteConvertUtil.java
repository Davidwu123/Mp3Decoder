package com.aispeech;

import java.nio.ByteOrder;

/**
 * Created by yuruilong on 16-8-29.
 */
public class ByteConvertUtil {

    public static short getShort(byte[] buf) {
        return getShort(buf, testCPU());// 第二位true/false得看cpu
    }

    public static boolean testCPU() {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            return true;
        } else {
            return false;
        }
    }

    private static short getShort(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        if (bBigEnding) {
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        }

        return r;
    }

    public static short[] Bytes2Shorts(byte[] buf, int readSize) {
        byte bLength = 2;
        short[] s = new short[readSize / bLength];
        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = new byte[bLength];
            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                temp[jLoop] = buf[iLoop * bLength + jLoop];
            }
            s[iLoop] = getShort(temp);
        }
        return s;
    }

    public static byte[] Shorts2Bytes(short[] datas) {
        byte bLength = 2;
        byte[] buf = new byte[datas.length * bLength];
        for (int iLoop = 0; iLoop < datas.length; iLoop++) {
            byte[] temp = getBytes(datas[iLoop]);
            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                buf[iLoop * bLength + jLoop] = temp[jLoop];
            }
        }
        return buf;
    }

    private static byte[] getBytes(short data) {
        return getBytes(data, testCPU());
    }

    private static byte[] getBytes(int data, boolean bBigEnding) {
        byte[] buf = new byte[4];
        if (bBigEnding) {
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (data & 0x000000ff);
                data >>= 8;
            }
        } else {
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (data & 0x000000ff);
                data >>= 8;
            }
        }
        return buf;
    }
}
