package Client;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Server.Column;
import Server.Table;

public class CreateTableGUI extends JDialog{

	JPanel panel;
	JTextField tableName = new JTextField();
	JLabel tableLabel = new JLabel("Enter table name");
	JButton btnCommit = new JButton("Create Table");
	JButton btnCancel = new JButton("Cancel");
	Client parent;
	JTextField column;
	JComboBox<String> columnType;
	
	public CreateTableGUI(Client client)
	{
		parent = client;
		initialize();
	}

	private void initialize() {
		JDialog thisDialog = this;
		setResizable(false);
		setBounds(100, 100, 325, 282);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setLayout(null);
		tableLabel.setBounds(130,20,150,30);
		tableName.setBounds(90,50,150,30);
		getContentPane().add(tableLabel);
		getContentPane().add(tableName);
		
		column = new JTextField(30);
		column.setBounds(90,110,150,30);
		JLabel columnLabel = new JLabel("Field name");
		columnLabel.setBounds(130,80,150,30);
		JLabel typeLabel = new JLabel("Field type");
		typeLabel.setBounds(130,140,150,30);
		columnType = new JComboBox<String>();
		columnType.addItem("String");
		columnType.addItem("Integer");
		columnType.addItem("Double");
		columnType.addItem("Date");
		columnType.setBounds(90, 170, 150, 30);
		getContentPane().add(column);
		getContentPane().add(columnLabel);
		getContentPane().add(typeLabel);
		getContentPane().add(columnType);
		
		btnCommit.setBounds(20, 221, 90, 23);
		btnCommit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.createTable(tableName.getText());
				parent.addColumn(column.getText(), columnType.getSelectedIndex());
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
