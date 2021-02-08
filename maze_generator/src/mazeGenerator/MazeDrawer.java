package mazeGenerator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import datatypes.Maze;
import datatypes.Node;
import datatypes.Wall;
import program.MazeGenerator;
import program.MazeType;

public class MazeDrawer {
	
	public static final int maxDimension = 200;
	public static final int settingsWidth = 350;
	public static final int sliderMax = 100;
	private int width, height;
	private int animationDelay;
	private JFrame frame;
	private JPanel container;
	private JPanel settingsPanel;
	private MazeDrawerCanvas canvas;
	private JButton btnGenerate;
	private JButton btnAbort;
	private JFormattedTextField seed;
	private JFormattedTextField widthInput;
	private JFormattedTextField heightInput;
	private JCheckBox randomCheckBox;
	private JCheckBox animationCheckBox;
	private JSlider animationSlider;
	
	private MazeGenerator generator;
	
	/**
	 * Constructs a maze drawer which draws the maze of give width and height 
	 * @param widthInBlocks given in number of nodes/blocks
	 * @param heightInBlocks given in number of nodes/blocks
	 */
	public MazeDrawer(int widthInBlocks, int heightInBlocks, int animationDelay, 
					  boolean animate, long seed, MazeGenerator generator) {
		this.animationDelay = animationDelay;
		this.generator = generator;

		// Initialize main window
		init(widthInBlocks, heightInBlocks);

		this.randomCheckBox.setSelected(seed == 0);
		this.seed.setEditable(seed != 0);
		if (seed == 0) {
			setSeedValue(seed);
		}
		this.animationCheckBox.setSelected(animate);
		this.setAnimationSliderValue(this.generator.getAnimationSpeed());
		this.btnAbort.setEnabled(false);
	}
	
	private void init(int widthInBlocks, int heightInBlocks) {
		if (this.frame != null) {
			this.frame.setVisible(false);
			this.frame.dispose();
		}
		// Set up main JFrame
		this.frame = new JFrame("Håkon's Maze Generator");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Calculate some needed values
		int blocksize = calculateBlocksize(widthInBlocks, heightInBlocks);
		
		this.width = calculateWidth(widthInBlocks, blocksize);	// width in pixels
		this.height = calculateHeight(heightInBlocks, blocksize); // height in pixels
		
		// Put window in the center of the screen
		centerScreen();
		
		// Main flowLayout-style-container for all other UI-elements
		this.container = new JPanel(new FlowLayout());
		
		
		// Initialize the canvas
		initCanvas(blocksize, blocksize);
		
		// Initialize the settings panel
		initSettingsPanel();
		
		// Set application icon
		setIcon("icon32x32.png");

		// Finally, add the main container JPanel to the frame
		this.frame.add(this.container);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);

		// Set blank canvas
		clearCanvas();

