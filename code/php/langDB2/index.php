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
	<a href="index.php?lang=en"><?php echo $obj_lang->getText('LANG_EN', $lang); ?></a>|<a href="index.php?lang=jp"><?php echo $obj_lang->getText('LANG_JP', $lang); ?></a>|<a href="index.php?lang=zh"><?php echo $obj_lang->getText('LANG_ZH', $lang); ?></a>
	<form>
		<?php echo $obj_lang->getText('FNAME', $lang); ?>:<input type="text" name="firstname" /><br />
		<?php echo $obj_lang->getText('LNAME', $lang); ?>:<input type="text" name="lastname" /><br />
		<select name="lang">
			<option value="en"><?php echo $obj_lang->getText('LANG_EN', $lang); ?></option>
			<option value="jp"><?php echo $obj_lang->getText('LANG_JP', $lang); ?></option>
			<option value="zh"><?php echo $obj_lang->getText('LANG_ZH', $lang); ?></option>
		</select><br />
		<input type="submit" value=<?php echo $obj_lang->getText('SUBMIT', $lang); ?> />
	</form> 
</body>
</html>
