package Server;


public class Table {
	DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> entries;
	int nextPK = 0;
	
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList();
	}	
	
	public <T> void addField(Column toAdd)
	{
		columns.add(toAdd);
		AVLNode base = entries.minimum();
		boolean isMax = false;
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.addField(null);
			if(base != entries.maximum())
			{
				base = base.getNext();
			}
			else
				isMax = false;
		}
	}
	
	public void rmvField(int toRmv)
	{
		columns.remove(toRmv);
		AVLNode base = entries.minimum();
		boolean isMax = false;
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.deleteField(toRmv);
			if(base != entries.maximum())
			{
				base = base.getNext();
			}
			else
				isMax = false;
		}
	}
	
	public void rmvEntry(Entry toDelete)
	{
		entries.delete(toDelete);
	}
	
	public void editEntry(Entry toEdit)
	{
		entries.replace(toEdit);
		
	}
	
	public void addEntry(Comparable[] toAdd)
	{
		int pkey = 0;
		entries.add(new Entry(pkey,toAdd));
		nextPK++;
	}
	
}
