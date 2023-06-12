/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author dapav8190
 */
public class Bomber extends Enemy{

    
     public Bomber(double x, double y, World world){
        super(x,y,45,64,world);
        damage = 3;
        hp =10;
        speed = 3.5;
    }
    
    
    @Override
    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -40,yoff = -20;
        if(facing == -1){
            xoff = 75;
        }
        
        g2d.drawImage(drawImage,(int)x + xoff,(int)y +yoff,(int)(hitBox.getWidth()*3)*facing,(int)(hitBox.getHeight()*1.5),null);
    }

    @Override
    public BufferedImage[] loadImages() {
        BufferedImage running[] = new BufferedImage[5];   //initializind image array
        try { 
            
            
            for (int i = 0; i < running.length; i++) {
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("enemy1-"+i+".png")); //load the dirt sprite as a buffered image
            }
            down = running[3];
            jump = running[0];
            standing = running[4];
            

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        moving = running;
        return running;
    }
    
}
