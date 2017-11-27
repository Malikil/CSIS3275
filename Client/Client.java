package Client;

import Server.Table;

public interface Client
{
	public void setDatabaseList(String[] list);
	public void createDatabase();
	public void deleteDatabase();
	public void getTableNames(String database);
	public void createTable();
	public void deleteCurrentTable();
	public void getTable(String tableName);
	public String[] getColumnNames();
	public void addColumn();
	public void deleteColumn(int selectedIndex);
	public void createEntry(String[] headers);
	public void deleteEntry(int entryKey);
	public void editEntry(int entryIndex);
	public void setTable(Table newTable);
	public void applySearch(String[] values, String[] comparisons, int[] fields);
	public void editUser();
	public void addUser();
	public void deleteUser();

}