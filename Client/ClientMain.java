package Client;

public class ClientMain implements Client
{
	public static void main(String[] args)
	{
		LoginGUI login = new LoginGUI();
		while (true)break;
		
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