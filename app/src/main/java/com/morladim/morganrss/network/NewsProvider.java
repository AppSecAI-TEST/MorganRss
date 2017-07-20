package com.morladim.morganrss.network;

import com.morladim.morganrss.database.ChannelManager;
import com.morladim.morganrss.database.ItemManager;
import com.morladim.morganrss.database.RssVersionManager;
import com.morladim.morganrss.database.entity.Item;
import com.morladim.morganrss.rss2.Rss2Xml;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <br>创建时间：2017/7/20.
 *
 * @author morladim
 */
public class NewsProvider {

    public static void getXml(String url, Consumer<List<Item>> onNext, Consumer<Throwable> onError, final int offset, final int limit) {
        RssHttpClient.getNewsApi().getXml(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<Rss2Xml, List<Item>>() {
                    @Override
                    public List<Item> apply(@NonNull Rss2Xml rss2Xml) throws Exception {
                        String version = rss2Xml.version;
                        if (version != null) {
                            long versionId = RssVersionManager.getInstance().insertOrUpdate(version);
                            long channelId = ChannelManager.getInstance().insertOrUpdate(rss2Xml.channel, versionId);
                            return ItemManager.getInstance().getList(channelId, offset, limit);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
//                .subscribeWith(observable);
    }
}
