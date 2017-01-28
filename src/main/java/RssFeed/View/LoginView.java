package RssFeed.View;

import RssFeed.Controller.ApiManager;
import RssFeed.Model.Singleton;
import RssFeed.SimpleDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by martreux on 26/01/2017.
 */
public class LoginView{

    public JPanel panel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton connexionButton;
    private JButton inscriptionButton;
    private JPanel spaceTop;
    private JPanel spaceLeft;
    private JPanel spaceRight;
    private JLabel loginText;
    private JLabel passwordText;
    private JLabel errorLabel;

    public LoginView() {
        initView();
        connexionButton.setEnabled(false);
        inscriptionButton.setEnabled(false);
        errorLabel.setVisible(false);
    }

    private void initView() {
        loginText.setFont(loginText.getFont().deriveFont(20.0f));
        passwordText.setFont(passwordText.getFont().deriveFont(20.0f));

        spaceLeft.setPreferredSize(new Dimension(200, 100));
        spaceRight.setPreferredSize(new Dimension(200, 100));
        loginField.setPreferredSize(new Dimension(50,50));
        passwordField.setPreferredSize(new Dimension(50, 50));
        connexionButton.setPreferredSize(new Dimension(250, 50));
        inscriptionButton.setPreferredSize(new Dimension(250, 50));

        loginField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            if (e.getLength() == 0 || loginField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                connexionButton.setEnabled(false);
                inscriptionButton.setEnabled(false);
            } else {
                connexionButton.setEnabled(e.getLength() > 0 && String.valueOf(passwordField.getPassword()).length() > 0);
                inscriptionButton.setEnabled(e.getLength() > 0 && String.valueOf(passwordField.getPassword()).length() > 0);
            }
        });

        passwordField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            if (e.getLength() == 0|| loginField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                connexionButton.setEnabled(false);
                inscriptionButton.setEnabled(false);
            } else {
                connexionButton.setEnabled(e.getLength() > 0 && loginField.getText().length() > 0);
                inscriptionButton.setEnabled(e.getLength() > 0 && loginField.getText().length() > 0);
            }
        });

        connexionButton.addActionListener(e -> {
            try {
                ApiManager.connectUser(this, loginField.getText(), String.valueOf(passwordField.getPassword()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        inscriptionButton.addActionListener(e -> {
            try {
                ApiManager.createAccount(this, loginField.getText(), String.valueOf(passwordField.getPassword()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }

    public void correctConnexion() {
        JFrame frame = Singleton.getInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new FeedView().panel);
        frame.setSize(1250, 650);
        frame.validate();
    }

    public void errorConnexion() {
        errorLabel.setVisible(true);
    }
}
