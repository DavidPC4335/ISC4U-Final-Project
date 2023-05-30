/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author dapav8190
 */
public class Menu implements MouseListener{

    private ArrayList<MenuComponent> components;
    private boolean clicked;
    private double mx,my;
    private Surface gameSurface;
    
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
    
    
    public void add(MenuComponent m){
        components.add(m);
    }
    public void draw(Graphics2D g2d){
        for (int i = 0; i <components.size(); i++) {
            MenuComponent m = components.get(i);
            m.draw(g2d);
            if(m instanceof Button){
                if(((Button)m).checkClick(mx,my,clicked)){
                    gameSurface.setScreen(1);
                }
            }
        }
        if (clicked){clicked = false;}
    }
    
    public void setMousePos(double mx,double my){
        this.mx= mx;
        this.my = my;
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
