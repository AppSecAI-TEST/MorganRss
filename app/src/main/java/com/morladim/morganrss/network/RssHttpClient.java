package com.morladim.morganrss.network;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <br>创建时间：2017/7/20.
 *
 * @author morladim
 */
public class RssHttpClient {

    public static final String BASE_URL = "http://www.pannny.com";

    private static final int CONNECT_TIME_OUT = 5;
    private static final int READ_TIME_OUT = 5;
    private static final int WRITE_TIME_OUT = 5;

    private NewsApi newsApi;
    private volatile static RssHttpClient client;

    private RssHttpClient() {
        Retrofit retrofit = getRetrofit();
        newsApi = retrofit.create(NewsApi.class);
    }

    private static RssHttpClient getInstance() {
        if (client == null) {
            synchronized (RssHttpClient.class) {
                if (client == null) {
                    client = new RssHttpClient();
                }
            }
        }
        return client;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, SECONDS)
                .readTimeout(READ_TIME_OUT, SECONDS)
                .writeTimeout(WRITE_TIME_OUT, SECONDS)
//                        .cache(getCache())
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new CookieInterceptor())
                .addInterceptor(new HeadInterceptor())
                .build();
    }

    private Retrofit getRetrofit() {
        AnnotationStrategy annotationStrategy = new AnnotationStrategy();
        Persister persister = new Persister(annotationStrategy);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(SimpleXmlConverterFactory.create(persister))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NewsApi getNewsApi() {
        return RssHttpClient.getInstance().newsApi;
    }

}
