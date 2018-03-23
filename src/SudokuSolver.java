public class SudokuSolver {
	private SudokuModel model;
	
	public SudokuSolver(SudokuModel model) {
		this.model = model;
	}
	
	public void solve() {
		
	}
	
	private void solve(int row, int col) {
		if(model.inBounds(row, col)) {
			
		}
	}
}
