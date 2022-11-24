package com.qa.tools;

import java.util.Random;

/**
 * @author Tesla Liu
 * @date 2022/11/24 16:41
 * 描述 随机时间工具类
 */
public class TimeUtils {

    public static String randomTimeStyle01(){
        Random rndYear = new Random();
        String year = String.valueOf(rndYear.nextInt(18)+2000);
        Random rndMonth = new Random();
        String month = String.valueOf(rndMonth.nextInt(12)+1);
        Random rndDay = new Random();
        String day = String.valueOf(rndDay.nextInt(30)+1);
        Random rndHour = new Random();
        String hour = String.valueOf(rndHour.nextInt(23));
        Random rndMinute = new Random();
        String minute = String.valueOf(rndMinute.nextInt(60));
        Random rndSecond = new Random();
        String second = String.valueOf(rndSecond.nextInt(60));
        return year + month + day + hour + minute + second;
    }

    public static String randomTimeStyle02(){
        Random rndYear = new Random();
        String year = String.valueOf(rndYear.nextInt(18)+2000);
        Random rndMonth = new Random();
        String month = String.valueOf(rndMonth.nextInt(12)+1);
        Random rndDay = new Random();
        String day = String.valueOf(rndDay.nextInt(30)+1);
        Random rndHour = new Random();
        String hour = String.valueOf(rndHour.nextInt(23));
        Random rndMinute = new Random();
        String minute = String.valueOf(rndMinute.nextInt(60));
        Random rndSecond = new Random();
        String second = String.valueOf(rndSecond.nextInt(60));
        return year + "-" + month + "-" + day + "  " + hour + ":" + minute + ":" + second;
    }

}