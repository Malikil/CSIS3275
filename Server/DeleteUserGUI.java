package Server;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class DeleteUserGUI {

	private JFrame frmDeleteUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteUserGUI window = new DeleteUserGUI();
					window.frmDeleteUser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DeleteUserGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeleteUser = new JFrame();
		frmDeleteUser.setTitle("Delete User");
		frmDeleteUser.getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		frmDeleteUser.setBounds(100, 100, 348, 326);
		frmDeleteUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDeleteUser.getContentPane().setLayout(null);
		
		JLabel userListLbl = new JLabel("User List");
		userListLbl.setHorizontalAlignment(SwingConstants.CENTER);
		userListLbl.setBounds(123, 22, 100, 16);
		frmDeleteUser.getContentPane().add(userListLbl);
		
		JList<?> userList = new JList<>();
		JScrollPane sp = new JScrollPane(userList);
		sp.setBounds(86, 50, 169, 157);
		frmDeleteUser.getContentPane().add(sp);
		
		JButton deleteUserBttn = new JButton("Delete User");
		deleteUserBttn.setBounds(110, 219, 128, 29);
		frmDeleteUser.getContentPane().add(deleteUserBttn);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(302, 258, 40, 40);
		helpBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(helpBttn,
					"Database List: \n  Select certain Databases you wish to delete\n\n" +
					"	Press 'Delete Database' to delete selected databases");
			}
		});
		frmDeleteUser.getContentPane().add(helpBttn);
	}
	
}
