package Server;

import java.io.Serializable;

public class Message implements Serializable
{
	private static final long serialVersionUID = -5903038581933510537L;
	
	private Command type;
	private Object data;
	
	public Message(Command messageType, Object messageData)
	{
		type = messageType;
		data = messageData;
	}
	
	public Command getCommandType() { return type; }
	
	public String getMessage()
	{
		if (data instanceof String)
			return (String)data;
		else
			return null;
	}
	
	public Entry getEntry()
	{
		return (Entry)data;
	}
	
	public Comparable[] getNewEntry()
	{
		return (Comparable[]) data;
	}
	
	public String getTableName()
	{
			return (String)data;
	}
	
	public Table getTable()
	{
			return (Table)data;
	}
	
	public String getDatabase()
	{
			return (String)data;
	}
	
	public String[] getDatabaseList()
	{
		if (data instanceof String[])
			return (String[])data;
		else
			return null;
	}
	
	public String[] getTableNames()
	{
			return (String[])data;
	}
	
	public String[] getLogin()
	{
		if (data instanceof String[])
			return (String[])data;
		else
			return null;
	}
	
	public Column[] getColumns()
	{
		return (Column[])data;
	}
	
	public Integer getColumnIndex()
	{
		return (Integer)data;
	}
	
	public Integer getKey()
	{
		return (Integer)data;
	}
	
	public User getUser()
	{
		return (User)data;
	}

	public String getUsername() {
		return (String) data;
	}
	
	public String[] getUserList() {
		return (String[])data;
	}
}
