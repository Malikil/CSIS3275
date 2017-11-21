package Client;

import Server.Entry;
import Server.Message;

public interface Client
{
	public void sendEdits(Entry e);
	public void getTableNames(String database);
	public void deleteTable(String table, String database);
	public void getTable(String database);
	public void deleteColumn(int selectedIndex);
	public void addEntry(String[] headers);
	public void writeMessage(Message send);
	public void addColumn();
}