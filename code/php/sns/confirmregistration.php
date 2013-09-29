<?php
 require 'connect.php';
 require 'verifyinfo.php';


/* 
 * add new user to database
 */
function addUser($fName, $lName,
	 	 $Email, 
		 $Password,
		 $gender, 
		 $year, $month, $day) {
  $verify = verifyData($fName, $lName,$Email,$Password,$gender,$year,$month,$day);
  if($verify != "") {
    return $verify;
  }
  if(isUserID($UarkEmail) == TRUE) {
     removeUser($UarkEmail);
  }
  $sql="INSERT INTO Users (FirstName,LastName,Email,Password,Gender,Year, Month, Day) VALUES   ('$fName','$lName','$Email','$Password','$gender','$year','$month','$day')";
  $result=mysql_query($sql);

}

$Email = $_REQUEST["Email"];
$Email2 = $_REQUEST["Email2"];
$Password = $_REQUEST["Password"];
$Password2 = $_REQUEST["Password2"];
$gender = $_REQUEST["gender"];

 if($Email != $Email2) {
   $msg = "<br />Emails did not match!";
   header( 'Location: register.php?error_msg='.$msg) ;
 } else if($Password != $Password2) {
   $msg = "<br />Passwords did not match!";
   header( 'Location: register.php?error_msg='.$msg) ;
 } else {
   // small hack
   if($gender != "Male" && $gender != "Female") {
     $gender = "Male";
   }
   $msg = addUser($_REQUEST["fName"],$_REQUEST["lName"],
	 	 $Email, 
		 $Password,
		 $gender, 
		 $_REQUEST["dob_year"], $_REQUEST["dob_month"], $_REQUEST["dob_day"]);
   if($msg != "") { 
     $msg = "<br />".$msg;
     header( 'Location: register.php?error_msg='.$msg) ;
   } else {
     $msg = "Thank you for Registering<br />";
     header( 'Location: login.php?error_msg='.$msg) ;
   }
 }











?>
