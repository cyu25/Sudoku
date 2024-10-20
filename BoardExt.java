/* File: BoardExt.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class BoardExt
{
    //initalize variables 
    private CellExt[][] board;
    public int SIZE; 
    protected boolean finished; //a boolean field

    /*
     * A constructor that takes an int parameter and a size parameter
     * to specify the number of inital cells to be locked and what the board
     * size should be
     */
    public BoardExt(int lockedCells, int newSize)
    {
      int count = 0;
      SIZE = newSize;
      Random ran = new Random();
      this.board = new CellExt[SIZE][SIZE];
      for (int x = 0; x < SIZE; x++)
      {
          for (int y = 0; y < SIZE; y++)
          {
              this.board[x][y] = new CellExt(x, y, 0);
          }
      }

      while(count < lockedCells){

        // System.out.println(count);
        // pick a random cell
        CellExt cell = this.board[ran.nextInt(SIZE)][ran.nextInt(SIZE)];

        // pick a random value
        int val = ran.nextInt(SIZE) + 1;

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

                if (c % Math.sqrt(SIZE) == 4) //2 for 3 (9x9), 4 for 5 (25x25), etc. 
             {
                result += "\t";
             }
             } result += "\n";

             if (r % Math.sqrt(SIZE) == 4)
             {
                result += "\n";
             }
         }
 
         return result;
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
      public CellExt get(int r, int c)
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
        if (value < 1 || value > SIZE)
        {
          return false;
        }
        
        int x = (int) Math.sqrt(SIZE);

        for (int r = row/x * x; r < row/x * x + x; r++) //so if row is 1/3 * 3 = 0
        {
          for (int c = col/x * x; c < col/x * x + x; c++) //so if column is 5/3 *3  = 3
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
       * If all the cells are between the given board size and all the Cells are valid, the 
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
       * with a 0 value. Then start checking the possible values from the given board size
       * using the validValue method
       */
      public CellExt findNextCell()
      {

        //to scan through a board
        for (int r = 0; r < SIZE; r++)
        {
          for (int c = 0; c < SIZE; c++)
          {
            //if a value is equal to 0
            if (this.board[r][c].getValue() == 0)
            {
              //to have a value from the given board size (1 - board length)
              int count = 1;

              //a while loop that checks the values from the given board size and makes sure that
              // the value at that specific location is valid
              while (count < SIZE)
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
        for(int i = 0; i<SIZE; i++){
          for(int j = 0; j<SIZE; j++){
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
       * To find the cell with the fewest valid value options
       * and try it first. In some cases there may be only one valid value.
       *  If there is a cell with no valid options, return null so the solver can bactrack
       */
      public CellExt findNextCell2()
      {
        CellExt temp = null;
        int previous = SIZE;
        for (int r = 0; r < SIZE; r++)
        {
          for (int c = 0; c < SIZE; c++)
          {
            if (this.board[r][c].getValue() == 0)
            {
              int count = 0; //keeps track of the number of valid values
              int sizeCount = 1; //keeps track of the size

              //a while loop that checks the values from the given board size and makes sure that
              // the value at that specific location is valid
                while (sizeCount < SIZE)
                {
                  //if the value is valid, count increases, holding the valid values present in this cell
                if (this.validValue(r, c, sizeCount))
                  {
                    count++;
                  }
                sizeCount++;
                }
                

              ///outside the while loop which checks the number of solutions (valid values)
              // if the number of solutions is less than the solutions from the previous cell then
              //temp (a reference) holds the cell
              if (this.board[r][c].getNumberOfSolutions() < previous)
              {
                this.board[r][c].setNumberOfSolutions(count);
                temp = this.board[r][c];
              }
              previous = count;
            }
          }
        
        }

        //after finding the location, now use the location to set the value of the cell
        int sizeCount = 1;
        
        //same process as before, but now focusing on one column and one row
        while (sizeCount < SIZE)
          {
            //also making sure that the cell being worked on is not locked
            if (this.validValue(temp.getRow(), temp.getCol(), sizeCount) && !this.isLocked(temp.getRow(), temp.getCol()))
              {
                this.board[temp.getRow()][temp.getCol()].setValue(sizeCount);

                // return this cell
                return this.board[temp.getRow()][temp.getCol()];
              }
                sizeCount++;
          }

          return null;

      }
      /*
      * set the boolean finished
      */
      public void setFinished(boolean done)
      {
        finished = done;
      }

      /*
      * return the boolean finisheds
      */
      public boolean getFinished()
      {
        return finished;
      }
      public static void main(String[] args)
      {  
        BoardExt board = new BoardExt(50, 25);
        System.out.println(board);
        //System.out.println(board.findNextCell2());


        // Board a = new Board();

        // //inital board 
        // System.out.println(a);

        // //trying set
        // a.set(0, 0, 2);
        // a.set(0, 1, 5, false);
        // a.set(2, 1, 5, true);
        // a.set(5, 7, 9, true);

        // System.out.println(a);

        // //get columns and rows
        // System.out.println(a.getCols());
        // System.out.println(a.getRows());

        // //see how many cells are locked
        // System.out.println(a.numLocked());

        // //testing isLocked()
        // System.out.println(a.isLocked(0, 1));
        // System.out.println(a.isLocked(5, 7));

        // //trying the get method
        // System.out.println(a.get(2, 1));

        // //trying the value method
        // System.out.println(a.value(0, 0));




      }
}
