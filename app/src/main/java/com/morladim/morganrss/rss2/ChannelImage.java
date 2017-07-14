package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * channel中的image标签
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(strict = false, name = "image")
public class ChannelImage {

    @Element(name = "link", required = false)
    public String link;
}
