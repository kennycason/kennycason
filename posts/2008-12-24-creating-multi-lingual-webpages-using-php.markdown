---
title: Creating Multi-lingual Webpages using PHP
author: Kenny Cason
tags: php
---

For those who have ever tried to design a multilingual web page only to end up with 5 different index pages to represent your 5 different languages, this article may be of interest.

Here is the demo that we are going to create:
<a href="/code/php/langsupport/index.php" target="_blank">Multilingual Page in PHP Example</a>

First create your main page: i.e. index.php


```php
<?php
  $language = $_REQUEST['lang'];
  if($language == 'en') {
    require 'languages/language.en.php';  
  } else if($language == 'jp') {
    require 'languages/language.jp.php';
  } else if($language == 'ko') {
    require 'languages/language.ko.php';
  } else if($language == 'zh') {
    require 'languages/language.zh.php';
  } else {
    require 'languages/language.en.php';  
  }
?>	 
<html>
<head>
  <title><?php print $txt['txt_lang']; ?></title>
</head>
<meta http-equiv='Content-Type' content='text/html; charset=<?php print $txt['txt_charset']; ?>'>
<style type='text/css'>
body{
padding:0;
background-color: #EEEEEE;
}
</style>

<body style='color:gray'>
<div class='nav0'>
<a  href='index.php?lang=en' title='English'><img  src='/code/php/langsupport/languages/flag_english.gif' alt='English'\></a>
<a href='index.php?lang=jp' title='Japanese'><img src='/code/php/langsupport/languages/flag_japan.gif' alt='Japanese'\></a></li>
<a  href='index.php?lang=ko' title='Korean'><img  src='/code/php/langsupport/languages/flag_korean.gif' alt='Korean'\></a>
<a href='index.php?lang=zh' title='Chinese'><img src='/code/php/langsupport/languages/flag_chinese.gif' alt='Chinese'\></a></li>
</div>  
<?php print $txt['txt_first_name']; ?> <br />
<?php print $txt['txt_last_name']; ?> <br />
<?php print $txt['txt_phone_number']; ?><br />
</body>


```

Notice the lines at the top.

```php
$language = $_REQUEST['lang'];
```

If you have ever looked at the address bar when a php is loading you may have noticed something like www.something.com/index.php?lang=en
The $_REQUEST['lang']; call would return 'en'. This can be used for use inside your PHP script. You can also use cookies or information from a user model to store the language. In the case of our index.php file, the 'lang' variable is used to determine what language file to include, and in the case that 'lang' is not defined, the English language file is included by default.
Next, notice how when the pages html is 'echoed' to the page every instance where text is displayed, it refers to the hash array $txt
for example:

```php
<?php print $txt['txt_first_name']; ?>
```

This looks up the hash called 'txt_first_name' and writes the value.  Note: As well as using a hash array using Defines also works well.
Here are the sample language files used in the example.
language.en.php

```php
<?php
$txt = array (
  'txt_charset'                 => 'iso-8859-1',
  'txt_lang'                    => 'Language',
  'txt_first_name'              => 'First Name',
  'txt_last_name'               => 'Last Name',
  'txt_phone_number'            => 'Phone Number'
);
?>
```

language.jp.php

```php
<?php
$txt = array (
  'txt_charset'                 => 'utf-8',
  'txt_lang'                    => '言語',
  'txt_first_name'              => '名',
  'txt_last_name'               => '姓',
  'txt_phone_number'            => '電話番号'
);
?>
```

language.zh.php

```php
<?php
$txt = array (
  'txt_charset'                 => 'utf-8',
  'txt_lang'                    => '语言',
  'txt_first_name'              => '名字',
  'txt_last_name'               => '姓',
  'txt_phone_number'            => '电话号码'
);
?>
```

language.ko.php

```php
<?php
$txt = array (
  'txt_charset'                 => 'utf-8',
  'txt_lang'                    => '언어 ',
  'txt_first_name'              => '이름',
  'txt_last_name'               => '성',
  'txt_phone_number'            => '전화 번호'
);
?>
```

<b>Scaling further</b><br/>

Because manually managing a language file can be very tedious it is not uncommon to build an interface to manage this values in a database and use an export feature to programmatically export these languague files.

Dealing with place holders. It's common for text to be of the form "Welcome Back, ${NAME}" or "{$NAME} liked ${NAME2}'s post.". To support these we need to add support for key/value replacement in our language printing function.  
