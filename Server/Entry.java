package Server;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Entry implements Comparable<Entry>, Serializable
{
	private static final long serialVersionUID = 481061902950995857L;
	
	private static int comparer;
	public static int getComparer() { return comparer; }
	public static void setComparer(int fieldNumber) { comparer = fieldNumber; }
	
	private MyAL<Comparable> fields; //TODO: Need to change ArrayList to LinkedList 
	private final int primaryKey;
	
	public Entry(int key)
	{
		fields = new MyAL<Comparable>(); //TODO: Need to change ArrayList to LinkedList 
		primaryKey = key;
	}
	
	public Entry(int key, Comparable[] data)
	{
		fields = new MyAL<Comparable>(data.length);
		for (Comparable d : data)
			fields.add(d);
		this.primaryKey = key;
	}
	
	public Comparable getField(int fieldNumber)
	{
		return fields.get(fieldNumber);
	}
	
	public void deleteField(int fieldNumber)
	{
		fields.remove(fieldNumber);
	}
	
	public void addField(int fieldNumber, Comparable data)
	{
		fields.add(fieldNumber, data);
	}
	
	public void addField(Comparable data)
	{
		fields.add(data);
	}
	
	public void setfield(int fieldNumber, Comparable data)
	{
		fields.set(fieldNumber, data);
	}
	
	public int getKey()
	{
		return primaryKey;
	}
	
	@Override
	public int compareTo(Entry o) 
	{
		if (comparer == -1)
		{
			return primaryKey - o.primaryKey;
		}
		return fields.get(comparer).compareTo(o.fields.get(comparer));
	}
	
	public int compareTo(Entry o, int comparisonField) 
	{
		if (comparer == -1)
		{
			return primaryKey - o.primaryKey;
		}
		return fields.get(comparer).compareTo(o.fields.get(comparer));
	}
}
