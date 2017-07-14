package com.morladim.morganrss;

import com.morladim.morganrss.rss2.Rss2Xml;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
public interface NewsApi {

    @GET("http://www.appinn.com/feed/")
//    @GET("http://www.zhihu.com/rss")
    Observable<Rss2Xml> getXml();
//    Observable<Rss2Xml> getXml(@Url String url);
}
