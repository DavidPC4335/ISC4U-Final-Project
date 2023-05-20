/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 *
 * @author david
 */
public abstract class Entity {
    protected double x,y,xspd,yspd;
    public static final double GRAVITY = 0.3;
    protected Shape hitBox;
    
    public abstract void draw(Graphics2D g2d,double x,double y);
    
    
    public Entity(){
        x=0;
        y=0;
        xspd=0;
        yspd=0;
        hitBox = new Rectangle(new Dimension(50,50));//sets hitbox to new square 50x50
    }
    
    public Entity(double x, double y,int width,int height){
        this();
        this.x=x;
        this.y=y;
        this.hitBox = new Rectangle(new Dimension(width,height));
    }
    
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public Shape getBounds(){
        return hitBox;
    }
    
    public void step(){
        if(x<0){
            x = World.WIDTH;
        }else if(x>World.WIDTH){
            x = 0;
        }
    }
}
