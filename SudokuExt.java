/* File: SudokuExt.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */

public class SudokuExt
{
    //declare variable
    private BoardExt board;
    private int numberOfCells;
    private LandscapeDisplayExt ld;
    
    /*
     * a constructor that takes in the number of initial cells a board should start with
     * along with the size of the board
     */
    public SudokuExt(int fixedCells, int size)
    {
        this.numberOfCells = fixedCells;
        board = new BoardExt(this.numberOfCells, size);
        ld = new LandscapeDisplayExt(board);
    }

    /*
     * A stack is used to keep track of the solution and 
     * allow backtracking when it
     * gets stuck
     */
    public boolean solve(int delay) throws InterruptedException
    {
        //Allocate a stack, initially empty
        CellStackExt stack = new CellStackExt();

        //while the stack size is less than the number of unspecified cells (81 is the total number of cells in Sudoku)
        while (stack.size() < this.board.SIZE * this.board.SIZE - this.numberOfCells)
        {
            if (delay > 0)
            Thread.sleep(delay);
            if (ld != null)
            ld.repaint();


            // System.out.println(board);
            //select the next cell to check (you'll be calling findNextCell, described below)
            CellExt cell = this.board.findNextCell();

            //if this cell has a valid value to try
            //push the cell onto the stack
            //update the board
            if(cell != null)
            {   
                stack.push(cell);
            }
            else
            {
                /*
                 *   while it is possible to backtrack (if the stack is nonempty)
                    pop a cell off the stack
                    check if there are other untested values this cell could try
                 */
                while (!stack.empty())
                {
                    //this is the cell that is popping out of the stack
                    CellExt gone = stack.pop();

                    // if there is another valid untested value for this cell (create a for loop to add a new cell)
                    // push the cell with its new value onto the stack
                    // update the board
                    // break
                    boolean found = false;

                    for (int x = gone.getValue() + 1; x <= this.board.SIZE; x++)
                    {
                        if (board.validValue(gone.getRow(), gone.getCol(), x))
                        {
                            //setting the value
                            this.board.set(gone.getRow(), gone.getCol(), x);
                            
                            //adding to stack
                            stack.push(gone);
                            found = true;
                            break;
                        }
                    }
                    if (found)
                    {
                        break;
                    }
                    else
                    {
                        //if no value works then it needs to be set to 0
                        gone.setValue(0);
                    }
                }
            }

            //return false: there is no solution
            if (stack.size() == 0)
            {
                this.board.setFinished(true);
                return false;
            }
        }
        //Update your Sudoku class to update finished appropriately in the solve() method
        this.board.setFinished(true);

        //return true: the board contains the solution
        System.out.println(board);
        return true;
    }

    /*
     * to print out the board
     */
    public String toString()
    {
        return board.toString();
    }

    public static void main(String[] args) throws InterruptedException
    {
        SudokuExt board = new SudokuExt(22, 25); //If you want to try various sizes of boards
        //you must change the values in the toString method of BoardExt. Specifically, the part 
        //where it states "if (c % Math.sqrt(SIZE)...) "

         //to figure out the timing differences when the fixed values are changed
         long startTime = System.nanoTime();
         board.solve(5);
         long endTime = System.nanoTime();
 
         long duration = (endTime - startTime);
 
         System.out.println(duration);
    }
}
