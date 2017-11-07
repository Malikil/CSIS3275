package Client;

public class ClientMain implements Client
{
	public static void main(String[] args)
	{
		LoginGUI login = new LoginGUI();
		while (true)
		{
			login.setVisible(true);
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
			if (login.isVisible()) break;
			// Right now, this should always return false, so the loop will be infinite unless
			// the user cancels
		}
		
		ClientGUI gui = new ClientGUI(new ClientMain());
		gui.setVisible(true);
		
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