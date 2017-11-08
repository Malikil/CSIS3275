package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerMain implements Server
{
	private ArrayList<ClientHandler> clientList;
	
	public static void main(String[] args)
	{
		// Create a new server window, and assign it a new server handler
		ServerMain server = new ServerMain();
		ServerGUI gui = new ServerGUI(server);
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
		clientList.add(client);
		new Thread(client).start();
	}
	
	public void sendToAll(String message)
	{
		for (ClientHandler client : clientList)
			client.sendMessage(message);
	}

	@Override
	public void messageReceived(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getUserDatabases(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTableList(String database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getTable(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}
}