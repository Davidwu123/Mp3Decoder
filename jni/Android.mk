LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := lame

LOCAL_SRC_FILES := bitstream.c encoder.c fft.c gain_analysis.c id3tag.c lame.c mpglib_interface.c newmdct.c presets.c psymodel.c quantize_pvt.c quantize.c reservoir.c set_get.c tables.c takehiro.c util.c vbrquantize.c VbrTag.c version.c \
				common.c  dct64_i386.c  interface.c layer1.c layer2.c layer3.c tabinit.c decode_i386.c	com_aispeech_tts_MP3Decoder.c
					
LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog

include $(BUILD_SHARED_LIBRARY)