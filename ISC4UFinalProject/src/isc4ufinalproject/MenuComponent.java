/*
Calum M., David P.C.
May 26 2023
Abstract MenuComponent Class
 */
package isc4ufinalproject;

import java.awt.Graphics2D;

/**
 *
 * @author camac0688
 */
public abstract class MenuComponent {

    protected int x, y,width;  //declaring protected ints
    protected boolean center =false; 
    /**
     * draw method that any subclass must contain to draw the component
     *
     * @param g - the graphics to draw the component
     */
    public abstract void draw(Graphics2D g);

    
    public boolean getCenter(){
        return center;
    }
    
    public void setCentered(boolean centered){
        this.center = centered;
    }
    /**
     * getter method for the position of the component
     *
     * @return - the x and y position in a string
     */
    public abstract String getPosition();

    /**
     * setter method for the position of the component
     *
     * @param x - the x position
     * @param y - the y position
     */
    public abstract void setPosition(int x, int y);

    /**
     * to string method for the menu components
     *
     * @return - the info in a string
     */
    public abstract String toString();

    public int getY(){
        return y;
    }
    
    public int getWidth(){
        return width;
    }
    
}
