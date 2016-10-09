/*
 * Randomly generates a maze using the Depth First Search algorithm and iterative backtracking
 *
 * @author  Shalin Upadhyay
 * @since   2016-10-09
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;

public class Generator {

    // Uses Graphics2D to draw the generated maze. Then stores the drawn maze into a .PNG file
    private static void drawMaze(Cell[][] maze, int x, int y, File file) throws IOException {

        int xRect = x * 10;
        int yRect = y * 10;
        int w_border = x - 1;
        int h_border = y - 1;

        // Stores the drawn image
        BufferedImage draw = new BufferedImage(xRect + 1, yRect + 1, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) draw.getGraphics();
        // Sets background color to WHITE
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, xRect + 1, yRect + 1);
        // Sets colors of lines to BLACK
        g2d.setColor(Color.BLACK);
        // Sets thickness of lines
        g2d.setStroke(new BasicStroke(3));

        // Used to draw vertical lines for the maze
        for (int i = 0; i < w_border; i++) {
            for (int j = 0; j < y; j++) {

                // Determines if the object at the coordinate is a wall or empty space
                boolean isWall = maze[i][j].getWall(maze[i + 1][j]).getStatus();

                // Draws a line if it is a wall
                if (isWall) {

                    int xPos = i * 10 + 10;
                    int yPos = j * 10;

                    g2d.drawLine(xPos, yPos, xPos, yPos + 10);
                }
            }
        }

        // Used to draw horizontal lines for the maze
        for (int i = 0; i < h_border; i++) {
            for (int j = 0; j < x; j++) {

                // Determines if the object at the coordinate is a wall or empty space
                boolean isWall = maze[j][i].getWall(maze[j][i + 1]).getStatus();

                // Draws a line if it is a wall
                if (isWall) {

                    int xPos = j * 10;
                    int yPos = i * 10 + 10;

                    g2d.drawLine(xPos, yPos, xPos + 10, yPos);
                }
            }
        }

        // Draws the outside border of the maze
        g2d.drawRect(0, 0, xRect, yRect);

        // Draws the initial position
        g2d.setColor(Color.RED);
        g2d.fillRect(1, 1, 9, 9);

        // Draws the end position
        g2d.setColor(Color.BLUE);
        g2d.fillRect(xRect - 9, yRect - 9, 9, 9);

        // Releases resources
        g2d.dispose();

        // Writes the image to the file
        ImageIO.write(draw, "PNG", file);
    }

    private static void createWall(Cell n1, Cell n2) {

        Wall w = new Wall(true);

        // Creates a wall between n1 and n2
        n1.setWall(n2, w);
        // Sets n2 as a neighbour to n1
        n1.setNeighbours(n2);

        // Creates a wall between n2 and n1
        n2.setWall(n1, w);
        // Sets n1 as a neighbour to n2
        n2.setNeighbours(n1);
    }

    private static Cell[][] createMaze(int x, int y) {

        // Creates an empty 2D array of Cells to represent the maze
        Cell maze[][] = new Cell[x][y];

        // The border must be located at these positions
        int w_border = x - 1;
        int h_border = y - 1;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                // Initializes each Cell object
                maze[i][j] = new Cell();
            }
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (j < h_border) {
                    // Creates a South/North wall between bordering cells
                    createWall(maze[i][j], maze[i][j + 1]);
                }
                if (i < w_border) {
                    // Creates a East/West wall between bordering cells
                    createWall(maze[i][j], maze[i + 1][j]);
                }
            }
        }
        return maze;
    }

    private static Cell[][] mazeGenerator(int x, int y) throws IOException {

        Cell maze[][] = createMaze(x, y);

        // Stores the visitedCells within the Stack
        // Uses the LIFO property of a Stack for backtracking
        Stack<Cell> visitedCells = new Stack<Cell>();

        Random r = new Random();

        // Randomly picks the starting position
        Cell current = maze[r.nextInt(x)][r.nextInt(y)];
        // Sets the initial position as visited
        current.setVisited();
        // Pushes the cell into the Stack
        visitedCells.push(current);

        while (!visitedCells.isEmpty()) {
            // Stores cells that have not yet been visited
            ArrayList<Cell> unvisited = new ArrayList<Cell>();

            // Iterates through the neighbours of the current cell
            for (Cell c : current.getNeighbours()) {
                // If the cell has not been visited it is stored in the List
                if (!c.getVisited()) {
                    unvisited.add(c);
                }
            }

            // If the number of unvisited cells is greater than 0, then visit one of these cells
            if (unvisited.size() > 0) {
                // Randomly chooses a unvisited cell to visit
                Cell visited = unvisited.get(r.nextInt(unvisited.size()));
                visited.setVisited();
                // Sets the cell has NOT a wall
                visited.getWall(current).setStatus(false);
                // Pushes this new cell into the List of visited cells
                visitedCells.push(visited);
                // Sets the current cell to the newest visited cell
                current = visited;
            } else {
                // Sets current cell as the latest visited cell
                // Begins iterative backtracking process
                current = visitedCells.pop();
            }
        }
        // Returns the final maze;
        return maze;
    }

    public static void main(String[] args) throws IOException {

        // Height and Width (Units) of the generated maze
        int width = 1000, height = 1000;

        // Generates the maze and stores it within maze[][]
        Cell maze[][] = mazeGenerator(width, height);

        // Draws the generated maze, and stores the image within a .PNG file
        // NOTE: The user will have to change this PATH for their local environment
        drawMaze(maze, width, height, new File("C://Users//Shalin//Desktop//Maze.png"));
    }
}