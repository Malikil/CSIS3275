package Client;

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
	private MyLL<Fields> fieldsList = new MyLL<Fields>();
	private static MyLL<Tuple> tupleList = new MyLL<Tuple>();
	private String tableName;
	private int numberoffields = 0;
	private String databaseName = "Default";
	private int assignPKID = 1;
	private MyLL<Integer> unusedPKID = new MyLL<Integer>();

	
	
	Table(String name) throws IOException
	{
		tableName = name;
		
	}
	
	Table(String name, String dbName) throws IOException
	{
		tableName = name;
		databaseName = dbName;
		
	}
	
	
		
	public int getFieldNum()
	{
		return numberoffields;
	}
	
	
	
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
	
	
	
	
	
	public String getFields() 
	{
		String fieldNames = ""; 
	
		for(int i = fieldsList.size()-1; i >-1; i--) 		
		{
			fieldNames += ((Fields) fieldsList.get(i)).getfieldName() + "\t";
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
	
	/* Import feature
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
	
	public static <T extends Comparable<T>> int BinarySearch(T searchItem, int first, int last, int searchfield)
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
	
	
	public static <T extends Comparable<T>> void quickSorter(int left, int right, int sortfield )
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
	
	 
	 public static <T extends Comparable<T>> void swap(int a, int b, MyLL<T> list)
	 {
			 T temp = (T) list.get(a);
			 list.set(a, list.get(b));
			 list.set(b, temp);

	 }

	
}
