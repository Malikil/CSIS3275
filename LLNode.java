package Server;

public class LLNode<T> {

	T value;
	LLNode<T> next;
	
	LLNode()
	{
		value = null;
		next = null;
	}
	LLNode(T val)
	{
		value = val;
		next = null;
	}
	LLNode(LLNode<T> n)
	{
		value = null;
		next = n;
	}
	LLNode(T val, LLNode<T> n)
	{
		value = val;
		next = n;
	}
	public String toString()
	{
		return value.toString();
	}
}