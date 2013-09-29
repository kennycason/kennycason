---
title: PHP - Binary Tree
author: Kenny Cason
tags: binary tree, btree, PHP
---

A simple binary tree in php, will add a delete function later :P


```php
<?php

/**
 * binary Tree node
 */
class BinaryTreeNode {

	public $object;
	public $left;
	public $right;

	public function __construct($object) {
		$this->object = $object;
		$this->left = null;
		$this->right = null;
	}

	public function compareTo($object) {
		if ($this->object <= $object) {
			return BinaryTree::$RIGHT;
		}
		return BinaryTree::$LEFT;
	}

}
/**
 * A simple BinaryTree
 *
 * @author kenny
 */
class BinaryTree {

	public static $LEFT = -1;

	public static $RIGHT = 1;

	private $root;

	private $size;

	public function __construct() {
		$this->root = null;
		$this->size = null;
	}

	public function add($object) {
		$this->addRecur($this->root, $object);
	}

	private function addRecur($n, $object) {
		if($this->root == null) {
			$this->root = new BinaryTreeNode($object);
			$this->size++;
		} else if($n->compareTo($object) == BinaryTree::$LEFT) {
			if($n->left == null) {
				$n->left = new BinaryTreeNode($object);
				$this->size++;
			} else {
				$this->addRecur($n->left, $object);
			}
		} else {
			if($n->right == null) {
				$n->right = new BinaryTreeNode($object);
				$this->size++;
			} else {
				$this->addRecur($n->right, $object);
			}
		}
	}

	public function contains($object) {
		return $this->containsRecur($this->root, $object);
	}

	private function containsRecur($n, $object) {
		if($this->root == null) {
			return false;
		} else if($n->object == $object) {
			return true;
		} else if($n->compareTo($object) == BinaryTree::$LEFT) {
			if($n->left == null) {
				return false;
			} else {
				return $this->containsRecur($n->left, $object);
			}
		} else {
			if($n->right == null) {
				return false;
			} else {
				return $this->containsRecur($n->right, $object);
			}
		}
	}

	public function size() {
		return $this->size;
	}

	public function isEmpty() {
		return ($this->size == 0);
	}

	public function getAll() {
		$all = array();
		$this->getAllRecur($this->root, $all);
		return $all;
	}

	private function getAllRecur($n, &$all) {
		if($n == null) {
			return;
		}
		$all[] = $n;
		$this->getAllRecur($n->left, $all);
		$this->getAllRecur($n->right, $all);
	}

	public function getRoot() {
		if($this->root == null) {
			return null;
		}
		return $this->root->object;
	}

}
?>

```

A Unit test (using SimpleTest)


```php
<?php

define('SIMPLE_TEST', '../simpletest/'); 
require_once(SIMPLE_TEST . 'unit_tester.php');
require_once(SIMPLE_TEST . 'reporter.php');
$test = &new GroupTest('BinaryTreeTest');
$test->addTestCase(new BinaryTreeTest());
$test->run(new HtmlReporter());


class BinaryTreeTest extends UnitTestCase {
   
    public function setUp() {

    }

    public function test() {

        $btree = new BinaryTree();
        $this->assertEqual($btree->size(), 0);
        $btree->add(10);
        $this->assertEqual($btree->size(), 1);
        $this->assertEqual($btree->getRoot(), 10);
        
        $btree->add(15);
        $this->assertEqual($btree->size(), 2);
        
        $btree->add(5);
        $this->assertEqual($btree->size(), 3);
        $btree->add(3);
        $this->assertEqual($btree->size(), 4);

        $this->assertEqual($btree->getRoot(), 10);


        $this->assertTrue($btree->contains(10));
        $this->assertTrue($btree->contains(15));
        $this->assertTrue($btree->contains(5));
        $this->assertFalse($btree->contains(-1));
        $this->assertFalse($btree->contains(55));

        $arr = $btree->getAll();
        $this->assertEqual(count($arr), $btree->size());
        

    }

}

?>


```
