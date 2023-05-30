/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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

    private double player_screen_y = 540, player_screen_x = 930;//playerX and player YU
    private double mx, my;
    private double xmove = 0, ymove = 0, moveSpeed = 1;
    public static final int WIDTH = 800 * 50, playerStartX = 930;
    public static final int HEIGHT = 3200;
    private Player player;
    private boolean clicked = false;
    private boolean canPlace = true;
    //as of now the world is 50 chunks wide
    private Chunk[] chunks = new Chunk[50];
    private Chunk[] drawChunks;
    public static String debugMessage = "";
    private ArrayList<Entity> entities = new ArrayList();

    public World() {
        chunks = Chunk.generateWorld(25, chunks, 0);//generating world
        player = new Player(WIDTH / 2, 0, this);
        entities.add(player);

    }

    public void draw(Graphics2D g2d) {
        debugMessage += "(X,Y): (" + (int) player.getX() + "," + (int) player.getY() + ") \t" + Chunk.Y;
        //g2d.drawString(getMouseScreenPos().toString() +"k"+k, (int)mx, (int)my);
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

    public int updateWorld(int i, double x) {
        if ((i == 49 || i == 0) && x < 500) {//edge cases for end of world
            i = 0;

            if (player_screen_x <= playerStartX) {
                player_screen_x += player.getXSpd();
            } else {
                player_screen_x = playerStartX;
            }
            drawChunks[0] = chunks[0];
            drawChunks[1] = chunks[1];
            drawChunks[2] = chunks[2];
        } else if (i >= 47 && x > 39000 - player_screen_x) {//edge cases for end of world
            i = 47;

            if (player_screen_x >= playerStartX) {
                player_screen_x += player.getXSpd();
            } else {
                player_screen_x = playerStartX;
            }
            drawChunks[0] = chunks[47];
            drawChunks[1] = chunks[48];
            drawChunks[2] = chunks[49];
        }
        return i;
    }

    public boolean checkCollision(Entity e, double xoff, double yoff) {
        boolean collided = false;
        int roundX = (int) Math.round(e.getX() + xoff);
        int roundY = (int) Math.round(e.getY() + yoff);
        int i = (roundX % Chunk.WIDTH) / Chunk.tSize;
        int j = (roundY + Chunk.Y) / Chunk.tSize;
        int tempi =i;
        int chunkI = getChunki(roundX);
        Chunk chunkOn = chunks[chunkI];
        debugMessage+="chunki("+chunkI+","+i+") ";
        
        Rectangle bounds = e.getBounds();
        //checking Y collision

         for (int k = 0; k <= (bounds.getWidth() / Chunk.tSize); k++) {//repeat for height
             chunkOn = chunks[getChunki(e.getX() + xoff+(Chunk.tSize*k))];
             tempi = ((roundX+(Chunk.tSize*(k))) % Chunk.WIDTH) / Chunk.tSize;
             
        if (yoff > 0) {
            if (chunkOn.getSolid(tempi, j + (int) (bounds.getHeight() / Chunk.tSize))) {
                collided = true;
            }
        } else if (yoff < 0) {
            if (chunkOn.getSolid(tempi, j)) {
                collided = true;
            }
        }
        //checking X collision
        for (int k = 0; k <= (bounds.getHeight() / Chunk.tSize); k++) {//repeat for height

             chunkOn = chunks[getChunki(roundX)];
        if (xoff > 0) {
            
            //tempi = (i)%25;
 
            if (chunkOn.getSolid(i, j+k)) {
                collided = true;
            }
        } else {
            if (chunkOn.getSolid(i, j+k)) {
                collided = true;
            }
        }

        return collided;
    }

    public void drawWorld(Graphics2D g2d) {

        //drawing world
        double chunkScreenX, chunkScreenY;

        //player_screen_x+=xmove;
        double x = player.getX() - player_screen_x;
        double y = player.getY();
        int i = getChunki(x);
        drawChunks = getVisibleChunks(x);

        i = updateWorld(i,x);
        

        chunkScreenX = ((i * Chunk.WIDTH) - x);//get the player X on the screen
        chunkScreenY = (Chunk.Y - y) + player_screen_y;

        int index;
        for (int j = 0; j < 4; j++) {
            //System.out.println(chunkScreFenX + (Chunk.WIDTH * (j - 1)));
            drawChunks[j].draw(g2d, chunkScreenX + (Chunk.WIDTH * (j)), chunkScreenY);
            g2d.drawString((i+j)+"",(int)chunkScreenX+500+(Chunk.WIDTH * (j)),500);
        }
        Point m = getMouseScreenPos();

        int j = (m.x % Chunk.WIDTH) / Chunk.tSize;
        int k = (m.y + Chunk.Y) / Chunk.tSize;

        int mi = getChunki(m.x);
        boolean hovered = false;
        if (m.distance(new Point((int) player.getX(), (int) player.getY())) < 400) {
            g2d.setColor(Color.yellow);
            g2d.drawRect((int) (mi * Chunk.WIDTH - x + (j * Chunk.tSize)), (int) ((k * Chunk.tSize) - y + player_screen_y), Chunk.tSize, Chunk.tSize);
            g2d.setColor(Color.BLACK);
        }
        if (clicked) {
            clicked = false;
            if (chunks[mi].getSolid(j, k)) {

                chunks[mi].remove(j, k);

            } else if (canPlace) {
                chunks[mi].place(j, k, 1);
            }
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
            x2 = World.WIDTH - 1;
        } else if (x2 > World.WIDTH) {
            x2 = 0;
        }
        int i = x2 / Chunk.WIDTH;
        if (i == 50) {
            System.out.println("50! " + x);
            return 49;
        }
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
                if (checkCollision(player, 0, 1)) {
                    player.yspd = -8;
                }
                break;
        }
    }

    public void setMousePos(double x, double y) {
        this.mx = x;
        this.my = y;
    }

    public Point getMouseScreenPos() {
        return new Point((int) (player.getX() + mx - player_screen_x), (int) (player.getY() + my - player_screen_y));
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
        clicked = true;
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
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
