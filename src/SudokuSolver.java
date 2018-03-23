public class SudokuSolver {
    
    private int[][] board;
    private boolean[][] lookedAt;
    
    //creates a SudokuSolver from the array that represents a Sudoku board
    //if the board is not 9x9, throws IllegalArgumentException
    //assumes all values in the board are valid for a normal Sudoku game
    public SudokuSolver(int[][] board){
        if(board.length != 9 || board[0].length != 9){
            throw new IllegalArgumentException("Invalid board size");
        }
        this.board = new int[9][9];
        //copies all values over into the new array
        //if a spot contains a 0, it's considered blank
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                this.board[r][c] = board[r][c];
            }
        }
        this.lookedAt = new boolean[9][9];
    }
    
    public int[][] solve(){
        this.solve(0,0);
        String result = "";
        for(int r = 0; r < 9; r++){
            result += "[";
            for(int c = 0; c < 8; c++){
                result += this.board[r][c] + ", ";
            }
            result += this.board[r][8] + "]\n";
        }
        System.out.print(result);
        
        
        
        return this.board; 
    }
    
    
    private void solve(int row, int col){
        if(row > 8 || col > 8){
            return;
        }
        //you havent looked at it yet and it doesn't have a number
        if(!this.lookedAt[row][col] && this.board[row][col] == 0){
            this.lookedAt[row][col] = true;
            for(int i = 1; i <= 9; i++){
                if(isLegal(row, col, i)){
                    this.board[row][col] = i;
                    this.solve(row, col + 1);
                    this.solve(row + 1, col);
                }
            }
            
        }
    }
    
    //returns if it's legal to put "num" in that specific spot on the board
    private boolean isLegal(int row, int col, int num){
        //checks if the number already exists in that row
        for(int c = 0; c < 9; c++){
            if(this.board[row][c] == num){
                return false;
            }
        }
        //checks if the number already exists in that column
        for(int r = 0; r < 9; r++){
            if(this.board[r][col] == num){
                return false;
            }
        }
        //checks if the number already exists in that 3x3 square
        int[] squareOf9 = this.findSquare(row, col);
        for(int i = squareOf9[0]; i < squareOf9[0] + 3; i++){
            for(int j = squareOf9[1]; i < squareOf9[1] + 3; i++){
                if(this.board[i][j] == num){
                    return false;
                }
            }
        }
        return true;
    }
    
    //finds the 3x3 square that spot lies in
    //returns array with upper left corner of the square
    //array[0] is row number, array[1] is col number
    //i.e. if the spot was in the middle 3 rows and last 3 columns,
    //it would return [3,6]
    private int[] findSquare(int row, int col) {
        int[] square = new int[2];
        if(row < 3){
            if(col < 3){
                square[0] = 0;
                square[1] = 0;
            } else if(col < 6){
                square[0] = 0;
                square[1] = 3;
            } else {
                square[0] = 0;
                square[1] = 6;
            }
        } else if(row < 6){
            if(col < 3){
                square[0] = 3;
                square[1] = 0;
            } else if(col < 6){
                square[0] = 3;
                square[1] = 3;
            } else {
                square[0] = 3;
                square[1] = 6;
            }
        } else {
            if(col < 3){
                square[0] = 6;
                square[1] = 0;
            } else if(col < 6){
                square[0] = 6;
                square[1] = 3;
            } else {
                square[0] = 6;
                square[1] = 6;
            }
        }
        return square;
    }
}
