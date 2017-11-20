package Server;

import java.io.Serializable;

public class Table implements Serializable {
	DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> entries;
	int nextPK = 0;
	DefinitelyNotArrayList<Integer> unusedPKs = new DefinitelyNotArrayList<Integer>(); 
	
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList();
	}	
	
	public <T> void addField(Column toAdd)
	{
		columns.add(toAdd);
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
		columns.remove(toRmv);
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
	
	public void rmvEntry(Entry toDelete)
	{
		unusedPKs.add(toDelete.getKey());
		entries.delete(toDelete);
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
		String[] temp = new String[columns.size()]; 
		for(int i = 0; i<columns.size();i++)
		{
			temp[i] = (columns.get(i).name);
		}
		return temp;
		
	}
	
	public Object[][] getEntries()
	{
		
		AVLNode base = entries.minimum();
		Entry entry = (Entry) base.getValue();
		Object[][] entriesArray = new Object[entries.getCount()][entry.getFieldSize()+1];
		
		for(int i = 0; i< entries.getCount(); i++)
		{			
			 entry = (Entry) base.getValue();
			 entriesArray[i] = entry.getData();
				base = base.getNext();

		}
		
		return entriesArray;
	}
}
