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
	
	Server parent;
	
	public ClientHandler(Socket connection, Server server)
	{
		parent = server;
		// Establish connections
		try
		{
			objIn = new ObjectInputStream(connection.getInputStream());
			objOut = new ObjectOutputStream(connection.getOutputStream());
			
			strIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			strOut = new PrintWriter(connection.getOutputStream());
		}
		catch (IOException ex)
		{
			// Problem establishing connections 
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
					// Receive username and password from client
					String user = strIn.readLine();
					String pass = strIn.readLine();
					
					String nextLine;
					while ((nextLine = userList.readLine()) != null)
					{
						String[] userInfo = nextLine.split(",");
						if (userInfo[0].equals(user))
							if (userInfo[1].equals(pass))
							{
								// Send success to client
								objOut.writeObject(Command.CONNECTION_SUCCESS);
								// Send available databases to client
								objOut.writeObject(parent.getUserDatabases(userInfo[0]));
								loggedIn = true;
								break;
							}
							else
							{
								objOut.writeObject(Command.INCORRECT_PASSWORD);
								break;
							}
					}
					if (!loggedIn)
					{
						objOut.writeObject(Command.INCORRECT_USER);
					}
					
				}
				catch (IOException ex)
				{
					// Error receiving user/pass from client
				}
			} while (!loggedIn);
			userList.close();
		}
		catch (IOException ex)
		{
			// User file couldn't be found
		}
		
		// Client is logged in, now wait for commands
		while (true)
		{
			try
			{
				switch ((Command)objIn.readObject())
				{
				case ADD_COLUMN:
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
				case GET_TABLE:
					break;
				case MESSAGE:
					parent.messageReceived(strIn.readLine());
					break;
				}
			}
			catch (ClassNotFoundException | IOException ex)
			{
				
			}
		}
	}
}
