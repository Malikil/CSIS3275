package Client;

import Server.Entry;

public interface Client
{
	public void sendEdits(Entry e);
	public void getTables(String database);
	public void deleteTable(String table, String database);
}