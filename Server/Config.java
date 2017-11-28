package Server;

import java.io.Serializable;

public class Config implements Serializable
{
	private DefinitelyNotArrayList<User> userList;
	private int entryKey;
	
	Config(DefinitelyNotArrayList<User> userList2, int key)
	{
		userList = userList2;
		entryKey = key;
	}
	
	public DefinitelyNotArrayList<User> getUserList()
	{
		return userList;
	}
	public int getEntryKey()
	{
		return entryKey;
	}
}
