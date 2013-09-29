---
title: Multi-Linked List (Graph) - Java
author: Kenny Cason
tags: double-linked, Graph, Java, LinkedList, List, Mult-Linked List, Multi-linked, multiply-linked list
---

This program is a standard multi-linked list (aka, <a href="http://en.wikipedia.org/wiki/Graph_%28mathematics%29" title="Graph" target="_blank">Graph</a> . <b>Note</b>, this is not a simple linked list. each node of this list can connect to any number of other nodes in the list, including itself. This should be very interesting to build off of and tinker with.

The Jar file can be downloaded here: <a href="/code/java/mll/MultiLinkedList.jar">MultiLinkedList.jar</a>
To run the Jar file open the command prompt, cd into the directory, then type:
<code>java -jar MultiLinkedList.jar</code>
That should give a display similar to the image below (The current jar also prints the haschode() of the Node to verify that nodes that are equal() do not get duplicated in the MultiLinkedList). The output shows a series of tests being ran as well as a final output of the MLL.
<a href="/code/java/mll/MLL01.png" target="_blank" ><img src="/code/java/mll/MLL01.png" width="250" alt="Multilinked list"/></a>

The source is also bundled into the Jar file.

MLL.java - contains many of the main functions necessary in a multilinked list.
<p><a href="http://ken-soft.com/code/java/mll/MultiLinkedList.java" class="code">MultiLinkedList.java</a></p>
<p><a href="http://ken-soft.com/code/java/mll/Node.java" class="code">Node.java</a></p>
<p><a href="http://ken-soft.com/code/java/mll/MultiLinkedListTest.java" class="code">MultiLinkedListTest.java</a></p>