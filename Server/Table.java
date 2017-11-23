package Server;

import java.io.Serializable;

public class Table implements Serializable {
	private DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> tree;
	int nextKey = 0;
	DefinitelyNotArrayList<Integer> unusedKeys = new DefinitelyNotArrayList<Integer>(); 
	
		
	public Table()
	{
		 tree = new AVLTree<Entry>();
		 setColumns(new DefinitelyNotArrayList<>());
	}	
	
	public <T> void addColumn(Column toAdd)
	{
		columns.add(toAdd);
		Entry[] allEntries = tree.toArray(new Entry[tree.size()]);
		for (Entry e : allEntries)
			e.addField(null);
	}
	
	public void removeColumn(int toRmv)
	{
		columns.remove(toRmv);
		AVLNode base = tree.minimum();
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.deleteField(toRmv);
			if(base != tree.maximum())
			{
				base = base.getNext();
			}
			else
				base = null;
		}
	}
	
	public void rmvEntry(int toDelete)
	{
		unusedPKs.add(toDelete);
		tree.delete(new Entry(toDelete));
	}
	
	public void editEntry(Entry toEdit)
	{
		tree.delete(new Entry(toEdit.getKey()));
		tree.add(toEdit);
		
	}
	
	public void addEntry(Comparable[] toAdd)
	{
		if(unusedPKs.get(0) == null){
			int pkey = nextPK;
			tree.add(new Entry(pkey,toAdd));
			nextPK++;
		}
		else
		{
			int pkey = unusedPKs.get(0);
			unusedPKs.remove(0);
			tree.add(new Entry(pkey,toAdd));
			}
	}
	
	public String[] getColumnNames()
	{
		String[] temp = new String[getColumns().size()]; 
		for(int i = 0; i<getColumns().size();i++)
		{
			temp[i] = (getColumns().get(i).getName());
		}
		return temp;
		
	}
	
	public Comparable[][] getEntries()
	{
		
		AVLNode base = tree.minimum();
		Entry entry = (Entry) base.getValue();
		Comparable[][] entriesArray = new Comparable[tree.size()][entry.getFieldSize()+1];
		
		for(int i = 0; i< tree.size(); i++)
		{			
			 entry = (Entry) base.getValue();
			 entriesArray[i] = entry.getData();
				base = base.getNext();

		}
		
		return entriesArray;
	}

	public DefinitelyNotArrayList<Column> getColumns() {
		return columns;
	}

	public void setColumns(DefinitelyNotArrayList<Column> columns) {
		this.columns = columns;
	}
}
