package Server;

public interface Server
{
	public void messageReceived(String message);
	public String[] getUserDatabases(String user);
	public String[] getTableList(String database);
	public AVLTree<Entry> getTable(String tableName);
	public void createTable(String tableName);
}
