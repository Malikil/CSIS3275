package Server;

import java.io.Serializable;

public class Column implements Serializable {
	public static final int STRING = 0;
	public static final int INTEGER = 1;
	public static final int DOUBLE = 2;
	public static final int DATE = 3;
	String name;
	int type;
	
	public Column(String columnName, int columnType)
	{
		name = columnName;
		type = columnType;
	}
	

}
