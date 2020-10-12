# Maze Generator
A maze generator written in Java, generating mazes of custom size.
The size and maze type can be set in the config file. The maze size 
will automatically scale to fit the screen size. 

There are currently only two maze generation algorithms implemented, both of which
are randomized versions of graph algorithms, specifically Depth First Search and Prim's algorithm.
Personally, I think the randomized DFS approach creates the best looking mazes, although these are 
not particularly difficult to solve. On the other hand, Prim's algorithm creates a much 
messier-looking maze, but one which seems to be more difficult to traverse than the DFS mazes.

# Usage
To run this program, you could clone this project to a Java IDE, such as Eclipse or IntelliJ
and run the main class maze_generator.program.MazeGenerator.java.

If you wish to run this code elsewhere, such as in your own main method or class, simply
use:

```java
import maze_generator.program.MazeGenerator;
MazeGenerator.run();
```

# Configuration
The configuration file, config.ini, can be used to customize the output of the Maze Generator.
Currently, you can modify the width, height, and maze type here.

Changing the maze dimensions, will cause the maze to have more 'rooms' and hallways, and it
will automatically scale the size of each hallway to fit your screen.

Changing the type variable, will change which algorithm is used to create the maze. Currently
the only options here are the keywords 'dfs' and 'prim' (without the quotes). This sets the 
Maze Generator to use the randomized Depth First Search or randomized Prim's algorithm respectively.


# License
This code is protected under the GNU General Public License 3.0
