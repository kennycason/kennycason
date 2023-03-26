---
title: 遗传算法与重现画像 - Improved Chinese with ChatGPT
author: Kenny Cason
tags: 遗传算法, 遗传编程, chatgpt, 中文, chinese
---

This project/experiment was motivated by the success I had using ChatGPT to improve my Chinese post on genetic algorithms.

[<b>Original Post</b>](/posts/2016-06-28-genetic-algorithm-draw-images-chinese.html)

遗传算法是一种用于解决最优化问题的搜索算法。本文提供的遗传算法可以用来重现人物画像。相关程序已经上传到GitHub，有兴趣的读者可以前往查看: <a href="https://github.com/kennycason/genetic_draw" target="blank">这里</a>

### 算法概念

这些编程中的遗传算法有两种。这两种算法都是通过遗传算法来把随机的图像演化成目标图像。个体的遗传是基因序列，每个基因表示一个形状的构成。可能的形状包括圆形，椭圆形，三角形，四边形，矩形，多边形和像素。除了形状之外，基因还包含其他信息。每个基因包含形状的颜色，透明度，位置（x，y，z轴）和大小。

基因表达过程很直接，个体基因中表示的形状画在黑色的图像上（根据z轴顺序）。由于这个应用程序的进化从完全随机的个体种群开始，所以第一代表达的基因的结果与目标图像平均完全不像。基因表达后，通过比较目标图像和表达图像来计算个体的适应度。这个比较函数被称为适应函数。在这个应用程序中，我们使用的适应函数是通过计算目标图像和表达图像的每个像素之间的误差来计算个体的总体误差。这个适应度决定了谁生存谁死，也称为“适者生存”。

在很多应用中，适应度越高越好，但是在这个应用中，由于我们在计算误差（错误函数），所以完美的适应度是零。零的适应度表示进化的个体表达的图像与目标图像完全一致。用更正式的说法，错误函数等于最大适应度减误差。以后当我说适应度高时，我的意思是误差接近零。

#### 算法参数

1. 种群规模：模型个体的数量。
2. 基因串长度：个体中染色体的数目。
3. 交叉概率：在两个个体（A和B）交叉繁殖时，孩子接收A个体基因的概率。接收B个体基因的概率为1.0减去接收A个体基因的概率。
4. 突变概率：在交叉或基因复制时，发生突变的概率。可以选择一个定值的概率，也可以选择变化的概率。改变突变概率的过程有点类似模拟退火算法。
5. 适应函数：能计算个体适应度的函数。有多种方法可以比较适应度。例如，将RGB值（红、绿、蓝）拆分成R、G、B三个值进行比较。R值也可以再拆分为RH（红高位）和RL（红低位）。
6. 利用的形状：该应用程序可以选择基因能表示的形状。
7. 子代选择函数：决定谁生存繁殖，谁死。通过精英主义选择最优秀的个体的遗传物质会传到下一代。在单亲遗传算法中，最优秀的个体的遗传物质会直接复制到下一代，在双亲遗传算法中，保证最优秀的个体有机会与其他种群中的个体交叉繁殖，产生子代。双亲遗传算法还有另一个参数。适应性高的个体需要选择适合的配偶交叉繁殖。有多种方法选择配偶。该应用程序可以选择两种方法：联赛选择算法和随机接受选择算法。联赛选择算法会随机选择几个个体（竞争者）。从这个小种群中适应性最高的个体交叉繁殖，产生子代。在随机接受选择算法中，适应性高的个体与适应性高的个体交叉繁殖的概率比选择适应性低的个体高。简而言之，强与强在一起，弱与弱在一起。当然，如果弱者有机会与强者交叉繁殖，他们不会拒绝，但是强者与弱者交叉繁殖的概率较低。该选择算法模拟了自然界中的优胜。


#### 单亲遗传算法 ([SingleParentGeneticDraw.kt](https://github.com/kennycason/genetic_draw/blob/master/src/main/java/com/kennycason/genetic/draw/SingleParentGeneticDraw.kt))

1. 随机创建一个亲体。 
2. 复制亲体的遗传物质以产生后代。每个基因根据变异概率进行变异。 
3. 评估后代的适应性。 
4. 如果后代的适应性优于亲体，则亲体死亡，后代成为新的亲体。 
5. 重复步骤2-4，直到亲体的适应性达到预期水平。


#### 双亲遗传算法 ([PopulationBasedGeneticDraw.kt](https://github.com/kennycason/genetic_draw/blob/master/src/main/java/com/kennycason/genetic/draw/PopulationBasedGeneticDraw.kt))

1. 从完全随机的种群个体开始。 
2. 评估个体的适应度。根据适应度排序个体。 
3. 选择最优秀的个体存活到下一代。 
4. 选择适应度最低的个体进行淘汰。他们不能繁殖。 
5. 根据子代选择函数，决定哪些个体有权利进行繁殖，生成下一代。 
6. 重复步骤2-5，直到满足种群的适应度水平。


