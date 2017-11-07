package Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JDialog
{	
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField ipField;
	
	private String enteredUser;
	private String enteredPass;
	private String enteredIP;
	private boolean cancelled;

	/**
	 * Create the application.
	 */
	public LoginGUI()
	{
		setTitle("Login Page");
		enteredUser = "";
		enteredPass = "";
		enteredIP = "";
		cancelled = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		JDialog thisDialog = this;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 323, 228);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(22, 65, 63, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(22, 96, 63, 14);
		getContentPane().add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setBounds(95, 62, 133, 20);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(95, 93, 133, 20);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(22, 142, 89, 23);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Make sure login info is valid
				if (!ipField.getText().equals(""))
					if (!usernameField.getText().equals("") &&
						!passwordField.getText().equals(""))
					{
						enteredUser = usernameField.getText();
						enteredPass = passwordField.getText();
						enteredIP = ipField.getText();
						cancelled = false;
						thisDialog.setVisible(false);
					}
					else
						JOptionPane.showMessageDialog(thisDialog, "Username or Password are invalid");
				else
					JOptionPane.showMessageDialog(thisDialog, "Enter an IP to connect to.");
			}
		});
		getContentPane().add(btnLogin);
		
		JLabel lblIp = new JLabel("IP: ");
		lblIp.setBounds(59, 26, 29, 14);
		getContentPane().add(lblIp);
		
		ipField = new JTextField();
		ipField.setBounds(95, 23, 133, 20);
		getContentPane().add(ipField);
		ipField.setColumns(10);
		
		JButton button = new JButton("?");
		button.setFont(new Font("Tahoma", Font.BOLD, 9));
		button.setBounds(257, 133, 40, 40);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisDialog, "Help message goes here");
			}
		});
		getContentPane().add(button);
		
		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.setBounds(139, 142, 89, 23);
		cancelBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cancelled = true;
				thisDialog.dispose();
			}
		});
		getContentPane().add(cancelBttn);
	}
	
	@Override
	public void setVisible(boolean b)
	{
		if (b)
		{
			if (!enteredUser.equals("") || !enteredPass.equals(""))
				JOptionPane.showMessageDialog(this, "Username or Password was incorrect.");
			cancelled = true;
		}
		super.setVisible(b);
	}
	
	public String getEnteredIP() { return enteredIP; }
	public String getEnteredUser() { return enteredUser; }
	public String getEnteredPass() { return enteredPass; }
	public boolean isCancelled() { return cancelled; }
}
