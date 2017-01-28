package RssFeed.Controller;

import RssFeed.Model.Feed;
import RssFeed.Model.FeedItem;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.description;

/**
 * Created by martreux on 27/01/2017.
 */
public class ParserRssFeed {

    public static Feed getFeed(String url) {
        URL feedUrl = null;
        try {
            feedUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed sf = null;
        try {
            sf = input.build(new XmlReader(feedUrl));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Feed feed = new Feed();
        feed.setDescription(sf != null ? sf.getDescription() : null);
        feed.setLink(sf.getLink());
        feed.setImage(sf.getImage().getUrl());
        feed.setPubDate(String.valueOf(sf.getPublishedDate()));
        feed.setTitle(sf.getTitle());
        feed.setUrl(url);


        List entries = sf.getEntries();
        FeedItem[] feedItems = new FeedItem[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            FeedItem item = new FeedItem();
            SyndEntry entry = (SyndEntry) sf.getEntries().get(i);
            item.setTitle(entry.getTitle());
            item.setPubDate(String.valueOf(entry.getPublishedDate()));
            item.setLink(entry.getLink());
            item.setDescription(entry.getDescription().getValue());
            feedItems[i] = item;
        }
        feed.setItems(feedItems);
        return feed;
    }

}