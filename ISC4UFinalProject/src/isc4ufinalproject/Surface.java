/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author dapav8190
 */
public class Surface extends JPanel implements Runnable{
    private Thread animator;
    private final int DELAY = 15;
    
    private World gameWorld = new World();
    
    public Surface(){
        super();
        this.setFocusable(true);
        this.requestFocus();
        addKeyListener(gameWorld);
        addMouseListener(gameWorld);
        
    }
    public void addNotify(){
        super.addNotify();
        animator = new Thread(this);
        animator.start();
        
       
    }
    
    /**
     * draws scene()
     * @param g 
     */
    public void draw(Graphics g){
        //draw here
        Graphics2D g2d = (Graphics2D)g;
        gameWorld.draw(g2d);
        
    }
    public void run(){
        long dt,pt,sleep=0;
        pt = System.currentTimeMillis();
        while(true){
            
            //Running code 
            repaint();
            
            
            dt = System.currentTimeMillis()-pt;
            
            sleep = DELAY-dt;
            if(sleep<2){sleep =2;}
            
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            pt = System.currentTimeMillis();
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
 


}
