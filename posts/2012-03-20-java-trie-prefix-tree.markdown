---
title: Java - Trie (Prefix Tree)
author: Kenny Cason
tags: java, tree, trie, prefix tree
---

A Java implmentation of a Trie (i.e. Prefix Tree).  A definition of a Trie can be found <a href="http://en.wikipedia.org/wiki/Trie" title="here (Wikipedia)" target="_blank"></a>
This implementation (ObjectTrie.java) is capable of storing an Array of any generic data. i.e. Object[], or Integer[], etc.
In the end I also include a wrapper Trie.java that wraps ObjectTrie.java providing String support.

Trie.java

```{.java .numberLines startFrom="1"}
package krunch.lib.ds.trie;

public class Trie {
	
	private ObjectTrie<Character> trie;
	
	public Trie() {
		trie = new ObjectTrie<Character>(' ');
	}
	
	public void insert(String s) {
		trie.insert(toArray(s));
	}
	
	public boolean search(String s) {
		return trie.search(toArray(s));
	}
	
	public int numberEntries() {
		return trie.numberEntries();
	}
	
	private Character[] toArray(String s) {
		Character[] cArray = new Character[s.length()];
		for(int i = 0; i < cArray.length; i++) {
			cArray[i] = s.charAt(i);
		}
		return cArray;
	}
	
	public String toString() {
		return trie.toString();
	}

}


```

Node.java

```{.java .numberLines startFrom="1"}
package krunch.lib.ds.trie;

import java.util.ArrayList;

public class Node<T> {
	
	private T value;
	
	private boolean endMarker;
	
	public ArrayList<Node<T>> children;
	
	
	public Node(T value) {
		this.value = value;
		this.endMarker = false;
		this.children = new ArrayList<Node<T>>();
	}
	
	public Node<T> findChild(T value) {
		if(children != null) {
			for(Node<T> n : children) {
				if(n.getValue().equals(value)) {
					return n;
				}
			}
		}
		return null;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setEndMarker(boolean endMarker) {
		this.endMarker = endMarker;
	}
	
	public boolean isEndMarker() {
		return endMarker;
	}

	public Node<T> addChild(T value) {
		Node<T> n = new Node<T>(value);
		children.add(n);
		return n;
	}
	
}


```

ObjectTrie.java

```{.java .numberLines startFrom="1"}
package krunch.lib.ds.trie;

public class ObjectTrie<T> {

	private Node<T> root;

	private int numberEntries;

	public ObjectTrie(T rootNodeValue) {
		root = new Node<T>(rootNodeValue); // "empty value", usually some "null"  value or "empty string"
		numberEntries = 0;
	}

	public void insert(T[] values) {
		Node<T> current = root;
		if (values != null) {
			if (values.length == 0) { // "empty value"
				current.setEndMarker(true);
			}
			for (int i = 0; i < values.length; i++) {
				Node<T> child = current.findChild(values[i]);
				if (child != null) {
					current = child;
				} else {
					current = current.addChild(values[i]);
				}
				if (i == values.length - 1) {
					if (!current.isEndMarker()) {
						current.setEndMarker(true);
						numberEntries++;
					}
				}
			}
		} else {
			System.out.println("Not adding anything");
		}
	}

	public boolean search(T[] values) {
		Node<T> current = root;
		for (int i = 0; i < values.length; i++) {
			if (current.findChild(values[i]) == null) {
				return false;
			} else {
				current = current.findChild(values[i]);
			}
		}
		/*
		 * Array T[] values found in ObjectTrie. Must verify that the "endMarker" flag
		 * is true
		 */
		if (current.isEndMarker()) {
			return true;
		} else {
			return false;
		}
	}

	public int numberEntries() {
		return numberEntries;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("number entries: ");
		sb.append(numberEntries);
		
		return sb.toString();
	}

}


```


TrieTest.java (Unit Test)

```{.java .numberLines startFrom="1"}
package krunch.lib.utils.ds.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import krunch.lib.ds.trie.Trie;
import krunch.lib.ds.trie.ObjectTrie;

import org.junit.Test;

public class TrieTest {

	@Test
	public void testStringTrie() {
		Trie trie = new Trie();
		assertEquals(0, trie.numberEntries());
		trie.insert("HELLO");
		assertEquals(1, trie.numberEntries());
		trie.insert("HELLO");					// duplicate words do not count
		assertEquals(1, trie.numberEntries());
		trie.insert("FROG");
		assertEquals(2, trie.numberEntries());	
		
		assertTrue(trie.search("HELLO"));		// should find it
		assertTrue(trie.search("FROG"));		// should find it
		assertFalse(trie.search("HEL"));		// not a full word
	}
	
	
	@Test
	public void testObjectTrie() {	
		ObjectTrie<Object> trie = new ObjectTrie<Object>(new Object());
		Object o1 = 12;
		Object o2 = "ASFAS";
		Object o3 = new Character('X');
		Object o4 = new HashMap<String, String>();
		
		Object[] oArray1 = new Object[] {o1, o1, o2, o3, o3, o4};
		Object[] oArray2 = new Object[] {o1, o2, o3, o4, o4, o1};
		Object[] oArray3 = new Object[] {o1, o1, o2, o2};
		
		assertEquals(0, trie.numberEntries());
		trie.insert(oArray1);
		assertEquals(1, trie.numberEntries());
		trie.insert(oArray1);					// duplicate words do not count
		assertEquals(1, trie.numberEntries());
		trie.insert(oArray2);
		assertEquals(2, trie.numberEntries());	
		
		assertTrue(trie.search(oArray1));		// should find it
		assertTrue(trie.search(oArray2));		// should find it
		assertFalse(trie.search(oArray3));		// not a full word
	}

}


```
