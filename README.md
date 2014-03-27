# 2048 in java #
a simple implement of [2048] using java.
there is the online version, [Play it here!](http://gabrielecirulli.github.io/2048/)
[2048]: https://github.com/gabrielecirulli/2048/

### Development ###

some ideas behind the game.

* Tiles are drawn by java.awt.Graphics.
* Single dimensional arrays to simulate coordinate system. tiles[x + y * 4] get the tile in (x, y)
* A movements just move all the tile to one direction, then merge it ASAP.
* The movements to different direction is using rotate to do so.


### Requirements ###

* OpenJDK-7 or newer to compile and run

```
$ >  cd 2048.java; mkdir bin
$ >  javac -d bin src/phx/*       # compile
$ >  java -cp bin phx.GUI2048     # enjoy
```


### HOWTO play ###

1. use arrow keys to move the tiles.
2. a vi-like keybindind also OK :)
3. press 'r' to start new game.

### BUGS ###

* Didn't implements score
