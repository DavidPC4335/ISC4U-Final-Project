/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public abstract class Entity {

    // declaring protected variables
    protected double x, y, xspd, yspd, prevX;
    protected Rectangle hitBox;
    protected World world;
    public static final double GRAVITY = 0.2;   //initializing the gravity constant
    protected int facing = 1;
    protected Image[] moving = loadImages();
    protected Image standing;
    protected double animationFrame =0;
    protected Image drawImage,jump,down; 
    boolean isCollided = false; //set the collided variable to false
    protected boolean animate = true;

    protected int screenX=0,screenY=0;
    
    protected double animationSpeed =0.1;
    /**
     * abstract draw method for any entity
     *
     * @param g2d - the graphics to draw the entity
     * @param x - the x coord of the entity
     * @param y - the y coord of the entity
     */
    public abstract void draw(Graphics2D g2d, double x, double y);
    
    public void draw(Graphics2D g2d){
        draw(g2d,screenX,screenY);
    }
    
    public abstract BufferedImage[] loadImages();

    /**
     * abstract constructor method of entity
     */
    public Entity() {
        x = 0;
        y = 0;
        xspd = 0;
        yspd = 0;
        hitBox = new Rectangle(new Dimension(50, 50));//sets hitbox to new square 50x50
        
        drawImage = standing;
    }

    /**
     * chained abstract constructor method for entity
     *
     * @param x - the desired x coord
     * @param y - the desired y coord
     * @param width - the desired hitbox width
     * @param height - the desired hitbox height
     * @param world - the world the entity will exist in
     */
    public Entity(double x, double y, int width, int height, World world) {
        this();
        this.x = x;
        this.y = y;
        this.hitBox = new Rectangle((int)x,(int)y,width,height);  //sets hitbox to desired specifications
        this.world = world;
    }

    /**
     * getter method for the entity x coord
     *
     * @return - the x coord
     */
    public double getX() {
        return x;
    }

    /**
     * getter method for the entity y coord
     *
     * @return - the y coord
     */
    public double getY() {
        return y;
    }

    /**
     * setter method for the entity y coord
     *
     * @param y - the desired y coord
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * setter method for the entity x coord
     *
     * @param x - the x coord
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * getter method for the entity bounds
     *
     * @return - the entity hitbox
     */
    public Rectangle getBounds() {
        return hitBox;
    }

    /**
     * method for
     */
    public void step() {
        hitBox.setLocation((int)x, (int)y);
        prevX = x;
        if (x + xspd * 2 < 0) {
            x = 0;
        } else if (x + xspd * 2 > World.WIDTH - Chunk.tSize) {

            x = World.WIDTH - Chunk.tSize;
        }

        x += xspd;
        y += yspd;
        xspd *= 0.90;
        if(Math.abs(xspd) < 0.3){xspd =0;}
        yspd += GRAVITY;
        if (world.checkCollision(this, 0, yspd)) {
           if(yspd <0){ y-=yspd;}
            yspd = 0;
        }
        if (world.checkCollision(this, xspd, 0) || world.checkCollision(this, xspd + hitBox.getWidth(), 0)) {
            x -= xspd * 1.1;
            xspd = 0;
        }

        if(animate){
                    
        animationFrame += animationSpeed;
        if((int)animationFrame >= moving.length){animationFrame =0;}
        if(Math.abs(xspd)>0){
        if(xspd*facing < 0){
            facing*=-1;
        }
        
        drawImage = moving[(int)animationFrame];
        }else{
            drawImage = standing;
        }
        if(!world.checkCollision(this, 0, 1)){
        if (yspd > GRAVITY){
            drawImage = down;
            
        }else{
            drawImage = jump;
        }
        }
        }
        

    }

    /**
     * getter method for the x speed of the entity
     *
     * @return the x speed
     */
    public double getXSpd() {
        return x - prevX;
    }
    
    public void setScreenPos(int x,int y){
        screenX = x;
        screenY =y;
    }
    
    public int getScreenX(){
        return screenX;
    }
    public int getScreenY(){
        return screenY;
    }
    /**
     * setter method for the collided boolean
     *
     * @param collided - the desired T/F of if collided
     */
    public void setCollided(boolean collided) {
        this.isCollided = collided;
    }
}
