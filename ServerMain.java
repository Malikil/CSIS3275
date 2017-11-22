package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ServerMain implements Server
{
	private ArrayList<ClientHandler> clientList;
	
	public ServerMain()
	{
		clientList = new ArrayList<>();
	}
	
	public static void main(String[] args)
	{
		Table test = new Table();
		test.addField(new Column("Field1", 1));
		test.addField(new Column("Field2", 1));
		test.addField(new Column("Field3", 1));
		Comparable[] gah =  {"1","2","3"};
		Comparable[] gah2 =  {"65","52","98"};
		Comparable[] gah3 =  {"1","2","dfgdfg"};
		Comparable[] gah4 =  {"1","2","gdf"};
		
		test.addEntry(gah);
		test.addEntry(gah2);
		test.addEntry(gah3);
		test.addEntry(gah4);
		

		{
			FileOutputStream fOut = null;
			ObjectOutputStream oStream = null;
			
			try {
				File dir = new File("db1");
				if(!dir.isDirectory())
					dir.mkdir();
				File saveFile = new File("db1"+"\\"+"test7665"+".eric");
				
				fOut = new FileOutputStream(saveFile);
				oStream = new ObjectOutputStream(fOut);
				oStream.writeObject(test);
				oStream.close();
				fOut.close();
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		// Create a new server window, and assign it a new server handler
		ServerMain server = new ServerMain();
		new Thread(new ServerGUI(server)).start();
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
	
	public void sendToAll(String message)
	{
		for (ClientHandler client : clientList)
			client.sendMessage(message);
	}

	@Override
	public void messageReceived(String message)
	{
		for (ClientHandler client : clientList)
			client.sendMessage(message);
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
			usersFile = new BufferedReader(new FileReader(new File("Users.txt")));
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
	public String[] getTableList(String database) {
		File db = new File(database);
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
	public Table getTable(String dbname, String tableName) {
		Table tableReq = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(dbname + "\\"+ tableName +".eric"));
		} catch (FileNotFoundException e1) {
			
		}
		try {
			ObjectInputStream fileObjIn = new ObjectInputStream(file);
			tableReq = (Table) fileObjIn.readObject();
			file.close();
			fileObjIn.close();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return tableReq;
	}
	
	public boolean saveTable(Table table, String databaseName, String tableName)
	{
		FileOutputStream fOut = null;
		ObjectOutputStream oStream = null;
		
		try {
			File dir = new File(databaseName);
			if(!dir.isDirectory())
				dir.mkdir();
			File saveFile = new File(databaseName+"\\"+tableName+".eric");
			
			fOut = new FileOutputStream(saveFile);
			oStream = new ObjectOutputStream(fOut);
			oStream.writeObject(table);
			oStream.close();
			fOut.close();
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean saveDatabase(String databaseName)
	{
		
		File dir = new File(databaseName);
		if(!dir.isDirectory())
		{
			dir.mkdir();
			return true;
		}
		return false;
	}
	
	public boolean deleteDatabase(String databaseName)
	{
		File dir = new File(databaseName);
		if(dir.delete())
			return true;
		return false;
	}
	
	public boolean deleteTable(String databaseName, String tableName)
	{
		File table = new File(databaseName+"\\"+tableName+".eric");
		if(table.delete())
			return true;
		return false;
	}
}