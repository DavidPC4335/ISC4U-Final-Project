/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Color;
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
         textSize = txtSz;
        text = txt;
    }

    /**
     * method that draws the desired message at the desired size
     *
     * @param g2d - the graphics 2D to draw the label
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        Font sFont = new Font("Consolas", Font.BOLD, textSize);    //create new font of desired size
        g2d.setFont(sFont); //apply font to g2d
        g2d.drawString(text, x, y); //draw the string text with g2d at x,y
    }

    /**
     * getter method for the x and y pos of the button
     *
     * @return - the x and y coords in a string
     */
    public String getPosition() {
        return x + y + "";
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
     * getter method for text size
     *
     * @return - the text size as an int
     */
    public int getTxtSz() {
        return textSize;
    }

    /**
     * setter method for text size
     *
     * @param txtSz - the desired text size
     */
    public void setTxtSz(int txtSz) {
        textSize = txtSz;
    }

    /**
     * getter method for text msg
     *
     * @return - the text displayed
     */
    public String getTxt() {
        return text;
    }

    /**
     * setter method for the text msg
     *
     * @param txt - the desired text output
     */
    public void setTxt(String txt) {
        text = txt;
    }

    /**
     * clone method for the label
     *
     * @return - the cloned label
     */
    public Label clone() {
        Label lab2 = new Label(x, y, textSize, text);
        return lab2;
    }

    /**
     * equals method for the label
     *
     * @param other - the other label to compare to
     * @return - T/F if the labels are equal
     */
    public boolean equals(Label other) {
        if (this.x == other.x && this.y == other.y && this.textSize == other.textSize
                && this.text.equalsIgnoreCase(other.text)) { // id the labels are equal
            return true;
        }   //if not
        return false;
    }

    /**
     * to string method for the label info
     *
     * @return - the label info in a string
     */
    public String toString() {
        return "X: " + x + " Y: " + y + " Text Size: " + textSize + " Text: " + text;
    }
}
