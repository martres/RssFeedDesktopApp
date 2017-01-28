package RssFeed.Model;

import RssFeed.Controller.ParserRssFeed;

import javax.swing.*;

/**
 * Created by martreux on 27/01/2017.
 */
public class Singleton {

    JFrame frame;
    User userConnected;
    private Feed[] feeds;

    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public User getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(User userConnected) {
        this.userConnected = userConnected;
    }

    public Feed[] getFeeds() {
        return feeds;
    }

    public void setFeeds(Feed[] feedsTmp) {
        this.feeds = new Feed[feedsTmp.length];
        for (int i = 0; i < feedsTmp.length; i++) {
            Feed tmp = ParserRssFeed.getFeed(feedsTmp[i].getUrl());
            tmp.setId(feedsTmp[i].getId());
            this.feeds[i] = tmp;
        }
    }
}
