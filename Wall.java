/*
 * Properties of the Wall class
 *
 * @author  Shalin Upadhyay
 * @since   2016-10-09
 */

class Wall {

    private boolean isWall;

    // Sets whether the object at the coordinate is a wall or not
    public Wall(boolean isWall){
        this.setStatus(isWall);
    }

    // Sets whether the object at the coordinate is a wall or not
    public void setStatus(boolean isWall){
        this.isWall = isWall;
    }

    // Returns if the Cell is a Wall or not
    public boolean getStatus(){
        return this.isWall;
    }
}