package com.dexpaser;

/**
 * Created by zk on 2017/6/12.
 */

public class Header {


    byte[] dex_magic;
    byte[] checksum;
    byte[]MSHA1;
    int file_size;
    int header_size ;
    int endian_tag ;
    int link_size ;
    int link_off;
    int map_off ;
    int string_ids_size ;
    int string_ids_off ;
    int type_ids_size ;
    int type_ids_off;
    int proto_ids_size ;
    int proto_ids_off ;
    int field_ids_size;
    int field_ids_off;
    int method_ids_size;
    int method_ids_off ;
    int class_defs_size;
    int class_defs_off ;
    int data_size ;
    int data_off;


    @Override
    public String toString() {
        return "Header{" +
                "\n\tdex_magic = " + PaserUtil.byteArray2String(dex_magic) +
                ", \n\tchecksum = " +  PaserUtil.byteArray2String(checksum) +
                ", \n\tMSHA1 = " + PaserUtil.byteArray2String(MSHA1) +
                ", \n\tfile_size = " + file_size +
                ", \n\theader_size = " + header_size +
                ", \n\tendian_tag = " + Integer.toHexString(endian_tag)  +
                ", \n\tlink_size = " + link_size +
                ", \n\tlink_off = " + link_off +
                ", \n\tmap_off = " + map_off +
                ", \n\tstring_ids_size = " + string_ids_size +
                ", \n\tstring_ids_off = " + string_ids_off +
                ", \n\ttype_ids_size = " + type_ids_size +
                ", \n\ttype_ids_off = " + type_ids_off +
                ", \n\tproto_ids_size = " + proto_ids_size +
                ", \n\tproto_ids_off = " + proto_ids_off +
                ", \n\tfield_ids_size = " + field_ids_size +
                ", \n\tfield_ids_off = " + field_ids_off +
                ", \n\tmethod_ids_size = " + method_ids_size +
                ", \n\tmethod_ids_off = " + method_ids_off +
                ", \n\tclass_defs_size = " + class_defs_size +
                ", \n\tclass_defs_off = " + class_defs_off +
                ", \n\tdata_size = " + data_size +
                ", \n\tdata_off = " + data_off +
                '}';
    }



}
