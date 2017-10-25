import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Table implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4225828966516582087L;
	private myLL<fields> fieldsList = new myLL<fields>();
	private static myLL<tuple> tupleList = new myLL<tuple>();
	String tableName;
	int numberoffields = 0;
	String databaseName = "Default";

	
	
	Table(String name) throws IOException
	{
		tableName = name;
		Update();
		
	}
	
	Table(String name, String dbName) throws IOException
	{
		tableName = name;
		databaseName = dbName;
		Update();
		
	}
	
	
	public void addField(String s, int n) throws IOException
	{
		fieldsList.push(new fields(s,n,numberoffields));
		for(int i = 0; i<tupleList.size(); i++)
		{
			((tuple) tupleList.get(i)).setNull(numberoffields);
		}
		numberoffields++;
		Update();
		
	}
	
	public void removeField(int n) throws IOException
	{
		for(int i = 0; i<tupleList.size(); i++)
		{
			((tuple) tupleList.get(i)).removeFieldEntry(n);
		}
		fieldsList.remove(n);
		numberoffields--;
		Update();
		
	}
	
	
	public int getFieldNum()
	{
		return numberoffields;
	}
	
	public String addTuple(String[] rawData) 
	{
		String result ="";
		 if(rawData.length > numberoffields)
		 {
			 result = "Unforseen error, please contact admin";
		 }
		 else if(rawData.length < numberoffields)
		 {
			 result = "Please do not leave empty fields";
		 }
		 else 
		 {
			 myLL<fieldData> tupleEntry = new myLL<fieldData>();

			 for(String s: rawData)
			 {
				 tupleEntry.push(new fieldData(s));
			 }
			 tupleList.push(new tuple(tupleEntry));

			 System.out.println("Tupple added, new size is " + tupleList.size());
			 
		 }
		 Update();

		 return result;
	}
	
	
	public void removeTuple(int n) throws IOException
	{
		tupleList.remove(n);
		 Update();

	}
	
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




	public String getTuple(int i) {
		
		if(tupleList.element() == null)
		{
			return null;
		}
		else {
			tuple toGet =  (tuple) tupleList.get(i);
			
			return toGet.toString();
		}
		
	}
	
	
	public String setTuple(int i, int fieldnum, Comparable editedData) {
		
		if(tupleList.element() == null)
		{
			return "Record does not exist"; //Should technically never happen once GUI is made
		}
		else {
			((tuple) tupleList.get(i)).setFData(fieldnum, editedData);
			Update();
			return "Record changed";
		}
		
	}
	
	
	public String getFields() 
	{
		String fieldNames = ""; 
	
		for(int i = fieldsList.size()-1; i >-1; i--) 		
		{
			fieldNames += ((fields) fieldsList.get(i)).getfieldName() + "\t";
		}
		
		return fieldNames;
	}
	
	
	public int numberofentries()
	{
		return tupleList.size();
	}
	
	public void printEntries() 
	{
		System.out.println(getFields().toString());
		for(int i = tupleList.size()-1; i >-1; i--) 		
		{
			System.out.println(getTuple(i).toString());
		}
		
	}
	
	public void importdata() throws IOException
	{
		File file = new File("MockData.txt");
		BufferedReader bR = new BufferedReader(new FileReader(file));
		String entry = bR.readLine();
		while(entry != null)
		{
			String[] enterThis = entry.split(",");
			addTuple(enterThis);
			entry = bR.readLine();
		}
		bR.close();
		
	}
	
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
				tuple foundrecord =  (tuple) tupleList.get(stuff++);
				while(search.compareTo(foundrecord.getFData(searchfield).toString()) == 0)
				{
					System.out.println(foundrecord.toString());
					foundrecord =  (tuple) tupleList.get(stuff++);
				}
			}
			
			
	 }
	
	public static <T extends Comparable<T>> int BinarySearch(T searchItem, int first, int last, int searchfield)
	{
		myLL<T> array = (myLL<T>) tupleList;
		fieldData searchThis = new fieldData(searchItem);
		((tuple) array.get(first)).setsearchField(searchfield);
		((tuple) array.get(last)).setsearchField(searchfield);	
		if(last - first <2)
		{
			if(searchThis.compareTo((tuple) array.get(first)) == 0)
			return first;
			else
			{
				return -1;
			}
		}
		
		int pos = -1;
		int mid = (first + last)/2;
		((tuple) array.get(mid)).setsearchField(searchfield);	
		int diff = searchThis.compareTo((tuple) array.get(mid));
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
	
	
	public static <T extends Comparable<T>> void quickSorter(int left, int right, int sortfield )
	{
		myLL<T> list = (myLL<T>) tupleList;
	
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
	    	 ((tuple) list.get(++front)).setsearchField(sortfield);
	    	 ((tuple) list.get(--last)).setsearchField(sortfield);	         
	    	 while (((Comparable<T>) list.get(front)).compareTo(pivot) < 0)
	    	 {
	    		 ((tuple) list.get(++front)).setsearchField(sortfield);
	    	 }
	         while (((Comparable<T>) list.get(last)).compareTo(pivot) > 0  && last > left)
	         {
	        	 ((tuple) list.get(--last)).setsearchField(sortfield);	
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
	
	 
	 public static <T extends Comparable<T>> void swap(int a, int b, myLL<T> list)
	 {
			 T temp = (T) list.get(a);
			 list.set(a, list.get(b));
			 list.set(b, temp);

	 }

	
}
