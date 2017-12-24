#include <jni.h>
#include <string>
#include <dlfcn.h>
#include "Object.h"
#include "base64.h"
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
#define MIN_INT  ((u4)1<<(sizeof(u4)*8 - 1))
MemMapping mappingxx;
using namespace std;
DexHeader *dexHeader;

#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,"wodelog", __VA_ARGS__)
extern "C" {
void printDexHeader(DexFile *pDexFile);
void data(DexFile *pDexFile, MemMapping *mem);

char *mPackageNamex;
long str_class_data_len;
int classs_index;


const char *str_dump_opcode;
const char *str_class_defs;
const char *str_class_data;
const char *str_fppart1;
const char *str_fppart3;
const char *str_dump_whole;
const char *str_hahahahah;


pthread_t mThread;
void *AlertThreadStub(void *lparam);


void *AlertThreadStub(void *lparam) {
    printDexHeader((DexFile *) lparam);
    //__android_log_print(ANDROID_LOG_INFO, "wodelog", "#Thread working#%lu", mThread);
    return 0;
}


void wirteFile(const char *filename, const void *addr, int length) {
    FILE *xx = fopen(filename, "ab+");
    fwrite(addr, 1, length, xx);
    // fflush(xx);
    fclose(xx);
}

long getfileLen(const char *filename) {
    FILE *xx = fopen(filename, "ab+");

    fseek(xx, 0, SEEK_END);
    long file_len = ftell(xx);

    //fflush(xx);
    fclose(xx);
    return file_len;
}


const char *makeStr(char *fileName) {
    char *szPath = (char *) malloc(100);
    sprintf(szPath, "/data/data/%s/cache/%s", mPackageNamex, fileName);
    return szPath;
}


void writeFile1(const char *str) {

    FILE *xx = fopen(str, "rb");
    fseek(xx, 0, SEEK_END);
    long file_len = ftell(xx);
    char *buf = (char *) malloc(file_len);
    fseek(xx, 0, SEEK_SET);
    fread(buf, 1, file_len, xx);

    wirteFile(str_dump_whole, buf, file_len);
    fflush(xx);
    fclose(xx);
}


JNIEXPORT void JNICALL
haha(JNIEnv *env, jobject instance, jint cookie, jstring pact) {

    mPackageNamex = (char *) env->GetStringUTFChars(pact, 0);
    if (cookie == 0 || cookie == NULL) {
        LOGD("无效输入");
        return;
    }

    // TODO
    DexOrJar *pDexOrJar = (DexOrJar *) cookie;
    DvmDex *pDvmDex;
    printf("jni", pDexOrJar->fileName);
    if (pDexOrJar->isDex) {
        pDvmDex = pDexOrJar->pRawDexFile->pDvmDex;
    } else {
        pDvmDex = pDexOrJar->pJarFile->pDvmDex;
    }
    DexFile *dexFile = pDvmDex->pDexFile;
    MemMapping mapping = pDvmDex->memMap;
    mappingxx = mapping;
    LOGD("MemMapping:filename:%s  addr:%x length:%x baseAddr:%x baseLength:%x", pDexOrJar->fileName,
         mapping.addr, mapping.length, mapping.baseAddr, mapping.baseLength);

    LOGD("dexLength_dexlen:%d", dexFile->pOptHeader->dexLength);


    data(dexFile, &mapping);

    wirteFile(str_hahahahah, mapping.addr, mapping.length);
    //保存三倍dex文件长度
    //printDexHeader(dexFile);
    pthread_create(&mThread, NULL, AlertThreadStub, dexFile);



    /*size_t dlen = mapping.length * 2.5;//base64以后肯定会变长
    unsigned char *dst = (unsigned char *) malloc(dlen);
    base64_encode(dst, &dlen, (const unsigned char *) mapping.addr, mapping.length);//保存三倍dex文件长度
    sprintf(szPathxx, "/data/data/%s/cache/hahahahaha%d", mPackageName, mapping.length);
    LOGD("创建文件：%s", szPathxx);
    FILE *file = fopen(szPathxx, "wb+");
    //fwrite(mapping.addr, mapping.length, 1, file);//保存三倍dex文件长度
     fwrite(dst, dlen, 1, file);
    fclose(file);*/



}


static JNINativeMethod method[] = {

        {"aaattachBaseContext",
                "(ILjava/lang/String;)V",
                (void *) haha
        }

};


jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    JNIEnv *env = NULL;
    jint result = -1;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }
    jclass jclass1 = env->FindClass("com/dexpaser/zk/myapplication/MainActivity");
    int ret = env->RegisterNatives(jclass1, method, 1);

    if (ret < 0) {
        return result;
    }
    return JNI_VERSION_1_6;

}

