package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.table.DefaultTableModel;

import Server.Command;
import Server.Entry;
import Server.Message;
import Server.Table;

public class ClientMain implements Client
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private ClientGUI gui;
	private static Message received;
	private Table currentTable = null;
	
	public ClientMain(Socket sock, ObjectOutputStream out, ObjectInputStream in) throws IOException
	{
		objOut = out;
		objIn = in;
		gui = new ClientGUI(this);
	}
	
	public static void main(String[] args)
	{
		LoginGUI login = new LoginGUI();
		Socket sock;
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
				received = (Message)in.readObject();
				Command conf = received.getCommandType(); System.out.println("Response received");
				System.out.println("Server responded with " + conf.toString()); // TODO DEBUG
				if (conf == Command.CONNECTION_SUCCESS)		
					break;
				else
					login.setMessage(conf);
						
			}
			catch (IOException ex)
			{
				System.out.println("Error communicating with server:\t" + ex.getMessage());
			}
			catch (ClassNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
		
		try
		{
			new ClientMain(sock, out, in).start();
		}
		catch (IOException ex)
		{
			try	{ sock.close(); }
			catch (IOException io) { /* Couldn't close socket */ }
		}
	}

	public void start()
	{
		gui.setVisible(true);
		gui.setDatabases(received.getDatabaseList());
		System.out.println("Databases set");
		try
		{
				while (true)
				{
					received = (Message)objIn.readObject();
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
	public void sendEdits(Entry e)
	{
		try
		{
			Message send = new Message(Command.EDIT_ENTRY, e);
			objOut.writeObject(send);
		}
		catch (IOException ex)
		{
			// I'm starting to get tired of writing catch blocks for IOException
		}
	}
	
	
	public void addTable(String tableName, String DBname)
	{
		try
		{
			Message send = new Message(Command.ADD_TABLE, tableName);
			objOut.writeObject(send);
		}
		catch (IOException ex)
		{
			// I'm starting to get tired of writing catch blocks for IOException
		}
	}
	
	@Override
	public void deleteTable(String tableName, String DBname)
	{
		try
		{
			Message send = new Message(Command.DELETE_TABLE, tableName);
			objOut.writeObject(send);
		}
		catch (IOException ex)
		{
			// I'm starting to get tired of writing catch blocks for IOException
		}
	}


	@Override
	public void getTableNames(String database)
	{
		try
		{
			Message send = new Message(Command.GET_DATABASE, database);
			objOut.writeObject(send); System.out.println("Sent GET_DATABASE to server");
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
			Message send = new Message(Command.GET_TABLE, tablename);
			objOut.writeObject(send); System.out.println("Sent GET_TABLE to server");
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
			newColNames[i] = name;
			i++;
			gui.fieldsCB.addItem(name);
		}
		
		Comparable[][] entryList = currentTable.getEntries();
		DefaultTableModel tableModel = new DefaultTableModel(entryList, newColNames);
			gui.setTableModel(entryList,newColNames);
	}

	@Override
	public void deleteColumn(int selectedIndex) {
		try {
			objOut.writeObject(new Message(Command.DELETE_COLUMN, selectedIndex)); //-1 because Primary Key is the first element
		} catch (IOException e) {
		} 
	}
	
	public void addEntry(String[] headers) { 
		EditEntryGUI addEnt = new EditEntryGUI(headers,this);
		addEnt.setVisible(true);
	}
	
	public void rmvEntry(int primaryKey)
	{
		try {
			objOut.writeObject(new Message(Command.DELETE_ENTRY, primaryKey));
		} catch (IOException e) {
		}
	}
	
	public void writeMessage(Message send)
	{
		try {
			objOut.writeObject(send);
		} catch (IOException e) {
		}
	}

	@Override
	public void addColumn() {
		AddFieldGUI addCol = new AddFieldGUI(this);
		addCol.setVisible(true);
		
	}

	@Override
	public void editEntry(int entryKey, Comparable[] entryData, String[] headers) {
		EditEntryGUI editEnt = new EditEntryGUI(headers, new Entry(entryKey,entryData), this);
		editEnt.setVisible(true);
	}
}
