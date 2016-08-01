<?php
/**
 * BNID - Base N <=> 10 converter
 *
 * @author kenny cason
 * @site www.kennycason.com
 */
class BNID {
        // Alphabet of Base N (This is a Base 62 Implementation)
        var $bN = array(
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
        );
        
        // Alphabet of Base 86 (comment out the B62 array and comment this to try it out)
       /*var $bN = array(
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
	    '-','+','_','%','|','@','!','$','*','~','`','#','(',')','=','&','[',']','{','}','<','>',':',';'
        );*/

        var $baseN;

        function __construct() {
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

// test
$conv = new BNID();
$max = pow($conv->baseN, 2);
for($i = 0; $i <= $max; $i++) {
    echo $conv->base10ToN($i).", ";

}
echo "<br/><br/>";
for($i = 0; $i <= $max; $i++) {
    $x = $conv->base10ToN($i);
     echo $conv->baseNTo10($x).", ";
}

?>