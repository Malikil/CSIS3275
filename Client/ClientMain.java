package Client;

import Server.Command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain implements Client
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private BufferedReader strIn;
	private PrintWriter strOut;
	
	private void setConnections(Socket sock) throws IOException
	{
		objIn = new ObjectInputStream(sock.getInputStream());
		objOut = new ObjectOutputStream(sock.getOutputStream());
		strIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		strOut = new PrintWriter(sock.getOutputStream());
	}
	
	public Command checkLogin(String[] info)
	{
		try
		{
			objOut.writeObject(info);
			return (Command)objIn.readObject();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return Command.INCORRECT_USER;
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		ClientMain client = new ClientMain();
		LoginGUI login = new LoginGUI();
		while (true)
		{
			login.setVisible(true);
			try
			{
				client.setConnections(new Socket(login.getEnteredIP(), 8001));
				Command conf = client.checkLogin(new String[]
						{ login.getEnteredUser(), login.getEnteredPass() }); 
				if (conf == Command.CONNECTION_SUCCESS)
					break;
				else
					login.setMessage(conf);
						
			}
			catch (IOException ex)
			{
				
			}
			// Connect to server
			/* Depending on whether we decide to use an array or individual strings.
			 * An array will be more secure, but will require more work and I don't
			 * know off the top of my head if arrays are natively serializable
			String[] userPass = { login.getEnteredUser(), login.getEnteredPass() };
			server.checkInfo(userPass);
					- OR -
			server.checkInfo(login.getEnteredUser(), login.getEnteredPass());
			*/
			if (login.isCancelled()) return;
		}
		
		ClientGUI gui = new ClientGUI(client);
		gui.setVisible(true);
		
		while (true)
		{
			//switch ()
		}
	}
	
	@Override
	public void doSomething()
	{
		
	}

	@Override
	public void doSomethingElse()
	{
		
	}
}