package Client;

import java.awt.EventQueue;
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
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class ClientGUI extends JFrame{
	private Client parent;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField searchField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI(new ClientMain());
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI(Client owner) {
		parent = owner;
		getContentPane().setFont(new Font("Tahoma", Font.ITALIC, 14));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame thisFrame = this;
		new JFrame();
		setBounds(100, 100, 638, 553);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel appLabel = new JLabel("Client App.");
		appLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		appLabel.setBounds(10, 11, 95, 19);
		getContentPane().add(appLabel);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		tablesLbl.setBounds(10, 87, 80, 14);
		getContentPane().add(tablesLbl);
		
		JButton tablesGoBttn = new JButton("Select");
		tablesGoBttn.setBounds(297, 83, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JLabel create_tableLbl = new JLabel("Create Table");
		create_tableLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		create_tableLbl.setHorizontalAlignment(SwingConstants.CENTER);
		create_tableLbl.setBounds(178, 136, 109, 19);
		getContentPane().add(create_tableLbl);
		
		JComboBox<String> tablesCB = new JComboBox<String>();
		tablesCB.setBounds(99, 86, 188, 20);
		getContentPane().add(tablesCB);
		
		tableModel = new DefaultTableModel();
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(tableModel);
		table.setBounds(52, 277, 334, 172);
		getContentPane().add(table);
		
		JButton button = new JButton("?");
		button.setFont(new Font("Tahoma", Font.BOLD, 9));
		button.setBounds(582, 475, 40, 40);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(thisFrame,
						 "DataBase Dropdown Menu: \n" +
							    "Choose A DataBase to modify and press go to recieve data \n" +
							  "Table DropDown Menu: \n" +
							    "Choose a Table to Modify and press select to recieve data \n" +
							  "Add: \n" +
							    "Shows GUI for adding new Entry \n" +
							  "Edit: \n" +
							    "Shows GUI for editing selected Entry \n" +
							  "Delete: \n" +
							    "Popup box will confirm the deletion of entry \n" +
							  "Sort DropDown Menu: \n" +
							    "Choose which column to Sort By \n" +
							  "Search Field: \n" +
							    "Enter Query to search by, Hit Search to Proceed");
			}
		});
		getContentPane().add(button);
		
		JLabel databseLbl = new JLabel("Database");
		databseLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		databseLbl.setBounds(10, 58, 80, 14);
		getContentPane().add(databseLbl);
		
		JButton dbButton = new JButton("Go!");
		dbButton.setBounds(297, 54, 89, 23);
		getContentPane().add(dbButton);
		
		JComboBox<String> databaseCB = new JComboBox<String>();
		databaseCB.setBounds(99, 54, 188, 20);
		
		getContentPane().add(databaseCB);
		
		JLabel lblErrorLog = new JLabel("Error Log");
		lblErrorLog.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblErrorLog.setBounds(473, 24, 80, 19);
		getContentPane().add(lblErrorLog);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(99, 166, 89, 23);
		getContentPane().add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setBounds(198, 166, 89, 23);
		getContentPane().add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(297, 166, 89, 23);
		getContentPane().add(deleteButton);
		
		JComboBox<String> sortCB = new JComboBox<String>();
		sortCB.setBounds(99, 202, 188, 20);
		getContentPane().add(sortCB);
		
		JButton btnSort = new JButton("Sort");
		btnSort.setBounds(297, 201, 89, 23);
		getContentPane().add(btnSort);
		
		searchField = new JTextField();
		searchField.setBounds(99, 234, 188, 20);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(297, 233, 89, 23);
		getContentPane().add(searchButton);
		
		JTextArea errorTextArea = new JTextArea();
		errorTextArea.setEditable(false);
		errorTextArea.setBounds(434, 53, 89, 396);
		getContentPane().add(errorTextArea);
		
		JScrollPane sp = new JScrollPane(errorTextArea);
		sp.setBounds(434, 53, 150, 396);
		getContentPane().add(sp);
	}
}
