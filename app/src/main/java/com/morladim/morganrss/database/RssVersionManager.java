package com.morladim.morganrss.database;

import com.morladim.morganrss.database.dao.RssVersionDao;
import com.morladim.morganrss.database.entity.RssVersion;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * RssVersion表管理类，提供数据访问方法封装
 * Created on 2017/7/16 上午7:32 <p>
 * by morladim.
 */

@SuppressWarnings("WeakerAccess")
public class RssVersionManager {

    public static long insert(RssVersion version) {
        return getVersionDao().insert(version);
    }

    private static RssVersionDao getVersionDao() {
        return DBManager.getDaoSession().getRssVersionDao();
    }

    public static void deleteByKey(@NotNull Long id) {
        getVersionDao().deleteByKey(id);
    }

    public static void update(RssVersion version) {
        getVersionDao().update(version);
    }

    public static RssVersion getVersionByName(@NotNull String name) {
        return getVersionDao().queryBuilder().where(RssVersionDao.Properties.Name.eq(name.trim())).unique();
    }

    /**
     * 根据版本名向表中添加数据，如果已经存在同名版本，则times加1。
     *
     * @param versionName rss版本名
     * @return 插入行号
     */
    public static long insertOrUpdate(@NotNull String versionName) {
        RssVersion versionInDB = getVersionByName(versionName);
        if (versionInDB == null) {
            return insert(new RssVersion(versionName));
        } else {
            versionInDB.setUpdateAt(new Date());
            versionInDB.setTimes(versionInDB.getTimes() + 1);
            update(versionInDB);
            return versionInDB.getId();
        }
    }
}
