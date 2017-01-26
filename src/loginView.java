import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.Button;
import java.awt.event.ActionListener;

public class loginView {

	private JFrame frame;
	private JTextField login;
	private JTextField password;
	private JButton connexion;
	private JButton createAccount;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginView window = new loginView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public loginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		connexion = new JButton("Connexion");
		connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (login.getText() != "" && password.getText() != "") {
					
				}
			}
		});
		connexion.setAction(action);
		connexion.setBounds(152, 180, 138, 29);
		frame.getContentPane().add(connexion);
		
		login = new JTextField();
		login.setBounds(40, 124, 374, 44);
		frame.getContentPane().add(login);
		login.setColumns(10);
		
		createAccount = new JButton("Create account");
		createAccount.setBounds(152, 221, 138, 29);
		frame.getContentPane().add(createAccount);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(40, 44, 374, 44);
		frame.getContentPane().add(password);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(199, 96, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(199, 16, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblRssfeedcreator = new JLabel("RSSFeedCreator");
		lblRssfeedcreator.setBounds(6, 256, 107, 16);
		frame.getContentPane().add(lblRssfeedcreator);
		
		connexion.setEnabled(false);
		createAccount.setEnabled(false);
		checkUpdateTextField();
	}
	
	private void checkUpdateTextField() {
		login.getDocument().addDocumentListener(new SimpleDocumentListener() {

			@Override
			public void textUpdate(DocumentEvent e) {
				if (e.getLength() > 0 && password.getText().length() > 0) {
					connexion.setEnabled(true);
					createAccount.setEnabled(true);
				}
			}
			
		});
		
		password.getDocument().addDocumentListener(new SimpleDocumentListener() {

			@Override
			public void textUpdate(DocumentEvent e) {
				if (e.getLength() > 0 && login.getText().length() > 0) {
					connexion.setEnabled(true);
					createAccount.setEnabled(true);
				}

			}
			
		});
	}
	
	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
