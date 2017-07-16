package com.morladim.morganrss.database;

import android.text.TextUtils;

import com.morladim.morganrss.database.dao.ChannelDao;
import com.morladim.morganrss.database.entity.Channel;
import com.morladim.morganrss.rss2.Link;
import com.morladim.morganrss.rss2.Rss2Channel;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import java.util.List;

/**
 * Created on 2017/7/15 下午4:10 <p>
 * by morladim.
 */

@SuppressWarnings("WeakerAccess")
public class ChannelManager {

    public static long insert(Channel channel) {
        return getChannelDao().insert(channel);
    }

    private static ChannelDao getChannelDao() {
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance().getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        return DBManager.getDaoSession().getChannelDao();
    }

    private static void deleteByKey(@NotNull Long id) {
        getChannelDao().deleteByKey(id);
    }

    public static void update(Channel channel) {
        getChannelDao().update(channel);
    }

    public static List<Channel> getAll(){
        return getChannelDao().loadAll();
    }

    public static long insertOrUpdate(@NotNull Rss2Channel rss2Channel, long versionId) {
        Channel channelInDB = getChannelByTitleAndLink(rss2Channel.title, getChannelLink(rss2Channel));
        if (channelInDB == null) {
            return insert(convertXmlToEntity(rss2Channel, versionId));
        } else {
            channelInDB.setUpdateAt(new Date());
            channelInDB.setTimes(channelInDB.getTimes() + 1);
            channelInDB.setLastBuildDate(rss2Channel.lastBuildDate);
            update(channelInDB);
            return channelInDB.getId();
        }
    }

    public static Channel getChannelByTitleAndLink(String title, String link) {
        return getChannelDao().queryBuilder().where(ChannelDao.Properties.Title.eq(title), ChannelDao.Properties.Link.eq(link)).unique();
    }

    private static String getChannelLink(@NotNull Rss2Channel rss2Channel) {
        if (rss2Channel.linkList == null) {
            return null;
        }
        for (Link link : rss2Channel.linkList) {
            if (link.value != null) {
                return link.value;
            }
        }
        return null;
    }

    public static Channel convertXmlToEntity(@NotNull Rss2Channel rss2Channel, long versionId) {
        if (TextUtils.isEmpty(rss2Channel.title) || rss2Channel.linkList == null) {
            return null;
        }

        String linkUrl = null;
        String atomLinkUrl = null;
        for (Link link : rss2Channel.linkList) {
            if (link.value != null) {
                linkUrl = link.value;
            }
            if (link.href != null) {
                atomLinkUrl = link.href;
            }
        }

        Channel channel = new Channel(rss2Channel.title);
        channel.setLink(linkUrl);
        channel.setAtomLink(atomLinkUrl);
        channel.setDescription(rss2Channel.description);
        channel.setImageUrl(rss2Channel.image == null ? null : rss2Channel.image.url);
        channel.setImageLink(rss2Channel.image == null ? null : rss2Channel.image.link);
        channel.setLastBuildDate(rss2Channel.lastBuildDate);
        channel.setRssVersionId(versionId);
        return channel;
    }
}
