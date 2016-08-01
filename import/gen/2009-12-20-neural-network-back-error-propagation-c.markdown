---
title: Neural Network (Back-Error Propagation) C++
author: Kenny Cason
tags: ai, artificial intelligence, back-error propagation, c++, neural network, ニューラルネット, 人工知能, 誤差逆伝播法
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
<code>Neural Network Connections Inited
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
</code>

<b>Resources:</b>
<a href="http://kennycason.com/2008/12/24/neural-networks-simple-models/" target="_blank" >About Neural Networks (English)</a>
<a href="http://kennycason.com/2008/12/24/%E3%83%8B%E3%83%A5%E3%83%BC%E3%83%A9%E3%83%AB%E3%83%8D%E3%83%83%E3%83%88%EF%BC%88%E7%A5%9E%E7%B5%8C%E5%9B%9E%E8%B7%AF%E7%B6%B2%E3%83%BB%E8%AA%A4%E5%B7%AE%E9%80%86%E4%BC%9D%E6%92%AD%E6%96%B9%EF%BC%89/" target="_blank" >About Neural Networks (Japanese/日本語)</a>
<a href="http://kennycason.com/2008/12/25/neural-network-back-error-propagation-java/">Java Implementation of a Neural Network</a>