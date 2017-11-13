package Client;

import Server.Entry;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

public class EditEntryGUI extends JDialog
{
	JPanel panel;
	Entry edit;

	/**
	 * Create the application.
	 * @param fields The names of the fields to display
	 */
	public EditEntryGUI(String[] fields, Entry entry)
	{
		initialize();
		JTextField[] newData = new JTextField[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			JLabel label = new JLabel(fields[i]);
			label.setBounds(10, i * 25 + 11, 90, 14);
			panel.add(label);
			newData[i] = new JTextField(entry.getField(i).toString());
			newData[i].setBounds(110, i * 25 + 8, 120, 20);
			panel.add(newData[i]);
		}
		panel.setPreferredSize(new Dimension(0, fields.length * 25 + 11));
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
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setLayout(null);
		
		scrollPane.setBounds(10, 10, 300, 200);
		getContentPane().add(scrollPane);
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.setBounds(20, 221, 90, 23);
		btnCommit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
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