void data(DexFile *pDexFile, MemMapping *mem) {
    str_dump_opcode = makeStr("dump_opcode");
    str_dump_whole = makeStr("dump_whole");
    str_class_data = makeStr("class_data");
    str_class_defs = makeStr("class_defs");
    str_hahahahah = makeStr("hahahahah");
    str_fppart1 = makeStr("part1");
    str_fppart3 = makeStr("part3");


    remove(str_dump_whole);
    remove(str_dump_opcode);
    remove(str_class_data);
    remove(str_class_defs);
    remove(str_hahahahah);
    remove(str_fppart1);
    remove(str_fppart3);


    const u1 *addr = (const u1 *) mem->addr;
    //int length = int(pDexFile->baseAddr + pDexFile->pHeader->classDefsOff - addr);
    int length = (pDexFile->pHeader->classDefsOff + sizeof(DexOptHeader));

    wirteFile(str_fppart1, addr, length);


    addr = pDexFile->baseAddr + pDexFile->pHeader->classDefsOff +
           (sizeof(DexClassDef) * pDexFile->pHeader->classDefsSize);
    // length = int((const u1 *) mem->addr + mem->length - addr);
    length = int((const u1 *) mem->addr + mem->length - addr);

    wirteFile(str_fppart3, addr, length);
}
u1 *writeunsignedleb128(u1 *ptr, u4 data, const char *str_class) {

    while (true) { //循环
        u1 out = data & 0x7f;; //跟7F进行与运算 得出最后7位

        if (out != data) {

            *ptr++ = out | 0x80; //80就等于10000000 也就是跟前面补1
            wirteFile(str_class, ptr - 1, sizeof(u1));
            data >>= 7;// 继续下个7个字节

        } else {

            *ptr++ = out;
            wirteFile(str_class, ptr - 1, sizeof(u1));
            break;

        }

    }

    return ptr;

}

void writeSignedLeb1281(u4 data)
{
    if (data >= 0){
       // writeunsignedleb128(num, file);
    }else{
        int  mask = 0x80000000;
        int tmp;
        int iii;
        for (int i = 0; i <32 ; ++i) {
            tmp = data & mask;
            mask >>= 1;
            iii=i;
            if (tmp == 0){
                break;
            }

        }



       int loop = 32 - iii + 1;
        int cur;
        while (loop > 7){
            cur = data & 0x7f | 0x80;
            data >>= 7;
            //file.write(struct.pack("B", cur));
            loop -= 7;
        }



        cur = data & 0x7f;
        //file.write(struct.pack("B", cur))
    }
}


 int readunsignedleb128(const u1** pStream) {
        const u1* ptr = *pStream;
        int result = *(ptr++);

        if (result > 0x7f) {
                int cur = *(ptr++);
                result = (result & 0x7f) | ((cur & 0x7f) << 7);
                if (cur > 0x7f) {
                        cur = *(ptr++);
                        result |= (cur & 0x7f) << 14;
                        if (cur > 0x7f) {
                                cur = *(ptr++);
                                result |= (cur & 0x7f) << 21;
                                if (cur > 0x7f) {
                                        /*
                     * Note: We don't check to see if cur is out of
                     * range here, meaning we tolerate garbage in the
                     * high four-order bits.
                     */
                                        cur = *(ptr++);
                                        result |= cur << 28;
                                    }
                            }
                    }
            }

        *pStream = ptr;
        return result;
    }

/*int readunsignedleb128(const u1 **pStream) {
    const u1 *ptr = *pStream;
    int result = *(ptr++);
    if (result > 0x7f) {
        int cur = *(ptr++);
        result = (result & 0x7f) | ((cur & 0x7f) << 7);
        if (cur > 0x7f) {
            cur = *(ptr++);
            result |= (cur & 0x7f) << 14;
            if (cur > 0x7f) {
                cur = *(ptr++);
                result |= (cur & 0x7f) << 21;
                if (cur > 0x7f) {
                    cur = *(ptr++);
                    result |= cur << 28;
                }
            }
        }
    }
    *pStream = ptr;
    return result;
}*/


