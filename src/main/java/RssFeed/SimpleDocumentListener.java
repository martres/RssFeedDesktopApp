package RssFeed;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by martreux on 26/01/2017.
 */
@FunctionalInterface
public interface SimpleDocumentListener extends DocumentListener {
    void textUpdate(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        textUpdate(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e) {
        textUpdate(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        textUpdate(e);
    }
}