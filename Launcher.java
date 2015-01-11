import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A type of enemy that is capable of moving around the screen, shooting at the player,
 * and droping Exp for the player to pick up.
 * 
 * @author Bretton Murphy
 */
public class Launcher  extends Enemy
{
    private static GreenfootImage[] images;
    
    public int index = 0;
   
    /**
     * Constructor that records the newly created launcher's x and y location
     * 
     * @param x The launcher's x location
     * @param y The launcher's y location
     */
    public Launcher(int x, int y)
    {
        super(images.length);
        super.images = this.images;
        setImage(images[0]);
        
        xPose = x;
        yPose = y;
        
        health = 1;
    }
    
    /**
     * Constructor that records the newly created launcher's x and y location and 
     * tells the enemy how fast it's bullets will travel. The pattern it
     * will move across the screen with is also given.
     * 
     * @param x The launcher's x location
     * @param y The launcher's y location
     * @param bulletspeed The launcher's bullet travel speed
     * @param pattern The pattern for which the launcher's movement will emulate
     */
    public Launcher(int x, int y, int bulletspeed, int[][] pattern)
    {
        super(images.length);
        super.images = this.images;
        setImage(images[0]);
        
        xPose = x;
        yPose = y;
        bulletSpeed = bulletspeed;
        movePattern = pattern;

        health = 1;
    }
    
    /**
     * Constructor that records the newly created launcher's x and y location and 
     * tells the enemy how fast it's bullets will travel. The pattern it
     * will move across the screen with is also given as well as a Exp object for it
     * to drop once dead.
     * 
     * @param x The launcher's x location
     * @param y The launcher's y location
     * @param bulletspeed The launcher's bullet travel speed
     * @param pattern The pattern for which the launcher's movement will emulate
     * @param exp The Exp object that will be added to the world once dead
     */
    public Launcher(int x, int y, int bulletspeed, int[][] pattern, Exp exp)
    {
        super(images.length);
        super.images = this.images;
        setImage(images[0]);
        
        xPose = x;
        yPose = y;
        bulletSpeed = bulletspeed;
        movePattern = pattern;
        expDrop = exp;
        
        state = State.OPEN;
        
        health = 1;
    }
    
    /**
     * Act - do whatever the Cloud wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        /*
        
        if(!theWorld.isPaused())
        {
            switch(state)
            {
                case OPEN:
                    animateOpen();
                    break;
                    
                case SHOOT:
                    shoot();
                    break;
                    
                case CLOSE:
                    animateClose();
                    break;
                    
                default:
                    System.err.println("Incorrect launcher state!!");
                    
                animate();
                shoot();
                move();
            }
        }
        
        */
    }

    /**
     * Loads the needed images for animation
     */
    public static void loadImages()
    {
        images = new GreenfootImage[9];
        
        for(int i = 0; i < images.length; i++)
            images[i] = new GreenfootImage("Enemy/launcher" + i + ".png");  
    }
    
    /*
    
    public static void animateOpen()
    {
        if(index > 4)
        {
            index = (index + 1) % images.length;  
            setImage(images[index]);
        }
        
        if(index = 4)
        {
            index = (index + 1) % images.length;  
            setImage(images[index]);
            state = State.SHOOT;
        }
    }
    
    public static void animateClose()
    {
        if(index < 4 && index > 8)
        {
            index = (index + 1) % images.length;  
            setImage(images[index]);
        }
        
        if(index = 8)
        {
            index = (index + 1) % images.length;  
            setImage(images[index]);
            state = State.CLOSE;
        }
    }
    
    */
}
