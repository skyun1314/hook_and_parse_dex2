package com.example.hookcheck;

import android.util.Log;

import java.nio.ByteBuffer;

/**
 * Created by zk on 2017/12/21.
 */

public class Encode1 {

    class bp{
        public String a="1.0";   //version 默认
        public String b="5836a0ab7f2c746c4b001800";//address  UMENG_APPKEY 默认
        public String c="fd123933bffbc688b5aec3046b2f8e95d54b1bec2fcfcbadd887b476a3413a5a";//signature
        public int d=1;//serial_num
        public int e=1513755389;  //ts_secs ((int)(System.currentTimeMillis() / 1000));//ts_secs
        public int f=694;//length
        /* entity:*/    public ByteBuffer g=ByteBuffer.wrap(Encode.hexStringToBytes("789C8D924F6BD440188787DDA6420FED2A24BCB239E4182F613299C9CCE6B68858B0EAE21FF420C89B9959136836344B17DCCF504A0FBDFB097AF028540F7E02BD79F423F8114CD62E167451E6383F9EF7797F33BE4BBA437C00A1921429E672CAB4E4A9E639A5B1A2141C16B148B89FFBB0A7EB2A9A2F8F4C644A3CB48D4BC011118F18ECBE3D5E16C7387BA3EDBC2899E8887B8918C54C5091A49C2AC1204845C230E59A1BE44A68118FB84E7383DA4A25F338819B5395E12833349371969A8CCAF0D6F8F1D34950CF827DAC2ADBEC5B34301C3F79B890C1A4A9DB71F3BA091ABB0868102EE4E11DB8319E99A62E0D38BC55E3BEFB7DE8FE1810B553AC0045075867BC2FE7EFDE7F7801FD83FBF760FBC1B3E78A4FE05A90F8ED8ABD6501BDBB8F14115BC4F72E2FBF5E9CBE240743E764077F515EB7C3065621DAA9540CF5C83226BC6F1FBBA0DB23FD0AF55F36F3CE3E5D05B6E7B669FB84014D8DC59C699EA0CA636EBDD375C4294D65C4BF1BFC0DDD2A2B5BFEF106DEC9FAFEF62BB8E61F6EF227B002851B4004AEE4C34DF2ED1759B987FFEB4EA02B2CDC581821E4272B5FC8F1"));
        public String h="00003933bffbc688b5aec3046b2f8e95d54b1bec2fcfcbadd887b476a3410000";//guid
        public String i="8a40e464395aa7b957456e28af9c6563";//checksum
        public int j=0;//codex

    }






    private  final Encode.db m = new Encode.db("version", (byte) 11, (short) 1);
    private  final Encode.db n = new Encode.db("address", (byte) 11, (short) 2);
    private  final Encode.db o = new Encode.db("signature", (byte) 11, (short) 3);
    private  final Encode.db p = new Encode.db("serial_num", (byte) 8, (short) 4);
    private  final Encode.db q = new Encode.db("ts_secs", (byte) 8, (short) 5);
    private  final Encode.db r = new Encode.db("length", (byte) 8, (short) 6);
    private  final Encode.db s = new Encode.db("entity", (byte) 11, (short) 7);
    private  final Encode.db t = new Encode.db("guid", (byte) 11, (short) 8);
    private  final Encode.db u = new Encode.db("checksum", (byte) 11, (short) 9);
    private  final Encode.db v = new Encode.db("codex", (byte) 8, (short) 10);







    public void b()  {
        bp bpVar= new bp();

        Encode.a();
        if (bpVar.a != null) {
            Encode.a(m);
            Encode.a(bpVar.a);
        }
        if (bpVar.b != null) {
            Encode.a(n);
            Encode.a(bpVar.b);
        }
        if (bpVar.c != null) {
            Encode.a(o);
            Encode.a(bpVar.c);
        }
        Encode.a(p);
        Encode. a(bpVar.d);
        Encode. a(q);
        Encode. a(bpVar.e);
        Encode.a(r);
        Encode. a(bpVar.f);
        if (bpVar.g != null) {
            Encode. a(s);
            Encode. a(bpVar.g);
        }
        if (bpVar.h != null) {
            Encode. a(t);
            Encode. a(bpVar.h);
        }
        if (bpVar.i != null) {
            Encode.  a(u);
            Encode. a(bpVar.i);
        }
        if (Encode.H()) {
            Encode.  a(v);
            Encode. a(bpVar.j);
        }
        Encode.d();
        Encode. b();
        Log.e("wodelog", Encode.buffer.toString());
        Encode.buffer.setLength(0);
    }

}
