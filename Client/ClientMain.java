package Client;

import Server.Command;
import Server.Entry;
import Server.AVLTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain implements Client, Runnable
{
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private BufferedReader strIn;
	private PrintWriter strOut;
	private ClientGUI gui;
	
	public ClientMain(Socket sock) throws IOException
	{
		objIn = new ObjectInputStream(sock.getInputStream());
		objOut = new ObjectOutputStream(sock.getOutputStream());
		strIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		strOut = new PrintWriter(sock.getOutputStream());
		gui = new ClientGUI(this);
	}
	
	public static void main(String[] args)
	{
		LoginGUI login = new LoginGUI();
		Socket sock;
		
		while (true)
		{
			login.setVisible(true);
			if (login.isCancelled()) return;
			try
			{
				sock = new Socket(login.getEnteredIP(), 8001); System.out.println("Opened socket");
				ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream()); System.out.println("Got output stream");
				ObjectInputStream in = new ObjectInputStream(sock.getInputStream()); System.out.println("Got input stream");
				out.writeObject(new String[] { login.getEnteredUser(), login.getEnteredPass() }); System.out.println("Sent user/pass");
				Command conf = (Command)in.readObject(); System.out.println("Response received");
				System.out.println("Server responded with " + conf.toString()); // TODO DEBUG
				if (conf == Command.CONNECTION_SUCCESS)
					break;
				else
					login.setMessage(conf);
						
			}
			catch (IOException ex)
			{
				System.out.println("Error communicating with server:\t" + ex.getMessage());
			}
			catch (ClassNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
		
		try
		{
			new Thread(new ClientMain(sock)).start();
		}
		catch (IOException ex)
		{
			try	{ sock.close(); }
			catch (IOException io) { /* Couldn't close socket */ }
		}
	}

	@Override
	public void run()
	{
		try
		{
			// Get databases
			gui.setDatabases((String[])objIn.readObject());
			while (true)
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
					break;
				default:
					throw new IOException("Unexpected server command");
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex) { /* Skip to finally */ }
		finally
		{
			try
			{
				objIn.close();
				objOut.close();
				strIn.close();
				strOut.close();
			}
			catch (IOException ex) { /* Couldn't close streams */ }
		}
	}

	@Override
	public void sendEdits(Entry e)
	{
		try
		{
			objOut.writeObject(Command.EDIT_ENTRY);
			objOut.writeObject(e);
		}
		catch (IOException ex)
		{
			// I'm starting to get tired of writing catch blocks for IOException
		}
	}
}