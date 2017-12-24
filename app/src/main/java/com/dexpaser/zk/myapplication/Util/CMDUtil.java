package com.dexpaser.zk.myapplication.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zk on 2017/7/1.
 */

public class CMDUtil {




    public static void runCMD(String cmd){
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line=null;
            while ((line=br.readLine())!=null){
                System.out.println(line);
            }

            if (br==null){
                br.close();;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
