package Client;

import Server.Entry;
import Server.Message;

public interface Client
{
	public void getTableNames(String database);
	public void createTable(String table);
	public void deleteTable(String table);
	public void getTable(String tableName);
	public void addColumn();
	public void deleteColumn(int selectedIndex);
	public void createEntry(String[] headers);
	public void deleteEntry(int entryKey);
	public void editEntry(int entryIndex);
}