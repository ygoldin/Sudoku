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
	
	public SudokuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Sudoku");
		
		sudokuModel = new SudokuModel(0);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(SudokuModel.GRID_SIZE, SudokuModel.GRID_SIZE));
		mainGridButtons = new MainGridButton[SudokuModel.GRID_SIZE][SudokuModel.GRID_SIZE];
		for(int r = 0; r < SudokuModel.GRID_SIZE; r++) {
			for(int c = 0; c < SudokuModel.GRID_SIZE; c++) {
				mainGridButtons[r][c] = new MainGridButton(r, c);
				buttonPanel.add(mainGridButtons[r][c]);
			}
		}
		add(buttonPanel);
	}
	
	private class MainGridButton extends JButton {
		public MainGridButton(int row, int col) {
			
		}
	}
}
