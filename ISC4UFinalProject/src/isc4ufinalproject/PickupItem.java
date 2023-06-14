/*
David PC and Calum M
5/29/2023
entity of an item that can bee picked up by player
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author dapav8190
 */
public class PickupItem extends Entity implements Serializable{
    private double t=0;
    private Item item;
    public PickupItem(int x,int y,Item item,World world){
        super(x,y,25,32,world);
        this.item = item;
        this.standing = item.getImage();
        animate = false;
        yspd = -2;
    }
    
    
    @Override
    public void draw(Graphics2D g2d, double x, double y) {
        double yoff=Math.sin(t)/5;
        t+=0.1;
        double dist = new Point(screenX,screenY).distance(new Point((int)world.getPlayer().getScreenX() + 10,(int)world.getPlayer().getScreenY() + 32));
        if(dist<40){
            if(world.addItem(item)){
            world.remove(this);
            }
        }
        
        g2d.setColor(Color.white);
        
        g2d.drawImage(standing,(int)x ,(int)(y+yoff),25,25,null);
        g2d.drawRect((int)x ,(int)(y+yoff),25,25);
        g2d.setColor(Color.black);
    }
    
    
    
    
    
    @Override
    public BufferedImage[] loadImages() {
        return null;
    }
    
}
