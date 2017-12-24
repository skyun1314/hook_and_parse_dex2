package com.example.hookcheck;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * Created by zk on 2017/12/21.
 */

public class Encode {

    private static cf mmmmmm = new cf(15);
    private static short nnnnn;
    private static byte[] aaaaaa = new byte[5];
    private static byte[] rrrrrr =new byte[1];
    private static byte B=15;
    private static final byte[] f = new byte[16];
    public static StringBuffer buffer=new StringBuffer();;


    private static db o;
    static byte[]b = new byte[10];

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private  static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void d(){
        b((byte) 0);
    }

    public static void b()  {
        nnnnn =mmmmmm.a();
    }


    public static void a(int i) {
        b(c(i));
    }

    public static void a(ByteBuffer byteBuffer) {
        a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }


    public static void a(String str) {

        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void a(long j) {
        b(c(j));
    }
    private static long c(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static final class dd {
        public final byte a;
        public final byte b;
        public final int c;

        public dd() {
            this((byte) 0, (byte) 0, 0);
        }

        public dd(byte b, byte b2, int i) {
            this.a = b;
            this.b = b2;
            this.c = i;
        }
    }
    public static void a(dd ddVar)  {
        if (ddVar.c == 0) {
            d(0);
            return;
        }
        b(ddVar.c);
        d((e(ddVar.a) << 4) | e(ddVar.b));
    }

    private static byte e(byte b) {
        return f[b];
    }


    private static void b(long j) {
        int i = 0;
        while ((-128 & j) != 0) {
            int i2 = i + 1;
           b[i] = (byte) ((int) ((127 & j) | 128));
            j >>>= 7;
            i = i2;
        }
        int i3 = i + 1;
        b[i] = (byte) ((int) j);
        b(b, 0, i3);
    }



    private static void a(byte[] bArr, int i, int i2)  {
        b(i2);
        b(bArr, i, i2);
    }

    static {
        f[0] = (byte) 0;
        f[2] = (byte) 1;
        f[3] = (byte) 3;
        f[6] = (byte) 4;
        f[8] = (byte) 5;
        f[10] = (byte) 6;
        f[4] = (byte) 7;
        f[11] = (byte) 8;
        f[15] = (byte) 9;
        f[14] = (byte) 10;
        f[13] = (byte) 11;
        f[12] = (byte) 12;
    }


    private static void a(db dbVar, byte b)  {
        if (b == (byte) -1) {
            b=   f[dbVar.b];

        }
        if (dbVar.c <= nnnnn || dbVar.c - nnnnn > 15) {
            b(b);
            a(dbVar.c);
        } else {
            d(((dbVar.c - nnnnn) << 4) | b);
        }
        nnnnn = dbVar.c;
    }


    private static void d(int i)  {
        b((byte) i);
    }



    public static void a(short s) {
        b(c((int) s));
    }
    private static int c(int i) {
        return (i << 1) ^ (i >> 31);
    }



    private static void b(int i) {
        int i2 = 0;
        while ((i & -128) != 0) {
            int i3 = i2 + 1;
            aaaaaa[i2] = (byte) ((i & 127) | 128);
            i >>>= 7;
            i2 = i3;
        }
        int i4 = i2 + 1;
        aaaaaa[i2] = (byte) i;
        b(aaaaaa, 0, i4);
    }



    private static void b(byte b) {
        rrrrrr[0] = b;
        b(rrrrrr,0,1);
    }
    public static void b(byte[] bArr, int start, int len){
        // b(bArr, i, bArr.length);
        // Log.e("wodelog","终极方法");
        if (bArr == null) {
            return ;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = start; i < len; i++) {
            stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bArr[i])}));
        }

        String s = stringBuffer.toString().toLowerCase(Locale.US);
        buffer.append(s);

    }

    public static final class dl {
        public final String a;

        public dl() {
            this("");
        }

        public dl(String str) {
            this.a = str;
        }
    }

    public static class db {
        public final String a;
        public final byte b;
        public final short c;


        public db(String str, byte b, short s) {
            this.a = str;
            this.b = b;
            this.c = s;
        }

        public String toString() {
            return "<TField name:'" + this.a + "' type:" + this.b + " field-id:" + this.c + ">";
        }

        public boolean a(db dbVar) {
            return this.b == dbVar.b && this.c == dbVar.c;
        }
    }
    public static boolean H() {
        return a(B, 3);
    }

    public static final boolean a(byte b, int i) {
        return a((int) b, i);
    }
    public static final boolean a(int i, int i2) {
        return ((1 << i2) & i) != 0;
    }
    public static void a(db dbVar) {

        a(dbVar, (byte) -1);
    }
    public static final byte a(byte b, int i, boolean z) {
        return (byte) a((int) b, i, z);
    }

    public static final int a(int i, int i2, boolean z) {
        if (z) {
            return (1 << i2) | i;
        }
        return b(i, i2);
    }
    public static final int b(int i, int i2) {
        return ((1 << i2) ^ -1) & i;
    }


    private static void a(long j, byte[] bArr, int i) {
        bArr[i + 0] = (byte) ((int) (j & 255));
        bArr[i + 1] = (byte) ((int) ((j >> 8) & 255));
        bArr[i + 2] = (byte) ((int) ((j >> 16) & 255));
        bArr[i + 3] = (byte) ((int) ((j >> 24) & 255));
        bArr[i + 4] = (byte) ((int) ((j >> 32) & 255));
        bArr[i + 5] = (byte) ((int) ((j >> 40) & 255));
        bArr[i + 6] = (byte) ((int) ((j >> 48) & 255));
        bArr[i + 7] = (byte) ((int) ((j >> 56) & 255));
    }


    public static void a(double d) {
        byte[] bArr = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        a(Double.doubleToLongBits(d), bArr, 0);
        b(bArr, 0, bArr.length);

    }


    public static void a(boolean z) {
        byte b = (byte) 1;
        if (o != null) {
            db dbVar =o;
            if (!z) {
                b = (byte) 2;
            }
            a(dbVar, b);
           o = null;
            return;
        }
        if (!z) {
            b = (byte) 2;
        }
        b(b);
    }



    public static void a() {
        mmmmmm.a(nnnnn);
       nnnnn = (short) 0;
    }


    /* compiled from: TList */
    public static final class dc {
        public final byte a;
        public final int b;

        public dc() {
            this((byte) 0, 0);
        }

        public dc(byte b, int i) {
            this.a = b;
            this.b = i;
        }
    }
    public static void a(dc dcVar){
        a(dcVar.a, dcVar.b);
    }




    public static class cf {
        private short[] a;
        private int b = -1;

        public cf(int i) {
            this.a = new short[i];
        }

        public short a() {
            short[] sArr = this.a;
            int i = this.b;
            this.b = i - 1;
            return sArr[i];
        }

        public void a(short s) {
            if (this.a.length == this.b + 1) {
                d();
            }
            short[] sArr = this.a;
            int i = this.b + 1;
            this.b = i;
            sArr[i] = s;
        }

        private void d() {
            Object obj = new short[(this.a.length * 2)];
            System.arraycopy(this.a, 0, obj, 0, this.a.length);
            this.a = (short[]) obj;
        }

        public short b() {
            return this.a[this.b];
        }

        public void c() {
            this.b = -1;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<ShortStack vector:[");
            for (int i = 0; i < this.a.length; i++) {
                if (i != 0) {
                    stringBuilder.append(" ");
                }
                if (i == this.b) {
                    stringBuilder.append(">>");
                }
                stringBuilder.append(this.a[i]);
                if (i == this.b) {
                    stringBuilder.append("<<");
                }
            }
            stringBuilder.append("]>");
            return stringBuilder.toString();
        }
    }

}
