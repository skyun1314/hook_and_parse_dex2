package com.example.hookdeviceinfo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("zkjg-lib");
    }

    public  native void haha();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.b1);
        final TextView t1 = findViewById(R.id.t1);
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                TelephonyManager telepManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    Method TelephonyManager_private_getSubscriberInfo = telepManager.getClass().getDeclaredMethod("getSubscriberInfo");
                    TelephonyManager_private_getSubscriberInfo.setAccessible(true);
                    String mac1 = getMac();
                    Object iphonesubinfo = TelephonyManager_private_getSubscriberInfo.invoke(telepManager);
                    Method getDeviceId = iphonesubinfo.getClass().getDeclaredMethod("getDeviceId");
                    getDeviceId.setAccessible(true);

                    Object deviceid = getDeviceId.invoke(iphonesubinfo);
                @SuppressLint("MissingPermission") String imei = telepManager.getDeviceId();

                String andId = Settings.Secure.getString(getContentResolver(), "android_id");

                @SuppressLint("WrongConstant") WifiManager localWifiManager = (WifiManager) getSystemService("wifi");
                String Wifymac = localWifiManager.getConnectionInfo().getMacAddress();

                    String serial1 = Build.SERIAL;

                    NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
                    byte[] mac = networkInterface.getHardwareAddress();

                    t1.setText("android_id:" + andId +
                            "\nimei:" + deviceid +
                            "\nimei:" + imei +
                            "\nMac:" + Wifymac+
                            "\nmac1:" + mac1+
                            "\nserial1:" + serial1+"");


                    haha();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /**
     * 获取手机的MAC地址
     * @return
     */
    public static String getMac(){
        String str="";
        String macSerial="";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");


            BufferedReader br=new BufferedReader(new InputStreamReader(pp.getInputStream()));
            String line=null;
            while ((line=br.readLine())!=null){
                return line.trim();
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return macSerial;
    }
    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }
    public static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }


}
