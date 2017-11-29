package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;

public class ServerMain implements Server
{
	private DefinitelyNotArrayList<ClientHandler> clientList;
	private int entryKey;
	private AVLTree<User> userList;
	public ServerMain()
	{
		clientList = new DefinitelyNotArrayList<ClientHandler>();
		initializeFromConfig();
	}	
	
	public static void main(String[] args)
	{
		// Create a new server window, and assign it a new server handler
		ServerMain server = new ServerMain();

		ServerSocket socket = null;
		try
		{
			// Open a socket to accept incoming connections
			socket = new ServerSocket(8001);
			while (true)
			{
				server.addClient(new ClientHandler(socket.accept(), server));
				System.out.println("Client Connected");
			}
		}
		catch (IOException ex)
		{
		}
		finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException ex2)
				{
					ex2.printStackTrace();
				}
			}
		}
	}
	
	public void addClient(ClientHandler client)
	{
		new Thread(client).start();
		clientList.add(client);
	}
	
	public void initializeFromConfig()
	{
		File file = new File("config.albert");
		if(!file.exists())
		{
			userList = new AVLTree<User>(new User("admin", "NewAdmin", new String[0], true));
			entryKey = 0;
			saveConfig();
		}
		try
		{
			FileInputStream fIn = new FileInputStream(file);
			ObjectInputStream oIn = new ObjectInputStream(fIn);
			Config config = (Config)oIn.readObject();
			fIn.close();
			oIn.close();
			userList = config.getUserList();
			entryKey = config.getEntryKey();
		}
		catch (IOException ex)
		{
			if (file.exists())
				file.delete();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		file =  new File("databases");
		if(!file.exists())
			file.mkdir();
	}
	
	public void saveConfig()
	{
		File file = new File("config.albert");
		
		try 
		{
			file.delete();
			file.createNewFile();
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			Config config = new Config(userList, entryKey);
			oOut.writeObject(config);
			oOut.close();
			fOut.close();
		} 
		catch (FileNotFoundException e)
		{	} 
		catch (IOException e) 
		{	}
	}
	public void sendObjectToAll(Message message, String database, String table)
	{
		for (ClientHandler client : clientList)
		{
			if((message.getCommandType() == Command.ADD_USER ||
					message.getCommandType() == Command.DATABASE_LIST) &&
					client.getCurrentUser().isAdmin())
			{
				client.sendObject(message);
			}
			else if(client.getCurrentDatabaseName().equals(database))
			{
				if(message.getCommandType()==Command.DELETE_TABLE 
				|| message.getCommandType()==Command.ADD_TABLE
				|| client.getCurrentTableName().equals(table))
					client.sendObject(message);
				//else if (client.getUser().compareTo)
			}
		}	
	}

	@Override
	public String[] getTableList(String database) 
	{
		File db = new File("databases\\" + database);
		if (db.exists())
		{
			String[] fileList = db.list();
			for (int i = 0; i < fileList.length; i++)
				fileList[i] = fileList[i].substring(0, fileList[i].length() - 5);
			return fileList;
		}
		else
			return new String[] { "Database doesn't exist" };
	}	
	
	@Override
	public Table getTable(String dbname, String tableName) 
	{
		Table tableReq = null;
		FileInputStream file = null;
		try 
		{
			file = new FileInputStream(new File("databases\\" + dbname + "\\" + tableName + ".eric"));
		} 
		catch (FileNotFoundException e1) 
		{	}
		try 
		{
			ObjectInputStream fileObjIn = new ObjectInputStream(file);
			tableReq = (Table)fileObjIn.readObject();
			file.close();
			fileObjIn.close();
		} 
		catch (IOException e)
		{	}
		catch (ClassNotFoundException e)
		{	}
    
		return tableReq;	
	}

	public void saveTable(String dbName, String tableName, Table table)
	{
		File file = new File("databases\\" + dbName + "\\" + tableName + ".eric");
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) 
			{
			}
		}
		try 
		{
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(table);
			oOut.close();
			fOut.close();
		} 
		catch (FileNotFoundException e)
		{
		} 
		catch (IOException e) 
		{
		}
	}
		
	@Override
	public void addEntry(String databaseName, String tableName, Comparable[] data) 
	{
		Entry newEntry = new Entry(++entryKey, data);
		saveConfig();
		Table newTable = getTable(databaseName,tableName);
		newTable.addEntry(newEntry);
		saveTable(databaseName, tableName, newTable);
		sendObjectToAll(new Message(Command.ADD_ENTRY, newEntry),databaseName,tableName);
	}

	@Override
	public void addColumns(String databaseName, String tableName, Column[] columnList) //always sending an array of columns to add
	{
		Table currentTable = getTable(databaseName,tableName);
		for(int i = 0 ; i < columnList.length ; i++)
			currentTable.addColumn(columnList[i]);
		saveTable(databaseName, tableName, currentTable);
		sendObjectToAll(new Message(Command.ADD_COLUMNS, columnList),databaseName,tableName);
	}
	
	@Override
	public void addTable(String databaseName, String tableName)
	{
		Table newTable = new Table();
		saveTable(databaseName, tableName, newTable);
		sendObjectToAll(new Message(Command.ADD_TABLE, tableName),databaseName,tableName);
	}
	
	@Override
	public void editEntry(String databaseName, String tableName, Entry newEntry)
	{
		Table currentTable = getTable(databaseName,tableName);
		currentTable.editEntry(newEntry);
		saveTable(databaseName, tableName, currentTable);
		sendObjectToAll(new Message(Command.EDIT_ENTRY, newEntry),databaseName,tableName);
	}
	
	@Override
	public void deleteEntry(String databaseName, String tableName, int key)
	{
		Table currentTable = getTable(databaseName,tableName);
		currentTable.removeEntry(key);
		saveTable(databaseName, tableName, currentTable);
		sendObjectToAll(new Message(Command.DELETE_ENTRY,key),databaseName,tableName);
	}
	
	@Override
	public void deleteColumn(String databaseName, String tableName, Integer index)
	{
		Table currentTable = getTable(databaseName,tableName);
		currentTable.removeColumn(index);
		saveTable(databaseName, tableName, currentTable);
		sendObjectToAll(new Message(Command.DELETE_COLUMN,index),databaseName,tableName);
	}
	
	@Override
	public void deleteTable(String databaseName, String tableName)
	{
		File table = new File("databases\\" + databaseName + "\\" + tableName + ".eric");
		table.delete();
		sendObjectToAll(new Message(Command.GET_TABLE_NAMES,getTableList(databaseName)),databaseName,tableName);
	}
	
	public User getUser(String username)
	{
		return userList.get(new User(username));
	}

	@Override
	public void createDatabase(String databaseName)
	{
		File db = new File("databases\\" + databaseName);
		if (!db.exists())
		{
			db.mkdir();
			User[] users = userList.toArray(new User[userList.size()]);
			String[] newDB = { databaseName };
			for (User u : users)
			{
				if(u.isAdmin())
					u.addDatabases(newDB);
			}
			this.sendObjectToAll(new Message(Command.DATABASE_LIST, getDatabaseList()),
											 databaseName, null);
		}
		else
		{
			// TODO Message if fail
		}
	}

	@Override
	public void createUser(User user)
	{
		if (userList.delete(user)) System.out.println("User overwritten");
		userList.add(user);
		saveConfig();
	}
	
	private String[] getDatabaseList() { return new File("databases").list(); }
}
