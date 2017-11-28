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
	private String currentDatabaseName;
	private String currentTableName;
	private User currentUser;
	
	public String getUsername() { return currentUser.getUsername(); }
	public String getCurrentTableName() { return currentTableName; }
	public String getCurrentDatabaseName() { return currentDatabaseName; }
	
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
		boolean loggedIn = false;
		do
		{
			try 
			{
				String[] userPass = ((Message)objIn.readObject()).getLogin();
				User parentUser = parent.getUser(userPass[0]);
				if(parentUser != null)
				{
					if(parentUser.equals(new User(userPass[0], userPass[1], null)))
					{
						objOut.writeObject(new Message(Command.CONNECTION_SUCCESS, parentUser));
						loggedIn = true;
						break;
					}
					else
					{
						objOut.writeObject(new Message(Command.INCORRECT_PASSWORD, null));
						break;
					}
				}
				if (!loggedIn)
					objOut.writeObject(new Message(Command.INCORRECT_USER, null));
			} 
			catch (ClassNotFoundException e) 
			{	} // TODO
			catch (IOException e) 
			{	} // TODO
		} while (!loggedIn);
		
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
					objOut.writeObject(new Message(Command.GET_ACTUAL_TABLE,parent.getTable(currentDatabaseName, currentTableName)));
					break;
				case DELETE_COLUMN:
					parent.deleteColumn(currentDatabaseName, currentTableName, received.getColumnIndex());
					break;
				case DELETE_ENTRY:
					parent.deleteEntry(currentDatabaseName, currentTableName, received.getKey());
					break;
				case DELETE_TABLE:
					parent.deleteTable(currentDatabaseName, currentTableName);
					currentTableName = null;
					break;
				case EDIT_ENTRY:
					parent.editEntry(currentDatabaseName, currentTableName,received.getEntry());
					break;
				case GET_ACTUAL_TABLE:
					currentTableName = received.getTableName();
					objOut.writeObject(new Message(Command.GET_ACTUAL_TABLE, parent.getTable(currentDatabaseName, currentTableName)));
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
}
