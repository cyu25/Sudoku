/* File: Sudoku.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */

public class Sudoku
{
    //declare variable
    private Board board;
    private int numberOfCells;
    private LandscapeDisplay ld;

    /*
    * A constructor that creates a board with some initial values
    */
    public Sudoku()
    {
        this.numberOfCells = 17;
        board = new Board(this.numberOfCells);
        ld = new LandscapeDisplay(board);

    }
    
    /*
     * Another constructor takes in the number of initial cells that should
     * start on the board by the user
     */
    public Sudoku(int fixedCells)
    {
        this.numberOfCells = fixedCells;
        board = new Board(this.numberOfCells);
        ld = new LandscapeDisplay(board);
    }

    /*
     * A stack is used to keep track of the solution and 
     * allow backtracking when it
     * gets stuck
     */
    public boolean solve(int delay) throws InterruptedException
    {
        //Allocate a stack, initially empty
        CellStack stack = new CellStack();

        //while the stack size is less than the number of unspecified cells (81 is the total number of cells in Sudoku)
        while (stack.size() < 81 - this.numberOfCells)
        {
            if (delay > 0)
            Thread.sleep(delay);
            if (ld != null)
            ld.repaint();


            // System.out.println(board);
            //select the next cell to check (you'll be calling findNextCell, described below)
            Cell cell = this.board.findNextCell();

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
                    Cell gone = stack.pop();

                    // if there is another valid untested value for this cell (create a for loop to add a new cell)
                    // push the cell with its new value onto the stack
                    // update the board
                    // break
                    boolean found = false;

                    for (int x = gone.getValue() + 1; x <= 9; x++)
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
        // Sudoku board2 = new Sudoku();
        // System.out.println(board2);

        Sudoku board = new Sudoku(10);
        System.out.println(board);
        //System.out.println(board.board.read("Board1.txt")); //must set 10 since initial constructor
        //sets 17 cells to be fixed

        //to figure out the timing differences when the fixed values are changed
        long startTime = System.nanoTime();
        board.solve(20);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println(duration);
        System.out.println();
        System.out.println(board);


    }
}
