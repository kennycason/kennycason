---
title: Cellular Automata - (Fire, Water, Grass)
author: Kenny Cason
tags: Cellular Automata
---

The code can all be found on GitHub: <a href="https://github.com/kennycason/cellular-automata-pokemon-types" target="blank">here</a>

- Fire beats Grass.
- Grass beats Water.
- Water beats Fire.
- If neighboring cells are empty, the cell is occupied by the attacker.
- Each cell has a determined amount of HP to determine when it dies.
- Each loss, a cell loses one HP.
- Depending on mode, both the attacker and defender can mutually attack each other, else only the attacker attacks.

A large gif demonstrating the rules being applied with mutual attacks

![](/images/cellular_automata_elements/fire_water_grass_large_mutual_attacks.gif?raw=true)

A large gif demonstrating the rules being applied with only the attacker attacking. Note how much smoother the patterns are.

![](/images/cellular_automata_elements/fire_water_grass_large_only_attacker_attacks.gif?raw=true)

The below convergence graph shows how the populations starting from a random state tend towards homogeneousness. The end state is that each of the three elements represent approximately 33% of the occupied space.

The below graph shows when both the attacker and defender exchange an attack.

![](/images/cellular_automata_elements/fire_water_grass_convergence_mutual_attacks.png?raw=true)

The below graph shows when only the attacker attacks each step.

![](/images/cellular_automata_elements/fire_water_grass_convergence_only_attacker_attacks.png?raw=true)

A video of a larger simulation: <a href="https://v.usetapes.com/l58ybCs2bT" target="blank">here</a>
