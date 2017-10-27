package Server;

import java.io.Serializable;
import java.util.Date;

public class Fields implements Serializable, Comparable<Fields>{
	private String fieldName;
	int fieldType;
	String fieldTypeName;
	int fieldnumber;
	
	
	public Fields(String name, int type, int n2)
	{
		fieldName = name;
		fieldType = type;
		fieldnumber = n2;
		setField(type);
	}
	





	
	public void setField(int n)
	{
		fieldType = n;
		if(n==0)
		{
			fieldTypeName = "String";
		}
		else if(n==1)
		{
			fieldTypeName = "Integer";
		}
		else if(n==2)
		{
			fieldTypeName = "Double";
		}
		else if(n==3)
		{
			fieldTypeName = "Date";
		}
	}







	public String getfieldName() {
		
		return fieldName;
	}

	@Override
	public int compareTo(Fields o) {
		return fieldName.compareTo(o.getfieldName());
	}



}
