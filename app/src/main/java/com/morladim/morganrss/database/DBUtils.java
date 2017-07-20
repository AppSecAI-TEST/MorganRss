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

    public static Date convertStringToDate(String dateString) {
        Date date = null;
        try {
            date = DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
