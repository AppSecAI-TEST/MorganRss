package com.morladim.morganrss.database;

import com.morladim.morganrss.database.dao.ItemDao;
import com.morladim.morganrss.database.entity.Item;
import com.morladim.morganrss.rss2.Rss2Item;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import java.util.List;

/**
 * item表管理类
 * <br>创建时间：2017/7/17.
 *
 * @author morladim
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ItemManager extends BaseTableManager<Item, ItemDao> {

    private volatile static ItemManager instance;

    public static ItemManager getInstance() {
        if (instance == null) {
            synchronized (ItemManager.class) {
                if (instance == null) {
                    instance = new ItemManager();
                }
            }
        }
        return instance;
    }

    @Override
    protected ItemDao getDao() {
        return DBManager.getDaoSession().getItemDao();
    }

    public void insertOrUpdateList(List<Rss2Item> rss2ItemList, Long channelId) {
        if (channelId == null || rss2ItemList == null || rss2ItemList.size() == 0) {
            return;
        }
        for (Rss2Item item : rss2ItemList) {
            insertOrUpdate(item, channelId);
        }
    }

    public void insertOrUpdate(Rss2Item rss2Item, long channelId) {
        Item itemInDB = getDao().queryBuilder().where(ItemDao.Properties.ChannelId.eq(channelId), ItemDao.Properties.Link.eq(rss2Item.link), ItemDao.Properties.Title.eq(rss2Item.title)).unique();
        long itemId;
        if (itemInDB == null) {
            itemId = insert(convertXmlToEntity(rss2Item, channelId));
        } else {
            updateEntityFromXml(rss2Item, itemInDB);
            itemId = itemInDB.getId();
        }
        CategoryManager.getInstance().insertOrUpdateList(rss2Item.categoryList, itemId);
    }

    public Item convertXmlToEntity(@NotNull Rss2Item rss2Item, long channelId) {
        if (rss2Item.title == null) {
            return null;
        }

        Item item = new Item();
        item.setChannelId(channelId);
        item.setCommentRss(rss2Item.commentRss);
        item.setComments(getCommonsFromXml(rss2Item.commentList));
        item.setContent(rss2Item.encoded);
        Date date = new Date();
        item.setCreateAt(date);
        item.setUpdateAt(date);
        item.setCreator(rss2Item.creator);
        item.setDescription(rss2Item.description);
        item.setGuid(rss2Item.guid);
        item.setLink(rss2Item.link);
        item.setPubDate(rss2Item.pubDate);
        item.setTitle(rss2Item.title);
        return item;
    }

    /**
     * 获取commonList中最长的为commons
     */
    private String getCommonsFromXml(List<String> commonList) {
        int longestI = 0;
        int length = 0;
        int temPLength;
        for (int i = 0; i < commonList.size(); i++) {
            temPLength = commonList.get(i).length();
            if (temPLength > length) {
                longestI = i;
            }
        }
        return commonList.get(longestI);
    }

    private void updateEntityFromXml(Rss2Item rss2Item, Item item) {
        if (item == null) {
            return;
        }
        item.setPubDate(rss2Item.pubDate);
        item.setDescription(rss2Item.description);
        item.setComments(rss2Item.encoded);
        item.setUpdateAt(new Date());
        item.resetCategoryList();
    }

    public List<Item> getList(int offset, int limit) {
        return getDao().queryBuilder().offset(offset).limit(limit).orderDesc(ItemDao.Properties.PubDate).list();
    }

    public List<Item> getList(long channelId, int offset, int limit) {
        return getDao().queryBuilder().where(ItemDao.Properties.ChannelId.eq(channelId)).offset(offset).limit(limit).orderDesc(ItemDao.Properties.PubDate).list();
    }
}

