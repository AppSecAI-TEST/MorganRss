package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * link标签
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
@Root(name = "link",strict = false)
public class Link {

    @Attribute(name = "href", required = false)
    public String href;

    @Attribute(name = "rel", required = false)
    public String rel;

    @Attribute(name = "type", required = false)
    public String type;

    /**
     * 上面三个解析的是Atom命名空间下的link。
     */
    @Text(required = false)
    public String value;
}
