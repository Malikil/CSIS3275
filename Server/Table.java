package Server;


public class Table {
	MyAL<Field> fields;
	AVLTree<Entry> entries;
		
	public Table()
	{
		 entries = new AVLTree<Entry>();
	}	
	
	public void addField(Field toAdd)
	{
		//Addfield to linkedlist
	}
	
	public void rmvField(Field toRmv)
	{
		//field to linkedlist
	}
	
	public void rmvEntry(Entry toDelete)
	{
		
	}
	
	public void editEntry(Entry toEdit)
	{
		
	}
	
	public void addEntry(Entry toAdd)
	{
		
	}
	
}
