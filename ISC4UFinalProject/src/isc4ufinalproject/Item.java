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
    private int stack =1;
    private int damage = 3;
    private double drawScale =1;
    public static final Item[] blocks = {
    new Item("Dirt", "From the ground!", true, 1),
    new Item("Dirt", "From the ground!", true, 1),
    new Item("Stone","From the Ground",true,2),
    new Item("Grass","From the Dirt",true,3),
    new Item("Sand","From the Desert",true,4),
    new Item("Sandstone","From the Desert Temple",true,5)
    };
    public static final Item PICKAXE = new Item("Pickaxe","For miners only",true,Chunk.tile_images.length);
    public static final Item SWORD = new Item("Sword","pointy metal stick",true,Chunk.tile_images.length+1,1.4);
    public static final Item KHOPESH = new Item("Khopesh","Gold sword thingy with longer reach",true,Chunk.tile_images.length+2,1.8);
    public Item(String name,String description,boolean canUse,int imageIndex){
        this.name =name;
        this.description = description;
        this.canUse = canUse;
        this.imageIndex =imageIndex;
    }
    public Item(String name,String description,boolean canUse,int imageIndex,double drawScale){
        this(name,description,canUse,imageIndex);
        this.drawScale = drawScale;
    }
    
    public int getIndex(){
        return this.imageIndex;
    }
    
    public void setStack(int stackOff){
        this.stack+=stackOff;
        if(stack <=0){
            //this = null;
        }
    }
    
    public int getStack(){
        return stack;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    public int getDamage(){
        return damage;
    }
    public boolean equals(Item other){
        return(this.canUse == other.canUse && this.name.equals(other.name));
    }
    public double getDrawScale(){
        return drawScale;
    }
    
    /**
     * method that loads the desired image into an image array
     *
     * @return - the array full of the desired buffered image
     */
    public static Image[] loadImages() {
        Image images[] = new BufferedImage[9];   //initializind image array
        try {
            int i;
            for (i = 0; i < Chunk.tile_images.length; i++) {
                images[i] = Chunk.tile_images[i];
            }
            images[i] = ImageIO.read(Chunk.class.getResourceAsStream("pickaxe.png")); //load the dirt sprite as a buffered image
            images[i+1] = ImageIO.read(Chunk.class.getResourceAsStream("sword.png")); //load the dirt sprite as a buffered image
            images[i+2] = ImageIO.read(Chunk.class.getResourceAsStream("khopesh.png")); //load the dirt sprite as a buffered image
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
    public boolean canPlace(){
        return (imageIndex >=0 && imageIndex <Chunk.tile_images.length) && canUse;
    }
    public boolean canMine(){
        return this.equals(PICKAXE);
    }
    public boolean canAttack(){
        return imageIndex>Chunk.tile_images.length;
    }
}
