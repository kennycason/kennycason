---
title: Facebook - Find Who Deleted/Added You
author: Kenny Cason
tags: Detecting Deleted Friends, Facebook
---

Being a Facebook Junky I spent a bit of time trying to find out how to find out who deleted or added me. These are the general steps I go through (I have not bothered to make a script to automate this yet). Keep in mind you must have a bit of computer knowledge to do this. All my commands are geared towards Unix/Mac users, sorry Windows :)

<h3>1. Setup</h3>
Create A folder called "fb" somewhere, this is where we will store our lists of Facebook friends for later comparison. 

<h3>2. Get List of current Facebook Friends</h3>

<strong>a. </strong>Go to this URL (This is the Facebook Graph API's home page) 
<a href="http://developers.facebook.com/docs/reference/api/">http://developers.facebook.com/docs/reference/api/</a>

<strong>b. </strong>Find the link prefaced with "Friends: " and click it, this will display a list of your friends in JSON format. 
i.e.

```javascript
{
   "data": [
      {
         "name": "Person A",
         "id": "111111"
      },
      {
         "name": "Person B",
         "id": "222222"
      },
      {
         "name": "Person C",
         "id": "333333"
      }
   ]
}

```
<strong>c. </strong>Then Copy the contents into a file named "FB_Friends_2011_8_4" (or whatever suites you, but at least put the current date in the file name to make it easy to differentiate from other files)
<strong>d. </strong>You probably noticed there is a lot of fluff in this file, so now lets clean it up and get rid of everything except the name entries. Do do this run the below commands in the terminal (be sure to CD to the directory).
<code>
cat FB_Friends_2011_8_4 | grep "name" > tmp.txt
mv tmp.txt FB_Friends_2011_8_4
</code>
Your file now should look like:

```javascript
         "name": "Person A",
         "name": "Person B",
         "name": "Person C",

```
Which makes it a bit easier to process later :)

<h3>3. Detecting who deleted/added you</h3>
<strong>a. </strong>You must wait until you have noticed a change in your friends numbers or for some other amount of time. I typically do this about once a month. 
<strong>b. </strong>After waiting for a change in your friend's list, repeat step 2 from above but name the file based on the current date of which you are sampling. i.e. if I do it on September 4th 2011, name the file FB_Friends_2011_9_4
<strong>c. </strong>Finally we will use a program called "diff" to compare the two files and echo out the results. Using our example dates previously mentioned I would run the below command:
<code>diff FB_Friends_2011_8_4 FB_Friends_2011_9_4</code>
<strong>d. </strong>Understanding the output
<strong>lines with: </strong>
<code> <       "name" : "Person A"</code> 
Mean that "Person A" is no longer your friend, i.e. you deleted them, or they deleted you, or they deactivated their Facebook account.
<strong>lines with: </strong>
<code> >       "name" : "Person A"</code> 
Mean that "Person A" is a new friend
<strong>lines with: </strong>
<code> <       "name" : "Person A"
---
>       "name" : "Person AA"
</code> Mean that "Person A" changed his/her name to "Person AA"
Facebook


It's a lot of steps, but it becomes very easy after you've done it a couple times :) 