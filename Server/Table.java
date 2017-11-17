package Server;


public class Table {
	DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> entries;
	
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList();
	}	
	
	public void addField(Column toAdd)
	{
		columns.add(toAdd);
	}
	
	public void rmvField(Column toRmv)
	{
		columns.remove(toRmv);
	}
	
	public void rmvEntry(Entry toDelete)
	{
		entries.delete(toDelete);
	}
	
	public void editEntry(Entry toEdit)
	{
		//toEdit.getKey() entries. TODO.
	}
	
	public void addEntry(Entry toAdd)
	{
		entries.add(toAdd);
	}
	
}
