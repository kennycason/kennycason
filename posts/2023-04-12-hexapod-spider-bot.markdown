---
title: Hexapod Spider Bot - Raspberry Pi + Arduino + Astropi + Video + OpenCV
author: Kenny Cason
tags: robotics, raspberry pi, arduino, astropi, 
---

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_final.jpeg" width="100%"/>
Finished Hexapod Spider Bot equipped with Raspberry Pi 4, Camera, and Astro Pi.

&nbsp;

#### Background

During High School I joined a program called <a href="https://www.odysseyofthemind.com/" target="blank">"Odyssey of the Mind"</a>. 
Our group's project was to build a remote-controlled robot.
Those were some humbling weeks. 
It quickly became clear that I had bit off more than I could chew, and I ultimately gave up.
I had no idea where to source the parts, and the internet wasn't as robust as it is nowadays.

My next attempt at robotics would by in 2008 during my year of study abroad in Japan, an amazing country and where I made many life-long friends.
I spent my second semester learning about <a href="/posts/2008-12-24-neural-network-jp.html" target="blank">neural networks</a> and robotics.
- ✅ The robot project was successful and I could control it via code. 
- ✅ The neural network "research" was also successful and I learned alot.
- ❌ However, I did not at integrate the two; a larger task. :)

For my first robotics project in 15 years, I decided to purchase a <a href="https://www.amazon.com/Freenove-Raspberry-Crawling-Detailed-Tutorial/dp/B07FLVZ2DN/" target="blank">"FREENOVE Hexapod Robot Kit with Remote"</a> on Amazon for a very reasonable $129 USD. Freenove's customer support is also friendly and fast.


#### Features

- Remote Control via RF24 using Arduino + Sketch
- Walk, bend, and rotate in all directions
- Mounted Raspberry Pi4 (Now battery powered)
- Daytime/Nighttime camera mounted on front with virtual display
- Face/object detection via OpenCV
- Astro Pi HAT (temperature, humidity, accelerometer, air pressure, and more)
- KEYESTUDIO GPIO Breakout Board + 37 Sensor Kit (Flashlight / Infrared Sensor)

&nbsp;

#### Code & Resources on GitHub
- <a href="https://github.com/kennycason/robotics/tree/main/arduino" target="blank">Hexapod Arduino Code</a>`*`
- <a href="https://github.com/kennycason/robotics/tree/main/processing" target="blank">Hexapod Calibration Code</a>`*`
- <a href="https://github.com/kennycason/robotics/tree/main/picamera" target="blank">Picamera / OpenCV</a>
- <a href="https://github.com/kennycason/robotics/tree/main/astropi" target="blank">Astropi HAT</a>

`*` For Hexapod code, I recommend downloading the latest sources from FREENODE directly.

&nbsp;

#### Video Demo

<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/XpG_W7vGShM?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div>
A video demonstrating remote-control walking + camera w/face detection.

&nbsp;

#### The Build

The build is broken into three phases:
1. Build the Hexapod Base + Remote Control
2. Build and mount the Raspberry Pi "Brain"
3. Configure Camera + Live Video Stream w/OpenCV

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_final_zoomed.jpeg" width="75%"/>

&nbsp;

#### Hexapod Base Build

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly01.jpeg" width="47%"/><img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly02.jpeg" width="47%"/>

Before assembly, I examined each piece and read through a couple tutorials + instructions to gain some familiarity.

My first thought was that I was impressed with the quality of the acrylic pieces.

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly03.jpeg" width="75%"/>

Some late night Netflixing & leg assembly.

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_help01.jpeg" width="38%"/><img class="modal-target" src="/images/robotics/spiderbot/spiderbot_help02.jpeg" width="58%"/>

With some help of course. :)

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly04.jpeg" width="75%"/>

I recommend paying particularly close attention when you are hooking up the leg wires to the Arduino board. 
It's very easy to mess up, and you may not know until you begin calibration.

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly05.jpeg" width="75%"/>

