import javax.swing.*;
import java.awt.*;

/**
 * SudokuFrame can be used to control and display a game of Sudoku
 * @author Yael Goldin
 */
@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {
	private SudokuModel sudokuModel;
	private MainGridButton[][] mainGridButtons;
	private NumberSelectionGrid numberSelectionGrid;
	private JButton help;
	private JButton newGame;
	private static final Color[] GAME_COLORS = {Color.BLUE, Color.RED};
	private static final String[] DIFFICULTIES = {"EASY", "MEDIUM", "HARD", "EXPERT"};
	private static final int DEFAULT_DIFFICULTY = 0;
	
	public SudokuFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Sudoku");
		
		
		
		JPanel mainGridButtonPanel = new JPanel();
		mainGridButtonPanel.setLayout(new GridLayout(SudokuModel.GRID_SIZE, SudokuModel.GRID_SIZE));
		mainGridButtons = new MainGridButton[SudokuModel.GRID_SIZE][SudokuModel.GRID_SIZE];
		for(int r = 0; r < SudokuModel.GRID_SIZE; r++) {
			for(int c = 0; c < SudokuModel.GRID_SIZE; c++) {
				mainGridButtons[r][c] = new MainGridButton(r, c);
				mainGridButtonPanel.add(mainGridButtons[r][c]);
			}
		}
		add(mainGridButtonPanel);
		
		JPanel numberSelectionPanel = new JPanel();
		numberSelectionPanel.setLayout(new GridLayout(SudokuModel.SQUARES, SudokuModel.SQUARES));
		numberSelectionGrid = new NumberSelectionGrid(numberSelectionPanel);
		add(numberSelectionPanel, BorderLayout.SOUTH);
		
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		help = new JButton("Help");
		menu.add(help);
		help.addActionListener(e -> {
			String message = "Click on a number in the bottom grid to select it\n";
			message += "Then click on a square in the main grid to place the number there\n";
			message += "Numbers that were legally placed show up blue, illegal ones show up red\n";
			message += "To remove a number from a square, have no number in the bottom grid selected";
			message += " and click on the square";
			JOptionPane.showMessageDialog(this, message);
		});
		newGame = new JButton("New Game");
		menu.add(newGame);
		newGame.addActionListener(e -> {
			newGame();
		});
		
		pack();
		setVisible(true);
		
		pickDifficulty();
		for(int r = 0; r < SudokuModel.GRID_SIZE; r++) {
			for(int c = 0; c < SudokuModel.GRID_SIZE; c++) {
				mainGridButtons[r][c].setInitialValueVisible();
			}
		}
	}
	
	private void pickDifficulty() {
		int response = JOptionPane.showOptionDialog(this, "Choose a difficulty:", "Difficulty Selection",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DIFFICULTIES, 
				DIFFICULTIES[DEFAULT_DIFFICULTY]);
		if(response < 0) {
			response = DEFAULT_DIFFICULTY;
		}
		sudokuModel = new SudokuModel(response);
	}
	
	private void newGame() {
		numberSelectionGrid.unselectLastSelected();
		pickDifficulty();
		for(int r = 0; r < SudokuModel.GRID_SIZE; r++) {
			for(int c = 0; c < SudokuModel.GRID_SIZE; c++) {
				int initialValue = sudokuModel.initialSetup().get(r).get(c);
				if(initialValue != 0) {
					mainGridButtons[r][c].setText("" + initialValue);
					mainGridButtons[r][c].setRolloverEnabled(false);
				} else {
					mainGridButtons[r][c].setText(null);
				}
			}
		}
	}
	
	private void gameOverActions() {
		if(JOptionPane.showConfirmDialog(this, "Victory!", "Play again?", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION) { //play again
			newGame();
		}
	}
	
	private class MainGridButton extends JButton {
		private static final int FONT_SIZE = 40;
		private static final String FONT_NAME = "Arial";
		private static final int SQUARE_BOUNDARY_BORDER_WIDTH = 4;
		private static final int NORMAL_BORDER_WIDTH = 1;
		private int initialValue;
		private int row;
		private int col;
		
		public MainGridButton(int row, int col) {
			this.row = row;
			this.col = col;
			setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			//set background with the 3x3 outer squares getting a thicker border
			setBorder(row, col);
			//action listener for setting values in buttons
			addActionListener(e -> {
				if(!sudokuModel.gameOver() && initialValue == 0) { //only change noninitial values
					int[] lastSelectedNumber = numberSelectionGrid.lastSelected;
					if(lastSelectedNumber == null) { //remove
						if(sudokuModel.spotFilled(row, col)) {
							sudokuModel.remove(row, col);
						}
						setText(null);
					} else { //place
						if(sudokuModel.spotFilled(row, col)) {
							sudokuModel.remove(row, col);
						}
						int valueToPlace = numberSelectionGrid.numberSelections[lastSelectedNumber[0]]
								[lastSelectedNumber[1]].value;
						if(sudokuModel.safeToPlace(row, col, valueToPlace)) {
							sudokuModel.place(row, col, valueToPlace);
							setForeground(GAME_COLORS[0]);
							setText("" + valueToPlace);
							if(sudokuModel.gameOver()) {
								gameOverActions();
							}
						} else {
							setForeground(GAME_COLORS[1]);
							setText("" + valueToPlace);
						}
					}
				}
			});
		}
		
		public void setInitialValueVisible() {
			initialValue = sudokuModel.initialSetup().get(row).get(col);
			if(initialValue != 0) {
				setText("" + initialValue);
				setRolloverEnabled(false);
			}
		}
		
		private void setBorder(int row, int col) {
			if(row == SudokuModel.SQUARES - 1 || row == SudokuModel.SQUARES*2 - 1) {
				if(col == SudokuModel.SQUARES - 1 || col == SudokuModel.SQUARES*2 - 1) {
					setBorder(BorderFactory.createMatteBorder(NORMAL_BORDER_WIDTH, NORMAL_BORDER_WIDTH,
							SQUARE_BOUNDARY_BORDER_WIDTH, SQUARE_BOUNDARY_BORDER_WIDTH, Color.BLACK));
				} else {
					setBorder(BorderFactory.createMatteBorder(NORMAL_BORDER_WIDTH, NORMAL_BORDER_WIDTH,
							SQUARE_BOUNDARY_BORDER_WIDTH, NORMAL_BORDER_WIDTH, Color.BLACK));
				}
				
			} else if(col == SudokuModel.SQUARES - 1 || col == SudokuModel.SQUARES*2 - 1) {
				setBorder(BorderFactory.createMatteBorder(NORMAL_BORDER_WIDTH, NORMAL_BORDER_WIDTH,
						NORMAL_BORDER_WIDTH, SQUARE_BOUNDARY_BORDER_WIDTH, Color.BLACK));
			} else {
				setBorder(BorderFactory.createMatteBorder(NORMAL_BORDER_WIDTH, NORMAL_BORDER_WIDTH,
						NORMAL_BORDER_WIDTH, NORMAL_BORDER_WIDTH, Color.BLACK));
			}
		}
	}
	
	private class NumberSelectionGrid extends JComponent {
		public int[] lastSelected;
		public NumberSelection[][] numberSelections;
		
		public NumberSelectionGrid(JPanel numberSelectionPanel) {
			super();
			numberSelections = new NumberSelection[SudokuModel.SQUARES][SudokuModel.SQUARES];
			for(int r = 0; r < SudokuModel.SQUARES; r++) {
				for(int c = 0; c < SudokuModel.SQUARES; c++) {
					numberSelections[r][c] = new NumberSelection(r, c);
					numberSelectionPanel.add(numberSelections[r][c]);
				}
			}
		}
		
		public void unselectLastSelected() {
			assert(lastSelected != null);
			numberSelections[lastSelected[0]][lastSelected[1]].deselect();
		}
		
		private class NumberSelection extends JButton {
			public int value;
			private final Color UNSELECTED = Color.WHITE;
			private final Color SELECTED = Color.GREEN;
			
			public NumberSelection(int row, int col) {
				super();
				value = row*SudokuModel.SQUARES + col + 1;
				setText("" + value);
				setBackground(UNSELECTED);
				addActionListener(e -> {
					if(lastSelected != null) {
						if(lastSelected[0] == row && lastSelected[1] == col) { //unselect this button
							deselect();
						} else { //unselect another and select this
							unselectLastSelected();
							setBackground(SELECTED);
							lastSelected = new int[] {row, col};
						}
					} else {
						setBackground(SELECTED);
						lastSelected = new int[] {row, col};
					}
				});
			}
			
			public void deselect() {
				setBackground(UNSELECTED);
				lastSelected = null;
			}
		}
	}
}
