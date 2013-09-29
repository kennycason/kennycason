<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01//EN'
            'http://www.w3.org/TR/html4/strict.dtd'>

<?php
// require 'imageverification.php';
 $error_msg = $_REQUEST['error_msg'];

echo "
<html lang='en'>
<head>
	<title>SNS Login</title>
	<meta http-equiv='content-type' content='text/html; charset=utf-8'>
	<meta name='author' content='kenny cason'>
	<meta name='keywords' content=''>
	<meta name='description' content=''>
	<link rel='stylesheet' type='text/css' href='css/layout.css'>
</head>
<body>
	<h2>SNS Login</h2>
	<form action='verifylogin.php'>
		<p><req>{$error_msg}</req> </p>
		<fieldset class='information'>
			<legend>Email</legend>
			<div>
				<label for='Email'>Email</label> <input type='text' id='Email' name='Email'>
			</div>
			<div>
				<label for='Password'>Password</label> <input type='password' id='Password' name='Password'>
			</div>
		</fieldset>
		<div><button type='submit' id='submit'>Login</button></div>
	</form>
</body></html>";


?>

