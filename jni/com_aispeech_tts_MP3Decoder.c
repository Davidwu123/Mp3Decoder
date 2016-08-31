#include "com_aispeech_tts_MP3Decoder.h"
#include "lame.h"

#include <android/log.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>


#define BUF_SIZE 512
#define INBUF_SIZE 4096
#define MP3BUF_SIZE (int)(1.25 * BUF_SIZE) + 7200

short pcm_l[INBUF_SIZE];
short pcm_r[INBUF_SIZE];

static hip_t hip = NULL;

JNIEXPORT void JNICALL Java_com_aispeech_tts_MP3Decoder_init
(JNIEnv *env, jclass objclass)
{
    hip = hip_decode_init();
}


JNIEXPORT jint JNICALL Java_com_aispeech_tts_MP3Decoder_decode
        (JNIEnv *env, jclass objclass, jbyteArray mp3buf, jint len, jshortArray buffer_l, jshortArray buffer_r)
{
    jshort* j_buffer_l = (*env)->GetShortArrayElements(env, buffer_l, NULL);

    jshort* j_buffer_r = (*env)->GetShortArrayElements(env, buffer_r, NULL);

    jbyte* j_mp3buf = (*env)->GetByteArrayElements(env, mp3buf, NULL);

    int ret = hip_decode(hip, j_mp3buf, len, j_buffer_l, j_buffer_r);

    (*env)->ReleaseShortArrayElements(env, buffer_l, j_buffer_l, 0);
    (*env)->ReleaseShortArrayElements(env, buffer_r, j_buffer_r, 0);
    (*env)->ReleaseByteArrayElements(env, mp3buf, j_mp3buf, 0);

    return ret;
}

JNIEXPORT void JNICALL Java_com_aispeech_tts_MP3Decoder_destroy
        (JNIEnv *env, jclass objclass)
{
    hip_decode_exit(hip);
}
