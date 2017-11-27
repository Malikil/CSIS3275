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
import Server.DefinitelyNotArrayList;
import Server.Entry;
import Server.Message;

import javax.swing.border.LineBorder;


import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JList;

public class ClientGUI extends JFrame
{
	/**
	 * 
	 */
	private Client parent;
	private DefaultTableModel tableModel;
	private JTable table;
	private JMenu menuItem_DB;
	private JMenu mnTables;
	private JComboBox<String> fieldsCB;
	private JPanel tablesPanel;
	private JScrollPane scroller;
	private DefinitelyNotArrayList<JComboBox<String>> fieldFilter;
	private DefinitelyNotArrayList<JComboBox<String>> comparisonTypes;
	private DefinitelyNotArrayList<JTextField> valueFilter;
	private int[] tableKeys;
	private JTextField textField;

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
		setBounds(100, 100, 482, 482);
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
				parent.createTable();
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
		searchTab.setBounds(0, 21, 465, 427);
		getContentPane().add(searchTab);
		
		tableModel = new DefaultTableModel();
		
		JPanel adminPanel = new JPanel();
		searchTab.addTab("Admin", null, adminPanel, null);
		adminPanel.setLayout(null);
		
		JButton selectUserButton = new JButton("Select User");
		selectUserButton.setBounds(0, 0, 107, 23);
		adminPanel.add(selectUserButton);
		
		JComboBox<String> selectUserDropdown = new JComboBox<String>();
		selectUserDropdown.setBounds(0, 0, 338, 20);
		adminPanel.add(selectUserDropdown);
		
		JLabel userlistLabel = new JLabel("Userlist");
		userlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userlistLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		userlistLabel.setBounds(0, 0, 80, 14);
		adminPanel.add(userlistLabel);
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBounds(0, 0, 107, 23);
		adminPanel.add(addUserButton);
		
		JButton deleteUserButton = new JButton("Delete User");
		deleteUserButton.setBounds(0, 0, 107, 23);
		adminPanel.add(deleteUserButton);
		
		JList databaseList = new JList();
		JScrollPane uerListScroller = new JScrollPane(databaseList);
		uerListScroller.setBorder(new LineBorder(new Color(0, 0, 0)));
		uerListScroller.setBounds(67, 41, 338, 253);
		adminPanel.add(uerListScroller);
		
