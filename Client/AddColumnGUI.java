package Client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Server.Column;
import Server.DefinitelyNotArrayList;

import java.awt.Font;

public class AddColumnGUI extends JDialog
{
	private JLabel tableLabel;
	private JTextField tableName;
	private DefinitelyNotArrayList<JComboBox<String>> colTypes;
	private DefinitelyNotArrayList<JTextField> colNames;
	
	public String getTableName() { return tableName != null ? tableName.getText() : null; }
	public Column[] getColumns()
	{
		if (colNames != null)
		{
			Column[] cols = new Column[colNames.size()];
			for (int i = 0; i < cols.length; i++)
				cols[i] = new Column(colNames.get(i).getText(), colTypes.get(i).getSelectedIndex());
			return cols;
		}
		else
			return null;
	}
	
	/**
	 * Create the application.
	 * @param fields The names of the fields to display
	 */
	public AddColumnGUI(boolean createTable)
	{
		getContentPane().setBackground(UIManager.getColor("Tree.selectionBackground"));
		initialize();
		if (createTable)
		{
			setTitle("Create Table");
			
			tableName = new JTextField();
			tableName.setBounds(142, 17, 130, 26);
			tableName.setColumns(10);
			getContentPane().add(tableName);
			
			tableLabel = new JLabel("Table Name");
			tableLabel.setBounds(51, 22, 83, 16);
			getContentPane().add(tableLabel);
		}
		else
			setTitle("Add Field");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		JDialog thisDialog = this;
		setResizable(false);
		setBounds(100, 100, 325, 310);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		
		colNames = new DefinitelyNotArrayList<>();
		colTypes = new DefinitelyNotArrayList<>();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JButton btnCommit = new JButton("Commit");
		btnCommit.setBounds(61, 248, 90, 23);
		btnCommit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (tableName != null && tableName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(thisDialog, "Table must have a name");
					return;
				}
				else
				{
					for (JTextField col : colNames)
						if (col.getText().equals(""))
						{
							JOptionPane.showMessageDialog(thisDialog, "All fields must have a name");
							return;
						}
				}
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCommit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(173, 248, 90, 23);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableName = null;
				colNames = null;
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCancel);
		
		JButton helpBttn = new JButton("?");
		helpBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(helpBttn,
						 "Field Name: \n Enter what you wish your field to be called\n\n\n" + "Field Type: \n Select what your new field's type is");
			}
		});
		helpBttn.setFont(new Font("Tahoma", Font.BOLD, 9));
		helpBttn.setBounds(321, 149, 40, 40);
		getContentPane().add(helpBttn);
		
		JPanel tablesPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(tablesPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tablesPanel.setLayout(null);
		tablesPanel.setPreferredSize(new Dimension(0, 11));
		tablesPanel.setBounds(16, 48, 293, 188);
		
		
		JScrollPane newColScroller = new JScrollPane();
		newColScroller.setBounds(-1, -1, 293, 142);
		tablesPanel.add(newColScroller);
		
		JPanel newColPanel = new JPanel();
		newColPanel.setBorder(null);
		newColScroller.setViewportView(newColPanel);
		newColPanel.setLayout(null);
		
		String[] fieldTypes = { "String", "Number" };
		Dimension windowSize = new Dimension(275, 50);
		newColPanel.setPreferredSize(windowSize);
		
		JButton addFieldButton = new JButton("Add");
		addFieldButton.setBounds(10, 152, 89, 23);
		addFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Add Field Name
				colNames.add(new JTextField());
				colNames.get(colNames.size() - 1).setBounds(12, (colNames.size() - 1) * 32 + 13, 155, 22);
				newColPanel.add(colNames.get(colNames.size() - 1));
				// Add Field Type
				colTypes.add(new JComboBox<>(fieldTypes));
				colTypes.get(colTypes.size() - 1).setBounds(179, (colTypes.size() - 1) * 32 + 13, 85, 22);
				newColPanel.add(colTypes.get(colTypes.size() - 1));
				// Resize Panel
				//newColPanel.setPreferredSize(new Dimension(293, colNames.size() * 32 + 32));
				windowSize.setSize(275, colNames.size() * 32 + 11);
				// Repaint
				newColPanel.revalidate();
				newColPanel.repaint();
			}
		});
		tablesPanel.add(addFieldButton);
		
		JButton removeFieldButton = new JButton("Remove");
		removeFieldButton.setBounds(192, 152, 89, 23);
		removeFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (colNames.size() > 0)
				{
					// Remove Field Name
					newColPanel.remove(colNames.get(colNames.size() - 1));
					colNames.remove(colNames.size() - 1);
					// Remove Field Type
					newColPanel.remove(colTypes.get(colTypes.size() - 1));
					colTypes.remove(colTypes.size() - 1);
					// Resize Panel
					windowSize.setSize(275, colNames.size() * 32 + 11);
					// Repaint
					newColPanel.revalidate();
					newColPanel.repaint();
				}
			}
		});
		tablesPanel.add(removeFieldButton);
		
		colNames.add(new JTextField());
		colNames.get(0).setBounds(12, 13, 155, 22);
		newColPanel.add(colNames.get(0));
		
		colTypes.add(new JComboBox<>(fieldTypes));
		colTypes.get(0).setBounds(179, 13, 85, 22);
		newColPanel.add(colTypes.get(0));
		
		scrollPane.setBounds(16, 48, 293, 188);
		getContentPane().add(scrollPane);
	}
}
