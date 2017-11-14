package Client;
import Server.*;
public class Message {
	private Command type;
	private String chat;
	private Entry entry;
	private String table;
	private String database;
	private String field;
	private int fieldtype;
	private String[] databases;
	private String[] tablenames;
	private String username;
	private String password;
	
	public Message(Command messagetype)
	{
		type = messagetype;
	}
	
	public Command getType()
	{
		return type;
	}
	
	void setChat(String Chat)
	{
		chat = Chat;
	}
	
	public String getChat()
	{
		return chat;
	}
	
	void setEntry(Entry record)
	{
		entry = record;
	}
	
	public Entry getEntry()
	{
		return entry;
	}
	
	void setTable(String tablename)
	{
		table = tablename;
	}
	
	public String getTable()
	{
		return table;
	}
	
	public void setDatabase(String DBname)
	{
		database = DBname;
	}
	
	public String getDatabase()
	{
		return database;
	}
	
	public void setDatabases(String[] DBnames)
	{
		databases = DBnames;
	}
	
	public String[] getDatabases()
	{
		return databases;
	}
	
	public void setTableNames(String[] TableNames)
	{
		tablenames = TableNames;
	}
	
	public String[] getTableNames()
	{
		return tablenames;
	}
	
	void setField(String fieldname, int fieldtype)
	{
		field = fieldname;
		this.fieldtype = fieldtype;
	}
	
	public String getField()
	{
		return field;
	}
	
	public int getFieldType()
	{
		return fieldtype;
	}
	
	void setUsername(String user)
	{
		username = user;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	void setPassword(String pass)
	{
		password = pass;
	}
	
	public String getPassword()
	{
		return password;
	}

}