		JButton btnCreateDatabase = new JButton("Create Database");
		btnCreateDatabase.setBounds(67, 307, 97, 25);
		btnCreateDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.createDatabase();
			}
		});
		adminPanel.add(btnCreateDatabase);
		
		JButton btnDeleteDatabase = new JButton("Delete Databasee");
		btnDeleteDatabase.setBounds(241, 307, 97, 25);
		adminPanel.add(btnDeleteDatabase);
		
		tablesPanel = new JPanel();
		tablesPanel.setBackground(UIManager.getColor("Tree.selectionBackground"));
		searchTab.addTab("Tables", null, tablesPanel, null);
		tablesPanel.setLayout(null);
		
		JLabel fieldLabel = new JLabel("Fields");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		fieldLabel.setBounds(178, 13, 109, 19);
		tablesPanel.add(fieldLabel);
		
		fieldsCB = new JComboBox<String>();
		fieldsCB.setBounds(82, 43, 300, 20);
		tablesPanel.add(fieldsCB);
		table = new JTable(tableModel);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scroller = new JScrollPane(table);
		scroller.setBounds(62, 108, 334, 172);
		tablesPanel.add(scroller);
		
		JButton addFieldBttn = new JButton("Add");
		addFieldBttn.setBounds(82, 74, 89, 23);
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
		deleteFieldBttn.setBounds(188, 74, 89, 23);
		tablesPanel.add(deleteFieldBttn);
		
		JButton sortFieldBttn = new JButton("Sort");
		sortFieldBttn.setBounds(293, 74, 89, 23);
		tablesPanel.add(sortFieldBttn);
		
		JButton addBttn = new JButton("Add Entry");
		addBttn.setBounds(62, 291, 99, 43);
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
						JOptionPane.showMessageDialog(thisFrame, "Please select an entry to delete");
					else
						parent.deleteEntry(tableKeys[entryRow]);
				}
			});
		deleteBttn.setBounds(171, 291, 106, 43);
		tablesPanel.add(deleteBttn);
		
		JButton editBttn = new JButton("Edit Entry");
		editBttn.setBounds(287, 291, 109, 43);
		editBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int entryRow = table.getSelectedRow();
				parent.editEntry(entryRow);
			}
		});
		tablesPanel.add(editBttn);
		
		JPanel mainPanel = new JPanel();
		searchTab.addTab("Filter", null, mainPanel, null);
		mainPanel.setLayout(null);
		
		valueFilter = new DefinitelyNotArrayList<>();
		valueFilter.add(new JTextField());
		valueFilter.get(0).setBounds(249, 53, 146, 22);
		
		String[] comparisonStrings = {  "<", "<=", "=", ">=", ">" };
		comparisonTypes = new DefinitelyNotArrayList<>();
		comparisonTypes.add(new JComboBox/*<>*/(comparisonStrings));
		comparisonTypes.get(0).setBounds(186, 53, 51, 22);
		
		fieldFilter = new DefinitelyNotArrayList<>();
		fieldFilter.add(new JComboBox/*<>*/(parent.getColumnNames()));
		fieldFilter.get(0).setBounds(71, 53, 103, 22);
		
		JPanel filterButtonPanel = new JPanel();
		filterButtonPanel.setBounds(0, 0, 461, 398);
		mainPanel.add(filterButtonPanel);
		filterButtonPanel.setLayout(null);
		JButton btnRemoveFilter = new JButton("Remove");
		btnRemoveFilter.setBounds(184, 350, 91, 23);
		filterButtonPanel.add(btnRemoveFilter);
		JButton btnApplyFilter = new JButton("Apply");
		btnApplyFilter.setBounds(287, 350, 89, 23);
		filterButtonPanel.add(btnApplyFilter);
		
		JScrollPane searchTabScroll = new JScrollPane();
		searchTabScroll.setBounds(0, 0, 461, 335);
		filterButtonPanel.add(searchTabScroll);
		searchTabScroll.setViewportBorder(null);
		searchTabScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel searchPanel = new JPanel();
		searchTabScroll.setViewportView(searchPanel);
		searchPanel.setBackground(UIManager.getColor("Tree.selectionBackground"));
		searchPanel.setLayout(null);
		
		JLabel searchItemLbl = new JLabel("Value");
		searchItemLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchItemLbl.setBounds(301, 26, 46, 14);
		searchPanel.add(searchItemLbl);
		
		JLabel searchFieldLbl = new JLabel("Field ");
		searchFieldLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchFieldLbl.setBounds(102, 26, 46, 14);
		searchPanel.add(searchFieldLbl);
		searchPanel.add(valueFilter.get(0));
		searchPanel.add(comparisonTypes.get(0));
		searchPanel.add(fieldFilter.get(0));
		
		JButton btnAddFilter = new JButton("Add");
		btnAddFilter.setBounds(81, 350, 91, 23);
		filterButtonPanel.add(btnAddFilter);
		btnAddFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Add filter row
				fieldFilter.add(new JComboBox<>(parent.getColumnNames()));
				fieldFilter.get(fieldFilter.size() - 1).setBounds(71, fieldFilter.size() * 27 + 26, 103, 22);
				searchPanel.add(fieldFilter.get(fieldFilter.size() - 1));
				
				comparisonTypes.add(new JComboBox<>(comparisonStrings));
				comparisonTypes.get(comparisonTypes.size() - 1).setBounds(186, comparisonTypes.size() * 27 + 26, 51, 22);
				searchPanel.add(comparisonTypes.get(comparisonTypes.size() - 1));
				
				valueFilter.add(new JTextField());
				valueFilter.get(valueFilter.size() - 1).setBounds(249, valueFilter.size() * 27 + 26, 146, 22);
				searchPanel.add(valueFilter.get(valueFilter.size() - 1));
				
				// Set preferred size of panel
				searchPanel.setPreferredSize(new Dimension(0, valueFilter.size() * 27 + 60));
				searchPanel.revalidate();
				searchPanel.repaint();
			}
		});
		btnApplyFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] values = new String[valueFilter.size()],
						 comps = new String[comparisonTypes.size()];
				int[] fields = new int[fieldFilter.size()];
				
				for (int i = 0; i < values.length; i++)
				{
					values[i] = valueFilter.get(i).getText();
					comps[i] = (String)comparisonTypes.get(i).getSelectedItem();
					fields[i] = fieldFilter.get(i).getSelectedIndex();
				}
				try
				{
					parent.applySearch(values, comps, fields);
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(thisFrame, "Error parsing numbers\n" + ex.getMessage());
				}
			}
		});
		btnRemoveFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (valueFilter.size() > 0)
				{
					// Remove row
					searchPanel.remove(fieldFilter.get(fieldFilter.size() - 1));
					fieldFilter.remove(fieldFilter.size() - 1);
					
					searchPanel.remove(valueFilter.get(valueFilter.size() - 1));
					valueFilter.remove(valueFilter.size() - 1);
					
					searchPanel.remove(comparisonTypes.get(comparisonTypes.size() - 1));
					comparisonTypes.remove(comparisonTypes.size() - 1);
					
					// Repaint
					searchPanel.setPreferredSize(new Dimension(0, valueFilter.size() * 27 + 60));
					searchPanel.revalidate();
					searchPanel.repaint();
				}
			}
		});
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
				}
			});
			mnTables.add(newTable);
		}
	}
	
	public void addTableName(String tableName)
	{
			JMenuItem newTable = new JMenuItem(tableName);
			newTable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					parent.getTable(tableName);
				}
			});
			mnTables.add(newTable);	
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
		tableKeys = new int[data.length];
		for (int i = 0; i < data.length; i++)
		{
			tableModel.addRow(data[i].getData());
			tableKeys[i] = data[i].getKey();
		}

	}
	
	public Entry getSelectedEntry()
	{
		int row = table.getSelectedRow();
		Comparable[] data = new Comparable[table.getModel().getColumnCount()];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = (Comparable)table.getModel().getValueAt(row, i); // TODO Unchecked
		}
		return new Entry(tableKeys[row], data);
	}

	
}
