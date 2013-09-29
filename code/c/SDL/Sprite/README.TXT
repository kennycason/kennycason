Sprite library for SDL - using bitmaps
This library was put together by Kenny Cason and is designed to
be easily implemented into any C++ program using SDL
Feel free to do what ever you want with it, just please give me some credit!
enjoy!
Please report any bugs
kenneth.cason@gmail.com
www.ken-soft.com
www.facebook.com/kenny.cason
+----------------------------+
compile:
g++ *.cpp -o OUTPUT_NAME -lSDL
run:
./OUTPUT_NAME

be sure to have appropriate SDL libs installed
http://www.libsdl.org/

+----------------------------+
version 1d
2010 Jun 18
added "builder design" to all operations/setters such that they return the Sprite object.
	This allows easy implentation of a set of operations.
	examples:
	sprite->zoom(50)->setTransparency(255,0,255);
	sprite->animate()->draw(screen,220,70);
	AS OPPOSED TO: (2 lines)
	sprite->zoom(50);
	sprite->setTransparency(255,0,255); 
added more functions, primarily getters and setters

+----------------------------+
version 1c 
2010 Jun 1
fixed some minor bugs

+----------------------------+
version 1b
2009 Sep 20
original release
