<?php

/**
 *  verify user data
 */
function verifyData($fName, $lName,
	 	    $Email, 
 	            $Password,
		    $gender,
                    $year, $month, $day) {
   $msg = "";
   $msg .= verifyFirstName($fName);
   $msg .= verifyLastName($lName);
   $msg .= verifyEmail($Email);
   $msg .= verifyPassword($Password);
   $msg .= verifyGender($gender);
   $msg .= verifyBirthYear($year);
   $msg .= verifyBirthMonth($month);
   $msg .= verifyBirthDay($day);
  return $msg;
}

/*
 * Verify Data functions
 */
function verifyFirstName($fName) {
  $msg = "";
  if(strlen($fName) < 2 || strlen($fName) > 15){ $msg = "<error>First Name must be from 2 - 15 characters.</error><br/>";};
  return $msg;
}

function verifyLastName($lName) {
  $msg = "";
  if(strlen($lName) < 2 || strlen($lName) > 15){ $msg = "<error>Last Name must be from 2 - 15 characters.</error><br/>";};
  return $msg;
}

function verifyEmail($Email) {
  $msg = "";
  if(strlen($Email) < 10 || strlen($Email) > 45){ $msg = "<error>Email must be between 10 - 45 characters.</error><br/>";};
  return $msg;
}

function verifyPassword($Password) {
  $msg = "";
  if(strlen($Password) < 8 || strlen($Password) > 45){ $msg = "<error>Password must be between 8 - 45 characters.</error><br/>";};
  return $msg;
}

function verifyBirthYear($year) {
  $msg = "";
  if($year < 0 || $year > date("Y")){ $msg .= "<error>Year must be between 0 - and current year</error><br/>";};
  return $msg;
}

function verifyBirthMonth($mon) {
  $msg = "";
  if($mon < 1 || $mon > 12){ $msg .= "<error>Month must be between 1 - 12</error><br/>";};
  return $msg;
}

function verifyBirthDay($day) {
  $msg = "";
  if($day < 1 || $day > 31){ $msg .= "<error>Day must be between 1 - 31</error><br/>";};
  return $msg;
}

function verifyGender($gender) {
  $msg = "";
  if($gender != "Male" && $gender != "Female"){ $msg .= "<error>Gender must be Male or Female.</error><br/>";};
  return $msg;
}


function isUserID($Email) {
  $sql="SELECT * FROM Users WHERE
  Email='$Email'";
  $result=mysql_query($sql);
  $count=mysql_num_rows($result);
  if($count >= 1) {
    return TRUE;
  }
  return FALSE;
}



?>
