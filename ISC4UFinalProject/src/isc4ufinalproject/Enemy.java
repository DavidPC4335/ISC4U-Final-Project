/*
David PC and Calum M
5/29/2023
Abstract Enemy class that contains damage speed and requires images to be loaded 
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author dapav8190
 */
public abstract class Enemy extends Entity {

    //declaring and initializing protected variables
    protected int damage, hp;
    protected double speed;
    protected int hitCooldown = 0;
    protected double knockback=1;
    /**
     * constructor method for an enemy
     *
     * @param x - x position
     * @param y - y position
     * @param width - width of the enemy
     * @param height - height of the enemy
     * @param world - world that the enemy will exist in
     */
    public Enemy(double x, double y, int width, int height, World world) {
        super(x, y, width, height, world);
    }

    public void setHP(int h){
        hp=h;
    }
    public void step(){
        super.step();
       
        
        if (world.checkCollision(this, xspd*2, 0) || world.checkCollision(this, xspd*2 + hitBox.getWidth(), 0)) {
            x -= xspd;
            yspd = -4;
        }
        if(hitCooldown<=0){
           // hitBox.setLocation((int)x+(facing*50),(int)y);
        if(hitBox.intersects(world.getPlayer().getBounds())){
            world.getPlayer().hit(damage);
            hitCooldown = 70;
            this.xspd = -facing * 15;
            yspd = -3;
            world.getPlayer().move(facing * 10, -3);
        }else{
            if(Math.abs(xspd) < Math.abs(speed)){
            xspd += ((double)sign((int)(world.getPlayer().getX()-x)));
            }
        }
        
            if (hitBox.contains(world.getPlayer().getBounds())) {   //if the hitbox is within bounds
                world.getPlayer().hit(damage);  //
                hitCooldown = 70;   //set the hit cooldown
                this.xspd = -facing * 15;
                yspd = -3;
                world.getPlayer().move(facing * 10, -3);
            } else {
                xspd += ((double) sign((int) (world.getPlayer().getX() - x)) / 10) * speed;
            }

    }else{
            hitCooldown--;
        }
    }
    /**
     * getter method for enemy damage
     *
     * @return - the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * getter method for the enemy health
     *
     * @return - the enemy health
     */
    public int getHp() {
        return hp;
    }


    /**
     * hit method for if the enemy is hit
     *
     * @param damage - the damage inflicted on the enemy
     */

    public void hit(int damage){
        hp-=damage;
        hitCooldown = 70;
        world.addParticles(screenX,screenY,5 , Color.red.brighter());
        if(hp<=0){
            world.addParticles(screenX,screenY,10 , Color.yellow);
            world.remove(this);
            if(this instanceof Zombie){
                world.getEntities().add(new PickupItem((int)x,(int)y,Item.COIN,world));
                if(((Zombie)this).isBoss){
                 world.getEntities().add(new PickupItem((int)x,(int)y,Item.COIN,world));
                 world.killBoss();
                }
            }
        }else if(Math.abs(xspd) < 0.1){
            hitCooldown+=100;
        }
    }
public double getKnockBack(){
    return knockback;
}
    
    /**
     * method for return the +/- sign of a number
     *
     * @param x - the number to check
     * @return - -1,1,0
     */
    public int sign(int x) {
        if (x != 0) {   //if x is not 0
            return x / Math.abs(x); //return 1/ -1
        }
        return 0;   //return 0
    }

}
