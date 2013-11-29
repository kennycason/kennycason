---
title: Markov Chains - Java
author: Kenny Cason
tags: markov chains, java
---

I woke up on Thanksgiving to discover my friend <a href="http://www.grantslatton.com" target="_blank">Grant Slatton's</a> #1 HN post using <a href="http://en.wikipedia.org/wiki/Markov_chain" target="_blank">Markov Chains</a> to generate random HN Titles based on the daily top 10 posts for the last 1 year. His site can be found here: <a href="http://grantslatton.com/hngen/?lookback=2" target="_blank">http://grantslatton.com/hngen/</a>. There you can also find his data sets and source code (python).


I had a bit of free time so I decided to build one in Java, nice and verbose. I generated results using both a Unigram and Bigram tokenizer. I originally built the Markov Chain generator to function on the HN dataset that Grant generated, but also compiled a dataset of ~7000 tweets mentioning Tide Pods, and another containing <a href="http://www.gamerevolution.com/game/all/all/long_name/asc" target="_blank">17,000+ video game titles</a>. I had no idea there were so many game titles :)


The GitHub source code can be found: <a href="https://github.com/kennycason/markovchains/" target="_blank">here</a>


Here are some sample auto-generated video game titles: 
```
Bigram:
--------------------
Ragnarok Online 2: Legend Rock 
City of Edition 
The Lord of the Horned Rat 
Gabriel Knight 2: The Last Resort 
Virtual Villagers 2: The Destiny 
The Lord of the New World 
Turtle Beach Modern Warfare 
The Elder Scrolls IV: Knights of the Cross 
Monkey Island Chapter 5: Rise of Nations: Rise of Legends 
World War Final Front 
Command and Conquer: Red Counterstrike 
Rise of Wolf 
Rise of a Soldier 
Indiana Jones and the Lost Treasure 
The Grim Adventures of Van Helsing 
Star Wars: Knights of Cable 
World Cup South Africa 
Knights And Dragons: Rise of the Black Dogs 
Tony Tough and the Donut Disaster 
Rise of a Dynasty 
SOCOM U.S. Navy Seals 
The City That Dares Not Sleep 
Prinny 2: Dawn of the Dragon 
Lord of the Rings: Return of Lee 
Math Blaster in the Salad Kingdom 
Star Ocean The Last of Us: Left Behind 
Mario Vs. Donkey Kong: Again! 
Rome: Total War - Fire Age 
Beijing 2008 - The Army 
Mr. Biscuits - Case of the Horned Rat 
Splinter Cell: U) 
Sam & Max Episode 203: Night of the Rings: The Third Age 
Chronicles of Mystery: Curse of the Rings: War Ring 
World in 80 Days 
Warhammer: Shadow of the Ninja 2 
No Gravity - The Burning Earth 
Tales of Monkey Island 
Warhammer 40,000: Dawn of Magic 2 
Fist of the North 
Revenge of the Enchantress 
Heroes of Might and Magic 6: Heaven 
Spyro: The Legend of Zelda: Oracle of Seasons 
Naruto Shippuden: Ultimate Ninja Storm 3 
Enslaved: Odyssey to the '98 
Age of Pirates 2: City of Freedom 
Phoenix Wright: Ace Attorney 
Koala Lumpur: Journey to Rooted Hold 
NARUTO Shippuden: Ultimate Ninja Heroes 3 
Ni No Kuni: Wrath of the Modern World 
Billy Hatcher and the Golden Touch 
```


Here are some sample auto-generated HN Titles:
```
Bigram:
--------------------
What everyone should know 
Richard Dreyfuss' dramatic reading of the word Daemon 
Can we please slow down the Sality botnet 
A Built-in Web Server 
NSA monitored 500 million Germany 
This is what it's like to Mars" 
2005 Zuckerberg Didn't Want To Take This Down 
Ask HN: Does anyone know what's going on in Germany 
OAuth 2.0 and the DRM-Free Revolution 
How one man escaped from a Stanford Student 
Build a Classifier 
DHS asks Mozilla to Certificate Authorities: no subordinate CAs for traffic interception 
How Forbes Stole a New York Section 
Google Docs makes it personal 
I Don???t Need No Stinking API: Web Scraping Python 
Youtube claims I don't learn anything on HN 
Cable lacing on the Store 
This hacker might seem shady, but throwing him in jail is bad news if you plan country 
Site plagiarizes blog posts, then files DMCA takedown notices they receive here. 
How To Make a Python 
Everything popular is wrong: The NSA Is Breaking Most Encryption Internet 
How to Learn to read a binary font by reading a story 
A Warning to Publishers on The Dangers of Always-Online DRM 
Dropbox Cofounder & CTO Arash Ferdowsi responds to Gruber 
Anonymous releases 10,365 e-mails from the days when PayPal was a startup 
GM Says Facebook Ads 
In Head-Hunting, Big Data University: Free Database And Hadoop Courses 
I made progress bars using only the machine code from the ROMs 
How I thought I wanted to become a morning person? 
"Most likely to succeed" YC W11 Gaming Startup, growing 10% a day, Hiring Hackers 
````