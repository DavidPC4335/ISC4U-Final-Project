/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author dapav8190
 */
public class Menu implements MouseListener{

    private ArrayList<MenuComponent> components;
    private boolean clicked;
    private double mx,my;
    private Surface gameSurface;
    public static final BufferedImage BACKGROUND = loadImages();
    boolean drawBackground = false;
    public Menu(Surface gameSurface){
        this.gameSurface =gameSurface;
        mx=0;my=0;
        components = new ArrayList();
        clicked = false;
    }
    public Menu(ArrayList<MenuComponent> list,Surface gameSurface){
        this(gameSurface);
        components = list;
    }
    
    
    
        /**
     * method that loads the desired image into an image array
     *
     * @return - the array full of the desired buffered image
     */
    public static BufferedImage loadImages() {
        BufferedImage b = null;   //initializind image array
        try {
            BufferedImage bg = ImageIO.read(Chunk.class.getResourceAsStream("pixelBackground.jpg")); //load the dirt sprite as a buffered image
            b = bg;

        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
        return b;   //return the loaded image array
    }
    
    
    
    
    
    
    public void add(MenuComponent m){
        components.add(m);
    }
    public void draw(Graphics2D g2d){
        if(drawBackground){
            g2d.drawImage(BACKGROUND,0,0,gameSurface.getWidth(),gameSurface.getHeight(),null);
        }
        
        for (int i = 0; i <components.size(); i++) {
            MenuComponent m = components.get(i);
            m.draw(g2d);
            if(m instanceof Button){
                if(((Button)m).checkClick(mx,my,clicked)){
                    ((Button)m).run();
                }
            }
        }
        if (clicked){clicked = false;}
    }
    
    public void setMousePos(double mx,double my){
        this.mx= mx;
        this.my = my;
    }
    
    public void setVisibleBackground(boolean v){
        drawBackground = v;
    }
    
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
