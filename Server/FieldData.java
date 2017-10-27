package Server;

import java.io.Serializable;
import java.util.Date;

public class FieldData implements Serializable, Comparable{
	
	private Comparable data;
	
	
	int fieldType;
	

	public <T extends Comparable<T>> FieldData(T rawData)
	{
		data = rawData;
	}
	
	public String toString()
	{

		return data.toString();
		
	}
	
	
	public <T extends Comparable<T>> void set(T editedData)
	{
		data = editedData;
	}
	
	public int compareTo(FieldData b) {
		if(b==null)
		{
			return 1;
		}
		else 
		return (data).compareTo(b.data);
	}

	public int compareTo(Tuple tuple) {
		FieldData b = tuple.getFData();
		if((FieldData)b==null)
		{
			return 1;
		}
		else 
		return (data).compareTo(b.data);
	}

	@Override
	public int compareTo(Object o) {
		if((FieldData)o==null)
		{
			return 1;
		}
		else 
		{
			FieldData b = (FieldData)o;
			return (data).compareTo(b.data);
		}
	}

}
