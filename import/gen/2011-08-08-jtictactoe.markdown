---
title: jTicTacToe – Display a Tic Tac Toe Game State/Animation (jGames)
author: Kenny Cason
tags: jgames, jquery, jtictactoe, tic tac toe
---

jTicTacToe is one module within the <a href="http://kennycason.com/2011/08/08/jgames/">jGames</a> suite used to display Tic Tac Toe game states, as well as animations. jGames can be downloaded from the <a href="http://kennycason.com/2011/08/08/jgames/">jGames home page</a>. 
    <table>
        <tr><td><div id="tictactoe"></div></td><td><div id="tictactoe_anim"></div></td></tr>
    </table>

<strong>Display Static Tic Tac Toe State</strong>
First include the following lines to your webpage

```javascript
    <script type="text/javascript" src="js/jgames/jquery.jgames.js"></script>
    <link href="js/jgames/css/style.css" rel="stylesheet" type="text/css" />

```
Create an empty div tag and give it an ID, i.e. "tictactoe". This is where the tic tac toe board will be rendered to.

```javascript
<div id="tictactoe"></div>

```
Next, create the state of the tic tac toe board using Javascript. The below state represents every piece in the Tic Tac Toe game and renders the tic tac toe above left tic tac toe board.

```javascript
        var board_tictactoe = [
            ["o", "o", "x"],
            ["o", "x", "x"],
            [" ", "o", " "]
        ];
       $("#tictactoe").tictactoe(board_tictactoe);
```

<strong>Creating an Animation</strong>
Creating an animation is very easy. You simply pass an array of states, and the time interval between states (in milliseconds) to the <b>tictactoeAnimator()</b> function. Below is the code to render the above right Tic Tac Toe animation.

```javascript
        var board_tictactoe_anim =
        [
            [
                [" ", " ", " "],
                [" ", " ", " "],
                [" ", " ", " "]
            ],
            [
                [" ", " ", "o"],
                [" ", " ", " "],
                [" ", " ", " "]
            ],
            [
                [" ", " ", "o"],
                [" ", "x", " "],
                [" ", " ", " "]
            ],
            [
                [" ", " ", "o"],
                [" ", "x", " "],
                [" ", " ", "o"]
            ],
            [
                [" ", " ", "o"],
                [" ", "x", "x"],
                [" ", " ", "o"]
            ],
            [
                [" ", " ", "o"],
                [" ", "x", "x"],
                [" ", "o", "o"]
            ],
            [
                [" ", " ", "o"],
                ["x", "x", "x"],
                [" ", "o", "o"]
            ]
        ];

        $("#tictactoe_anim").tictactoeAnimator(board_tictactoe_anim, 1000);
```
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script><script type="text/javascript" src="http://kennycason.com/js/jgames/jquery.jgames.js"></script>
<script type="text/javascript" src="http://kennycason.com/js/jgames/jquery.jgames.demo-data.js"></script>
<link href="http://kennycason.com/js/jgames/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
$(document).ready(function(){$("#tictactoe").tictactoe(board_tictactoe);$("#tictactoe_anim").tictactoeAnimator(board_tictactoe_anim, 1000);});
//--></script>