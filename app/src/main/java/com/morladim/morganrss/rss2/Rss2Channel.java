package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(name = "channel", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
@Convert(ChannelConverter.class)
public class Rss2Channel {

    /**
     * 频道标题
     */
    @Element(name = "title", required = false)
    public String title;


    //    @Namespace(prefix = "")
//    @Element(name = "link", required = false)
//    @Path("link")
//    @Text(required = false)
//    public String link;

//    @Namespace(prefix = "atom")
//    @Element(name = "link", required = false)
//    @Attribute(name = "href", required = false)
//    public String linkxx;

    @Element(name = "language", required = false)
    public String language;

    @Element(name = "image", required = false, type = ChannelImage.class)
    public ChannelImage image;

    @Element(required = false)
    public String link;

//    @Path("link")
    @Namespace(reference = "http://www.w3.org/2005/Atom")
    @Element(name = "link",required = false, type = AtomLink.class)
    public AtomLink atomLink;
}
