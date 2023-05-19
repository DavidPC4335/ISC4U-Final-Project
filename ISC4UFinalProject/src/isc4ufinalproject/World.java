/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author david
 */
public class World {
    private double px;//playerX and player YU
    private double py;
    public static final int WIDTH = 40000;
    public static final int HEIGHT = 3200;
    //as of now the world is 50 chunks wide
    private Chunk[] chunks = new Chunk[50];
    
    

    public World(){
        px = WIDTH/2;
        py = 0;
        chunks = Chunk.generateWorld(25, chunks,0);//generating world
    }
    
    
    public void draw(Graphics2D g2d){
        chunks[0].draw(g2d, 0, 0);
        chunks[1].draw(g2d, Chunk.WIDTH, 0);
        chunks[2].draw(g2d, Chunk.WIDTH*2, 0);
    }
    
    
}
