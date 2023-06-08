/*
Calum M, David PC
May 18 2023 - 
Bombe type enemy class extends enemy
 */
package isc4ufinalproject;

//imports
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author dapav8190
 */
public class Bomber extends Enemy {

    /**
     * constructor method for the bomber
     *
     * @param x - the x position
     * @param y - the y position
     * @param world - the world the bomber will be in
     */
    public Bomber(double x, double y, World world) {
        super(x, y, 45, 64, world);
        damage = 3;
        hp = 1;
        speed = 3.5;
    }

    @Override //overrides super method

    /**
     * draw method for the bomber
     *
     * @param g2d - the graphics to draw the bomber
     * @param x - the x position to draw
     * @param y - the y position to draw
     */
    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -40, yoff = -20; //initialzing x and y offsets
        if (facing == -1) { //if facing in the -1 direction
            xoff = 75; //set the x offset accordingly
        }

        g2d.drawImage(drawImage, (int) x + xoff, (int) y + yoff, (int) (hitBox.getWidth() * 3) * facing, (int) (hitBox.getHeight() * 1.5), null);  //draw the bomber
        g2d.draw(hitBox);   //draw the hitbox of bomber
        g2d.draw(world.getPlayer().getBounds());    //
    }

    @Override   //overrides the super method
    /**
     * method that loads frames as buffered images into an array
     *
     * @return - the loaded buffered image array
     */
    public BufferedImage[] loadImages() {
        BufferedImage running[] = new BufferedImage[5];   //initializind image array
        try {
            for (int i = 0; i < running.length; i++) { //for the length of the buffered image array
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("enemy1-" + i + ".png")); //load the dirt sprite as a buffered image
            }
            //setting the frams responsible for certain positions
            down = running[3];
            jump = running[0];
            standing = running[4];

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return running; //returrn the filled array
    }

}
