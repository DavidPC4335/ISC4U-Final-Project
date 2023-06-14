/*
Calum MacKenzie, David Pavlove Cunsolo
May 18 2023 - 
Main class for ICS 4U final project
 */
package isc4ufinalproject;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author david
 */
public class ISC4UFinalProject extends JFrame {

    public ISC4UFinalProject() {
        URL imageURL = getClass().getResource("coin.png");
        Image icon = new ImageIcon(imageURL).getImage();
        setIconImage(icon);
        setTitle("Final Project!");
        setSize(600, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);//for Sull screen
        add(new Surface());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //makes sure that GUI updates nicely with the rest of the OS
        EventQueue.invokeLater(() -> {
            ISC4UFinalProject ex = new ISC4UFinalProject();
            ex.setVisible(true);
        });
    }
}
