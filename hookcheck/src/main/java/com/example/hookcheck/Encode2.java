package com.example.hookcheck;

import android.util.ArrayMap;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2017/12/21.
 */

public class Encode2 {




    static class ap {
        private static final Encode.dl e = new Encode.dl("ClientStats");
        private static final Encode.db f = new Encode.db("successful_requests", (byte) 8, (short) 1);
        private static final Encode.db g = new Encode.db("failed_requests", (byte) 8, (short) 2);
        private static final Encode.db h = new Encode.db("last_request_spent_ms", (byte) 8, (short) 3);

        public int a=0;
        public int b=0;
        public int c=0;
        public byte m;


        public boolean l() {
            return Encode.a((int)this.m, 2);
        }




        public static void b(ap apVar) {
            Encode.a();
            Encode.a(ap.f);
            Encode.a(apVar.a);
            Encode.a(ap.g);
            Encode.a(apVar.b);
            if (apVar.l()) {
                Encode.a(ap.h);
                Encode.a(apVar.c);
            }
            Encode.d();
            Encode.b();
        }

    }


    public enum bk   {
        ANDROID(0),
        IOS(1),
        WINDOWS_PHONE(2),
        WINDOWS_RT(3);

        private final int e;

        private bk(int i) {
            this.e = i;
        }

        public int a() {
            return this.e;
        }

        public static bk a(int i) {
            switch (i) {
                case 0:
                    return ANDROID;
                case 1:
                    return IOS;
                case 2:
                    return WINDOWS_PHONE;
                case 3:
                    return WINDOWS_RT;
                default:
                    return null;
            }
        }
    }

    static class ao {


        public String a="5836a0ab7f2c746c4b001800";
        public String b="2.2.5";
        public int c=225;
        public String d="com.szqd.dialer";
        public bk e=bk.ANDROID;
        public String f="5.4.2";
        public String g="yuzhuangceshi2";
        public String h;
        public String i;
        public int j=0;


        private byte z;


        private static final Encode.db m = new Encode.db("key", (byte) 11, (short) 1);
        private static final Encode.db n = new Encode.db("version", (byte) 11, (short) 2);
        private static final Encode.db o = new Encode.db("version_index", (byte) 8, (short) 3);
        private static final Encode.db p = new Encode.db("package_name", (byte) 11, (short) 4);
        private static final Encode.db q = new Encode.db("sdk_type", (byte) 8, (short) 5);
        private static final Encode.db r = new Encode.db("sdk_version", (byte) 11, (short) 6);
        private static final Encode.db s = new Encode.db("channel", (byte) 11, (short) 7);
        private static final Encode.db t = new Encode.db("wrapper_type", (byte) 11, (short) 8);
        private static final Encode.db u = new Encode.db("wrapper_version", (byte) 11, (short) 9);
        private static final Encode.db v = new Encode.db("vertical_type", (byte) 8, (short) 10);
        public boolean l() {
            return Encode.a(this.z, 0);
        }


        public boolean G() {
            return Encode.a(this.z, 1);
        }


        public static void b( ao aoVar) {
            Encode.a();
            if (aoVar.a != null) {
                Encode.a(ao.m);
                Encode.a(aoVar.a);
            }
            if (aoVar.b != null) {
                Encode.a(ao.n);
                Encode.a(aoVar.b);
            }
            if (aoVar.l()) {
                Encode.a(ao.o);
                Encode.a(aoVar.c);
            }
            if (aoVar.d != null ) {
                Encode.a(ao.p);
                Encode.a(aoVar.d);
            }
            if (aoVar.e != null) {
                Encode.a(ao.q);
                Encode.a(0);

                        /*ANDROID(0),
                        IOS(1),
                        WINDOWS_PHONE(2),
                        WINDOWS_RT(3);*/


            }
            if (aoVar.f != null) {
                Encode.a(ao.r);
                Encode.a(aoVar.f);
            }
            if (aoVar.g != null) {
                Encode.a(ao.s);
                Encode.a(aoVar.g);
            }
            if (aoVar.h != null) {
                Encode.a(ao.t);
                Encode.a(aoVar.h);
            }
            if (aoVar.i != null) {
                Encode.a(ao.u);
                Encode.a(aoVar.i);
            }
            if (aoVar.G()) {
                Encode.a(ao.v);
                Encode.a(aoVar.j);
            }
            Encode.d();
            Encode.b();
        }
    }


    /* compiled from: Resolution */
    public static class bi  {
        private static final Encode.dl d = new Encode.dl("Resolution");
        private static final Encode.db e = new Encode.db("height", (byte) 8, (short) 1);
        private static final Encode.db f = new Encode.db("width", (byte) 8, (short) 2);

