/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isc4ufinalproject;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author camac0688
 */
public class Button extends MenuComponent {

    private Rectangle2D bounds;
    private boolean hovered;

    public Button() {

    }

    public void hovering() {
        if (bounds.contains(mouseX, mouseY)) {

        }
    }

    public boolean checkClick(double mouseX, double mouseY, boolean clicked) {
        hovering();

    }

}
