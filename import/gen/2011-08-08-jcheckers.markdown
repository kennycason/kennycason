---
title: jCheckers - Display a Checkers Game State/Animation (jGames)
author: Kenny Cason
tags: checkers, jcheckers, jgames, jquery
---

jCheckers is one module within the <a href="http://ken-soft.com/2011/08/08/jgames/">jGames</a> suite used to display Checkers game states, as well as animations. jGames can be downloaded from the <a href="http://ken-soft.com/2011/08/08/jgames/">jGames home page</a>. 
    <table>
        <tr><td><div id="checkers"></div></td><td><div id="checkers_anim"></div></td></tr>
    </table>

<strong>Display Static Checkers State</strong>
First include the following lines to your webpage

```javascript
    <script type="text/javascript" src="js/jgames/jquery.jgames.js"></script>
    <link href="js/jgames/css/style.css" rel="stylesheet" type="text/css" />

```
Create an empty div tag and give it an ID, i.e. "checkers". This is where the chess board will be rendered to.

```javascript
<div id="checkers"></div>

```
Next, create the state of the chess board using Javascript. The below state represents every piece in the checkers game and renders the chess above left checkers board. (Note that currently "kings" are not supported, however they will be in later releases).

```javascript
        var board_checkers = [
            ["r", " ", "r", " ", "r", " ", "r", " "],
            [" ", "r", " ", "r", " ", "r", " ", "r"],
            ["r", " ", "r", " ", "r", " ", "r", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", " ", " ", " ", " ", " ", " ", " "],
            [" ", "b", " ", "b", " ", "b", " ", "b"],
            ["b", " ", "b", " ", "b", " ", "b", " "],
            [" ", "b", " ", "b", " ", "b", " ", "b"]];
         $("#checkers").checkers(board_checkers);
```

<strong>Creating an Animation</strong>
Creating an animation is very easy. You simply pass an array of states, and the time interval between states (in milliseconds) to the <b>checkersAnimator()</b> function. Below is the code to render the above right Checkers animation.

```javascript
        var board_checkers_anim =
        [
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", " ", " ", " ", " ", " ", " ", " "],
                [" ", " ", " ", " ", " ", " ", " ", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"],
                ["b", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", " ", " ", " ", " ", " ", " ", " "],
                ["b", " ", " ", " ", " ", " ", " ", " "],
                [" ", "", " ", "b", " ", "b", " ", "b"],
                ["b", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                [" ", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", " ", " ", " ", " ", " "],
                ["b", " ", " ", " ", " ", " ", " ", " "],
                [" ", "", " ", "b", " ", "b", " ", "b"],
                ["b", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                [" ", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", " ", " ", " ", " ", " "],
                ["b", " ", " ", " ", " ", " ", " ", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"],
                [" ", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                [" ", " ", " ", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", " ", " ", " "],
                ["b", " ", " ", " ", " ", " ", " ", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"],
                [" ", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", "r", " ", "r"],
                [" ", " ", "b", " ", "r", " ", "r", " "],
                [" ", "", " ", "r", " ", " ", " ", " "],
                [" ", " ", " ", " ", " ", " ", " ", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"],
                [" ", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ],
            [
                ["r", " ", "r", " ", "r", " ", "r", " "],
                [" ", "r", " ", " ", " ", "r", " ", "r"],
                [" ", " ", " ", " ", "r", " ", "r", " "],
                [" ", "r", " ", "r", " ", " ", " ", " "],
                ["", " ", " ", " ", " ", " ", " ", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"],
                [" ", " ", "b", " ", "b", " ", "b", " "],
                [" ", "b", " ", "b", " ", "b", " ", "b"]
            ]
        ];
       $("#checkers_anim").checkers(board_checkers_anim);

```
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script><script type="text/javascript" src="http://ken-soft.com/js/jgames/jquery.jgames.js"></script>
<script type="text/javascript" src="http://ken-soft.com/js/jgames/jquery.jgames.demo-data.js"></script>
<link href="http://ken-soft.com/js/jgames/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
$(document).ready(function(){$("#checkers").checkers(board_checkers);$("#checkers_anim").checkersAnimator(board_checkers_anim, 1000);});
//--></script>