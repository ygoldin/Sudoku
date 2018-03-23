import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * SudokuModel can be used to model a game of Sudoku
 * @author Yael Goldin
 */
public class SudokuModel {
	private int[][] board;
	private List<List<Integer>> initialSetup;
	private int filledSpots;
	private static final String[] INITIAL_BOARD_BY_DIFFICULTY =
		{"805714000102000038000030007764180000000260780008000500451678309080050600003001005",
		"000020080030000500056708000080006000105000803000090020000450039020007000700000040",
		"000078005000400060706009000002300050910000700000080203090030000005000009000100080",
		"004290070700000000000010090030000089407600100050038000500003602000002001006000000"};
	
	public static final int SQUARES = 3;
	public static final int GRID_SIZE = SQUARES*SQUARES;
	public static final int HIGHEST_DIFFICULTY = INITIAL_BOARD_BY_DIFFICULTY.length - 1;
	public static final int SMALLEST_NUMBER = 1;
	public static final int HIGHEST_NUMBER = 9;
	
	/**
	 * initializes the game with a board of the given difficulty
	 * 
	 * @param difficulty The level of difficulty desired (higher number -> higher difficulty)
	 * @throws IllegalArgumentException if 'difficulty' is not in the range [0, HIGHEST_DIFFICULTY]
	 * inclusive
	 */
	public SudokuModel(int difficulty) {
		if(difficulty < 0 || difficulty > HIGHEST_DIFFICULTY) {
			throw new IllegalArgumentException("invalid difficulty level");
		}
		board = new int[GRID_SIZE][GRID_SIZE];
		initialSetup = new ArrayList<List<Integer>>(GRID_SIZE);
		for(int r = 0; r < board.length; r++) {
			List<Integer> row = new ArrayList<Integer>(GRID_SIZE);
			for(int c = 0; c < board[0].length; c++) {
				int cur = INITIAL_BOARD_BY_DIFFICULTY[difficulty].charAt(r*board.length + c) - '0';
				board[r][c] = cur;
				row.add(cur);
				if(cur != 0) {
					filledSpots++;
				}
			}
			initialSetup.add(Collections.unmodifiableList(row));
		}
	}
	
	//a private constructor used to copy the model
	private SudokuModel(SudokuModel original) {
		board = new int[GRID_SIZE][GRID_SIZE];
		initialSetup = Collections.unmodifiableList(original.initialSetup);
		filledSpots = original.filledSpots;
		for(int r = 0; r < GRID_SIZE; r++) {
			for(int c = 0; c < GRID_SIZE; c++) {
				board[r][c] = original.board[r][c];
			}
		}
	}
	
	/**
	 * returns the original board setup
	 * 
	 * @return an unmodifiable list representing the setup of the board at the start of the game
	 * every interior list represents a row, and every value in that list represents the number at
	 * that column in the row
	 */
	public List<List<Integer>> initialSetup() {
		return Collections.unmodifiableList(initialSetup);
	}
	
	/**
	 * checks if the spot has already been filled
	 * 
	 * @param row The row of the spot
	 * @param col The column of the spot
	 * @return true if there is a number in that spot, false otherwise
	 */
	public boolean spotFilled(int row, int col) {
		if(!inBounds(row, col)) {
			throw new IllegalArgumentException("out of bounds");
		}
		return board[row][col] != 0;
	}
	
	/**
	 * checks whether the given number can be placed in the given spot
	 * 
	 * @param row The row of the spot
	 * @param col The column of the spot
	 * @param num The number to be placed
	 * @return true if the number can be placed there, false if not
	 * @throws IllegalArgumentException if the spot is out of bounds or the number is not in the range
	 * [SMALLEST_NUMBER, HIGHEST_NUMBER] inclusive
	 */
	public boolean safeToPlace(int row, int col, int num) {
		if(!validNumber(num)) {
			throw new IllegalArgumentException("invalid number to place");
		}
		return !spotFilled(row, col) && (safeToPlaceHorizontally(row, num)
				|| safeToPlaceVertically(col, num) 
				|| safeToPlaceWithinSquare((row/SQUARES)*SQUARES, (col/SQUARES)*SQUARES, num));
	}
	
