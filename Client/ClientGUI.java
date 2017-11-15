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
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

public class ClientGUI extends JFrame
{
	private Client parent;
	private DefaultTableModel tableModel;
	private JTable table_1;

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
		setBounds(100, 100, 638, 486);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		tableModel = new DefaultTableModel();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(Color.PINK);
		menuBar.setBounds(0, 0, 622, 21);
		getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setBackground(new Color(153, 204, 255));
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(fileMenu);
		
		JMenu menuItem_DB = new JMenu("Database");
		fileMenu.add(menuItem_DB);
		
		JMenu mnTables = new JMenu("Tables");
		fileMenu.add(mnTables);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		JMenuItem mntmAddTable = new JMenuItem("Add Table");
		fileMenu.add(mntmAddTable);
		
		JMenuItem mntmDeleteTable = new JMenuItem("Delete Table");
		fileMenu.add(mntmDeleteTable);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setBackground(new Color(102, 204, 255));
		helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(helpMenu);
		
		JButton instructionsBttn = new JButton("Details");
		instructionsBttn.addActionListener(new ActionListener() 
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
		helpMenu.add(instructionsBttn);
		
		JTabbedPane searchTab = new JTabbedPane(JTabbedPane.TOP);
		searchTab.setBackground(Color.WHITE);
		searchTab.setBounds(0, 21, 622, 427);
		getContentPane().add(searchTab);
		
		JPanel panel = new JPanel();
		searchTab.addTab("Tables", null, panel, null);
		panel.setLayout(null);
		
		JLabel fieldLabel = new JLabel("Fields");
		fieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldLabel.setBounds(152, 11, 109, 19);
		panel.add(fieldLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(56, 41, 300, 20);
		panel.add(comboBox);
		
		JButton button = new JButton("Add");
		button.setBounds(56, 72, 89, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("Delete");
		button_1.setBounds(162, 72, 89, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Sort");
		button_2.setBounds(267, 72, 89, 23);
		panel.add(button_2);
		
		table_1 = new JTable();
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBounds(36, 106, 334, 172);
		panel.add(table_1);
		
		JButton addBttn = new JButton("Add Entry");
		addBttn.setBounds(36, 289, 99, 43);
		panel.add(addBttn);
		
		JButton deleteBttn = new JButton("Delete Entry");
		deleteBttn.setBounds(145, 289, 106, 43);
		panel.add(deleteBttn);
		
		JButton editBttn = new JButton("Edit Entry");
		editBttn.setBounds(261, 289, 109, 43);
		panel.add(editBttn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(398, 38, 150, 294);
		panel.add(scrollPane);
		
		JLabel label_1 = new JLabel("Error Log");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(439, 11, 80, 19);
		panel.add(label_1);
		
		JPanel mainPanel = new JPanel();
		searchTab.addTab("Search", null, mainPanel, null);
		
		mainPanel.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(0, 0, 617, 366);
		mainPanel.add(searchPanel);
		
		
	}
	/*
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
	*/
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
