package Server;

import java.util.ArrayList;

public class Entry implements Comparable<Entry>
{
	@SuppressWarnings("rawtypes")
	private ArrayList<Comparable> fields;
	private static int comparer;
	private final int primaryKey;
	
	public Entry(int key)
	{
		primaryKey = key;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Entry o) 
	{
		if (comparer == -1)
		{
			return primaryKey - o.primaryKey;
		}
		return fields.get(comparer).compareTo(o.fields.get(comparer));
	}
	
	public static int getComparer()
	{
		return comparer;
	}
	
	public static void setComparer(int fieldNumber)
	{
		comparer = fieldNumber;
	}
	
	public void deleteField(int fieldNumber)
	{
		fields.remove(fieldNumber);
	}
	
	public void setfield(int fieldNumber, Comparable data)
	{
		fields.set(fieldNumber, data);
	}
	
	public int getKey()
	{
		return primaryKey;
	}
	
}
