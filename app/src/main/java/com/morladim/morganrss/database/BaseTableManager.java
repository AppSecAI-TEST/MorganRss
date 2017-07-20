package com.morladim.morganrss.database;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * 表管理工具基类
 * <br>创建时间：2017/7/18.
 *
 * @author morladim
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseTableManager<E, DAO extends AbstractDao<E, Long>> {

    protected abstract DAO getDao();

    public long insert(E entity) {
        return getDao().insert(entity);
    }

    public void deleteByKey(@NotNull Long id) {
        getDao().deleteByKey(id);
    }

    public void update(E entity) {
        getDao().update(entity);
    }

    public List<E> getAll() {
        return getDao().loadAll();
    }

    public void insertInTx(List<E> entityList) {
        getDao().insertInTx(entityList);
    }
}
