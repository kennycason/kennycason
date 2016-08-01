---
title: Rubik's Cube - 2D Implementation - Java
author: Kenny Cason
tags: java, learning, programming, r, rubik's cube, ルービックキューブ
---

This is a simple 2D representation of a Rubik's Cube written in Java. Its main purpose to be easily integrated with other software. I.e. A Rubik's Cube solving Neural Network. Therefore, the UI is not extremely eye catching. But it serves a good test to demonstrate that the software is working. Later implementations will better support NxNxN cubes.

The Jar file can be downloaded here: <a href="/code/java/rc/RC.jar">Rubik's Cube.jar</a> Just click it to run it. If you have any problems running it, check to ensure that Java is correctly installed on your machine.
<table width=100%><tr><td>
<a href="/code/java/rc/RC01.png" target="_blank" ><img src="/code/java/rc/RC01.png" width="295" alt="Rubik's Cube 2D java"/></a></td><td>
<a href="/code/java/rc/RC02.png" target="_blank" ><img src="/code/java/rc/RC02.png" width="295"  alt="Rubik's Cube 2D java"/></a></td></tr></table>

<p><a href="http://kennycason.com/code/java/rc/RubiksCubeGUI.java">RubiksCubeGUI.java</a></p> 
RubiksCube.java is the main wrapper class to Cube3D
<p><a href="http://kennycason.com/code/java/rc/RubiksCube.java">RubiksCube.java</a></p>
<p><a href="http://kennycason.com/code/java/rc/Cube3D.java">Cube3D.java</a></p>

Each of the 27 cubes that make up a standard 3x3 Rubik's Cube, I call a Cubit.
<p><a href="http://kennycason.com/code/java/rc/Cubit.java">Cubit.java</a></p>
<p><a href="http://kennycason.com/code/java/rc/Cube3DTest.java">Cube3DTest.java</a></p>
