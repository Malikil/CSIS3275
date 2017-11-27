package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDatabaseGUI extends JDialog{

	private JTextField dbNameField;

	/**
	 * Create the application.
	 */
	public AddDatabaseGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Add Database");
		getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		setBounds(100, 100, 392, 356);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		
		JLabel dbNameLbl = new JLabel("Enter Database Name");
		dbNameLbl.setBounds(53, 35, 138, 16);
		getContentPane().add(dbNameLbl);
		
		dbNameField = new JTextField();
		dbNameField.setBounds(203, 30, 130, 26);
		getContentPane().add(dbNameField);
		dbNameField.setColumns(10);
		
		JList<?> dbList = new JList<>();
		dbList.setBounds(125, 84, 154, 141);
		JScrollPane sp = new JScrollPane(dbList);
		sp.setBounds(125, 104, 154, 159);
		getContentPane().add(sp);
		
		JButton addDBBttn = new JButton("Add Database");
		addDBBttn.setBounds(135, 275, 117, 29);
		addDBBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		getContentPane().add(addDBBttn);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(346, 288, 40, 40);
		helpBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(helpBttn,
					"Database Name Field:  \n   Enter a desired Database Name\n\n" + 
					"User List: \n  Select certain Users you wish to \n" + "  assign to the new Database\n\n" +
					"	Press 'Add Database' to add new Database");
			}
		});
		getContentPane().add(helpBttn);
		
		JLabel userListLbl = new JLabel("User List");
		userListLbl.setBounds(168, 78, 61, 16);
		getContentPane().add(userListLbl);
	}
	
	public String getDatabaseName()
	{
		if(dbNameField != null)
		{
			return dbNameField.getText();
		}
		else
		{
			return null;
		}
	}
	

}
