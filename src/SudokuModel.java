
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
		if(!inBounds(row, col) || num < 1 || num > GRID_SIZE) {
			throw new IllegalArgumentException("invalid params");
		}
		return false;
	}
	
	private boolean inBounds(int row, int col) {
		return false;
	}
	
	public void place(int row, int col, int num) {
		
	}
}
