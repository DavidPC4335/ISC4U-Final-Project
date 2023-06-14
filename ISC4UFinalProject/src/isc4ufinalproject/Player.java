/*
David PC and Calum M
5/29/2023
player class that contains the position and physics varibles as well as animations for the player
 */
package isc4ufinalproject;

import static isc4ufinalproject.World.debugMessage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Player extends Entity implements Serializable{

    public final int MAXSPEED = 3;
    private int hp = 10;
    private int hitCooldown =0;
    private Rectangle drawRect = new Rectangle();
    public Player(double x, double y, World world) {
        super(x, y, 32, 64, world);
        animationSpeed = 0.2;

    }

    public void draw(Graphics2D g2d, double x, double y) {
        debugMessage+="("+x+","+y+")";
         if(hitCooldown >0){
             hitCooldown--;
         }
        int xoff = -40,yoff = -20;
        if(facing == -1){
            xoff = 75;
        }
        g2d.drawImage(drawImage,(int)x + xoff,(int)y +yoff,(int)(hitBox.getWidth()*3)*facing,(int)(hitBox.getHeight()*1.5),null);
        //g2d.drawRect((int) x, (int) y, (int) hitBox.getWidth(), (int) hitBox.getHeight());
        //g2d.draw(hitBox);
    }
    
    public BufferedImage[] loadImages() {
        BufferedImage running[] = new BufferedImage[8];   //initializind image array
        try {
            jump = ImageIO.read(Chunk.class.getResourceAsStream("characterJump.png")); //load the dirt sprite as a buffered image
            
            for (int i = 0; i < running.length; i++) {
                running[i] = ImageIO.read(Chunk.class.getResourceAsStream("characterRun-"+i+".png")); //load the dirt sprite as a buffered image
            }
            down = running[3];
            standing = ImageIO.read(Chunk.class.getResourceAsStream("characterStanding.png")); //load the dirt sprite as a buffered image;
            

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        moving = running;
        return running;
    }
    
    public void hit(int damage){
        hp-=damage;
        if(hp<=0){
            world.getSurface().setScreen(0);
            world.getSurface().getDeadMenu(world.getInventory()[world.linearSearch(Item.COIN)].getStack(),world.getInventory()[world.linearSearch(Item.KHOPESH)].getStack(),world.getBosses());
        }else{
            world.addParticles(screenX,screenY,10 , Color.red);
        }
    }
    public void attack(int damage,int reach){
        if(hitCooldown <=0){
            Rectangle hitZone;
            if(reach>0){
        hitZone = new Rectangle(screenX+(int)(hitBox.getWidth()/2),screenY,reach,(int)hitBox.getBounds().getHeight());
            }else{
         hitZone = new Rectangle(screenX+(int)(hitBox.getWidth()/2)+reach,screenY,Math.abs(reach),(int)hitBox.getBounds().getHeight());
            }
        drawRect = hitZone;
        Entity e;
        for (int i=0;i<world.getEntities().size();i++) {
            e = world.getEntities().get(i);
            if(e instanceof Enemy){
                        
                if(e.getBounds().intersects(hitZone)){
                    e.setSpeed(facing*14*((Enemy)e).getKnockBack(),-3);  
                    ((Enemy) e).hit(damage);

                }
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
