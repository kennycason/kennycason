---
title: Support Vector Machine / サポートベクターマシン / 支持向量机
author: Kenny Cason
tags: artificial intelligence, machine learning, support vector machine, サポートベクターマシン, 支持向量机
---

Source code on Github: <a href="https://github.com/kennycason/supportvectormachine" target="blank">here</a>
Java OOP Port from C LibSVM library

<table width="600px">
<tr>
<td>
Linear Kernel
<img src="https://raw.github.com/kennycason/supportvectormachine/master/save/svm_linear.png" width="300"/>
</td>
<td>
Gaussian Kernel
<img src="https://raw.github.com/kennycason/supportvectormachine/master/save/svm_gaussian.png" width="300"/>
</td>
</tr>
<tr>
<td>
Polynomial Kernel
<img src="https://raw.github.com/kennycason/supportvectormachine/master/save/svm_polynomial.png" width="300"/>
</td>
<td>
Polynomial Kernel
<img src="https://raw.github.com/kennycason/supportvectormachine/master/save/svm_polynomial2.png" width="300"/>
</td>
</tr>
</table>


**Problem Loaders**

```java
IProblemLoader loader = new LibSVMProblemLoader();

Problem train = loader.load("svm/data/libsvm/linear_train.libsvm");
Problem test = loader.load("svm/data/libsvm/linear_test.libsvm");
/*
The typical LibSVM format, the first item represents the Class, the remaining
elements are prefixed by the index in the vector input that they represent

-1 1:1 2:1
-1 1:1 2:-1
-1 1:-1 2:1
-1 1:-1 2:-1
+1 1:5 2:5
+1 1:10 2:10
+1 2:10
*/

IProblemLoader loader = new SimpleProblemLoader();
Problem train = loader.load("svm/data/linear_train.svm");
Problem test = loader.load("svm/data/linear_test.svm");
/*
In this simple format, the first item represents the Class, the remaining
elements represent features of the class
i.e. -1 1 1 means Class = -1, input vector <1, 1>.
this simple format does not allow for sparse vectors

-1 1 1
-1 1 -1
-1 -1 1
-1 -1 -1
1 5 5
1 10 10
1 0 10
*/

```

Sample Outputs

```{.numberLines startFrom="1"}
Loading problem: svm/data/linear_train.svm
-1: 1 1
-1: 1 -1
-1: -1 1
-1: -1 -1
1: 5 5
1: 10 10
1: 0 10
Loading problem: svm/data/linear_test.svm
-1: 1 1
-1: 1 -1
-1: -1 1
-1: -1 -1
1: 5 5
1: 10 10
1: 0 10
Loaded.
Training...
Testing...
-1: 1.0 1.0
-1: 1.0 -1.0
-1: -1.0 1.0
-1: -1.0 -1.0
1: 5.0 5.0
1: 10.0 10.0
1: 0.0 10.0
7/7 correct
Accuracy=1.0
Done.
```
