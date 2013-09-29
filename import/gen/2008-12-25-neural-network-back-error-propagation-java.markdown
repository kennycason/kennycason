---
title: Neural Network - Back-Error Propagation - Java
author: Kenny Cason
tags: ai, artificial intelligence, back-error propagation, java, neural network, programming, ニューラルネット, 人工知能, 誤差逆伝播法
---

This Neural Network is a command line implementation that uses the Back-Error Propagation learning algorithm. As well, The number of center layers, neurons per center layer, and learning rate are all changeable. The provided Test file teach Logial AND, however other test files can easily be created.

<b>update:</b>Source is now maintained on <a href="https://github.com/kennycason/neuralnetwork/" title="GitHub" target="_blank">GitHub</a>

The Jar file can be downloaded here: <a href="/code/java/nn01/NN.jar">NN.jar</a>
Below is the Syntax for running NN.jar in a command line:
<code>java -jar NN.jar [num center layers] [num center layer neurons] [learning rate]</code>
An example includes:
<code>java -jar NN.jar 2 10 1.5</code>
Which specifies a neural network with 2 center layers, 10 neurons per center layer, and a learning rate of 1.5. If now parameters are supplied it runs with default parameter; which are 1 center layers, 1 neurons per center layer, and a learning rate of 0.5.
That should give a display similar to the image below.
<a href="/code/java/nn01/NN01.png" target="_blank" ><img src="/code/java/nn01/NN01.png" width="250" alt="neural network back error propagation java"/></a>

Below are some links to A couple articles that give a good brief overview about neural networks, including some concepts about developing learning algorithms. Hope they are useful.
<a href="http://ken-soft.com/2008/12/24/neural-networks-simple-models/" target="_blank" >About Neural Networks (English)</a>
<a href="http://ken-soft.com/2008/12/24/%E3%83%8B%E3%83%A5%E3%83%BC%E3%83%A9%E3%83%AB%E3%83%8D%E3%83%83%E3%83%88%EF%BC%88%E7%A5%9E%E7%B5%8C%E5%9B%9E%E8%B7%AF%E7%B6%B2%E3%83%BB%E8%AA%A4%E5%B7%AE%E9%80%86%E4%BC%9D%E6%92%AD%E6%96%B9%EF%BC%89/" target="_blank" >About Neural Networks (Japanese/日本語)</a>

<p><a href="http://ken-soft.com/code/java/nn01/NeuralNetwork.java" class="code">NeuralNetwork.java</a></p>
<p><a href="http://ken-soft.com/code/java/nn01/Layer.java" class="code">Layer.java</a></p>
There are two test file for you to try out.
<p><a href="http://ken-soft.com/code/java/nn01/NeuralNetworkTest.java" class="code">NeuralNetworkTest.java</a></p>
<p><a href="http://ken-soft.com/code/java/nn01/NeuralNetworkTest2.java" class="code">NeuralNetworkTest2.java</a></p>