---
title: PHP Timer Class
author: Kenny Cason
tags: interval, milliseconds, php, seconds, timer
---

A small timer class I wrote:

Timer.php

```php
<?php

class Timer {

　　　　const MICROSECONDS = 0;

　　　　const SECONDS = 1;

　　　　private $startTime;

　　　　private $interval = 0;

　　　　private $units;
　　　　
　　　　public function __construct($units = Timer::MICROSECONDS) {
　　　　　　　　$this->startTime = $this->timeAsMilliseconds();
　　　　　　　　$this->units = $units;
　　　　}

　　　　public function start() {
　　　　　　　　$this->startTime = $this->timeAsMilliseconds();
　　　　}

　　　　public function restart() {
　　　　　　　　$this->start();
　　　　}

　　　　public function elapsedTime() {
　　　　　　　　$diff = $this->timeAsMilliseconds() - $this->startTime;
　　　　　　　　if($this->units == Timer::MICROSECONDS) {
　　　　　　　　　　　　return $diff;
　　　　　　　　} else {
　　　　　　　　　　　　return (int)($diff / 1000);
　　　　　　　　}
　　　　}

　　　　public function setInterval($interval) {
　　　　　　　　if($this->units == Timer::SECONDS) {
　　　　　　　　　　　　$interval *= 1000;
　　　　　　　　}
　　　　　　　　$this->interval = $interval;
　　　　}

　　　　public function intervalElapsed() {
　　　　　　　　return ($this->timeAsMilliseconds() - $this->startTime) > $this->interval;
　　　　}

　　　　private function timeAsMilliseconds() {
　　　　　　　　return round(microtime(true) * 1000);
　　　　}
　　　　
}

```

TimerDemo.php

```php
require_once('Timer.php');

// timer test, milliseconds
$timer = new Timer();
$timer->start();
usleep(1000);
echo $timer->elapsedTime() . 'ms';

// timer test, seconds
$timer = new Timer(Timer::SECONDS);
$timer->start();
usleep(1000);
echo $timer->elapsedTime() . 's';

// interval test, milliseconds
$timer = new Timer();
$timer->setInterval(2000);
$timer->start();
usleep(1000);
echo $timer->intervalElapsed() ? 'true' : 'false';
usleep(1200);
echo $timer->intervalElapsed() ? 'true' : 'false';

// interval test, seconds
$timer = new Timer(Timer::SECONDS);
$timer->setInterval(2);
$timer->start();
usleep(1000);
echo $timer->intervalElapsed() ? 'true' : 'false';
usleep(1200);
echo $timer->intervalElapsed() ? 'true' : 'false';

```
