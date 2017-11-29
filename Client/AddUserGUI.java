package Client;

import java.awt.Font;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.UIManager;
import Server.User;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class AddUserGUI extends JDialog {

	private JTextField addUserField;
	private JTextField addPasswordField;
	private JCheckBox isAdmin;
	private JList<String> databaseList;

	/**
	 * Create the application.
	 */
	public AddUserGUI(String[] databases)
	{
		initialize(databases);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] databases)
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		JDialog thisD = this;
		getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		setTitle("Add User");
		setBounds(100, 100, 405, 371);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBounds(70, 253, 117, 29);
		addUserButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				thisD.dispose();
			}
		});
		getContentPane().add(addUserButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(210, 253, 117, 29);
		cancelButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addUserField = null;
				thisD.dispose();
			}
		});
		getContentPane().add(cancelButton);
		
		JLabel addUserLbl = new JLabel("Enter Username");
		addUserLbl.setBounds(73, 16, 104, 16);
		getContentPane().add(addUserLbl);
		
		addUserField = new JTextField();
		addUserField.setColumns(10);
		addUserField.setBounds(183, 11, 130, 26);
		getContentPane().add(addUserField);
		
		databaseList = new JList<>(databases);
		databaseList.setBounds(114, 88, 154, 141);
		databaseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane sp = new JScrollPane(databaseList);
		sp.setBounds(114, 100, 154, 141);
		getContentPane().add(sp);
		
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
		getContentPane().add(helpBttn);
		
		JLabel DatabaseListLbl = new JLabel("Database List");
		DatabaseListLbl.setHorizontalAlignment(SwingConstants.CENTER);
		DatabaseListLbl.setBounds(135, 72, 104, 16);
		getContentPane().add(DatabaseListLbl);
		
		JLabel PassLB = new JLabel("Enter Password");
		PassLB.setBounds(73, 48, 104, 16);
		getContentPane().add(PassLB);
		
		addPasswordField = new JTextField();
		addPasswordField.setColumns(10);
		addPasswordField.setBounds(183, 43, 130, 26);
		getContentPane().add(addPasswordField);
	
		isAdmin = new JCheckBox("Check If Admin");
		isAdmin.setBounds(145, 289, 97, 23);
		getContentPane().add(isAdmin);
	}
	
	public User getUser()
	{
		if (addUserField == null)
			return null;
		String[] selectedItems =
				databaseList.getSelectedValuesList().toArray(new String[databaseList.getSelectedIndices().length]);
		
		return new User(addUserField.getText(),addPasswordField.getText(), selectedItems, isAdmin.isSelected());
	}
}
