/*
 * Properties of the Cell class
 *
 * @author  Shalin Upadhyay
 * @since   2016-10-09
 */

import java.util.HashMap;
import java.util.ArrayList;

class Cell {

    private HashMap<Cell, Wall> map = new HashMap<Cell, Wall>();
    private ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
    private boolean visit = false;

    // Sets the Cell c as a neighbour
    public void setNeighbours(Cell c){
        cellNeighbours.add(c);
    }

    // Returns the neighbours of a Cell
    public ArrayList<Cell> getNeighbours(){
        return cellNeighbours;
    }

    // Sets a Wall for a Cell
    public void setWall(Cell c, Wall w){
        map.put(c, w);
    }

    // Returns the Wall that is neighbouring a Cell
    public Wall getWall(Cell c){
        return map.get(c);
    }

    // Sets that the Cell has been visited
    public void setVisited(){
        visit = true;
    }

    // Returns if the Cell has been visited or not
    public boolean getVisited(){
        return visit;
    }
}