---
title: 4D Rotation Matrix - Graph 4D
author: Kenny Cason
tags: 3D, 4D, Graph, Java, Matrix, Projection, Rotation, Transformation, vector
---

This program rotates points about the XY, YZ, XZ, XU, YU, and ZU axises. I then projects each 4D vector to the 2D canvas.
The Jar file can be downloaded here: <a href="/code/java/graph4D/Graph4D.jar">Graph4D.jar</a>
<table><tr><td>
<a href="/code/java/graph4D/graph4D.png" target="_blank" ><img src="/code/java/graph4D/graph4D.png" width="150" alt="4D rotation matrix java"/></a></td><td>
<a href="/code/java/graph4D/graph4D2.png" target="_blank" ><img src="/code/java/graph4D/graph4D2.png" width="150" alt="4D rotation matrix java"/></a></td></tr></table>
Before looking at the source, let's take a look at some of the fundamental mathematics behind the software.
If you are uncomfortable with the thought of 4D matrix rotations, then I recommend reading Wikipedia, or checking out my article about 3D graphing, which can be found <a href="http://ken-soft.com/2008/12/25/graph3d-java-project-3d-points-to-2d/">here</a>. In this example, I will only show the 4D rotation matrices. Note that for each rotation matrix, 2 axises are held still while the vector is rotated around the other two axises. This may be hard to visualize at first, but It will become clear after a while.


<table><tr><td>
<tr><td>
	<div class="latex">
	rotXY = \\left[\\begin{array}{cccc}
	cos(\\theta) & sin(\\theta) & 0 & 0       \\\\
	-sin(\\theta) & cos(\\theta) & 0 & 0       \\\\
	0 & 0 & 1 & 0      \\\\
	0 & 0 & 0 & 1
	\\end{array}\\right]
	</div>
</td>
<td>
	<div class="latex">
	rotYZ = \\left[\\begin{array}{cccc}
	1 & 0 & 0 & 0       \\\\
	0 & cos(\\theta) & sin(\\theta) & 0       \\\\
	0 & -sin(\\theta) & cos(\\theta) & 0      \\\\
	0 & 0 & 0 & 1
	\\end{array}\\right]
	</div>
</td></tr>
<tr><td>
	<div class="latex">
	rotXZ = \\left[\\begin{array}{cccc}
	cos(\\theta) & 0 & -sin(\\theta) & 0       \\\\
	0 & 1 & 0 & 0      \\\\
	sin(\\theta) & 0 & cos(\\theta) & 0      \\\\
	0 & 0 & 0 & 1
	\\end{array}\\right]
	</div>
</td>
<td>
	<div class="latex">
	rotXU = \\left[\\begin{array}{cccc}
	cos(\\theta) & 0 & 0 & sin(\\theta)       \\\\
	0 & 1 & 0 & 0      \\\\
	0 & 0 & 1 & 0      \\\\
	-sin(\\theta) & 0 & 0 & cos(\\theta)     \\\\    
	\\end{array}\\right]
	</div>
</td></tr>
<tr><td>
	<div class="latex">
	rotYU = \\left[\\begin{array}{cccc}
	1 &  0 & 0 & 0 \\\\
	0 & cos(\\theta) & 0 & -sin(\\theta) \\\\
	0 & 0 & 1 & 0 \\\\
	0 & sin(\\theta) & 0 & cos(\\theta)
	\\end{array}\\right]
	</div>
</td>
<td>
	<div class="latex">
	rotZU = \\left[\\begin{array}{cccc}
	1 & 0 & 0 & 0      \\\\
	0 & 1 & 0 & 0		\\\\
	0 & 0 & cos(\\theta) & -sin(\\theta)       \\\\
	0 & 0 & sin(\\theta) & cos(\\theta)       
	\\end{array}\\right]
	</div>
</td></tr>
</table>
The source code can be found below as well as being bundled into the Jar file.

Transform4D.java - contains method for rotating a 4D vector
<p><a href="http://ken-soft.com/code/java/graph4D/Transform4D.java" class="code">Transform4D.java</a></p>
<p><a href="http://ken-soft.com/code/java/graph4D/Point4D.java">Point4D.java</a></p>
<p><a href="http://ken-soft.com/code/java/graph4D/Graph4D.java">Graph4D.java</a></p>