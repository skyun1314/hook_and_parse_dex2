#include <jni.h>
#include <string>
#include <dlfcn.h>

# include <stdlib.h>
#include <zlib.h>
#include <android/log.h>
#include <sys/types.h>
#include <unistd.h>
#include<vector>
#include <fcntl.h>
#include <sys/mman.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include<cstring>
#include <pthread.h>　
#include <stdio.h>
#include "substrate.h"
#include <sys/system_properties.h>
#include <stdlib.h>

#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,"wodelog", __VA_ARGS__)

char propValue_all[PROP_VALUE_MAX] = {0};



int (*old__system_property_read)(int pi, char *name, char *value);

int new__system_property_read(int pi, char *name, char *value) {
    int result;
    if (strcmp(propValue_all,"ro.debuggable")==0){
        strcpy(value,"0");
    }
    else  if (strcmp(propValue_all,"persist.service.bdroid.bdaddr")==0){
        strcpy(value,"88:77:66:55");
    }else  if (strcmp(propValue_all,"ro.boot.serialno")==0||strcmp(propValue_all,"ro.serialno")==0){
        strcpy(value,"aaaaab2c43a8b14e");
    }else  if (strcmp(propValue_all,"net.hostname")==0){
        strcpy(value,"android-e8aaef782ac9aaaa");
    }else{
         result = old__system_property_read(pi, name, value);
    }
    result=strlen(value);

    LOGD("hook result(%d) system_property_read(%s): %s ",result, propValue_all, value);
    return result;
}


int (*old__system_property_find)(const char *name);

int new__system_property_find(const char *name) {
    memset(propValue_all, 0, PROP_VALUE_MAX);
    strcpy(propValue_all,name);
    return  old__system_property_find(name);
}

int (*old__system_property_get)(char *name, char *value);

int new__system_property_get( char *name, char *value) {
    int result = old__system_property_get( name, value);
   //   LOGD("hook system_property_get：%s : %s ", name, value);
    return result;
}

//配置，这里是hook可运行程序（即NDK小程序）的写法，下面那个就是hook dvm的写法
//MSConfig(MSFilterExecutable,"/system/bin/app_process")
MSConfig(MSFilterLibrary, "libc.so");
//程序入口
MSInitialize {
     MSImageRef image = MSGetImageByName("/system/lib/libc.so");
     if (image != NULL) {

         void *hook__system_property_read = MSFindSymbol(image, "__system_property_read");
         if (hook__system_property_read)
             MSHookFunction(hook__system_property_read, (
                     void *) &new__system_property_read, (void **) &old__system_property_read);
         else
             LOGD("error find __system_property_read ");


         void *hook____system_property_find = MSFindSymbol(image, "__system_property_find");
         if (hook____system_property_find)
             MSHookFunction(hook____system_property_find, (
                     void *) &new__system_property_find, (void **) &old__system_property_find);
         else
             LOGD("error find __system_property_read ");


         void *__system_property_get = MSFindSymbol(image, "__system_property_get");
         if (__system_property_get)
             MSHookFunction(__system_property_get, (
                     void *) &new__system_property_get, (void **) &old__system_property_get);
         else
             LOGD("error find __system_property_get ");
     }
}

extern "C" {
JNIEXPORT void JNICALL
Java_com_example_hookdeviceinfo_MainActivity_haha(JNIEnv *env, jobject instance) {
    char propValue[PROP_VALUE_MAX] = {0};
    __system_property_get("ro.boot.serialno", propValue);
   //LOGD("serial:%s", propValue);

    memset(propValue, 0, PROP_VALUE_MAX);
    __system_property_get("ro.serialno", propValue);
   // LOGD("serial:%s", propValue);


    memset(propValue, 0, PROP_VALUE_MAX);
    __system_property_get("net.hostname", propValue);
   // LOGD("android_id:%s", propValue);

    memset(propValue, 0, PROP_VALUE_MAX);
    __system_property_get("persist.service.bdroid.bdaddr", propValue);
    LOGD("mac:%s", propValue);


    memset(propValue, 0, PROP_VALUE_MAX);
    const prop_info *pi = __system_property_find("persist.service.bdroid.bdaddr");
    if (pi != 0) {
        __system_property_read(pi, 0, propValue);
        LOGD("mac:%s", propValue);
    }


}

}
jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    JNIEnv *env = NULL;
    jint result = -1;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }

    return JNI_VERSION_1_6;

}