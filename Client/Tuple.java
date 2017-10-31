package Client;

import java.io.Serializable;
import java.util.LinkedList;

public class Tuple implements Serializable, Comparable{
	
	private MyLL<FieldData> dataList = new MyLL<FieldData>();
	private int searchField = 0;
	
	
	public Tuple(MyLL<FieldData> record)
	{
		dataList = record;
	}
	
	public void settuple(MyLL<FieldData> record)
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
			tupleRecord += ((FieldData) dataList.get(i)).toString() + "\t";
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
	
	public FieldData getFData(int n)
	{
		return (FieldData) dataList.get(n);
	}
	
	public FieldData getFData()
	{
		return (FieldData) dataList.get(searchField);
	}
	
	public void setFData(int fieldnum, Comparable editedData)
	{
		 ((FieldData) dataList.get(fieldnum)).set(editedData);
	}
	
	public void setsearchField(int n)
	{
		searchField = n;
	}

	@Override
	public int compareTo(Object o) {
		
		  
				FieldData a = (FieldData) dataList.get(searchField);
				Tuple comparator = (Tuple)o;
				FieldData b = (FieldData) comparator.getFData(searchField);
				return a.compareTo(b);

	}
}
