package com.aispeech;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aispeech.tts.MP3Decoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends Activity{

    short[] pcm_l = new short[44096];
    short[] pcm_r = new short[44096];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Thread(){
            public void run() {
                MP3Decoder.init();
                FileInputStream fis = null;
                RandomAccessFile randomFile = null;
                byte[] buffer = new byte[522];
                try {
                    fis = new FileInputStream("/sdcard/aaa.mp3");
                    randomFile = new RandomAccessFile("/sdcard/out.pcm", "rw");
                    int len = 0;
                    while((len = fis.read(buffer)) != -1){
                        Log.e("yuruilong", "len : " + len);
                        int outLen = MP3Decoder.decode(buffer, len, pcm_l, pcm_r);
                        Log.e("yuruilong", "outLen : " + outLen);
                        if(outLen > 0) {
                            short[] result = new short[outLen];
                            System.arraycopy(pcm_l, 0, result, 0, outLen);
//                            Log.e("yuruilong", "pcm_l : " + pcm_l[0]);
                            byte[] resultByteArray = ByteConvertUtil.Shorts2Bytes(result);
                            long fileLength = randomFile.length();
//                            Log.e("yuruilong", "fileLength : " + fileLength);
                            // 将写文件指针移到文件尾。
                            randomFile.seek(fileLength);
                            randomFile.write(resultByteArray, 0, resultByteArray.length);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        MP3Decoder.destroy();
                        randomFile.close();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

}
