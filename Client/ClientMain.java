package Client;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Server.AVLTree;
import Server.Column;
import Server.Command;
import Server.Entry;
import Server.Message;
import Server.Table;
import Server.User;

public class ClientMain implements Client
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private ClientGUI gui;
	private Table currentTable = null;
	private String[] databaseList;
	AVLTree<Entry> newTree;
	
	public ClientMain(Socket sock, ObjectOutputStream out, ObjectInputStream in, boolean admin) throws IOException
	{
		objOut = out;
		objIn = in;
		gui = new ClientGUI(this, admin);
	}
	
	public static void main(String[] args)
	{
		LoginGUI login = new LoginGUI();
		Socket sock = null;
		ObjectOutputStream out;
		ObjectInputStream in;
		
		while (true)
		{
			login.setVisible(true);
			if (login.isCancelled()) return;
			try
			{
				login.showPopup("Attempting to Connect to Server at specified IP. Press OK to continue.\n"
						+ "If Client does not immediately open after pressing OK, "
						+ "server is not responding at specified IP");
				sock = new Socket(login.getEnteredIP(), 8001); System.out.println("Opened socket");
				out = new ObjectOutputStream(sock.getOutputStream()); System.out.println("Got output stream");
				in = new ObjectInputStream(sock.getInputStream()); System.out.println("Got input stream");
				Message loginAttempt = new Message(Command.LOGIN,
						new String[] { login.getEnteredUser(), login.getEnteredPass() });
				out.writeObject(loginAttempt); System.out.println("Sent user/pass");
				loginAttempt = (Message)in.readObject();
				Command conf = loginAttempt.getCommandType(); System.out.println("Response received");
				System.out.println("Server responded with " + conf.toString()); // TODO DEBUG
				if (conf == Command.CONNECTION_SUCCESS)
				{
					User user = loginAttempt.getUser();
					ClientMain client = new ClientMain(sock, out, in, user.isAdmin());
					client.setGUITitle(user);
					client.setDatabaseList(user.getDatabases());
					System.out.println("Databases set");
					client.start();
				}
				else
					login.setMessage(conf);
						
			}
			catch (IOException ex)
			{
				System.out.println("Error communicating with server:\t" + ex.getMessage());
				login.showPopup("Could not connect to Server at specified IP. Please try again with the correct server IP.");
				try
				{
					if(sock!=null)
						sock.close();
				}
				catch (IOException io)
				{
					io.printStackTrace();
				}
			}
			catch (ClassNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void start()
	{
		gui.setVisible(true);
		try
		{
				while (true)
				{
					Message received = (Message)objIn.readObject();
					switch (received.getCommandType())
					{
					case ADD_COLUMNS:
						Column[] columnList = received.getColumns();
						for(int i = 0 ; i < columnList.length ; i++)
							currentTable.addColumn(columnList[i]);
						setTable(currentTable);
						break;
					case ADD_ENTRY:
						currentTable.addEntry(received.getEntry());
						setTable(currentTable);
						break;
					case ADD_TABLE:
						gui.addTableName(received.getTableName());
						break;
					case DELETE_COLUMN:
						currentTable.removeColumn(received.getColumnIndex());
						setTable(currentTable);
						break;
					case DELETE_ENTRY:
						currentTable.removeEntry(received.getKey());
						setTable(currentTable);
						break;
					case EDIT_ENTRY:
						currentTable.editEntry(received.getEntry());
						setTable(currentTable);
						break;
					case GET_ACTUAL_TABLE:
						setTable(received.getTable());
						break;
					case GET_TABLE_NAMES:
						gui.setTableList(received.getTableNames());
						break;
					case DATABASE_LIST:
						setDatabaseList(received.getDatabaseList());
						break;
					default:
						throw new IOException("Unexpected server command");
					}
				}
			
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex) { /* Skip to finally */ }
		finally
		{
			try
			{
				objIn.close();
				objOut.close();
			}
			catch (IOException ex) { /* Couldn't close streams */ }
		}
	}
	
	@Override
	public void setDatabaseList(String[] list)
	{
		databaseList = list;
		gui.setDatabases(list);
	}
	
	@Override
	public void createTable()
	{
	  	AddColumnGUI tableGUI = new AddColumnGUI(true); //true for create table, false for create column
	  	tableGUI.setVisible(true);
	  	if (tableGUI.getTableName() != null)
			try
			{
				objOut.writeObject(new Message(Command.ADD_TABLE, tableGUI.getTableName()));
				objOut.writeObject(new Message(Command.ADD_COLUMNS, tableGUI.getColumns()));
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
				// TODO Catch block
			}
	}
	
	@Override
	public void deleteCurrentTable()
	{
		try
		{
			objOut.writeObject(new Message(Command.DELETE_TABLE, null));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void getTableNames(String database)
	{
		try
		{
			objOut.writeObject(new Message(Command.GET_TABLE_NAMES, database));
			System.out.println("Sent GET_DATABASE to server");
		}
		catch (IOException ex)
		{
			System.out.println("Error asking for tables from server");
		}
	}
	
	@Override
	public void getTable(String tableName)
	{
		try
		{
			objOut.writeObject(new Message(Command.GET_ACTUAL_TABLE, tableName));
			System.out.println("Sent GET_TABLE to server");
		}
		catch (IOException ex)
		{
			System.out.println("Error asking for table from server");
		}
	}
	
	public void setTable(Table newTable)
	{
		if(newTable == null)
		{
			currentTable = null;
			gui.setFieldList(new String[0]);
			gui.setTable(new Entry[0],null);
		}
		else
		{
			currentTable = newTable;
			String[] colNames =  currentTable.getColumnNames();
			gui.setFieldList(colNames);
			gui.setTable(currentTable.asArray(),colNames);
			newTree = currentTable.getTree();
			gui.resetFilters();
		}
	}

	@Override
	public void deleteColumn(int selectedIndex)
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
		}
		else if (selectedIndex >= 0)
			try
			{
				objOut.writeObject(new Message(Command.DELETE_COLUMN, selectedIndex));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
	}
	
	@Override
	public void createEntry()
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		EditEntryGUI addEnt = new EditEntryGUI(currentTable.getColumnNames());
		addEnt.setVisible(true);
		
		String[] newData = addEnt.getData();
		if (newData != null)
		{
			Column[] cols = currentTable.getColumns();
			Comparable[] newEntry = new Comparable[newData.length];
			try
			{
				for (int i = 0; i < newEntry.length; i++)
					if (cols[i].getType() == Column.NUMBER)
						newEntry[i] = Double.parseDouble(newData[i]);
					else
						newEntry[i] = newData[i];
				
				objOut.writeObject(new Message(Command.ADD_ENTRY, newEntry));
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(gui, "Error converting value to Number\n" + ex.getMessage());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void deleteEntry(int key)
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		try
		{
			objOut.writeObject(new Message(Command.DELETE_ENTRY, key));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void addColumn() 
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		AddColumnGUI newCols = new AddColumnGUI(false);
		newCols.setVisible(true);
		Column[] addedColumns = newCols.getColumns();
		if (addedColumns != null)
	 		try
	 		{
	 			objOut.writeObject(new Message(Command.ADD_COLUMNS, addedColumns));
	 		}
	 		catch (IOException ex)
	 		{
	 			ex.printStackTrace();
	 		}
	}

	@Override
	public void editEntry()
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		Entry editEntry = gui.getSelectedEntry();
		EditEntryGUI editGUI = new EditEntryGUI(currentTable.getColumnNames(), editEntry);
		editGUI.setVisible(true);
		// Data validation
		String[] newValues = editGUI.getData();
		if (newValues != null)
		{
			Column[] cols = currentTable.getColumns();
			try
			{
				for (int i = 0; i < cols.length; i++)
				{
					if (cols[i].getType() == Column.NUMBER)
						editEntry.setfield(i, Double.parseDouble(newValues[i]));
					else
						editEntry.setfield(i, newValues[i]);
				}
				
				objOut.writeObject(new Message(Command.EDIT_ENTRY, editEntry));
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(gui, "Error converting value to Number\n" + e.getMessage());
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public String[] getColumnNames()
	{
		if (currentTable != null)
			return currentTable.getColumnNames();
		else
			return new String[0];
	}

	@Override
	public void applySearch(String[] values, String[] comparisons, int[] fields)
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		Comparable[] filterValues = new Comparable[values.length];
		Column[] cols = currentTable.getColumns();
		for (int i = 0; i < values.length; i++)
		{
			switch (cols[fields[i]].getType())
			{
			case Column.STRING:
				filterValues[i] = values[i];
				break;
			case Column.NUMBER:
				filterValues[i] = Double.parseDouble(values[i]);
				break;
			}
		}
		newTree = currentTable.getTree();
		for (int i = 0; i < values.length; i++)
		{
			Entry.setComparer(fields[i]);
			newTree = newTree.reconstructTree();
			// Create dud entry
			Comparable[] tempDat = new Comparable[cols.length];
			tempDat[fields[i]] = filterValues[i];
			newTree = newTree.getRange(new Entry(-1, tempDat), comparisons[i]); // TODO
		}
		// Display table
		gui.setTable(newTree.toArray(new Entry[newTree.size()]), currentTable.getColumnNames());
	}

	@Override
	public void createDatabase()
	{	
		try
		{
			String database = JOptionPane.showInputDialog("Create Database");
			if (database != null && !database.equals(""))
				objOut.writeObject(new Message(Command.ADD_DATABASE, database)); //sending String
			else if(database == null)
				return;
			else
				JOptionPane.showMessageDialog(gui, "Databases must have names");
		}
		catch (HeadlessException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void addUser()
	{
		try
		{
			AddUserGUI userGUI = new AddUserGUI(databaseList);
			userGUI.setVisible(true);
			User newUser = userGUI.getUser();
			if (newUser != null)
			{
				objOut.writeObject(new Message(Command.ADD_USER, newUser));
				if(newUser.isAdmin())
					gui.showPopup("Admin clients will be able to access all DBs. Click OK to continue.");
			}
		}
		catch (HeadlessException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void sort(int field)
	{
		if(currentTable == null)
		{
			gui.showPopup("Please select a table first.");
			return;
		}
		Entry.setComparer(field);
		newTree = newTree.reconstructTree();
		gui.setTable(newTree.toArray(new Entry[newTree.size()]), currentTable.getColumnNames());
	}

	@Override
	public void quit()
	{
		try
		{
			objOut.writeObject(new Message(Command.LOGOFF, null));
		}
		catch (IOException writeError)
		{
			System.out.println("Error writing LOGOFF\n" + writeError.getMessage());
		}
		finally
		{
			try
			{
				if(objIn != null)
					objIn.close();
				if(objOut!= null)
					objOut.close();
			}
			catch (IOException ex)
			{
				System.out.println("Couldn't close sockets");
			}
			finally
			{
				System.exit(0);
			}
		}
	}
	
	private void setGUITitle(User user)
	{
		gui.setTitle((user.isAdmin() ? "Admin" : "Client") +
						" GUI - " + user.getUsername());
	}
}
