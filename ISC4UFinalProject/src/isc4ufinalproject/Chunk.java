/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

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

    private int[][] tiles;
    private static final int tSize = 32, sHeight = 25;
    public static final int WIDTH = 25 * tSize;
    private static Image[] tile_images = loadImages();

    public Chunk() {
        tiles = new int[25][50];
    }

    public static BufferedImage[] loadImages() {
        BufferedImage b[] = new BufferedImage[2];
        try {
            BufferedImage dirt = ImageIO.read(Chunk.class.getResourceAsStream("dirt.jpg"));
            b[0] = dirt;
            b[1] = dirt;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return b;
    }

    /**
     * static world generation method
     *
     * @param pHeight height toi start at
     * @param c array of chunks
     * @param i index to generate
     * @return array of generated chunks
     */
    public static Chunk[] generateWorld(int pHeight, Chunk[] c, int i) {
        if (i < c.length) {
            c[i] = generateChunk(pHeight);
            generateWorld(c[i].getHeight(), c, i + 1);
        }
        return c;
    }

    private int getHeight() {
        for (int i = 0; i < tiles[0].length; i++) {
            if(tiles[24][i] > 0){
                return i;
            }
        }
        return tiles.length;
    }

    /**
     * generates a chunk
     *
     * @param pHeight height to start at
     * @return generated chunk
     */
    public static Chunk generateChunk(int pHeight) {
        Chunk c = new Chunk();
        int[][] t = c.tiles;
        int height = pHeight;
        double rand = 0;
        for (int i = 0; i < t.length; i++) {
            rand = Math.random();//generate random number for chunk Generation;
            if (rand < 0.25) {//down weight
                height--;
            } else if (rand > 0.75) {//up weight
                height++;
            }

            for (int j = height; j < t[i].length; j++) {
                t[i][j] = 1;//set to index of dirt Tile
            }

        }
        return c;//return chunk
    }

    /**
     * draws chunk
     *
     * @param g graphics
     */
    public void draw(Graphics2D g2d, double x, double y) {
        int dx = (int) Math.round(x), dy = (int) Math.round(y);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {//loops through entire chunk
                //System.out.println("i"+i+"  j"+j);
                if (tiles[i][j] > 0) {//if tile exists
                    g2d.drawImage(tile_images[tiles[i][j]], dx + (tSize * i), dy + (tSize * j), null);
                    //System.out.println(dx + (tSize * i) + "," + dy + (tSize * j));
                }
            }
        }
    }
}
