---
title: PHP - Observer/Observable Design Pattern
author: Kenny Cason
tags: design pattern, observable, observer, php
---

This is a PHP implementation of the Java Observer/Observable classes.

Observable.php

```php
<?php

/**
 * Observable
 *
 * @author kenny
 */
class Observable {

	private $changed = false;

	private $observers = array();

	public function addObserver(Observer $o) {
		if($o == null) {
			throw new NullPointerException();
		}
		$contains = 0;
		foreach($this->observers as $observer) {
			if($o === $observer) {
				$contains = true;
				break;
			}
		}
		if(!$contains) {
			$this->observers[] = $o;
		}
	}

	public function deleteObserver(Observer $o) {
		for($i = 0; $i < count($this->observers); $i++) {
			if($this->observers[$i] == $o) {
				unset($this->observers[$i]);
			}
		}
		$observers = array();
		foreach($this->observers as $observer) {
			$observers[] = $observer;
		}
		$this->observers = $observers;
	}

	public function notifyObservers($object = null) {
		if(!$this->changed) {
			return;
			$this->clearChanged();
		}
		foreach($this->observers as $ob) {
			$ob->update($this, $object);
		}
	}

	public function deleteObservers() {
		$this->observers = array();
	}

	protected function setChanged() {
		$this->changed = true;
	}

	protected function clearChanged() {
		$this->changed = false;
	}

	public function hasChanged() {
		return $this->changed;
	}

	public function countObservers() {
		return count($this->observers);
	}

}
?>

```

Observer.php

```php
<?php

/**
 *
 * @author kenny
 */
interface Observer {

	public function update(Observable $o, $object);
}
?>

```

A Unit test (using SimpleTest)

```php
<?php

define('SIMPLE_TEST', '../simpletest/'); 
require_once(SIMPLE_TEST . 'unit_tester.php');
require_once(SIMPLE_TEST . 'reporter.php');
require_once('Observable.php');
require_once('Observer.php');

class MockObservable extends Observable {

    public $object = null;

    public function set($object) {
        $this->object = $object;
    }

    public function setAndNotify($object) {
        $this->object = $object;
        $this->setChanged();
        $this->notifyObservers($object);
    }
   
}

class MockObserver implements Observer {

    public $observed = null;

    public function update(Observable $o, $observed) {
        $this->observed = $observed;
    }
}

class ObserverTest extends UnitTestCase {
   
    public function setUp() {

    }

    public function test() {

        $observer = new MockObserver();
        $observable = new MockObservable();

        // test defaults
        $this->assertNull($observer->observed);
        $this->assertNull($observable->object);
        $this->assertFalse($observable->hasChanged());
        $this->assertEqual($observable->countObservers(), 0);
        
        // test add
        $observable->addObserver($observer);
        $this->assertEqual($observable->countObservers(), 1);

        // test to not add duplicate observers
        $observable->addObserver($observer);
        $this->assertEqual($observable->countObservers(), 1);
        $observer2 = new MockObserver();
        $observable->addObserver($observer2);
        $this->assertEqual($observable->countObservers(), 2);
        $observable->deleteObserver($observer2);
        $this->assertEqual($observable->countObservers(), 1);

        // test delete
        $observable->deleteObserver($observer);
        $this->assertEqual($observable->countObservers(), 0);
        $observable->addObserver($observer);

        $observable->set(10);
        // test not notfiy unless setChagnged() has been called
        $observable->notifyObservers();
        $this->assertNull($observer->observed);

        // test notify
        $observable->setAndNotify(10);
        $this->assertEqual($observer->observed, 10);

    }

}

?>

```