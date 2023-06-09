/*
Calum M, David PC
May 18 2023 - 
Button class for getting pressed extends menu component
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author camac0688
 */
public class Button extends MenuComponent {

    //declaring private variables
    private Rectangle butt;
    private boolean hovered = false;
    private int width, height;
    private Color col = Color.red;
    private Menu menu;
    private String text;
    private int txtSize;
    private Runnable action;

    /**
     * constructor method for a button
     *
     * @param width - the width of the rectangle
     * @param height - the height of the rectangle
     */
    public Button(int x, int y, int width, int height, String text, Menu menu) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.width = width;
        this.height = height;
        butt = new Rectangle(x, y, width, height);    //instatiating rectangle button
        this.menu = menu;
super.width = width;
        this.txtSize = height / 3;
    }
    public Button(int x, int y, int width, int height, String text) {
        super.width = width;
        this.x = x;
        this.y = y;
        this.text = text;
        this.width = width;
        this.height = height;
        butt = new Rectangle(x, y, width, height);    //instatiating rectangle button
        

        this.txtSize = height / 3;
    }

    /**
     * method for returning if the mouse is hovered over the button
     *
     * @param mX - the mouse x
     * @param mY - the mouse y
     * @return - t/f depending on if hovering or not
     */
    public boolean hovering(double mX, double mY) {
        if (butt.contains(mX, mY)) {    //if the mouse x and y are in the dimensions of the rectangle
            hovered = true;
            return true;
        } else {    //if they are outsdide of the dimensions
            return false;
        }
    }

    /**
     * method for checking if the button is clicked
     *
     * @param mouseX - the mouse x position
     * @param mouseY - the mouse y position
     * @param clicked - the true/ false for the mouse clicked in general
     * @return - T/F of the button clicked
     */
    public boolean checkClick(double mouseX, double mouseY, boolean clicked) {
        hovered = false;

        return hovering(mouseX, mouseY) && clicked;
    }

    /**
     * setter method for a runnable
     *
     * @param r - the desired runnable
     */
    public void setAction(Runnable r) {
        this.action = r;
    }

    /**
     * run method for the button class
     */
    public void run() {
        if (action != null) {   //if the runnable action is not null
            action.run(); // run the action
        }
    }

    /**
     * draw method for the button
     *
     * @param g - the graphics to draw the button
     */
    public void draw(Graphics2D g) {
        butt.setLocation(x,y);
        g.setColor(col);
        if (hovered) {
            g.setColor(col.darker());
        }
        g.fill(butt);
        g.setColor(Color.BLACK);
        g.draw(butt);
        g.setColor(Color.black);
        Font sFont = new Font("Consolas", Font.BOLD, txtSize);    //create new font of desired size
        g.setFont(sFont); //apply font to g2d
        g.drawString(text, x + width / 15, y + (height / 10) + txtSize); //draw the string text with g2d at x,y
    }

    /**
     * getter method for the x and y pos of the button
     *
     * @return - the x and y coords in a string
     */
    public String getPosition() {
        return "x:" + x + " " + "y:" + y;
    }

    /**
     * setter method for the button position
     *
     * @param x - the desired x coord
     * @param y - the desired y coord
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter method for the rectangle button
     *
     * @return - the rectangle that is the button
     */
    public Rectangle getButton() {
        return butt;
    }

    /**
     * setter method for the button rectangle
     *
     * @param b2 - the desired rectangle
     */
    public void setButton(Rectangle b2) {
        butt = b2;
    }

    /**
     * getter method for the width and height of a button
     *
     * @return - the width and height in a string
     */
    public String getWidthHeight() {
        return "w:" + width + " " + "h:" + height;
    }

    /**
     * setter method for the button height and width
     *
     * @param w - the desired width
     * @param h - the desired height
     */
    public void setWidthHeight(int w, int h) {
        width = w;
        height = h;
    }

    /**
     * equals method for the rectangle button
     *
     * @param other - the other button
     * @return - T/F if the buttons are equal
     */
    public boolean equals(Rectangle other) {
        if (((Rectangle) butt).equals((Rectangle) other)) { //if the buttons are equal
            return true;
        }   //if not
        return false;
    }

    /**
     * clone method for the button
     *
     * @return - the clone of the button
     */
    public Rectangle clone() {
        Rectangle b2 = (Rectangle) ((Rectangle) butt).clone();
        return b2;
    }

    /**
     * to string method for rectangle button
     *
     * @return - the string with rect info
     */
    public String toString() {
        return "X: " + x + " Y: " + y + " Width: " + width + " Height: " + height;
    }

}
