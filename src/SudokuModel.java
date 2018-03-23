
public class SudokuModel {
	private static final String[] DIFFICULTIES = {"80571400010200003800003000776418000000026078"
            + "0008000500451678309080050600003001005", "0000200800300005000567080000800060001050008"
            + "03000090020000450039020007000700000040", "000078005000400060706009000002300050910000700"
            + "000080203090030000005000009000100080", "0042900707000000000000100900300000894076001"
            + "00050038000500003602000002001006000000"};
	private int[][] board;
	private int filledPanels;
	
	public static final int SQUARES = 3;
	public static final int GRID_SIZE = SQUARES*SQUARES;
	public static final int HIGHEST_DIFFICULTY = DIFFICULTIES.length - 1;
	
	public SudokuModel(int difficulty) {
		if(difficulty < 0 || difficulty >= DIFFICULTIES.length) {
			throw new IllegalArgumentException("invalid difficulty level");
		}
		board = new int[GRID_SIZE][GRID_SIZE];
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				board[r][c] = DIFFICULTIES[difficulty].charAt(r*board.length + c) - '0';
			}
		}
	}
	
	public boolean safeToPlace(int row, int col, int num) {
		if(!inBounds(row, col) || !validNumber(num)) {
			throw new IllegalArgumentException("invalid params");
		}
		return safeToPlaceHorizontally(row, num) || safeToPlaceVertically(col, num) || 
				safeToPlaceWithinSquare((row/SQUARES)*SQUARES, (col/SQUARES)*SQUARES, num);
	}
	
	private boolean safeToPlaceHorizontally(int row, int num) {
		return false;
	}
	
	private boolean safeToPlaceVertically(int col, int num) {
		return false;
	}
	
	private boolean safeToPlaceWithinSquare(int topLeftRow, int topLeftCol, int num) {
		return false;
	}
	
	private boolean inBounds(int row, int col) {
		return false;
	}
	
	private boolean validNumber(int num) {
		return num < 1 || num > GRID_SIZE;
	}
	
	public void place(int row, int col, int num) {
		if(!safeToPlace(row, col, num)) {
			throw new IllegalArgumentException("not safe to place");
		}
		filledPanels++;
	}
	
	public void remove(int row, int col) {
		if(!inBounds(row, col)) {
			throw new IllegalArgumentException("invalid params");
		} else if(board[row][col] == 0) {
			throw new IllegalArgumentException("no number here");
		}
		board[row][col] = 0;
		filledPanels--;
	}
	
	public boolean gameOver() {
		return filledPanels == GRID_SIZE*GRID_SIZE;
	}
}
