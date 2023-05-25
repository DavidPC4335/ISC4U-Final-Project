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
    public static final double GRAVITY = 0.1;
    protected Rectangle hitBox;
    protected World world;
    
    boolean isCollided = false;
    public abstract void draw(Graphics2D g2d,double x,double y);
    
    
    public Entity(){
        x=0;
        y=0;
        xspd=0;
        yspd=0;
        hitBox = new Rectangle(new Dimension(50,50));//sets hitbox to new square 50x50
    }
    
    public Entity(double x, double y,int width,int height,World world){
        this();
        this.x=x;
        this.y=y;
        this.hitBox = new Rectangle(new Dimension(width,height));
        this.world = world;
    }
    
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
      public void setY(double y){
        this.y=y;
    }
    public void setX(double x){
        this.x=x;
    }
    public Rectangle getBounds(){
        return hitBox;
    }
    
    public void step(){
        if(x<0){
            x = World.WIDTH;
        }else if(x>World.WIDTH){
            x = 0;
        }
       
        x+=xspd;
        y+=yspd;
        xspd*=0.95;
        yspd += GRAVITY;
        if(world.checkCollision(this,0,yspd)){
            yspd =0;
        }
        if(world.checkCollision(this,xspd,0) || world.checkCollision(this,xspd+hitBox.getWidth(),0)){
            x-=xspd;
            xspd =0;
        }
    }
    
    public void setCollided(boolean collided){
        this.isCollided = collided;
    }
}
