package com.morladim.morganrss.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.morladim.morganrss.base.RssApplication;
import com.morladim.morganrss.database.dao.DaoMaster;
import com.morladim.morganrss.database.dao.DaoSession;

/**
 * Created on 2017/7/15 下午3:43 <p>
 * by morladim.
 */

public class DBManager {
    private final static String dbName = "test_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

//    /**
//     * 获取单例引用
//     *
//     * @param context
//     * @return
//     */
//    public static DBManager getInstance(Context context) {
//        if (mInstance == null) {
//            synchronized (DBManager.class) {
//                if (mInstance == null) {
//                    mInstance = new DBManager(context);
//                }
//            }
//        }
//        return mInstance;
//    }


    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(RssApplication.getContext());
                }
            }
        }
        return mInstance;
    }

//    private static void setDBManager() {
//        if (mInstance == null) {
//            mInstance = getInstance(RssApplication.getContext());
//        }
//    }


    /**
     * 获取可写数据库
     */
    SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        return openHelper.getWritableDatabase();
    }

    /**
     * 获取可读数据库
     */
    public SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        return openHelper.getReadableDatabase();
    }


    public static DaoSession getDaoSession() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance().getWritableDatabase());
        return daoMaster.newSession();
    }
}