	//checks if the number can be placed in that row
	private boolean safeToPlaceHorizontally(int row, int num) {
		for(int c = 0; c < GRID_SIZE; c++) {
			if(board[row][c] == num) {
				return false;
			}
		}
		return true;
	}
	
	//checks if the number can be placed in that column
	private boolean safeToPlaceVertically(int col, int num) {
		for(int r = 0; r < GRID_SIZE; r++) {
			if(board[r][col] == num) {
				return false;
			}
		}
		return true;
	}
	
	//checks if the number can be placed in the 3x3 square that starts at the given spot
	private boolean safeToPlaceWithinSquare(int topLeftRow, int topLeftCol, int num) {
		for(int r = topLeftRow; r < topLeftRow + SQUARES; r++) {
			for(int c = topLeftCol; c < topLeftCol + SQUARES; c++) {
				if(board[r][c] == num) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * checks if the given spot is in bounds in the grid
	 * 
	 * @param row The row of the spot
	 * @param col The column of the spot
	 * @return true if the spot is in bounds, false otherwise
	 */
	public boolean inBounds(int row, int col) {
		return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
	}
	
	//returns if the number is valid (1-9)
	private boolean validNumber(int num) {
		return num >= SMALLEST_NUMBER && num <= HIGHEST_NUMBER;
	}
	
	/**
	 * places the given number in the given spot
	 * 
	 * @param row The row of the spot
	 * @param col The column of the spot
	 * @param num The number to be placed
	 * @throws IllegalStateException if the game is over
	 * @throws IllegalArgumentException if the spot is out of bounds, the number is not in the range
	 * [SMALLEST_NUMBER, HIGHEST_NUMBER] inclusive, or it is not safe to place the number in that spot
	 */
	public void place(int row, int col, int num) {
		if(gameOver()) {
			throw new IllegalStateException("game is over");
		} else if(!safeToPlace(row, col, num)) {
			throw new IllegalArgumentException("not safe to place");
		}
		board[row][col] = num;
		filledSpots++;
	}
	
	/**
	 * removes any number from the given spot, making it blank
	 * 
	 * @param row The row of the spot
	 * @param col The column of the spot
	 * @throws IllegalStateException if the game is over
	 * @throws IllegalArgumentException if the spot is out of bounds, there is no number in that spot, or
	 * that spot was initialized with a number at the start of the game
	 */
	public void remove(int row, int col) {
		if(gameOver()) {
			throw new IllegalStateException("game is over");
		} else if(!inBounds(row, col)) {
			throw new IllegalArgumentException("invalid params");
		} else if(board[row][col] == 0) {
			throw new IllegalArgumentException("no number here");
		} else if(initialSetup.get(row).get(col) != 0) {
			throw new IllegalArgumentException("cannot remove number from initial setup");
		}
		board[row][col] = 0;
		filledSpots--;
	}
	
	/**
	 * checks if the game is over and the board has been filled
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean gameOver() {
		return filledSpots == GRID_SIZE*GRID_SIZE;
	}
	
	/**
	 * creates a copy of the current model. The initial setup of the copy will be the initial setup of
	 * the current model, not the state of the model at the time of copying
	 * 
	 * @return the copy of the model
	 */
	public SudokuModel copy() {
		return new SudokuModel(this);
	}
	
	/**
	 * returns the String representation of the model
	 */
	@Override
	public String toString() {
		String result = "";
		for(int r = 0; r < GRID_SIZE; r++) {
			result += "[" + board[r][0];
			for(int c = 1; c < GRID_SIZE; c++) {
				result += ", " + board[r][c];
			}
			result += "]";
			if(r != GRID_SIZE - 1) {
				result += "\n";
			}
		}
		return result;
	}
}
