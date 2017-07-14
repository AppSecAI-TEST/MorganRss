package com.morladim.morganrss;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
@Root(name = "body", strict = false)
public class NewsDataXml {

    @Attribute
    public String copyright; //属性

    @ElementList(required = true, inline = true, entry = "route") //标志是集合
    public List<NewsXml> newsXmls = new ArrayList<>();
}
