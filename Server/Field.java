package Server;

public class Field {
	public static final int STRING = 0;
	public static final int INTEGER = 1;
	public static final int DOUBLE = 2;
	public static final int DATE = 3;
	String name;
	FieldType type;
	
	public Field(String fieldName, FieldType fieldtype)
	{
		name = fieldName;
		type = fieldtype;
	}
}
