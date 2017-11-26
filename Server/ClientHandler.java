package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private Server parent;
	private String currentDatabaseName = null;
	private String currentTableName = null;
	private Table currentTable = null;
	
	public ClientHandler(Socket connection, Server server)
	{
		parent = server;
		// Establish connections
		try
		{
			objOut = new ObjectOutputStream(connection.getOutputStream());
			objIn = new ObjectInputStream(connection.getInputStream());
		}
		catch (IOException ex)
		{
			System.out.println("Problem establishing connections");
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			boolean loggedIn = false;
			BufferedReader userList = new BufferedReader(new FileReader(new File("users.txt")));
			do
			{
				try
				{
					String[] userPass = ((Message)objIn.readObject()).getLogin();
					// Make received username lowercase to avoid case sensitivity
					userPass[0] = userPass[0].toLowerCase();
					String nextLine;
					while ((nextLine = userList.readLine()) != null)
					{
						String[] userInfo = nextLine.split(",");
						// Make username from file lowercase to avoid case sensitivity
						userInfo[0] = userInfo[0].toLowerCase();
						if (userInfo[0].equals(userPass[0]))
							
							if (userInfo[1].equals(userPass[1]))
							{
								// Send success to client
								// Send available databases to client
								objOut.writeObject(new Message(
										Command.CONNECTION_SUCCESS,
										parent.getUserDatabases(userInfo[0])));
								loggedIn = true;
								break;
							}
							else
							{
								objOut.writeObject(new Message(Command.INCORRECT_PASSWORD, null));
								break;
							}
					}
					if (!loggedIn && nextLine == null)
						objOut.writeObject(new Message(Command.INCORRECT_USER, null));		
				}
				catch (IOException ex)
				{
				}
				catch (ClassNotFoundException ex)
				{
				}
			} while (!loggedIn);
			userList.close();
		}
		catch (IOException ex)
		{
			System.out.println("User file couldn't be found");
			try
			{
				objIn.readObject(); // Receive the String[] of user/pass, we just don't need it now
				objOut.writeObject(Command.MESSAGE);
				objOut.close();
				objIn.close();
			}
			catch (IOException | ClassNotFoundException e2)
			{ System.out.println("Couldn't close sockets"); }
			return;
		}
		
		// Client is logged in, now wait for commands
		while (true)
		{
			try
			{
				Message received = (Message) objIn.readObject();
				switch (received.getCommandType())
				{
				case ADD_COLUMNS:
					parent.addColumns(currentDatabaseName, currentTableName, received.getColumns());
					break;
				case ADD_ENTRY:
					parent.addEntry(currentDatabaseName, currentTableName, received.getNewEntry());
					break;
				case ADD_TABLE:
					currentTableName = received.getTableName();
					parent.addTable(currentDatabaseName,currentTableName);
					break;
				case DELETE_COLUMN:
					parent.deleteColumn(currentDatabaseName, currentTableName, received.getColumnIndex());
					break;
				case DELETE_ENTRY:
					parent.deleteEntry(currentDatabaseName, currentTableName, received.getEntry());
					break;
				case DELETE_TABLE:
					parent.deleteTable(currentDatabaseName, received.getTableName());
					currentTableName = null;
					break;
				case EDIT_ENTRY:
					parent.editEntry(currentDatabaseName, currentTableName,received.getEntry());
					break;
				case GET_TABLE:
					currentTableName = received.getTableName();
					currentTable = parent.getTable(currentDatabaseName, currentTableName);
					objOut.writeObject(new Message(Command.GET_TABLE, currentTable));
					break;
				case GET_TABLE_NAMES:
					currentDatabaseName  = received.getDatabase();
					objOut.writeObject(new Message(Command.GET_TABLE_NAMES, parent.getTableList(currentDatabaseName))); System.out.println("Sent databases to client");
					break;
				default:
					System.out.println("lol you sent wrong message to ClientHandler"); //TODO
					break;
				}
			}
			catch (ClassNotFoundException | IOException ex)
			{
			}
		}
	}
	
	public void sendObject(Message message)
	{
		try
		{
			objOut.writeObject(message);
		}
		catch (IOException ex)
		{
		}
	}
	public String getCurrentTableName()
	{
		return currentTableName;
	}
	
	public String getCurrentDatabaseName()
	{
		return currentDatabaseName;
	}
}
