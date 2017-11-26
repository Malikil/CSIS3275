package Client;

import Server.Table;

public interface Client
{
	public void setDatabaseList(String[] list);
	public void getTableNames(String database);
	public void createTable(String string);
	public void deleteCurrentTable();
	public void setCurrentTableName(String tableName);
	public void getTable(String tableName);
	public void addColumn(String string, int i);
	public void deleteColumn(int selectedIndex);
	public void createEntry(Comparable[] entryData);
	public void deleteEntry(int entryKey);
	public void editEntry(int entryIndex);
	public void setTable(Table newTable);
}