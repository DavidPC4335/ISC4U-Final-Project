/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author dapav8190
 */
public class Item {
    private static Image[] icons = loadImages();
    private String name;
    private String description;
    private boolean canUse;
    private int imageIndex=0;
    private int stack =0;
    
    public Item(String name,String description,boolean canUse,int imageIndex){
        this.name =name;
        this.description = description;
        this.canUse = canUse;
        this.imageIndex =imageIndex;
    }
    
    public int getIndex(){
        return this.imageIndex;
    }
    
    public void setStack(int stackOff){
        this.stack+=stackOff;
    }
    
    public int getStack(){
        return stack;
    }
    
    public boolean equals(Item other){
        return(this.canUse == other.canUse && this.name.equals(other.name));
    }
    
    /**
     * method that loads the desired image into an image array
     *
     * @return - the array full of the desired buffered image
     */
    public static Image[] loadImages() {
        Image images[] = new BufferedImage[4];   //initializind image array
        try {
            images[0] = Chunk.tile_images[0];
            images[1] = Chunk.tile_images[1];
            images[2] = ImageIO.read(Chunk.class.getResourceAsStream("pickaxe.png")); //load the dirt sprite as a buffered image
            images[3] = ImageIO.read(Chunk.class.getResourceAsStream("pickaxe.png")); //load the dirt sprite as a buffered image
        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return images;   //return the loaded image array
    }
    
    public boolean canUse(){
        return canUse;
    }
    
    public Image getImage(){
        return icons[imageIndex];
    }
}
