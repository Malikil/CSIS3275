package Server;

import java.io.Serializable;

public class AVLTree<T extends Comparable<T>> implements Serializable
{
	private static final long serialVersionUID = -4795806296678293205L;
	private AVLNode<T> base;
	private int count;
	
	/**
	 * The default constructor, the base will be set to null and no items will be in the tree 
	 */
	public AVLTree()
	{
		base = null;
		count = 0;
	}
	
	/**
	 * A constructor to set the base of the tree to an initial value
	 * @param value The initial base value
	 * @return 
	 */
	
	public AVLNode<T> getBase()
	{
		return base;
	}
	
	public AVLTree(T value)
	{
		base = new AVLNode<T>(value);
		count = 1;
	}
	
	/**
	 * Adds a value to the tree, then makes sure the tree is balanced
	 * @param value The value to add to the tree
	 */
	public void add(T value)
	{
		if (base == null)
			base = new AVLNode<>(value);
		else
		{
			AVLNode<T> current = base;
			while (true)
			{
				if (value.compareTo(current.getValue()) < 0)
				{
					if (current.getLeft() != null)
						current = current.getLeft();
					else
					{
						current.setLeft(new AVLNode<T>(value, current));
						break;
					}
				}
				else
				{
					if (current.getRight() != null)
						current = current.getRight();
					else
					{
						current.setRight(new AVLNode<T>(value, current));
						break;
					}
				}
			}
			rebalance(current);
		}
		count++;
	}
	
	/**
	 * Deletes the first node found which matches the passed value
	 * @param value The value to delete
	 * @return Returns true if the value was found, false if the value wasn't on the tree
	 */
	public boolean delete(T value)
	{
		AVLNode<T> current = base;
		while (current != null)
		{
			if (current.compareTo(value) < 0)
				current = current.getRight();
			else if (current.compareTo(value) > 0)
				current = current.getLeft();
			else
			{
				// delete current value
				if (current.getLayers() == 1)
				{
					if (current.getParent() == null)
					{
						base = null;
						count = 0;
						return true;
					}
					else if (current.getParent().getLeft() == current)
						current.getParent().setLeft(null);
					else
						current.getParent().setRight(null);
					rebalance(current.getParent());
				}
				else
				{
					AVLNode<T> deleter = current;
					
					if (current.getRight() != null)
					{
						current = current.getRight();
						while (current.getLeft() != null)
							current = current.getLeft();
					}
					else
						current = current.getLeft();
					// replace deleter with current
					if (current.getParent().getLeft() == current)
						current.getParent().setLeft(null);
					else
						current.getParent().setRight(null);
					
					AVLNode<T> rebalancer = current.getParent();
					current.setLeft(deleter.getLeft());
					current.setRight(deleter.getRight());
					current.setParent(deleter.getParent());
					// set parent child
					if (deleter.getLeft() != null)
						deleter.getLeft().setParent(current);
					if (deleter.getRight() != null)
						deleter.getRight().setParent(current);
					if (deleter.getParent() != null)
						if (deleter.getParent().getRight() == deleter)
							deleter.getParent().setRight(current);
						else
							deleter.getParent().setLeft(current);
					rebalance(rebalancer);
				}
				
				count--;
				return true;
			}
		}
		return false;
	}
	
	private void rebalance(AVLNode<T> current)
	{
		if (current.getLeft() != null)
			current = current.getLeft();
		else if (current.getRight() != null)
			current = current.getRight();
		
		while (current.getParent() != null)
		{
			current = current.getParent();
			current.updateLayers();
			if (current.getOffset() < -1)
			{
				if (current.getRight().getOffset() > 0)
					current.getRight().rotateRight();
				current.rotateLeft();
			}
			else if (current.getOffset() > 1)
			{
				if (current.getLeft().getOffset() < 0)
					current.getLeft().rotateLeft();
				current.rotateRight();
			}
		}
		base = current;
	}
	
	/**
	 * Checks if the tree contains a certain value
	 * @param value The value to check
	 * @return Returns true if the value is on the tree, otherwise false
	 */
	public boolean contains(T value)
	{
		if (base == null)
			return false;
		else
		{
			AVLNode<T> current = base;
			while (!current.equals(value))
				if (current.getValue().compareTo(value) > 0)
					if (current.getLeft() == null)
						return false;
					else
						current = current.getLeft();
				else
					if (current.getRight() == null)
						return false;
					else
						current = current.getRight();
			return true;
		}
	}
	
	private AVLNode<T> find(T value)
	{
		AVLNode<T> current = base;
		while (current != null)
		{
			if (current.compareTo(value) > 0)
				current = current.getRight();
			else if (current.compareTo(value) < 0)
				current = current.getLeft();
			else
				return current;
		}
		return current;
	}
	
	/**
	 * Will print a text representation of this tree to console,
	 * either with each node on its own line with the path from the base,
	 * or on a single line with each value separated by a comma
	 * @param commas True to print on a single line with commas, false to print a multi-line structure
	 */
	public void printTree(boolean commas)
	{
		if (commas)
			printCommas(base);
		else
			printNode(base, "B");
	}
	
	AVLNode<T> minimum()
	{
		AVLNode<T> n = base;
		while (n.getLeft() != null)
			n = n.getLeft();
		return n;
	}
	
	AVLNode<T> maximum()
	{
		AVLNode<T> n = base;
		while (n.getRight() != null)
			n = n.getRight();
		return n;
	}
	
	/**
	 * Prints basic information about the tree to console, including:
	 * <ul>
	 * <li>The number of items on the tree and the smallest and largest values</li>
	 * <li>The value of the root of the tree</li>
	 * <li>The highest number of layers on the tree</li>
	 * <li>The values of the left and right items from the base and which layer they're on</li>
	 * <li>The offset of the left and right nodes (the difference in layers on the left and right sides)</li>
	 * </ul>
	 */
	public void printBasics()
	{
		System.out.println(count + " elements, ranging from " + minimum() + " to " + maximum());
		System.out.println("Base value: " + base);
		System.out.println("Layers: " + base.getLayers());
		System.out.println("Left value: " + base.getLeft() + " on layer " + base.getLeft().getLayers());
		System.out.println("Offset: " + base.getLeft().getOffset());
		System.out.println("Right value: " + base.getRight() + " on layer " + base.getRight().getLayers());
		System.out.println("Offset: " + base.getRight().getOffset());
	}
	
	private void printCommas(AVLNode<T> node)
	{
		
		if (node.getLeft() != null)
			printCommas(node.getLeft());
		System.out.print(node.toString() + ", ");
		if (node.getRight() != null)
			printCommas(node.getRight());
	}
	
	private void printNode(AVLNode<T> node, String pre)
	{
		System.out.println(pre + " " + node.toString());
		if (node.getLeft() != null)
			printNode(node.getLeft(), pre + ".L");
		if (node.getRight() != null)
			printNode(node.getRight(), pre + ".R");
	}

	void replace(T value, T oldValue) //I WANT SET - Angelo
	{
		AVLNode<T> current = base;
		while (current != null)
		{
			if (current.compareTo(oldValue) > 0)
				current = current.getRight();
			else if (current.compareTo(oldValue) < 0)
				current = current.getLeft();
			else 
				break;
		}
		 current.set(value);
	}
	
	int getCount()
	{
		return count;
	}
}
