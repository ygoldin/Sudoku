/**
 * SudokuSolved can solve a Sudoku board
 * @author Yael Goldin
 */
public class SudokuSolver {
	public SudokuModel model;
	
	/**
	 * creates a solver over the given model (NOTE: 'model' parameter will not be altered)
	 * @param model The model to solve
	 */
	public SudokuSolver(SudokuModel model) {
		this.model = model.copy();
	}
	
	/**
	 * solves the sudoku board
	 */
	public void solve() {
		solve(0, 0);
	}
	
	//uses recursive backtracking to attempt every move
	private void solve(int row, int col) {
		if(model.inBounds(row, col)) {
			int[] nextSpot = nextSpot(row, col);
			if(model.spotFilled(row, col)) {
				solve(nextSpot[0], nextSpot[1]);
			} else {
				for(int i = SudokuModel.SMALLEST_NUMBER; i <= SudokuModel.HIGHEST_NUMBER; i++) {
					if(model.safeToPlace(row, col, i)) {
						model.place(row, col, i);
						solve(nextSpot[0], nextSpot[1]);
						model.remove(row, col);
					}
				}
			}
		}
	}
	
	//returns the next spot to look at after the given one
	private int[] nextSpot(int row, int col) {
		if(col < SudokuModel.GRID_SIZE - 1) {
			return new int[] {row, col + 1};
		} else {
			return new int[] {row + 1, 0};
		}
	}
}
