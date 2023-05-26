/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

//imports
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Chunk {

    //initializinf private variables
    private int[][] tiles;
    public static final int tSize = 32, sHeight = 25,Y = 0;
    public static final int WIDTH = 25 * tSize;
    public static final double WEIGHT = 0.2;
    private static Image[] tile_images = loadImages();
    /**
     * constructor method for a chunk
     */
    public Chunk() {
        tiles = new int[25][50];    //initializes tiles 2D array
    }

    /**
     * method that loads the desired image into an image array
     *
     * @return - the array full of the desired buffered image
     */
    public static BufferedImage[] loadImages() {
        BufferedImage b[] = new BufferedImage[2];   //initializind image array
        try {
            BufferedImage dirt = ImageIO.read(Chunk.class.getResourceAsStream("dirt.jpg")); //load the dirt sprite as a buffered image
            b[0] = dirt;    //index 0 will not draw anything, bur must still contain an image
            b[1] = dirt;    //loads image to index 1

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return b;   //return the loaded image array
    }

    /**
     * static world generation method
     *
     * @param pHeight height to start at
     * @param c array of chunks
     * @param i index to generate
     * @return array of generated chunks
     */
    public static Chunk[] generateWorld(int pHeight, Chunk[] c, int i) {
        if (i < c.length) { //if the index to genererate is less than the hghest index in the array of chunks
            c[i] = generateChunk(pHeight);  //generates a chuk of a set base height and adds it to chunk array at index i
            generateWorld(c[i].getHeight(), c, i + 1);
        }
        return c;   //return the chunk array;
    }

    /**
     * getter method for the height of the chunk at last index
     *
     * @return the int for number of tiles high the height of ground is at edge
     * of chunk
     */
    private int getHeight() {
        for (int i = 0; i < tiles[0].length; i++) { //for 50 times
            if (tiles[24][i] > 0) { //if a solid block is in place
                return i;   //return the index of the block
            }
        }
        return tiles.length;    //return the length of the tiles array
    }

    /**
     * generates a chunk
     *
     * @param pHeight height to start at
     * @return generated chunk
     */
    public static Chunk generateChunk(int pHeight) {
        Chunk c = new Chunk();  //intantiate a new chunk
        int height = pHeight;   //set the height equal to the height thaat should be started at
        double rand = 0;    //set a new double (rand which will hold a random double) equal to 0
        for (int i = 0; i < (c.tiles).length; i++) {    //for the array length of the tiles array pf the chunk
            rand = Math.random();//generate random number for chunk Generation;
            if (rand < WEIGHT && height > 10) { //if the weight (chnance of nect tile movind up or down) is more than rand and height is greater than 10
                height--;   //decrease the height
            } else if (rand > 1 - WEIGHT && height < 30) {  //if rand is greater than 1 - weight and height is less than 20
                height++;   //increase the height
            }

            for (int j = height; j < c.tiles[i].length; j++) {  //for the num int between the height and the number of chunks
                c.tiles[i][j] = 1;  //set to index of dirt Tile
            }

        }
        return c;//return chunk
    }

    /**
     * Draws a chunk
     *
     * @param g2d - the graphics to be used to draw
     * @param x - the x coord of the chunk
     * @param y - the y coord of the chunk
     */
    public void draw(Graphics2D g2d, double x, double y) {
        int dx = (int) Math.round(x), dy = (int) Math.round(y); //assigning x and y coords to rounded numbers as an int
        for (int i = 0; i < tiles.length; i++) {    //for the number f tiles in a chunk
            for (int j = 0; j < tiles[0].length; j++) { //loops through entire chunk
                //System.out.println("i"+i+"  j"+j);
                if (tiles[i][j] > 0) {//if tile exists
                    g2d.drawImage(tile_images[tiles[i][j]], dx + (tSize * i), dy + (tSize * j), null);
                    //System.out.println(dx + (tSize * i) + "," + dy + (tSize * j));
                }else{
                  //  g2d.drawString("("+i+","+j+")",dx + (tSize * i), dy + (tSize * j));
                }
            }
        }
    }
    public boolean getSolid(int i,int j){
        if(j>=0 && j<tiles[0].length && i<tiles.length){
        return (tiles[i][j] != 0);
        }else{
            return true;
        }
    }
    public boolean remove(int i,int j){
        if(j>=0 && j<tiles[0].length && i<tiles.length){
            if(tiles[i][j] !=0){
            tiles[i][j] =0;
            }
        return true;
        }else{
            return false;
        }
    }
}
