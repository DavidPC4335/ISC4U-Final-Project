/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author david
 */
public class Player extends Entity{

        public final int MAXSPEED = 3;
     public Player(double x, double y,World world){
        super(x,y,32,64,world);
        
    }
    
    
    
public void draw(Graphics2D g2d,double x,double y){
    g2d.drawRect((int)x ,(int)y,(int)hitBox.getWidth(),(int)hitBox.getHeight());
}

public void move(double xmove,double ymove){
    if(Math.abs(xspd) < MAXSPEED){
        xspd+=xmove;
    }
    if(Math.abs(ymove) > 0){
        yspd = ymove;
    }
}


}
