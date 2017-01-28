package RssFeed.View;

import RssFeed.Model.FeedItem;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;



/**
 * Created by martreux on 27/01/2017.
 */
public class MyCell extends JPanel implements ListCellRenderer<FeedItem>  {
    private JLabel title;
    private JLabel date;
    private JTextArea description;
    private JPanel panel;

    public MyCell() {
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        add(panel);
        setBackground(Color.WHITE);
        Font font = title.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        title.setFont(boldFont);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends FeedItem> list, FeedItem value, int index, boolean isSelected, boolean cellHasFocus) {
        title.setText(StringEscapeUtils.unescapeHtml3(value.getTitle()));
        date.setText(value.getPubDate());
        description.setText(value.getDescription());
        return this;
    }
}
