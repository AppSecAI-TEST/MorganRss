package com.morladim.morganrss.base.database;

import com.morladim.morganrss.base.RssApplication;
import com.morladim.morganrss.base.database.dao.DaoMaster;
import com.morladim.morganrss.base.database.dao.DaoSession;

/**
 * 单例获得DaoSession
 * <br>Created on 2017/7/15 下午3:43
 *
 * @author morladim.
 */

class DBManager {

    ///////////////////////////////////////////////////////////////////////////
    // 调试时报错，需要关闭Instant Run。
    ///////////////////////////////////////////////////////////////////////////

    private final static String dbName = "test_db";
    private volatile static DBManager instance;
    private DaoSession daoSession;

    private DBManager(RssApplication context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    private static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager(RssApplication.getContext());
                }
            }
        }
        return instance;
    }

    static DaoSession getDaoSession() {
        return getInstance().daoSession;
    }
}
