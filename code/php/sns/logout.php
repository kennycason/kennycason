<?php
  session_start();
  session_destroy();
  $msg = 'Successfully logged out!';
  header( 'Location: login.php?error_msg='.$msg) ;
?>
