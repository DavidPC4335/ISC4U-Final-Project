/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Player extends Entity {

    public final int MAXSPEED = 3;
    private int hp = 10;
    public Player(double x, double y, World world) {
        super(x, y, 32, 64, world);
        animationSpeed = 0.2;

    }

    public void draw(Graphics2D g2d, double x, double y) {
        int xoff = -40,yoff = -20;
        if(facing == -1){
            xoff = 75;
        }
        g2d.drawImage(drawImage,(int)x + xoff,(int)y +yoff,(int)(hitBox.getWidth()*3)*facing,(int)(hitBox.getHeight()*1.5),null);
        //g2d.drawRect((int) x, (int) y, (int) hitBox.getWidth(), (int) hitBox.getHeight());
    }
    
    public BufferedImage[] loadImages() {
        BufferedImage running[] = new BufferedImage[8];   //initializind image array
        try {
            BufferedImage readImg; 
            jump = ImageIO.read(Chunk.class.getResourceAsStream("characterJump.png")); //load the dirt sprite as a buffered image
            
            for (int i = 0; i < running.length; i++) {
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("characterRun-"+i+".png")); //load the dirt sprite as a buffered image
            }
            down = running[3];
            standing = ImageIO.read(Chunk.class.getResourceAsStream("characterStanding.png")); //load the dirt sprite as a buffered image;
            

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return running;
    }
    
    public void hit(int damage){
        hp-=damage;
        if(hp<=0){
            System.out.println("dead");
        }else{
           world.getParticles().add(new Particle(screenX,screenY));
            world.addParticles(10,screenX,screenY , Color.red);
        }
    }
    public void attack(int damage,int reach){
        for (Entity e : world.getEntities()) {
            if(e instanceof Enemy){
                if(hitBox.contains(e.getBounds())){
                    
                }
            }
        }
    }
    public int getHP(){
        return hp;
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
