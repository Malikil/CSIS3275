import java.io.Serializable;
import java.util.Date;

public class fields implements Serializable, Comparable<fields>{
	private String fieldName;
	int fieldType;
	String fieldTypeName;
	int fieldnumber;
	
	
	public fields(String name, int type, int n2)
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
	public int compareTo(fields o) {
		return fieldName.compareTo(o.getfieldName());
	}



}
