package Server;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Entry implements Comparable<Entry>, Serializable
{
	private static final long serialVersionUID = 481061902950995857L;
	
	private static int comparer = -1;
	public static int getComparer() { return comparer; }
	public static void setComparer(int fieldNumber) { comparer = fieldNumber; }
	
	private DefinitelyNotArrayList<Comparable> fields; 
	private final int primaryKey;
	
	public Entry(int key)
	{
		fields = new DefinitelyNotArrayList<>(); 
		primaryKey = key;
	}
	
	public Entry(int key, Comparable[] data)
	{
		fields = new DefinitelyNotArrayList<>(data.length);
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
		fields.insert(fieldNumber, data);
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
		if(fields.get(comparer) == null || o.fields.get(comparer) == null )
		{
			if(fields.get(comparer) == null && o.fields.get(comparer) == null)
			{
				return 0;
			}
			if(fields.get(comparer) == null)
			{
				return ( (Comparable)("")).compareTo(o.fields.get(comparer));
			}
			if(o.fields.get(comparer) == null)
			{
				return fields.get(comparer).compareTo("");
			}
		}
		
		return fields.get(comparer).compareTo(o.fields.get(comparer));
	}
	
	public Comparable[] getData()
	{
		return fields.toArray(new Comparable[fields.size()]);
	}
	
	int getFieldSize()
	{
		return fields.size();
	}
}
