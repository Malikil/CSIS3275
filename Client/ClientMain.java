package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Server.Command;
import Server.Entry;
import Server.Message;
import Server.Table;

public class ClientMain implements Client
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private ClientGUI gui;
	private Table currentTable = null;
	private Entry[] filteredTable;
	private String currentTableName;
	
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
					// Couldn't close socket
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
						gui.setTableList(received.getTableList());
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
	public void createTable(Table table)
	{
		try
		{
			objOut.writeObject(new Message(Command.ADD_TABLE, table));
		}
		catch (IOException ex)
		{
			// TODO Catch block
		}
	}
	
	@Override
	public void deleteCurrentTable()
	{
		try
		{
			objOut.writeObject(new Message(Command.DELETE_TABLE, currentTableName));
		}
		catch (IOException ex)
		{
			// TODO Catch block
		}
	}
	
	@Override
	public void setCurrentTableName(String tableName)
	{
		currentTableName = tableName;
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
	
	public void getTable(String tablename)
	{
		try
		{
			objOut.writeObject(new Message(Command.GET_TABLE, tablename));
			System.out.println("Sent GET_TABLE to server");
		}
		catch (IOException ex)
		{
			System.out.println("Error asking for table from server");
		}
	}
	
	@Override
	public void setTable(Table newTable)
	{
		currentTable = newTable;
		filteredTable = newTable.asArray();
		String[] colNames =  currentTable.getColumnNames();
		gui.setFieldList(colNames);
		gui.setTable(currentTable.asArray(),colNames);
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
	public void addColumn() 
	{
	}

	@Override
	public void editEntry(int entryKey)
	{
		EditEntryGUI editGUI = new EditEntryGUI(currentTable.getColumns(), filteredTable[entryKey]);
		editGUI.setVisible(true);
		if (editGUI.getEntry() != null)
			try
			{
				objOut.writeObject(new Message(Command.EDIT_ENTRY, editGUI.getEntry()));
			}
			catch (IOException ex)
			{
				
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
		filteredTable = currentTable.asArray();
		for (int i = 0; i < values.length; i++)
		{
			Entry.setComparer(fields[i]);
			quickSort(0, filteredTable.length, filteredTable);
			// Binary search for filter value
			int index = binarySearch((Comparable[])filteredTable, (Comparable)values[i]);
			switch (comparisons[i])
			{
			case "<":
				while (filteredTable[++index].getField(fields[i]).compareTo(values[i]) < 1);
			case "<=":
			case "=":
			case ">=":
			case ">":
			}
			// Update temporary table
			
		}
		// Display table
	}
	
	private <T extends Comparable<T>> int binarySearch(T[] arr, T val)
	{
		int front = 0, end = arr.length - 1;
		for (int middle = (front + arr.length) / 2; front <= end; middle = (front + end) / 2)
		{
			if (val.equals(arr[middle]))
				return middle;
			else if (val.compareTo(arr[middle]) > 0)
				front = middle + 1;
			else
				end = middle - 1;
		}
		return -1;
	}
	
	private <T extends Comparable<T>> void quickSort(int start, int length, T[] arr)
	{
	    if (start >= length - 1) return;
	    
	    int front = start - 1;
	    int last = length - 1;
	    T pivot = arr[last];
	    while (true)
	    {
	        while (arr[++front].compareTo(pivot) < 0);
	        while (arr[--last].compareTo(pivot) > 0 && last > start);
	        if (front < last)
	        {
	            T temp = arr[front];
	            arr[front] = arr[last];
	            arr[last] = temp;
	        }
	        else
	        {
	            T temp = arr[front];
	            arr[front] = pivot;
	            arr[length - 1] = temp;
	            break;
	        }
	    }
	    
	    quickSort(start, front, arr);
	    quickSort(front + 1, length, arr);
	}
}