Finally, install the bottom plate.

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_assembly06.jpeg" width="50%"/>

And with that, the Hexapod's assembly is complete.

&nbsp;


<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_calibration.jpeg" width="60%"/>

After assembly, the servo motors must be calibrated. 
Calibration is done via a Processing script and was pretty straightforward to use.
I also took a small diversion to learn more about the Processing language, which I have heard of but never had the chance to explore.

<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/L434Y4VIexE?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div>
<div class="yt-container"><iframe class="yt-video" src="//www.youtube.com/embed/CbeuG4O-77c?feature=player_detailpage" frameborder="0" allowfullscreen></iframe></div>

&nbsp;

#### Mount Raspberry Pi "Brain"

Initially I was considering extending the existing Hexapod's base code and building on the Arduino board.
But after more thought I decided to instead mount a Raspberry Pi 4 on top of the Hexapod. 
The Raspberry Pi will handle the camera stream, infrared sensor as well as any post-processing, such as using OpenCV for face detection, and Astro Pi integration.

I chose Python because of how easy it was to set up on the Pi, and it's numerous of libraries.
There are libraries for GPIO communication, which is how we interface with most of our sensors.
Libraries like Tensorflow and OpenCV are also available.
Even libraries like Pygame are nice for quick PS5 controller support, which is now my default controller.
Finally, I wrote a light REST API interface using Flask.

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_brain01.jpeg" width="49%"/>
<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_brain02.jpeg" width="49%"/>
<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_brain03.jpeg" width="49%"/>

&nbsp;

I found the below references helpful when mapping GPIO pins.
I unfortunately am not sure where I found these.

<img class="modal-target" src="/images/robotics/spiderbot/gpio_board_pins.png" width="49%"/>
<img class="modal-target" src="/images/robotics/spiderbot/pi_gpio_pins.png" width="49%"/>

&nbsp;


##### Astro Pi

From the <a href="https://www.esa.int/Education/AstroPI/What_is_an_Astro_Pi" target="blank">ESA website</a>:

> Astro Pi is a small Raspberry Pi computer developed by the Raspberry Pi Foundation (RPF), in collaboration with the UK Space Agency and the European Space Agency (ESA).<br/><br/>
> Astro Pi computers come with a set of sensors and gadgets that can be used to run great scientific experiments by means of computer coding. This set of sensors is called ‘Sense HAT’ (that stands for ‘Hardware Attached on Top’).<br/><br/>
> The Sense HAT add-on board was specially created for the Astro Pi competition. The board gives Astro Pi the ability to ‘sense’ and make many kinds of measurements, from temperature to movement, and to output information using a special display  - the  8x8 LED matrix. The Astro Pis are also equipped with a joystick and buttons -  just like a videogame console!

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_astropi_02.jpeg" width="75%"/>

I bought it on a whim, but really enjoyed programming with it.
I was able to write code that would change the LED display based on the Hexapod's orientation. I'm looking forward to using its sensory as input for other robots.

Code for interfacing with the Astro Pi HAT can be found <a href="https://github.com/kennycason/robotics/tree/main/astropi" target="blank">here</a>.

Example code to read temperature:
```python
from sense_hat import SenseHat

sense = SenseHat()
sense.clear()

temp = sense.get_temperature()
print(temp) # 21.263586044311523 Celsius
```

&nbsp;


##### Stream Video over TCP

To stream video from the Raspberry Pi to a client computer, I used `libcamera-vid` and `ffplay`/`vlc`.
For my video client I would either use my primary laptop or a Raspberry Pi 3 + Monitor.


Server (spider.local)
```bash
libcamera-vid -t 0  -q 100 --framerate 3 -n --codec mjpeg --inline --listen -o tcp://192.168.4.76:8888 -v
```
or with lower quality and a higher framerate.
```bash
libcamera-vid -t 0  -q 50 --framerate 10 -n --codec mjpeg --inline --listen -o tcp://192.168.4.76:8888 -v 
```


