package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Table implements Serializable
{
	private static final long serialVersionUID = 4225828966516582087L;
	// Table organization
	private String tableName;
	
	// Data storage
	private MyLL<Fields> fieldsList = new MyLL<Fields>();
	private MyLL<Tuple> tupleList = new MyLL<Tuple>();
	private int numberoffields = 0;
	private String databaseName = "Default";
	private int assignPKID = 1;
	private MyLL<Integer> unusedPKID = new MyLL<Integer>();

	
	/**
	 * Create a table with "Default" as the Database
	 * @param name Table Name
	 */
	Table(String name)
	{
		tableName = name;
		addField("Primary Key ID", 1);
		Update();
	}
	
	/**
	 * Create a Table with first parameter as Table Name and second parameter as the Database
	 * @param name Table Name
	 * @param dbName Database Name
	 */
	Table(String name, String dbName)
	{
		tableName = name;
		databaseName = dbName;
		addField("Primary Key ID", 1);
		Update();
		
	}
	
	/**
	 * <pre>
	 * Create a Field with first parameter as Field Name and second parameter as the Field type.
	 * Field type legend is as follows:
	 * 	0 for String
	 * 	1 for Int 
	 * 	2 for Double 
	 * 	3 for Date
	 * </pre>
	 * @param s Field Name
	 * @param n Field Type
	 */
	public void addField(String s, int n)
	{
		fieldsList.push(new Fields(s,n,numberoffields));
		for(int i = 0; i<tupleList.size(); i++)
		{
			((Tuple) tupleList.get(i)).setNull(numberoffields);
		}
		numberoffields++;
		Update();
		
	}
	
	/**
	 * Removes a Field with position "n" from the Table
	 * @param n Field position
	 */
	public void removeField(int n)
	{
		for(int i = 0; i<tupleList.size(); i++)
		{
			((Tuple) tupleList.get(i)).removeFieldEntry(n);
		}
		fieldsList.remove(n);
		numberoffields--;
		Update();
	}
	
	/**
	 * @return Returns the number of Fields the Table
	 */
	public int getFieldNum()
	{
		return numberoffields;
	}
	
	/**
	 * Adds a Tuple to the Table
	 * @param rawData String array of the field data 
	 * @return Result
	 */
	public String addTuple(String[] rawData) 
	{
		String result ="";
		 if(rawData.length > numberoffields-1)
		 {
			 result = "Unforseen error, please contact admin";
		 }
		 else if(rawData.length < numberoffields-1)
		 {
			 result = "Please do not leave empty fields";
		 }
		 else 
		 {
			 MyLL<FieldData> tupleEntry = new MyLL<FieldData>();
			 
			 if(unusedPKID.size() == 0)
			 {
				 tupleEntry.push(new FieldData(assignPKID));
				 assignPKID++;
			 }
			 
			 else
			 {
				 int PK = (int) unusedPKID.pop();
				 tupleEntry.push(new FieldData(PK));
			 }
			 
			 
			 for(String s: rawData)
			 {
				 tupleEntry.push(new FieldData(s));
			 }
			 tupleList.push(new Tuple(tupleEntry));

			 
			 
		 }
		 Update();

		 return result;
	}
	
	/**
	 * Removes a Tuple from the Table at the position passed
	 * @param n Position of the Tuple
	 */
	public void removeTuple(int n)
	{
		tupleList.remove(n);
		 Update();

	}
	
	/**
	 * Writes current Table to a file. Overwrites current file.
	 */
	public void Update()
	{
		File folder = new File(databaseName);

		if (!folder.exists()) {
		    try{
		        folder.mkdir();
		    } 
		    catch(SecurityException se){
		    	//Do nothing
		    }        
		}
		
		 File file = new File(folder + "\\" + tableName + ".txt");
		 FileOutputStream fileOut = null;
		 ObjectOutputStream objWriter = null;
		try {
			fileOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			return;
		}
		try {
			objWriter = new ObjectOutputStream(fileOut);
		} catch (IOException e) {
			try {
				fileOut.close();
			} catch (IOException e1) {

			}
			return;
		}
		 try {
			objWriter.writeObject(this);
			 objWriter.close();

		} catch (IOException e) {
			return;
		}
	}
	
	/**
	 * Writes sorted version of a Table to a file. Overwrites current file.
	 */
	public void sortUpdate()
	{
		File folder = new File(databaseName);

		if (!folder.exists()) {
		    try{
		        folder.mkdir();
		    } 
		    catch(SecurityException se){
		    	//Should never happen really...
		    }        
		}
		
		 File file = new File(folder + "\\" + tableName + "sorted.txt");
		 FileOutputStream fileOut = null;
		 ObjectOutputStream objWriter = null;
		try {
			fileOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			return;
		}
		try {
			objWriter = new ObjectOutputStream(fileOut);
		} catch (IOException e) {
			try {
				fileOut.close();
			} catch (IOException e1) {

			}
			return;
		}
		 try {
			objWriter.writeObject(this);
			 objWriter.close();

		} catch (IOException e) {
			return;
		}
	}

	/**
	 * Gets current Tuple, mainly used to print Tuple results for user.
	 * @param i Tuple position
	 * @return Returns the Tuple in a String format
	 */
	public String getTuple(int i) {
		
		if(tupleList.element() == null)
		{
			return null;
		}
		else {
			Tuple toGet =  (Tuple) tupleList.get(i);
			
			return toGet.toString();
		}
		
	}
	
	/**
	 * Changes the contents of specified Tuple
	 * @param i Tuple position
	 * @param newtuple New Tuple values
	 * @return Result
	 */
	public String setTuple(int i, String[] newtuple) {
		
		if(tupleList.element() == null)
		{
			return "Record does not exist"; //Should technically never happen once GUI is made
		}
		else {
			
			MyLL<FieldData> tupleEntry = new MyLL<FieldData>();

			 for(String s: newtuple)
			 {
				 tupleEntry.push(new FieldData(s)); //Needs testing
			 }
			
			
			((Tuple) tupleList.get(i)).settuple(tupleEntry);
			Update();
			return "Record changed";
		}
		
	}
	
	/**
	 * @return Returns a '\t' separated string of all fields in the Table
	 */
	public String getFields() 
	{
		String fieldNames = ""; 
	
		for(int i = fieldsList.size()-1; i >-1; i--) 		
		{
			fieldNames += ((Fields) fieldsList.get(i)).getfieldName() + "\t";
		}
		
		return fieldNames;
	}
	
	/**
	 * @return Returns the number of entries in the Table
	 */
	public int numberofentries()
	{
		return tupleList.size();
	}
	
	/**
	 * Prints all entries in the Table to console
	 */
	public void printEntries() 
	{
		System.out.println(getFields().toString());
		for(int i = tupleList.size()-1; i >-1; i--) 		
		{
			System.out.println(getTuple(i).toString());
		}
		
	}
	
	/**
	 * Imports test file, will be updated to import chosen file to Table as Tuple entries
	 */
	public void importdata()
	{
		File file;
		BufferedReader bR = null;
		String str;
		try
		{
		file = new File("MockData.txt");
		bR = new BufferedReader(new FileReader(file));
		String entry = bR.readLine();
		while(entry != null)
		{
			String[] enterThis = entry.split(",");
			addTuple(enterThis);
			entry = bR.readLine();
		}
		}
		catch(IOException e)
		{
			str = "Can not find file";
			sendError(str);
		}
		finally
		{
			if(bR != null)
			{
				try 
				{
					bR.close();
				} 
				catch (IOException e) 
				{
					str = "Error on file close";
					sendError(str);
				}
			}
		}
	}
	
	/**
	 * Prints all Tuples with searched item in search field
	 * @param search Search item
	 * @param searchfield Field to search
	 */
	 public <T> void genBin(String search, int searchfield)
	 {
		
			quickSorter(0,tupleList.size(),searchfield);
			printEntries();
			int stuff = BinarySearch(search, 0, tupleList.size()-1, searchfield);
			if (stuff == -1)
			{
				System.out.println("No records found");
			}
			else {
				Tuple foundrecord =  (Tuple) tupleList.get(stuff++);
				while(search.compareTo(foundrecord.getFData(searchfield).toString()) == 0)
				{
					System.out.println(foundrecord.toString());
					foundrecord =  (Tuple) tupleList.get(stuff++);
				}
			}
			
			
	 }
	
	/**
	 * @param Search item, first index of search, last index of search, search field
	 * @return Tuple position with search item in search field
	 */	 
	public <T extends Comparable<T>> int BinarySearch(T searchItem, int first, int last, int searchfield)
	{
		MyLL<T> array = (MyLL<T>) tupleList;
		FieldData searchThis = new FieldData(searchItem);
		((Tuple) array.get(first)).setsearchField(searchfield);
		((Tuple) array.get(last)).setsearchField(searchfield);	
		if(last - first <2)
		{
			if(searchThis.compareTo((Tuple) array.get(first)) == 0)
			return first;
			else
			{
				return -1;
			}
		}
		
		int pos = -1;
		int mid = (first + last)/2;
		((Tuple) array.get(mid)).setsearchField(searchfield);	
		int diff = searchThis.compareTo((Tuple) array.get(mid));
		if(diff == 0)
		{
			pos = mid;
		}
		else if(diff > 0)
		{
			pos = BinarySearch(searchItem,mid+1,last,searchfield);
		}
		else
		{
			pos = BinarySearch(searchItem,first,mid-1,searchfield);
		}

			return pos;
	}
	
	/**
	 * Sorts current Table Tuples by chosen sort field
	 * @param Tuple starting position to sort, ending position to sort, field to sort by
	 */	
	public <T extends Comparable<T>> void quickSorter(int left, int right, int sortfield )
	{
		MyLL<T> list = (MyLL<T>) tupleList;
	
		if (list == null || left >= right-1 || left == right ) 
		{
			return; 
		}

	     int front = left - 1;
	     int last = right - 1;
	     T pivot = (T) list.get(last);
	     boolean needsWork = true;
	     while (needsWork)
	     {
	    	 ((Tuple) list.get(++front)).setsearchField(sortfield);
	    	 ((Tuple) list.get(--last)).setsearchField(sortfield);	         
	    	 while (((Comparable<T>) list.get(front)).compareTo(pivot) < 0)
	    	 {
	    		 ((Tuple) list.get(++front)).setsearchField(sortfield);
	    	 }
	         while (((Comparable<T>) list.get(last)).compareTo(pivot) > 0  && last > left)
	         {
	        	 ((Tuple) list.get(--last)).setsearchField(sortfield);	
	         }
	         if (front < last)
	         {
	             swap(front,last, list);
	         }
	         else if(front != (right-1))
	         {
	        	 swap(front,right-1, list);
	             needsWork = false;   
	         }
	         else
	         {
	        	 needsWork = false;
	         }
	     }
	     
	     quickSorter(left, front,sortfield);
	     quickSorter(front + 1, right,sortfield);
	    
	     
	     
	}
	
	/**
	 * Sends Client an error message
	 */
	public void sendError(String err)
	{
		System.out.println(err);
		
		//insert 'send message to client' command
	}
	
	
	/**
	 * Swaps two elements in Linked List
	 * @param First element, Second element, Linked List
	 */	
	public static <T extends Comparable<T>> void swap(int a, int b, MyLL<T> list)
	{
		 T temp = list.get(a);
		 list.set(a, list.get(b));
		 list.set(b, temp);

	}

	
}
