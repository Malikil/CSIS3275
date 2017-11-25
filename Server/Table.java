package Server;

import java.io.Serializable;
import java.io.SyncFailedException;

public class Table implements Serializable
{
	private DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> tree;
	int nextKey = 0;
	DefinitelyNotArrayList<Integer> unusedKeys = new DefinitelyNotArrayList<>();
	
	public Column[] getColumns() { return columns.toArray(new Column[columns.size()]); }
	public Entry[] asArray() { return tree.toArray(new Entry[tree.size()]); }
	
	public Table()
	{
		 tree = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList<>();
	}	
	
	public Table(Column firstColumn)
	{
		 tree = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList<>();
		 columns.add(firstColumn);
	}	

	public void addColumn(Column col)
	{
		columns.add(col);
		if (tree.size() > 0)
		{
			Entry[] allEntries = tree.toArray(new Entry[tree.size()]);
			for (Entry e : allEntries)
				e.addField(null);
		}
	}
	
	public void removeColumn(int index)
	{
		columns.remove(index);
		if (tree.size() > 0)
		{
			Entry[] allEntries = tree.toArray(new Entry[tree.size()]);
			for (Entry e : allEntries)
				e.deleteField(index);
		}
	}
	
	public void removeEntry(int key)
	{
		unusedKeys.add(key);
		int temp = Entry.getComparer();
		Entry.setComparer(-1);
		tree.delete(new Entry(key));
		Entry.setComparer(temp);
	}
	
	public void editEntry(Entry toEdit)
	{
		int temp = Entry.getComparer();
		Entry.setComparer(-1);
		tree.delete(new Entry(toEdit.getKey()));
		tree.add(toEdit);
		Entry.setComparer(temp);
	}
	
	public void addEntry(Comparable[] toAdd)
	{
		if (toAdd.length != columns.size())
			throw new IllegalArgumentException("Passed array is different length than column list");
		if(unusedKeys.size() == 0)
			tree.add(new Entry(nextKey++,toAdd));
		else
			tree.add(new Entry(unusedKeys.remove(unusedKeys.size() - 1),toAdd));
	}
	
	// TODO Find a better way to indicate when the server's tree and the client's tree don't match
	public void addEntry(Entry e) throws SyncFailedException
	{
		if (e.getKey() == nextKey)
		{
			tree.add(e);
			nextKey++;
		}
		else if (e.getKey() < nextKey)
		{
			if (unusedKeys.remove(new Integer(e.getKey())))
				tree.add(e);
			else
				throw new SyncFailedException("Key is already in use");
		}
		else
		{
			for (; nextKey < e.getKey(); nextKey++)
				unusedKeys.add(nextKey);
			tree.add(e);
			nextKey++;
		}
	}
	
	public String[] getColumnNames()
	{
		String[] temp = new String[columns.size()]; 
		for(int i = 0; i < columns.size(); i++)
		{
			temp[i] = (columns.get(i).getName());
		}
		return temp;
		
	}
	
	/*public Comparable[][] getTable()
	{
		Entry[] entries = tree.toArray(new Entry[tree.size()]);
		Comparable[][] tableArray = new Comparable[tree.size()][entries[0].getFieldSize()];
		
		for(int i = 0; i < tree.size(); i++)
		{			
			tableArray[i] = entries[i].getData();
		}
		
		return tableArray;
	}*/
}
