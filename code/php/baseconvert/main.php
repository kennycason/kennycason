<?php
$root = $_SERVER['DOCUMENT_ROOT'] . "/code/php/baseconvert/";
require_once($root."BNID.php");

$bNid = new BNID();

$id_N = $_GET["id"];
$id = $bNid->baseNTo10($id_N);
if($id) {
echo "ID: ".$id;
} else {
  echo "You did not try a valid Addresss, try <a href='http://www.ken-soft.com/code/php/basen/ABCz23'>http://www.ken-soft.com/code/php/basen/ABCz23</a>";

}
