package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(name = "rss", strict = false)
public class Rss2Xml {
    @Element(name = "channel", type = Rss2Channel.class)
    public Rss2Channel channel;

    @Attribute(name = "version")
    public String version;

}


