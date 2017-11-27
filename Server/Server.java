package Server;

public interface Server
{
	public String[] getUserDatabases(String user);
	public String[] getTableList(String database);
	public Table getTable(String dbname, String tableName);
	public void addEntry(String database, String table, Comparable[] data);
	public void addColumns(String databaseName, String tableName, Column[] columns);
	public void addTable(String databaseName, String tableName);
	public void deleteEntry(String databaseName, String tableName, Entry entryToDelete);
	public void deleteColumn(String databaseName, String tableName, Integer index);
	public void deleteTable(String databaseName, String tableName);
	public void editEntry(String databaseName, String tableName, Entry newEntry);
	public void createDatabase();
	//public void addColumn(String database)
}
