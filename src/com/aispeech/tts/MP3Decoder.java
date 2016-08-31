package com.aispeech.tts;


/**
 * Created by yuruilong on 16-8-25.
 */
public class MP3Decoder {
    static {
        System.loadLibrary("lame");
    }

    public static native void init();
    public static native int decode(byte[] mp3_buf, int len, short[] pcm_l, short[] pcm_r);
    public static native void destroy();

}
