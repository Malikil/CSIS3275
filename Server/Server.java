package Server;

public interface Server
{
	public void messageReceived(String message);
	public String[] getUserDatabases(String user);
	public String[] getTableList(String database);
	public Table getTable(String dbname, String tableName);
	public void saveTable(String dbName, String tableName, Table table);
	public void addEntry(String database, String table, Comparable[] data);
}
