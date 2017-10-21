package Sudoku;

import java.util.Scanner;
import java.awt.*;

public class Sudoku {

    private int[][] board;
    private Scanner input;
    private static final String easy = "80571400010200003800003000776418000000026078"
            + "0008000500451678309080050600003001005";
    private static final String medium = "0000200800300005000567080000800060001050008"
            + "03000090020000450039020007000700000040";
    private static final String hard = "000078005000400060706009000002300050910000700"
            + "000080203090030000005000009000100080";
    private static final String expert = "0042900707000000000000100900300000894076001"
            + "00050038000500003602000002001006000000";
    private int filledSquares;
    private static final int SIZE = 700;
    private Graphics graphics;

    public Sudoku() {
        this.board = new int[9][9];
        this.input = new Scanner(System.in);
        this.filledSquares = 0;
        String choice = "";
        while (choice.length() == 0) {
            System.out.print("Do you want easy(1), medium(2), hard(3), or "
                             + "expert(4)? ");
            String text = this.input.nextLine().toLowerCase();
            if (text.startsWith("1")) {
                choice = this.easy;
            } else if (text.startsWith("2")) {
                choice = this.medium;
            } else if (text.startsWith("3")) {
                choice = this.hard;
            } else if(text.startsWith("4")){
                choice = this.expert;
            }
        }

        int i = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int value = choice.charAt(i) - '0';
                this.board[r][c] = value;
                i++;
                if(value != 0){
                    this.filledSquares++;
                }
            }
        }
        DrawingPanel panel = new DrawingPanel(SIZE, SIZE + 50);
        this.graphics = panel.getGraphics();
        this.graphics.setFont(new Font("Title", Font.BOLD, 40));
        this.graphics.drawString("SUDOKU", SIZE/2 - 85, 40);
        this.graphics.fillRect(5,50,SIZE-10,SIZE-10);
        this.graphics.setColor(Color.WHITE);
        this.graphics.fillRect(10,55,SIZE-20,SIZE-20);
        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(10 + (SIZE-30)/3, 55, 5, SIZE-20);
        this.graphics.fillRect(10 + 2*(SIZE-30)/3 + 5, 55, 5, SIZE-20);
        this.graphics.fillRect(10, 55 + (SIZE-30)/3, SIZE-20, 5);
        this.graphics.fillRect(10, 55 + 2*(SIZE-30)/3 + 5, SIZE-20, 5);
        //this.drawGrid(false);
        this.drawGrid(true);
        for(int start = 0; start < 3; start++){
            this.graphics.fillRect(10 + start*(2*(SIZE-30)/3 + 5), 55 + (start+1)*(SIZE-52)/9 + i*3, SIZE-20, 2);
            this.graphics.fillRect(10 + (start+1)*(SIZE-52)/9 + i*3, 55+ start*(2*(SIZE-30)/3 + 5), 2, SIZE-20);
        } 
    }
    
    private void drawGrid(boolean isCol){
        if(isCol){
            for(int i = 0; i < 2; i++){
                
            }
            
            //this.graphics.fillRect(10 + 2*(end-start)/spaces + lineWidth, 55, lineWidth, SIZE-20);
        } 
    }

    //plays one game of Sudoku
    public void play(){
        while(this.filledSquares < 81){
            this.playOnce();
        }
    }
    
    //plays one round of one game (i.e. putting one number in or removing one)
    private void playOnce(){
        this.print();
        int row = this.getNum("row") - 1;
        int col = this.getNum("column") - 1;
        System.out.print("Do you want to put a number in (y) or remove it (n)? ");
        char answer = this.input.nextLine().charAt(0);
        if(answer == 'y' || answer == 'Y'){
            int num = this.getNum("number you want to put there");
            if(!isLegal(row, col, num)){
                System.out.println("That is an invalid move. Try again.");
                this.playOnce();
            } else {
                this.board[row][col] = num;
                this.filledSquares++;
            }
        } else { //remove
            this.board[row][col] = 0;
            this.filledSquares--;
        }
    }
    
    
    //asks the user for a number until they enter 1-9 and then returns it
    private int getNum(String coordinate){
        int cur = -1;
        while(cur < 1 || cur > 9){
            System.out.print("Enter a " + coordinate + " (1-9): ");
            String text = this.input.nextLine();
            if(text.length() == 1){
                cur = text.charAt(0) - '0';
            }
        }
        return cur;
    }
    
    //returns if it's legal to put "num" in that specific spot on the board
    //first checking if there is a number there already and then using the game rules
    private boolean isLegal(int row, int col, int num){
        //checks if a number exists in that spot
        if(this.board[row][col] != 0){
            return false;
        }
        
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
    
    //prints the board
    public void print(){
        String result = "";
        for(int r = 0; r < 9; r++){
            result += "[";
            for(int c = 0; c < 8; c++){
                result += this.board[r][c] + ", ";
            }
            result += this.board[r][8] + "]\n";
        }
        System.out.println(result);
    }

}
