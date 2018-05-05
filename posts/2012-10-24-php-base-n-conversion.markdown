---
title: Base 2, 8, 16, 62, N Conversion - PHP
author: Kenny Cason
tags: php
---

A simple class that can easily be used to convert between Base10 and any other Base.

Base2.php

```php
<?php

class Base2 extends BaseN {

    public function  __construct() {
        parent::__construct(2, '01');
    }

}
```

Base8.php

```php
<?php

class Base8 extends BaseN {

    public function  __construct() {
        parent::__construct(8, '01234567');
    }

}

```

Base16.php

```php
<?php

class Base16 extends BaseN {

    public function  __construct() {
        parent::__construct(16, '0123456789ABCDEF');
    }

}
```
Base62.php

```php
<?php

class Base62 extends BaseN {

    public function  __construct() {
        parent::__construct(62, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
    }

}
```

BaseN.php

```php
<?php

abstract class BaseN {

    private $chars;

    private $base;

    protected function  __construct($base, $chars) {
        $this->base = $base;
        $this->chars = $chars;
        if(mb_strlen($chars) != $base) {
            error_log('base does not equal alphabet length');
        }

    }

    public function encode($val) {
        $val = (int) $val;
        // can't handle numbers larger than 2^31-1 = 2147483647
        $str = '';
        do {
            $i = $val % $this->base;
            $str = $this->chars[$i] . $str;
            $val = ($val - $i) / $this->base;
        } while ($val > 0);
        return $str;
    }

    public function decode($str) {
        $str = (string)$str;
        $len = mb_strlen($str);
        $val = 0;
        for ($i = 0; $i < $len; ++$i) {
            $val += mb_strpos($this->chars, mb_substr($str, $i, 1)) * pow($this->base, $len - $i - 1);
        }
        return $val;
    }

}
```

Usage Example:

```php
$base = new Base16();
for($i = 0; $i < 100; $i++) {
      $b16 = $base->encode($i);
      $b10 = $base->decode($b16);
      echo $i . ' => encode b16 => ' . $b16 . ' => decode b10 => ' . $b10 . '<br/>';
}

```

Output
<pre>
0 => encode b16 => 0 => decode b10 => 0
1 => encode b16 => 1 => decode b10 => 1
2 => encode b16 => 2 => decode b10 => 2
3 => encode b16 => 3 => decode b10 => 3
4 => encode b16 => 4 => decode b10 => 4
5 => encode b16 => 5 => decode b10 => 5
6 => encode b16 => 6 => decode b10 => 6
7 => encode b16 => 7 => decode b10 => 7
8 => encode b16 => 8 => decode b10 => 8
9 => encode b16 => 9 => decode b10 => 9
10 => encode b16 => A => decode b10 => 10
11 => encode b16 => B => decode b10 => 11
12 => encode b16 => C => decode b10 => 12
13 => encode b16 => D => decode b10 => 13
14 => encode b16 => E => decode b10 => 14
15 => encode b16 => F => decode b10 => 15
16 => encode b16 => 10 => decode b10 => 16
17 => encode b16 => 11 => decode b10 => 17
18 => encode b16 => 12 => decode b10 => 18
19 => encode b16 => 13 => decode b10 => 19
20 => encode b16 => 14 => decode b10 => 20
21 => encode b16 => 15 => decode b10 => 21
22 => encode b16 => 16 => decode b10 => 22
23 => encode b16 => 17 => decode b10 => 23
24 => encode b16 => 18 => decode b10 => 24
25 => encode b16 => 19 => decode b10 => 25
26 => encode b16 => 1A => decode b10 => 26
27 => encode b16 => 1B => decode b10 => 27
28 => encode b16 => 1C => decode b10 => 28
29 => encode b16 => 1D => decode b10 => 29
30 => encode b16 => 1E => decode b10 => 30
31 => encode b16 => 1F => decode b10 => 31
32 => encode b16 => 20 => decode b10 => 32
33 => encode b16 => 21 => decode b10 => 33
34 => encode b16 => 22 => decode b10 => 34
35 => encode b16 => 23 => decode b10 => 35
36 => encode b16 => 24 => decode b10 => 36
37 => encode b16 => 25 => decode b10 => 37
38 => encode b16 => 26 => decode b10 => 38
39 => encode b16 => 27 => decode b10 => 39
40 => encode b16 => 28 => decode b10 => 40
41 => encode b16 => 29 => decode b10 => 41
42 => encode b16 => 2A => decode b10 => 42
43 => encode b16 => 2B => decode b10 => 43
44 => encode b16 => 2C => decode b10 => 44
45 => encode b16 => 2D => decode b10 => 45
46 => encode b16 => 2E => decode b10 => 46
47 => encode b16 => 2F => decode b10 => 47
48 => encode b16 => 30 => decode b10 => 48
49 => encode b16 => 31 => decode b10 => 49
50 => encode b16 => 32 => decode b10 => 50
51 => encode b16 => 33 => decode b10 => 51
52 => encode b16 => 34 => decode b10 => 52
53 => encode b16 => 35 => decode b10 => 53
54 => encode b16 => 36 => decode b10 => 54
55 => encode b16 => 37 => decode b10 => 55
56 => encode b16 => 38 => decode b10 => 56
57 => encode b16 => 39 => decode b10 => 57
58 => encode b16 => 3A => decode b10 => 58
59 => encode b16 => 3B => decode b10 => 59
60 => encode b16 => 3C => decode b10 => 60
61 => encode b16 => 3D => decode b10 => 61
62 => encode b16 => 3E => decode b10 => 62
63 => encode b16 => 3F => decode b10 => 63
64 => encode b16 => 40 => decode b10 => 64
65 => encode b16 => 41 => decode b10 => 65
66 => encode b16 => 42 => decode b10 => 66
67 => encode b16 => 43 => decode b10 => 67
68 => encode b16 => 44 => decode b10 => 68
69 => encode b16 => 45 => decode b10 => 69
70 => encode b16 => 46 => decode b10 => 70
71 => encode b16 => 47 => decode b10 => 71
72 => encode b16 => 48 => decode b10 => 72
73 => encode b16 => 49 => decode b10 => 73
74 => encode b16 => 4A => decode b10 => 74
75 => encode b16 => 4B => decode b10 => 75
76 => encode b16 => 4C => decode b10 => 76
77 => encode b16 => 4D => decode b10 => 77
78 => encode b16 => 4E => decode b10 => 78
79 => encode b16 => 4F => decode b10 => 79
80 => encode b16 => 50 => decode b10 => 80
81 => encode b16 => 51 => decode b10 => 81
82 => encode b16 => 52 => decode b10 => 82
83 => encode b16 => 53 => decode b10 => 83
84 => encode b16 => 54 => decode b10 => 84
85 => encode b16 => 55 => decode b10 => 85
86 => encode b16 => 56 => decode b10 => 86
87 => encode b16 => 57 => decode b10 => 87
88 => encode b16 => 58 => decode b10 => 88
89 => encode b16 => 59 => decode b10 => 89
90 => encode b16 => 5A => decode b10 => 90
91 => encode b16 => 5B => decode b10 => 91
92 => encode b16 => 5C => decode b10 => 92
93 => encode b16 => 5D => decode b10 => 93
94 => encode b16 => 5E => decode b10 => 94
95 => encode b16 => 5F => decode b10 => 95
96 => encode b16 => 60 => decode b10 => 96
97 => encode b16 => 61 => decode b10 => 97
98 => encode b16 => 62 => decode b10 => 98
99 => encode b16 => 63 => decode b10 => 99
</pre>
