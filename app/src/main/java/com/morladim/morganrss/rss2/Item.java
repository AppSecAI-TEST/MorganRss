package com.morladim.morganrss.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by morladim on 2017/7/15.
 */
@Root(strict = false)
public class Item {
    @Element(required = false)
    public String title;

    @Element(required = false)
    public String link;

    @ElementList(entry = "comments",required = false, inline = true, type = String.class)
    public List<String> comments;

    @Element(required = false)
    public String pubDate;

    @Namespace(reference = "http://purl.org/dc/elements/1.1/")
    @Element(required = false)
    public String creator;

    @ElementList(entry = "category", type = String.class, inline = true, required = false)
    public List<String> categoryList;

    @Element(required = false)
    public String guid;

    @Element(required = false)
    public String description;

    @Namespace(reference = "http://purl.org/rss/1.0/modules/content/")
    @Element(required = false)
    public String encoded;

    @Namespace(reference = "http://wellformedweb.org/CommentAPI/")
    @Element(required = false)
    public String commentRss;

//    @Namespace(reference = "http://purl.org/rss/1.0/modules/slash/")
//    @Element(required = false)
//    public String comments;

}