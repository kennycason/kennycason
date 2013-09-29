---
title: PHP - Binary Bayesian Filter
author: Kenny Cason
tags: Bayesian Filter, classfication, nlp, PHP, Spam
---

This is a simple Binary Bayesian Filter. The reason for the Interfaces and Abstract classes is because I am still mid-ways through my Java to PHP port :)

Also notice that this model calculates <code>P(A|B)</code> by assigning probability values rather than frequency counts. <code>P(A|B) = P(A & B) / P(B)</code>

Sample Usage

```php
$neg = File::read(LIB_DIR . 'classify/bayes/data/NEG'); // my file loading methods, you can use your own
$pos = File::read(LIB_DIR . 'classify/bayes/data/POS');

$bayes = new PositiveNegativeBayesianFilter();
$bayes->trainBad($neg);
$bayes->trainGood($pos); 
$bayes->finalizeTraining();

$reviews = array(
    'I hate this stupid website',
    'I love life, life is awesomme',
    'blah blah blah'
);

// live sentiment analysis
for($i = 0; $i < count($reviews); $i++) {
    $score = $bayes->analyze($reviews[$i]);
    echo $reviews[$i] . ' = ' . $score . '<br/>';
}

```

PositiveNegativeBayesianFilter.php

```php
<?php

require_once("AbstractBayesianBinaryFilter.php");

/**
 *
 * @author Kenny
 */
class PositiveNegativeBayesianFilter extends AbstractBayesianBinaryFilter {

    // How to split the String into tokens
    private $splitregex;

    public function __construct() {
        $this->splitregex = "/\\w+/i";
    }

    public function parseTokens($content) {
        $matches = array();
        preg_match_all($this->splitregex, $content, $matches);
        return $matches[0];
    }

    public function setSplitRegex($splitregex) {
        $this->splitregex = $splitregex;
    }

    public function trainBad($content) {
        $tokens = $this->parseTokens($content);
        $spamTotal = 0;

        // For every word token
        // d($tokens);
        for ($i = 0; $i < count($tokens); $i++) {
            $word = strtolower($tokens[$i]);
            $word = preg_replace("/[^A-Za-z0-9\s\s+]/", "", $word);
            if ($word != "") {

                $spamTotal++;
                // If it exists in the HashMap already
                // Increment the count
                if (array_key_exists($word, $this->words)) {
                    $this->words[$word]->countBad();
                } else {  // Otherwise it's a new word so add it
                    $w = new Word($word);
                    $w->countBad();
                    $this->words[$word] = $w;
                }
            }
        }
        // Go through all the words and divide
        // by total words
        foreach ($this->words as $k => $w) {
            $w->calcBadProb($spamTotal);
        }
    }

    public function trainGood($content) {
        $tokens = $this->parseTokens($content);
        $goodTotal = 0;

        // For every word token
        for ($i = 0; $i < count($tokens); $i++) {
            $word = strtolower($tokens[$i]);
            $word = preg_replace("/[^A-Za-z0-9\s\s+]/", "", $word);
            if ($word != "") {
                $goodTotal++;
                // If it exists in the HashMap already
                // Increment the count
                if (array_key_exists($word, $this->words)) {
                    $this->words[$word]->countGood();
                } else {  // Otherwise it's a new word so add it
                    $w = new Word($word);
                    $w->countGood();
                    $this->words[$word] = $w;
                }
            }
        }
        // Go through all the words and divide
        // by total words
        foreach ($this->words as $k => $w) {
            $w->calcGoodProb($goodTotal, 2.0);
        }
    }

    /**
     * This method is derived from Paul Graham:
     * http://www.paulgraham.com/spam.html
     */
    public function analyze($content) {

        $tokens = $this->parseTokens($content);
        $interesting = $this->getInterestingWords($tokens, 15);

        // Apply Bayes' rule (via Graham)
        $pposproduct = 1.0;
        $pnegproduct = 1.0;
        // For every word, multiply Spam probabilities ("Pneg") together
        // (As well as 1 - Pneg)
        for ($i = 0; $i < count($interesting); $i++) {
            $pposproduct *= $interesting[$i]->getPNegative();
            $pnegproduct *= (1.0 - $interesting[$i]->getPNegative());
        }

        // Apply formula
        $pPos = $pnegproduct / ($pposproduct + $pnegproduct);
        return $pPos;
    }

    /**
     * Create an arraylist of <limit> most "interesting" words
     * Words are most interesting based on how different their BAD
     * probability is from 0.5
     * @param content
     * @param size
     * @return
     */
    private function getInterestingWords($tokens, $limit) {
        $interesting = array();

        // For every word in the String to be analyzed
        for ($i = 0; $i < count($tokens); $i++) {
            $word = strtolower($tokens[$i]);
            $word = preg_replace("/[^A-Za-z0-9\s\s+]/", "", $word);
            if ($word != "") {
                // d($word);
                $w;
                // If the String is in our HashMap get the word out
                if (array_key_exists($word, $this->words)) {
                    $w = $this->words[$word];
                    // Otherwise, make a new word with a Bad probability of 0.5;
                } else {
                    $w = new Word($word);
                    $w->setPNegative(0.4);
                }

                // If this list is empty, then add this word in!
                if (count($interesting) == 0) {
                    $interesting[] = $w;
                    // Otherwise, add it in sorted order by interesting level
                } else {
                    for ($j = 0; $j < count($interesting); $j++) {
                        // For every word in the list already
                        $nw = $interesting[$j];
                        // If it's the same word, don't bother
                        if ($w->getWord() == $interesting[$j]->getWord()) {
                            break;
                            // If it's more interesting stick it in the list
                        } else if ($w->interesting() > $interesting[$j]->interesting()) {
                            ArrayUtils::insertAt($j, $w, $interesting);
                            break;
                            // If we get to the end, just tack it on there
                        } else if ($j == count($interesting) - 1) {
                            $interesting[] = $w;
                        }
                    }
                }

                // If the list is bigger than the limit, delete entries
                // at the end (the more "interesting" ones are at the
                // start of the list
                while (count($interesting) > $limit) {
                    array_pop($interesting);
                }
            }
        }
//        for ($j = 0; $j < count($interesting); $j++) {
//            d('word: ' . $interesting[$j]->getWord() . ' pBad: ' . $interesting[$j]->getPBad()  . ' pGood: ' . $interesting[$j]->getPGood());
//        }
        return $interesting;
    }

}

```


