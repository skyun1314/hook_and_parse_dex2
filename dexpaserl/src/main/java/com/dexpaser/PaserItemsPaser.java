package com.dexpaser;

import com.dexpaser.Header_Items.Header_Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dexpaser.PaserUtil.bytesToString;
import static com.dexpaser.PaserUtil.decodeUleb128;

/**
 * Created by zk on 2017/6/12.
 */

public class PaserItemsPaser {

    public static List<Header_Strings> header_strings;
    public static List<Header_Items.Header_Types> header_types;
    public static List<Header_Items.Header_Proto> header_protos;
    public static List<Header_Items.Header_Field> header_field;
    public static List<Header_Items.Header_Method> header_method;

    public static void paserStringId() {
        header_strings = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.string_ids_size; i++) {

            int haeder_string_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            Header_Strings haeder_string = new Header_Strings();
            haeder_string.off = haeder_string_off;


            PaserUtil.readUnsignedLeb128_offset = haeder_string_off;
            int haeder_string_size = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());

            byte[] headerInfoByoff = PaserHaeder.getHeaderInfoByoff(PaserUtil.readUnsignedLeb128_offset, haeder_string_size);
            String s = bytesToString(headerInfoByoff);
            haeder_string.str = s;
            haeder_string.size = haeder_string_size;
            header_strings.add(haeder_string);

        }

        System.out.println("\n\n-----------------------paserStringId-------------------------------");
         System.out.println(header_strings.toString());
        System.out.println("-----------------------paserStringId-------------------------------\n\n");

    }

    public static void paserTypeId() {
        header_types = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.type_ids_size; i++) {
            Header_Items.Header_Types type = new Header_Items.Header_Types();
            int haeder_string_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            type.off = haeder_string_off;
            type.str = header_strings.get(type.off).str;
            header_types.add(type);
        }

        System.out.println("\n\n-----------------------paserTypeId-------------------------------");
        System.out.println(header_types.toString());
        System.out.println("-----------------------paserTypeId-------------------------------\n\n");

    }

    public static void paserProtoId() {
        header_protos = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.proto_ids_size; i++) {
            Header_Items.Header_Proto proto = new Header_Items.Header_Proto();
            int shorty_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int return_type_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int parameters_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            if (parameters_off != 0) {
                int parameters_offint = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(parameters_off, 4));

                int parameters_off_index = parameters_off + 4;
                proto.parameters_off = new ArrayList<>();
                for (int j = 0; j < parameters_offint; j++) {
                    int i1 = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(parameters_off_index, 2));
                    proto.parameters_off.add(header_types.get(i1).str);
                    parameters_off_index += 2;
                }
            }


            proto.shorty_idx = header_strings.get(shorty_idx).str;
            proto.return_type_idx = header_types.get(return_type_idx).str;
            header_protos.add(proto);
        }

        System.out.println("\n\n-----------------------paserProtoId-------------------------------");
        System.out.println(header_protos.toString());
        System.out.println("-----------------------paserProtoId-------------------------------\n\n");

    }

    public static void paserFieldId() {
        header_field = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.field_ids_size; i++) {
            Header_Items.Header_Field field = new Header_Items.Header_Field();
            int class_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(2));
            int type_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(2));
            int name_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));

            field.class_idx = header_types.get(class_idx).str;
            field.type_idx = header_types.get(type_idx).str;
            field.name_idx = header_strings.get(name_idx).str;


            header_field.add(field);
        }

         System.out.println("\n\n-----------------------paserFieldId-------------------------------");
        System.out.println(header_field.toString());
        System.out.println("-----------------------paserFieldId-------------------------------\n\n");

    }


    public static void paserMethodId() {
        header_method = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.method_ids_size; i++) {
            Header_Items.Header_Method method = new Header_Items.Header_Method();
            int class_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(2));
            int proto_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(2));
            int name_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));

            method.class_idx = header_types.get(class_idx).str;
            Header_Items.Header_Proto proto = header_protos.get(proto_idx);
            method.proto_idx = proto.return_type_idx + "(" + proto.parameters_off + ")";
            method.name_idx = header_strings.get(name_idx).str;


            header_method.add(method);
        }

         System.out.println("\n\n-----------------------paserMethodId-------------------------------");
        System.out.println(header_method.toString());
        System.out.println("-----------------------paserMethodId-------------------------------\n\n");

    }


    public static void paserClassId() {
        List<Header_Items.Header_Class> header_class = new ArrayList<>();
        for (int i = 0; i < PaserHaeder.header.class_defs_size; i++) {
            Header_Items.Header_Class classs = new Header_Items.Header_Class();
            int class_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int ACCESS_FLAGS = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int superclass_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int interfaces_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int source_file_idx = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int annotations_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int class_data_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));
            int values_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff(4));

            classs.class_idx = header_types.get(class_idx).str;
            classs.ACCESS_FLAGS = ACCESS_FLAGS;
            classs.ACCESS_FLAGS_string = PaserUtil.GetFriendlyAccessFlags(ACCESS_FLAGS).toString();
            ;
            classs.superclass_idx = header_types.get(superclass_idx).str;
            classs.interfaces_off = interfaces_off;
            classs.source_file_idx = header_strings.get(source_file_idx).str;
            classs.annotations_off = annotations_off;
            classs.class_data_off = class_data_off;
            classs.values_off = values_off;


            if (annotations_off != 0) {

            }
            if (class_data_off != 0) {
                PaserUtil.readUnsignedLeb128_offset = class_data_off;
                classs.class_data.static_fields_size = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                classs.class_data.instance_fields_size = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                classs.class_data.direct_methods_size = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                classs.class_data.virtual_methods_size = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());


                if (classs.class_data.static_fields_size != 0) {//static_fields_size

                    for (int j = 0; j < classs.class_data.static_fields_size; j++) {

                        Header_Items.Header_Class.EncodedField encodedField = new Header_Items.Header_Class.EncodedField();

                        encodedField.filed_idx_diff = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());

                        encodedField.access_flags = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        encodedField.access_flags_string = PaserUtil.GetFriendlyAccessFlags(encodedField.access_flags).toString();

                        classs.class_data.static_fields.add(encodedField);
                    }

                }
                if (classs.class_data.instance_fields_size != 0) {//instance_fields_size
                    for (int j = 0; j < classs.class_data.instance_fields_size; j++) {
                        Header_Items.Header_Class.EncodedField instance_fields = new Header_Items.Header_Class.EncodedField();
                        instance_fields.access_flags = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        instance_fields.access_flags_string = PaserUtil.GetFriendlyAccessFlags(instance_fields.access_flags).toString();
                        instance_fields.filed_idx_diff = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        classs.class_data.instance_fields.add(instance_fields);

                    }
                }
                if (classs.class_data.direct_methods_size != 0) {//direct_methods_size

                    for (int j = 0; j < classs.class_data.direct_methods_size; j++) {
                        Header_Items.Header_Class.EncodedMethod direct_methods = new Header_Items.Header_Class.EncodedMethod();

                        direct_methods.method_idx_diff = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        direct_methods.access_flags = decodeUleb128(PaserUtil.readUnsignedLeb128());
                        direct_methods.access_flags_string = PaserUtil.GetFriendlyAccessFlags(direct_methods.access_flags).toString();
                        direct_methods.code_off = decodeUleb128(PaserUtil.readUnsignedLeb128());
                        direct_methods.code = paserCode(direct_methods.code_off);
                        classs.class_data.direct_methods.add(direct_methods);
                    }
                }
                if (classs.class_data.virtual_methods_size != 0) {//virtual_methods_size

                    for (int j = 0; j < classs.class_data.virtual_methods_size; j++) {

                        Header_Items.Header_Class.EncodedMethod encodedMethod = new Header_Items.Header_Class.EncodedMethod();

                        encodedMethod.method_idx_diff = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        encodedMethod.access_flags = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        encodedMethod.access_flags_string = PaserUtil.GetFriendlyAccessFlags(encodedMethod.access_flags).toString();
                        encodedMethod.code_off = PaserUtil.decodeUleb128(PaserUtil.readUnsignedLeb128());
                        encodedMethod.code = paserCode(encodedMethod.code_off);


                        classs.class_data.virtual_methods.add(encodedMethod);
                    }
                }

            }
            if (values_off != 0) {

            }


            header_class.add(classs);
        }

        System.out.println("-----------------------paserClassId-------------------------------");
        System.out.println(header_class.toString());
        System.out.println("-----------------------paserClassId-------------------------------");
    }


    public static Header_Items.Header_Class.Code_item paserCode(int code_off) {
        Header_Items.Header_Class.Code_item code_item = new Header_Items.Header_Class.Code_item();
        PaserHaeder.DexFileBytesIndex2 = code_off;
        code_item.registers_size = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(2));
        code_item.ins_size = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(2));
        code_item.outs_size = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(2));
        code_item.tries_size = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(2));
        code_item.debug_info_off = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(4));
        code_item.insns_size = PaserUtil.byte2int(PaserHaeder.getHeaderInfoByoff2(4));

        for (int i = 0; i < code_item.insns_size; i++) {
            byte[] headerInfoByoff2 = PaserHaeder.getHeaderInfoByoff2(2);
            String op = PaserUtil.byteArray2String(headerInfoByoff2);
            code_item.insns.add(op);
            code_item.insns_byte.add(headerInfoByoff2);
        }

        Opcode2Smail(code_item);

        return code_item;
    }


    public static Map<String, Object> Opcode2Smail(Header_Items.Header_Class.Code_item code_item) {
        int insns_index = 0;

        List<String> opcodes=code_item.insns;
        while (insns_index<opcodes.size()){
            for (Opcodes.Opcode op : Opcodes.Opcode.values()) {

                String substring = opcodes.get(insns_index).substring(0, 2);


                if (Integer.parseInt(op.toString()) ==Integer.parseInt(substring,16) ) {
                    String gOpName = Opcodes.gOpNames[op.ordinal()];
                    String gOpFormat = Opcodes.Format[op.ordinal()];

                    Map<String, Object> objectMap = Opcodes.getforFormat(gOpFormat, gOpName, code_item, insns_index);
                    insns_index = (int) objectMap.get("size");
                    String code = (String) objectMap.get("code");

                    code_item.insns_string.add(code);
                    break;
                }
            }

        }





        return null;
    }


}
