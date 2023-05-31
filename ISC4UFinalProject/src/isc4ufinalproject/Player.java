/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Player extends Entity {

    public final int MAXSPEED = 3;
    private double animationSpeed = 0.1;
    public Player(double x, double y, World world) {
        super(x, y, 32, 64, world);

    }

    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -40,yoff = -20;
        if(facing == -1){
            xoff = 75;
        }
        g2d.drawImage(moving[1],(int)x + xoff,(int)y +yoff,(int)(hitBox.getWidth()*3)*facing,(int)(hitBox.getHeight()*1.5),null);
        //g2d.drawRect((int) x, (int) y, (int) hitBox.getWidth(), (int) hitBox.getHeight());
    }
    
    public static void loadImages() {
        BufferedImage running[] = new BufferedImage[8];   //initializind image array
        try {
            BufferedImage readImg; 
            for (int i = 0; i < running.length; i++) {
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("characterRun-"+i+".png")); //load the dirt sprite as a buffered image
            }
            

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    public void move(double xmove, double ymove) {
        if (Math.abs(xspd) < MAXSPEED) {
            xspd += xmove;
        }
        if (Math.abs(ymove) > 0) {
            yspd = ymove;
        }
    }

}
