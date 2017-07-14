package com.morladim.morganrss;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
@Root(name = "route", strict = false)
public class NewsXml {
    @Attribute
    public String tag;

    @Attribute
    public String title;
}
