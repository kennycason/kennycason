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
<a  href='index.php?lang=en' title='English'><img  src='http://www.Ken-Soft.com/code/php/langsupport/languages/flag_english.gif' alt='English'\></a>
<a href='index.php?lang=jp' title='Japanese'><img src='http://www.Ken-Soft.com/code/php/langsupport/languages/flag_japan.gif' alt='Japanese'\></a></li>
<a  href='index.php?lang=ko' title='Korean'><img  src='http://www.Ken-Soft.com/code/php/langsupport/languages/flag_korean.gif' alt='Korean'\></a>
<a href='index.php?lang=zh' title='Chinese'><img src='http://www.Ken-Soft.com/code/php/langsupport/languages/flag_chinese.gif' alt='Chinese'\></a></li>
</div>	
<?php print $txt['txt_first_name']; ?> <br />
<?php print $txt['txt_last_name']; ?> <br />
<?php print $txt['txt_phone_number']; ?><br />
</body>
