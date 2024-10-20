/* File: Cell.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */

import java.awt.Graphics;
import java.awt.Color;

public class CellExt
{
    //variables
    private int row;
    private int col;
    private int value;
    private boolean locked;
    private int numberOfSolutions;

    /*
     * constructor that initalize all values to 0 or false
     */
    public CellExt()
    {
        this.row = 0;
        this.col = 0;
        this.value = 0;
        this.locked = false;
    }

    /*
     * initalize the row, column, and value fields to the given
     * parameter values. Set the locked field to false
     */
    public CellExt(int row, int col, int value)
    {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = false;

    }

    /*
     * initialize all of the Cell fields given the parameters
     */
    public CellExt(int row, int col, int value, boolean locked)
    {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    /*
     * return the Cell's row index
     */
    public int getRow()
    {
        return this.row;
    }

    /*
     * return the Cell's column index
     */
    public int getCol()
    {
        return this.col;
    }

    /*
     * return the Cell's value
     */
    public int getValue()
    {
        return this.value;
    }

    /*
     * set the Cell's value
     */
    public void setValue(int newval)
    {
        this.value = newval;
    }

    /*
     * return the value of the locked field
     */
    public boolean isLocked()
    {
        return this.locked;
    }

    /*
     * set the Cell's locked field to the new value
     */
    public void setLocked(boolean lock)
    {
        this.locked = lock;
    }

    /*
     * generate a representating String
     */
    public String toString()
    {
        return "" + this.value;
    }

    /*
     * if the two objects are equal
     */
    public boolean isEqual(Object other)
    {
        if (this.value == ((Cell) other).getValue())
        {
            return true;
        }
        return false;
    }

    /*
     * set the numberofSolutions for a Cell
     */
    public void setNumberOfSolutions(int num)
    {
        numberOfSolutions = num;
    }

    /*
     * return number of solutions
     */
    public int getNumberOfSolutions()
    {
        return numberOfSolutions;
    }


    /*
     * a draw method that draws the Cell's number
     * x) and y0 are a global offset for the board
     */
    public void draw(Graphics g, int x0, int y0, int scale)
    {
        String myString = Integer.toString(value);
        g.setColor(locked? Color.BLUE : Color.RED);
        g.drawString(myString, x0, y0);
    }
}
