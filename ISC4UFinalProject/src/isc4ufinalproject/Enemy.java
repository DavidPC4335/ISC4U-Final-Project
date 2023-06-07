/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Rectangle;

/**
 *
 * @author dapav8190
 */
public abstract class Enemy extends Entity{
    
    protected int damage,hp;
    protected double speed;
    protected int hitCooldown=0;
    
    public Enemy(double x, double y,int width,int height, World world){
        super(x,y,width,height,world);
    }
    
    public void step(){
        super.step();
       
        
        if (world.checkCollision(this, xspd*2, 0) || world.checkCollision(this, xspd*2 + hitBox.getWidth(), 0)) {
            x -= xspd;
            yspd = -4;
        }
        if(hitCooldown<=0){
        if(hitBox.contains(world.getPlayer().getBounds())){
            world.getPlayer().hit(damage);
            hitCooldown = 70;
            this.xspd = -facing * 15;
            yspd = -3;
            world.getPlayer().move(facing * 10, -3);
        }else{
            xspd += ((double)sign((int)(world.getPlayer().getX()-x))/10) * speed;
        }
        }else{
            hitCooldown --;
             
        }
        
    }
    
    public int getDamage(){
        return damage;
    }
    
    public int getHp(){
        return hp;
    }
    public void hit(int damage){
        hp-=damage;
        if(hp<=0){
            world.remove(this);
        }
    }
    public int sign(int x){
        if(x!=0){
        return x/Math.abs(x);
        }
        return 0;
    }
        
        
}
