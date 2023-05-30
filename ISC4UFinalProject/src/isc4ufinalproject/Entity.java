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

    // declaring protected variables
    protected double x, y, xspd, yspd, prevX;
    protected Rectangle hitBox;
    protected World world;
    public static final double GRAVITY = 0.2;   //initializing the gravity constant

    boolean isCollided = false; //set the collided variable to false

    /**
     * abstract draw method for any entity
     *
     * @param g2d - the graphics to draw the entity
     * @param x - the x coord of the entity
     * @param y - the y coord of the entity
     */
    public abstract void draw(Graphics2D g2d, double x, double y);

    /**
     * abstract constructor method of entity
     */
    public Entity() {
        x = 0;
        y = 0;
        xspd = 0;
        yspd = 0;
        hitBox = new Rectangle(new Dimension(50, 50));//sets hitbox to new square 50x50
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
        this.hitBox = new Rectangle(new Dimension(width, height));  //sets hitbox to desired specifications
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
     *
     */
    public void step() {
        prevX = x;
        if (x + xspd * 2 < 0) {
            x = 0;
        } else if (x + xspd * 2 > World.WIDTH - Chunk.tSize) {

            x = World.WIDTH - Chunk.tSize;
        }

        x += xspd;
        y += yspd;
        xspd *= 0.90;
        yspd += GRAVITY;
        if (world.checkCollision(this, 0, yspd)) {
            yspd = 0;
        }
        if (world.checkCollision(this, xspd, 0) || world.checkCollision(this, xspd + hitBox.getWidth(), 0)) {
            x -= xspd * 1.1;
            xspd = 0;
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

    /**
     * setter method for the collided boolean
     *
     * @param collided - the desired T/F of if collided
     */
    public void setCollided(boolean collided) {
        this.isCollided = collided;
    }
}
