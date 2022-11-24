package com.qa.tools;

import java.util.Random;

/**
 * @author Tesla Liu
 * @date 2022/11/24 16:41
 * 描述 随机时间工具类
 */
public class TimeUtils {

    public static Integer randTime(){
        Random rndYear = new Random();
        int year = rndYear.nextInt(18)+2000;
        Random rndMonth = new Random();
        int month = rndMonth.nextInt(12)+1;
        Random rndDay = new Random();
        int day = rndDay.nextInt(30)+1;
        Random rndHour = new Random();
        int hour = rndHour.nextInt(23);
        Random rndMinute = new Random();
        int minute = rndMinute.nextInt(60);
        Random rndSecond = new Random();
        int second = rndSecond.nextInt(60);
        return year + month + day + hour + minute + second;
    }
}