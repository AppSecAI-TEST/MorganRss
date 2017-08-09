package com.morladim.morganrss.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <br>Created on 2017/7/16 下午12:29
 *
 * @author morladim.
 */

public class DBUtils {

    //Sun, 16 Jul 2017 01:12:26 +0000
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
    //Tue, 08 Aug 2017 08:21:28
    private static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
    //2017-08-07 15:05:35 +0800
    private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);

    public static Date convertStringToDate(String dateString) {
        Date date = null;
        try {
            date = DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            try {
                date = DATE_FORMAT1.parse(dateString);
            } catch (ParseException e1) {
                try {
                    date = DATE_FORMAT2.parse(dateString.replaceAll("\n", ""));
                } catch (ParseException e2) {
                    e.printStackTrace();
                    e1.printStackTrace();
                    e2.printStackTrace();
                }
            }
        }
        return date;
    }
}
