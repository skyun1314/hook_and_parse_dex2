package com.dexpaser.zk.myapplication.Util;

/**
 * Created by zk on 2017/8/25.
 */

public class ApkInfo {

    String apkName;
    String packageName;
    String application;
    String activityName;
    boolean bInit;

    public boolean isbInit()
    {
        return this.bInit;
    }

    public void setbInit(boolean bInit)
    {
        this.bInit = bInit;
    }

    public String getApkName()
    {
        return this.apkName;
    }

    public void setApkName(String apkName)
    {
        this.apkName = apkName;
    }

    public String getPackageName()
    {
        return this.packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getActivityName()
    {
        return this.activityName;
    }

    public void setActivityName(String activityName)
    {
        if(activityName==null){
            return;
        }

        int length = activityName.split("\\.").length;

        if(activityName.startsWith(".")){
            this.activityName=packageName+activityName;
        } else  if(length==0){
            this.activityName=packageName+"."+activityName;
        } else{
            this.activityName = activityName;
        }

    }

    public String getApplication()
    {
        return this.application;
    }

    public void setApplication(String application)
    {
        if (application==null){
            return;
        }
        int length = application.split("\\.").length;

        if(application.startsWith(".")){
            this.application=packageName+application;
        }
        else if(length==0){
            this.application=packageName+"."+application;
        } else{
            this.application = application;
        }
    }


    @Override
    public String toString() {
        return "ApkInfo{" +
                "apkName='" + apkName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", application='" + application + '\'' +
                ", activityName='" + activityName + '\'' +
                ", bInit=" + bInit +
                '}';
    }
}
