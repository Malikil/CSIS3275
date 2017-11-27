package Server;

import java.io.Serializable;

public class Config implements Serializable
{
	private AVLTree<User> userList;
	private int entryKey;
	
	Config(AVLTree<User> newUserList, int key)
	{
		userList = newUserList;
		entryKey = key;
	}
	
	public AVLTree<User> getUserList()
	{
		return userList;
	}
	public int getEntryKey()
	{
		return entryKey;
	}
}
