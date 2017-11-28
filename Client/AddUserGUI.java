package Client;

import java.awt.Font;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
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

	private JFrame addUserFrame;
	private JTextField textField;
	private JTextField addUserField;
	private JTextField addPasswordField;
	private JCheckBox isAdmin;
	private JList<String> databaseList;
	private String[] databases;
	private DefaultListModel model;

	/**
	 * Create the application.
	 */
	public AddUserGUI(String[] databases)
	{
		this.databases = databases;
		initialize(true, null);
		

	}
	
	public AddUserGUI(String[] databaseslist, String username,boolean add)
	{
		databases = databaseslist;
		initialize(add, username);

	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean add, String username) {
		model = new DefaultListModel<String>();
		databaseList = new JList<String>(databases);
		for(String dbName: databases) 
		{
			model.addElement(dbName);
		}
		databaseList.setModel(model);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		JDialog thisD = this;
		
		
		if(add)
		{
			
			getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
			setTitle("Add User");
			setBounds(100, 100, 300, 344);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			
			JButton addDBBttn = new JButton("Add User");
			addDBBttn.setBounds(135, 253, 117, 29);
			addDBBttn.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					thisD.dispose();
				}
			});
			getContentPane().add(addDBBttn);
			setTitle("Add User");
			setBounds(35, 10, 405, 371);
			getContentPane().add(addDBBttn);
			
			JLabel addUserLbl = new JLabel("Enter Username");
			addUserLbl.setBounds(73, 16, 104, 16);
			getContentPane().add(addUserLbl);
			
			addUserField = new JTextField();
			addUserField.setColumns(10);
			addUserField.setBounds(183, 11, 130, 26);
			getContentPane().add(addUserField);
			

			databaseList.setBounds(114, 88, 154, 141);

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
		else //Edit---------------------------------------------------------------------------------
		{
			getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
			setTitle("Edit User");
			setBounds(100, 100, 300, 344);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			
			JButton addDBBttn = new JButton("Edit User");
			addDBBttn.setBounds(135, 253, 117, 29);
			addDBBttn.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					thisD.dispose();
				}
			});
			getContentPane().add(addDBBttn);
			setTitle("Edit User");
			setBounds(35, 10, 405, 371);
			getContentPane().add(addDBBttn);
			
			JLabel addUserLbl = new JLabel("Username:");
			addUserLbl.setBounds(73, 16, 104, 16);
			getContentPane().add(addUserLbl);
			
			addUserField = new JTextField();
			addUserField.setColumns(10);
			addUserField.setBounds(183, 11, 130, 26);
			getContentPane().add(addUserField);
			addUserField.setText(username);
			addUserField.setEditable(false);
			

			databaseList.setBounds(114, 88, 154, 141);
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
	}
	
	public User getUser()
	{
		int[] selectedIndexes =  databaseList.getSelectedIndices();
		String[] selectedItems = new String[selectedIndexes.length];
		for(int i = 0; i < selectedIndexes.length; i++)
		{
			selectedItems[i] = databases[i];
		}
		
		return new User(addUserField.getText(),addPasswordField.getText(), selectedItems, isAdmin.isSelected());
	}
}
