<?php

  $Email = $_REQUEST["Email"];

  session_start();
  if(!session_is_registered($Email)) {
     $msg = 'Must be Logged in!';
     header( 'Location: login.php?error_msg='.$msg) ;
  } 


 echo "
   <html>
     Home Page<br />
     Email: {$Email}<br />
	<form action='img.upload.php'>
	  <div><button type='submit' id='submit'>Upload Profile Picture</button></div>
	</form>
	<form action='logout.php'>
	  <div><button type='submit' id='submit'>Logout</button></div>
	</form>
   </html>"

?>
