/*
Calum M., David P.C.
May 26 2023
Abstract MenuComponent Class
 */
package isc4ufinalproject;

import java.awt.Graphics2D;

/**
 *
 * @author camac0688
 */
public abstract class MenuComponent {
    
    protected int x,y;  //declaring protected ints
    
    public abstract void draw(Graphics2D g);
    
}
