package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(name = "rss", strict = false)
@NamespaceList({
        @Namespace(reference = "http://purl.org/rss/1.0/modules/content/"),
        @Namespace(reference = "http://wellformedweb.org/CommentAPI/"),
        @Namespace(reference = "http://purl.org/dc/elements/1.1/"),
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
public class Rss2Xml {
    @Element(type = Rss2Channel.class)
    public Rss2Channel channel;

    @Attribute
    public String version;

}


