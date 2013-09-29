---
title: 3D Cube Engine - Java
author: Kenny Cason
tags: 3D, Development, Game, Graphics, Java, Rotation, Rubik's Cube, vector
---

<table ><tr>
<td><a href="/code/java/cube3D/Screenshot-Cubes3D-4.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/cube3D/Screenshot-Cubes3D-4.png" width="190" /></a></td>
<td><div<a href="/code/java/kubix/Screenshot-Kubix-8.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/kubix/Screenshot-Kubix-8.png" width="190" /></a></td>
<td><a href="/code/java/cube3D/Screenshot-Cubes3D-2.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/cube3D/Screenshot-Cubes3D-2.png" width="190" /></a></td>
</tr></table>
This is a recent project of mine for building 3D puzzle games, (like Rubik's Cubes). 

While the code could be optimized quite a bit, this is mainly for those who wish to better understand concepts of 3D programming. For example, one way to create, store, and manipulate a 3D polygon, whether it be a Cube, or an ellipsoid. This project contains code and algorithm's to rotate 3D polygons, around the origin, and themselves. Ability to draw 3D objects in order of increasing Z-values. Draw wireframe or solid color polygons. Keyboard and Mouse controls to move and alter the Polygons. It is a work in progress and not intended for use in graphic intensive games.

Note: later I will add rotateAroundVector() for more general rotation
Note: Ellipse3D is still very primitive but demonstrates one method creating an ellipsoid
Note: Not all Jar files contain the same versions of code. Each Jar file contains code specific to what it is supposed to do.

All source code can be found in the JAR files
Downloadable Jars (simple to complex):
1. <a href="/code/java/cube3D/RenderWiredCubes3D-demo.jar">RenderWiredCubes3D-demo.jar</a>: this code rotates 4 rotating cubes about the origin.
2.  <a href="/code/java/cube3D/RenderPolygons3D-demo.jar">RenderPolygons3D-demo.jar</a>: this code demonstrates various ellipsoids and cubes rotating around unique axises.
3. <a href="/code/java/cube3D/Cubes3D-demo.jar">Cubes3D-demo.jar</a>: this code demonstrates more of the functionality of the 3D Cube engine by creating a Rubik's cube like look and feel. 
<b>Controls</b>
<ul>
 <li>Mouse: click and drag the cube</li>
 <li>A - reset</li>
 <li>S - toggle between solid and non-solid mode. (i.e. the inside is filled with cubes or not)</li>
 <li>D - randomly select an internally defined color scheme</li>
 <li>F - toggle between random rotate mode. Just try it :)</li>
 <li>Q - Decrease space between pieces</li>
 <li>W - Increase space between pieces</li>
 <li>E - Decrease size of cubes</li>
 <li>R - Increase size of cubes</li>
 <li>T - Decrease dimensions. i.e. 4x4x4 -> 3x3x3</li>
 <li>Y - Increase dimensions. i.e. 4x4x4 -> 5x5x5 (not that if solid mode is true, then it will render slower</li>
 <li>Arrows - Translate the cubes across the screen</li>
</ul>

If you're only interested in the Transformation algorithms, check the below link:
<a href="http://ken-soft.com/2009/01/08/graph4d-rotation4d-project-to-2d/" >Graph4D</a> - demonstrates methods and actual source code for rotating a 4D vector.
<a href="http://ken-soft.com/2008/12/25/graph3d-java-project-3d-points-to-2d/" >Graph3D</a> - demonstrates methods and actual source code for rotating a 3D vector.
<table ><tr>
<td><a href="/code/java/kubix/Kubix-RandomCubes.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/kubix/Kubix-RandomCubes.png" width="190" /></a></td>
<td><a href="/images/cube3D/Screenshot-Cubes3D.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/cube3D/Screenshot-Cubes3D.png" width="190" /></a></td>
<td><a href="/code/java/kubix/Screenshot-Kubix-1.png" target="_blank" alt="3D rotating cube engine java" ><img src="/code/java/kubix/Screenshot-Kubix-1.png" width="190" /></a></td>
</tr></table>