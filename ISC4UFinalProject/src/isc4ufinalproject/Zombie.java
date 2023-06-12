/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Zombie extends Enemy{
     
        
     public Zombie(double x, double y, World world){
        super(x,y,30,64,world);
        damage = 2;
        hp =7;
        speed = 0.5;
    }
    
    
    @Override
    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -30,yoff = -45;
        if(facing == -1){
            xoff = 55;
        }
        //(int)(hitBox.getWidth()*5)*facing,(int)(hitBox.getHeight()*3)
        g2d.drawImage(drawImage,(int)x + xoff,(int)y +yoff,85*facing,120,null);
        //g2d.draw(hitBox);
    }

    @Override
    public BufferedImage[] loadImages() {
        BufferedImage running[] = new BufferedImage[5];   //initializind image array
        try { 
            
            
            for (int i = 0; i < running.length; i++) {
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("zombieF"+(i+1)+".png")); //load the dirt sprite as a buffered image
            }
            down = running[3];
            jump = running[0];
            standing = running[4];
            

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return running;
    }
}
