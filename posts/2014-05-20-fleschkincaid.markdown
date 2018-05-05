---
title: Flesch/Kincaid Readability Test
author: Kenny Cason
tags: algorithm, nlp, java
---

This is a simple implementation of the Flesch-Kincaid readability tests in Java. From <a href="http://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests" target="blank">Wikipedia</a>, The Flesch–Kincaid readability tests are readability tests designed to indicate comprehension difficulty when reading a passage of contemporary academic English.

GitHub Source Code: <a href="https://github.com/kennycason/fleschkincaid" target="blank">here</a>

The basic algorithm is as follows:

```java
final double f1 = 206.835;
final double f2 = 84.6;
final double f3 = 1.015;
final double r1 = (double) syllableCount / (double) wordCount;
final double r2 = (double) wordCount / (double) sentences.size();

final double score = f1 - (f2 * r1) - (f3 * r2);
```

Control Data:

```java
// Using Naive Sentence Parser
Reading file: control/cat_in_the_hat.txt
107.98837976539589 (Actual 111)
Reading file: control/cnn_article.txt
44.86807692307694 (Actual 52)
Reading file: control/gettysburg_address.txt
62.97771223021584 (Actual 66)
Reading file: control/harry_potter.txt
59.290833333333346 (Actual 64)
Reading file: control/state_of_the_union.txt
57.22566326530614 (Actual 70)
Reading file: control/voter_preference.txt
26.881941176470626 (Actual 28.7)

// Using Stanford NLP Sentence Parser
Reading file: control/cat_in_the_hat.txt
118.51136363636364 (Actual 111)
Reading file: control/cnn_article.txt
43.510981463878345 (Actual 52)
Reading file: control/gettysburg_address.txt
62.933870967741946 (Actual 66)
Reading file: control/harry_potter.txt
65.63153508771933 (Actual 64)
Reading file: control/state_of_the_union.txt
59.565565610859736 (Actual 70)
Reading file: control/voter_preference.txt
26.881941176470626 (Actual 28.7)
```

Misc. data samples:
```java
Reading file: childrens_story1.txt
90.34097487432476
Reading file: childrens_story2.txt
91.72403884214481
Reading file: childrens_story3.txt
90.77821524178391
Reading file: lawyer1.txt
13.250785714285726
Reading file: lawyer2.txt
45.1943225582686
Reading file: lawyer3.txt
36.527610029341176
Reading file: sample_textbook.txt
34.17133834586468
Reading file: sample_essay1.txt
58.32419270507856
Reading file: sample_essay2.txt
71.230389261745
Reading file: sample_essay3.txt
62.60618553523733
Reading file: sample_essay4.txt
76.48776459820613
Reading file: 4th_grade_essay1.txt
66.83645933014357
Reading file: kindergarten_essay1.txt
79.20263157894739
Reading file: kindergarten_essay2.txt
89.51692307692309
Reading file: kindergarten_essay3.txt
85.67187500000001
```
