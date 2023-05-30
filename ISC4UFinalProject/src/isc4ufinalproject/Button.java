/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Color;
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
    private Color col = Color.red;
    private Menu menu;
    /**
     * constructor method for a button
     *
     * @param width - the width of the rectangle
     * @param height - the height of the rectangle
     */
    public Button(int x, int y, int width, int height,Menu menu) {
        butt = new Rectangle(x, y, width, height);    //instatiating rectangle button
        this.menu = menu;
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
     * draw method for the button
     *
     * @param g - the grapgic to draw the button
     */
    public void draw(Graphics2D g) {
        g.setColor(col);
        if(hovered){
            g.setColor(col.darker());
        }
        g.fill(butt);
    }
}
