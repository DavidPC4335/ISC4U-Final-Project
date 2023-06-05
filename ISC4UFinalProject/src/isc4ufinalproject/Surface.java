/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author dapav8190
 */
public class Surface extends JPanel implements Runnable {

    private Thread animator;
    private final int DELAY = 15;
    private Point mp;
    private World gameWorld = new World(this);
    private int focusScreen = 0;
    private Menu titleMenu = getTitleMenu();

    public Surface() {

        super();
        this.setFocusable(true);
        this.requestFocus();
        addKeyListener(gameWorld);
        addMouseListener(gameWorld);
        addMouseListener(titleMenu);
        //this.removeMouseListener(titleMenu);
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();

    }

    /**
     * draws scene()
     *
     * @param g
     */
    public void draw(Graphics g) {
        //draw here

        Graphics2D g2d = (Graphics2D) g;
        mp = getMousePos();
        switch (focusScreen) {
            case 0:

                titleMenu.setMousePos(mp.x, mp.y);
                titleMenu.draw(g2d);
                break;
            case 1:

                gameWorld.setMousePos(mp.x, mp.y);
                gameWorld.draw(g2d);
                break;
            case 2:

                break;
        }
    }

    public Point getMousePos() {
        //getting mouse position in frame
        Point p = MouseInfo.getPointerInfo().getLocation();
        Point p2 = new Point(0, 0);
        //catches if running before displayed
        try {
            p2 = this.getLocationOnScreen();
        } catch (IllegalComponentStateException e) {
        }
        Point mp = new Point(p.x - p2.x, p.y - p2.y);
        return mp;
    }

    public void run() {
        long dt, pt, sleep = 0;
        pt = System.currentTimeMillis();
        while (true) {

            //Running code 
            repaint();

            dt = System.currentTimeMillis() - pt;

            sleep = DELAY - dt;
            if (sleep < 2) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            pt = System.currentTimeMillis();
        }
    }

    public Menu getTitleMenu() {
        Menu m = new Menu(this);
        m.setVisibleBackground(true);

        Button newWorld = new Button(920, 500, 200, 100, "New World", m);
        Button credits = new Button(920, 750, 200, 50, "Credits", m);


        //setting the action for the "New world button to generate new world
        newWorld.setAction(() -> {
            focusScreen = 1;
        });
        //setting the action for the credits button to display the credits
        credits.setAction(() -> {
            JOptionPane.showMessageDialog(null, "David, Calum");
        });
        m.add(newWorld);
        m.add(credits);
        m.add(new Label(850, 100, 48, "Game name here"));
        return m;
    }

    public void setScreen(int i) {
        focusScreen = i;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

}
