---
title: Send SMS Messages to Cell Phone
author: Kenny Cason
tags: php
---

While sending SMS or MMS messages from your computer is not by any means a new technology, this is the first time that I have devoted the time to it. It was definitely a fun experience. Again, I want to emphasize that I know this is not a new technology, but I feel that the structure of American cell phone E-mails is very prone to spam.

The idea came to me when I was in Japan last year. Phones in Japan have two primary methods for sending messages. C-mail (Cメール) and phone E-mail (携帯メール). Japanese phones can send messages that basically function like E-mail. It wasn't until then that it actually dawned on me that American phones must also have some sort of unique address, i.e. an E-mail address. Then a few Google searches away I found a nice site that lists a method for obtaining each phones unique email address.

I expanded on the list by adding Japanese cell phone information as well. The List can be found at the bottom of this post. I will probably continually update this list in the future.

So what is so special about this? The fact that you can text message to virtually any phone in the world, may be very interesting to most people.  There are many exceptions of course, i.e. a that user can't receive text messages, or is blocking external mail. But there is an interesting difference between Japanese and American phone email addresses. For example, in Japan, a user of AU by KDDI　(<a href="http://www.au.kddi.com/" target="_blank">株式会社エーユー</a>), may have an email address like: <code>sushigadaisuki@ezweb.ne.jp</code>
This is because the phone's emails are changeable by the user. This is much harder for Spammers to guess. In many cases information collecting companies are required to build up large databases of peoples information in order to spam the masses. However, in America, where the prefix of the email is always comprised of a 10-digit phone number, A spammer could easily use this information to "Target" an area to spam or  if he really wanted to cause mischief, potentially email any cell phone capable of receiving messages in the U.S., and other countries complying to similar E-mail formatting standards.

To compare the two methods numerically first lest look at an email comprised of 10 numbers. There are 10^10 = 10000000000 different combinations. A sample 10 digit email comprised of characters(a-z), numbers(0-9), and special characters(._-), thats (26+10+3)^10 = 39^10 = 8140406085191601. That is significantly larger, thus harder to systematically guess every unique email address.
(Note: the list of special characters may not be complete, also, a Japanese email could be longer or shorter than 10 digits)

For example, we know that all phones beginning with 479 fall into Northwest Arkansas. That leaves only 7 digits of possible combinations. 10^7 (10000000, which is much less than 10^10). However, this is still a relatively large number. You can call mail() in php 10^7 times. To remove some wasteful calls of the mail(), you can look at the next 3 digits and find commonly used numbers in the area like 422 or others depending on where you are located. Then you only have 4 digits to iterate over (10^4 possibilities). This is much more feasible, and depending on what the intentions of the sender are, maybe even more useful as the messages are now target a region.

Now we know that we can send emails to specific phone emails, but how do you do it? It's also very simple. Again, another quick search on Google will yield many results for how to do it using PHP or various other methods. I prefer <s>PHP</s>Kotlin. Just stick the below code in a file called something.php and run it over the server. With a few small modifications this can be implemented to send messages one at a time, bulk, or even iteratively send messages to a large number of cell phones. I would not encourage anyone to abuse this. Just some food for thought.

```php
<?php
  $to = "479XXXXXXX@txt.att.net";
  $subject = "Spam";
  $body = "This is Spam!!";
  mail($to, $subject, $body);
?>
```


<!-- Here is a simple implementation that created. you can send messages to standard emails like GMail, and phones provided you know the phone number and carrier.
<a href="/code/sms/sendsms.php">Send SMS using PHP</a>  -->

I'm fairly certain a phone company and users alike would not appreciate this very much. Perhaps before this begins to be more and more exploited maybe phone companies should do more to allow users to have a more secure and unique phone E-mail address. Much like the phones of Japan. Then if you start getting more spam than you'd like, you can easily change your phone's E-mail, without having to change your phone number. ;)
Example usage: Your friend uses <b>AT&T</b> and his phone number is <b>1234567890</b>.
The resulting email to send a SMS: <b>1234567890@txt.att.net</b>
and to send a MMS: <b>1234567890@mms.att.net</b>
Before anyone decides to go on a spamming spree I recommend consulting laws governing the usage of messaging and SPAM.<a href="http://www.fcc.gov/cgb/consumerfacts/canspam.html" target="_blank">FCC.GOV</a>
<a href='http://networking.ringofsaturn.com/Telecommunications/mobile-phone-emails.php' target="_blank">Expanded List of Phone and Pager Email Formats</a><table border="1"  align="left"><tr ><td>American Provider</td><td>Email Extension</td></tr><tr><td>AT&amp;T (Cingular)</td><td>@txt.att.net</td></tr><tr><td>Boost Mobile</td><td>@myboostmobile.com </td></tr><tr><td>Virgin Mobile USA</td><td>@vmobl.com</td></tr>
<tr><td>Verizon</td><td>@vtext.com</td></tr><tr><td>Alltel</td><td>@message.alltel.com</td></tr>
<tr><td>Sprint PCS</td><td>@messaging.sprintpcs.com</td></tr><tr><td>T-Mobile</td><td>@tmomail.net</td></tr><tr><td>Nextel</td><td>@messaging.nextel.com</td></tr><tr ><td>Japanese Provider</td><td>Email Extension</td></tr><tr><td>AU KDDI</td><td>@ezweb.ne.jp</td></tr><tr><td>NTT Docomo</td><td>@docomo.ne.jp</td></tr>
<tr><td>Softbank</td><td>@softbank.ne.jp</td></tr></table>
