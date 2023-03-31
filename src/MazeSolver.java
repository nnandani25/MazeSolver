/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Creates an array list of the maze cells which are the solution.
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        Stack<MazeCell> s = new Stack<MazeCell>();
        MazeCell currCell = maze.getEndCell();
        s.push(currCell);

        // Starts at the end of the maze and gets the parent cells until it gets to the beginning.
        while(currCell != maze.getStartCell())
        {
            // Changes the current cell to the parent cell and pushes the current cell.
            currCell = currCell.getParent();
            s.push(currCell);
        }

        // Moves the values in the stack into the arraylist, and since its in a stack, it will reverse the current order.
        while(!s.empty())
        {
            solution.add(s.pop());
        }

        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Creates a new stack of maze cells.
        Stack<MazeCell> s = new Stack<MazeCell>();
        s.push(maze.getStartCell());
        maze.getStartCell().setExplored(true);

        // Goes through until the stack is empty, because once it is empty, everything has been visited.
        while(!s.empty())
        {
            MazeCell currCell= s.pop();
            // Goes through and pushes the cells which have valid neighbors.
            for(MazeCell c: findNeighbors(currCell))
            {
                // Pushes the cell, sets the cell to explored, and sets the parent cell to current cell.
                // (Because the current cell will be the parent for all of its neighbors).
                s.push(c);
                c.setExplored(true);
                c.setParent(currCell);
            }
        }
        // Returns the solution.
        return getSolution();
    }

    // Returns an arraylist of valid neighbor maze cells.
    public ArrayList<MazeCell> findNeighbors(MazeCell c)
    {
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();

        // Checks if the cell North of the current cell is valid, and if so, adds it to the array.
        if(maze.isValidCell(c.getRow() - 1, c.getCol()))
        {
            neighbors.add(maze.getCell(c.getRow() - 1, c.getCol()));
        }

        // Checks if the cell West of the current cell is valid, and if so, adds it to the array.
        if(maze.isValidCell(c.getRow(), c.getCol() + 1))
        {
            neighbors.add(maze.getCell(c.getRow() , c.getCol() + 1));
        }

        // Checks if the cell South of the current cell is valid, and if so, adds it to the array.
        if(maze.isValidCell(c.getRow() + 1, c.getCol()))
        {
            neighbors.add(maze.getCell(c.getRow() + 1 , c.getCol()));
        }

        // Checks if the cell East of the current cell is valid, and if so, adds it to the array.
        if(maze.isValidCell(c.getRow(), c.getCol() - 1))
        {
            neighbors.add(maze.getCell(c.getRow(), c.getCol() - 1));
        }

        return neighbors;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Uses a Queue, because it is constantly adding cells, but checks in the order it was added.
        Queue<MazeCell> s = new LinkedList<MazeCell>();
        s.add(maze.getStartCell());
        maze.getStartCell().setExplored(true);

        // Goes through until the queue is empty, because once it is empty, everything has been visited.
        while(!s.isEmpty())
        {
            MazeCell currCell= s.remove();
            // Adds the cell, sets the cell to explored, and sets the parent cell to current cell.
            for(MazeCell c: findNeighbors(currCell))
            {
                // Goes through and adds the cells which have valid neighbors.
                s.add(c);
                c.setExplored(true);
                c.setParent(currCell);
            }
        }
        // Returns the solution.
        return getSolution();

    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
