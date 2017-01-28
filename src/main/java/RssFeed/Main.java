package RssFeed;

import RssFeed.Model.Singleton;
import RssFeed.View.FeedView;
import RssFeed.View.LoginView;

import javax.swing.*;

/**
 * Created by martreux on 26/01/2017.
 */
public class Main {

    public static void main(String[] args) {
        Runnable guiCreator = new Runnable() {
                public void run() {
                    JFrame frame = new JFrame("RSSFeed");
//                    frame.add(new FeedView().panel, 0);
                    frame.add(new LoginView().panel, 0);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setResizable(false);
                    frame.setBounds(100, 100, 1250, 650);
                    frame.setVisible(true);

                Singleton.getInstance().setFrame(frame);
            }
        };
        SwingUtilities.invokeLater(guiCreator);
    }
}
