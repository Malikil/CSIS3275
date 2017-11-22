package Server;

public class Column
{
	public static final int STRING = 0;
	public static final int INTEGER = 1;
	public static final int DOUBLE = 2;
	public static final int DATE = 3;
	
	private String name;
	private int type;
	
	public String getName() { return name; }
	public int getType() { return type; }

	public void setName(String name) { this.name = name; }
	public void setType(int type) { this.type = type; }
	
	public Column(String fieldName, int fieldType)
	{
		name = fieldName;
		type = fieldType;
	}
}
