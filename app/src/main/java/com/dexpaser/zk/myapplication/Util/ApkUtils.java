package com.dexpaser.zk.myapplication.Util;

/**
 * Created by zk on 2017/8/25.
 */


public class ApkUtils {
    public static ApkInfo getApkInfo(String path) {
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.setApkName(path);
        String xml = AXMLPrinter.getManifestXMLFromAPK(path);
        System.out.println(xml);
        String packName = Dom4jXMLUtils.getPackageName(xml);

        apkInfo.setPackageName(packName);
        String applicationName = Dom4jXMLUtils.getApplicationName(xml);

        apkInfo.setApplication(applicationName);
        String activity = Dom4jXMLUtils.getMainActivty(xml);

        apkInfo.setActivityName(activity);
        return apkInfo;
    }
}
