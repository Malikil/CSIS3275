package Server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class ServerGUI extends JFrame
{
	private JTextField txtUserList;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel tableModel;
	private Server parent;

	/**
	 * Create the application.
	 * @param server The server to be notified of events
	 */
	public ServerGUI(Server server)
	{
		initialize();
		parent = server;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		new JFrame();
		setBounds(100, 100, 622, 485);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblServerApp = new JLabel("Server App.");
		lblServerApp.setBounds(10, 11, 95, 35);
		lblServerApp.setFont(new Font("Tahoma", Font.BOLD, 16));
		getContentPane().add(lblServerApp);
		
		JLabel usrListLbl = new JLabel("Userlist");
		usrListLbl.setBounds(31, 61, 80, 14);
		usrListLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(usrListLbl);
		
		JComboBox<String> usrListCB = new JComboBox<String>();
		usrListCB.setBounds(120, 175, 188, 20);
		getContentPane().add(usrListCB);
		
		JButton usrListGoBttn = new JButton("Select User");
		usrListGoBttn.setBounds(318, 57, 89, 23);
		getContentPane().add(usrListGoBttn);
		
		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setBounds(31, 90, 80, 14);
		lblDatabase.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblDatabase);
		
		JComboBox<String> dtbseCB = new JComboBox<String>();
		dtbseCB.setBounds(120, 148, 188, 20);
		getContentPane().add(dtbseCB);
		
		JButton dtbseGoBttn = new JButton("Select");
		dtbseGoBttn.setBounds(318, 86, 89, 23);
		getContentPane().add(dtbseGoBttn);
		
		JLabel selectLbl = new JLabel("Select Table");
		selectLbl.setBounds(31, 120, 80, 14);
		selectLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(selectLbl);
		
		//Why TextField?
				/*
				textField = new JTextField();
				textField.setBounds(120, 117, 188, 20);
				textField.setEditable(false);
				textField.setColumns(10);
				getContentPane().add(textField);
				*/
		
		JButton selectGobttn = new JButton("Enter");
		selectGobttn.setBounds(318, 116, 89, 23);
		getContentPane().add(selectGobttn);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setBounds(31, 149, 80, 14);
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(tablesLbl);
		
		JComboBox<String> tablesCB = new JComboBox<String>();
		tablesCB.setBounds(120, 60, 188, 20);
		getContentPane().add(tablesCB);
		
		JButton tablesGoBttn = new JButton("Go!");
		tablesGoBttn.setBounds(318, 145, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JLabel entriesLbl = new JLabel("Entries");
		entriesLbl.setBounds(31, 178, 80, 14);
		entriesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(entriesLbl);
		
		JComboBox<String> entriesCB = new JComboBox<String>();
		entriesCB.setBounds(120, 87, 188, 20);
		getContentPane().add(entriesCB);
		
		JButton entriesGoBttn = new JButton("Change");
		entriesGoBttn.setBounds(318, 174, 89, 23);
		getContentPane().add(entriesGoBttn);
		
		tableModel = new DefaultTableModel();
		table = new JTable();
		table.setBounds(81, 228, 326, 124);
		table.setModel(tableModel);
		getContentPane().add(table);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setBounds(566, 407, 40, 40);
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		getContentPane().add(helpBttn);
		
		txtUserList = new JTextField();
		txtUserList.setBounds(465, 17, 131, 23);
		getContentPane().add(txtUserList);
		txtUserList.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtUserList.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserList.setText("User List");
		txtUserList.setBackground(Color.LIGHT_GRAY);
		txtUserList.setEditable(false);
		txtUserList.setColumns(10);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(465, 42, 129, 342);
		getContentPane().add(textArea_1);
	}
}
