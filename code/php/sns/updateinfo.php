<?php


/** 
 * setter methods
 */
function setUserFirstName($Email,$FirstName) {
  $sql = "UPDATE Users SET FirstName = '$FirstName' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}

function setUserLastName($Email,$LastName) {
  $sql = "UPDATE Users SET LastName = '$LastName' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}

function setUserEmail($Email,$NewEmail) {
  $sql = "UPDATE Users SET Email = '$Email' WHERE Email = '$NewEmail'";
  $result=mysql_query($sql);
}

function setUserPassword($Email,$Password) {
  $sql = "UPDATE Users SET Password = '$Password' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}


function setUserGender($Email,$Gender) {
  $sql = "UPDATE Users SET Gender = '$Gender' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}

function setUserBirthYear($Email,$Year) {
  $sql = "UPDATE Users SET Year = '$Year' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}

function setUserBirthMonth($Email,$Month) {
  $sql = "UPDATE Users SET Month = '$Month' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}

function setUserBirthDay($Email,$Day) {
  $sql = "UPDATE Users SET Day = '$Day' WHERE Email = '$Email'";
  $result=mysql_query($sql);
}




?>
