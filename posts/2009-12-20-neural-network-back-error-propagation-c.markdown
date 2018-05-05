---
title: Neural Network - Back-Error Propagation - C++
author: Kenny Cason
tags: machine learning, neural network, c , ニューラルネット, 機械学習, 誤差逆伝播法
---

Here is yet another simple Neural Network that implements Back-Error Propagation as a form of Reinforced Learning.
The entire project is written in C++ and requires no special libraries to compile and run.
main.cpp contains code to train both a 2 and 3-input Logical AND gate.
The zipped source code can be downloaded <a href="/code/c/nn01/NeuralNetwork.zip">here</a>
A Linux executable is already compiled and included in the zip, but feel free to recompile it. A Code::Blocks Project file is also included.

<b>To Compile</b>
<code>g++ *.cpp -o NeuralNetwork</code>

It will output an executable name "NeuralNetwork"

<b>To Run</b>

open a terminal and type:

<code>./NeuralNetwork</code>

<b>Sample Output</b>

<pre>Neural Network Connections Inited
Trained in 10000 trails within an error of 1.03127e-05
0 & 0 = 4.63117e-05
0 & 1 = 0.00349833
1 & 0 = 0.00290835
1 & 1 = 0.995469
Train Logical AND 2 Inputs Demo End
Neural Network Connections Inited
Training...
Trained in 5584 trails within an error of 9.99977e-06
0 & 0 & 0 = 3.62242e-05
0 & 0 & 1 = 0.00194301
0 & 1 & 0 = 0.000102096
0 & 1 & 1 = 0.00344352
1 & 0 & 0 = 0.000142368
1 & 0 & 1 = 0.00333881
1 & 1 & 0 = 0.0035418
1 & 1 & 1 = 0.993633
Logical AND 3 Inputs Demo End
</pre>

<b>Resources:</b>
<a href="/posts/2008-12-24-neural-networks-simple-models.html" target="blank">About Neural Networks (English)</a>
<a href="/posts/2008-12-24-neural-network-jp.html" target="blank">About Neural Networks (Japanese/日本語)</a>
<a href="/posts/2008-12-25-neural-network-back-error-propagation-java.html "target="blank">Java Implementation of a Neural Network</a>
