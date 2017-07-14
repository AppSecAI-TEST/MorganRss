package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(name = "channel", strict = false)
public class Rss2Channel {

    /**
     * 频道标题
     */
    @Element(name = "title",required = false)
    public String title;



}
