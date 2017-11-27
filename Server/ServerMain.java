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

import Client.AddColumnGUI;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;

public class ServerMain implements Server
{
	private ArrayList<ClientHandler> clientList;
	private ServerGUI gui;
	private int entryKey;
	private DefinitelyNotArrayList<String> userList;
	public ServerMain()
	{
		clientList = new ArrayList<>();
		entryKey = this.getKey();
		gui = new ServerGUI(this);
		populateUserList();
	}	
	
	public static void main(String[] args)
	{
		// Create a new server window, and assign it a new server handler
		ServerMain server = new ServerMain();
		
		/*
		//FORTESTING TODO
		server.createDatabase("db1");
		String[] testDBs = new String[1];
		testDBs[0]= "db1";
		server.addUser("a", "a", testDBs);
		server.addTable("db1", "table1");
		Column[] testCols = new Column[1];
		Column testCol = new Column("col1=string",0);
		testCols[0] = testCol;
		server.addColumns("db1", "table1", testCols);
		//server.getTable("db1", "table1").getColumns().
		//FORTESTING TODO
		*/

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
	
	public String[] getUserList()
	{
		return userList.toArray(new String[userList.size()]);
	}
	
	public void populateUserList()
	{
		DefinitelyNotArrayList<String> newUserList = new DefinitelyNotArrayList<String>();
		File file = new File("users.txt");
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				System.out.println("new user file created");
			} 
			catch (IOException e) 
			{
			}
		}
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String nextLine = null;
			while((nextLine = reader.readLine())!=null)
			{
				String[] validList = nextLine.split(",");
				newUserList.add(validList[0]);
			}
			reader.close();
			userList = newUserList;
		} 
		catch (FileNotFoundException e) 
		{
		} 
		catch (IOException e) 
		{	
		}
	}
	
	public void sendObjectToAll(Message message, String database, String table)
	{
		for (ClientHandler client : clientList)
			if(client.getCurrentDatabaseName().equals(database))
				if(message.getCommandType()==Command.DELETE_TABLE || client.getCurrentTableName().equals(table))
					client.sendObject(message);
		/*if(gui.getCurrentDatabase().equals(database)) TODO
			if(gui.getTableName().equals(table))
			{
				switch(message.getCommandType())
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
				}
			*/
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
		// Avoid case sensitivity
		user = user.toLowerCase();
		BufferedReader usersFile = null;
		String[] databases = new String[] { "No databases associated with this user" };
		try
		{
			usersFile = new BufferedReader(new FileReader(new File("users.txt")));
			String nextLine;
			String[] userInfo = null;
			while ((nextLine = usersFile.readLine()) != null)
			{
				userInfo = nextLine.split(",");
				// Avoid case sensitivity
				if (userInfo[0].toLowerCase().equals(user))
					break;
			}
			if (nextLine != null)
			{
				databases = new String[userInfo.length - 2]; 
				for (int i = 2; i < userInfo.length; i++)
					databases[i - 2] = userInfo[i];
			}
		}
		catch (IOException ex)
		{
			databases = new String[] { "Could not read Users file" };
		}
		finally
		{
			if (usersFile != null)
				try { usersFile.close(); }
				catch (IOException ex) { /* Fail Silently */ }
		}
		
		return databases;
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
			file = new FileInputStream(new File("databases\\" + dbname + "\\"+ tableName +".eric"));
		} 
		catch (FileNotFoundException e1) 
		{
			
		}
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
		File file = new File("databases\\" + dbName+"\\"+tableName+".eric");
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
	public void createDatabase() //String[] userList)
	{
		AddDatabaseGUI adg = new AddDatabaseGUI();
		adg.setVisible(true);
		File dir = new File("databases\\" + adg.getDatabaseName());
		if(!dir.isDirectory())
		{
			dir.mkdir();
			return;
		}
		
		//TODO //changeUserDatabases();
	}

	public void addUser(String username, String password, String[] databaseList)
	{
		File file = new File("users.txt");
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				System.out.println("new user file created");
			} 
			catch (IOException e) 
			{
			}
		}
		try 
		{
			BufferedReader userList = new BufferedReader(new FileReader(file));
			String nextLine = null;
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.split(",");
				if((username.toLowerCase()).compareTo(validList[0].toLowerCase()) == 0)
					return;
			}
			userList.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
			writer.write("\n" + username + "," + password);
			for(int i = 0; i < databaseList.length; i++)
			{
				writer.write("," + databaseList[i]);
			}
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
		} 
		catch (IOException e) 
		{	
		}
		trimUserfile();
	}
	
	public void deleteUser(String username)
	{
		File file = new File("users.txt");
		try 
		{
			BufferedReader userList = new BufferedReader(new FileReader(file));
			StringBuilder newFileContents = new StringBuilder();
			String nextLine = null;
			int lineCount = 0;
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.trim().split(",");
				if(validList[0]!=null && !validList[0].isEmpty())
				{
					if(username.toLowerCase().compareTo(validList[0])==0)
					{
						sendDeleteUser(new Message(Command.INCORRECT_USER,
						"Your user profile has been deleted. Please request a new profile from an Admin."), username);
						continue;
					}
					else
					{
						if(lineCount!=0)
							newFileContents.append(System.getProperty("line.separator"));
						newFileContents.append(nextLine);
						lineCount++;
					}
				}
			}
			userList.close();
			PrintWriter overwrite = new PrintWriter(file);
			overwrite.write(newFileContents.toString());
			overwrite.close();
		} 
		catch (FileNotFoundException e) 
		{	
		} 
		catch (IOException e) 
		{
		}
	}
	
	public void trimUserfile() //trims whitespace and empty lines in user.txt
	{
		File file = new File("users.txt");
		try {
			BufferedReader userList = new BufferedReader(new FileReader(file));
			StringBuilder newFileContents = new StringBuilder();
			String nextLine = null;
			int lineCount = 0;
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.trim().split(",");
				if(validList[0]!=null && !validList[0].isEmpty())
				{
					if(lineCount!=0)
						newFileContents.append(System.getProperty("line.separator"));
					newFileContents.append(nextLine);
					lineCount++;
				}
			}
			userList.close();
			PrintWriter overwrite = new PrintWriter(file);
			overwrite.write(newFileContents.toString());
			overwrite.close();
		} 
		catch (FileNotFoundException e) 
		{	
			
		} 
		catch (IOException e) 
		{
			
		}
	}

	/* TODO
	public void changeDatabaseUsers(String databaseName, String usernames[])
	{
		
	}
	*/
	
	public void changeUserDatabases(String username, String[] databases) //overwrites old databases with new databases array
	{
		File file = new File("users.txt");
		try 
		{
			BufferedReader userList = new BufferedReader(new FileReader(file));
			StringBuilder newFileContents = new StringBuilder();
			String nextLine = null;
			int lineCount = 0;
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.trim().split(",");
				if(validList[0]!=null && !validList[0].isEmpty())
				{
					if(lineCount != 0)
						newFileContents.append(System.getProperty("line.separator"));
					if(username.toLowerCase().compareTo(validList[0]) == 0) //found user
					{
						newFileContents.append(validList[0]+","+validList[1]);
						for(int i= 0;i<databases.length;i++)
							newFileContents.append(","+databases[i]);
					}
					else
						newFileContents.append(nextLine.trim());
				}
				lineCount++;
			}
			userList.close();
			PrintWriter overwrite = new PrintWriter(file);
			overwrite.write(newFileContents.toString());
			overwrite.close();
		} 
		catch (FileNotFoundException e) 
		{
			
		} 
		catch (IOException e) 
		{
			
		}
	}
	
	public void changePassword(String username, String newPass)
	{
		File file = new File("users.txt");
		try 
		{
			BufferedReader userList = new BufferedReader(new FileReader(file));
			StringBuilder newFileContents = new StringBuilder();
			String nextLine = null;
			int lineCount = 0;
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.trim().split(",");
				if(validList[0]!=null && !validList[0].isEmpty())
				{
					if(lineCount != 0)
						newFileContents.append(System.getProperty("line.separator"));
					if(username.toLowerCase().compareTo(validList[0]) == 0) //found user
					{
						newFileContents.append(validList[0]+","+newPass);
						for(int i= 2;i<validList.length;i++)
							newFileContents.append(","+validList[i]);
					}
					else
						newFileContents.append(nextLine.trim());
				}
				lineCount++;
			}
			userList.close();
			PrintWriter overwrite = new PrintWriter(file);
			overwrite.write(newFileContents.toString());
			overwrite.close();
		} 
		catch (FileNotFoundException e) 
		{
			
		} 
		catch (IOException e) 
		{
			
		}
	}
	
	public String[] getUserlist()
	{
		File file = new File("users.txt");
		System.out.println(file.getAbsolutePath());
		String[] listOfUsers = null;
		try 
		{
			BufferedReader userList = new BufferedReader(new FileReader(file));
			String nextLine = null;
			DefinitelyNotArrayList<String> usersArrayList = new DefinitelyNotArrayList<>();
			while((nextLine = userList.readLine())!=null)
			{
				String[] validList = nextLine.trim().split(",");
				if(validList[0]!=null && !validList[0].isEmpty())
				{
					usersArrayList.add(validList[0]);
				}
			}
			userList.close();
			listOfUsers = new String[usersArrayList.size()];
			listOfUsers = (String[]) usersArrayList.toArray(listOfUsers);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch (IOException e) 
		{
			System.out.println("catch2");
		}
		return listOfUsers;
	}

	private void saveKey(int key)
	{
		File file = new File("key.ini");
		if(!file.exists())
		{
			key = 0;
			try 
			{
				file.createNewFile();
				System.out.println("key file created");
			} 
			catch (IOException e) 
			{
			}
		}
		try 
		{
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(key);
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
	
	private int getKey() //TODO its private right now
	{
		int key;
		File file = new File("key.config");
		if(!file.exists())
		{
			saveKey(0);
			return 0;
		}
		else try 
		{
			FileInputStream fIn = new FileInputStream(file);
			ObjectInputStream oIn = new ObjectInputStream(fIn);
			key = (int)oIn.readObject();
			fIn.close();
			oIn.close();
			return key;
		}
		catch (FileNotFoundException e1) 
		{
			System.out.println("getKey failed, abort");
			return -1;
		}
		catch (IOException e)
		{	
			System.out.println("getKey failed, abort");
			return -1;
		}
		catch (ClassNotFoundException e)
		{	
			System.out.println("getKey failed, abort");
			return -1;
		}
	}
	
	@Override
	public void addEntry(String databaseName, String tableName, Comparable[] data) 
	{
		Entry newEntry = new Entry(++entryKey, data);
		saveKey(entryKey);
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
		AddColumnGUI ac = new AddColumnGUI(true);
		ac.setVisible(true);
		Table newTable = new Table();
		saveTable(databaseName, tableName, newTable);
		sendObjectToAll(new Message(Command.ADD_TABLE, newTable),databaseName,tableName);
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
	public void deleteEntry(String databaseName, String tableName, Entry entryToDelete)
	{
		Table currentTable = getTable(databaseName,tableName);
		currentTable.removeEntry(entryToDelete.getKey());
		saveTable(databaseName, tableName, currentTable);
		sendObjectToAll(new Message(Command.DELETE_ENTRY,entryToDelete),databaseName,tableName);
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
		sendObjectToAll(new Message(Command.DELETE_TABLE,tableName),databaseName,tableName);
	}
	
	/*
	//TODO //THIS USES USER AND CONFIG CLASSES
	public void saveConfig(int entryKey, int userKey, User[] userList)
	{
		File file = new File("config.ini");
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				System.out.println("config file created");
			} 
			catch (IOException e) 
			{
			}
		}
		try 
		{
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(userList);
			oOut.writeObject(entryKey);
			oOut.writeObject(userKey);
			oOut.close();
			fOut.close();
		} 
		catch (FileNotFoundException e)
		{
		} 
		catch (IOException e) 
		{
		}
	}*/
}