        private static final int h = 0;
        private static final int i = 1;
        public int a=1776;
        public int b=1080;
        private byte j;

        public void b(bi biVar)  {
            Encode.a();
            Encode.a(bi.e);
            Encode.a(biVar.a);
            Encode.a(bi.f);
            Encode.a(biVar.b);
            Encode.d();
            Encode.b();
        }

    }


        static class ar {
            private static final Encode.db A = new Encode.db("os_version", (byte) 11, (short) 8);
            private static final Encode.db B = new Encode.db("resolution", (byte) 12, (short) 9);
            private static final Encode.db C = new Encode.db("is_jailbroken", (byte) 2, (short) 10);
            private static final Encode.db D = new Encode.db("is_pirated", (byte) 2, (short) 11);
            private static final Encode.db E = new Encode.db("device_board", (byte) 11, (short) 12);
            private static final Encode.db F = new Encode.db("device_brand", (byte) 11, (short) 13);
            private static final Encode.db G = new Encode.db("device_manutime", (byte) 10, (short) 14);
            private static final Encode.db H = new Encode.db("device_manufacturer", (byte) 11, (short) 15);
            private static final Encode.db I = new Encode.db("device_manuid", (byte) 11, (short) 16);
            private static final Encode.db J = new Encode.db("device_name", (byte) 11, (short) 17);
           // private static final Map<Class<? extends do>, dp> K = new HashMap();
            private static final int L = 0;
            private static final int M = 1;
            private static final int N = 2;
          //  public static final Map<e, ct> r;
            private static final Encode.dl s = new Encode.dl("DeviceInfo");
            private static final Encode.db t = new Encode.db("device_id", (byte) 11, (short) 1);
            private static final Encode.db u = new Encode.db("idmd5", (byte) 11, (short) 2);
            private static final Encode.db v = new Encode.db("mac_address", (byte) 11, (short) 3);
            private static final Encode.db w = new Encode.db("open_udid", (byte) 11, (short) 4);
            private static final Encode.db x = new Encode.db("model", (byte) 11, (short) 5);
            private static final Encode.db y = new Encode.db("cpu", (byte) 11, (short) 6);
            private static final Encode.db z = new Encode.db("os", (byte) 11, (short) 7);

            private byte O;
        public String a="359125053640852";
        public String b="6532a64c4da485c5194c6bdace787b13";
        public String c="f8:a9:d0:71:6d:07";
        public String d;
        public String e="AOSP on HammerHead";
        public String f="ARMv7 Processor rev 0 (v7l)";
        public String g="Android";
        public String h="4.4.4";
        public bi i=new bi();
        public boolean j;
        public boolean k;
        public String l="hammerhead";
        public String m="Android";
        public long n=1502350665000L;
        public String o="LGE";
        public String p="KTU84P";
        public String q="hammerhead";


            public boolean G() {
                return Encode.a(this.O, 0);
            }
            public boolean J() {
                return Encode.a(this.O, 1);
            }
            public boolean S() {
                return Encode.a(this.O, 2);
            }


            public static void b( ar arVar)  {
            Encode.a();
            if (arVar.a != null ) {
                Encode.a(ar.t);
                Encode.a(arVar.a);
            }
            if (arVar.b != null ) {
                Encode.a(ar.u);
                Encode.a(arVar.b);
            }
            if (arVar.c != null) {
                Encode.a(ar.v);
                Encode.a(arVar.c);
            }
            if (arVar.d != null ) {
                Encode.a(ar.w);
                Encode.a(arVar.d);
            }
            if (arVar.e != null ) {
                Encode.a(ar.x);
                Encode.a(arVar.e);
            }
            if (arVar.f != null ) {
                Encode.a(ar.y);
                Encode.a(arVar.f);
            }
            if (arVar.g != null ) {
                Encode.a(ar.z);
                Encode.a(arVar.g);
            }
            if (arVar.h != null ) {
                Encode.a(ar.A);
                Encode.a(arVar.h);
            }
            if (arVar.i != null ) {
                Encode.a(ar.B);
                arVar.i.b(new bi());
            }
            if (arVar.G()) {
                Encode.a(ar.C);
                Encode.a(arVar.j);
            }
            if (arVar.J()) {
                Encode.a(ar.D);
                Encode.a(arVar.k);
            }
            if (arVar.l != null ) {
                Encode.a(ar.E);
                Encode.a(arVar.l);
            }
            if (arVar.m != null ) {
                Encode.a(ar.F);
                Encode.a(arVar.m);
            }
            if (arVar.S()) {
                Encode.a(ar.G);
                Encode.a(arVar.n);
            }
            if (arVar.o != null ) {
                Encode.a(ar.H);
                Encode.a(arVar.o);
            }
            if (arVar.p != null) {
                Encode.a(ar.I);
                Encode.a(arVar.p);
            }
            if (arVar.q != null ) {
                Encode.a(ar.J);
                Encode.a(arVar.q);
            }
            Encode.d();
            Encode.b();
        }

    }

