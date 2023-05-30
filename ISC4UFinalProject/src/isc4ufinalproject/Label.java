/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author camac0688
 */
public class Label extends MenuComponent {

    //initializing private variables
    private int textSize;
    private String text;

    /**
     * constructor method for label with text size and text parameters (still
     * contains inherited x and y coords
     *
     * @param txtSz - the text size
     * @param txt - the text message
     */
    public Label(int txtSz, String txt) {
        textSize = txtSz;
        text = txt;
    }

    /**
     * constructor method for label with all variables as parameters
     *
     * @param x
     * @param y
     * @param txtSz
     * @param txt
     */
    public Label(int x, int y, int txtSz, String txt) {
        this.x = x;
        this.y = y;
    }

    /**
     * method that draws the desired message at the desired size
     *
     * @param g2d - the graphics 2D to draw the label
     */
    public void draw(Graphics2D g2d) {
        Font sFont = new Font("Consolas", Font.PLAIN, textSize);    //create new font of desired size
        g2d.setFont(sFont); //apply font to g2d
        g2d.drawString(text, x, y); //draw the string text with g2d at x,y
    }

}
