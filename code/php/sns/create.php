<?php

require 'connect.php';

function initDB() {
  $sql = "CREATE TABLE Users
  (
    FirstName varchar(15),
    LastName varchar(15),
    Password varchar(15),
    Email varchar(45),
    Gender varchar(6),
    Year int,
    Month int,
    Day int
  )";
  $result=mysql_query($sql);
}

initDB();

?>


