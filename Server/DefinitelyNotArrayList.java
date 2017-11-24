package Server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;

public class DefinitelyNotArrayList<E> implements Serializable, Iterable<E>
{
	
	private static final long serialVersionUID = 4706791903249948343L;
	
	private E[] array;
	private int count;
	
	public int size() { return count; }
	
	public DefinitelyNotArrayList(Class<E> c)
	{
		array = (E[])Array.newInstance(c, 10);
		count = 0;
	}
	
	public DefinitelyNotArrayList(Class<E> c, int i)
	{
		array = (E[])Array.newInstance(c, i);
		count = 0;
	}
	
	public E[] toArray()
	{
		E[] temp = (E[])Array.newInstance(array[0].getClass(), count);
		for (int i = 0; i < count; i++)
			temp[i] = array[i];
		return temp;
	}
	
	public E get(int index)
	{
		if (index < 0 || index >= count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		return array[index];
	}
	
	public void add(E item)
	{
		if (count < array.length)
			array[count++] = item;
		else
		{
			E[] old = array;
			array = (E[])Array.newInstance(item.getClass(), (int)(array.length * 1.5) + 1);
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
			E[] old = array;
			array = (E[])Array.newInstance(item.getClass(), (int)(array.length * 1.5) + 1);
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
		E value = array[index];
		for (int i = index; i < count; i++)
		{
			array[i] = array[i+1];
		}
		count--;
		return value;
	}
	
	public E set(int index, E value)
	{
		if (index < 0 || index >= count)
			throw new IndexOutOfBoundsException("Given index is outside the bounds of the array");
		E oldValue = array[index];
		array[index] = value;
		return oldValue;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			int i = 0;
			@Override
			public boolean hasNext() { return i < count; }
			@Override
			public E next() { return array[i++]; }
		};
	}
}