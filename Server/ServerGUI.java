package Server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import java.awt.SystemColor;

public class ServerGUI extends JFrame implements Runnable
{
	private JTextField textField;
	private DefaultTableModel tableModel;
	private Server parent;
	private JTable table;

	/**
	 * Create the application.
	 * @param server The server to be notified of events
	 */
	public ServerGUI(Server server)
	{
		getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		setTitle("Server Application");
		parent = server;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		new JFrame();
		setBounds(100, 100, 636, 817);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton selectUserBttn = new JButton("Select User");
		selectUserBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		selectUserBttn.setBounds(69, 103, 107, 23);
		getContentPane().add(selectUserBttn);
		
		JComboBox<String> userlistCB = new JComboBox<String>();
		userlistCB.setBounds(69, 72, 338, 20);
		getContentPane().add(userlistCB);
		
		tableModel = new DefaultTableModel();
		
		JScrollPane userlistListSP = new JScrollPane();
		userlistListSP.setBorder(new LineBorder(new Color(0, 0, 0)));
		userlistListSP.setBounds(69, 145, 338, 253);
		getContentPane().add(userlistListSP);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(new Color(204, 204, 255));
		menuBar.setBounds(0, 0, 628, 21);
		getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setBackground(new Color(153, 204, 255));
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(fileMenu);
		
		JMenu dbMenu = new JMenu("Databases");
		fileMenu.add(dbMenu);
		
		JMenu tablesMenu = new JMenu("Tables");
		fileMenu.add(tablesMenu);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		JMenuItem addTable = new JMenuItem("Add Table");
		addTable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//parent.saveTable(dbName, tableName, table);
			}
			
		});
		fileMenu.add(addTable);
		
		JMenuItem deleteTable = new JMenuItem("Delete Table");
		fileMenu.add(deleteTable);
		deleteTable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//parent.saveTable(dbName, tableName, table);
			}
		});
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setBackground(new Color(153, 204, 255));
		helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(helpMenu);
		
		JMenuItem details = new JMenuItem("Details");
		details.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(details,
						 "Userlist: \n\n\n"
						 + "" +
						 "" +
							  "Database Dropdown Menu: \n" +
							    "Choose A Database to modify and press go to recieve data \n\n\n" +
							  "Table Dropdown Menu: \n" +
							    "Choose a Table to Modify and press select to recieve data \n\n\n" +
							  "Add: \n" +
							    "Shows GUI for adding new Entry \n\n\n" +
							  "Edit: \n" +
							    "Shows GUI for editing selected Entry \n\n\n" +
							  "Delete: \n" +
							    "Popup box will confirm the deletion of entry \n\n\n" +
							  "Sort Dropdown Menu: \n" +
							    "Choose which column to Sort By \n\n\n" +
							  "Search Field: \n" +
							    "Enter Query to search by, Hit Search to Proceed");
			}
		});
		helpMenu.add(details);
		
		JLabel fieldsLbl = new JLabel("Fields");
		fieldsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		fieldsLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		fieldsLbl.setBounds(195, 409, 80, 19);
		getContentPane().add(fieldsLbl);
		
		JComboBox<String> fieldsCB = new JComboBox<String>();
		fieldsCB.setToolTipText("Fields");
		fieldsCB.setBounds(69, 439, 338, 20);
		getContentPane().add(fieldsCB);
		
		JButton addField = new JButton("Add");
		addField.setBounds(69, 470, 107, 23);
		getContentPane().add(addField);
		
		JButton deleteField = new JButton("Delete");
		deleteField.setBounds(186, 470, 104, 23);
		getContentPane().add(deleteField);
		
		JButton sortField = new JButton("Sort");
		sortField.setBounds(300, 470, 107, 23);
		getContentPane().add(sortField);
		
		table = new JTable();
		table.setBackground(SystemColor.control);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBounds(69, 504, 338, 172);
		getContentPane().add(table);
		
		JButton addEntry = new JButton("Add Entry");
		addEntry.setBounds(69, 687, 99, 43);
		getContentPane().add(addEntry);
		
		JButton deleteEntry = new JButton("Delete Entry");
		deleteEntry.setBounds(178, 687, 106, 43);
		getContentPane().add(deleteEntry);
		
		JButton editEntry = new JButton("Edit Entry");
		editEntry.setBounds(294, 687, 109, 43);
		getContentPane().add(editEntry);
		
		JScrollPane errorLogSP = new JScrollPane();
		errorLogSP.setBounds(431, 72, 158, 658);
		getContentPane().add(errorLogSP);
		
		JLabel errorLogLbl = new JLabel("Error Log");
		errorLogLbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorLogLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		errorLogLbl.setBounds(461, 45, 80, 19);
		getContentPane().add(errorLogLbl);
		
		JLabel userlistLbl = new JLabel("Userlist");
		userlistLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		userlistLbl.setHorizontalAlignment(SwingConstants.CENTER);
		userlistLbl.setBounds(195, 47, 80, 14);
		getContentPane().add(userlistLbl);
		
		JButton addUserBttn = new JButton("Add User");
		addUserBttn.setBounds(183, 103, 107, 23);
		getContentPane().add(addUserBttn);
		
		JButton deleteUserBttn = new JButton("Delete User");
		deleteUserBttn.setBounds(300, 103, 107, 23);
		getContentPane().add(deleteUserBttn);
		
		JList userlistList = new JList();
		getContentPane().add(userlistList);
		userlistList.setBackground(UIManager.getColor("Viewport.background"));
		userlistList.setBounds(69, 145, 336, 253);
	}

	@Override
	public void run()
	{
		this.setVisible(true);
	}
}