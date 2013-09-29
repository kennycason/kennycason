package mll;

import java.util.ArrayList;
import java.util.LinkedList;

public class Node<T> {


	protected T value; // this contains the value of the multi doubly-linked list node
	protected LinkedList<Node<T>> children; // the links to other nodes
	protected boolean check;

	/**
	 * Constructor for objects of class MLLNode
	 * @Param T - the value being stored in the node
	 */
	public Node(T value) {
		this.value = value;
		check = false;
		children = new LinkedList<Node<T>>();
	}
	
	public Node() {
		value = null;
		check = false;
		children = new LinkedList<Node<T>>();
	}

	/**
	 * getValue - returns the value
	 * @Return T - the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * setValue - sets the value of this node
	 * @Param T - the value
	 */
	public void setValue(T value) {
		this.value = value;
	}


	/**
	 * appendValue - append value to the node
	 * @Param T - value
	 */
	public void append(T value) {
		append(new Node<T>(value));
	}

	public void append(Node<T> node) {
		children.add(node);
	}
	
	/**
	 * connectNode - connect to another node
	 * @Param MLLNode -  node 
	 */
	public void add(Node<T> node) {
		children.add(node);
	}

	/**
	 * getAll - returns all children nodes
	 * @Return LinkedList<AbstractNode<T>> - the children nodes
	 */
	public LinkedList<Node<T>> getAll() {
		return children;
	}
	
	/**
	 * get - returns a specific linked element
	 * @Param int - which element to return
	 * @Return AbstractNode<T> - the specific linked element
	 */
	public Node<T> get(int i) {
		return children.get(i);
	}
	
	/**
	 * getCheck - returns whether or not the node is checked
	 * @Return boolean - checked or not
	 */
	public boolean checked() {
		return check;
	}

	/**
	 * check - check the node
	 */
	public void check() {
		check = true;
	}

	/**
	 * unCheck - uncheck the node
	 */
	public void unCheck() {
		check = false;
	}

	/**
	 * remove - remove a specific node
	 * @Param int - which node to remove
	 * @Return AbstractNode<T> - the node being removed
	 */
	public Node<T> remove(int i) {
		return children.remove(i);
	}
	
	/**
	 * clear - remove all children nodes
	 */
	public void clear() {
		children.clear();
	}


}
