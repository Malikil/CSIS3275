package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private Server parent;
	private String currentDatabaseName;
	private String currentTableName;
	private User currentUser;
	
	public User getCurrentUser() { return currentUser; }
	public String getCurrentTableName() { return currentTableName; }
	public String getCurrentDatabaseName() { return currentDatabaseName; }
	
	public void setCurrentTableName(String tableName) { currentTableName = tableName; }
	
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
			String[] userPass = ((Message)objIn.readObject()).getLogin();
			User parentUser = parent.getUser(userPass[0]);
			if(parentUser != null)
			{
				if(parentUser.equals(new User(userPass[0], userPass[1])))
				{
					objOut.writeObject(new Message(Command.CONNECTION_SUCCESS, parentUser));
					currentUser = parentUser;
					currentDatabaseName = "";
					currentTableName = "";
				}
				else
					objOut.writeObject(new Message(Command.INCORRECT_PASSWORD, null));
			}
			if (currentUser == null)
			{
				objOut.writeObject(new Message(Command.INCORRECT_USER, null));
				parent.disconnectClient(this);
				objOut.close();
				objIn.close();
				return;
			}
		} 
		catch (ClassNotFoundException e) 
		{	} // TODO
		catch (IOException e) 
		{	} // TODO
		
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
					currentTableName = "";
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
				case ADD_DATABASE:
					parent.createDatabase(received.getDatabase());
					break;
				case ADD_USER:
					parent.createUser(received.getUser());
					break;
				case LOGOFF:
					parent.disconnectClient(this);
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
			parent.disconnectClient(this);
			try
			{
				objOut.close();
				objIn.close();
			}
			catch (IOException e)
			{
				System.out.println("Couldn't close sockets" + e.getMessage()); // TODO
			}
		}
	}
}
