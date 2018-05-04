---
title: Logical Method for Solving the 3x3x3 Rubik's Cube
author: Kenny Cason
tags: how to, layer-by-layer, algorithm, puzzle, rubik's cube
---

How to Logically Solve a Rubik’s Cube<br/>

First, as with any problem, it is important to understand what your objective is. In the case of the Rubik’s Cube, the main goal is to arrange the pieces in such a way that each side is the same color. Next lets luck at the structure of a standard 3x3 Rubik’s Cube. It is a cube composed of 6 sides, each side composed of 9 stickers (3x3). There are 8 corner pieces, 12 edge pieces and 6 center pieces (one on each side). It is also important to note that on all “odd” Rubik’s Cube’s the center pieces can rotate clockwise or counter clockwise, but the positions are fixed. “odd” meaning that the cube is a 3x3, 5x5, NxN, where N is an odd number. Also, because the center pieces are fixed it becomes immediately obvious which side is which color.
Now that we know the basic structure of a Rubik’s Cube next lets think about strategies to solve it. First, lets examine what is probably the most common way to solve a Rubik’s Cube, the “Layer by Layer” method. For those not familiar with the Layer by Layer method, it involves solving the top, middle, and bottom layer in that order. However, despite this seeming to be the most logical way to approach the Rubik’s Cube, it is actually farm more difficult to solve in this manner. The problem is very simple. If you solve the first layer completely, in order to solve the second layer without messing up the solved first layer the moves grow in length and complexity. Then to solve the bottom layer without messing up the top and middle ayers, the moves again grow even more complex. To add a quantitative example, lets look at the case of trying to place a piece in the second row without messing up the first row it takes 7 permutations, and to switch the bottom layer edge pieces positions without messing up the top and middle layers, permutations can be as much as 15, in which you are more likely to give up before you ever derive on your own techniques. Not to say that it is impossible, but without any experience cubing can prove to be very difficult. However using the method In this paper, you can place all edge pieces in their correct positions in as  f approximately 3 permutations each. In a nutshell, this is where most people are mistaken, is thinking that the Layer by Layer method is the easiest way to solve a Rubik’s Cube when in fact mathematically it’s more complex to do so.
Also, until you are comfortable with the feel of a Rubik’s Cube, I highly recommend to avoid checking many websites for solutions as they primarily rely on rote memorization of techniques, though definitely useful after you’ve got a feel for the cube on your own. Plus it’s more rewarding when you do it on your own. Now, the next time you see someone solving a Rubik’s Cube using the Layer by Layer method, you can assume that about 99% of the time they learned how to solve it from either A) a friend or B) the internet. Also, contrary to popular belief, you cannot re-stick the stickers after peeling them off. As encouragement, you may also be happy to know that you need not know or understand any mathematics to solve the Rubik’s Cube. Good Luck!
Note, as I believe it spoils the reward of solving the Rubik’s Cube, I will NOT write any algorithms that will directly tell you how to move pieces as shown in the following diagrams. This guide is merely designed to guide and kindle your thoughts and let you know that there is a simpler method than the layer-by-layer method.

Explanation:
1. First, scramble up the Rubik’s Cube, if it isn’t already.

2. All except on corner piece, solve the top layer. This may seem difficult at first, but will become simple as you practice.
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc2.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>Just like in the diagram, solve the for edge pieces in the top layer, creating a cross. Ensure that the color orientation is correct all the way around by examining the center pieces color in the middle layer.
</td></table>
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc3.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>Next, solve any three of the corner pieces in the top layer, leaving one unsolved corner piece. You may choose to the corner to not solve at your own discretion.</td></table>

3. Except for one edge piece, place corresponding 3 edge pieces  into the middle layer.
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc4.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>In order to do this, we are going to make use of the unsolved corner piece in the top layer. In general you first place the unsolved corner of the top layer directly over the place in the second layer where you want to place the correct piece (which must be in the bottom layer). Then in only 3 moves each you can solve 3 of the 4 edge pieces in the middle layer. The resulting pattern should be similar to whats in the diagram to the left.</td></table>

4. Next, turn the cube upside down exposing the bottom layer.
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc5.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>The unsolved 2 pieces from the top and middle layer in previous steps can now be used to place the edge pieces correctly in the bottom row.</td></table>
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc6.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>If done correctly the resulting pattern will be as described in the diagram to the left.</td></table>

5. Next, place the remaining corner pieces into their correct place, without regarding orientation. Typical algorithms involve the rotation of 3 corner pieces either clockwise or counterclockwise. To do so takes and algorithm of only 8 moves. Depending on probability you may even be lucky enough to solve the cube at this point by simple rotating the corners.
<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc7.jpg" alt="Rubik's Cube Tutorial" /></center></td><td>An example of a sample 3 corner rotation clockwise.</td></table>

6. Finally, what may seem to be the most difficult part of the cube, rotating two corner pieces orientation. However, this is deceptively easy. As you can see in the diagram if one corners orientation is rotated clockwise, one other corner piece is rotated oppositely. This is because during the process of rotating one corner piece, the cube gets messed up. Then by performing the operations in reverse to another corner piece, it causes the cube to fix itself and rotate one other piece in the opposite direction. It can be viewed in a simpler case such as if you rotate the top face clockwise, naturally by doing the opposite and rotating the top face counterclockwise, you will fix the cube. This principle is in my opinion a key concept in understanding the Rubik’s Cube.

<table width="100%"><tr><td><center><img padding="0" width="150" src="/images/rc/rc8.jpg" alt="Rubik's Cube Tutorial"/></center></td><td>To perform the rotations shown in the diagram requires and algorithm of 12 moves. However, Don’t be alarmed, half of it is simply undoing what you did in the first half. So it’s really more like 6 moves to rotate a single corner piece in one direction, 1 move to put the next corner to rotate into position, 6 moves to undo the first 6 moves you did which causes the corner piece to rotate in the opposite direction. 1 move to readjust the top layer</td></table>

The secret to the Rubik’s Cube is practice. The more you work at it the more you will understand it.
Good Luck!

PDF Download available <a href="/pdf/rubikscube-en.pdf">here</a>
Japanese Version available here <a href="/pdf/rubikscube-jp.pdf">here</a>
