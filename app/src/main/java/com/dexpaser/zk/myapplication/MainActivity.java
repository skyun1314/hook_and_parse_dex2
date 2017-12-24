package com.dexpaser.zk.myapplication;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dexpaser.zk.myapplication.Util.ApkInfo;
import com.dexpaser.zk.myapplication.Util.ApkUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean mHasRoot = true;
    private AppListAdapter mListAdapter;
    private List<AppListAdapter.ListItem> mListData;
    private ListView mListView;
    TextView text;

    {
        // System.loadLibrary("zkjg-lib");
    }


    public static native void aaattachBaseContext(int base, String my_packageName);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.mHasRoot = isRoot();
        init();





        // hookRes.replaceClassLoader(getPackageName(),getClassLoader());

    }

    private void init() {
        CharSequence charSequence;
        if (this.mListData == null) {
            this.mListData = new ArrayList();
        }
        this.text = (TextView) findViewById(R.id.text);
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo pak = (PackageInfo) packages.get(i);
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                AppListAdapter.ListItem item = new AppListAdapter.ListItem();
                PackageInfo packageInfo = (PackageInfo) packages.get(i);
                item.icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                item.name = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                item.pkg = packageInfo.packageName;
                item.path = packageInfo.applicationInfo.publicSourceDir;
                this.mListData.add(item);
            }


        }
        TextView textView = this.text;
        if (this.mHasRoot) {
            charSequence = "";
        } else {
            charSequence = "";
        }
        // textView.setText(charSequence);
        initWidgets();
    }

    private boolean isRoot() {
        if (new File("/system/bin/su").exists() || new File("/system/xbin/su").exists()) {
            return true;
        }
        return false;
    }

    private void initWidgets() {
        this.mListView = (ListView) findViewById(R.id.list);
        this.mListAdapter = new AppListAdapter(this, this.mListData);
        this.mListView.setAdapter(this.mListAdapter);

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                view.setBackgroundColor(Color.parseColor("#F5F5DC"));
                String apkpath = mListData.get(position).path;

                ApkInfo apkInfo = ApkUtils.getApkInfo(apkpath);


                String my_packageName = apkInfo.getPackageName();
                String protect_Application = apkInfo.getApplication();
                String Main_Activity = apkInfo.getActivityName();

                writeFileData(my_packageName + "::" + protect_Application + "::" + Main_Activity);

                String s = readFile();


            }
        });
    }


    public void writeFileData(String message) {

        try {

            File file = new File("/sdcard/tuoke.txt");
            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = message.getBytes();

            fout.write(bytes);

            fout.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static String readFile() {
        StringBuffer sb = null;
        try {
            File file = new File("/sdcard/tuoke.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

}
