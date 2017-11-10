package Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JDialog
{	
	private JTextField usernameField;
	private JPasswordField passwordField;
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
		
		JLabel lblIp = new JLabel("IP: ");
		lblIp.setBounds(70, 26, 30, 14);
		getContentPane().add(lblIp);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(22, 65, 75, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(22, 96, 75, 14);
		getContentPane().add(lblPassword);
		
		ipField = new JTextField();
		ipField.setBounds(100, 23, 133, 20);
		getContentPane().add(ipField);
		ipField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setBounds(100, 62, 133, 20);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 93, 133, 20);
		getContentPane().add(passwordField);
		passwordField.setEchoChar('*');
		passwordField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(22, 142, 89, 23);
		btnLogin.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String ip = ipField.getText();
				// Make sure login info is valid
				if (!ip.equals(""))
					if (!usernameField.getText().equals("") &&
						!(new String(passwordField.getPassword()).equals("")))
					{
						enteredUser = usernameField.getText();
						enteredPass = new String(passwordField.getPassword());
						enteredIP = ipField.getText();
						cancelled = false;
						thisDialog.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(thisDialog, "Username or Password are invalid");
						usernameField.requestFocusInWindow();
						passwordField.requestFocusInWindow();
					}
				else
				{
					JOptionPane.showMessageDialog(thisDialog, "Enter an IP to connect to: \nxxx.xxx.x.xxx");
					ipField.requestFocusInWindow();
				}
			}
		});
		getContentPane().add(btnLogin);
		
		JButton button = new JButton("?");
		button.setFont(new Font("Tahoma", Font.BOLD, 9));
		button.setBounds(257, 133, 40, 40);
		button.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisDialog,
					"IP Address Field: \nEnter Server IP Address\n" + 
					"UserName TextField: \nEnter Username with corresponding Password \n" +
					"Press 'Login' to continue");
			}
		});
		getContentPane().add(button);
		
		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.setBounds(139, 142, 89, 23);
		cancelBttn.addActionListener(new ActionListener() 
		{
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
	
	public String getEnteredIP() 	{ return enteredIP; }
	public String getEnteredUser() 	{return enteredUser;}
	public String getEnteredPass() 	{return enteredPass;}
	public boolean isCancelled() 	{ return cancelled; }
}