    public enum al {
        ACCESS_TYPE_UNKNOWN(0),
        ACCESS_TYPE_2G_3G(1),
        ACCESS_TYPE_WIFI(2),
        ACCESS_TYPE_ETHERNET(3);

        private final int e;

        private al(int i) {
            this.e = i;
        }

        public int a() {
            return this.e;
        }

        public static al a(int i) {
            switch (i) {
                case 0:
                    return ACCESS_TYPE_UNKNOWN;
                case 1:
                    return ACCESS_TYPE_2G_3G;
                case 2:
                    return ACCESS_TYPE_WIFI;
                case 3:
                    return ACCESS_TYPE_ETHERNET;
                default:
                    return null;
            }
        }
    }
    static class bf {
        private static final Encode.dl l = new Encode.dl("MiscInfo");
        private static final Encode.db m = new Encode.db("time_zone", (byte) 8, (short) 1);
        private static final Encode.db n = new Encode.db("language", (byte) 11, (short) 2);
        private static final Encode.db o = new Encode.db("country", (byte) 11, (short) 3);
        private static final Encode.db p = new Encode.db("latitude", (byte) 4, (short) 4);
        private static final Encode.db q = new Encode.db("longitude", (byte) 4, (short) 5);
        private static final Encode.db r = new Encode.db("carrier", (byte) 11, (short) 6);
        private static final Encode.db s = new Encode.db("latency", (byte) 8, (short) 7);
        private static final Encode.db t = new Encode.db("display_name", (byte) 11, (short) 8);
        private static final Encode.db u = new Encode.db("access_type", (byte) 8, (short) 9);
        private static final Encode.db v = new Encode.db("access_subtype", (byte) 11, (short) 10);


        private static final int x = 0;
        private static final int y = 1;
        private static final int z = 2;
        private byte B;

        public int a=0;
        public String b="zh";
        public String c="CN";
        public double d;
        public double e;
        public String f;
        public int g;
        public String h="中国电信";
        public al i=al.ACCESS_TYPE_WIFI;
        public String j;

        public boolean e() {
            return Encode.a(this.B, 0);
        }
        public boolean o() {
            return Encode.a(this.B, 1);
        }

        public void e(boolean z) {
            this.B = Encode.a(this.B, 2, z);
        }

        public boolean r() {
            return Encode.a(this.B, 2);
        }


        public boolean x() {
            return Encode.a(this.B, 3);
        }

        public static void b( bf bfVar) {
            Encode.a();
            if (bfVar.e()) {
                Encode.a(bf.m);
                Encode.a(bfVar.a);
            }
            if (bfVar.b != null) {
                Encode.a(bf.n);
                Encode.a(bfVar.b);
            }
            if (bfVar.c != null ) {
                Encode.a(bf.o);
                Encode.a(bfVar.c);
            }
            if (bfVar.o()) {
                Encode.a(bf.p);
                Encode.a(bfVar.d);
            }
            if (bfVar.r()) {
                Encode.a(bf.q);
                Encode.a(bfVar.e);
            }
            if (bfVar.f != null ) {
                Encode.a(bf.r);
                Encode.a(bfVar.f);
            }
            if (bfVar.x()) {
                Encode.a(bf.s);
                Encode.a(bfVar.g);
            }
            if (bfVar.h != null) {
                Encode.a(bf.t);
                Encode.a(bfVar.h);
            }
            if (bfVar.i != null ) {
                Encode.a(bf.u);
                Encode.a(bfVar.i.a());
            }
            if (bfVar.j != null) {
                Encode.a(bf.v);
                Encode.a(bfVar.j);
            }
            Encode.d();
            Encode.b();
        }

    }


    static class am {
        private static final Encode.dl c = new Encode.dl("ActivateMsg");
        private static final Encode.db d = new Encode.db("ts", (byte) 10, (short) 1);
        public long a=System.currentTimeMillis();

        public static void b( am amVar) {
            Encode.a();
            Encode.a(am.d);
            Encode.a(amVar.a);
            Encode.d();
            Encode.b();
        }

    }
    public enum au {
        LEGIT(1),
        ALIEN(2);

        private final int c;

        private au(int i) {
            this.c = i;
        }

        public int a() {
            return this.c;
        }

