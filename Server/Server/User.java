package Server;

public class User implements Comparable<Integer>{

	private String username;
	private String password;
	private int userKey;
	private DefinitelyNotArrayList<String> databases;
	
	public User(String uName, String pWord, String[] dBases, int key)
	{
		userKey = key;
		username = uName;
		password = pWord;
		for(int i = 0; i < dBases.length ; i++)
			databases.add(dBases[i]);
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
		DefinitelyNotArrayList<String> newDBs = new DefinitelyNotArrayList<String>();
		for(int i = 0 ; i < newDBList.length ; i++)
			newDBs.add(newDBList[i]);
		databases = newDBs;
	}
	
	public void deleteDatabase(String databaseToDelete)
	{
		//databases.remove(databases.search(databaseToDelete));    TODO 
	}
	
	@Override
	public int compareTo(Integer key)
	{
		return key.intValue() - userKey;
	}
	
	
	
}
