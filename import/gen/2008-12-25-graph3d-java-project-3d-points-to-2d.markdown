---
title: 3D Rotation Matrix - Graph 3D
author: Kenny Cason
tags: 2d, 3d, graph3d, java, linear, matrices, matrix, point, programming, projectiion, rotation, vector
---

This was a simple program I wrote that Displays 3D points. It does so by simply projecting a 3D (vector) into 2D. 
The Jar file can be downloaded here: <a href="/code/java/graph3D/Graph3D.jar">Graph3D.jar</a>
The C/C++ version can be found <a href="http://kennycason.com/2009/12/19/graph-3d-vector-rotation-source-included-c/" target="_blank">here</a>

<a href="/code/java/graph3D/graph3D.png" target="_blank" ><img src="/code/java/graph3D/graph3D.png" width="150" alt="3D rotation matrix" /></a>
Before looking at the source, let's take a look at some of the fundamental mathematics behind the software.
<b>rotations</b> - Rotations in this software simple geometric transformations based around an unmoving center axis. Below are the three rotation matrices for each axis, X, Y, and Z, respectively. Every operation can be found in Transform.java below.

<br/><b>Rotate Around X,Y, or Z Axis</b>
<table width="100%"><tr><td>[m]rotX = delim{[}{matrix{3}{3}{1 0 0 0 {cos(theta)} {-sin(theta)} 0 {sin(theta)} {cos(theta)}}}{]}[/m]</td><td>[m]rotY = delim{[}{matrix{3}{3}{{cos(theta)} 0 {sin(theta)} 0 1 0 {-sin(theta)} 0 {cos(theta)}}}{]}[/m]</td></tr><tr><td>[m]rotZ = delim{[}{matrix{3}{3}{{cos(theta)} {-sin(theta)} 0 {sin(theta)} {cos(theta)} 0 0 1 0}}{]}[/m]</td><td></td></tr></table>
 It is also important to know that every point in our world is defined by:
[m]vec{p } = delim{[}{matrix{3}{1}{x y z}}{ ]}[/m]
This can be seen in Point3D.java below. (Which is synonymous to a Vector in this application.) 

<br/><b>Rotate Around Arbitrary Axis</b>
The above rotations are all rotations about either the X,Y, or Z axises. But another common and more complicated rotation is to rotate Vector/Point A around Vector/Point B.
Here are the Steps:
1. the Vector that is being rotated around must be NORMALIZED.
this can be done very easily
 <table width="100%"><td>1a. [m]d = sqrt(x^2 + y^2 + z^2)[/m]</td><td>
 1b. [m]hat{p} = delim{[}{matrix{3}{1}{x/d y/d z/d}}{]}[/m]</td></table>
2. next, using quaternions, perform the rotation.
Where:
2a. [m]hat{p} = ai + bj + ck[/m]
[m]q0 = cos(theta/2),  q1 = sin(theta/2) a,  q2 = sin(theta/2) b,  q3 = sin(theta/2) c[/m]  ([m]theta[/m] is in radians)
The rotation matrix evaluates to the following:
2b. [m]rotAB = delim{[}{matrix{3}{3}{ (q0^2 + q1^2 - q2^2 - q3^2) {2(q1q2 - q0q3)} {2(q1q3 + q0q2)} {2(q2q1 + q0q3)} (q0^2 - q1^2 + q2^2 - q3^2)  {2(q2q3 - q0q1)}  {2(q3q1 - q0q2)} {2(q3q2 + q0q1)} {(q0^2 - q1^2 - q2^2 + q3^2)} }}{]}[/m]
<br/><b>Example:</b> Suppose we have point [m]vec{p} = delim{[}{matrix{3}{1}{1 2 3}}{]}[/m]
and we would like to rotate [m]vec{p}[/m] by 30° around the X axis.
[m] vec{p}_{new} = (rotX)vec{p} = delim{[}{matrix{3}{3}{1 0 0 0 {cos(theta)} {-sin(theta)} 0 {sin(theta)} {cos(theta)}}}{]}.delim{[}{matrix{3}{1}{x y z}}{]} = delim{[}{matrix{3}{3}{1 0 0 0 {cos(30)} {-sin(30)} 0 {sin(30)} {cos(30)}}}{]}.delim{[}{matrix{3}{1}{1 2 3}}{]}[/m]

And finally, to project the 3D points onto a 2D canvas after performing a rotation, a simple way is to simply ignore the Z coordinate and draw the point based on it's X and Y coordinates. However this is assuming that you're projecting it on to the screen as if you are looking straight at it.

The source code can be found below as well as being bundled into the Jar file.

<a href="http://kennycason.com/?p=368" >Cube 3D</a> - A simple 3D Cube engine that demonstrates the usage of ALL the above mentioned equations.

Transform3D.java - This is a simple version using a 3x3 matrix. Using a 4x4 matrix you can also store the translation information. This is useful when trying to program a skeleton represented by vectors.
(Also, check out Graph/Rotate4D <a href="http://kennycason.com/?p=296">here</a>
<p><a href="http://kennycason.com/code/java/graph3D/Transform3D.java" class="code">Transform.java</a></p>
<p><a href="http://kennycason.com/code/java/graph3D/Point3D.java" class="code">Point3D.java</a></p>
Other included source:
<p><a href="http://kennycason.com/code/java/graph3D/Graph3D.java">Graph3D.java</a></p>