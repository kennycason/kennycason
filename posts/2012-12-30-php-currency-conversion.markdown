---
title: PHP - Currency Conversion
author: Kenny Cason
tags: currency conversion, PHP
---

I can't remember when I wrote this but I found this little snippet in my library and didn't want it to go to waste.

It's very simple and just uses Yahoo! Finance to get the quote. To use the Class simply use the below code:

```{.php .numberLines startFrom="1"}
  $currency = new Currency();
  print_r($currency->convert('USD', 'JPY', 100));

```
It should then echo out something like:

```{.php .numberLines startFrom="1"}
Array ( [currency] => USDJPY [rate] => 85.995 [date] => 12/28/2012 [time] => 5:55pm [value] => 85.995 )

```

I.e. 100 USD is now worth $85.995 in Japan, this is called 円高(endaka) in Japanese :)


Currency.php

```{.php .numberLines startFrom="1"}
<?php

class Currency {

    public function __construct() {

    }

    public function convert($from='USD', $to='GBP', $amount=null) {

        $url = 'http://finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1&s=' . $from . $to . '=X';
        $handle = @fopen($url, 'r');
        if ($handle) {
            $result = fgets($handle, 4096);
            fclose($handle);
        }
        $result = preg_replace('/"/', '', $result);
        $array = explode(',', $result);

        $ret = array('currency' => substr($array[0], 0, strlen($array[0]) - 2),
                    'rate' => $array[1],
                    'date' => $array[2],
                    'time' => $array[3]);
        if($amount != null) {
            $ret['value'] = $amount * ($array[1] / 100.0);
        }
        return $ret;
    }

}

//  $currency = new Currency();
//  print_r($currency->convert('USD', 'JPY', 100));


?>

```