package Server;

public interface Server
{
	public void messageReceived(String message);
	public String[] getUserDatabases(String user);
	public String[] getTableList(String database);
	public Table getTable(String dbname, String tableName);
	public void createTable(String tableName);
	public void updateTable(String db, String table, Table newTable);
}
