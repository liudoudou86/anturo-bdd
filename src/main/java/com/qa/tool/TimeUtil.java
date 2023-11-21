package com.qa.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author Tesla Liu
 * @date 2022/11/24 16:41
 * 描述 时间工具类
 */
public class TimeUtil {

    /**
     * 月日时分秒补0
     * */
    private static String cp(String num){
        String number = num + "";
        if (number.length() == 1){
            return "0" + number;
        }else {
            return number;
        }
    }

    /**
     * 获取随机时间戳
     * 格式为：yyyyMMddHHmmss
     * */
    public static String randomTimeStamp(){
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
        return year + cp(month) + cp(day) + cp(hour) + cp(minute) + cp(second);
    }

    /**
     * 获取随机日期
     * 格式为：yyyy-MM-dd
     * */
    public static String randomDate(){
        Random rndYear = new Random();
        String year = String.valueOf(rndYear.nextInt(18)+2000);
        Random rndMonth = new Random();
        String month = String.valueOf(rndMonth.nextInt(12)+1);
        Random rndDay = new Random();
        String day = String.valueOf(rndDay.nextInt(30)+1);
        return year + "-" + cp(month) + "-" + cp(day);
    }

    /**
     * 获取随机时间
     * 格式为：yyyy-MM-dd HH:mm:ss
     * */
    public static String randomTime(){
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
        return year + "-" + cp(month) + "-" + cp(day) + "  " + cp(hour) + ":" + cp(minute) + ":" + cp(second);
    }

    /**
     * 获取当前时间
     * 格式为：yyyy-MM-dd
     * */
    public static String currentTimeStyle01() {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(date);
    }

    /**
     * 获取当前时间
     * 格式为：yyyyMMdd
     * */
    public static String currentTimeStyle02() {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        return formater.format(date);
    }

    /**
     * 获取当前时间
     * 格式为：yyyyMMddHHmm
     * */
    public static String currentTimeStamp() {
        String date = String.valueOf(System.currentTimeMillis());
        return date;
    }

    /**
     * 获取上月的任意时间
     * 格式为：yyyy-MM-dd
     * */
    public static String lastMonthRandomDate() {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        date = calendar.getTime();
        return formater.format(date);
    }

}