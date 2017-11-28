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
	private DefinitelyNotArrayList<ClientHandler> clientList = null;
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

	public User[] getUserList()
	{
		return userList.toArray(new User[userList.size()]);
	}
	
	public String[] getUsernameStrings()
	{
		int size = userList.size();
		String[] uList = new String[size];
		User[] newUList = userList.toArray(new User[size]);
		for(int i = 0 ; i < size ; i++)
		{
			uList[i] = newUList[i].getUsername();
		}
		return uList;
	}
	
	public void initializeFromConfig()
	{
		File file = new File("config.albert");
		if(!file.exists())
		{
			userList = new AVLTree<User>(new User("admin", "New Admin", new String[0], true));
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
		{	} 
		catch (ClassNotFoundException e) 
		{	}
	}
	
	public void saveConfig()
	{
		File file = new File("config.albert");
		try 
		{
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
			if(client.getCurrentDatabaseName().equals(database))
				if(message.getCommandType()==Command.DELETE_TABLE 
				|| message.getCommandType()==Command.ADD_TABLE
				|| client.getCurrentTableName().equals(table))
					client.sendObject(message);
	}
	
	public void sendDeleteUser(Message message, String username)
	{
		for (ClientHandler client : clientList)
			if(client.getUsername().equals(username))
					client.sendObject(message);
	}
	
	@Override
	public String[] getUserDatabases(String user)
	{
		return userList.get(new User(user)).getDatabases();
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
	public boolean deleteDatabase(String databaseName)
	{
		File dir = new File("databases\\" + databaseName);
		if (dir.list().length == 0)
		{
			dir.delete();
			User[] users = userList.toArray(new User[userList.size()]);
			for (User u : users)
				u.deleteDatabase(databaseName);
			return true;
		}
		else return false;
	}
	
	@Override
	public void deleteUser(String username)
	{
		userList.delete(new User(username));
		for (ClientHandler c : clientList)
			if (c.getUsername().equals(username))
				c.sendObject(new Message(Command.DELETE_USER, null));
		saveConfig();
	}
	
	public void changeUserDatabases(String username, String[] databases) //overwrites old databases with new databases array
	{
		User newUser = userList.get(new User(username));
		userList.delete(newUser);
		newUser = new User(newUser,databases);
		userList.add(newUser);
		saveConfig();
	}
	
	public void changePassword(String username, String newPass)
	{
		User newUser = userList.get(new User(username));
		userList.delete(newUser);
		newUser = new User(newUser,newPass);
		userList.add(newUser);
		saveConfig();
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

	@Override
	public void createDatabase(String databaseName)
	{
		// TODO
	}

	@Override
	public void createUser(User user)
	{
		userList.add(user);
		saveConfig();
	}

	@Override
	public void editUser(User user) {
		// TODO Auto-generated method stub
		
	}
}
