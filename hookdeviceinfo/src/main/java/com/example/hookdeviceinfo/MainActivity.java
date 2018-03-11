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

    public native void haha();

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

                   /* Object getSubscriberInfo = fanshe.invokeDeclaredMethod(TelephonyManager.class.getName(), "getSubscriberInfo", null, null, telepManager);
                    Object imei1 = fanshe.invokeDeclaredMethod(getSubscriberInfo.getClass().getName(), "getDeviceId", null, null, getSubscriberInfo);
                    Object imei3 = fanshe.invokeDeclaredMethod("com.android.internal.telephony.IPhoneSubInfo$Stub$Proxy", "getDeviceId", null, null, getSubscriberInfo);
                    @SuppressLint("MissingPermission") String imei2 = telepManager.getDeviceId();
*/
                    @SuppressLint("WrongConstant") WifiManager localWifiManager = (WifiManager) getSystemService("wifi");
                    NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
                    String mac1 = getMac();
                    String mac2 = localWifiManager.getConnectionInfo().getMacAddress();
                    String name = networkInterface.getClass().getName();
                    byte[] mac3 = networkInterface.getHardwareAddress();
                    //  String mac4 = Settings.Secure.getString(getContentResolver(), "bluetooth_address");

 /*
                    String andId = Settings.Secure.getString(getContentResolver(), "android_id");




                    String serial1 = Build.SERIAL;


                   */

                    t1.setText(
                            /*"\nimei2:" + imei2 +
                            "\nimei1:" + imei1 +
                            "\nimei3:" + imei3 +*/
                            "\nMac1:" + mac1 +
                                    "\nmac2:" + mac2 +
                                    "\nmac3:" + HTool.parseByte2HexStr(mac3)
                                   // "\nmac4:" + mac4
                            /*        +
                            "\nserial1:" + serial1 + "\nandroid_id:" + andId */
                    );


                     haha();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /**
     * 获取手机的MAC地址
     *
     * @return
     */
    public static String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");

            BufferedReader br = new BufferedReader(new InputStreamReader(pp.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
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
