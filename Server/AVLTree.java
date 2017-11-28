package Server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;

public class AVLTree<T extends Comparable<T>> implements Serializable
{
	private static final long serialVersionUID = -4795806296678293205L;
	private AVLNode<T> base;
	private int count;
	
	public int size() { return count; }
	
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
	 */
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
	
	public T get(T value)
	{
		AVLNode<T> current = base;
		while (current != null)
		{
			if (current.compareTo(value) > 0)
				current = current.getRight();
			else if (current.compareTo(value) < 0)
				current = current.getLeft();
			else
				return current.getValue();
		}
		return null;
	}
	
	public T[] toArray(T[] arr)
	{
		// Don't do this at home, kids
		count = 0;
		if (base != null)
			copyNode(base, arr);
		if (arr.length > count)
			arr[count] = null;
		return arr;
	}
	
	private void copyNode(AVLNode<T> node, T[] arr)
	{
		if (node.getLeft() != null)
			copyNode(node.getLeft(), arr);
		arr[count++] = node.getValue();
		if (node.getRight() != null)
			copyNode(node.getRight(), arr);
	}
	
	public AVLTree<T> reconstructTree()
	{
		AVLTree<T> temp = new AVLTree<>();
		reconstructNode(base, temp);
		return temp;
	}
	
	private void reconstructNode(AVLNode<T> node, AVLTree<T> newTree)
	{
		newTree.add(node.getValue());
		if (node.getLeft() != null)
			reconstructNode(node.getLeft(), newTree);
		if (node.getRight() != null)
			reconstructNode(node.getRight(), newTree);
	}
	
	public AVLTree<T> getRange(T value, String mode)
	{
		AVLTree<T> temp = new AVLTree<T>();
		switch (mode)
		{
		case "<":
			lessThanNode(base, value, temp);
			break;
		case "<=":
			lessThanNode(base, value, temp);
			equalToNode(base, value, temp);
			break;
		case "=":
			equalToNode(base, value, temp);
			break;
		case ">=":
			greaterThanNode(base, value, temp);
			equalToNode(base, value, temp);
			break;
		case ">":
			greaterThanNode(base, value, temp);
			break;
		}
		return temp;
	}
	
	private void lessThanNode(AVLNode<T> current, T value, AVLTree<T> newTree)
	{
		if (current.compareTo(value) <= 0)
		{
			if (current.compareTo(value) < 0)
				newTree.add(current.getValue());
			if (current.getRight() != null)
				lessThanNode(current.getRight(), value, newTree);
		}
		if (current.getLeft() != null)
			lessThanNode(current.getLeft(), value, newTree);
	}
	
	private void greaterThanNode(AVLNode<T> current, T value, AVLTree<T> newTree)
	{
		if (current.compareTo(value) >= 0)
		{
			if (current.compareTo(value) > 0)
				newTree.add(current.getValue());
			if (current.getLeft() != null)
				greaterThanNode(current.getLeft(), value, newTree);
		}
		if (current.getRight() != null)
			greaterThanNode(current.getRight(), value, newTree);
	}
	
	private void equalToNode(AVLNode<T> current, T value, AVLTree<T> newTree)
	{
		if (current.compareTo(value) > 0)
		{
			if (current.getLeft() != null)
				equalToNode(current.getLeft(), value, newTree);
		}
		else if (current.compareTo(value) < 0)
		{
			if (current.getRight() != null)
				equalToNode(current.getRight(), value, newTree);
		}
		else
			newTree.add(current.getValue());
			
	}
}
