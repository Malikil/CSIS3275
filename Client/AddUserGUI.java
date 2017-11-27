package Server;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class AddUserGUI {

	private JFrame addUserFrame;
	private JTextField textField;
	private JTextField addUserField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserGUI window = new AddUserGUI();
					window.addUserFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddUserGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addUserFrame = new JFrame();
		addUserFrame.getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		addUserFrame.setTitle("Add User");
		addUserFrame.setBounds(100, 100, 300, 344);
		addUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addUserFrame.getContentPane().setLayout(null);
		
		JButton addDBBttn = new JButton("Add User");
		addDBBttn.setBounds(135, 253, 117, 29);
		addUserFrame.getContentPane().add(addDBBttn);
		addUserFrame.setTitle("Add User");
		addUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addUserFrame.setBounds(35, 10, 405, 371);
		addUserFrame.getContentPane().add(addDBBttn);
		
		JLabel addUserLbl = new JLabel("Enter Username");
		addUserLbl.setBounds(72, 39, 104, 16);
		addUserFrame.getContentPane().add(addUserLbl);
		
		addUserField = new JTextField();
		addUserField.setColumns(10);
		addUserField.setBounds(182, 34, 130, 26);
		addUserFrame.getContentPane().add(addUserField);
		
		JList<?> userList = new JList<>();
		userList.setBounds(114, 88, 154, 141);
		JScrollPane sp = new JScrollPane(userList);
		sp.setBounds(114, 100, 154, 141);
		addUserFrame.getContentPane().add(sp);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(359, 303, 40, 40);
		helpBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(helpBttn,
					"Username Field:  \n   Enter a desired Username\n" + 
					"Database List: \n  Select certain databases you wish to \n assign to the new user\n\n" +
					"	Press 'Add User' to add new User");
			}
		});
		addUserFrame.getContentPane().add(helpBttn);
		
		JLabel DatabaseListLbl = new JLabel("Database List");
		DatabaseListLbl.setHorizontalAlignment(SwingConstants.CENTER);
		DatabaseListLbl.setBounds(135, 72, 104, 16);
		addUserFrame.getContentPane().add(DatabaseListLbl);
	}
}
