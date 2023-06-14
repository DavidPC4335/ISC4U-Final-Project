/*
David PC and Calum M
5/29/2023
zombie enemy that extends enemy with given stats
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
     
    protected boolean isBoss;
     public Zombie(double x, double y, World world){
        super(x,y,30,64,world);
        damage = 2;
        hp =7;
        speed = (Math.random()*0.2)+0.4;
        
    }
    
     
    
    @Override
    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -(int)hitBox.getWidth()/2,yoff = - (int)(hitBox.getHeight()*0.8);
        if(facing == -1){
            xoff = (int) (hitBox.getWidth()*1.5);
        }
        //(int)(hitBox.getWidth()*5)*facing,(int)(hitBox.getHeight()*3)
        g2d.drawImage(drawImage,(int)x + xoff,(int)y +yoff,(int)(hitBox.getWidth()*2)*facing,(int)hitBox.getHeight()*2,null);
        //g2d.draw(hitBox);
    }

    
    public void isBoss(boolean b){
        isBoss =b;
        damage = 4;
        knockback =0.5;
        speed = 2.4;
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
        moving = running;
        return running;
    }
}
