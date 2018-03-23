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
	private Color[] GAME_COLORS = {Color.BLUE};
	
	public SudokuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Sudoku");
		sudokuModel = new SudokuModel(0);
		
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
	}
	
	private class MainGridButton extends JButton {
		private static final int FONT_SIZE = 40;
		private static final String FONT_NAME = "Arial";
		private static final int SQUARE_BOUNDARY_BORDER_WIDTH = 4;
		private static final int NORMAL_BORDER_WIDTH = 1;
		
		public MainGridButton(int row, int col) {
			int value = sudokuModel.initialSetup().get(row).get(col);
			if(value != 0) {
				setText("" + value);
				setRolloverEnabled(false);
			} else {
				setForeground(GAME_COLORS[0]);
			}
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
			setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		}
	}
	
	private class NumberSelectionGrid extends JComponent {
		public int[] lastSelected;
		private JToggleButton[][] numberSelections;
		
		public NumberSelectionGrid(JPanel numberSelectionPanel) {
			numberSelections = new JToggleButton[SudokuModel.SQUARES][SudokuModel.SQUARES];
			for(int r = 0; r < SudokuModel.SQUARES; r++) {
				for(int c = 0; c < SudokuModel.SQUARES; c++) {
					numberSelections[r][c] = new JToggleButton("" + (r*SudokuModel.SQUARES + c + 1));
					numberSelectionPanel.add(numberSelections[r][c]);
				}
			}
		}
	}
}