int readSignedLeb128(const u1 **pStream) {
    const u1 *ptr = *pStream;
    int result = *(ptr++);

    if (result <= 0x7f) {
        result = (result << 25) >> 25;
    } else {
        int cur = *(ptr++);
        result = (result & 0x7f) | ((cur & 0x7f) << 7);
        if (cur <= 0x7f) {
            result = (result << 18) >> 18;
        } else {
            cur = *(ptr++);
            result |= (cur & 0x7f) << 14;
            if (cur <= 0x7f) {
                result = (result << 11) >> 11;
            } else {
                cur = *(ptr++);
                result |= (cur & 0x7f) << 21;
                if (cur <= 0x7f) {
                    result = (result << 4) >> 4;
                } else {
                    /*
 * Note: We don't check to see if cur is out of
 * range here, meaning we tolerate garbage in the
 * high four-order bits.
 */
                    cur = *(ptr++);
                    result |= cur << 28;
                }
            }
        }
    }

    *pStream = ptr;
    return result;
}


class CodeItem {

    int start;
    int register_size = 0;
    int ins_size = 0;
    int outs_size = 0;
    int tries_size = 0;
    //int tries[];
    int debug_info_off = 0;
    int insns_size = 0;
    // vector<u2> insns;
    int padding;
    vector<DexTry> dexTry;
    int duiqi;
public:


    void dump(const u1 *addr) {

        start = (long) addr;
        memcpy(&register_size, addr, 2);
        memcpy(&ins_size, addr += 2, 2);
        memcpy(&outs_size, addr += 2, 2);
        memcpy(&tries_size, addr += 2, 2);
        memcpy(&debug_info_off, addr += 2, 4);
        memcpy(&insns_size, addr += 4, 4);
        addr += 4;

        LOGD("---------------Code_start--------------------");

        /* for (int i = 0; i < insns_size; ++i) {
             u2 xx;
             memcpy(&xx, (const void *) addr, 2);
             LOGD("%x", xx);
             insns.push_back(xx);
             addr += 2;
         }*/

        int insns_len = sizeof(char) * 2 * insns_size;

        char *insns = (char *) malloc(insns_len);
        memset(insns, 0, insns_len);
        memcpy(insns, addr, insns_len);


        for (int i = 0; i < insns_size*2; ++i) {
            LOGD("类的索引%d-----opcode:%02x", classs_index, *(insns + i));
        }


        free(insns);


        addr += insns_len;

        if (tries_size != 0 && insns_size & 1 == 1) {
            memcpy(&padding, addr, 2);
            addr += 2;
            LOGD("可选参数为padding：%d", padding);
        }


        for (int i = 0; i < tries_size; ++i) {
            DexTry aTry;
            memcpy(&aTry.startAddr, addr, 4);
            memcpy(&aTry.insnCount, addr += 4, 2);
            memcpy(&aTry.handlerOff, addr += 2, 2);
            addr += 2;
            dexTry.push_back(aTry);
            LOGD("类的索引：%d---------执行 DexTry：%d", classs_index, i);
        }
        int xx00 = 0;
        wirteFile(str_dump_opcode, (const void *) start, (long) addr - start);
        if (tries_size != 0) {



            int handlersize = readunsignedleb128(&addr);
            writeunsignedleb128((u1 *) &xx00, handlersize, str_dump_opcode);
            LOGD("得到handlersize%d", handlersize);

            for (int i = 0; i < handlersize; ++i) {
                int num_of_handlers = readSignedLeb128(&addr);

                if(num_of_handlers<0){

                    writeunsignedleb128((u1 *) &xx00, 128+num_of_handlers, str_dump_opcode);
                }else{
                    writeunsignedleb128((u1 *) &xx00, num_of_handlers, str_dump_opcode);
                }

                int num=num_of_handlers;
                if (num_of_handlers<=0) {
                    num=-num_of_handlers;
                }
                for (; num > 0; num--) {
                    int type = readunsignedleb128(&addr);
                    writeunsignedleb128((u1 *) &xx00, type, str_dump_opcode);
                    int uaddr = readunsignedleb128(&addr);
                    writeunsignedleb128((u1 *) &xx00, uaddr, str_dump_opcode);
                }
                if (num_of_handlers<=0) {
                    int alladdr = readunsignedleb128(&addr);
                    writeunsignedleb128((u1 *) &xx00, alladdr, str_dump_opcode);
                }


            }


        }

       int align= long(addr-start)%4;
        if( align!=0){

            memcpy(&duiqi, addr, 4-align);

            wirteFile(str_dump_opcode, addr, 4-align);
            addr+=(4-align);
        }


        LOGD("----------------Code_end-------------------");


    }
};


