# Maze Generator
A maze generator written in Java, generating mazes of custom size.
Maze type, seed and other settings can be set in the GUI  
The maze size will automatically scale to fit the screen size.

There are currently only two maze generation algorithms implemented, both of which
are randomized versions of graph algorithms, specifically Depth First Search and Prim's algorithm.
Personally, I think the randomized DFS approach creates the best looking mazes, although these are
not particularly difficult to solve. On the other hand, Prim's algorithm creates a much
messier-looking maze, but one which seems to be more difficult to traverse than the DFS mazes.

# Usage
To run this program, you can download the runnable .jar file or clone this project to a Java IDE

### Download 
First download the [jar](https://github.com/haakon8855/maze_generator/releases), then execute the 
program by running `java -jar haakons_maze_generator_vX.X.jar`.
This application has only been tested on JDK 11, and I can not guarantee any success on any other
version of Java, although any JDK above version 11 should be perfectly fine. 

### Clone
Simply clone the project to a Java IDE such as Eclipse or IntelliJ and run the main class 
maze_generator.program.MazeGenerator.java or you could clone the project to any local 
directory and compile and run manually. If you opt
for the latter, i'm sure you know what you're doing, just run the main class program.MazeGenerator
in the maze_generator module. 

# Requirements
- JDK 11 or higher

# Configuration
Most settings can be changed in the application's GUI. There is however also a
config file, config.conf, which can be used to change some more 'experimental'
settings.
The main purpose of this config file is mainly to change the default settings
when starting the application.
You can modify the width, height, seed, animation settings, timer settings and maze type
here. Below is an explanation of how to change these in the config file.

### Width and height
Changing the maze dimensions, will cause the maze to have more 'rooms' and hallways, and it
will automatically scale the size of each hallway to fit your screen. Each dimension must be
at least 5 and should not be larger than three times the other dimension.  
**Possible values:** Any positive integer larger than 5  
###### Example:  

```
width = 48
height = 22
```

### Maze type (algorithm)
Changing the type variable, will change which algorithm is used to create the maze. This sets the
Maze Generator to use either randomized Depth First Search or randomized Prim's algorithm.  
**Possible values:** dfs, prim  
###### Example:

```
type = dfs
```

### Seed
The seed causes your maze to generate with the given seed. This means that as long as your
initial conditions (width, height, generation algorithm and seed) are the same, the maze
generator will generate the same maze every time you run it.

**Possible values:** Any positive integer (including 0) ;P  
If seed is 0, the program will generate its own random seed, resulting in a random maze each iteration.  
###### Example:

```
seed = 8855
```

#### Animation
The animate setting can be set to either true or false whether you want the
generation process to be animated or not.  
**Possible values:** true/false

Furthermore, you can change the speed at which
the generation process runs, by changing the animation delay. A higher delay value
causes the maze to generate slower. I recommend a value between 0 and 20 depending
on the maze size.  
**Possible values:** Any positive integer  
###### Example:

```
animate = true
animationDelay = 5
```

#### Timer
The timer setting can be used to turn on or off the timer showing after the maze
has generated. While the timer variable is true, the program will state the time
it took to generate the maze after it is done generating. This will be printed
to the terminal.  
**Possible values:** true/false  
###### Example:

```
timer = true
```


# License
This code is protected under the [GNU General Public License 3.0](http://www.gnu.org/licenses/gpl-3.0.html)
