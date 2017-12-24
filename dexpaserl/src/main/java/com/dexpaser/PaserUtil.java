package com.dexpaser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zk on 2017/6/12.
 */

public class PaserUtil {

    public static String byteArray2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String s = Integer.toHexString((bytes[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    public static int byte2int(byte[] res) {


        if (res.length == 1) {
            return (res[0] & 0xff);
        } else if (res.length == 2) {
            return (res[0] & 0xff) | ((res[1] << 8) & 0xff00);
        } else if (res.length == 3) {
            return (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
                    | ((res[2] << 24) >>> 8);
        } else if (res.length == 4) {
            return (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
                    | ((res[2] << 24) >>> 8) | (res[3] << 24);
        }


        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static String bytesToHexString(byte[] src) {
        //byte[] src = reverseBytes(src1);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString();
    }

    public static String bytesToString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            char c = (char) src[i];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static byte[] int2Byte(final int integer) {
        int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
            byteArray[3 - n] = (byte) (integer >>> (n * 8));

        return (byteArray);
    }

    public static int decodeUleb128(byte[] byteAry) {
        int index = 0, cur;
        int result = byteAry[index];
        result &= 0xff;
        index++;
        if (result > 0x7f) {
            cur = byteAry[index];
            cur &= 0xff;
            result = (result & 0x7f) | (cur & 0x7f) << 7;
            index++;
            if (cur > 0x7f) {
                cur = byteAry[index];
                cur &= 0xff;
                result = result | (cur & 0x7f) << 14;
                index++;
                if (cur > 0x7f) {
                    cur = byteAry[index];
                    cur &= 0xff;
                    result = result | (cur & 0x7f) << 21;
                    index++;
                }
                if (cur > 0x7f) {
                    cur = byteAry[index];
                    cur &= 0xff;
                    result = result | (cur & 0x7f) << 28;
                }
            }
        }
        return result;
    }


    /**
     * 读取C语言中的uleb类型
     * 目的是解决整型数值浪费问题
     * 长度不固定，在1~5个字节中浮动
     *
     * @return
     */

    public static int readUnsignedLeb128_offset;

    public static byte[] readUnsignedLeb128() {
        List<Byte> byteAryList = new ArrayList<Byte>();
        byte bytes = copyByte(PaserHaeder.DexFileBytes, readUnsignedLeb128_offset, 1)[0];
        byte highBit = (byte) (bytes & 0x80);
        byteAryList.add(bytes);
        readUnsignedLeb128_offset++;
        while (highBit != 0) {
            bytes = copyByte(PaserHaeder.DexFileBytes, readUnsignedLeb128_offset, 1)[0];
            highBit = (byte) (bytes & 0x80);
            readUnsignedLeb128_offset++;
            byteAryList.add(bytes);
        }
        byte[] byteAry = new byte[byteAryList.size()];
        for (int j = 0; j < byteAryList.size(); j++) {
            byteAry[j] = byteAryList.get(j);
        }
        return byteAry;
    }


    public static byte[] copyByte(byte[] src, int start, int len) {
        if (src == null) {
            return null;
        }
        if (start > src.length) {
            return null;
        }
        if ((start + len) > src.length) {
            return null;
        }
        if (start < 0) {
            return null;
        }
        if (len <= 0) {
            return null;
        }
        byte[] resultByte = new byte[len];
        for (int i = 0; i < len; i++) {
            resultByte[i] = src[i + start];
        }
        return resultByte;
    }

    public static void haha(char descriptor) {
        String desc;
        switch (descriptor) {
            case 'V':
                desc = "void";
                break;
            case 'Z':
                desc = "boolean";
                break;
            case 'B':
                desc = "byte";
                break;
            case 'S':
                desc = "short";
                break;
            case 'C':
                desc = "char";
                break;
            case 'I':
                desc = "int";
                break;
            case 'J':
                desc = "long";
                break;
            case 'F':
                desc = "float";
                break;
            case 'D':
                desc = "double";
                break;
        }
    }


    public static enum ACC {
        // 利用构造函数传参

        ACC_PUBLIC(0x00000001),       // class, field, method, ic
        ACC_PRIVATE(0x00000002),       // field, method, ic
        ACC_PROTECTED(0x00000004),       // field, method, ic
        ACC_STATIC(0x00000008),       // field, method, ic
        ACC_FINAL(0x00000010),       // class, field, method, ic
        ACC_SYNCHRONIZED(0x00000020),       // method (only allowed on natives)
        ACC_SUPER(0x00000020),       // class (not used in Dalvik)
        ACC_VOLATILE(0x00000040),       // field
        ACC_BRIDGE(0x00000040),       // method (1.5)
        ACC_TRANSIENT(0x00000080),       // field
        ACC_VARARGS(0x00000080),       // method (1.5)
        ACC_NATIVE(0x00000100),       // method
        ACC_INTERFACE(0x00000200),       // class, ic
        ACC_ABSTRACT(0x00000400),       // class, method, ic
        ACC_STRICT(0x00000800),       // method
        ACC_SYNTHETIC(0x00001000),       // field, method, ic
        ACC_ANNOTATION(0x00002000),       // class, ic (1.5)
        ACC_ENUM(0x00004000),       // class, field, ic (1.5)
        ACC_CONSTRUCTOR(0x00010000),       // method (Dalvik only)
        ACC_DECLARED_SYNCHRONIZED(0x00020000);     // method (Dalvik only)

        // 定义私有变量
        private int nCode;

        // 构造函数，枚举类型只能为私有
        private ACC(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }


          /*   ACC_CLASS_MASK =
                    (ACC_PUBLIC | ACC_FINAL | ACC_INTERFACE | ACC_ABSTRACT
                            | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM),
            ACC_INNER_CLASS_MASK =
                    (ACC_CLASS_MASK | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC),
            ACC_FIELD_MASK =
                    (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
                            | ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM),
            ACC_METHOD_MASK =
                    (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
                            | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE
                            | ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC | ACC_CONSTRUCTOR
                            | ACC_DECLARED_SYNCHRONIZED);*/
    }

    public static List<String> GetFriendlyAccessFlags(int FLAGS) {
        List<String>accs=new ArrayList<>();


        for (ACC acc:ACC.values()) {

            if (FLAGS<=0){
                break;
            }

            int oo=25&1;
            int xx = FLAGS & Integer.parseInt(acc.toString());
            if ((xx)>0){
                FLAGS-=xx;
                accs.add(acc.name());
            }
        }

        return accs;
    }


   /* public static String GetFriendlyAccessFlag(ACC type) {
        switch (type) {
            case ACC_PUBLIC:
                return "public";
            case ACC_PRIVATE:
                return "private";
            case ACC_PROTECTED:
                return "protected";
            case ACC_STATIC:
                return "static";
            case ACC_FINAL:
                return "final";
            case ACC_SYNCHRONIZED:
                return "synchronized";
            case ACC_VOLATILE:
                //if (type == AF_FIELD)
                    return "volatile";
                //else  return "bridge"; // 0x40 is 'bridge' for methods
            case ACC_TRANSIENT:
                //if (type == AF_FIELD)
                    return "transient";
                //else  return "varargs"; // 0x80 is 'varargs' for methods
            case ACC_NATIVE:
                return "native";
            case ACC_INTERFACE:
                return "interface";
            case ACC_ABSTRACT:
                return "abstract";
            case ACC_STRICT:
                return "strict";
            case ACC_SYNTHETIC:
                return "synthetic";
            case ACC_ANNOTATION:
                return "annotation";
            case ACC_ENUM:
                return "enum";
            case ACC_CONSTRUCTOR:
                return "constructor";
            case ACC_DECLARED_SYNCHRONIZED:
                return "declared-synchronized";
        }
        return "ERROR";
    }*/

}
