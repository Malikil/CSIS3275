package Server;

import java.io.Serializable;

public class Table implements Serializable
{
	private DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> tree;
	int nextKey = 0;
	DefinitelyNotArrayList<Integer> unusedKeys = new DefinitelyNotArrayList<>(Integer.class);
	
	public Column[] getColumns() { return columns.toArray(); }
	public Entry[] asArray() { return tree.toArray(); }
	
	public Table()
	{
		 tree = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList<>(Column.class);
	}	
	
	public <T> void addColumn(Column col)
	{
		columns.add(col);
		Entry[] allEntries = tree.toArray();
		for (Entry e : allEntries)
			e.addField(null);
	}
	
	public void removeColumn(int index)
	{
		columns.remove(index);
		Entry[] allEntries = tree.toArray();
		for (Entry e : allEntries)
			e.deleteField(index);
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
		if(unusedKeys.size() == 0)
			tree.add(new Entry(nextKey++,toAdd));
		else
			tree.add(new Entry(unusedKeys.remove(unusedKeys.size() - 1),toAdd));
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
	
	public Comparable[][] getTable()
	{
		Entry[] entries = tree.toArray();
		Comparable[][] tableArray = new Comparable[tree.size()][entries[0].getFieldSize()];
		
		for(int i = 0; i < tree.size(); i++)
		{			
			tableArray[i] = entries[i].getData();
		}
		
		return tableArray;
	}
}
