<?php

  require 'connect.php';


function checkUserID($id, $password) {
  $sql="SELECT * FROM Users WHERE
  Email='$id' and Password='$password'";
  $result=mysql_query($sql);
  $count=mysql_num_rows($result);
  if($count==1) {
    return TRUE;
  }
  return FALSE;
}


function login($Email,$Password){
 if($Email != NULL && $Password != NULL) {
    if(checkUserID($Email, $Password)) {
      session_register($Email);
      header('location: home.php?Email='.$Email);
    } else {
     $msg = 'Login information incorrect!';
     header( 'Location: login.php?error_msg='.$msg) ;
    }
  } else {
     $msg = 'Login information incorrect!';
     header( 'Location: login.php?error_msg='.$msg) ;
  }
}


$Email = $_REQUEST["Email"];
$Password = $_REQUEST["Password"];

login($Email,$Password);

?>

