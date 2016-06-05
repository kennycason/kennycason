---
title: Genetic Algorithm to Draw Images
author: Kenny Cason
tags: genetic algorithm, genetic programming, draw
---

The code can all be found on GitHub: <a href="https://github.com/kennycason/genetic_draw" target="blank">here</a>

### Includes

- Single Parent Models (Asexual reproduction)
- Two Parent Population Models (Sexual reproduction)
- Stochastic Selection (Selecting, with decreasing probability, less fit mates)
- Tournament Selection (Select most fit from a random sub-population)
- Static or Dynamic mutation probability rates
- Extendable Mutation/Selection classes
- DNA can be comprised of many shapes, including polygons (Size N), squares, rectangles, ellipses, circles, and pixels.

Two versions of Bulbasaur partially evolved. Used sexual reproduction via two parents. Population used stochastic acceptence with elitism to generate next populations.

<img src="/images/genetic_draw/bulbasaur_evolved_polygon.png"/>
<img src="/images/genetic_draw/bulbasaur_evolved_polygon.png"/>

Two GIFs showing the evolution of a square.

<img src="/images/genetic_draw/square_evolution.gif"/>
<img src="/images/genetic_draw/square_evolution2.gif"/>

GIF showing the evolution of the DataRank and Simply Measured logos.

<img src="/images/genetic_draw/datarank_whale_evolved.gif?raw=true"/>
<img src="/images/genetic_draw/sm_logo_evolved.gif?raw=true" height="125px"/>

Evolutions of Mario. The first is using a polygon rendering DNA. The second and third are using DNAs that render fixed position/sized pixels of size 4x4px and 8x8px.

<img src="/images/genetic_draw/mario_evolved_polygon.png" width="128px"/>
<img src="/images/genetic_draw/mario_evolved_pixel4.png" width="128px"/>
<img src="/images/genetic_draw/mario_evolved_pixel8.png" width="128px"/>

Evolution of Yoshi with convergence rate.

<img src="/images/genetic_draw/evolving_yoshi_with_stats.png" width="600px"/>

Mutation Rates and their effect on learning. Not normalized to generation count, you'll have to do some manual comparing. As expected, 10% performs better than 1% and 50%. I did not try a large number of intermittent values. (Note the generation count to see the faster convergence.)

<img src="/images/genetic_draw/single_parent_mutation_1_percent.png" width="400px"/>

<img src="/images/genetic_draw/single_parent_mutation_10_percent.png" width="400px"/>

<img src="/images/genetic_draw/single_parent_mutation_50_percent.png" width="400px"/>

On the topic of mutation, a low mutation rate means that the image converges slowly but gradually. However, there is a caveat. With a low mutation rate the algorithm is less likely to explore new search spaces which may be required to escape from a local minima. Similarly having too large of a mutation rate results in the algorithm constantly making too large of changes which result in a slowed convergence. A large mutation rate may serve well early on in the evolution, however, as time elapses, the required micro changes to finesse the model are less likely to occur. This is analogous to having too high of a learning rate in neural networks. Instead of adopting a simulated annealing-like model with a decreasing mutation rate, I implemented a random bounded mutation rate for each child produced. The results were more successful than a static mutation probability.

<img src="/images/genetic_draw/static_vs_dynamic_mutation_probability.png" width="300px"/>

Our good friend Kirby, evolved using both pixel and polygon rendering DNAs.

<img src="/images/genetic_draw/kirby_evolved_pixel4.png" width="128px"/>
<img src="/images/genetic_draw/kirby_evolved_polygon.png" width="128px"/>

However, one of our Kirby's didn't evolve so well. But why? It turns out that I had a bad fitness function. Specifically my fitness function compared the raw difference between raw RGB integer values between the evolved and target images. That is red is encoded in higher bits than green, and green higher than blue. This means that a difference between reds is significantly larger than differences between greens or blue and thus the genetic algorithm will perceive improvements of red being more important than blue. In other words, I introduced a bias in the fitness function. The result was random blue blotches in many of the renderings.

<img src="/images/genetic_draw/kirby_evolved_bad_fitness_function.png" width="128px"/>

Adding alpha channels to the polygons results in a considerable performance drop (about 7x). While adding alpha results in better results, there are options to remove the alpha channel. Below are two renderings, with alpha and without.

<img src="/images/genetic_draw/jing_evolved.png?raw=true" width="300px"/>
<img src="/images/genetic_draw/jing_evolved_no_alpha.png?raw=true" width="300px"/>

The canonical examples I found on the internet seem to be the evolution of Mona Lisa. Most examples I found demonstrated using triangles. I found that I had better results by mixing many shapes together. On the left is Mona Lisa evolved using rectangles and ellipses. Below, the first two evolutions demonstrate 1000 and 2000 genes containing only rectangles and ellipses, and the third using only triangles.

<img src="/images/genetic_draw/mona_lisa_evolved_1000_genes.png?raw=true" width="230px"/>
<img src="/images/genetic_draw/mona_lisa_evolved_2000_genes.png?raw=true" width="230px"/>
<img src="/images/genetic_draw/mona_lisa_evolved_polygon.png?raw=true" width="230px"/>

More of the statistics and graphs can be found in an excel file [here](https://github.com/kennycason/genetic_draw/raw/master/convergence_stats.xlsx).

And finally, a current snapshot of my profile being evolved. :)

<img src="/images/genetic_draw/my_profile_evolved4.png" width="400px"/>

And previous versions.

<img src="/images/genetic_draw/my_profile_evolved3.png" width="250px"/>
<img src="/images/genetic_draw/my_profile_evolved2.png" width="250px"/>
<img src="/images/genetic_draw/my_profile_evolved1.png" width="250px"/>
<img src="/images/genetic_draw/my_profile_evolved0.png" width="250px"/>
