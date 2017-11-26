package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import Server.Column;
import Server.Command;
import Server.Entry;
import Server.Message;

import javax.swing.border.LineBorder;


import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class ClientGUI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4439578214704065287L;
	private Client parent;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField itemField;
	private JTextField fieldField;
	private JMenu menuItem_DB;
	private JMenu mnTables;
	private JComboBox<String> fieldsCB;
	private JPanel tablesPanel;
	private JScrollPane scroller;
	private JTextArea chatArea;
	

	/**
	 * Create the application.
	 */
	public ClientGUI(Client owner) 
	{
		getContentPane().setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
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
		setBounds(100, 100, 638, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(new Color(204, 204, 255));
		menuBar.setBounds(0, 0, 622, 21);
		getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setBackground(new Color(153, 204, 255));
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(fileMenu);
		
		menuItem_DB = new JMenu("Database");
		fileMenu.add(menuItem_DB);
		
		mnTables = new JMenu("Tables");
		fileMenu.add(mnTables);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		JMenuItem mntmAddTable = new JMenuItem("Add Table");
		fileMenu.add(mntmAddTable);
		mntmAddTable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				CreateTableGUI a = new CreateTableGUI(parent);
				a.setVisible(true);
			}	
		});
		
		JMenuItem mntmDeleteTable = new JMenuItem("Delete Table");
		fileMenu.add(mntmDeleteTable);
		mntmDeleteTable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				parent.deleteCurrentTable();
			}
		});
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setBackground(new Color(102, 204, 255));
		helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(helpMenu);
		
		JMenuItem details = new JMenuItem("Details");
		details.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisFrame,
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
		
		JTabbedPane searchTab = new JTabbedPane(JTabbedPane.TOP);
		searchTab.setBackground(UIManager.getColor("Tree.selectionBackground"));
		searchTab.setBounds(0, 21, 622, 427);
		getContentPane().add(searchTab);
		
		tablesPanel = new JPanel();
		tablesPanel.setBackground(UIManager.getColor("Tree.selectionBackground"));
		searchTab.addTab("Tables", null, tablesPanel, null);
		tablesPanel.setLayout(null);
		
		JLabel fieldLabel = new JLabel("Fields");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		fieldLabel.setBounds(152, 11, 109, 19);
		tablesPanel.add(fieldLabel);
		
		fieldsCB = new JComboBox<String>();
		fieldsCB.setBounds(56, 41, 300, 20);
		tablesPanel.add(fieldsCB);
		
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scroller = new JScrollPane(table);
		scroller.setBounds(36, 106, 334, 172);
		tablesPanel.add(scroller);
		
		JButton addFieldBttn = new JButton("Add");
		addFieldBttn.setBounds(56, 72, 89, 23);
		addFieldBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.addColumn();
			}
		});
		tablesPanel.add(addFieldBttn);
		
		JButton deleteFieldBttn = new JButton("Delete");
		deleteFieldBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.deleteColumn(fieldsCB.getSelectedIndex());
			}
		});
		deleteFieldBttn.setBounds(162, 72, 89, 23);
		tablesPanel.add(deleteFieldBttn);
		
		JButton sortFieldBttn = new JButton("Sort");
		sortFieldBttn.setBounds(267, 72, 89, 23);
		tablesPanel.add(sortFieldBttn);
		
		JButton addBttn = new JButton("Add Entry");
		addBttn.setBounds(36, 289, 99, 43);
		addBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int i = fieldsCB.getItemCount();
				String[] headers = new String[i];
				for(int j = 0;j<headers.length;j++)
					headers[j] = fieldsCB.getItemAt(j);
				parent.createEntry(headers);
			}
		});
		tablesPanel.add(addBttn);
		
		JButton deleteBttn = new JButton("Delete Entry");
		deleteBttn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					int entryRow = table.getSelectedRow();
					if (entryRow == -1)
					{
						chatArea.append("SELECT SOMETHING DUMBASS");
					}
					else
					{
						int entryKey = Integer.parseInt((String) table.getModel().getValueAt(entryRow, 0));
						parent.deleteEntry(entryKey);
					}
				}
			});
		deleteBttn.setBounds(145, 289, 106, 43);
		tablesPanel.add(deleteBttn);
		
		JButton editBttn = new JButton("Edit Entry");
		editBttn.setBounds(261, 289, 109, 43);
		editBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int entryRow = table.getSelectedRow();
				parent.editEntry(entryRow);
			}
		});
		tablesPanel.add(editBttn);
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setBounds(398, 38, 150, 294);
		tablesPanel.add(scrollPane);
		
		JLabel label_1 = new JLabel("Error Log");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(439, 11, 80, 19);
		tablesPanel.add(label_1);
		
		JPanel mainPanel = new JPanel();
		searchTab.addTab("Search", null, mainPanel, null);
		
		mainPanel.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(UIManager.getColor("Tree.selectionBackground"));
		searchPanel.setBounds(0, 0, 617, 399);
		mainPanel.add(searchPanel);
		searchPanel.setLayout(null);
		itemField = new JTextField();
		itemField.setBackground(SystemColor.control);
		itemField.setBounds(211, 64, 227, 20);
		searchPanel.add(itemField);
		itemField.setColumns(10);
		
		fieldField = new JTextField();
		fieldField.setBackground(SystemColor.control);
		fieldField.setBounds(211, 99, 227, 20);
		searchPanel.add(fieldField);
		fieldField.setColumns(10);
		
		JLabel searchItemLbl = new JLabel("Item");
		searchItemLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchItemLbl.setBounds(155, 67, 46, 14);
		searchPanel.add(searchItemLbl);
		 		
		JLabel searchFieldLbl = new JLabel("Field ");
		searchFieldLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchFieldLbl.setBounds(155, 102, 46, 14);
		searchPanel.add(searchFieldLbl);

		JButton searchBttn = new JButton("Search");
		searchBttn.setBounds(349, 140, 89, 23);
		searchPanel.add(searchBttn);
	}
	
	public void setDatabases(String[] list)
	{
		menuItem_DB.removeAll();
		for (String dbname : list)
		{
			JMenuItem newDB = new JMenuItem(dbname);
			newDB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					parent.getTableNames(dbname);
				}
			});
			menuItem_DB.add(newDB);
		}
	}
	
	public void setTableList(String[] list)
	{
		mnTables.removeAll();
		for (String table : list)
		{
			JMenuItem newTable = new JMenuItem(table);
			newTable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					parent.getTable(table);
					parent.setCurrentTableName(table);
				}
			});
			mnTables.add(newTable);
		}
	}
	
	public void setFieldList(String[] fields)
	{
		fieldsCB.removeAllItems();
		for (String s : fields)
			fieldsCB.addItem(s);
	}
	
	public void setTable(Entry[] data, String[] columns)
	{
		tableModel.setRowCount(0);
		tableModel.setColumnIdentifiers(columns);
		for (Entry e : data)
			tableModel.addRow(e.getData());
	}
}
