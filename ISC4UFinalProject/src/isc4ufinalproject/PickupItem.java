/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author dapav8190
 */
public class PickupItem extends Entity{
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
        if(new Point((int)x,(int)y).distance(new Point((int)world.getPlayer().getX(),(int)world.getPlayer().getY()))<50){
            world.addItem(item);
            world.remove(this);
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
