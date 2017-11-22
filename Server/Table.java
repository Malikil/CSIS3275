package Server;

import java.io.Serializable;

public class Table implements Serializable {
	private DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> entries;
	int nextPK = 0;
	DefinitelyNotArrayList<Integer> unusedPKs = new DefinitelyNotArrayList<Integer>(); 
	
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
		 setColumns(new DefinitelyNotArrayList());
	}	
	
	public <T> void addField(Column toAdd)
	{
		getColumns().add(toAdd);
		if(nextPK !=0)
		{
			AVLNode base = entries.minimum();
			while(base != null)
			{
				Entry entry = (Entry) base.getValue();
				entry.addField(null);
				if(base != entries.maximum())
				{
					base = base.getNext();
				}
				else
					base = null;
			}	
		}
	}
	
	public void rmvField(int toRmv)
	{
		getColumns().remove(toRmv);
		AVLNode base = entries.minimum();
		while(base != null)
		{
			Entry entry = (Entry) base.getValue();
			entry.deleteField(toRmv);
			if(base != entries.maximum())
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
		entries.delete(new Entry(toDelete));
	}
	
	public void editEntry(Entry toEdit)
	{
		entries.replace(toEdit);
		
	}
	
	public void addEntry(Comparable[] toAdd)
	{
		if(unusedPKs.get(0) == null){
			int pkey = nextPK;
			entries.add(new Entry(pkey,toAdd));
			nextPK++;
		}
		else
		{
			int pkey = unusedPKs.get(0);
			unusedPKs.remove(0);
			entries.add(new Entry(pkey,toAdd));
			}
	}
	
	public String[] getColumnNames()
	{
		String[] temp = new String[getColumns().size()]; 
		for(int i = 0; i<getColumns().size();i++)
		{
			temp[i] = (getColumns().get(i).name);
		}
		return temp;
		
	}
	
	public Comparable[][] getEntries()
	{
		
		AVLNode base = entries.minimum();
		Entry entry = (Entry) base.getValue();
		Comparable[][] entriesArray = new Comparable[entries.getCount()][entry.getFieldSize()+1];
		
		for(int i = 0; i< entries.getCount(); i++)
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
