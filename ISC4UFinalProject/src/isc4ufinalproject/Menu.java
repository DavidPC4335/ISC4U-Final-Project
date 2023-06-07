/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author dapav8190
 */
public class Menu implements MouseListener {

    //declaring and initializing private variables
    private ArrayList<MenuComponent> components;
    private boolean clicked;
    private double mx, my;
    private Surface gameSurface;
    public static final BufferedImage BACKGROUND = loadImages();
    boolean drawBackground = false;

    /**
     * constructor method for a menu object
     *
     * @param gameSurface - the game surface that the menu will appear on
     */
    public Menu(Surface gameSurface) {
        this.gameSurface = gameSurface;
        mx = 0;
        my = 0;
        components = new ArrayList();
        clicked = false;
    }

    /**
     * chained constructor method for a menu object
     *
     * @param list - the array list of menu components that the menu will
     * contain
     * @param gameSurface - the game surface that the menu will display on
     */
    public Menu(ArrayList<MenuComponent> list, Surface gameSurface) {
        this(gameSurface);
        components = list;
    }

    /**
     * method that loads the desired image into an image array
     *
     * @return - the array full of the desired buffered image
     */
    public static BufferedImage loadImages() {
        BufferedImage b = null;   //initializind image array
        try {
            BufferedImage bg = ImageIO.read(Chunk.class.getResourceAsStream("pixelBackground.jpg")); //load the dirt sprite as a buffered image
            b = bg;

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return b;   //return the loaded image array
    }

    /**
     * add method that adds a menu component to a menu
     *
     * @param m - the menu component to add
     */
    public void add(MenuComponent m) {
        components.add(m);
    }

    /**
     * draw method for the menu
     *
     * @param g2d - the graphics engine to draw the menu
     */
    public void draw(Graphics2D g2d) {
        if (drawBackground) {   //if drawbackground is true
            g2d.drawImage(BACKGROUND, 0, 0, gameSurface.getWidth(), gameSurface.getHeight(), null); //draw the background
        }
        for (int i = 0; i < components.size(); i++) {   //for the number of times that components is long
            MenuComponent m = components.get(i); // set the menu component m to the menu compontnt at index 1 of the components aray list
            m.draw(g2d);    //draw the component
            if (m instanceof Button) {  //if m is a button component
                if (((Button) m).checkClick(mx, my, clicked)) { //if button is clicked
                    clicked = false;    //set the button to not clicked
                    ((Button) m).run(); //runs the fuction that button m controls

                }
            }
        }
        if (clicked) {  //if clicked is true
            clicked = false;    //set clicked to false
        }
    }

    /**
     * setter method for the mouse position
     *
     * @param mx - the desired mouse x
     * @param my - the desired mouse y
     */
    public void setMousePos(double mx, double my) {
        this.mx = mx;
        this.my = my;
    }

    /**
     * setter method for visible background
     *
     * @param v - the boolean for T/F if visible
     */
    public void setVisibleBackground(boolean v) {
        drawBackground = v;
    }

    // mouse clicked keylistener
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // mouse pressed keylistener
    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true; //set clicked = to true
    }

    // mouse release keylistener
    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false; // set clicked to false
    }
    
    // mouse entered keylistener
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    // mouse exited keylistener
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
