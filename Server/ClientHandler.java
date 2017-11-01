package Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	InputStream in;
	OutputStream out;
	public ClientHandler(Socket connection)
	{
		// Receive username and password from client
		// Check login info
		
	}
	
	@Override
	public void run()
	{
		// 
	}
}
