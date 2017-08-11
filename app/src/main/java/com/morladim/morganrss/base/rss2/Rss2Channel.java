package com.morladim.morganrss.base.rss2;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * channel标签
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@SuppressWarnings("unused")
@Root(name = "channel", strict = false)
@Default
public class Rss2Channel {

    /**
     * 频道标题
     */
    @Element(name = "title", required = false)
    public String title;

//    /**
//     * 语言
//     */
//    @Element(name = "language", required = false)
//    public String language;

    /**
     * 频道图片
     */
    @Element(name = "image", required = false, type = ChannelImage.class)
    public ChannelImage image;

    /**
     * 解析link和atom:link
     */
    @ElementList(inline = true, entry = "link", type = Link.class, required = false)
    public List<Link> linkList;


    @ElementList(inline = true, entry = "item", type = Rss2Item.class, required = false)
    public List<Rss2Item> itemList;

    @Element(required = false)
    public String description;

    @Element(required = false)
    public String lastBuildDate;

    @Element(required = false)
    public String updatePeriod;

    @Element(required = false)
    public String updateFrequency;

}
