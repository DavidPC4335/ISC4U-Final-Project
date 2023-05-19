/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isc4ufinalproject;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author david
 */
public class ISC4UFinalProject extends JFrame{
    public ISC4UFinalProject(){
        setTitle("Final Project!");
        setSize(600,600);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //setUndecorated(true);//for Sull screen
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
