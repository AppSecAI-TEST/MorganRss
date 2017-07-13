package com.morladim.morganrss.main;

/**
 * 描述rss源
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
public class RssSource {

    private String name;

    private String url;

    public RssSource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
