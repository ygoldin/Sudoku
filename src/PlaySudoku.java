import java.awt.EventQueue;

public class PlaySudoku {
    
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				SudokuFrame sudoku = new SudokuFrame();
				sudoku.pack();
				sudoku.setVisible(true);
			}
		});        
    }
}