### 特别的结果和统计

两个进化出我最喜欢的口袋妖怪（妙蛙种子）。使用双亲遗传算法，随机接受算法，精英选择。

<img src="/images/genetic_draw/bulbasaur_evolved_polygon.png?raw=true" height="250px"/> <img src="/images/genetic_draw/bulbasaur_evolved_polygon2.png?raw=true" height="250px"/>

两个GIF显示了黄色四角的进化。

<img src="/images/genetic_draw/square_evolution.gif"/> <img src="/images/genetic_draw/square_evolution2.gif"/>

两个GIF显示了DataRank公司和Simply Measured公司标志的进化。

<img src="/images/genetic_draw/datarank_whale_evolved.gif?raw=true"/> <img src="/images/genetic_draw/sm_logo_evolved.gif?raw=true" height="125px"/>

马里奥的进化。 左边的用多边形。中间和右边用像素。

<img src="/images/genetic_draw/mario_evolved_polygon.png" width="128px"/> <img src="/images/genetic_draw/mario_evolved_pixel4.png" width="128px"/> <img src="/images/genetic_draw/mario_evolved_pixel8.png" width="128px"/>

耀西的进化和收敛速度。

<img src="/images/genetic_draw/evolving_yoshi_with_stats.png" width="600px"/>

以下是我老婆头像进化的结果。虽然使用透明颜色会降低算法性能，但进化结果相当不错。左边和中间的图像使用了透明颜色。右边没有透明颜色。

<img src="/images/genetic_draw/jing_evolved_2500_genes.png?raw=true" width="230px"/> <img src="/images/genetic_draw/jing_evolved.png?raw=true" width="230px"/> <img src="/images/genetic_draw/jing_evolved_no_alpha.png?raw=true" width="230px"/>

我在开发这个遗传算法时，发现了几个网站，它们都有重现蒙娜丽莎的算法。我看了看，决定试一试。结果相当不错。以下有三个不同的进化，每个都使用了不同的参数。

<img src="/images/genetic_draw/mona_lisa_evolved_1000_genes.png?raw=true" width="230px"/> <img src="/images/genetic_draw/mona_lisa_evolved_2000_genes.png?raw=true" width="230px"/> <img src="/images/genetic_draw/mona_lisa_evolved_polygon.png?raw=true" width="230px"/>

以下三张图片表示突变概率对学习速度的影响。（为了更容易理解结果，我打算将这三个结果放在同一图片中）可以看出，突变概率为10%比1%和50%更有效。

<img src="/images/genetic_draw/single_parent_mutation_1_percent.png" width="400px"/>

<img src="/images/genetic_draw/single_parent_mutation_10_percent.png" width="400px"/>

<img src="/images/genetic_draw/single_parent_mutation_50_percent.png" width="400px"/>

虽然上面的图片并没有说明，但是最有效的做法是开始使用较高的突变概率，随着个体的进化，将突变概率逐渐降低。个体的适应度越高，较大的突变概率会产生的更适应的后代越少，因此在这种情况下，更低的突变概率更好。然而在开始阶段，由于个体的适应度非常低，因此更大的突变概率更有可能产生比亲体更适应的后代。

<img src="/images/genetic_draw/static_vs_dynamic_mutation_probability.png" width="300px"/>

我们以任天堂游戏《Kirby》中的角色进化为例。

<img src="/images/genetic_draw/kirby_evolved_pixel4.png" height="128px"/> <img src="/images/genetic_draw/kirby_evolved_polygon.png" height="128px"/> <img src="/images/genetic_draw/kirby_evolved_bad_fitness_function.png" width="120px"/>

看一下进化的Kirby，他明显有点奇怪。他的形状不错，但是颜色不正确。这是因为适应函数没有设置好导致的。我最初开发的适应函数评估画像像素时出了一个小问题。每个像素都是24bit，高位8bit表示红色，中位8bit表示绿色，低位8bit表示蓝色。想起我们之前讨论过的误差函数，一个像素的误差等于A画像的像素减B画像的像素。根据RGB（红、绿、蓝）的数位，由于红色的数位最高，所以红色的误差比其他颜色的误差更影响个体的适应度。相反，由于蓝色的数位最低，所以蓝色的误差对个体的适应度影响较小。

更多统计数据和图片可以通过下载查看。（XLSX）:[这里](https://github.com/kennycason/genetic_draw/raw/master/convergence_stats.xlsx).

最终展示的是我自己头像的进化结果。 :)

<img src="/images/genetic_draw/my_profile_evolved4.png" width="400px"/>

此外，还展示了之前的几代进化。

<img src="/images/genetic_draw/my_profile_evolved3.png" width="250px"/> <img src="/images/genetic_draw/my_profile_evolved2.png" width="250px"/>
<img src="/images/genetic_draw/my_profile_evolved1.png" width="250px"/> <img src="/images/genetic_draw/my_profile_evolved0.png" width="250px"/>

谢谢来看！
