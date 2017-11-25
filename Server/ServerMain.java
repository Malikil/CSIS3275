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
			// Error opening socket
			// Error accepting connection
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
	public Table getTable(String dbname, String tableName) 
	{
		Table tableReq = null;
		FileInputStream file = null;
		try 
		{
			file = new FileInputStream(new File(dbname + "\\"+ tableName +".eric"));
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
		File file = new File(dbName+"\\"+tableName+".eric");
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
	
	public void saveDatabase(String databaseName)
	{
		File dir = new File(databaseName);
		if(dir.isDirectory())
		{
			return;
		}
		if(!dir.isDirectory())
		{
			dir.mkdir();
			return;
		}	
		return;
	}
	
	public void deleteDatabase(String databaseName)
	{
		File dir = new File(databaseName);
		dir.delete();
	}
	
	public void deleteTable(String databaseName, String tableName)
	{
		File table = new File(databaseName+"\\"+tableName+".eric");
		table.delete();
	}
	
	public void addUser(String username, String password)//small issue with newlines being created
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
			BufferedWriter write = new BufferedWriter(new FileWriter("users.txt", true));
			write.write("\n" + username + "," + password);
			write.close();
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
						continue;
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

	public void changeUserDatabases(String username, String[] databases) //unfinished
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
	
	public void editEntry() //unfinished
	{
		// TODO Eric-generated method stub :thinking:
	}

	@Override
	public void addEntry(String database, String table, Comparable[] data) {
		// TODO Auto-generated method stub
		
	}
}
