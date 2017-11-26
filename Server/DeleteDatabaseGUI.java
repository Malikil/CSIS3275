package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

public class DeleteDatabaseGUI {

	private JFrame frmDeleteDatabase;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteDatabaseGUI window = new DeleteDatabaseGUI();
					window.frmDeleteDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DeleteDatabaseGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeleteDatabase = new JFrame();
		frmDeleteDatabase.setTitle("Delete Database");
		frmDeleteDatabase.getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		frmDeleteDatabase.setBounds(100, 100, 348, 326);
		frmDeleteDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDeleteDatabase.getContentPane().setLayout(null);
		
		JLabel dbListLbl = new JLabel("Database List");
		dbListLbl.setBounds(123, 22, 100, 16);
		frmDeleteDatabase.getContentPane().add(dbListLbl);
		
		JList<?> dbList = new JList<>();
		JScrollPane sp = new JScrollPane(dbList);
		sp.setBounds(86, 50, 169, 157);
		frmDeleteDatabase.getContentPane().add(sp);
		
		JButton deleteDBBttn = new JButton("Delete Database");
		deleteDBBttn.setBounds(110, 219, 128, 29);
		frmDeleteDatabase.getContentPane().add(deleteDBBttn);
		
		JButton helpBttn = new JButton("?");
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(302, 258, 40, 40);
		helpBttn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(helpBttn,
					"Database List: \n  Select certain Databases you wish to delete\n\n" +
					"	Press 'Delete Database' to delete selected databases");
			}
		});
		frmDeleteDatabase.getContentPane().add(helpBttn);
	}

}