		// Initialize the necessary action listeners for the UI-elements
		this.addActionListeners();
	}
	
	/**
	 * Sets the window's icon in the title bar
	 * @param icon path
	 */
	private void setIcon(String path) {
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(path));
		this.frame.setIconImage(icon);
	}
	
	/**
	 * Resizes the canvas to fit the current width and heigth of the program
	 * @param blocksize
	 * @param wallWidth
	 */
	private void resize(int blocksize, int wallWidth) {
		this.canvas.setSize(width, height);
		this.canvas.setPreferredSize(new Dimension(width, height));
		this.canvas.setBlocksize(blocksize);
		this.canvas.setWallWidth(wallWidth);
		this.settingsPanel.setSize(settingsWidth, height);
		this.settingsPanel.setPreferredSize(new Dimension(settingsWidth, height));
		this.frame.pack();
		clearCanvas();
		centerScreen();
	}
	
	/**
	 * Initializes the settings panel by setting its size, and adding its components
	 */
	private void initSettingsPanel() {
		this.settingsPanel = new JPanel();
		this.settingsPanel.setLayout(new BoxLayout(this.settingsPanel, BoxLayout.Y_AXIS));
		// temprorary size
		Dimension settingsDim = new Dimension(settingsWidth, height);
		this.settingsPanel.setSize(settingsDim);
		this.settingsPanel.setPreferredSize(settingsDim);
		
		JPanel settingsGrid = new JPanel(new GridBagLayout());

		// Add settings components
		int i = 0;
		addAlgortihmSelection(settingsGrid, i); i++;
		addWidthHeightInput(settingsGrid, i); i+=2;
		addSeedInput(settingsGrid, i); i++;
		addSeedCheckBox(settingsGrid, i); i++;
		addUseAnimation(settingsGrid, i); i++;
		addAnimationSpeedSlider(settingsGrid, i); i++;

		this.settingsPanel.add(settingsGrid);

		addControlPanel(settingsGrid);

		this.container.add(settingsPanel);
	}
	
	/**
	 * Add the option to select the algorithm used for maze generation. A dropdown list of 
	 * algorithms.
	 * @param settingsGrid
	 * @param line number for where to put boxes in the grid
	 */
	private void addAlgortihmSelection(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Algorithm selection label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Generation Algorithm:"), c);
		// Algorithm selection dropdown menu (JComboBox)
		String[] algorithms = new String[MazeType.values().length];
		for (int i = 0; i < algorithms.length; i++) {
			MazeType type_i = MazeType.values()[i];
			algorithms[i] = type_i.toString();
		}
		JComboBox<String> cbAlgorithms = new JComboBox<String>(algorithms);
		int selectedIndex = getSelectedMazeTypeIndex(algorithms);
		cbAlgorithms.setSelectedIndex(selectedIndex);
		c.gridx = 1;
		c.gridy = line;
		addCbAlgorithmsActionListener(cbAlgorithms);
		settingsGrid.add(cbAlgorithms, c);
		settingsGrid.setAlignmentY(Component.TOP_ALIGNMENT);
	}
	
	/**
	 * Add width and height input boxes
	 * @param settingsGrid
	 * @param line number for where to put boxes in the grid
	 */
	private void addWidthHeightInput(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Width input label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Width: "), c);
		// Width input box
		c.gridx = 1;
		c.gridy = line;
		this.widthInput = new JFormattedTextField(makeDimensionFormatter());
		this.widthInput.setColumns(18);
		this.widthInput.setMinimumSize(new Dimension(170, 20));
		this.widthInput.setHorizontalAlignment(SwingConstants.RIGHT);
		this.widthInput.setVisible(true);
		settingsGrid.add(this.widthInput, c);
		
		// Height
		line++;
		// Height input label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Heigth: "), c);
		// Height input box
		c.gridx = 1;
		c.gridy = line;
		this.heightInput = new JFormattedTextField(makeDimensionFormatter());
		this.heightInput.setColumns(18);
		this.heightInput.setMinimumSize(new Dimension(170, 20));
		this.heightInput.setHorizontalAlignment(SwingConstants.RIGHT);
		this.heightInput.setVisible(true);
		settingsGrid.add(this.heightInput, c);
	}
	
	/**
	 * Add the input field for seed selection to the UI.
	 * This field is also used to show the randomly generated seed in order to save seeds.
	 * @param settingsGridd
	 * @param line number for where to put boxes in the grid
	 */
	private void addSeedInput(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Seed input field label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Seed: "), c);
		// Seed input
		c.gridx = 1;
		c.gridy = line;
		NumberFormatter formatter = makeLongFormatter();
		this.seed = new JFormattedTextField(formatter);
		this.seed.setColumns(18);
		this.seed.setMinimumSize(new Dimension(170, 20));
		this.seed.setHorizontalAlignment(SwingConstants.RIGHT);
		this.seed.setVisible(true);
		settingsGrid.add(this.seed, c);
	}
	
	/**
	 * Add the option to use random or custom seed to the UI
	 * @param settingsGrid
	 * @param line number for where to put boxes in the grid
	 */
	private void addSeedCheckBox(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Seed check box label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Use random seed"), c);
		// Seed check box
		c.gridx = 1;
		c.gridy = line;
		this.randomCheckBox = new JCheckBox();
		settingsGrid.add(this.randomCheckBox, c);
	}
	
	/**
	 * Add the option to use animations or not to the UI
	 * @param settingsGrid
	 * @param line number for where to put boxes in the grid
	 */
	private void addUseAnimation(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Use animation label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Show animation"), c);
		// Use animation checkbox
		c.gridx = 1;
		c.gridy = line;
		this.animationCheckBox = new JCheckBox();
		settingsGrid.add(this.animationCheckBox, c);
	}
	
	/**
	 * Adds a slider to select animation speed
	 * @param settingsGrid
	 * @param line number for where to put boxes in the grid
	 */
	private void addAnimationSpeedSlider(JPanel settingsGrid, int line) {
		// Constraints
		GridBagConstraints c = getDefaultConstraints();
		// Use animation label
		c.gridx = 0;
		c.gridy = line;
		settingsGrid.add(new JLabel("Animation speed"), c);
		// Animation speed slider
		c.gridx = 1;
		c.gridy = line;
		this.animationSlider = new JSlider();
		settingsGrid.add(this.animationSlider, c);
	}
	
	/**
	 * Add the control panel with start and stop buttons to the UI
	 * @param settingsPanel
	 */
	private void addControlPanel(JPanel settingsPanel) {
		JPanel controlPanel = new JPanel();
		this.btnGenerate = new JButton("Generate!");
		this.btnGenerate.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.btnGenerate.setActionCommand("start");
		controlPanel.add(btnGenerate);
		this.btnAbort = new JButton("Abort");
		this.btnAbort.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.btnAbort.setActionCommand("stop");
		controlPanel.add(btnAbort);
		this.settingsPanel.add(controlPanel);
	}
	
	/**
	 * Generates a default GridBagConstraints for use when adding elements to the UI
	 * @return GridBagConstraints constraint
	 */
	private GridBagConstraints getDefaultConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 10, 4, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	/**
	 * Makes the numberformatter used to keep only digits in the width and height input fields.
	 * @return numberformatter which formats numbers for dimensions
	 */
	public NumberFormatter makeDimensionFormatter() {
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format) {
			private static final long serialVersionUID = -3632582082611336565L;
			public Object stringToValue(String string) throws ParseException {
				if (string == null || string.length() == 0) {
					return null;
				}
				Object value = super.stringToValue(string);
				int intValue = (Integer) value;
				if (intValue > maxDimension) {
					intValue = maxDimension;
				}
				return super.stringToValue("" + intValue);
			}
		};
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(maxDimension*10);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		return formatter;
	}
	
	/**
	 * Makes the numberformatter used to keep only digits in the seed text input field.
	 * Also adds a special case to allow an empty textbox. 
	 * @return
	 */
	public NumberFormatter makeLongFormatter() {
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format) {
			private static final long serialVersionUID = -3632582082611336565L;

			public Object stringToValue(String string) throws ParseException {
				if (string == null || string.length() == 0 || string.equals("-")) {
					return null;
				}
				return super.stringToValue(string);
			}
		};
		formatter.setMinimum(Long.MIN_VALUE);
		formatter.setMaximum(Long.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		return formatter;
	}
	
	/**
	 * Sends the dimensions (width and height) to the generator
	 */
	public void submitDimensions() {
		int widthInBlocks = 20;
		int heightInBlocks = 20;
		Object widthVal = this.widthInput.getValue();
		Object heightVal = this.heightInput.getValue();
		if (widthVal != null) {
			widthInBlocks = (int) widthVal;
		}
		if (heightVal != null) {
			heightInBlocks = (int) heightVal;
		}

		int oldWidthInBlocks = generator.getWidth();
		int oldHeightInBlocks = generator.getHeight();
		generator.setDimensions(widthInBlocks, heightInBlocks);
		int newWidthInBlocks = generator.getWidth();
		int newHeightInBlocks = generator.getHeight();
		if (newWidthInBlocks != widthInBlocks) {
			widthInBlocks = newWidthInBlocks;
		}
		if (newHeightInBlocks != heightInBlocks) {
			heightInBlocks = newHeightInBlocks;
		}
		boolean dimChanged = true;
		if (oldWidthInBlocks == newWidthInBlocks && oldHeightInBlocks == newHeightInBlocks) {
			dimChanged = false;
		}
		this.widthInput.setValue(widthInBlocks);
		this.heightInput.setValue(heightInBlocks);
		if (dimChanged) {
			int blocksize = calculateBlocksize(widthInBlocks, heightInBlocks);
			this.width = calculateWidth(widthInBlocks, blocksize);	// width in pixels
			this.height = calculateHeight(heightInBlocks, blocksize); // height in pixels
			resize(blocksize, blocksize);
		}
	}
	
	/**
	 * Sends the seed in the seed input field to the generator if the checkbox for random seed
	 * is not ticked.
	 */
	public void submitSeed() {
		long seed = 0;
		Object rawSeedVal = this.seed.getValue();
		if (rawSeedVal != null) {
			seed = (long) rawSeedVal;
		}
		if (randomCheckBox.isSelected()) {
			seed = 0;
		}
		generator.setSeed(seed);
	}
	
	/**
	 * Sets the value in the width input box
	 * @param width
	 */
	public void setWidthValue(int width) {
		this.widthInput.setText("");
		this.widthInput.setText("" + width);
	}
	
	/**
	 * Sets the value in the height input box
	 * @param height
	 */
	public void setHeightValue(int height) {
		this.heightInput.setText("");
		this.heightInput.setText("" + height);
	}
	
	/**
	 * Sets the value in the seed input field.
	 * @param seed
	 */
	public void setSeedValue(long seed) {
		this.seed.setText("");
		this.seed.setText("" + seed);
	}
	
	/**
	 * Sets the value for the animation slider.
	 * @param animation speed, number between 0 and 1 indicating the speed.
	 */
	public void setAnimationSliderValue(float animationSpeed) {
		int sliderValue = Math.round(animationSpeed * sliderMax);
		this.animationSlider.setValue(sliderValue);
	}
	
	/**
	 * Sets the value for the animation slider.
	 * @param animation speed, number between 0 and 1 indicating the speed.
	 */
	public float getAnimationSliderSpeed() {
		int sliderValue = this.animationSlider.getValue();
		return (float) sliderValue / sliderMax;
	}
	
	/**
	 * Returns the index of the currently set algorithm from the configuration file
	 * @param list
	 * @return int index corresponding to the currently selected item in the list
	 * (index of item from config in given list)
	 * (sets mazetype to dfs as default if value in config is invalid)
	 */
	private int getSelectedMazeTypeIndex(String[] list) {
		int selectedIndex = -1;
		for (int i = 0; i < list.length; i++) {
			String listElement = list[i];
			MazeType mazeType = generator.getMazeType();
			if (listElement.equals(mazeType.toString())) {
				selectedIndex = i;
			}
		}
		if (selectedIndex < 0) {
			selectedIndex = 0;
			generator.setMazeType(MazeType.DFS);
		}
		return selectedIndex;
	}
	
	/**
	 * Initializes the mazeCanvas with given sizes
	 * @param blocksize, an integer, the width in pixels of each corridor
	 * @param wallWidth, an integer, the width in pixels of each wall
	 */
	private void initCanvas(int blocksize, int wallWidth) {
		this.canvas = new MazeDrawerCanvas(blocksize, wallWidth);
		this.canvas.setSize(width, height);
		this.canvas.setPreferredSize(new Dimension(width, height));
		this.container.add(this.canvas);
	}
	
	/**
	 * Centers the window on the screen such that it is in roughly the same location each time
	 * regardless of the maze size.
	 */
	private void centerScreen() {
		// Dimensions of users screen
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowWidth = this.width + settingsWidth;
		int windowHeight = this.height;
		// sets location to center of screen
		this.frame.setLocation((screenWidth-windowWidth)/2, (screenHeight-windowHeight)/2);
	}
	
	/**
	 * Calculate the size in pixels for each block on the screen, i.e. corridor width/height
	 * @param widthInBlocks
	 * @param heightInBlocks
	 * @return blocksize, an integer
	 */
	private int calculateBlocksize(int widthInBlocks, int heightInBlocks) {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int actualWidthInBlocks = widthInBlocks*2-1 + 2;
		int actualHeightInBlocks = heightInBlocks*2-1 + 2;
		
		int preferredWidth = screenWidth*2/3;
		int preferredHeight = screenHeight*4/5;
		
		int blocksizeFromWidth = preferredWidth/actualWidthInBlocks;
		int blocksizeFromHeight = preferredHeight/actualHeightInBlocks;
		int blocksize = blocksizeFromHeight;
		if (blocksizeFromWidth*actualWidthInBlocks < preferredWidth &&
				blocksizeFromWidth < blocksizeFromHeight) {
			blocksize = blocksizeFromWidth;
		}
		return blocksize;
	}

	/**
	 * Calculate the width of the maze canvas in pixels
	 * @param widthInBlocks
	 * @param blocksize
	 * @return width, an integer
	 */
	private int calculateWidth(int widthInBlocks, int blocksize) {
		int actualWidthInBlocks = widthInBlocks*2-1 + 2;
		int width = actualWidthInBlocks*blocksize;	// width in pixels
		return width;
	}
	
	/**
	 * Calculate the height of the maze canvas in pixels
	 * @param heightInBlocks
	 * @param blocksize
	 * @return height, an integer
	 */
	private int calculateHeight(int heightInBlocks, int blocksize) {
		int actualHeightInBlocks = heightInBlocks*2-1 + 2;
		int height = actualHeightInBlocks*blocksize; // height in pixels
		return height;
	}
	
	/**
	 * Deactivates the button to generate mazes.
	 */
	public void deactivateGenerationBtn() {
		btnGenerate.setEnabled(false);
		btnAbort.setEnabled(true);
	}
	
	/**
	 * Activates the button to generate mazes.
	 */
	public void activateGenerationBtn() {
		btnGenerate.setEnabled(true);
		btnAbort.setEnabled(false);
	}
	
	/**
	 * Adds the appropriate action listeners for each UI element
	 */
	public void addActionListeners() {
		ActionListenerGenerate alg = new ActionListenerGenerate(this, generator);
		btnGenerate.addActionListener(alg);
		btnAbort.addActionListener(alg);
		randomCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (randomCheckBox.isSelected()) {
					seed.setEditable(false);
				} else {
					seed.setEditable(true);
				}
			}
		});
		animationCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (animationCheckBox.isSelected()) {
					generator.setAnimation(true);
				} else {
					generator.setAnimation(false);
				}
			}
		});
		addSelectAllOnFocusActionListener(seed);
		addSelectAllOnFocusActionListener(widthInput);
		addSelectAllOnFocusActionListener(heightInput);
		addSliderChangedListener(this.animationSlider);
		addKeyListener();
	}
	
	/**
	 * Adds a key listener to the program such that keypresses can be registered
	 * and handled accordingly regardless of where the user's focus is.
	 */
	public void addKeyListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
			.addKeyEventDispatcher(new KeyEventDispatcher() {
				@Override
				public boolean dispatchKeyEvent(KeyEvent e) {
					// Only events of key pressed down, not released etc.
					if (KeyEvent.KEY_PRESSED == e.getID()) {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_ESCAPE:
							btnAbort.doClick();
							break;
						case KeyEvent.VK_ENTER:
							btnGenerate.doClick();
							break;
						case KeyEvent.VK_R:
							btnAbort.doClick();
							btnGenerate.doClick();
							break;
						default:
							break;
						}
					}
					return false;
				}
			});
	}
	
	/**
	 * Adds a changed listener to the slider to call a function every time
	 * the slider is changed.
	 * @param slider
	 */
	public void addSliderChangedListener(JSlider slider) {
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				generator.setAnimationSpeed(getAnimationSliderSpeed());
			}
		});
	}
	
	/**
	 * Selects all text in a given text field when that text field gets focus.
	 * @param field
	 */
	public void addSelectAllOnFocusActionListener(JTextField field) {
		field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				field.select(0, 0);
			}

			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						field.selectAll();
					}
				});
			}
		});
	}
	
	/**
	 * Adds the action listener for the dropdown box setting the selected generation algorithm
	 * @param cbAlgorithms
	 */
	public void addCbAlgorithmsActionListener(JComboBox<String> cbAlgorithms) {
		ActionListener cbActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) cbAlgorithms.getSelectedItem();
				MazeType mazeType = MazeType.parseString(selectedItem);
				generator.setMazeType(mazeType);
			}
		};
		cbAlgorithms.addActionListener(cbActionListener);
	}

	/**
	 * Pauses execution for the delay duration 
	 * specified in the delayDuration variable
	 */
	public void delay() {
		try {
			Thread.sleep(animationDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears the canvas and sets it to the wall-color
	 */
	public void clearCanvas() {
		int val = 1;
		int[] col = new int[height];
		Arrays.fill(col, val);
		int[][] bitmap = new int[width][height];
		Arrays.fill(bitmap, col);
		updateMaze(bitmap);
	}
	
	/**
	 * Generates a bitmap of pixels which represents the maze's corridors and walls.
	 * The numbers in the bitmap represent different characteristics of the maze.
	 * <ul>
	 *  <li>0: a corridor in the maze</li>
	 *  <li>1: a wall in the maze</li>
	 * </ul>
	 * @param maze instance with walls and nodes
	 * @return bitmap of type int[][]
	 */
	public static int[][] generateBitmap(Maze maze) {
		int width = maze.getWidth()*2 - 1;
		int height = maze.getHeight()*2 - 1;
		int[][] bitmap = new int[width][height];
		bitmap = addNodes(bitmap, maze.getNodesIterator());
		bitmap = addWalls(bitmap, maze.getWallsIterator());
		bitmap = addCornerWalls(bitmap);
		bitmap = addBorderWalls(bitmap);
		return bitmap;
	}

	/**
	 * Adds the nodes or 'rooms' in the maze with their given values.
	 * If a node is not present in the given iterable, it is given the value of 0
	 * @param bitmap of type int[][]
	 * @param nodes iterable with the nodes from the maze with specified values
	 * @return bitmap of type int[][] with the nodes as specified int the maze's node-collection
	 */
	public static int[][] addNodes(int[][] bitmap, Iterable<Node> nodes) {
		for (Node node : nodes) {
			int x = node.getX()*2;
			int y = node.getY()*2;
			bitmap[x][y] = node.getValue();
		}
		return bitmap;
	}
	
	/**
	 * Adds the inner walls to the maze. These walls are specified in the maze-instance's 
	 * walls-collection.
	 * @param bitmap of type int[][]
	 * @param walls iterable with the walls from the maze
	 * @return bitmap of type int[][] with the walls as specified in the maze's wall-collection
	 */
	private static int[][] addWalls(int[][] bitmap, Iterable<Wall> walls) {
		for (Wall wall : walls) {
			int x1 = wall.getA().getX()*2;
			int y1 = wall.getA().getY()*2;
			int x2 = wall.getB().getX()*2;
			int y2 = wall.getB().getY()*2;
			int wallX = (x1 + x2) / 2;
			int wallY = (y1 + y2) / 2;
			bitmap[wallX][wallY] = 1;
		}
		return bitmap;
	}
	
	/**
	 * Adds the corner walls in the maze
	 * @param bitmap of type int[][]
	 * @return bitmap of type int[][] with corner walls (1s) at every node with only odd coords.
	 */
	private static int[][] addCornerWalls(int[][] bitmap) {
		for (int y = 0; y < bitmap[0].length; y++) {
			for (int x = 0; x < bitmap.length; x++) {
				if (x % 2 != 0 && y % 2 != 0) {
					bitmap[x][y] = 1;
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * Adds the outer walls to the maze
	 * @param bitmap of type int[][]
	 * @return bitmap of type int[][] with border walls (1s) at around the edges 
	 */
	private static int[][] addBorderWalls(int[][] bitmap) {
		int w = bitmap.length+2;
		int h = bitmap[0].length+2;
		int[][] outbitmap = new int[w][h];
		for (int y = 0; y < outbitmap[0].length; y++) {
			for (int x = 0; x < outbitmap.length; x++) {
				if ((y == 0 || y == outbitmap[0].length-1) || 
						(x == 0 || x == outbitmap.length-1)) {
					outbitmap[x][y] = 1;
				} else {
					outbitmap[x][y] = bitmap[x-1][y-1];
				}
			}
		}
		return outbitmap;
	}
	
	/**
	 * Gives a string representation of the maze.
	 * @param bitmap of type int[][] which contains the maze's walls, borders and nodes.
	 * @return string representation of the given bitmap, aka. maze.
	 */
	public static String bitmapToString(int[][] bitmap) {
		String outString = "";
		for (int y = 0; y < bitmap[0].length; y++) {
			for (int x = 0; x < bitmap.length; x++) {
				outString += bitmap[x][y] + " ";
			}
			outString += "\n";
		}
		return outString;
	}
	
	/**
	 * Updates the canvas with the given maze
	 * @param maze, instance of Maze.
	 */
	public void updateMaze(Maze maze) {
		int[][] bitmap = generateBitmap(maze);
		updateMaze(bitmap);
	}
	
	/**
	 * Updates the canvas with the given maze
	 * @param maze bitmap of type int[][].
	 */
	public void updateMaze(int[][] bitmap) {
		canvas.setBitmap(bitmap);
		canvas.repaint();
	}
	
}


















