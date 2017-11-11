package temp;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
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
import javax.swing.JScrollPane;

public class ServerGUI extends JFrame
{
	private JTextField textField;
	private JTable tableArea;
	private DefaultTableModel tableModel;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ServerGUI window = new ServerGUI();
					window.setVisible(true);
				}
        catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param server The server to be notified of events
	 */
	public ServerGUI()
	{
		setTitle("Server Application");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		new JFrame();
		setBounds(100, 100, 636, 749);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel usrListLbl = new JLabel("Userlist");
		usrListLbl.setBounds(31, 61, 80, 14);
		usrListLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(usrListLbl);
		
		JComboBox<String> entriesCB = new JComboBox<String>();
		entriesCB.setBounds(120, 345, 188, 20);
		getContentPane().add(entriesCB);
		
		JButton usrListGoBttn = new JButton("Select User");
		usrListGoBttn.setBounds(318, 57, 89, 23);
		getContentPane().add(usrListGoBttn);
		
		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setBounds(31, 290, 80, 14);
		lblDatabase.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblDatabase);
		
		JComboBox<String> tablesCB = new JComboBox<String>();
		tablesCB.setBounds(120, 318, 188, 20);
		getContentPane().add(tablesCB);
		
		JButton dtbseSelectBttn = new JButton("Select");
		dtbseSelectBttn.setBounds(318, 286, 89, 23);
		getContentPane().add(dtbseSelectBttn);
		
		JLabel tablesLbl = new JLabel("Tables");
		tablesLbl.setBounds(31, 319, 80, 14);
		tablesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(tablesLbl);
		
		JComboBox<String> userlistCB = new JComboBox<String>();
		userlistCB.setBounds(120, 60, 188, 20);
		getContentPane().add(userlistCB);
		
		JButton tablesGoBttn = new JButton("Go!");
		tablesGoBttn.setBounds(318, 315, 89, 23);
		getContentPane().add(tablesGoBttn);
		
		JLabel entriesLbl = new JLabel("Entries");
		entriesLbl.setBounds(31, 348, 80, 14);
		entriesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(entriesLbl);
		
		JComboBox<String> dbCB = new JComboBox<String>();
		dbCB.setBounds(120, 287, 188, 20);
		getContentPane().add(dbCB);
		
		JButton entriesChangeBttn = new JButton("Change");
		entriesChangeBttn.setBounds(318, 344, 89, 23);
		getContentPane().add(entriesChangeBttn);
		
		tableModel = new DefaultTableModel();
		tableArea = new JTable();
		tableArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableArea.setBounds(69, 396, 338, 264);
		tableArea.setModel(tableModel);
		getContentPane().add(tableArea);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setBounds(580, 671, 40, 40);
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		getContentPane().add(helpBttn);
		
		JList<String> userlistList = new JList<String>();
		userlistList.setEnabled(false);
		userlistList.setBorder(new LineBorder(new Color(0, 0, 0)));
		userlistList.setBounds(69, 97, 338, 153);
		getContentPane().add(userlistList);
		
		JLabel errorLabel = new JLabel("Error Log");
		errorLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		errorLabel.setBounds(437, 28, 80, 19);
		getContentPane().add(errorLabel);
		
		JTextArea serverErrorTextArea = new JTextArea();
		serverErrorTextArea.setEditable(false);
		serverErrorTextArea.setBounds(432, 61, 161, 599);
		getContentPane().add(serverErrorTextArea);
		
		JScrollPane sp = new JScrollPane(serverErrorTextArea);
		sp.setBounds(434, 53, 150, 605);
		getContentPane().add(sp);
	}
}