class Encodedfield {
public:
    int field_idx_diff = 0;
    int access_flags = 0;

    void dump(const u1 **addr) {
        field_idx_diff = readunsignedleb128(addr);
        int xx00 = 0;

        writeunsignedleb128((u1 *) &xx00, field_idx_diff, str_class_data);

        //fwrite(&xx00, sizeof(int), 1, &class_data);
        access_flags = readunsignedleb128(addr);
        writeunsignedleb128((u1 *) &xx00, access_flags, str_class_data);
        //fwrite(&xx00, sizeof(int), 1, &class_data);
    }

};


class Encodedmethod {
public:
    int method_idx_diff = 0;
    int access_flags = 0;
    int code_off = 0;

    void dump(const u1 **addr) {
        method_idx_diff = readunsignedleb128(addr);
        access_flags = readunsignedleb128(addr);
        code_off = readunsignedleb128(addr);

        int xx00 = 0;

        writeunsignedleb128((u1 *) &xx00, method_idx_diff, str_class_data);

        //fwrite(&xx00, sizeof(int), 1, &class_data);

        writeunsignedleb128((u1 *) &xx00, access_flags, str_class_data);
        //fwrite(&xx00, sizeof(int), 1, &class_data);
        int code_addr = code_off + (long) dexHeader;


        //读字节码文件长度确定偏移，设置code偏移

        writeunsignedleb128((u1 *) &xx00,
                            getfileLen(str_dump_opcode) - 40 + mappingxx.length +
                            str_class_data_len,//  140978,
                            str_class_data);//设置新的code偏移（code现在的字节数+文件大小，加上classdata大小，应为字节码被设置到了classdata后面）

        //fwrite(&xx00, sizeof(int), 1, &class_data);//这里重写写入偏移

        CodeItem codeItem;
        LOGD("函数名字--（%d）-----函数偏移(%d)-----------文件偏移(%d)", method_idx_diff, code_off,
             dexHeader->fileSize);
        if (code_off == 0) {
            LOGD("------------------------这个函数（%d）没有opcode", method_idx_diff);
            return;
        }
        if (code_off > dexHeader->fileSize) {
            LOGD("函数偏移大于文件偏移(%d)需要修复", code_off - dexHeader->fileSize);
        }

        codeItem.dump((const u1 *) code_addr);

    }

};


class ClassdataItem {
public:
    int static_field_size = 0;
    int instance_fields_size = 0;
    int direct_methods_size = 0;
    int virtual_methods_size = 0;
    vector<Encodedfield> static_fields;
    vector<Encodedfield> instance_fields;
    vector<Encodedmethod> direct_methods;
    vector<Encodedmethod> virtual_methods;


    void dump(const u1 **addr) {
        static_field_size = readunsignedleb128(addr);
        instance_fields_size = readunsignedleb128(addr);
        direct_methods_size = readunsignedleb128(addr);
        virtual_methods_size = readunsignedleb128(addr);
        int xx00 = 0;

        writeunsignedleb128((u1 *) &xx00, static_field_size, str_class_data);
        //fwrite(&xx00, sizeof(u1), 1, &class_data);
        writeunsignedleb128((u1 *) &xx00, instance_fields_size, str_class_data);
        //fwrite(&xx00, sizeof(u1), 1, &class_data);
        writeunsignedleb128((u1 *) &xx00, direct_methods_size, str_class_data);
        //fwrite(&xx00, sizeof(u1), 1, &class_data);
        writeunsignedleb128((u1 *) &xx00, virtual_methods_size, str_class_data);
        //fwrite(&xx00, sizeof(u1), 1, &class_data);


        for (int i = 0; i < static_field_size; ++i) {
            Encodedfield encodedfield;
            encodedfield.dump(addr);
            static_fields.push_back(encodedfield);
        }

        for (int i = 0; i < instance_fields_size; ++i) {
            Encodedfield encodedfield;
            encodedfield.dump(addr);
            instance_fields.push_back(encodedfield);
        }

        for (int i = 0; i < direct_methods_size; ++i) {

            Encodedmethod encodedfield;
            encodedfield.dump(addr);
            direct_methods.push_back(encodedfield);
        }


        for (int i = 0; i < virtual_methods_size; ++i) {
            Encodedmethod encodedfield;
            encodedfield.dump(addr);
            virtual_methods.push_back(encodedfield);
        }


    }


};

