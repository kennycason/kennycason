---
title: Base N to 10 Conversion Class - PHP (Base 62 Implementation)
author: Kenny Cason
tags: alphanumeric, base 10, base 62, base n, convert, php, url shortener
---

This class can be used convert a base N number into base 10, and back. (Which makes it ideal for usage in technologies such as URL shorteners.)

```{.php .numberLines startFrom="1"}
<?php
/**
 * BNID - Base N <=> 10 converter
 *
 * @author kenny cason
 * @site www.ken-soft.com
 */
class BNID {

        // Alphabet of Base N (default is a Base 62 Implementation)
        var $bN = array(
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
        );

        var $baseN;
        /**
         * pass in your own alphabet of any base following above examples
         *  base should be greater than one
         * @param array $alphabet
         */
        function __construct($alphabet=null) {
            if($alphabet) {
                $this->bN = $alphabet;
            }
            $this->baseN = count($this->bN);
        }

        // convert base 10 to base N
        function base10ToN($b10num=0) {
            $bNnum = "";
            do {
                $bNnum = $this->bN[$b10num % $this->baseN] . $bNnum;
                $b10num /= $this->baseN;
            } while($b10num >= 1);     
            return $bNnum;
        }

        // convert base N to base 10
        function baseNTo10($bNnum = "") {
           $b10num = 0;
            $len = strlen($bNnum);
            for($i = 0; $i < $len; $i++) {
                $val = array_keys($this->bN, substr($bNnum, $i, 1));
                $b10num += $val[0] * pow($this->baseN, $len - $i - 1);
            }
            return $b10num;
        }

}
/*
// uncomment and call this script to demonstrate it's functionality

//$customBase = Array('0','1','2','3','4');  // base 4
//$customBase = array(
//           '0','1','2','3','4','5','6','7','8','9',
//            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
//            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
//	    '-','+','_','%','|','@','!','$','*','~','`','#','(',')','=','&','[',']','{','}','<','>',':',';'
//        ); // Alphabet of Base 86 
//$conv = new BNID($customBase); // initialize with custom base alphabet 
$conv = new BNID(); // default base 62 implementation

$max = pow($conv->baseN, 2);
for($i = 0; $i <= $max; $i++) {
    echo $conv->base10ToN($i).", ";
}
echo "<br/><br/>";
for($i = 0; $i <= $max; $i++) {
    $x = $conv->base10ToN($i);
    echo $conv->baseNTo10($x).", ";
}
*/
?>
```

If you want to use this as a URL shortener, below is a quick demo of how to use URL Rewrite to accept www.page.com/<BaseNID> and forward it to some page to process the ID (i.e. convert to base 10 ID and query a URL from the Database using the ID). The below implementation will work for a base 62 alphabet comprised of [0-9][A-Z][a-z], which is the demo I posted.
place the below text in <b>.htaccess</b> in your websites root directory<br/>

<pre>Options +FollowSymlinks
RewriteEngine on
RewriteBase /yourrootdirectory/
RewriteRule ^(([A-Z]*[a-z]*[0-9]*)*)$ main.php?b62id=$1 [L,QSA]
</pre>

So it should take a domain  www.abc.com, if you specify <b>www.abc.com/zA4F</b>, it would forward to <b>www.abc.com/main.php?id=14576711</b>
<!--
<b>Here is a sample Demo</b>
<a href="/code/php/baseconvert/AABCz23" target="blank">/code/php/baseconvert/AABCz23</a>
-->