Client (VLC -> Open Network)
```bash
tcp/mjpeg://192.168.4.76:8888
```
Client using FFPlay
```bash
ffplay -probesize 32 -analyzeduration 0 -fflags nobuffer -fflags flush_packets -flags low_delay -framerate 30 -framedrop tcp://192.168.4.76:8888
```

Notes:
- Sometimes I begin to experience video lag after a while. Dropping the quality and framerate seemed to help, but this issue occurred frequently.
- Another option I had success with was to configure the Pi for Virtual Desktop and VNC Viewer as seen below.
- Using `libcamera-vid` and `ffplay`/`vlc` means you directly stream the webcam data to clients without any video processing. In order to apply face-detection, I use a Python + OpenCV to read images from the camera directly on the Pi.

&nbsp;

##### OpenCV + Face Detection

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_camera01.jpeg" width="49%"/>
<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_camera02.jpeg" width="49%"/>

Below is sample Python code demonstrating how easy it is to use OpenCV to perform face detection from a camera stream.

```python
#!/usr/bin/python3

# run the if running from terminal/ssh
# export DISPLAY=:0
import cv2
from time import sleep

from picamera2 import Picamera2

# Grab images as numpy arrays and leave everything else to OpenCV.
face_detector = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")
cv2.startWindowThread()

picam2 = Picamera2()
picam2.configure(picam2.create_preview_configuration(main={"format": 'XRGB8888', "size": (640, 480)}))
picam2.start()

i = 0
while True:
    image = picam2.capture_array()

    grey = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    faces = face_detector.detectMultiScale(grey, 1.1, 5)

    for (x, y, w, h) in faces:
        cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0))

    cv2.imwrite("/tmp/camera" + str(i) + ".jpg", image)
    # cv2.imshow("Camera", image) # uncomment if using Virtual Desktop / VNC Viewer
    sleep(1)
    i += 1
```

&nbsp;

#### Hardware Shopping List

- <a href="https://www.amazon.com/gp/product/B07FLVZ2DN/" target="blank">FREENOVE Hexapod Robot Kit with Remote</a>
- <a href="https://www.raspberrypi.com/products/raspberry-pi-4-model-b/" target="blank">Raspberry Pi 4</a>
- <a href="https://www.amazon.com/gp/product/B08ZJ46SKK/" target="blank">Sumolink Mount Holder for Raspberry Pi HQ Camera Module</a>
- <a href="https://www.amazon.com/gp/product/B0798DYZQW/" target="blank">GPIO Breakout Board + 37 Sensors Kit</a>
- <a href="https://www.amazon.com/gp/product/B07DNSSDGG/" target="blank">Dorhea for Raspberry Pi Camera Module Automatic IR-Cut Switching Day/Night Vision 1080p HD</a>
- <a href="https://www.raspberrypi.com/products/sense-hat/" target="blank">Astro Pi HAT</a>
- <a href="https://www.amazon.com/gp/product/B09BNRKQD8/" target="blank">VGE Battery Pack for Raspberry Pi 4, 4000mAh, 5V 2.4A, Adhesive (USB-C)</a>
- <a href="https://www.amazon.com/gp/product/B08C2DJBT2/" target="blank">40-pin GPIO adapters & extenders</a>
- <a href="https://www.amazon.com/gp/product/B09NL155Z9/" target="blank">3600mAh Flat Top 3.7V 30A Flat Top Rechargeable Battery </a>
- <a href="https://www.amazon.com/gp/product/B07BFWHD7G/" target="blank">Universal Smart Battery Charger 4 Bay for Rechargeable Batteries with LCD Display</a>

&nbsp;

<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_scarlett01.jpeg" width="49%"/>
<img class="modal-target" src="/images/robotics/spiderbot/spiderbot_scarlett02.jpeg" width="49%"/>
Scarlett playing with Spider Bot :)