void printDexHeader1(DexFile *pDexFile) {
    int new_pDexFile_len = sizeof(DexClassDef) * (dexHeader->classDefsSize);
    DexClassDef *new_pDexFile = (DexClassDef *) malloc(new_pDexFile_len);
    memcpy(new_pDexFile, pDexFile->pClassDefs, new_pDexFile_len);

    char *strm = (char *) malloc(40);
    memset(strm, 0, 40);

    wirteFile(str_class_data, strm, 40);
    wirteFile(str_dump_opcode, strm, 40);
    free(strm);
    const DexClassDef *dexClassDef;
    for (int i = 0; i < dexHeader->classDefsSize; ++i) {
        classs_index = i;
        //读取class——data文件。（filesize，methodssize）设置cloass——data偏移。-------重新设置这里是应为修改了opcode偏移
        //fclose(&class_data);

        dexClassDef = pDexFile->pClassDefs + i;

        new_pDexFile->classDataOff = getfileLen(str_class_data) - 40 + mappingxx.length;
        wirteFile(str_class_defs, new_pDexFile, sizeof(DexClassDef));
        new_pDexFile++;

        if (dexClassDef->classDataOff == 0) {
            LOGD("------------------------类（%d）没有opcode", i);

        } else {
            //int classDate_addr = pDexFile->pClassDefs->classDataOff + (long) dexHeader;
            int classDate_addr = dexClassDef->classDataOff + (long) dexHeader;

            int *pClassDate_addr = &classDate_addr;

            ClassdataItem classdataItem;
            LOGD("------------------------类（%d）开始", i);
            classdataItem.dump((const u1 **) pClassDate_addr);
            LOGD("------------------------类(%d)结束", i);
        }

    }
    free(new_pDexFile);
}

void printDexHeader(DexFile *pDexFile) {
    dexHeader = (DexHeader *) pDexFile->pHeader;
    LOGD("string off;%d", dexHeader->stringIdsOff);
    LOGD("type off;%d", dexHeader->typeIdsOff);
    LOGD("proto off;%d", dexHeader->protoIdsOff);
    LOGD("field off;%d", dexHeader->fieldIdsOff);
    LOGD("method off;%d", dexHeader->methodIdsOff);
    LOGD("classdef off;%d", dexHeader->classDefsOff);
    LOGD("classdef size:%d", dexHeader->classDefsSize);

    LOGD("这个dex一共有 %d 个类", dexHeader->classDefsSize);


    if (dexHeader->classDefsSize == 1) {
        LOGD("这个dex类太少。不解析");

        return;
    }


    LOGD("类偏移(%d)-------dexHeader->fileSize(%d)", dexHeader->classDefsOff, dexHeader->fileSize);
    if (dexHeader->classDefsOff > dexHeader->fileSize) {
        LOGD("类偏移大于文件偏移(%d)需要修复", dexHeader->classDefsOff - dexHeader->fileSize);
    }


    printDexHeader1(pDexFile);
    str_class_data_len = getfileLen(str_class_data);
    remove(str_class_data);
    remove(str_dump_opcode);
    remove(str_class_defs);


    printDexHeader1(pDexFile);

    writeFile1(str_fppart1);
    writeFile1(str_class_defs);
    writeFile1(str_fppart3);
    writeFile1(str_class_data);
    writeFile1(str_dump_opcode);
    LOGD("------------------------ok");
}



/*
/*方法三，调用C库函数,*//*
char *join3(char *s1, char *s2) {
    char *result = (char *) malloc(strlen(s1) + strlen(s2) + 1);//+1 for the zero-terminator
    //in real code you would check for errors in malloc here
    if (result == NULL) exit(1);
    strcpy(result, s1);
    strcat(result, s2);
    return result;
}
*/


}