# game_of_life

The Game of Life, invented by Cambridge mathematician John H. Conway, is a "cellular automaton" supposed to model the genetic laws for birth, survival and death. It became widely known when it was mentioned in an article published by Scientific American in 1970.

The Game of Life is a no-player game. One interacts with it by seeding it and observing how it evolves.

This is my version in Java Swing using LinkedList. 

## Running
The game requires Java to run. Download and install  the [latest](https://java.com/en/download/) version and then download the **game_of_life.jar** file from the [releases](https://github.com/bycomputing/game_of_life/releases/latest). Double click it to run or on the command line type:

    java -jar game_of_life.jar

You can also try the Java applet version I made and run this game thru your web browser if it still supports it. Click this [applet page](https://dl.dropboxusercontent.com/s/c2pj7hgn57e5px6/index.html) to see it.

You can also view it using the  AppletViewer if you have it installed. First create an **index.html** file, which contains the following:

    <APPLET archive="game_of_life.jar"
            code="LifeApplet.class"
            width=640
            height=480>
    </APPLET>
and save it in the same location as your **game_of_life.jar** file. Then run:

    appletviewer index.html
    
## Building
If you have Apache Ant installed then you can build it yourself just by typing:

    ant
It would create a build directory and the jar file would be inside that directory.

## Credits
There is a similar project from Matthew Burke from where I took the idea on how to implement the main algorithm of the game and some code on GUI formatting and re-sizing. You can find his project here: https://github.com/Burke9077/Conway-s-Game-of-Life
