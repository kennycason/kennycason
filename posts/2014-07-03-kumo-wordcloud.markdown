---
title: Kumo - Java Word Cloud
author: Kenny Cason
tags: kumo, word cloud, 标签云, タグクラウド, java
---

Kumo On GitHub: <a href="https://github.com/kennycason/kumo" target="blank">here</a>

The goal of Kumo is to create a powerful and user friendly Word Cloud library in Java. Kumo can directly generate an image file, or return a BufferedImage. I plan on hosting it on Maven Central soon.

Please feel free to jump in and help improve Kumo! There are many places for performance optimization in Kumo!

Current Features
<ol>
<li>Draw Rectangle, Circle or Image Overlay word clouds. Image Overlay will draw words over all non-transparent pixels.</li>
<li>Linear, Square-Root Font Scalars. Fully extendible.</li>
<li>Variable Font Sizes.</li>
<li>Word Rotation. Just provide a Start Angle, End Angle, and number of slices.</li>
<li>Custom BackGround Color. Fully customizable BackGrounds coming soon.</li>
<li>Word Padding.</li>
<li>Load Custom Color Pallettes.</li>
<li>Two Modes that of Colision and Padding: PIXEL_PERFECT and RECTANGLE.</li>
<li>Polar Word Clouds. Draw two opposing word clouds in one image to easily compare/contrast date sets.</li>
<li>Layered Word Clouds. Overlay multiple word clouds.</li>
<li>WhiteSpace and Chinese Word Tokenizer. Fully extendible.</li>
<li>Frequency Analyzer to tokenize, filter and compute word counts.</li>
</ol>

<table>
<tr><td>
<img src="/images/kumo/datarank.png" width="350"/>
</td><td>
<img src="/images/kumo/simplymeasured-transparent-bg.png" width="350"/>
</td></tr>
<tr><td>
<img src="/images/kumo/datarank_wordcloud_circle_sqrt_font.png" width="350"/>
</td><td>
<img src="/images/kumo/chinese_language_circle.png" width="350"/>
</td></tr>
<tr><td>
<img src="/images/kumo/polar_newyork_rectangle_blur.png" width="350"/>
</td><td>
<img src="/images/kumo/polar_tide_chinese_vs_english2.png" width="350"/>
</td></tr>
<tr><td>
<img src="/images/kumo/layered_haskell.png" width="300"/>
</td><td>
<img src="/images/kumo/layered_pho_bowl.png" width="300"/>
</td></tr>
<tr><td>
<img src="/images/kumo/whale_wordcloud_large_angles.png" width="350"/>
</td><td>
<img src="/images/kumo/layered_word_cloud.png" width="350"/>
</td></tr>
<tr><td>
<img src="/images/kumo/polar_newyork_whale_large_blur.png" width="350"/>
</td><td>
<img src="/images/kumo/wordcloud_rectangle.png" width="350"/>
</td></tr>
</table>

Download from Maven Central

```xml
<dependency>
    <groupId>com.kennycason</groupId>
    <artifactId>kumo</artifactId>
    <version>1.5</version>
</dependency>
```
Example to generate a Word Cloud on top of an image.

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
frequencyAnalyzer.setWordFrequenciesToReturn(300);
frequencyAnalyzer.setMinWordLength(4);
frequencyAnalyzer.setStopWords(loadStopWords());

final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/datarank.txt"));
final Dimension dimension = new Dimension(500, 312);
final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
wordCloud.setPadding(2);
wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale_small.png")));
wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
wordCloud.setFontScalar(new LinearFontScalar(10, 40));
wordCloud.build(wordFrequencies);
wordCloud.writeToFile("output/whale_wordcloud_small.png");
```

Example to generate a circular Word Cloud.

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/my_text_file.txt"));
final Dimension dimension = new Dimension(600, 600);
final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
wordCloud.setPadding(2);
wordCloud.setBackground(new CircleBackground(300));
wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
wordCloud.build(wordFrequencies);
wordCloud.writeToFile("output/datarank_wordcloud_circle_sqrt_font.png");
```

Example to generate a rectangle Word Cloud

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/my_text_file.txt"));
final Dimension dimension = new Dimension(600, 600);
final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
wordCloud.setPadding(0);
wordCloud.setBackground(new RectangleBackground(dimension));
wordCloud.setColorPalette(buildRandomColorPalette(20));
wordCloud.setFontScalar(new LinearFontScalar(10, 40));
wordCloud.build(wordFrequencies);
wordCloud.writeToFile("output/wordcloud_rectangle.png");
```

Example of tokenizing chinese text into a circle

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
frequencyAnalyzer.setWordFrequenciesToReturn(600);
frequencyAnalyzer.setMinWordLength(2);
frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/chinese_language.txt"));
final Dimension dimension = new Dimension(600, 600);
final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
wordCloud.setPadding(2);
wordCloud.setBackground(new CircleBackground(300));
wordCloud.setColorPalette(new ColorPalette(new Color(0xD5CFFA), new Color(0xBBB1FA), new Color(0x9A8CF5), new Color(0x806EF5)));
wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
wordCloud.build(wordFrequencies);
wordCloud.writeToFile("output/chinese_language_circle.png");
```

Create a polarity word cloud to contrast two datasets

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
frequencyAnalyzer.setWordFrequenciesToReturn(750);
frequencyAnalyzer.setMinWordLength(4);
frequencyAnalyzer.setStopWords(loadStopWords());

final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/new_york_positive.txt"));
final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/new_york_negative.txt"));
final Dimension dimension = new Dimension(600, 600);
final PolarWordCloud wordCloud = new PolarWordCloud(dimension, CollisionMode.PIXEL_PERFECT, PolarBlendMode.BLUR);
wordCloud.setPadding(2);
wordCloud.setBackground(new CircleBackground(300));
wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
wordCloud.build(wordFrequencies, wordFrequencies2);
wordCloud.writeToFile("output/polar_newyork_circle_blur_sqrt_font.png");
```


Create a Layered Word Cloud from two images/two word sets

```java
final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
frequencyAnalyzer.setWordFrequenciesToReturn(300);
frequencyAnalyzer.setMinWordLength(5);
frequencyAnalyzer.setStopWords(loadStopWords());

final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/new_york_positive.txt"));
final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(getInputStream("text/new_york_negative.txt"));
final Dimension dimension = new Dimension(600, 386);
final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(2, dimension, CollisionMode.PIXEL_PERFECT);

layeredWordCloud.setPadding(0, 1);
layeredWordCloud.setPadding(1, 1);

layeredWordCloud.setFontOptions(0, new KumoFont("LICENSE PLATE", FontWeight.BOLD));
layeredWordCloud.setFontOptions(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));

layeredWordCloud.setBackground(0, new PixelBoundryBackground(getInputStream("backgrounds/cloud_bg.bmp")));
layeredWordCloud.setBackground(1, new PixelBoundryBackground(getInputStream("backgrounds/cloud_fg.bmp")));

layeredWordCloud.setColorPalette(0, new ColorPalette(new Color(0xABEDFF), new Color(0x82E4FF), new Color(0x55D6FA)));
layeredWordCloud.setColorPalette(1, new ColorPalette(new Color(0xFFFFFF), new Color(0xDCDDDE), new Color(0xCCCCCC)));

layeredWordCloud.setFontScalar(0, new SqrtFontScalar(10, 40));
layeredWordCloud.setFontScalar(1, new SqrtFontScalar(10, 40));

layeredWordCloud.build(0, wordFrequencies);
layeredWordCloud.build(1, wordFrequencies2);
layeredWordCloud.writeToFile("output/layered_word_cloud.png");
```
