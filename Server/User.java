package Server;

public class User implements Comparable<User>{

	private String username;
	private String password;
	private AVLTree<String> databases;
	
	public User(String uName, String pWord, String[] dBases)
	{
		username = uName;
		password = pWord;
		databases = new AVLTree<String>();
		for(int i = 0; i < dBases.length ; i++)
			databases.add(dBases[i]);
	}
	
	public User(String uName)
	{
		username = uName;
	}
	
	public User(User newUser, String[] dBases) 
	{
		this(newUser.getUsername(),newUser.getPassword(),dBases);
	}
	
	public User(User newUser, String pWord)
	{
		this(newUser.getUsername(),pWord,newUser.getDatabases());
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String[] getDatabases()
	{
		return databases.toArray(databases.toArray(new String[databases.size()]));
	}
	
	public void changePassword(String newPass)
	{
		password = newPass;
	}
	
	public void changeUsername(String newUsername)
	{
		password = newUsername;
	}
	
	public void addDatabases(String[] newDBList)
	{
		for(int i = 0; i < newDBList.length; i++)
		{
			databases.add(newDBList[i]);
		}
	}
	
	public void changeDatabase(String[] newDBList)
	{
		AVLTree<String> newDBs = new AVLTree<String>();
		for(int i = 0 ; i < newDBList.length ; i++)
			newDBs.add(newDBList[i]);
		databases = newDBs;
	}
	
	public void deleteDatabase(String databaseToDelete)
	{
		databases.delete(databaseToDelete);
	}
	
	@Override
	public int compareTo(User user)
	{
		return username.compareTo(user.getUsername());
	}	
}
