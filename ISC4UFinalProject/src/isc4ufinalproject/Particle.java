/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author david
 */
public class Particle {
    private int alpha = ((int)Math.random()*255);
    private double x=0,y=0;
    private double xspd,yspd;
    private Color c = Color.red;
    
    
    public Particle(int x, int y){
        this.x=x;
        this.y=y;
        xspd = (Math.random()*2)-1;
        yspd = (Math.random()*2)-1;
    }
    
    public Particle(int x,int y,Color col){
        this(x,y);
        this.c = col;
    }
    
    public void draw(Graphics2D g2d){
        if(alpha>0){
            x+=xspd;
            y+=yspd;
        alpha--;
        g2d.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha));
        g2d.drawOval((int)x,(int)y,10,10);
        g2d.setColor(Color.white);
        }
    }
    public boolean getActive(){
        return (alpha>0);
    }

}