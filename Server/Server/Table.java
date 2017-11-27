package Server;

import java.io.Serializable;

public class Table implements Serializable
{
	private DefinitelyNotArrayList<Column> columns;
	AVLTree<Entry> tree;
	
	public Column[] getColumns() { return columns.toArray(new Column[columns.size()]); }
	public Entry[] asArray() { return tree.toArray(new Entry[tree.size()]); }
	
	public Table()
	{
		 tree = new AVLTree<Entry>();
		 columns = new DefinitelyNotArrayList<>();
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
	
	public void removeColumn(Integer index)
	{
		columns.remove(index.intValue());
		if (tree.size() > 0)
		{
			Entry[] allEntries = tree.toArray(new Entry[tree.size()]);
			for (Entry e : allEntries)
				e.deleteField(index);
		}
	}
	
	public void removeEntry(int key)
	{
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
	
	public void addEntry(Entry e)
	{
		int temp = Entry.getComparer();
		Entry.setComparer(-1);
		if (!tree.contains(e))
		{
			tree.add(e);
			Entry.setComparer(temp);
		}
		else
		{
			Entry.setComparer(temp);
			throw new IllegalArgumentException("Entry is already on tree");
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
}
