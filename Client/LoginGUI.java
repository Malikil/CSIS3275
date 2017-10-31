package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class LoginGUI extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public static void main(String[] args) 
	{
		
					LoginGUI window = new LoginGUI();
					window.setVisible(true);
				
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		setTitle("Login Page");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new JFrame();
		this.setBounds(100, 100, 323, 228);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(22, 65, 63, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(22, 96, 63, 14);
		getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(95, 62, 133, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(95, 93, 133, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(22, 142, 89, 23);
		getContentPane().add(btnLogin);
		
		JLabel lblIp = new JLabel("IP: ");
		lblIp.setBounds(59, 26, 29, 14);
		getContentPane().add(lblIp);
		
		textField_2 = new JTextField();
		textField_2.setBounds(95, 23, 133, 20);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton button = new JButton("?");
		button.setFont(new Font("Tahoma", Font.BOLD, 9));
		button.setBounds(257, 133, 40, 40);
		getContentPane().add(button);
		
		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.setBounds(139, 142, 89, 23);
		getContentPane().add(cancelBttn);
		
		
	}
}
