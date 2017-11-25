package Client;

import Server.Column;
import Server.Table;

public interface Client
{
	public void setDatabaseList(String[] list);
	public void getTableNames(String database);
	public void createTable(Table table);
	public void deleteTable(String table);
	public void getTable(String tableName);
	public void addColumn();
	public void deleteColumn(int selectedIndex);
	public void createEntry(String[] headers);
	public void deleteEntry(int entryKey);
	public void editEntry(int entryIndex);
	public void setTable(Table newTable);
}