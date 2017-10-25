import java.io.Serializable;
import java.util.LinkedList;

public class tuple implements Serializable, Comparable{
	
	private myLL<fieldData> dataList = new myLL<fieldData>();
	private int searchField = 0;
	
	
	public tuple(myLL<fieldData> record)
	{
		dataList = record;
	}
	
	public String toString()
	{
		String tupleRecord ="";

		for(int i = dataList.size()-1; i > -1; i--) 		
		{
			if(dataList.get(i) == null)
			{
				tupleRecord += "null" + "\t";
			}
			else
			tupleRecord += ((fieldData) dataList.get(i)).toString() + "\t";
		}	
			
			
			
		dataList.toString();

		return tupleRecord;
		
	}
	
	public void setCompare(int n)
	{
		searchField = n;
	}

	public void removeFieldEntry(int n) {
			dataList.remove(n);
		
	}
	
	public void setNull(int n)
	{
		
		dataList.push(null);
		
		
	}
	
	public fieldData getFData(int n)
	{
		return (fieldData) dataList.get(n);
	}
	
	public fieldData getFData()
	{
		return (fieldData) dataList.get(searchField);
	}
	
	public void setFData(int fieldnum, Comparable editedData)
	{
		 ((fieldData) dataList.get(fieldnum)).set(editedData);
	}
	
	public void setsearchField(int n)
	{
		searchField = n;
	}

	@Override
	public int compareTo(Object o) {
		
		  
				fieldData a = (fieldData) dataList.get(searchField);
				tuple comparator = (tuple)o;
				fieldData b = (fieldData) comparator.getFData(searchField);
				return a.compareTo(b);

	}
}
