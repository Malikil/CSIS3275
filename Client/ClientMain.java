package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.table.DefaultTableModel;

import Server.Column;
import Server.Command;
import Server.DefinitelyNotArrayList;
import Server.Entry;
import Server.Message;
import Server.Table;

public class ClientMain implements Client
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private ClientGUI gui;
	private Table currentTable = null;
	private DefinitelyNotArrayList<Column> headers;
	
	public ClientMain(Socket sock, ObjectOutputStream out, ObjectInputStream in) throws IOException
	{
		objOut = out;
		objIn = in;
		gui = new ClientGUI(this);
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
					ClientMain client = new ClientMain(sock, out, in);
					client.setDatabaseList(loginAttempt.getDatabaseList());
					System.out.println("Databases set");
					client.start();
				}
				else
					login.setMessage(conf);
						
			}
			catch (IOException ex)
			{
				System.out.println("Error communicating with server:\t" + ex.getMessage());
				try
				{
					sock.close();
				}
				catch (IOException io)
				{
					/* Couldn't close socket */
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
					case ADD_COLUMN:
						break;
					case ADD_ENTRY:
						break;
					case ADD_TABLE:
						break;
					case DELETE_COLUMN:
						break;
					case DELETE_ENTRY:
						break;
					case DELETE_TABLE:
						break;
					case EDIT_ENTRY:
						break;
					case GET_TABLE:
						currentTable = received.getTable();
						setTable(currentTable);
						break;
					case TABLE_LIST:
						gui.setTables(received.getTableList());
						break;
					case MESSAGE:
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
		gui.setDatabases(list);
	}
	
	@Override
	public void createTable(String tableName)
	{
		try
		{
			objOut.writeObject(new Message(Command.ADD_TABLE, tableName));
		}
		catch (IOException ex)
		{
			// TODO Catch block
		}
	}
	
	@Override
	public void deleteTable(String tableName)
	{
		try
		{
			objOut.writeObject(new Message(Command.DELETE_TABLE, tableName));
		}
		catch (IOException ex)
		{
			// TODO Catch block
		}
	}

	@Override
	public void getTableNames(String database)
	{
		try
		{
			objOut.writeObject(new Message(Command.GET_DATABASE, database));
			System.out.println("Sent GET_DATABASE to server");
		}
		catch (IOException ex)
		{
			System.out.println("Error asking for databases from server");
		}
	}
	
	public void getTable(String tablename)
	{
		try
		{
			objOut.writeObject(new Message(Command.GET_TABLE, tablename));
			System.out.println("Sent GET_TABLE to server");
		}
		catch (IOException ex)
		{
			System.out.println("Error asking for databases from server");
		}
	}
	
	public void setTable(Table newTable)
	{
		currentTable = newTable;
		String[] colNames =  currentTable.getColumnNames();
		gui.fieldsCB.removeAllItems();
		
		String[] newColNames = new String[colNames.length+1];
		newColNames[0] = "Primary Key";
		int i = 1;
		for(String name: colNames)
		{
			newColNames[i++] = name;
			gui.fieldsCB.addItem(name);
		}
		
		Comparable[][] entryList = currentTable.getEntries();
		DefaultTableModel tableModel = new DefaultTableModel(entryList, newColNames);
		gui.setTableModel(entryList,newColNames);
	}

	@Override
	public void deleteColumn(int selectedIndex)
	{
		try
		{
			objOut.writeObject(new Message(Command.DELETE_COLUMN, selectedIndex)); //-1 because Primary Key is the first element
		}
		catch (IOException e)
		{
			// TODO Catch block
		} 
	}
	
	@Override
	public void createEntry(String[] headers) { 
		EditEntryGUI addEnt = new EditEntryGUI(headers);
		addEnt.setVisible(true);
	}
	
	@Override
	public void deleteEntry(int primaryKey)
	{
		try {
			objOut.writeObject(new Message(Command.DELETE_ENTRY, primaryKey));
		} catch (IOException e) {
		}
	}

	@Override
	public void addColumn() {
		AddFieldGUI addCol = new AddFieldGUI(this);
		addCol.setVisible(true);
		
	}

	@Override
	public void editEntry(int entryKey)
	{
		EditEntryGUI editGUI = new EditEntryGUI((Column[])headers.toArray(), );
		editGUI.setVisible(true);
	}
}