        public static au a(int i) {
            switch (i) {
                case 1:
                    return LEGIT;
                case 2:
                    return ALIEN;
                default:
                    return null;
            }
        }
    }
    static class at {
        private static final Encode.dl e = new Encode.dl("Error");
        private static final Encode.db f = new Encode.db("ts", (byte) 10, (short) 1);
        private static final Encode.db g = new Encode.db("context", (byte) 11, (short) 2);
        private static final Encode.db h = new Encode.db("source", (byte) 8, (short) 3);
        private static final int j = 0;
        public long a;
        public String b;
        public au c;

        public static void b(at atVar) {
            Encode.a();
            Encode.a(at.f);
            Encode.a(atVar.a);
            if (atVar.b != null) {
                Encode.a(at.g);
                Encode.a(atVar.b);
            }
            if (atVar.c != null ) {
                Encode.a(at.h);
                Encode.a(atVar.c.a());
            }
            Encode.d();
            Encode.b();
        }

    }
    public enum a_co implements co {
        STRING_VALUE((short) 1, "string_value"),
        LONG_VALUE((short) 2, "long_value");

        private static final Map<String, a_co> c = null;
        private final short d;
        private final String e;

        static {
            Map<String, a_co>  c = new HashMap();
            Iterator it = EnumSet.allOf(a_co.class).iterator();
            while (it.hasNext()) {
                a_co aVar = (a_co) it.next();
                c.put(aVar.b(), aVar);
            }
        }

        public static a_co a(int i) {
            switch (i) {
                case 1:
                    return STRING_VALUE;
                case 2:
                    return LONG_VALUE;
                default:
                    return null;
            }
        }

        public static a_co b(int i) {
            a_co a = a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static a_co a(String str) {
            return (a_co) c.get(str);
        }

        private a_co(short s, String str) {
            this.d = s;
            this.e = str;
        }

        public short a() {
            return this.d;
        }

        public String b() {
            return this.e;
        }
    }

    public interface co {
        short a();

        String b();
    }
    static class bh{
        private static final Encode.dl d = new Encode.dl("PropertyValue");
        private static final Encode.db e = new Encode.db("string_value", (byte) 11, (short) 1);
        private static final Encode.db f = new Encode.db("long_value", (byte) 10, (short) 2);
        protected Object b;
        protected a_co c;

        public a_co i() {
            return this.c;
        }

        public Object j() {
            return this.b;
        }

        protected Encode.db a(a_co aVar) {
            switch (aVar) {
                case STRING_VALUE:
                    return e;
                case LONG_VALUE:
                    return f;
                default:
                    throw new IllegalArgumentException("Unknown field id " + aVar);
            }
        }

        protected void c()  {
            switch ((a_co) this.c) {
                case STRING_VALUE:
                    Encode.a((String) this.b);
                    return;
                case LONG_VALUE:
                    Encode.a(((Long) this.b).longValue());
                    return;
                default:
                    throw new IllegalStateException("Cannot write union with unknown field " + this.c);
            }
        }


