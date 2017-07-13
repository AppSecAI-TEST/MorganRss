package com.morladim.morganrss.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
public class RssFeed {
    private String title = null;
    private String pubdate = null;
    private int itemcount = 0;//用于计算列表数目
    private List<RssItem> itemlist;

    public RssFeed() {
        itemlist = new ArrayList<>(0);
    }

    public int addItem(RssItem item) {
        itemlist.add(item);
        itemcount++;
        return itemcount;
    }

    public RssItem getItem(int location) {
        return itemlist.get(location);
    }

    public List getAllItems() {
        return itemlist;
    }

    public List getAllItemsForListView() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        int size = itemlist.size();
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put(RssItem.TITLE, itemlist.get(i).getTitle());
            item.put(RssItem.PUBDATE, itemlist.get(i).getPubDate());
            data.add(item);
        }
        return data;
    }

    int getItemCount() {
        return itemcount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubDate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubdate;
    }
}
