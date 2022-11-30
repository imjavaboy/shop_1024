package com.gbq.config;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/28 17:50
 * @Copyright 总有一天，会见到成功
 */

public class Test {
    public static void main(String[] args) {
        String oldDateStr = "2022-11-15T11:15:39.993+08:00";
        Date date1 = null;
        DateFormat df2 = null;
        String res = "";
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            res = df2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(res);

    }
}
