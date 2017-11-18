package Server;

import java.io.Serializable;

public class DefinitelyNotArrayList<E> implements Serializable
{
	private static final long serialVersionUID = 4706791903249948343L;
	
	private Object[] array;
	private int count;
	
	public int size() { return count; }
	
	public DefinitelyNotArrayList()
	{
		array = new Object[10];
		count = 0;
	}
	
	public DefinitelyNotArrayList(int i)
	{
		array = new Object[i];
		count = 0;
	}
	
	public Object[] toArray()
	{
		Object[] temp = new Object[count];
		for (int i = 0; i < count; i++)
			temp[i] = array[i];
		return temp;
	}
	
	public <T> T[] toArray(T[] arr)
	{
		if (count < arr.length)
		{
			for (int i = 0; i < count; i++)
				arr[i] = (T)array[i];
			if (arr.length > count + 1)
				arr[count] = null;
			return arr;
		}
		else
		{
			Object[] temp = new Object[count];
			for (int i = 0; i < count; i++)
				temp[i] = arr[i];
			return (T[])temp;
		}
	}
	
	public E get(int index)
	{
		if (index < 0 || index >= count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		return (E)array[index];
	}
	
	public void add(E item)
	{
		if (count < array.length)
			array[count++] = item;
		else
		{
			Object[] old = array;
			array = new Object[(int)(array.length * 1.5) + 1];
			for (int i = 0; i < old.length; i++)
				array[i] = old[i];
			array[count++] = item;
		}
	}
	
	public void insert(int index, E item)
	{
		if (index < 0 || index > count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		
		if (count < array.length)
		{
			for (int i = count; i > index; i--)
				array[i] = array[i - 1];
			array[index] = item; count++;
		}
		else
		{
			Object[] old = array;
			array = new Object[(int)(array.length * 1.5) + 1];
			for (int i = 0; i < index; i++)
				array[i] = old[i];
			array[index] = item; count++;
			for (int i = index + 1; i < count; i++)
				array[i] = old[i - 1];
		}
	}
	
	public boolean remove(E item)
	{
		for (int i = 0; i < count; i++)
			if (array[i].equals(item))
			{
				for (; i < count; i++)
					array[i] = array[i + 1];
				count--;
				return true;
			}
		return false;
	}
	
	public E remove(int index)
	{
		if (index < 0 || index >= count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		E value = (E)array[index];
		for (int i = index; i < count; i++)
			array[i] = array[i + 1];
		count--;
		return value;
	}
	
	public E set(int index, E value)
	{
		if (index < 0 || index > count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		E oldValue = (E)array[index];
		array[index] = value;
		return oldValue;
	}
}