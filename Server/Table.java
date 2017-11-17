package Server;


public class Table {
	DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> entries;
	
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList();
	}	
	
	public <T> void addField(Column toAdd)
	{
		columns.add(toAdd);
		AVLNode base = entries.minimum();
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.addField(null);
			base = base.getNext();
		}
	}
	
	public void rmvField(int toRmv)
	{
		columns.remove(toRmv);
		AVLNode base = entries.minimum();
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.deleteField(toRmv);
			base = base.getNext();
		}
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
