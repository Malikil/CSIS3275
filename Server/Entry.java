package Server;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class Entry implements Comparable<Entry>, Serializable
{
	private static final long serialVersionUID = 481061902950995857L;
	
	private static int comparer = -1;
	public static int getComparer() { return comparer; }
	public static void setComparer(int fieldNumber) { comparer = fieldNumber; }
	
	private final int primaryKey;
	private DefinitelyNotArrayList<Comparable> fields;
	
	public Entry(int key)
	{
		fields = new DefinitelyNotArrayList<Comparable>();
		primaryKey = key;
	}
	
	public Entry(int key, Comparable[] data)
	{
		fields = new DefinitelyNotArrayList<Comparable>(data.length);
		for (Comparable d : data)
			fields.add(d);
		primaryKey = key;
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
		fields.add(data, fieldNumber);
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
