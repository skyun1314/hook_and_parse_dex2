package com.dexpaser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.dexpaser.PaserUtil.byte2int;

/**
 * Created by zk on 2017/6/12.
 */

public class PaserHaeder {
    public static int DexFileBytesIndex = 0;
    public static byte DexFileBytes[] = readDexFile("/Users/zk/Desktop/解析dex/classes.dex");
    public static Header header;
    public static int DexFileBytesIndex2 = 0;

    public static byte[] readDexFile(String path) {
        try {
            int byteread = 0;
            File oldfile = new File(path);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(path); //读入原文件
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, byteread);
                }
                inStream.close();
                return bos.toByteArray();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getHeaderInfoByoff(int lenth) {

        byte[] bytes = new byte[lenth];
        for (int i = 0; i < lenth; i++) {
            bytes[i] = DexFileBytes[DexFileBytesIndex + i];
        }
        DexFileBytesIndex += lenth;
        return bytes;

    }


    public static byte[] getDateByoff(int startaddr,int lenth) {

        byte[] bytes = new byte[lenth];
        for (int i = 0; i < lenth; i++) {
            bytes[i] = DexFileBytes[DexFileBytesIndex + i];
        }
        DexFileBytesIndex += lenth;
        return bytes;

    }


    public static byte[] getHeaderInfoByoff2(int lenth) {

        byte[] bytes = new byte[lenth];
        for (int i = 0; i < lenth; i++) {
            bytes[i] = DexFileBytes[DexFileBytesIndex2 + i];
        }
        DexFileBytesIndex2 += lenth;
        return bytes;

    }

    public static byte[] getHeaderInfoByoff(int off, int lenth) {

        byte[] bytes = new byte[lenth];
        for (int i = 0; i < lenth; i++) {
            bytes[i] = DexFileBytes[off + i];
        }
        return bytes;

    }


    public static void showAll(){

        header = new Header();

        byte[] dex_magic = getHeaderInfoByoff(8);
        byte[] checksum = getHeaderInfoByoff(4);
        byte[] MSHA1 = getHeaderInfoByoff(20);
        byte[] file_size = getHeaderInfoByoff(4);
        byte[] header_size = getHeaderInfoByoff(4);
        byte[] endian_tag = getHeaderInfoByoff(4);
        byte[] link_size = getHeaderInfoByoff(4);
        byte[] link_off = getHeaderInfoByoff(4);
        byte[] map_off = getHeaderInfoByoff(4);
        byte[] string_ids_size = getHeaderInfoByoff(4);
        byte[] string_ids_off = getHeaderInfoByoff(4);
        byte[] type_ids_size = getHeaderInfoByoff(4);
        byte[] type_ids_off = getHeaderInfoByoff(4);
        byte[] proto_ids_size = getHeaderInfoByoff(4);
        byte[] proto_ids_off = getHeaderInfoByoff(4);
        byte[] field_ids_size = getHeaderInfoByoff(4);
        byte[] field_ids_off = getHeaderInfoByoff(4);
        byte[] method_ids_size = getHeaderInfoByoff(4);
        byte[] method_ids_off = getHeaderInfoByoff(4);
        byte[] class_defs_size = getHeaderInfoByoff(4);
        byte[] class_defs_off = getHeaderInfoByoff(4);
        byte[] data_size = getHeaderInfoByoff(4);
        byte[] data_off = getHeaderInfoByoff(4);


        header.dex_magic = dex_magic;
        header.checksum = checksum;
        header.MSHA1 = MSHA1;

        header.file_size = byte2int(file_size);
        header.header_size = byte2int(header_size);
        header.endian_tag = byte2int(endian_tag);
        header.link_size = byte2int(link_size);
        header.link_off = byte2int(link_off);
        header.map_off = byte2int(map_off);
        header.string_ids_size = byte2int(string_ids_size);
        header.string_ids_off = byte2int(string_ids_off);
        header.type_ids_size = byte2int(type_ids_size);
        header.type_ids_off = byte2int(type_ids_off);
        header.proto_ids_size = byte2int(proto_ids_size);
        header.proto_ids_off = byte2int(proto_ids_off);
        header.field_ids_size = byte2int(field_ids_size);
        header.field_ids_off = byte2int(field_ids_off);
        header.method_ids_size = byte2int(method_ids_size);
        header.method_ids_off = byte2int(method_ids_off);
        header.class_defs_size = byte2int(class_defs_size);
        header.class_defs_off = byte2int(class_defs_off);
        header.data_size = byte2int(data_size);
        header.data_off = byte2int(data_off);

        System.out.println(header.toString());


        PaserItemsPaser.paserStringId();
        PaserItemsPaser.paserTypeId();
        PaserItemsPaser.paserProtoId();
        PaserItemsPaser.paserFieldId();
        PaserItemsPaser.paserMethodId();
       // PaserItemsPaser.paserClassId();

    }


    public static void main(String[] args) {
        showAll();

    }


}
