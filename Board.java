/* File: Board.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */

import java.io.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class Board
{
    //initalize variables 
    private Cell[][] board;
    public static final int SIZE = 9; //final keyboard means the value cannot be changed
    protected boolean finished; //a boolean field

    /*
     * Default constructor does not have any parameters, 
     * creates a new 2D array of Cells that is Board.size
     * by Board.size, and it should intialize each
     * location in the grid with a new Cell withh
     * value 0
     */
    public Board()
    {
      board = new Cell[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                this.board[x][y] = new Cell(x,y,0);
            }
        }
    }

    /*
     * A constructor that takes a String filename and calls
     * read on that filename to fill the board
     */
    public Board(String filename)
    {
      board = new Cell[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                this.board[x][y] = new Cell(x, y, 0);
            }
        }
      this.read(filename);
    }

    /*
     * A constructor that only takes an int parameter
     * to specify the number of inital cells to be locked
     */
    public Board(int lockedCells)
    {
      int count = 0;
      Random ran = new Random();
      this.board = new Cell[SIZE][SIZE];
      for (int x = 0; x < SIZE; x++)
      {
          for (int y = 0; y < SIZE; y++)
          {
              this.board[x][y] = new Cell(x, y, 0);
          }
      }

      while(count < lockedCells){

        // System.out.println(count);
        // pick a random cell
        Cell cell = this.board[ran.nextInt(SIZE)][ran.nextInt(SIZE)];

        // pick a random value
        int val = ran.nextInt(9) + 1;

        // if this cell doesnt already have a value and if this value is valid for it currently
        if (cell.getValue() == 0 && validValue(cell.getRow(), cell.getCol(), val))
        {
          // give it this value
          cell.setValue(val);
          cell.setLocked(true);

          //to increase count
          count++;
        }
      }
    }

    /*
     * Generates a multi-line string representation of the board
     */
    public String toString()
    {
         //to return
         String result = "";

         //to create a grid-look for the Landscape
         for (int r = 0; r < SIZE; r++)
         {
             for (int c = 0; c < SIZE; c++)
             {
                result += this.board[r][c] + " ";

                if (c % 3 == 2)
             {
                result += "\t";
             }
             } result += "\n";

             if (r % 3 == 2)
             {
                result += "\n";
             }
         }
 
         return result;
 
    }

    /*
     * Creating a read function that reads data from a file
     */
    public boolean read(String filename) {
        try {
          // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
          FileReader fr = new FileReader(filename);

          // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
          BufferedReader br = new BufferedReader(fr);
    
          // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
          String line = br.readLine();

          //have a counter for row
          int r = 0;

          // start a while loop that loops while line isn't null
          while(line != null)
          {
              // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
              String[] temp = line.split("[ ]+");
              
              //if there is a space between each line.. should move forward
              if (temp.length < 9)
              {
                line = br.readLine();
                continue;
              }

              //to add the numbers into the Cells
              for (int c = 0; c < SIZE; c++)
              {
                this.board[r][c].setValue(Integer.parseInt(temp[c]));
                if (!temp[c].equals("0")) this.board[r][c].setLocked(true);
              }

              // print the String (line)
              //System.out.println(line);


              // print the size of the String array (you can use .length)
              // System.out.println(temp.length);

              r++;

              // assign to line the result of calling the readLine method of your BufferedReader object.
              line = br.readLine();
          }
          // call the close method of the BufferedReader
          br.close();
          return true;
        }
        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    
        return false;
      }

      /*
       * returns the number of columns
       */
      public int getCols()
      {
        return SIZE;
      }

      /*
       * returns the number of rows
       */
      public int getRows()
      {
        return SIZE;
      }

      /*
       * returns the Cell at location r, c
       */
      public Cell get(int r, int c)
      {
        return this.board[r][c];
      }

      /*
       * returns whether the Cell at r, c, is locked
       */
      public boolean isLocked(int r, int c)
      {
        return this.board[r][c].isLocked();
      }

      /*
       * returns the number of locked Cells on the board
       */
      public int numLocked()
      {
        int count = 0;
        for (int r = 0; r < SIZE; r++)
        {
          for (int c = 0; c < SIZE; c++)
          {
            if (this.board[r][c].isLocked())
            {
              count++;
            }
          }
        }

        return count;
      }

      /*
       * returns the value at Cell r,c
       */
      public int value(int r, int c)
      {
        return this.board[r][c].getValue();
      }

      /*
       * sets the value of the Cell at r,c
       */
      public void set(int r, int c, int value)
      {
        this.board[r][c].setValue(value);
      }

      /*
       * sets the value and locked fields of the Cell at r, c
       */
      public void set(int r, int c, int value, boolean locked)
      {
        this.board[r][c].setValue(value);
        this.board[r][c].setLocked(locked);
      }

      /*
       * Tests if the given value is a valid value at the given 
       * row and column of the board
       * Using integer divison
       */
      public boolean validValue(int row, int col, int value)
      {
        if (value < 1 || value > 9)
        {
          return false;
        }

        for (int r = row/3 * 3; r < row/3 * 3 + 3; r++) //so if row is 1/3 * 3 = 0
        {
          for (int c = col/3 * 3; c < col/3 * 3 + 3; c++) //so if column is 5/3 *3  = 3
          {
            //if r = row and c = col then it should skip
            //if there is a repeated number in a 3x3 grid
            if (!(r == row && c == col) && this.board[r][c].getValue()==value)
            {
              // System.out.println("(" + r + ", " + c+ ")" + board[r][c].getValue());
              return false;
            }

          }
        }

        //now to check the whole row does not have the repeated number
        for (int r = 0; r < SIZE; r++)
        {
          if (r!=row && this.board[r][col].getValue() == value)
          {
            return false;
          }
        }

        //now to check the whole column does not have the repeated number
        for (int c = 0; c < SIZE; c++)
        {
          if (c != col && this.board[row][c].getValue() == value)
          {
            return false;
          }
        }

        return true;

      }

      /*
       * Returns true if the board is solved
       * If any of the Cell values are 0 or are not valid
       * then the board is not solved.
       * If all the cells are between 1 and 9 and all the Cells are valid, the 
       * board is solved
       */
      public boolean validSolution()
      {
         for (int r = 0; r < SIZE; r++)
         {
            for (int c = 0; c < SIZE; c++)
            {
              if (this.board[r][c].getValue() != 0 && this.validValue(r, c, this.value(r, c)))
              {
                continue;
              }
              else
              {
                return false;
              }

            }
         }
         return true;
      }

      /*
       * To scan the board by rows from the upper left to find the first cell
       * with a 0 value. Then start checking the possible values from 1 to 9
       * using the validValue method
       */
      public Cell findNextCell()
      {

        //to scan through a board
        for (int r = 0; r < SIZE; r++)
        {
          for (int c = 0; c < SIZE; c++)
          {
            //if a value is equal to 0
            if (this.board[r][c].getValue() == 0)
            {
              //to have a value from 1-9
              int count = 1;

              //a while loop that checks the values from 1 to 9 and makes sure that
              // the value at that specific location is valid
              while (count < 10)
              {
                if (this.validValue(r, c, count))
                {
                  // update this cell to have this value
                  this.board[r][c].setValue(count);

                  // return this cell
                  return this.board[r][c];
                  
                }
                count++;
              }
              // if no value was found for this cell, return null
              return null;
            }
          }
        }
        //looked through all the board and could not find a cell with a 0 value
        return null;
      }

      /*
       * The method should loop over the board and call the draw method of each Cell.
       */
      public void draw(Graphics g, int scale)
      {
        for(int i = 0; i<9; i++){
          for(int j = 0; j<9; j++){
            board[i][j].draw(g, j*scale+5, i*scale+10, scale);
          }
      } if(finished){
          if(validSolution()){
              g.setColor(new Color(0, 127, 0));
              g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
          } else {
              g.setColor(new Color(127, 0, 0));
              g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
          }
      }
      }

      /*
      * set the boolean finished
      */
      public void setFinished(boolean done)
      {
        finished = done;
      }

      /*
      * return the boolean finished
      */
      public boolean getFinished()
      {
        return finished;
      }
      public static void main(String[] args)
      {   
        //command-line arguments
        // if (args.length == 1)
        // {
        //     //Board board = new Board(args[0]);
        //     //System.out.println(board.validSolution());
        // }
        // else
        // {
        //   //if the user does not put in the correct format regarding command-lines
        //   //must put java Board.java filename
        //     System.out.println("Error!! Correct format: java Board.java filename");
        // }

        Board board = new Board();
        System.out.println(board.read("board1.txt"));

        Board a = new Board();

        //inital board 
        System.out.println(a);

        //trying set
        a.set(0, 0, 2);
        a.set(0, 1, 5, false);
        a.set(2, 1, 5, true);
        a.set(5, 7, 9, true);

        System.out.println(a);

        //get columns and rows
        System.out.println(a.getCols()); //9
        System.out.println(a.getRows()); //9

        //see how many cells are locked
        System.out.println(a.numLocked()); //2

        //testing isLocked()
        System.out.println(a.isLocked(0, 1)); //false
        System.out.println(a.isLocked(5, 7)); //true

        //trying the get method
        System.out.println(a.get(2, 1)); //5

        //trying the value method
        System.out.println(a.value(0, 0)); //2 

        //trying validSolution
        System.out.println(a.validSolution()); //false since the board has not finished

        //trying findNextCell
        System.out.println(a.findNextCell()); // 1

        //trying validValue
        System.out.println(a.validValue(0, 5, 5)); //should be false since there is already a five

        Board b = new Board(23); //trying the other constructor which uses the locked cells
        System.out.println(b);
        
        System.out.println(b.findNextCell()); // 1



      }
}
