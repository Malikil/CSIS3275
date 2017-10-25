import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Client extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private DefaultTableModel tableModel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
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
	public Client() {
		getContentPane().setFont(new Font("Tahoma", Font.ITALIC, 14));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		new JFrame();
		setBounds(100, 100, 455, 373);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel appLabel = new JLabel("Client App.");
		appLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		appLabel.setBounds(10, 11, 95, 19);
		getContentPane().add(appLabel);
		
		JLabel lblSelectTable = new JLabel("Select Table");
		lblSelectTable.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectTable.setBounds(10, 64, 80, 14);
		getContentPane().add(lblSelectTable);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(99, 61, 188, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnGo = new JButton("Go!");
		btnGo.setBounds(297, 60, 89, 23);
		getContentPane().add(btnGo);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		tablesLbl.setBounds(10, 93, 80, 14);
		getContentPane().add(tablesLbl);
		
		JButton tablesGoBttn = new JButton("Go!");
		tablesGoBttn.setBounds(297, 89, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JLabel entriesLbl = new JLabel("Entries");
		entriesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		entriesLbl.setBounds(10, 122, 80, 14);
		getContentPane().add(entriesLbl);
		
		JButton entriesGoBttn = new JButton("Go!");
		entriesGoBttn.setBounds(297, 118, 89, 23);
		getContentPane().add(entriesGoBttn);
		
		JComboBox<String> tablesCB = new JComboBox<String>();
		tablesCB.setBounds(99, 92, 188, 20);
		getContentPane().add(tablesCB);
		
		JComboBox<String> entriesCB = new JComboBox<String>();
		entriesCB.setBounds(99, 119, 188, 20);
		getContentPane().add(entriesCB);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(192, 12, 120, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel usnmeLbl= new JLabel("Username");
		usnmeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usnmeLbl.setBounds(99, 15, 80, 14);
		getContentPane().add(usnmeLbl);
		
		tableModel = new DefaultTableModel();
		table = new JTable();
		table.setModel(tableModel);
		table.setBounds(60, 170, 326, 124);
		getContentPane().add(table);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(322, 11, 89, 23);
		getContentPane().add(btnLogout);
		
		JButton button = new JButton("?");
		button.setFont(new Font("Tahoma", Font.BOLD, 9));
		button.setBounds(399, 295, 40, 40);
		getContentPane().add(button);
	}
}
