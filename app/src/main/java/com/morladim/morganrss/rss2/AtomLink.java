package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
@Root(name = "link",strict = false)
public class AtomLink {

    @Attribute(name = "href", required = false)
    public String href;

    @Attribute(name = "rel", required = false)
    public String rel;

    @Attribute(name = "type", required = false)
    public String type;
}
