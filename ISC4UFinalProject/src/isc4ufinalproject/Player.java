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

     public Player(double x, double y,int width,int height){
        super(x,y,width,height);
        
    }
    
    
    
public void draw(Graphics2D g2d,double x,double y){
    g2d.drawRect((int)x,(int)y,100,100);
}

public void move(double xmove,double ymove){
    x+=xmove;
    y+=ymove;
}


}
