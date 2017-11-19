package Client;

import Server.Entry;

public interface Client
{
	public void sendEdits(Entry e);
	public void getTableNames(String database);
	public void deleteTable(String table, String database);
	public void getTable(String database);
}