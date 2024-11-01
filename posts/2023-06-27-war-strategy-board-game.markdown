---
title: War - Chess-like, Customizable Strategy Board Game
author: Kenny Cason
tags: board game, game development
---

<img class="modal-target" src="/images/war/war-header.jpeg" width="100%"/>

## Background

Growing up, chess was my favorite board game. 
I was an active member of the Chess Club and even attended tournaments, though I unfortunately only managed to place as high as 6th in the regionals. 
Despite having a lot of fun, I gradually became a bit bored with chess over the years but had always wanted to design my own board game.
This is similar to how after learning to code, I immediately developed a desire to create games as well as play them.

Once I committed to building a board game, I established a few criteria for the game I wanted to create: 

1. Deterministic, chess-like rules to eliminate luck as a factor in player movement.
2. Diverse and unique pieces with complementary rules.
3. Considerations for terrain and/or environment. <sup>[1]</sup>
4. Customizable and modular terrain to introduce variability in game-play. <sup>[2]</sup>

<sub>[1] Version 1.0 only contains basic ground tiles, but I plan to introduce mud and water tiles in the future.</sub><br/>
<sub>[2] My aim here was to primarily eliminate randomness in player movement, but allow players to introduce randomness by creating non-uniform terrains if they choose.</sub><br/>


## Rules of War v1.0

### The Basics
- The game ends when any of the following conditions are met:
	- A player `wins` if the opponent's `Commander` dies.
	- A player `wins` if the opponent's only remaining piece is their `Commander`.
	- A player `loses` if they no longer have any attack pieces, excluding the `Commander`.
	- A player `wins` if the opponent surrenders.
	- A `draw` is declared if only the `Commander`s are remaining.
	- A `draw` is declared  the same two pieces repeat the same back-and-forth move more than twice, or it has been determined that the game has entered a endless cycle.
- If desired, time-limits and/or turn limits can be imposed.
- Turns consist of each player moving 1 piece at a time.
- Terrain provides tactical advantages and disadvantages. Refer to each piece's rule to determine how terrain impacts each piece.
- Pieces cannot ascend or descend more than 1 unit of elevation per tile during movement.
- Pieces cannot move through or over other pieces unless specified otherwise.
- Pieces cannot move diagonally around walls <sup>[2]</sup>, or over gaps <sup>[3]</sup>.
- Some pieces, such as `Artillery` and `Missiles`, can move irrespective of their attack pattern.
- `Line-of-Sight`:
	- A path can be drawn using diagonal, vertical, or horizontal lines between tiles.
	- If there are elevation changes, there must not be points where the elevation is higher than either of the 2 pieces, unless the target tile is in the `Line-of-Sight` of another piece.
	- Cannot see through small walls and pieces unless otherwise stated.

<sub>[1] A fortification is a tile piece with a raised edge which serves to protect from direct attack.</sub><br/>
<sub>[2] A wall is a tile that is 2 or more units higher than neighboring tile.</sub><br/>
<sub>[3] A gap is formed when a neighboring tile is 2 or more units lower than current tile.</sub><br/>

### Starting Formation
- The `Commander` begins in a corner-most tile.
- The remaining pieces are placed around the `Commander` in the following order:
	- `Air Defense`
	- `Missile`
	- `Bomber`
	- `Artillery`
	- `Tank`
	- `Infantry`
	- `Transport` (Optional)
	- `Excavator` (Optional)
- Note that the resulting shape of the troop formation is meant to be flexible based on player preference, number of players, and size of board.
- Common formations include: 
	- A 4x4 grid (16 total pieces)
	- A 3x6 grid (18 total pieces)
	- A 5x5 grid (25 total pieces)
- Feel free to experiment with different formations.


### Pieces

#### `Commander`
- Can move/attack 1 tile in any direction.

#### `Infantry`
- Can move 1 tile horizontally or vertically.
- Can attack 1 tile diagonally.
- Can attack from any elevation.

#### `Tank`
- Can move/attack 1-2 tiles straight horizontally or vertically.

#### `Artillery`
- Can move 1 tile in any direction.
- Can fire a shell 1 to 3 tiles plus 1 tile per unit of elevation difference in a straight line horizontally or vertically.
- Cannot fire for 1 turn. Reloads shell at the beginning of the 2nd turn.
- Immediate auto-reloads in the same round if positioned next to `Commander`.
- Cannot fire into immediately neighboring tiles.
- Can fire at a tile if it is in the `Line-of-Sight` of any piece on your team.
- Can fire over any piece.
- Can fire over small walls.

#### `Missile`
- Can move 1 tile in any direction.
- Can fire 1-5 tiles plus 1 tile per unit of elevation difference diagonally.
- Cannot fire into immediately neighboring tiles.
- Can fire at a tile if it is in the `Line-of-Sight` of any piece on your team.
- Can fire over any piece.
- Can fire over small walls.

#### `Air Defense`
- Can move 1 tile in any direction.
- Can defend against `Artillery` shells, `Missiles`, or `Bombers` that fly within 1 tile in any direction.
	- If 2 `Air Defense` systems are within response range, the defending player chooses which 1 to respond with.
- Single-use only, gets destroyed after defending.
- Defense systems auto-fire if an object enters the fire-zone.
  - This includes when a piece is retreating from an `Air Defense` unit.
- Cannot attack directly, for defense only.

