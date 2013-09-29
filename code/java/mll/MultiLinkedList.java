package mll;

public class MultiLinkedList<T> {
	
	private Node<T> nodes; // the MultiLinkedList

	private Node<T> lastTraversedNode = null;

	private boolean isFirstvalue = false;
	
	public MultiLinkedList() {
		nodes = new Node<T>();
	}
	
	/**
	 * clear - clear the whole list
	 */
	public void clear() {
		nodes.clear();
	}

	/**
	 * getRoot - returns the root node
	 */
	public Node<T> getRoot() {
		return nodes;
	}

	/**
	 * getLastTraversed - returns the last traversed node
	 */
	public Node<T> getLastTraversedNode() {
		return lastTraversedNode;
	}

	/**
	 * append - add an array of connected.
	 * 
	 * @Param T[] - the array of value
	 */
	public void append(T[] values) {
		Node<T> root = nodes;
		isFirstvalue = true;
		unCheckAllNodes();
		for (int i = 0; i < values.length; i++) {
			root = append(root, values[i]);
			if (i == values.length - 1) {
				lastTraversedNode = root;
			}
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public Node<T> append(T value) {
		unCheckAllNodes();
		return append(nodes, value);
	}
	
 
	/**
	 *  helper method for append(T[] value);
	 * 
	 * @Param Node - the root node to connect to
	 * @Param T - the value being added
	 * @Return Node - the new previous node to traverse from
	 */
	public Node<T> append(Node<T> root, T value) {
		Node<T> foundNode = null;
		boolean isConnection = false;
		foundNode = find(value);
		if (isFirstvalue && lastTraversedNode != null && foundNode != null) {
			lastTraversedNode.add(foundNode);
		}
		if (foundNode != null) { // if found a node
			isConnection = false;
			for (int i = 0; i < root.getAll().size(); i++) {
				if (root.get(i).getValue().equals(value)) { // there is already a connection
					isConnection = true;
				}
			}
			if (isConnection) {// if there was already a connection, don't make a new one
				root = foundNode;
			} else {
				if (isFirstvalue) { // this is the first time that value has been
									// used as the first value, even though it is
									// already known.
					nodes.add(foundNode);
				} else {
					root.add(foundNode);
				}
				root = foundNode;
			}
		} else {
			root.append(value);
			root = root.get(root.getAll().size() - 1);
		}
		isFirstvalue = false;
		return root;
	}

	/**
	 * find - recursively find value and return node
	 * 
	 * @Param T - The value being searched for
	 * @Return Node - return the found node
	 */
	public Node<T> find(T value) {
		unCheckAllNodes();
		return find(nodes, value);
	}

	/**
	 * helper method for find(T value)
	 * 
	 * @Param Node - the node to start from
	 * @Param T - The value being searched for
	 * @Return Node - return the found node
	 */
	public Node<T> find(Node<T> node, T value) {
		Node<T> foundNode = null;
		if (!node.checked()) {
			node.check();
			if (node.getValue() != null && node.getValue().equals(value)) {
				return node;
			}
			for (int i = 0; i < node.getAll().size(); i++) {
				Node<T> n = find(node.get(i), value);
				if (n != null) {
					foundNode = n;
				}
			}
		}
		return foundNode;
	}
	
	/**
	 * contains - recursively find value and return true if found
	 * 
	 * @Param T - The value being searched for
	 * @Return boolean
	 */
	public boolean contains(T value) {
		unCheckAllNodes();
		return contains(nodes, value);
	}

	/**
	 * helper method for contains(double value)
	 * 
	 * @Param Node - the node to start from
	 * @Param T - The value being searched for
	 * @Return Node - return the found node
	 */
	public boolean contains(Node<T> node, T value) {
		if (!node.checked()) {
			node.check();
			if (node.getValue().equals(value)) {
				return true;
			}
			for (int i = 0; i < node.getAll().size(); i++) {
				return contains(node.get(i), value);
			}
		}
		return false;
	}

	/**
	 * contains - searches the MultiLinkedList to see if a sequence of value is found.
	 * 
	 * @Param T[] - the value being searched for
	 * @Return boolean - was it found
	 */
	public boolean contains(T[] values) {
		Node<T> current = nodes;
		boolean valueFound;
		for (int j = 0; j < values.length; j++) {
			valueFound = false;
			for (int i = 0; i < current.getAll().size(); i++) {
				if (current.get(i).getValue() != null && current.get(i).getValue().equals(values[j])) {
					current = current.get(i);
					valueFound = true;
					break;
				}
			}
			if (!valueFound) {
				return false;
			}
		}
		return true;
	}


	/**
	 * unCheckAllNodes - uncheck all the nodes recursively from a given root
	 * node. Very critical because it prevents the recursive loops in other
	 * functions to not get stuck in infinite loops.
	 */
	public void unCheckAllNodes() {
		unCheckAllNodes(nodes);
	}

	/**
	 * private helper method for unCheckAllNodes()
	 */
	private void unCheckAllNodes(Node<T> root) {
		if (!root.checked()) {
			return;
		}
		root.unCheck();
		for (int i = 0; i < root.getAll().size(); i++) {
			unCheckAllNodes(root.get(i));
		}
	}

	/**
	 * remove - remove first found instance of node in the MultiLinkedList where value matches
	 * 
	 * @Param Node - the node to be deleted
	 */
	public Node<T> remove(T value) {
		unCheckAllNodes();
		return remove(nodes, value);
	}
	
	/**
	 *  remove first found instance of node from the MultiLinkedList starting at root
	 * 
	 * @Param Node - the node to be deleted
	 */
	public Node<T> remove(Node<T> trashNode) {
		unCheckAllNodes();
		return remove(nodes, trashNode.getValue());
	}


	/**
	 * private helper method
	 * @Param Node - the base node
	 * @Param double - the value being deleted
	 */
	private Node<T> remove(Node<T> root, T value) {
		if (!root.checked()) {
			root.check();
			for (int i = 0; i < root.getAll().size(); i++) {
				if (root.get(i).getValue().equals(value)) {
					return root.remove(i);
				} else {
					return remove(root.get(i), value);
				}
			}
		}
		return null;
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		unCheckAllNodes();
		String s = "[" + nodes.getValue() + "]\n";
		return printList(nodes, s, 0);
	}
	

	/**
	 * private helper method for toString to recursively traverse the list
	 * 
	 * @Param Node - the node being printed
	 */
	private String printList(Node<T> node, String s, int depth) {
		if (!node.checked()) {
			node.check();
			for (int i = 0; i < node.getAll().size(); i++) {
				if (node.getAll().size() > 0) {
					for (int j = 0; j < depth; j++) {
						s += "\t";
					}
					s += ("\\__[" + node.get(i).getValue()+"@" + node.get(i).hashCode() + "]\n");
				}
				s = printList(node.get(i), s, depth + 1);
			}
		}
		return s;
	}

}
