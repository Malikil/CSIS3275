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
	private BufferedReader strIn;
	private PrintWriter strOut;
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
			strOut = new PrintWriter(connection.getOutputStream(), true);
			strIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
			BufferedReader userList = new BufferedReader(new FileReader(new File("Users.txt")));
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
					if (!loggedIn)
						objOut.writeObject(new Message(Command.INCORRECT_USER, null));
					
				}
				catch (IOException ex)
				{
					// Error receiving user/pass from client
				}
				catch (ClassNotFoundException ex)
				{
					ex.printStackTrace();
					break;
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
				strOut.close();
				strIn.close();
			}
			catch (IOException | ClassNotFoundException e2) { System.out.println("Couldn't close sockets"); } // TODO DEBUG
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
				case ADD_COLUMN:
					Column toAdd = received.getColToAdd();
					currentTable.addField(toAdd);
					parent.updateTable(currentDatabaseName, currentTableName, currentTable);
					break;
				case ADD_ENTRY:
					Comparable[] entrydata = received.getAddEntry();
					currentTable.addEntry(entrydata);
					parent.updateTable(currentDatabaseName, currentTableName, currentTable);
					break;
				case ADD_TABLE:
					parent.createTable(received.getTableName());
					break;
				case DELETE_COLUMN:
					int ToRmv = received.getColToRmv();
					currentTable.rmvField(ToRmv);
					parent.updateTable(currentDatabaseName, currentTableName, currentTable);
					break;
				case DELETE_ENTRY:
					Entry entryToRmv = received.getEntry();
					currentTable.rmvEntry(entryToRmv);
					parent.updateTable(currentDatabaseName, currentTableName, currentTable);
					break;
				case DELETE_TABLE:
					String database2 = received.getDatabase();
					File db = new File(database2);
					File deleteFile = new File(db + "\\" + /*received.getTable()*/ "test.txt");
					deleteFile.delete();
					objOut.writeObject(new Message(Command.DELETE_TABLE, parent.getTableList(database2)));
					break;
				case EDIT_ENTRY:
					Entry entryToEdit = received.getEntry();
					currentTable.editEntry(entryToEdit);
					parent.updateTable(currentDatabaseName, currentTableName, currentTable);
					break;
				case GET_TABLE:
					System.out.println("Received GET_TABLE from client");
					System.out.println(currentDatabaseName);
					currentTableName = received.getTableName();
					System.out.println(currentTableName);
				    currentTable = parent.getTable(currentDatabaseName, currentTableName);
					objOut.writeObject(new Message(Command.GET_TABLE, currentTable)); System.out.println("Sent table to client");
					break;
				case GET_DATABASE:
					System.out.println("Received GET_DATABASE from client");
					currentDatabaseName  = received.getDatabase(); System.out.println("Received database name from client");
					objOut.writeObject(new Message(Command.TABLE_LIST, parent.getTableList(currentDatabaseName))); System.out.println("Sent databases to client");
					break;
				case MESSAGE:
					parent.messageReceived(strIn.readLine());
					break;
				case CONNECTION_SUCCESS:
					break;
				case INCORRECT_PASSWORD:
					break;
				case INCORRECT_USER:
					break;
				case LOGIN:
					break;
				default:
					break;
				}
			}
			catch (ClassNotFoundException | IOException ex)
			{
				
			}
		}
	}
	
	public void sendMessage(String message)
	{
		try
		{
			objOut.writeObject(Command.MESSAGE);
			strOut.println(message);
		}
		catch (IOException ex)
		{
			
		}
	}
}
