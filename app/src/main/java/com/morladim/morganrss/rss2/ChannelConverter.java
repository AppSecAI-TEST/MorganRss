package com.morladim.morganrss.rss2;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.io.Serializable;

/**
 * <br>创建时间：2017/7/14.
 *
 * @author morladim
 */
public class ChannelConverter implements Converter<Rss2Channel>, Serializable {

    @Override
    public Rss2Channel read(InputNode node) throws Exception {
        Rss2Channel channel = new Rss2Channel();
        InputNode child;

        while ((child = node.getNext()) != null) {
            System.out.println(child.getName());

            switch (child.getName()) {
                case "title":
                    channel.title = (child.getValue());
                    break;
                case "link":
                    if (child.getPrefix() != null && child.getPrefix().equals("atom")) {
                        AtomLink atom = new AtomLink();
                        atom.href = (child.getAttribute("href").getValue());
                        atom.rel = (child.getAttribute("rel").getValue());
                        atom.type = (child.getAttribute("type").getValue());
                        channel.atomLink = (atom);
                    } else {
                        channel.link = (child.getValue());
                    }
                    break;
                default:
                    break;
            }
        }
        return channel;
    }

    @Override
    public void write(OutputNode node, Rss2Channel value) throws Exception {

    }
}
