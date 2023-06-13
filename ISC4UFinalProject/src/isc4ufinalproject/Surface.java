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
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
    private World gameWorld;
    private int focusScreen = 0;
    private Menu titleMenu = getTitleMenu();
    private Menu deadMenu;

    public Surface() {
        super();
        newWorld();

        this.setFocusable(true);
        this.requestFocus();

        addMouseListener(titleMenu);
        //this.removeMouseListener(titleMenu);
    }

    public void newWorld() {
        if (gameWorld != null) {
            removeKeyListener(gameWorld);
            removeMouseListener(gameWorld);
        }
        gameWorld = new World(this);
        addKeyListener(gameWorld);
        addMouseListener(gameWorld);
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
                deadMenu.setMousePos(mp.x, mp.y);
                deadMenu.draw(g2d);
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

    /**
     * getter method for the title menu for the game
     *
     * @return - the completed menu
     */
    public Menu getTitleMenu() {
        Menu m = new Menu(this);
        m.setVisibleBackground(true);
        Button newWorld = new Button(850, 300, 200, 100, "New World", m);
        newWorld.setCentered(true);
        Button credits = new Button(850, 550, 200, 50, "Credits", m);
        credits.setCentered(true);
        Button cont = new Button(850, 425, 200, 100, "Continue", m);
        cont.setCentered(true);
        //setting the action for the "New world button to generate new world
        newWorld.setAction(() -> {
            //gameWorld = new World(this);
            focusScreen = 1;
        });
        //setting the action for the credits button to display the credits
        credits.setAction(() -> {
            JOptionPane.showMessageDialog(null, "David, Calum");
        });
        //setting the action for the continue button to load a save
        cont.setAction(() -> {
            this.removeMouseListener(gameWorld);
            this.removeKeyListener(gameWorld);
            try {
                FileInputStream fi = new FileInputStream(new File("save.world"));
                ObjectInputStream oi = new ObjectInputStream(fi);

                // Read objects
                World w = (World) oi.readObject();
                w.setSurface(this);
                Entity e;
                for (int i = 0; i < w.getEntities().size(); i++) {
                    e = w.getEntities().get(i);
                    e.loadImages();
                    e.setWorld(w);
                    e.setBounds(w.getBounds().get(i));
                    e.setPos(w.getPositions().get(i));
                }

                gameWorld = w;
                this.addMouseListener(gameWorld);
                this.addKeyListener(gameWorld);
                focusScreen = 1;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "World not Found");
            }
        });
        m.add(newWorld);    //adding new world button
        m.add(credits); //adding credits utton
        m.add(cont);    //adding continue button
        Label title = new Label(850, 100, 48, "Terrarium");
        title.setCentered(true);
        m.add(title);    //labels the title screen
        return m;   //returns the enu with all the added components
    }

    public Menu getDeadMenu(int coins, int khopesh, int bosses) {
        removeMouseListener(deadMenu);
        Menu m = new Menu(this);

        Label title = new Label(850, 100, 48, "You Died :(");
        title.setCentered(true);
        m.add(title);    //labels the title screen
        Label gold = new Label(850, 200, 24, "Gold collected: " + coins);
        gold.setCentered(true);
        m.add(gold);
        Label k = new Label(850, 250, 24, "Khopeshes collected: " + khopesh + "/8");
        k.setCentered(true);
        m.add(k);
        Label b = new Label(850, 300, 24, "Bosses Slain: " + bosses);
        b.setCentered(true);
        m.add(b);
        Button newWorld = new Button(850, 450, 200, 100, "Continue", m);
        newWorld.setCentered(true);
        newWorld.setAction(() -> {
            //gameWorld = new World(this);
            this.setScreen(0);
            titleMenu.setClicked(false);
            this.newWorld();
        });
        m.add(newWorld);
        focusScreen = 2;
        deadMenu =m;
        addMouseListener(deadMenu);
        return m;   //returns the enu with all the added components
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
