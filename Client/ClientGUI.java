package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class ClientGUI extends JFrame
{
	private Client parent;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField searchField;
	JComboBox<String> databaseCB;
	JComboBox<String> tablesCB;

	/**
	 * Create the application.
	 */
	public ClientGUI(Client owner) 
	{
		setTitle("Client Application");
		parent = owner;
		getContentPane().setFont(new Font("Tahoma", Font.ITALIC, 14));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		JFrame thisFrame = this;
		new JFrame();
		setBounds(100, 100, 638, 699);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		tablesLbl.setBounds(10, 98, 80, 14);
		getContentPane().add(tablesLbl);
		
		JButton tablesGoBttn = new JButton("Select");
		tablesGoBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		tablesGoBttn.setBounds(297, 94, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JButton tablesDeleteButton = new JButton("Delete");
		tablesDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		tablesDeleteButton.setBounds(297, 118, 89, 23);
		getContentPane().add(tablesDeleteButton);
		
		JLabel create_tableLbl = new JLabel("Entries");
		create_tableLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		create_tableLbl.setHorizontalAlignment(SwingConstants.CENTER);
		create_tableLbl.setBounds(194, 167, 109, 19);
		getContentPane().add(create_tableLbl);
		
		tablesCB = new JComboBox<String>();
		tablesCB.setToolTipText("");
		tablesCB.setBounds(99, 97, 188, 20);
		getContentPane().add(tablesCB);
		
		tableModel = new DefaultTableModel();
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(tableModel);
		table.setBounds(66, 396, 334, 172);
		getContentPane().add(table);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(582, 621, 40, 40);
		getContentPane().add(helpBttn);
		helpBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisFrame,
						 "DataBase Dropdown Menu: \n\n" +
							    "Choose A DataBase to modify and press go to recieve data \n\n" +
							  "Table DropDown Menu: \n\n" +
							    "Choose a Table to Modify and press select to recieve data \n\n" +
							  "Add: \n\n" +
							    "Shows GUI for adding new Entry \n\n" +
							  "Edit: \n" +
							    "Shows GUI for editing selected Entry \n\n" +
							  "Delete: \n" +
							    "Popup box will confirm the deletion of entry \n\n" +
							  "Sort DropDown Menu: \n\n" +
							    "Choose which column to Sort By \n" +
							  "Search Field: \n\n" +
							    "Enter Query to search by, Hit Search to Proceed");
			}
		});
		getContentPane().add(helpBttn);
		
		JLabel databseLbl = new JLabel("Database");
		databseLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		databseLbl.setBounds(10, 58, 80, 14);
		getContentPane().add(databseLbl);
		
		JButton dbButton = new JButton("Go!");
		dbButton.setBounds(297, 54, 89, 23);
		dbButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String database = (String)databaseCB.getSelectedItem();
				System.out.println("Ask for database " + database);
				parent.getTables(database);
			}
		});
		getContentPane().add(dbButton);
		
		databaseCB = new JComboBox<String>();
		databaseCB.setBounds(99, 54, 188, 20);
		getContentPane().add(databaseCB);
		
		JLabel lblErrorLog = new JLabel("Error Log");
		lblErrorLog.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblErrorLog.setBounds(473, 24, 80, 19);
		getContentPane().add(lblErrorLog);
		
		JButton entriesAddButton = new JButton("Add");
		entriesAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		entriesAddButton.setBounds(99, 197, 89, 23);
		getContentPane().add(entriesAddButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setBounds(198, 197, 89, 23);
		getContentPane().add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(297, 197, 89, 23);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String database = (String)databaseCB.getSelectedItem();
				String table = (String)tablesCB.getSelectedItem();
				parent.deleteTable(table, database);
			}
		});
		getContentPane().add(deleteButton);
		
		JComboBox<String> sortCB = new JComboBox<String>();
		sortCB.setBounds(99, 286, 287, 20);
		getContentPane().add(sortCB);
		
		JButton sortBttn = new JButton("Sort");
		sortBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		sortBttn.setBounds(297, 317, 89, 23);
		getContentPane().add(sortBttn);
		
		searchField = new JTextField();
		searchField.setBounds(86, 580, 201, 20);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		searchButton.setBounds(297, 579, 89, 23);
		getContentPane().add(searchButton);
		
		JScrollPane sp = new JScrollPane();
		sp.setBounds(434, 53, 150, 515);
		getContentPane().add(sp);
		
		JButton fieldsAddButton = new JButton("Add");
		fieldsAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		fieldsAddButton.setBounds(99, 317, 89, 23);
		getContentPane().add(fieldsAddButton);
		
		JButton fieldsDeleteButton = new JButton("Delete");
		fieldsDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		fieldsDeleteButton.setBounds(198, 317, 89, 23);
		getContentPane().add(fieldsDeleteButton);
		
		JLabel fieldsLbl = new JLabel("Fields");
		fieldsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		fieldsLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldsLbl.setBounds(194, 256, 109, 19);
		getContentPane().add(fieldsLbl);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 65, 21);
		getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(fileMenu);
		
		JMenu menuItem_DB = new JMenu("Database");
		fileMenu.add(menuItem_DB);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(helpMenu);
		
		JButton btnInstructions = new JButton("Details");
		btnInstructions.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisFrame,
						 "DataBase Dropdown Menu: \n\n" +
							    "Choose A DataBase to modify and press go to recieve data \n\n" +
							  "Table DropDown Menu: \n\n" +
							    "Choose a Table to Modify and press select to recieve data \n\n" +
							  "Add: \n\n" +
							    "Shows GUI for adding new Entry \n\n" +
							  "Edit: \n" +
							    "Shows GUI for editing selected Entry \n\n" +
							  "Delete: \n" +
							    "Popup box will confirm the deletion of entry \n\n" +
							  "Sort DropDown Menu: \n\n" +
							    "Choose which column to Sort By \n" +
							  "Search Field: \n\n" +
							    "Enter Query to search by, Hit Search to Proceed");
			}
		});
		helpMenu.add(btnInstructions);
		
		
	}
	
	public void setDatabases(String[] list)
	{
		databaseCB.removeAllItems();
		for (String database : list)
			databaseCB.addItem(database);
	}
	
	public void setTables(String[] list)
	{
		tablesCB.removeAllItems();
		for (String table : list)
			tablesCB.addItem(table);
		tablesCB.addItem("Create new table...");
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
