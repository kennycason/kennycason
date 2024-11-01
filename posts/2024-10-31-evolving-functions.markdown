---
title: Evolving Functions (EvoFun)
author: Kenny Cason
tags: math, genetic algorithm, kotlin
---
<video width="50%" controls autoplay muted loop><source src="https://cdn.kennycason.com/evofun/1729899046.mp4" type="video/mp4"></video><video width="50%" controls autoplay muted loop><source src="https://cdn.kennycason.com/evofun/1730174530.mp4" type="video/mp4"></video>

EvoFun is a generative art project that uses genetic algorithms to evolve DNA-inspired structures composed of mathematical functions, creating complex, dynamic patterns over time. At its core, EvoFun simulates DNA with two main components: genes, a sequence of mathematical functions that transform coordinates, and color genes, which define the organism’s visual expression. By composing and iterating these functions, each “organism” produces unique patterns on a canvas that evolve continuously, giving rise to intricate designs. 

<a href="https://github.com/kennycason/evofun/" target="_blank"><b>Source on GitHub</b></a>

### DNA Structure: Genes and Color Genes

<b>Genes:</b> Each gene in the DNA holds a transformation function over coordinates %%(x, y)%% to create fractal-like patterns. The functions are composed in a chain, where each gene applies a mathematical transformation that subtly alters or distorts the result from the previous step. Functions include options like `ABS`, `SWIRL`, `SPIRAL`, `SQUARED`, `GUASSIAN`, `SIN_COS`, `TRANSLATE`, `ROTATE`, and `SCALE` among others. These functions are recursively applied to generate self-similar, fractal patterns by transforming the coordinates repeatedly. Each gene also includes six numeric parameters, which the transformation functions can use for additional variability.

<b>Color Genes:</b> In addition to structural genes, DNA contains color genes, which determine the color of each point based on various characteristics such as height or intensity. The color genes translate the final results into color space by applying a variety of color mixing algorithms. These color genes are independent of the structural genes and thus evolve separately.

#### Below is an example of how genes are encoded:

```json
{
  "genes" : [ {
    "function" : "PARABOLIC",
    "a" : -0.9211078917930305,
    "b" : -0.9222121723293334,
    "c" : -0.8493207679992957,
    "d" : -0.4664991509256924,
    "e" : -0.968919175052643,
    "f" : 0.6850299635998457
  }, ... ],
  "colorGene" : {
    "algorithm" : "FUNCTIONS",
    "genes" : [ {
      "function" : "HORSESHOE",
      "a" : -0.9211078917930305,
      "b" : -0.9222121723293334,
      "c" : -0.8493207679992957,
      "d" : -0.4664991509256924,
      "e" : -0.968919175052643,
      "f" : 0.6850299635998457
    }, ... ],
    "alpha" : 0.7657284110765499
  }
}
```

#### Applying the Genetic Algorithm: Evolving Patterns Over Time

In EvoFun, the genetic algorithm iteratively evolves the DNA through transformations on a 2D grid (or “buffer”), where each %%(x, y)%% coordinate is manipulated by a chain of functions %%f \circ g \circ h \dots (x, y) \rightarrow (x{\prime}, y{\prime})%%. This transformation chain accumulates a “count” for each intermediate position in the buffer, representing the number of times a coordinate is visited or affected. The count is a key factor in how the color genes later interpret the visual intensity at each point, essentially “mapping the color based on height.”


#### Program Flow: Breeding, Mutation, and Selection

The interactive nature of EvoFun allows users to experiment with the evolving patterns by adjusting DNA in real-time:

- Pressing ‘N’ generates a new random DNA, thus increasing genetic diversity.
- Pressing ‘B’ triggers breeding, combining two previously saved DNA samples from the DNA pool to produce a new organism. This mechanism simulates sexual reproduction, where two existing patterns are combined at random to yield a new DNA with potentially more interesting visual qualities.
- Pressing ‘S’ saves the current DNA and a screenshot of the canvas to the DNA pool, allowing for future use in breeding or reloading.

The program also includes a basic entropy function which will birth a new DNA if the image is too boring. If a pattern becomes “boring” (low entropy) or reaches a certain iteration threshold, the organism resets with a new DNA sequence. This system keeps the visual output dynamic and engaging.

#### The Main Application: Interactive Evolution

The main application of EvoFun renders these DNA-driven organisms onto a canvas. With each iteration, the algorithm:

- <b>Transforms Coordinates:</b> For each of n random coordinate %%(x, y)%%, undergo a series of transformations, accumulating visits in an intermediate buffer.
- <b>Maps Color:</b> After transformation, the color genes assign colors based on accumulated counts, creating depth and intensity gradients in the visual output. Counts are slowly decreased each iteration to prevent over-saturation. 
- <b>Mutates DNA:</b> Depending on the visual entropy of the current DNA, mutation is applied, adjusting genes slightly to promote further evolution.

The result is a constantly evolving visual displays as seen below.

### Videos

<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/bDJs5xCmJcs?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div><br/>
<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/xYB6IgKBQOY?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div><br/>
<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/iowYCd0iKcg?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div><br/>

### Images

<div style="display:flex; width: 100%; flex-wrap: wrap;">
<img class="modal-target" src="/images/evofun/iteration_1728715656_0.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1728722276_200.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1728722000_2200.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1728721472_0.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1728718403_100.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1728722420_100.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729313889_89.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729313922_307.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729317878_860.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729317832_592.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1728720385_700.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1728973553_4000.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1728973332_1900.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1728973458_3100.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729109758_100.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729109803_200.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729226408_6939.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729228963_190.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729229357_1980.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729229465_2430.png" width="49%"/>
<img class="modal-target" src="/images/evofun/iteration_1729229879_4040.png" width="49%"/><img class="modal-target" src="/images/evofun/iteration_1729232157_760.png" width="49%"/>
</div>
