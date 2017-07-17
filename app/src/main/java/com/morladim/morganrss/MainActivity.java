package com.morladim.morganrss;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.morladim.morganrss.database.ChannelManager;
import com.morladim.morganrss.database.RssVersionManager;
import com.morladim.morganrss.database.entity.Channel;
import com.morladim.morganrss.main.RssFeed;
import com.morladim.morganrss.main.RssHandler;
import com.morladim.morganrss.main.RssSource;
import com.morladim.morganrss.rss2.Rss2Xml;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;

//http://blog.csdn.net/qq_37149313/article/details/70264656

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MHandler handler = new MHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Channel> list = ChannelManager.getAll();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        SAXParser
        final RssSource source = new RssSource("知乎", "http://zhihu.com/rss");
        System.out.println("==================");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                rssFeed = getFeed("https://www.zhihu.com/rss");
//                System.out.println(rssFeed.getAllItems());
//                handler.sendEmptyMessage(1);
//            }
//        }).start();

        NewsApi api = createByXML("http://www.baidu.com", NewsApi.class);
        api.getXml()
//        api.getXml("https://www.zhihu.com/rss")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<Rss2Xml>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Rss2Xml rss2Xml) throws Exception {

                        String version = rss2Xml.version;
                        if (version != null) {
                            long versionId = RssVersionManager.insertOrUpdate(version);
                            System.out.println("versionId " + versionId);
//                            Channel channel = ChannelManager. rss2Xml.channel;
                            long channelId = ChannelManager.insertOrUpdate(rss2Xml.channel, versionId);

                            System.out.println("channelId =" + channelId);
                            List<Channel> list = ChannelManager.getAll();

                        }


                        System.out.println("ddddddd");
                        System.out.println(rss2Xml.channel.title);
                        System.out.println(rss2Xml.channel.image.link);
//                        System.out.println(rss2Xml.channel.atomLink);
//                        System.out.println(rss2Xml.channel.link);
//                        System.out.println(rss2Xml.channel.atomLink.href);
//                        System.out.println(rss2Xml.channel.atomLink.href);
                        System.out.println("ddddddd");

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                });


    }

//    AndroidSchedulers

    private static final int CONNECT_TIME_OUT = 5;
    private static final int READ_TIME_OUT = 5;
    private static final int WRITE_TIME_OUT = 5;

    private OkHttpClient getClient() {
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIME_OUT, SECONDS)
                        .readTimeout(READ_TIME_OUT, SECONDS)
                        .writeTimeout(WRITE_TIME_OUT, SECONDS)
//                        .cache(getCache())
                        .retryOnConnectionFailure(true)
//                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                        .addInterceptor(new HeadInterceptor())
//                        .addInterceptor(new CookieInterceptor())
//                        .addInterceptor(new CacheInterceptor())
//                        .addInterceptor(new LoginInterceptor())
                        .build();
        return client;
    }

    public <T> T createByXML(String baseUrl, Class<T> service) {


        AnnotationStrategy annotationStrategy = new AnnotationStrategy();
//        Format format = new Format(0, null, new HyphenStyle(), Verbosity.HIGH);
        Persister persister = new Persister(annotationStrategy);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addConverterFactory(SimpleXmlConverterFactory.create(persister))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    RssFeed rssFeed;

    private class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("---------------------");
            System.out.println(rssFeed.getAllItems());
            System.out.println("---------------------");
        }
    }

    private RssFeed getFeed(String urlString) {
//        try {
        try {
            URL url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SAXParserFactory factory = SAXParserFactory.newInstance();  // 构建Sax解析工厂
        SAXParser parser = null; // 使用Sax解析工厂构建Sax解析器
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
//        try {
//            parser.setProperty(OutputKeys.ENCODING,"UTF-8");
//        } catch (SAXNotRecognizedException e) {
//            e.printStackTrace();
//        } catch (SAXNotSupportedException e) {
//            e.printStackTrace();
//        }

//            SAXParser parser = factory.newSAXParser(); // 使用Sax解析工厂构建Sax解析器
//            parser.setProperty(OutputKeys.ENCODING,"UTF-8");
//            Charset charset = Charset.forName("UTF-8");
//            XMLReader xmlreader = parser.getXMLReader();   // 使用Sax解析器构建xml Reader

        RssHandler rssHandler = new RssHandler(); // 构建自定义的RSSHandler作为xml Reader的处理器（或代理）
//            rssHandler.
//            xmlreader.setContentHandler(rssHandler);     // 构建自定义的RSSHandler作为xml Reader的处理器（或代理）


        InputSource is = new InputSource();      // 使用url打开流,并将流作为xml Reader的输入源并解析
//            is.setEncoding("GB2312");
//            is.setEncoding("gbk");
        is.setEncoding("UTF-8");
        is.setCharacterStream(new StringReader(urlString));
        try {
            parser.parse(is, rssHandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//            xmlreader.parse(is);

//            InputStream inputStream = new BufferedInputStream(url.openStream());
//
//            InputStreamReader isr = new InputStreamReader(inputStream,"utf-8");


        return rssHandler.getFeed();     // 将解析结果作为 RSSFeed 对象返回
//        } catch (Exception ee) {
//            ee.printStackTrace();
//            return null;
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
