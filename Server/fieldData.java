import java.io.Serializable;
import java.util.Date;

public class fieldData implements Serializable, Comparable{
	
	private Comparable data;
	
	
	int fieldType;
	

	public <T extends Comparable<T>> fieldData(T rawData)
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
	
	public int compareTo(fieldData b) {
		if(b==null)
		{
			return 1;
		}
		else 
		return (data).compareTo(b.data);
	}

	public int compareTo(tuple tuple) {
		fieldData b = tuple.getFData();
		if((fieldData)b==null)
		{
			return 1;
		}
		else 
		return (data).compareTo(b.data);
	}

	@Override
	public int compareTo(Object o) {
		if((fieldData)o==null)
		{
			return 1;
		}
		else 
		{
			fieldData b = (fieldData)o;
			return (data).compareTo(b.data);
		}
	}

}
