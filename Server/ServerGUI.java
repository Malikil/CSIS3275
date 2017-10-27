package Server;

import java.awt.EventQueue;
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
import javax.swing.JScrollPane;

public class ServerGUI extends JFrame {
	private JTextField txtUserList;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
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
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new JFrame();
		setBounds(100, 100, 622, 485);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblServerApp = new JLabel("Server App.");
		lblServerApp.setBounds(10, 11, 95, 35);
		lblServerApp.setFont(new Font("Tahoma", Font.BOLD, 16));
		getContentPane().add(lblServerApp);
		
		JLabel selectLbl = new JLabel("Select Table");
		selectLbl.setBounds(31, 120, 80, 14);
		selectLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(selectLbl);
		
		JLabel entriesLbl = new JLabel("Entries");
		entriesLbl.setBounds(31, 178, 80, 14);
		entriesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(entriesLbl);
		
		JButton usrListGoBttn = new JButton("Go!");
		usrListGoBttn.setBounds(318, 116, 89, 23);
		getContentPane().add(usrListGoBttn);
		
		JButton dtbseGoBttn = new JButton("Go!");
		dtbseGoBttn.setBounds(318, 174, 89, 23);
		getContentPane().add(dtbseGoBttn);
		
		JComboBox<String> usrListCB = new JComboBox<String>();
		usrListCB.setBounds(120, 175, 188, 20);
		getContentPane().add(usrListCB);
		
		JComboBox<String> dtbseCB = new JComboBox<String>();
		dtbseCB.setBounds(120, 148, 188, 20);
		getContentPane().add(dtbseCB);
		
		JButton selectGobttn = new JButton("Go!");
		selectGobttn.setBounds(318, 145, 89, 23);
		getContentPane().add(selectGobttn);
		
		textField = new JTextField();
		textField.setBounds(120, 117, 188, 20);
		textField.setEditable(false);
		textField.setColumns(10);
		getContentPane().add(textField);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setBounds(31, 149, 80, 14);
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(tablesLbl);
		
		JLabel usrListLbl = new JLabel("Userlist");
		usrListLbl.setBounds(31, 61, 80, 14);
		usrListLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(usrListLbl);
		
		JButton tablesGoBttn = new JButton("Go!");
		tablesGoBttn.setBounds(318, 57, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JComboBox<String> tablesCB = new JComboBox<String>();
		tablesCB.setBounds(120, 60, 188, 20);
		getContentPane().add(tablesCB);
		
		JButton entriesGoBttn = new JButton("Go!");
		entriesGoBttn.setBounds(318, 86, 89, 23);
		getContentPane().add(entriesGoBttn);
		
		JComboBox<String> entriesCB = new JComboBox<String>();
		entriesCB.setBounds(120, 87, 188, 20);
		getContentPane().add(entriesCB);
		
		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setBounds(31, 90, 80, 14);
		lblDatabase.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblDatabase);
		
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