        public void b( bh crVar) {

            if (crVar.i() == null || crVar.j() == null) {
                try {
                    throw new Exception("haha");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            Encode.a();
            Encode.a(crVar.a(crVar.c));
            crVar.c();
            Encode.d();
            Encode.b();

        }

    }


    static class av {
        private static final Encode.dl g = new Encode.dl("Event");
        private static final Encode.db h = new Encode.db("name", (byte) 11, (short) 1);
        private static final Encode.db i = new Encode.db("properties",  (byte) 13, (short) 2);
        private static final Encode.db j = new Encode.db("duration", (byte) 10, (short) 3);
        private static final Encode.db k = new Encode.db("acc", (byte) 8, (short) 4);
        private static final Encode.db l = new Encode.db("ts", (byte) 10, (short) 5);
        private static final int n = 0;
        private static final int o = 1;
        private static final int p = 2;
        public String a;
        public Map<String, bh> b;
        public long c;
        public int d;
        public long e;
        private byte q;

        public boolean m() {
            return Encode.a(this.q, 0);
        }

        public boolean p() {
            return Encode.a(this.q, 1);
        }


        public static void b( av avVar) {
            Encode.a();
            if (avVar.a != null) {
                Encode.a(av.h);
                Encode.a(avVar.a);
            }
            if (avVar.b != null) {
                Encode.a(av.i);
                Encode.a(new Encode.dd((byte) 11, (byte) 12, avVar.b.size()));
                for (Map.Entry entry : avVar.b.entrySet()) {
                    Encode.a((String) entry.getKey());
                    ((bh) entry.getValue()).b(new bh());
                }
            }
            if (avVar.m()) {
                Encode.a(av.j);
                Encode.a(avVar.c);
            }
            if (avVar.p()) {
                Encode.a(av.k);
                Encode.a(avVar.d);
            }
            Encode.a(av.l);
            Encode.a(avVar.e);
            Encode.d();
            Encode.b();
        }

    }


    static class bc {
        private static final Encode.dl f = new Encode.dl("InstantMsg");
        private static final Encode.db g = new Encode.db("id", (byte) 11, (short) 1);
        private static final Encode.db h = new Encode.db("errors",(byte) 15, (short) 2);
        private static final Encode.db i = new Encode.db("events", (byte) 15, (short) 3);
        private static final Encode.db j = new Encode.db("game_events",(byte) 15, (short) 4);
        public String a;
        public List<at> b;
        public List<av> c;
        public List<av> d;

        public static void b(bc bcVar) {
            Encode.a();
            if (bcVar.a != null) {
                Encode.a(bc.g);
                Encode.a(bcVar.a);
            }
            if (bcVar.b != null ) {
                Encode.a(bc.h);
                Encode.a(new Encode.dc((byte) 12, bcVar.b.size()));
                for (at b : bcVar.b) {
                    at.b(b);
                }
            }
            if (bcVar.c != null ) {
                Encode.a(bc.i);
                Encode.a(new Encode.dc((byte) 12, bcVar.c.size()));
                for (av b2 : bcVar.c) {
                    av.b(b2);
                }
            }
            if (bcVar.d != null ) {
                Encode.a(bc.j);
                Encode.a(new Encode.dc((byte) 12, bcVar.d.size()));
                for (av b22 : bcVar.d) {
                    av.b(b22);
                }
            }
            Encode.d();
            Encode.b();
        }

    }


    static class bg {
        private static final Encode.dl d = new Encode.dl("Page");
        private static final Encode.db e = new Encode.db("page_name", (byte) 11, (short) 1);
        private static final Encode.db f = new Encode.db("duration", (byte) 10, (short) 2);


        private static final int h = 0;
        public String a;
        public long b;
        private byte i;

        public static void b( bg bgVar) {
            Encode.a();
            if (bgVar.a != null) {
                Encode.a(bg.e);
                Encode.a(bgVar.a);
            }
            Encode.a(bg.f);
            Encode.a(bgVar.b);
            Encode.d();
            Encode.b();
        }

    }

    static class be {
        private static final Encode.dl e = new Encode.dl("Location");
        private static final Encode.db f = new Encode.db("lat", (byte) 4, (short) 1);
        private static final Encode.db g = new Encode.db("lng", (byte) 4, (short) 2);
        private static final Encode.db h = new Encode.db("ts", (byte) 10, (short) 3);
        private static final int j = 0;
        private static final int k = 1;
        private static final int l = 2;
        public double a;
        public double b;
        public long c;
        private byte m;

        public static void b( be beVar) {
            Encode.a();
            Encode.a(be.f);
            Encode.a(beVar.a);
            Encode.a(be.g);
            Encode.a(beVar.b);
            Encode.a(be.h);
            Encode.a(beVar.c);
            Encode.d();
            Encode.b();
        }

    }

    /* compiled from: Traffic */
    public static class bm  {
        private static final Encode.dl d = new Encode.dl("Traffic");
        private static final Encode.db e = new Encode.db("upload_traffic", (byte) 8, (short) 1);
        private static final Encode.db f = new Encode.db("download_traffic", (byte) 8, (short) 2);

        private static final int h = 0;
        private static final int i = 1;
        public int a;
        public int b;
        private byte j;


        public static void b( bm bmVar)  {
            Encode.a();
            Encode.a(bm.e);
            Encode.a(bmVar.a);
            Encode.a(bm.f);
            Encode.a(bmVar.b);
            Encode.d();
            Encode.b();
        }

    }


        static class bl {
        private static final Encode.dl i = new Encode.dl("Session");
        private static final Encode.db j = new Encode.db("id", (byte) 11, (short) 1);
        private static final Encode.db k = new Encode.db("start_time", (byte) 10, (short) 2);
        private static final Encode.db l = new Encode.db("end_time", (byte) 10, (short) 3);
        private static final Encode.db m = new Encode.db("duration", (byte) 10, (short) 4);
        private static final Encode.db n = new Encode.db("pages",(byte) 15, (short) 5);
        private static final Encode.db o = new Encode.db("locations", (byte) 15, (short) 6);
        private static final Encode.db p = new Encode.db("traffic", (byte) 12, (short) 7);
        private static final int r = 0;
        private static final int s = 1;
        private static final int t = 2;
        public String a="8FE97E45DD8506131893648188375098";
        public long b=1514048103621L;
        public long c=0;
        public long d=0;
        public List<bg> e;
        public List<be> f;
        public bm g;
        private byte u;


        public static void b(bl blVar) {
            Encode.a();
            if (blVar.a != null) {
                Encode.a(bl.j);
                Encode.a(blVar.a);
            }
            Encode.a(bl.k);
            Encode.a(blVar.b);
            Encode.a(bl.l);
            Encode.a(blVar.c);
            Encode.a(bl.m);
            Encode.a(blVar.d);
            if (blVar.e != null) {
                Encode.a(bl.n);
                Encode.a(new Encode.dc((byte) 12, blVar.e.size()));
                for (bg b : blVar.e) {
                    bg.b(b);
                }
            }
            if (blVar.f != null ) {
                Encode.a(bl.o);
                Encode.a(new Encode.dc((byte) 12, blVar.f.size()));
                for (be b2 : blVar.f) {
                    be.b(b2);
                }
            }
            if (blVar.g != null ) {
                Encode.a(bl.p);
               /* blVar.g.b(dgVar);*/
               bm.b(new bm());

            }
            Encode.d();
            Encode.b();
        }

    }


    /* compiled from: ImprintValue */
    public static class bb {
        private static final Encode.dl e = new Encode.dl("ImprintValue");
        private static final Encode.db f = new Encode.db("value", (byte) 11, (short) 1);
        private static final Encode.db g = new Encode.db("ts", (byte) 10, (short) 2);
        private static final Encode.db h = new Encode.db("guid", (byte) 11, (short) 3);

        private static final int j = 0;
        public String a;
        public long b;
        public String c;
        private byte k;

        public bb(String a, long b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public void b(bb bbVar){
            Encode.a();
            if (bbVar.a != null) {
                Encode.a(bb.f);
                Encode.a(bbVar.a);
            }
            Encode.a(bb.g);
            Encode.a(bbVar.b);
            if (bbVar.c != null) {
                Encode.a(bb.h);
                Encode.a(bbVar.c);
            }
            Encode.d();
            Encode.b();
        }


    }

        public static class ba {
        private static final Encode.dl e = new Encode.dl("Imprint");
        private static final Encode.db f = new Encode.db("property",(byte) 13, (short) 1);
        private static final Encode.db g = new Encode.db("version", (byte) 8, (short) 2);
        private static final Encode.db h = new Encode.db("checksum", (byte) 11, (short) 3);


        private static final int j = 0;
        public static Map<String, bb> a=new ArrayMap<>();
        public int b=1;
        public String c="3928682c68e8befd2a067da04b377f9d";
        private byte k;


        public static void b(ba baVar) {

            a.put("pretime",new bb("1514050717724",1514050717724L, "6cea18b4f31e8578d5565e222faa22fc2ba30059"));
            a.put("install_datetime",new bb("2017-12-19 10:56:13",1514050717724L, "6cea18b4c115fe952a920e0394a0fe6fa5bdc8f4"));
            a.put("jitter",new bb("0",1514050717724L, "6cea18b44d830ff5e4f1009314026b6db2dd9b87"));
            a.put("umid",new bb("586319d34c8d913a30cbc1265cf87ad",1514050717724L, "6cea18b49ebaf70bdf70eb645dc170460bd4556f"));
            a.put("install_channel",new bb("yuzhuangceshi2",1514050717724L, "6cea18b4851da8f266377d3815e87d42353545a1"));
            a.put("v",new bb("5-5-5",1514050717724L, "6cea18b43f7887cbc3ccdad1846703fb22e8c904"));
            a.put("app_version",new bb("2.2.5",1514050717724L, "6cea18b43ec6083159c5325d555f15b61814d643"));
            a.put("install_app_version",new bb("2.2.5",1514050717724L, "6cea18b46cae85e9a2f451ac52355f85b888149a"));
            a.put("os_version",new bb("6.0",1514050717724L, "6cea18b4a35f2afb1a40fd5055e28b8b872c942b"));
            a.put("channel",new bb("1_360shoujizhushou",1514050717724L, "6cea18b40dae9556b1bbaf325798bac4b98311a7"));


            Encode.a();
            if (baVar.a != null) {
                Encode.a(ba.f);
                Encode.a(new Encode.dd((byte) 11, (byte) 12, baVar.a.size()));
                for (Map.Entry entry : baVar.a.entrySet()) {
                    String key = (String) entry.getKey();
                    Encode.a(key);
                    ((bb) entry.getValue()).b(baVar.a.get(key));
                }
            }
            Encode.a(ba.g);
            Encode.a(baVar.b);
            if (baVar.c != null) {
                Encode.a(ba.h);
                Encode.a(baVar.c);
            }
            Encode.d();
            Encode.b();
        }

    }


    public static class ax {//这个类也不需要设置初始值。后期设置几个
        private static final Encode.dl f = new Encode.dl("IdJournal");
        private static final Encode.db g = new Encode.db("domain", (byte) 11, (short) 1);
        private static final Encode.db h = new Encode.db("old_id", (byte) 11, (short) 2);
        private static final Encode.db i = new Encode.db("new_id", (byte) 11, (short) 3);
        private static final Encode.db j = new Encode.db("ts", (byte) 10, (short) 4);

        public ax(String a, String b, String c, long d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        private static final int l = 0;
        public String a;
        public String b;
        public String c;
        public long d;
        private byte m;


        public static void b( ax axVar) {
            Encode.a();
            if (axVar.a != null) {
                Encode.a(ax.g);
                Encode.a(axVar.a);
            }
            if (axVar.b != null ) {
                Encode.a(ax.h);
                Encode.a(axVar.b);
            }
            if (axVar.c != null) {
                Encode.a(ax.i);
                Encode.a(axVar.c);
            }
            Encode.a(ax.j);
            Encode.a(axVar.d);
            Encode.d();
            Encode.b();
        }

    }


    public static class ay {//这个类不需要预先赋值，其他类赋值就好了
        private static final Encode.dl e = new Encode.dl("IdSnapshot");
        private static final Encode.db f = new Encode.db("identity", (byte) 11, (short) 1);
        private static final Encode.db g = new Encode.db("ts", (byte) 10, (short) 2);
        private static final Encode.db h = new Encode.db("version", (byte) 8, (short) 3);


        public ay(String a, long b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        private static final int j = 0;
        private static final int k = 1;
        public String a;
        public long b;
        public int c;
        private byte l;

        public void b( ay ayVar) {
            Encode.a();
            if (ayVar.a != null) {
                Encode.a(ay.f);
                Encode.a(ayVar.a);
            }
            Encode.a(ay.g);
            Encode.a(ayVar.b);
            Encode.a(ay.h);
            Encode.a(ayVar.c);
            Encode.d();
            Encode.b();
        }


    }


        /* compiled from: IdTracking */
    public static class az {
        private static final Encode.dl e = new Encode.dl("IdTracking");
        private static final Encode.db f = new Encode.db("snapshots",(byte) 13, (short) 1);
        private static final Encode.db g = new Encode.db("journals", (byte) 15, (short) 2);
        private static final Encode.db h = new Encode.db("checksum", (byte) 11, (short) 3);


        public static Map<String, ay> a=new HashMap<>();
        public static List<ax> b;
        public String c;

            {
                a.put("utdid",new ay("WYIYStrgNn0DACENC11qDg5n",1514050717255L,1));
                a.put("serial",new ay("D8YDU15701065778",1514050717218L,1));
                a.put("imei",new ay("866946023235515",1514050717249L,1));
                a.put("idmd5",new ay("586319d34c8d913a30cbc1265cf87ad",1514050717240L,1));
                a.put("mac",new ay("50:a7:2b:6d:ce:3c",1514050717257L,1));
                a.put("android_id",new ay("ac990fca12f33a16",1514050717249L,1));


                b.add(new ax("serial","","D8YDU15701065778",1514050717218L));
                b.add(new ax("idmd5", "","586319d34c8d913a30cbc1265cf87ad", 1514050717240L));
                b.add(new ax("imei", "","866946023235515", 1514050717249L));
                b.add(new ax("android_id","", "ac990fca12f33a16", 1514050717249L));
                b.add(new ax("utdid", "","WYIYStrgNn0DACENC11qDg5n", 1514050717255L));
                b.add(new ax("mac","","50:a7:2b:6d:ce:3c", 1514050717257L));

            }


        public static void b(az azVar) {


            Encode.a();
            if (azVar.a != null) {
                Encode.a(az.f);
                Encode.a(new Encode.dd((byte) 11, (byte) 12, azVar.a.size()));
                for (Map.Entry entry : azVar.a.entrySet()) {
                    String key = (String) entry.getKey();
                    Encode.a(key);
                    ((ay) entry.getValue()).b((ay) entry.getValue());
                }
            }
            if (azVar.b != null) {
                Encode.a(az.g);
                Encode.a(new Encode.dc((byte) 12, azVar.b.size()));
                for (ax b : azVar.b) {
                    ax.b(b);
                }
            }
            if (azVar.c != null ) {
                Encode.a(az.h);
                Encode.a(azVar.c);
            }
            Encode.d();
            Encode.b();
        }

    }


    /* compiled from: ActiveUser */
    public static class an {
        private static final Encode.dl d = new Encode.dl("ActiveUser");
        private static final Encode.db e = new Encode.db("provider", (byte) 11, (short) 1);
        private static final Encode.db f = new Encode.db("puid", (byte) 11, (short) 2);

        public String a;
        public String b;


        public static void b( an anVar) {
            Encode.a();
            if (anVar.a != null) {
                Encode.a(an.e);
                Encode.a(anVar.a);
            }
            if (anVar.b != null) {
                Encode.a(an.f);
                Encode.a(anVar.b);
            }
            Encode.d();
            Encode.b();
        }

    }





    /* compiled from: Latent */
    public static class bd {
        private static final Encode.dl d = new Encode.dl("Latent");
        private static final Encode.db e = new Encode.db("latency", (byte) 8, (short) 1);
        private static final Encode.db f = new Encode.db("interval", (byte) 10, (short) 2);

        private static final int h = 0;
        private static final int i = 1;
        public int a;
        public long b;
        private byte j;


        public static void b( bd bdVar) {
            Encode.a();
            Encode.a(bd.e);
            Encode.a(bdVar.a);
            Encode.a(bd.f);
            Encode.a(bdVar.b);
            Encode.d();
            Encode.b();
        }

    }

    /* compiled from: ControlPolicy */
    public static class aq {
        private static final Encode.dl c = new Encode.dl("ControlPolicy");
        private static final Encode.db d = new Encode.db("latent", (byte) 12, (short) 1);


        public bd a;

        public static void b( aq aqVar) {
            Encode.a();
            if (aqVar.a != null ) {
                Encode.a(aq.d);
                bd.b(new bd());
            }
            Encode.d();
            Encode.b();
        }

    }

    static class bn{//这个已经new出来了


        public ap a=new ap();
        public ao b=new ao();
        public ar c=new ar();
        public bf d=new bf();
        public am e=new am();
        public List<bc> f;
        public List<bl> g;
        public ba h;
        public az i=new az();
        public an j;
        public aq k;



        private static final Encode.dl m = new Encode.dl("UALogEntry");
        private static final Encode.db n = new Encode.db("client_stats", (byte) 12, (short) 1);
        private static final Encode.db o = new Encode.db("app_info", (byte) 12, (short) 2);
        private static final Encode.db p = new Encode.db("device_info", (byte) 12, (short) 3);
        private static final Encode.db q = new Encode.db("misc_info", (byte) 12, (short) 4);
        private static final Encode.db r = new Encode.db("activate_msg", (byte) 12, (short) 5);
        private static final Encode.db s = new Encode.db("instant_msgs", (byte) 15, (short) 6);
        private static final Encode.db t = new Encode.db("sessions", (byte) 15, (short) 7);
        private static final Encode.db u = new Encode.db("imprint", (byte) 12, (short) 8);
        private static final Encode.db v = new Encode.db("id_tracking", (byte) 12, (short) 9);
        private static final Encode.db w = new Encode.db("active_user", (byte) 12, (short) 10);
        private static final Encode.db x = new Encode.db("control_policy", (byte) 12, (short) 11);


        public void b(bn bnVar) {
            Encode.a();
            if (bnVar.a != null) {
                Encode.a(n);
                ap.b(new ap());
            }
            if (bnVar.b != null) {
                Encode.a(o);
                ao.b(new ao());
            }
            if (bnVar.c != null) {
                Encode.a(p);
                ar.b(new ar());

            }
            if (bnVar.d != null) {
                Encode.a(q);
                bf.b(new bf());

            }
            if (bnVar.e != null ) {
                Encode.a(r);
                am.b(new am());

            }
            if (bnVar.f != null ) {
                Encode.a(s);
                Encode.a(new Encode.dc((byte) 12, bnVar.f.size()));
                for (bc b : bnVar.f) {
                    bc.b(b);
                }

            }
            if (bnVar.g != null ) {
                Encode.a(t);
                Encode.a(new Encode.dc((byte) 12, bnVar.g.size()));
                for (bl b2 : bnVar.g) {
                    bl.b(b2);
                }

            }
            if (bnVar.h != null ) {
                Encode.a(u);
                ba.b(new ba());

            }
            if (bnVar.i != null) {
                Encode.a(v);
                az.b(new az());

            }
            if (bnVar.j != null ) {
                Encode.a(w);
                an.b(new an());

            }
            if (bnVar.k != null) {
                Encode.a(x);
                aq.b(new aq());

            }
            Encode.d();
            Encode.b();
        }
    }





}
