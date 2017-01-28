package RssFeed.View;

import RssFeed.Controller.ApiManager;
import RssFeed.Controller.ParserRssFeed;
import RssFeed.Model.Feed;
import RssFeed.Model.FeedItem;
import RssFeed.Model.Singleton;
import RssFeed.SimpleDocumentListener;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import javax.sql.rowset.spi.XmlReader;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


/**
 * Created by martreux on 26/01/2017.
 */
public class FeedView {
    private JTextField fieldFlux;
    private JButton addFluxButton;
    private JList<String> listFlux;
    private JList<FeedItem> listFluxSelected;
    private JLabel NameField;
    public JPanel panel;
    private JButton refreshButton;
    private JButton deleteFluxButton;
    private JButton logoutButton;
    private JLabel badUrl;


    public FeedView() {
        initView();
    }

    private void initView() {
        addFluxButton.setEnabled(false);
        deleteFluxButton.setEnabled(false);
        refreshButton.setEnabled(false);
        badUrl.setVisible(false);
        NameField.setText("Welcom, " + Singleton.getInstance().getUserConnected().email + ", to this Amazing RSSFeed");

        try {
            ApiManager.getFlux(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listFlux.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listFlux.getSelectedIndex() != -1) {
                    deleteFluxButton.setEnabled(true);
                    listFluxSelected.setListData(Singleton.getInstance().getFeeds()[listFlux.getSelectedIndex()].getItems());
                    refreshButton.setEnabled(true);
                }
            }
        });

        listFluxSelected.setCellRenderer(new MyCell());

        ComponentListener l = new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                // next line possible if list is of type JXList
                // list.invalidateCellSizeCache();
                // for core: force cache invalidation by temporarily setting fixed height
                listFluxSelected.setFixedCellHeight(10);
                listFluxSelected.setFixedCellHeight(-1);
            }

        };

        listFluxSelected.addComponentListener(l);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = Singleton.getInstance().getFrame();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new LoginView().panel);
                frame.setSize(1250, 650);
                frame.validate();
            }
        });

        fieldFlux.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            if (!Objects.equals(fieldFlux.getText(), "")) {
                addFluxButton.setEnabled(true);
                badUrl.setVisible(false);
            }
        });

        deleteFluxButton.addActionListener(e -> {
            try {
                for (Feed feed: Singleton.getInstance().getFeeds()) {
                    System.out.print(feed.getId());
                }
                System.out.println(Singleton.getInstance().getFeeds()[listFlux.getSelectedIndex()]);
                System.out.println(Singleton.getInstance().getFeeds()[listFlux.getSelectedIndex()].getId());
                ApiManager.deleteFlux(this, Singleton.getInstance().getFeeds()[listFlux.getSelectedIndex()].getId());
                deleteFluxButton.setEnabled(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        refreshButton.addActionListener(e -> {
            if (listFlux.getSelectedIndex() != -1){
                Feed feed = Singleton.getInstance().getFeeds()[listFlux.getSelectedIndex()];
                feed = ParserRssFeed.getFeed(feed.getUrl());
                listFluxSelected.setListData(feed.getItems());
            }
        });

        addFluxButton.addActionListener(e -> {
            try {
                if (fieldFlux.getText() != ""){
                    if (!goodUrl(fieldFlux.getText())) {
                        badUrl.setVisible(true);
                        return;
                    }
                    ApiManager.pushFlux(this, fieldFlux.getText());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void reloadListFlux() {
        Feed[] feeds = Singleton.getInstance().getFeeds();
        String[] urls = new String[feeds.length];
        for (int i = 0; i < feeds.length; i++) {
            urls[i] = feeds[i].getUrl();
        }
        listFlux.setListData(urls);
        listFluxSelected.setListData(new FeedItem[0]);
    }

    public boolean goodUrl(String address) {
        boolean ok = false;
        try{
            URL feedUrl = null;
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed sf = null;
            feedUrl = new URL(address);
            sf = input.build(new com.sun.syndication.io.XmlReader(feedUrl));
            ok = true;
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return ok;
    }
}