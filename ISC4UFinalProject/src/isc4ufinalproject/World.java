/*
David PC and Calum M
5/29/2023
World clas that Contains all of the attributes of the world and the player that explores it. The world is what gets saved 
 */
package isc4ufinalproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class World implements KeyListener, MouseListener, Serializable {

    //declaring and initializing private variables and final variables
    private Surface surface;
    private double player_screen_y = 540, player_screen_x = 930;//playerX and player YU
    private double mx, my;
    private double xmove = 0, ymove = 0, moveSpeed = 1;
    public static final int WIDTH = 800 * 50, playerStartX = 930;
    public static final int HEIGHT = 3200;
    private Player player;
    private boolean clicked = false;
    private boolean canPlace = true;
    //as of now the world is 50 chunks wide
    private Chunk[] chunks = new Chunk[50];
    private Chunk[] drawChunks;
    public static String debugMessage = "";
    private ArrayList<Entity> entities = new ArrayList();
    private ArrayList<Particle> particles;
    //visuals
    private ArrayList<Point> positions = new ArrayList();
    private ArrayList<Rectangle> bounds = new ArrayList();
    private static Image background, heart;
    private char pressedChar = 0;
    private Item[] inventory = new Item[12];
    private boolean showInventory = false, init = false;
    private int selected = 0, swingFrames = 0, startSwing = 30;
    private static Button saveBtn = new Button(10, 250, 150, 50, "Save and Exit");
    private int bosses =0;
    /**
     * constructor method for the world
     *
     * @param surface - the surface that the world exists on
     */
    public World(Surface surface) {
        loadImages();
        particles = new ArrayList();
        this.surface = surface;
        chunks = Chunk.generateWorld(25, chunks, 0);//generating world
        player = new Player(WIDTH / 2, 0, this);
        entities.add(player);

        inventory[0] = Item.PICKAXE;
        inventory[1] = Item.SWORD;

        for (int i = 0; i < chunks.length; i++) {
            if (chunks[i].isCastle()) {
                entities.add(new PickupItem((i * Chunk.WIDTH) + Chunk.WIDTH / 2, Chunk.HEIGHT / 2, Item.KHOPESH, this));
            }
        }

    }
    /*
    sets surace for the world(used for loading)
    */
    public void setSurface(Surface s){
        surface = s;
    }
    
    /**
     * loads images used (heart and background)
     */
    public static void loadImages() {
        background = Menu.BACKGROUND;
        try {
            heart = ImageIO.read(Chunk.class.getResourceAsStream("heart.png")); //load the heart sprite as a buffered image
        } catch (IOException e) {   //catch if image can't be read
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * draw method for the world and attributes
     *
     * @param g2d - the graphic to draw the world
     */
    public void draw(Graphics2D g2d) {
       if (!init) {//init player position
            player_screen_x = surface.getWidth() / 2;
            player_screen_y = surface.getHeight() / 2;
            init = true;
        }
        /*DRAWING BACKGROUND*/
        g2d.drawImage(background, 0, 0, surface.getWidth(), surface.getHeight(), null);
        /*DEBUG MESSAGES*/
       // debugMessage += "(X,Y): (" + (int) player.getX() + "," + (int) player.getY() + ") \t" + Chunk.Y;
        //g2d.drawString(new Point((int)mx,(int)my).toString(), (int)mx, (int)my);
        g2d.setColor(Color.white);
        //g2d.drawString(debugMessage, 10, 10);
        // g2d.drawString(mx+","+my,(int)mx,(int)my);
        debugMessage = "";
        drawWorld(g2d);//draws the world

        player.move(xmove, ymove); //moves the player
        player.setScreenPos((int) player_screen_x, (int) player_screen_y);//updates players screen position
        player.draw(g2d);//draws the player
        drawSwing(g2d);//draws swing weapon
        drawParticles(g2d);//draws particles
        drawUI(g2d);//draws UI

    }
/**
 * draws swing of player's tool
 * NOTE - there is some unknown issue with AfflineTransform that offsets the position when game is running certian aspect ratios(unable to resolve)
 * @param g2d 
 */
    public void drawSwing(Graphics2D g2d) {

        if (swingFrames > 0 && inventory[selected] != null) {//if can swing item

            if (inventory[selected].canMine() || inventory[selected].canAttack()) {//if item can mine or attack
                double scale = inventory[selected].getDrawScale();//get size of wepoon
                AffineTransform backup = g2d.getTransform();//save OG transform
                int dx = (int) player_screen_x + (10) - (int) (30 * scale - 30), dy = (int) player_screen_y - 30 - (int) ((50 * scale) - 50);
                
                debugMessage+="("+player.getScreenX()+","+player.getScreenY()+")";
                double swing = startSwing / 10 - (double) swingFrames / 10;//get the angle to swing
                if (player.facing == -1) {//include offset for backwards swing
                    dx -= 45;
                    swing = (double) swingFrames / 10;
                    swing -= Math.PI;
                }
                if (swingFrames == startSwing / 2) {
                    player.attack(inventory[selected].getDamage(), (int) (60 * scale * player.facing));
                }
                AffineTransform a = AffineTransform.getRotateInstance(swing, dx + 30 * scale, dy + 50 * scale);
                g2d.setTransform(a);//apply transformation
                //Draw our image like normal
                debugMessage+="("+dx+","+dy+")";
                g2d.drawImage(inventory[selected].getImage(), dx, dy, (int) (60 * scale), (int) (60 * scale), null);
                //Reset our graphics object so we can draw with it again.
                g2d.setTransform(backup);
            }
            swingFrames--;
        } else if (swingFrames > 0) {
            swingFrames--;
        }
    }
/**
 * getter for points
 * @return 
 */
    public ArrayList<Point> getPositions(){
        return positions;
    }
    /**
     * getter for bounds
     * @return 
     */
     public ArrayList<Rectangle> getBounds(){
        return bounds;
    }
     /**
      * draws the user interface and updates inventory systems
      * @param g2d graphics to draw
      */
    public void drawUI(Graphics2D g2d) {
        //drawing hotbar
        int dx, dy = 20;
        int i;

        if (showInventory) {//if inventory
            for (i = 0; i < inventory.length; i++) {
                dx = 20 + ((i % 4) * 60);
                if (i % 4 == 0 && i > 0) {
                    dy += 60;
                }

                g2d.setColor(Color.gray.brighter());//set square color
                g2d.fillRect(dx, dy, 50, 50);//draw rect
                if (inventory[i] != null) {
                    g2d.drawImage(inventory[i].getImage(), dx, dy, 50, 50, null);

                }
                Font font = new Font("Consolas", Font.BOLD, 20);    //create new font of desired size
                g2d.setFont(font); //apply font to g2d
                g2d.setColor(Color.black);
                g2d.drawString((i + 1) + "", dx, dy + 15);
                if (i == selected) {//if selected draw outline
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setColor(Color.YELLOW);
                    g2d.drawRect(dx, dy, 50, 50);
                    g2d.setStroke(new BasicStroke(2));
                }
                g2d.setFont(new Font("Consolas", Font.BOLD, 15));
                g2d.setColor(Color.white);

                g2d.drawString("1-4: Assign Hotbar Slot", 270, 125);//drawing tooltips
                g2d.drawString("O : Destroy Item", 270, 155);
                g2d.drawString("S : Sort inventory (by Name)", 270, 175);
                g2d.drawString("P : Quick Pickaxe", 270, 195);
                if(inventory[i] != null){g2d.drawString(inventory[i].getStack() + "", dx + 35, dy + 45);}
                if (new Rectangle(dx, dy, 50, 50).contains(new Point((int) mx, (int) my))) {

                    if (inventory[i] != null) {
                        
                        if (clicked) {
                            selected = i;
                        }
                        if (pressedChar == 'o') {
                            pressedChar = 0;
                            inventory[i] = null;
                        } else {
                            try {//checking if user swapped item to hotBar
                                int num = Integer.parseInt(pressedChar + "");
                                if (num <= 4 && num > 0) {//if number is 1-4
                                    Item temp;
                                    temp = inventory[num - 1];
                                    inventory[num - 1] = inventory[i];
                                    inventory[i] = temp;//swap item to hotBar
                                    pressedChar = 0;
                                }
                            } catch (NumberFormatException e) {

                            }
                        }
                        g2d.drawString(inventory[i].getDescription(), 270, 75);
                        Font sFont = new Font("Consolas", Font.BOLD, 20);    //create new font of desired size
                        g2d.setFont(sFont); //apply font to g2d
                        g2d.drawString(inventory[i].getName(), 270, 50);

                        //create new font of desired size
                    }

                }
            }
            saveBtn.draw(g2d);//draw saveButton
            /**
             * saving world
             */
            if (saveBtn.checkClick(mx, my, clicked)) {//checking saveBtn and saving f clicked
                clicked = false;
                try {
                    for (int j = 0; j < entities.size(); j++) {
                        positions.add(j,new Point((int)entities.get(j).getX(),(int)entities.get(j).getY()));//saving entity positions
                        bounds.add(j,entities.get(j).getBounds());
                    }
                    surface.setScreen(0);
                    surface = null;
                    FileOutputStream f = new FileOutputStream(new File("save.world"));
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    
                    o.writeObject(this);
                    o.close();
                    f.close();
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        } else {
            for (i = 0; i < 4; i++) {
                dx = 20 + (i * 60);
                g2d.setColor(Color.lightGray);

                g2d.fillRect(dx, dy, 50, 50);
                if (inventory[i] != null) {
                    g2d.drawImage(inventory[i].getImage(), dx, dy, 50, 50, null);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(inventory[i].getStack() + "", dx + 35, dy + 45);
                }
                Font font = new Font("Consolas", Font.BOLD, 20);    //create new font of desired size
                g2d.setFont(font); //apply font to g2d
                g2d.setColor(Color.black);
                g2d.drawString((i + 1) + "", dx, dy + 15);
                if (i == selected) {
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setColor(Color.YELLOW);
                    g2d.drawRect(dx, dy, 50, 50);
                    g2d.setStroke(new BasicStroke(2));
                }

                g2d.setFont(new Font("Consolas", Font.PLAIN, 15));
                g2d.setColor(Color.white);
                g2d.drawString("i: Open Inventory", 270, 35);
            }
        }
        dy = 20;
        //drawing hearts
        for (int j = 0; j < player.getHP(); j++) {
            if (j % 5 == 0 && j != 0) {
                dy += 40;
            }
            g2d.drawImage(heart, ((int)(player_screen_x*2) - 250) + (j % 5) * 50, dy, 40, 40, null);
        }
    }
    /**
     * get inventory
     * @return inventory
     */
    public Item[] getInventory(){
        return inventory;
    }
    /**
     * drws the particles in world(particles are not entites so they are more efficent to draw..)
     * @param g2d 
     */
    public void drawParticles(Graphics2D g2d) {
        ArrayList<Particle> remove = new ArrayList();
        //addParticles(500,500,10,Color.red);
        Particle p;
        for (int i = 0; i < particles.size(); i++) {
            p = particles.get(i);

            if (p.getActive()) {

                p.draw(g2d);//draw particle

            } else {
                remove.add(p);//if done remove particle
            }
        }
        for (Particle pa : remove) {
            particles.remove(pa);//remove all particles
        }

    }
/**
 * sorts array passed alphabeticly
 * @param a array to sort
 */
    public static void insertionSort(Item[] a){
        int j;
        for (int i = 1; i < a.length; i++) {
            j=i;
            while(j>0 && a[j-i].getName().compareTo(a[j].getName()) > 0){
            //swaps j and j-1
            Item t = a[j];
            a[j] = a[j-1];
            a[j-1] = t;
            j--;
            }
        }
    }
    /**
     * searches inventory for item 
     * @param i item
     * @return index of item
     */
public int linearSearch(Item i){
        for (int j = 0; j < inventory.length; j++) {
            if(inventory[j] != null){
            if(inventory[j].equals(i)){
                return j;
            }
            }
        }
        return -1;
    }
/**
 * update world for world border collisions
 * @param i chunk index player is on
 * @param x x of player
 * @return updated chunk index
 */
    public int updateWorld(int i, double x) {
        if ((i == 49 || i == 0) && x < 500) {//edge cases for end of world
            i = 0;

            if (player_screen_x <= playerStartX) {
                player_screen_x += player.getXSpd();
            } else {
                player_screen_x = playerStartX;//update player pan across screen
            }
            drawChunks[0] = chunks[0];
            drawChunks[1] = chunks[1];//hard set drawn chunks
            drawChunks[2] = chunks[2];
        } else if (i >= 47 && x > 39000 - player_screen_x) {//edge cases for end of world
            i = 47;

            if (player_screen_x >= playerStartX) {
                player_screen_x += player.getXSpd();//update player pan across screen
            } else {
                player_screen_x = playerStartX;
            }
            drawChunks[0] = chunks[47];
            drawChunks[1] = chunks[48];//hard set drawn chunks
            drawChunks[2] = chunks[49];
        }
        return i;
    }
    /**
     * checks collisions of entity to world
     * @param e enttiy to check
     * @param xoff x offset 
     * @param yoff y offset
     * @return if entity collides with world
     */
    public boolean checkCollision(Entity e, double xoff, double yoff) {
        boolean collided = false;
        int roundX = (int) Math.round(e.getX() + xoff);
        int roundY = (int) Math.round(e.getY() + yoff);//gets indexes to check colliding given offsets
        int i = (roundX % Chunk.WIDTH) / Chunk.tSize;
        int j = (roundY + Chunk.Y) / Chunk.tSize;
        int tempi = i;
        int chunkI = getChunki(roundX);
        Chunk chunkOn = chunks[chunkI];

        Rectangle bounds = e.getBounds();//getting bounds
        //checking Y collision(checks all of bounds)
        for (int k = 0; k <= (bounds.getWidth() / Chunk.tSize); k++) {//repeat for height
            chunkOn = chunks[getChunki(e.getX() + xoff + (Chunk.tSize * k))];
            tempi = ((roundX + (Chunk.tSize * (k))) % Chunk.WIDTH) / Chunk.tSize;

            if (yoff > 0) {
                if (chunkOn.getSolid(tempi, j + (int) (bounds.getHeight() / Chunk.tSize))) {
                    collided = true;
                }
            } else if (yoff < 0) {
                if (chunkOn.getSolid(tempi, j)) {
                    collided = true;
                }
            }
        }
        //checking X collision
        for (int k = 0; k <= (bounds.getHeight() / Chunk.tSize); k++) {//repeat for height

            chunkOn = chunks[getChunki(roundX)];//update chunk incase of boarder
            if (xoff > 0) {//if xoff> 0

                //tempi = (i)%25;
                if (chunkOn.getSolid(i, j + k)) {
                    collided = true;
                }
            } else {
                if (chunkOn.getSolid(i, j + k)) {
                    collided = true;
                }
            }
        }

        return collided;//return collided or not
    }
/**
 * return bosses killed
 * @return bosses killed
 */
    public int getBosses(){
        return bosses;
    }
    
    /**
     * draws the chunks of the world with player-focused camera and updates building and mining
     * @param g2d draw surfcace
     */
    public void drawWorld(Graphics2D g2d) {

        //drawing world
        double chunkScreenX, chunkScreenY;

        //player_screen_x+=xmove;
        double x = player.getX() - player_screen_x;
        double y = player.getY();
        int i = getChunki(x);
        drawChunks = getVisibleChunks(x);

        i = updateWorld(i, x);
        chunkScreenX = ((i * Chunk.WIDTH) - x);//get the chunk X on the screen
        chunkScreenY = (Chunk.Y - y) + player_screen_y;//get the chunkY on the screen

        Entity e;
        //setpping entities
        double dx = ((i * Chunk.WIDTH) - x);//get the player X on the screen
        double dy = (Chunk.Y - y) + player_screen_y;
        if (y > 1090) {
            g2d.drawImage(Chunk.tile_images[0], 0, (int) chunkScreenY + Chunk.HEIGHT, surface.getWidth() - 10, surface.getHeight() / 2, null);
        }
        for (int j = 0; j < entities.size(); j++) {//step all entities
            e = entities.get(j);
            if (!(e instanceof Player)) {
                e.setScreenPos((int) ((e.getX()) - x), (int) ((e.getY() - y) + player_screen_y));
                e.draw(g2d);
            }
            e.step();
        }

        int index;
        for (int j = 0; j < 4; j++) {
            //System.out.println(chunkScreFenX + (Chunk.WIDTH * (j - 1)));
            drawChunks[j].draw(g2d, chunkScreenX + (Chunk.WIDTH * (j)), chunkScreenY);
            //g2d.drawString((i+j)+"",(int)chunkScreenX+500+(Chunk.WIDTH * (j)),500);
        }
        //handling placing and mining
        Point m = getMouseScreenPos();

        int j = (m.x % Chunk.WIDTH) / Chunk.tSize;
        int k = (m.y + Chunk.Y) / Chunk.tSize;
        int pi = ((int) player.getX() % Chunk.WIDTH) / Chunk.tSize;
        int pj = ((int) player.getY() + Chunk.Y) / Chunk.tSize;
        debugMessage += "(" + pi + "," + pj + ")";
        int pIndex = getChunki(player.getX());
        if (chunks[pIndex].isCastle()) {//checking for castle enemy spawn
            if (pi > 11 && pi < 17 && pj > 20) {
                int by = chunks[pIndex].getHeight() * Chunk.tSize;
                entities.add(new Bomber(player.getX() + 200, by, this));
                entities.add(new Bomber(player.getX() - 200, by, this));

                chunks[pIndex].setCastle(false);
            }
        }

        int mi = getChunki(m.x);
        if (inventory[selected] != null) {//checking inventory item, interactions
            if (inventory[selected].canAttack()) {//checking if weapon
                if (clicked && swingFrames <= 0) {
                    swingFrames = 20;//attacking if weapon
                    startSwing = swingFrames;
                }
            } else {
                if (m.distance(new Point((int) player.getX(), (int) player.getY())) < 400) {//if close enough to player
                    g2d.setColor(Color.yellow);
                    g2d.drawRect((int) (mi * Chunk.WIDTH - x + (j * Chunk.tSize)), (int) ((k * Chunk.tSize) - y + player_screen_y), Chunk.tSize, Chunk.tSize);
                    g2d.setColor(Color.BLACK);

                    if (clicked) {//if clicked
                        clicked = false;
                        if (chunks[mi].get(j,k) > 0) {
                            if (inventory[selected].canMine()) {//if pickaxe
                                swingFrames = 30;
                                startSwing = swingFrames;
                                int minedBlock = chunks[mi].remove(j, k);
                                entities.add(new PickupItem((m.x / 32) * 32, (m.y / 32) * 32, Item.blocks[minedBlock], this));
                                if(minedBlock == 6){//if gravestone mined spawn a zombie
                                   entities.add(new Zombie((m.x / 32) * 32, (m.y / 32) * 32 +45, this)); 
                                }
                            }

                        } else {//placing
                            if (inventory[selected].canPlace() && chunks[mi].canPlace(j,k)) {
                                
                                chunks[mi].place(j, k, inventory[selected].getIndex());
                                if (inventory[selected].getIndex() == 6){
                                    boolean isStack = true;
                                    for (int l = 0; l < 5; l++) {
                                        if(chunks[mi].get(j,k+l) != 6){
                                            isStack = false;
                                        }
                                    }
                                    if(isStack){
                                        for (int l = 0; l < 5; l++) {
                                        chunks[mi].remove(j,k+l);
                                        }
                                        Zombie z = new Zombie((m.x / 32) * 32 - 50, (m.y / 32) * 32 +220, this);
                                        z.getBounds().setSize(100, 200);
                                        z.setHP(30);
                                        z.isBoss(true);
                                        entities.add(z);
                                    }
                                    }
                                
                                inventory[selected].setStack(-1);
                                if (inventory[selected].getStack() <= 0) {
                                    inventory[selected] = null;
                                }
                                
                            
                            }
                        }
                    }
                }
            }
        }

    }
    /**
     * gets visible chunks at position
     * @param x x to check
     * @return chunks visible
     */
    public Chunk[] getVisibleChunks(double x) {
        int x2 = (int) Math.round(x);
        Chunk[] list = new Chunk[4];
        for (int i = 0; i < list.length; i++) {
            list[i] = chunks[getChunki(x + (Chunk.WIDTH * (i)))];
        }
        return list;

    }
    /**
     * gets chunk currently on at x position
     * @param x
     * @return 
     */
    public int getChunki(double x) {
        int x2 = (int) Math.round(x);
        if (x2 < 0) {
            x2 = World.WIDTH - 1;
        } else if (x2 > World.WIDTH) {
            x2 = 0;
        }
        int i = x2 / Chunk.WIDTH;
        if (i == 50) {
            return 49;
        }
        return i;
    }
/**
 * removes entity
 * @param e 
 */
    public void remove(Entity e) {
        entities.remove(e);
    }
    /**
     * adds item
     * @param i item
     * @return if full
     */
    public boolean addItem(Item i) {
        for (int j = 0; j < inventory.length; j++) {
            if (inventory[j] != null) {
                if (inventory[j].equals(i)) {
                    inventory[j].setStack(1);
                    return true;
                }
            }
        }
        for (int j = 0; j < inventory.length; j++) {
            if (inventory[j] == null) {
                inventory[j] = i;
                return true;
            }
        }

        return false;
    }
/**
 * gets player
 * @return 
 */
    public Player getPlayer() {
        return player;
    }
/**
 * steps bosses killed
 */
    public void killBoss(){
        bosses++;
    }
    
    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyPressed(KeyEvent e) {
        pressedChar = e.getKeyChar();
        switch (e.getKeyChar()) {
            case 'd':
                xmove = moveSpeed;
                break;
            case 'a':
                xmove = -moveSpeed;
                break;
            case 'w':
                //ymove = -moveSpeed;
                break;
            case '1':
                selected = 0;

                break;
            case '2':
                selected = 1;

                break;
            case '3':
                selected = 2;
                //ymove = moveSpeed;
                break;
            case '4':
                selected = 3;
                //ymove = moveSpeed;
                break;
            case 'i':
                showInventory = !showInventory;
                break;
            case 'b':
                //entities.add(new Zombie(player.getX()+200,player.getY(),this));
                break;
            case 'p':
                int i = linearSearch(Item.PICKAXE);
                if(i>-1){
                selected = i;
                        }
                break;
            case 's':
                insertionSort(inventory);
                break;
        }

    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyChar()) {
            case 'd':
                xmove = 0;
                break;
            case 'a':
                xmove = 0;
                break;
            case 'w':
                ymove = 0;
                break;
            case 's':
                ymove = 0;
                break;
        }
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {

            case 'w':

                if (checkCollision(player, 0, 1)) {
                    player.yspd = -8;
                }
                break;
        }

    }

    public void addParticles(int x, int y, int num, Color col) {
        for (int i = 0; i < num; i++) {

            particles.add(new Particle(x, y + (i * 2), col));

        }

    }
/**
 * gets particles
 * @return 
 */
    public ArrayList<Particle> getParticles() {
        return particles;
    }
/**
 * sets mouse Pos
 * @param x
 * @param y 
 */
    public void setMousePos(double x, double y) {
        this.mx = x;
        this.my = y;
    }
/**
 * gets current world mouse Pos
 * @return 
 */
    public Point getMouseScreenPos() {
        return new Point((int) (player.getX() + mx - player_screen_x), (int) (player.getY() + my - player_screen_y));
    }
/**
 * gets surface
 * @return surface
 */
    public Surface getSurface() {
        return surface;
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;

    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * abstract mentod from the listener that reads user inputs
     *
     * @param e event passed from user
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
