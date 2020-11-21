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

If you wish to run this code elsewhere, such as in your own main method or class, simply import the
class `maze_generator.program.MazeGenerator`, then run

```java
MazeGenerator.run();
```

# Configuration
The configuration file, config.conf, can be used to customize the output of the Maze Generator.
Currently, you can modify the width, height, seed, animation settings, timer settings and maze type here.

### Width and height
Changing the maze dimensions, will cause the maze to have more 'rooms' and hallways, and it
will automatically scale the size of each hallway to fit your screen. Each dimension must be
at least 5 and should not be larger than three times the other dimension.  
###### Example:  

```
width=20
height=20
```

### Maze type (algorithm)
Changing the type variable, will change which algorithm is used to create the maze. This sets the
Maze Generator to use either randomized Depth First Search or randomized Prim's algorithm.  
**Possible values:** dfs, prim  
###### Example:

```
type=dfs
```

### Seed
The seed causes your maze to generate with the given seed. This means that as long as your
initial conditions (width, height, generation algorithm and seed) are the same, the maze
generator will generate the same maze every time you run the code.

**Possible values:** any positive integer (including 0) ;P  
If seed is 0, program will generate its own random seed, resulting in a random maze.  
###### Example:

```
seed=8855
```

#### Animation
The animate setting can be set to either true or false whether you want the
generation process to be animated or not.  
**Possible values:** true/false

Furthermore, you can change the speed at which
the generation process runs, by changing the animation delay. A higher delay value
causes the maze to generate slower. I recommend a value between 0 and 20 depending
on the maze size.  
**Possible values:** any positive integer  
###### Example:

```
animate=true
animationDelay=5
```

#### Timer
The timer setting can be used to turn on or off the timer showing after the maze
has generated. While the timer variable is true, the program will state the time
it took to generate the maze after it is done generating. This will be printed
to the terminal.  
**Possible values:** true/false  
###### Example:

```
timer=true
```


# License
This code is protected under the [GNU General Public License 3.0](http://www.gnu.org/licenses/gpl-3.0.html)
