<?

// Host name
$dbhost="10.6.166.63";
// Mysql username
$dbuser="sns01";
// Mysql password
$dbpass="kc64JPch";
// Database name
$dbname="sns01";

function makeConnection() {
  // Connect to server and select databse.
  $con = mysql_connect("$GLOBALS[dbhost]", "$GLOBALS[dbuser]", "$GLOBALS[dbpass]") or die("Failed to  connect to DB!");
  mysql_select_db("$GLOBALS[dbname]",$con) or die("Failed to select DB!");
  return $con;
}

$con = makeConnection();

