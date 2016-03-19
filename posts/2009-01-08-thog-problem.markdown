---
title: Thog Problem
author: Kenny Cason
tags: iq, logic, problem, thog
---

Given the following combinations of shapes and colors:<br/>

<a href="#" onClick="alert1()"><img width="100" src="/images/thog/black_sqr.jpg" border=0></a>
<a href="#" onClick="alert2()"><img width="100" src="/images/thog/black_crc.jpg" border=0></a>
<a href="#" onClick="alert3()"><img width="100" src="/images/thog/white_sqr.jpg" border=0></a>
<a href="#" onClick="alert4()"><img width="100" src="/images/thog/white_crc.jpg" border=0></a>

The experimenter makes the following statement:<br/>

<blockquote>
"I am thinking of one color (black or white) and one shape (square or circle). Any figure that has either the color I am thinking of, or the shape I am thinking of, but not both, is a THOG. Given that the black square is a THOG what, if anything, can you say about whether the other figures are THOGS?"
</blockquote>
<p>Select the THOG by clicking on one of the shapes above</p>

<script langauage="JavaScript">
function alert1() {
    alert("No, the Black Square is the original THOG");
}
function alert2() {
    alert("No, the experimenter could be thinking of either (Black and Circle) or (White and Square). " +
	"If the experimenter is thinking of (Black and Circle) " +
	"the Black Circle has BOTH properties. If the experimenter is "+
	"thinking of (White and Square) the Black Circle has NEITHER property.");
}
function alert3() {
    alert("No, the experimenter could be thinking of either (Black and Circle) or (White and Square). " +
	"If the experimenter is thinking of (Black and Circle) " +
	"the white square has NEITHER property; if the experimenter is "+
	"thinking of (White and Square) the White Square has BOTH properties.");
}
function alert4() {
    alert("Yes, the experimenter could be thinking of either (Black and Circle) or (White and Square). " +
	"If the experimenter is thinking of (Black and Circle)" +
	"the White Circle has one property: Circle; if the experimenter is" +
	"thinking of (White and Square) the White Circle has one property: White.");
}
</script>
