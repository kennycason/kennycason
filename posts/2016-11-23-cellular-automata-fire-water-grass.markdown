---
title: Cellular Automata Convergence - (Fire, Water, Grass)
author: Kenny Cason
tags: Cellular Automata
---

The code can all be found on GitHub: <a href="https://github.com/kennycason/cellular-automata-elements" target="blank">here</a>

Cells compete with neighboring cells:

- Fire beats Grass.
- Grass beats Water.
- Water beats Fire.
- If neighboring cells are empty, the cell is occupied by the attacker.
- Each cell has a determined amount of HP to determine when it dies.
- Each loss, a cell loses one HP.

A small gif demonstrating the rules being applied.

![](/images/cellular_automata_elements/fire_water_grass_small.gif)

A large gif demonstrating the rules being applied.

![](/images/cellular_automata_elements/fire_water_grass_large.gif?)

The below convergence graph shows how the populations starting from a random state tend towards homogeneousness. The end state is that each of the three elements represent approximately 33% of the occupied space.

![](/images/cellular_automata_elements/fire_water_grass_convergence.png)

A video of a larger simulation: <a href="https://v.usetapes.com/l58ybCs2bT" target="blank">here</a>
