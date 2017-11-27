package Client;

import Server.Table;

public interface Client
{
	public void setDatabaseList(String[] list);
	public void getTableNames(String database);
	public void deleteCurrentTable();
	public void getTable(String tableName);
	public void addColumn();
	public void deleteColumn(int selectedIndex);
	public void createEntry(String[] headers);
	public void deleteEntry(int entryKey);
	public void editEntry(int entryIndex);
	public void setTable(Table newTable);
	public String[] getColumnNames();
	public void applySearch(String[] values, String[] comparisons, int[] fields);
	public void createTable();
	public void createDatabase();
}