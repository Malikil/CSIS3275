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

	public void saveToFile(AVLTree<Entry> tree, String databaseName, String tableName, 
							String[] fieldNames, FieldType[] fieldTypes)
	{
		FileOutputStream fOut = null;
		ObjectOutputStream oStream = null;
		
		try {
			File dir = new File(databaseName);
			if(!dir.isDirectory())
				dir.mkdir();
			File saveFile = new File(databaseName+"\\"+tableName+".ser");
			
			fOut = new FileOutputStream(saveFile);
			oStream = new ObjectOutputStream(fOut);
			oStream.writeObject(fieldNames);
			oStream.writeObject(fieldTypes);
			oStream.writeObject(tree); 
			oStream.close();
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
	
	public boolean deleteFile()
	{
		return true;
	}
	
	
	
	@Override
	public Table getTable(String dbname, String tableName) {
		Table tableReq = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(dbname + "\\" + tableName + ".eric"));
		} catch (FileNotFoundException e1) {
		}
		try {
			ObjectInputStream fileObjIn = new ObjectInputStream(file);
			tableReq = (Table) fileObjIn.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return tableReq;
		
	}
	

	@Override
	public void createTable(String tableName) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateTable(String db, String table, Table newTable)
	{
		FileOutputStream file = null;
		try {
			file = new FileOutputStream(new File(db + "\\" + table + ".eric"));
		} catch (FileNotFoundException e1) {
		}
		try {
			ObjectOutputStream fileObjOut = new ObjectOutputStream(file);
			fileObjOut.writeObject(newTable);
		} catch (IOException e) {
		}
		
	}
}