AbstractBayesianBinaryFilter.php

```php
<?php

require_once("IBayesianBinaryFilter.php");
require_once("Word.php");
/**
 * Description of AbstractBayesianBinaryFilter
 *
 * @author Kenny
 */
abstract class AbstractBayesianBinaryFilter implements IBayesianBinaryFilter {
   
    // A Hashmap maping string to Word
    protected $words = array();

    public function displayStats() {
        foreach($this->words as $k => $word) {
            if($word != null) {
                echo $k . " " . $word->getNegative();
            }
        }
    }

    public function  finalizeTraining() {
        foreach($this->words as $k => $word) {
            $word->finalizeProb();
           // echo 'word: ' .  $word->getWord() . ' pNeg: ' .  $word->getPBad() . ', pGood: ' .  $word->getPGood() . '<br/>';
        }
    }

}

```

IBayesianBinaryFilter.php

```php
<?php

require_once("IBayesianFilter.php");
/**
 *
 * @author destructo
 */
interface IBayesianBinaryFilter extends IBayesianFilter {

    function trainBad($content);

    function trainGood($content);

    function parseTokens($content);
}

```

IBayesianFilter.php

```php
<?php

/**
 *
 * @author Kenny
 */
interface IBayesianFilter {

    function finalizeTraining();

    function analyze($content);

    function displayStats();
    
}

```

IBayesianFilter.php

```php
<?php

/**
 *
 * @author Kenny
 */
interface IBayesianFilter {

    function finalizeTraining();

    function analyze($content);

    function displayStats();
    
}

```

Word.php

```php
<?php

/**
 * Description of Word
 *
 * @author Kenny
 */
class Word {

    private $word; // The String itself
    private $countBad; // The total times it appears in "bad" messages
    private $countGood; // The total times it appears in "good" messages
    private $rBad; // bad count / total bad words
    private $rGood; // good count / total good words
    private $pNeg; // probability this word is negative
    private $pGood; // probability this word is positive

    // Create a word, initialize all vars to 0

    public function __construct($word) {
        $this->word = $word;
        $this->countBad = 0;
        $this->countGood = 0;
        $this->rBad = 0.0;
        $this->rGood = 0.0;
        $this->pNeg = 0.0;
        $this->pGood = 0.0;
    }

    // Increment bad counter
    public function countBad() {
        $this->countBad++;
    }

    // Increment good counter
    public function countGood() {
        $this->countGood++;
    }

    // Computer how often this word is bad
    public function calcBadProb($total, $biasBad = 1.0) {
        if ($total > 0) {
            $this->rBad = $biasBad * $this->countBad / $total;
        }
    }

    public function calcGoodProb($total, $biasGood = 1.0) {
        if ($total > 0) {
            $this->rGood = $biasGood * $this->countGood / $total;
        }
    }

    // Implement bayes rules to computer how likely this word is "negative"
    public function finalizeProb() {
        if ($this->rGood + $this->rBad > 0) {
            $this->pNeg = $this->rBad / ($this->rBad + $this->rGood);
            $this->pGood = $this->rGood / ($this->rBad + $this->rGood);
        }
        if ($this->pNeg < 0.01) {
            $this->pNeg = 0.01;
        } else if ($this->pNeg > 0.99) {
            $this->pNeg = 0.99;
        }
        if ($this->pGood < 0.01) {
            $this->pGood = 0.01;
        } else if ($this->pGood > 0.99) {
            $this->pGood = 0.99;
        }
        // echo 'word: ' . $this->word . ' pNeg: ' . $this->pNeg . ', pGood: ' . $this->pGood . '<br/>';
    }

    // The "interesting" rating for a word is
    // How different from 0.5 it is
    public function interesting() {
        return abs(0.5 - $this->pNeg);
    }

    public function getPGood() {
        return $this->rGood;
    }

    public function getPBad() {
        return $this->rBad;
    }

    public function getPNegative() {
        return $this->pNeg;
    }

    public function setPNegative($f) {
        $this->pNeg = $f;
    }

    public function getPPositive() {
        return $this->pGood;
    }

    public function setPPositive($f) {
        $this->pGood = $f;
    }

    public function getWord() {
        return $this->word;
    }

}

```