#### `Bomber`
- Can move/attack 1-4 tiles horizontally or vertically.
- Elevation does not affect the `Bomber`.  
- Can fly over own team’s pieces but not the opponent’s.

#### `Transport` (Optional)
- Can move 1-3 tiles horizontally or vertically.
- Can spend 1 turn to load a `Transport` with 1 piece from any neighboring tile. 
- Can spend 1 turn to drop off the loaded piece into any empty neighboring tile. 
- Can transport any piece on your team.
- Pieces are not active while being transported.
- If a piece is destroyed while being transported, both the piece and the `Transport` are destroyed.

#### `Excavator` (Optional)
- Can move 1 tile in any direction.
- Can raise or lower any neighboring tile by 1 unit of elevation. 
- Excavation: The minimum elevation is 1. The maximum elevation is determined by the available pieces. Can stack tiles if needed.

### Scoring of Pieces

Here's a sample scoring for each piece. However, note that this has not been tested and is meant to serve as a starting point.

| &nbsp;Piece&nbsp; | &nbsp;Score&nbsp; |
|-------------|--------|
| &nbsp;Commander   | &nbsp;∞ |
| &nbsp;Bomber      | &nbsp;7 |
| &nbsp;Missile     | &nbsp;6 |
| &nbsp;Artillery   | &nbsp;5 |
| &nbsp;Air Defense&nbsp; | &nbsp;4 |
| &nbsp;Tank        | &nbsp;3 |
| &nbsp;Transport   | &nbsp;2 |
| &nbsp;Excavator   | &nbsp;2 |
| &nbsp;Infantry    | &nbsp;1 |


- A player receives no points for an opponent's `Missile` blowing up after use. `Missiles` must be attacked directly to receive points.
- A player receives no points if an opponent sacrifices their `Air Defense` to defend against `Missiles` or `Jets`. `Air Defenses` must be attacked directly to receive points. 


### Optional Rules
- `Tank` can overrun own `Infantry` piece in a sacrifice if needed.
- `Air Defenses` are multi-use. 
- `Missiles` are be multi-use.
- Introduce water tiles that can only be traversed by air.
- Introduce mud tiles that slow movement by 1. 


## Notes & Progress
- Currently, I'm calling this project "War", but I am still brainstorming other names. 
- The rules and pieces are still v1.0 and are subject to change. 
- `Coming Soon`: Graphics to better demonstrate individual piece rules and game mechanics. 
- In order to verify the "interesting-ness" of the game, I'm aiming to building a video game + AI in order to help with testing. 
	- AI v1.0 will use Mini-Max + Monte-Carlo searches.
	- AI v2.0 will use Neural Networks.
	- The hope is that by observing games played by the NN-backed AI, as well as playing games against the AI, it will more clear if the game has any problems, such as over powered pieces, strategies, or any other problems. Upon discovering a problem, the rule-sets can be updated and the AI re-trained, re-evaluated, rinse and repeat.


### Photo Album

<img class="modal-target" src="/images/war/war-evan-and-steve-beta-testers.jpeg" width="100%"/>

My good friends Evan and Steve who were the first two to play test the game as well as numerous conversations, ideas, and feedback. Thanks!

---------------------
<img class="modal-target" src="/images/war/war-midgame.jpeg" width="100%"/>

Mid-game. 
Gray team has the right side hill access well defended with their `Tank`, `Bomber`, `Artillery`, and `Missile`. 
Meanwhile Black team has a pretty strong hold on the center hill with 4 `Infantry` units backed by a `Bomber` and a rearward `Missile`.

---------------------

<img class="modal-target" src="/images/war/war-side-by-side.jpeg" width="100%"/>

The bottom which holds the pieces and tiles alongside the main board.

---------------------


<img class="modal-target" src="/images/war/war-top-and-bottom.jpeg" width="100%"/>

The tiles and pieces packed inside.

---------------------

<img class="modal-target" src="/images/war/war-20x20-scarlett.jpeg" width="100%"/>

This is a prototype on a 20x20 board, and was aimed to be a 2x2 4-player game. Players move in round robin, alternating teams each move.



### The Build

<img class="modal-target" src="/images/war/war-build-step-1.jpeg" width="100%"/>

Using a 1"x1" square dowel rods, I cut a enough variable height tiles to fill a 11x11 tile board. 
There are 4 different heights on this version. 
The 20x20 large game board has 6 different heights.

---------------------

<img class="modal-target" src="/images/war/war-build-step-2.jpeg" width="100%"/><br/>

Using a miter saw and table router, I cut out a frame which will go own to hold the game board where the tiles are laid.
The inner dimensions needs to be 11"x11".
Everything is held together with glue and clamps.

---------------------

<img class="modal-target" src="/images/war/war-build-step-3.jpeg" width="100%"/><br/>

Next I installed a thin layer of 11"x11" cedar wood by gluing it into place.
I enjoy the smell the cedar gives off. 

---------------------

<img class="modal-target" src="/images/war/war-build-step-4.jpeg" width="66%"/><br/>

Finally, Chloe and Scarlett get to place tiles and design all kinds of terrains. 

They helped apply wood glue, sand pieces, and more. :)

---------------------

<img class="modal-target" src="/images/war/war-chloe-and-scarlett-configuration.jpeg" width="66%"/><br/>

The Official Chloe & Scarlett Terrain.

---------------------