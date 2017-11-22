package Client;

import Server.Column;
import Server.Command;
import Server.Entry;
import Server.Message;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

public class AddFieldGUI extends JDialog
{
	JPanel panel;
	Client parent;
	JTextField column;
	JComboBox<String> columnType;

	/**
	 * Create the application.
	 * @param fields The names of the fields to display
	 */

	
	public AddFieldGUI(Client client)
	{
		parent = client;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		JDialog thisDialog = this;
		setResizable(false);
		setBounds(100, 100, 325, 282);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setLayout(null);
		column = new JTextField(30);
		column.setBounds(90,50,150,30);
		JLabel columnLabel = new JLabel("Field name");
		columnLabel.setBounds(130,20,150,30);
		JLabel typeLabel = new JLabel("Field type");
		typeLabel.setBounds(130,80,150,30);
		columnType = new JComboBox<String>();
		columnType.addItem("String");
		columnType.addItem("Integer");
		columnType.addItem("Double");
		columnType.addItem("Date");
		columnType.setBounds(90, 110, 150, 30);
		getContentPane().add(column);
		getContentPane().add(columnLabel);
		getContentPane().add(typeLabel);
		getContentPane().add(columnType);
		JButton btnCommit = new JButton("Commit");
		btnCommit.setBounds(20, 221, 90, 23);
		btnCommit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.writeMessage(new Message(Command.ADD_COLUMN, new Column(column.getText(), columnType.getSelectedIndex())));
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCommit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(210, 221, 90, 23);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCancel);
	}
}
