<?php
include_once("language.php");

// connect to a MySql DB, usually have a seperate file, or DB class to do this.
$host = 'p50mysql285.secureserver.net';
$user = 'language';
$password = 'PaSa12Temp14';
$database = 'language';

$con = mysql_pconnect($host, $user, $password);
mysql_select_db($database,$con);
mysql_set_charset('utf8', $con);


// create a new Langauge Object
$obj_lang = new Language();

// ideally pull this from a users profile.
$lang = $_REQUEST["lang"]; 

?>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>
	<a href="index.php?lang=en"><? $obj_lang->echoText('LANG_EN', $lang); ?></a>|<a href="index.php?lang=jp"><? $obj_lang->echoText('LANG_JP', $lang); ?></a>|<a href="index.php?lang=zh"><? $obj_lang->echoText('LANG_ZH', $lang); ?></a>
	<form>
		<? $obj_lang->echoText('FNAME', $lang); ?>:<input type="text" name="firstname" /><br />
		<? $obj_lang->echoText('LNAME', $lang); ?>:<input type="text" name="lastname" /><br />
		<select name="lang">
			<option value="en"><? $obj_lang->echoText('LANG_EN', $lang); ?></option>
			<option value="jp"><? $obj_lang->echoText('LANG_JP', $lang); ?></option>
			<option value="zh"><? $obj_lang->echoText('LANG_ZH', $lang); ?></option>
		</select><br />
		<input type="submit" value=<? $obj_lang->echoText('SUBMIT', $lang); ?> />
	</form> 
</body>
</html>
