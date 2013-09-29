<?php

// AUTHOR: KENNY CASON
// WEBSITE: WWW.KEN-SOFT.COM

class Language {
    
	public $defaultlang = "en";	// Default Language

	function Language($defaultlang="") {
		if($defaultlang != "") {
			$this->defaultlang = $defaultlang;
		}
	}

    	// get text from DB
	public function getText($textid, $lang="") {
		if($lang == "") {
			$lang = $this->defaultlang;
		}	
      		$query = "SELECT text FROM lang_$lang WHERE textid='$textid'";
      		$result = mysql_query($query);	
        	$row = mysql_fetch_array($result);
		$text = $row[0];
		if($lang == $this->defaultlang) {  // if language is default, return the langauge unchecked.
			return $text;
		} else {
			if($text != "") { // Is text field un-translated? (i.e. not empty string)
				return $text;
			} else { // If not, query using default language
				return $this->getText($textid, $this->defaultlang);
			}
		}	
    	}

    	// get and echo text from DB
	public function echoText($textid, $lang="") {
		echo $this->getText($textid, $lang);
    	}
}
?>

