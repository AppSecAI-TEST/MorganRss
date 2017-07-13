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

import com.morladim.morganrss.main.RssFeed;
import com.morladim.morganrss.main.RssHandler;
import com.morladim.morganrss.main.RssSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

MHandler handler=                    new MHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                rssFeed = getFeed(source.getUrl());
                System.out.println(rssFeed.getAllItems());
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
    RssFeed rssFeed;
    private class MHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            System.out.println("---------------------");
            System.out.println(rssFeed.getAllItems());
            System.out.println("---------------------");
        }
    }

    private RssFeed getFeed(String urlString) {
        try {
            URL url = new URL(urlString);
            SAXParserFactory factory = SAXParserFactory.newInstance();  // 构建Sax解析工厂
            SAXParser parser = factory.newSAXParser(); // 使用Sax解析工厂构建Sax解析器
            parser.setProperty(OutputKeys.ENCODING,"UTF-8");
//            Charset charset = Charset.forName("UTF-8");
            XMLReader xmlreader = parser.getXMLReader();   // 使用Sax解析器构建xml Reader

            RssHandler rssHandler = new RssHandler(); // 构建自定义的RSSHandler作为xml Reader的处理器（或代理）
//            rssHandler.
            xmlreader.setContentHandler(rssHandler);     // 构建自定义的RSSHandler作为xml Reader的处理器（或代理）


            InputSource is = new InputSource(url.openStream());      // 使用url打开流,并将流作为xml Reader的输入源并解析
            xmlreader.parse(is);

            return rssHandler.getFeed();     // 将解析结果作为 RSSFeed 对象返回
        } catch (Exception ee) {
            ee.printStackTrace();
            return null;
        }
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
