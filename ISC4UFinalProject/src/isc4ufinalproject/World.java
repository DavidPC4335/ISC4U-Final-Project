/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class World implements KeyListener, MouseListener {

    private double px, player_screen_y = 540, player_screen_x = 930;//playerX and player YU
    private double py;
    private double xmove = 0, ymove = 0, moveSpeed = 0.3;
    public static final int WIDTH = 40000;
    public static final int HEIGHT = 3200;
    private Player player;
    //as of now the world is 50 chunks wide
    private Chunk[] chunks = new Chunk[50];
    private Chunk[] drawChunks;
    public String debugMessage = "";
    private ArrayList<Entity> entities = new ArrayList();

    public World() {
        px = 0;
        py = 0;
        chunks = Chunk.generateWorld(25, chunks, 0);//generating world
        player = new Player(player_screen_x, 0,this);
        entities.add(player);

    }

    public void draw(Graphics2D g2d) {
        debugMessage +="(X,Y): ("+(int)player.getX()+","+(int)player.getY()+") \t"+Chunk.Y;
        g2d.drawString(debugMessage, 10, 10);
        debugMessage = "";
        drawWorld(g2d);
        Entity e;
        //setpping entities
        for (int i = 0; i < entities.size(); i++) {//step all entities
            e = entities.get(i);
            e.step();
        }
        player.move(xmove, ymove);
        player.draw(g2d, player_screen_x, player_screen_y);

    }

    public boolean checkCollision(Entity e,double xoff,double yoff) {
        boolean collided = false;
        int roundX = (int) Math.round(e.getX()+xoff);
        int roundY = (int) Math.round(e.getY()+yoff);
        int i = (roundX % Chunk.WIDTH) / Chunk.tSize;
        int j = (roundY + Chunk.Y) / Chunk.tSize;
        int chunkI = getChunki(e.getX()+xoff);
        Chunk chunkOn = chunks[chunkI];
        Rectangle bounds = e.getBounds();
        
        if(chunkOn.getSolid(i,j+(int)(bounds.getHeight()/Chunk.tSize))){
            collided = true;
        }
        if(chunkOn.getSolid(i,j)){
            collided = true;
        }


        return collided;
    }

    public void drawWorld(Graphics2D g2d) {

        //drawing world
        double chunkScreenX, chunkScreenY;

        //player_screen_x+=xmove;
        double x = player.getX()-player_screen_x;
        double y = player.getY();
        int i = getChunki(x);
        drawChunks = getVisibleChunks(x);

        chunkScreenX = ((i * Chunk.WIDTH) - x );//get the player X on the screen
        chunkScreenY = (Chunk.Y - y)+player_screen_y;

        int index;
        for (int j = 0; j < 4; j++) {
            //System.out.println(chunkScreenX + (Chunk.WIDTH * (j - 1)));
            drawChunks[j].draw(g2d, chunkScreenX + (Chunk.WIDTH * (j)), chunkScreenY);
        }
    }

    public Chunk[] getVisibleChunks(double x) {
        int x2 = (int) Math.round(x);
        Chunk[] list = new Chunk[4];
        for (int i = 0; i < list.length; i++) {
            list[i] = chunks[getChunki(x + (Chunk.WIDTH * (i)))];
        }
        return list;

    }

    public int getChunki(double x) {
        int x2 = (int) Math.round(x);
        if (x2 < 0) {
            x2 = World.WIDTH;
        } else if (x2 > World.WIDTH) {
            x2 = 0;
        }
        int i = x2 / Chunk.WIDTH;
        return i;
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'd':
                xmove = moveSpeed;
                break;
            case 'a':
                xmove = -moveSpeed;
                break;
            case 'w':
                //ymove = -moveSpeed;
                break;
            case 's':

                //ymove = moveSpeed;
                break;
        }
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyChar()) {
            case 'd':
                xmove = 0;
                break;
            case 'a':
                xmove = 0;
                break;
            case 'w':
                ymove = 0;
                break;
            case 's':
                ymove = 0;
                break;
        }
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyTyped(KeyEvent e) {
         switch (e.getKeyChar()) {
             
             case 'w':
                 if(checkCollision(player,0,1)){
                 player.yspd = -4;
                 }
                 break;
         }
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
