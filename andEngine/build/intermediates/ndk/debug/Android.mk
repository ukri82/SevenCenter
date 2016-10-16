LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andengine_shared
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-lGLESv2 \

LOCAL_SRC_FILES := \
	E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni\Android.mk \
	E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni\Application.mk \
	E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni\build.sh \
	E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni\src\BufferUtils.cpp \
	E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni\src\GLES20Fix.c \

LOCAL_C_INCLUDES += E:\Unni\Projects\Android\Games\7Center\andEngine\src\main\jni
LOCAL_C_INCLUDES += E:\Unni\Projects\Android\Games\7Center\andEngine\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
