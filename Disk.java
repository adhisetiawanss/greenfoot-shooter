import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A type of enemy that is capable of moving around the screen, shooting at the player,
 * and droping Exp for the player to pick up.
 * 
 * @author Bretton Murphy
 */
public class Disk  extends Enemy
{
    private static GreenfootImage[] images;
   
    /**
     * Constructor that records the newly created disk's x and y location
     * 
     * @param x The disk's x location
     * @param y The disk's y location
     */
    public Disk(int x, int y)
    {
        super(images.length);
        super.images = this.images;
        setImage(images[0]);
        
        xPose = x;
        yPose = y;
        
        health = 1;
    }
    
    /**
     * Constructor that records the newly created disk's x and y location and 
     * tells the enemy how fast it's bullets will travel. The pattern it
     * will move across the screen with is also given.
     * 
     * @param x The disk's x location
     * @param y The disk's y location
     * @param bulletspeed The disk's bullet travel speed
     * @param pattern The pattern for which the disk's movement will emulate
     */
    public Disk(int x, int y, int bulletspeed, int[][] pattern)
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
     * Constructor that records the newly created disk's x and y location and 
     * tells the enemy how fast it's bullets will travel. The pattern it
     * will move across the screen with is also given as well as a Exp object for it
     * to drop once dead.
     * 
     * @param x The disk's x location
     * @param y The disk's y location
     * @param bulletspeed The disk's bullet travel speed
     * @param pattern The pattern for which the disk's movement will emulate
     * @param exp The Exp object that will be added to the world once dead
     */
    public Disk(int x, int y, int bulletspeed, int[][] pattern, Exp exp)
    {
        super(images.length);
        super.images = this.images;
        setImage(images[0]);
        
        xPose = x;
        yPose = y;
        bulletSpeed = bulletspeed;
        movePattern = pattern;
        expDrop = exp;
        
        health = 1;
    }
    
    /**
     * Act - do whatever the Cloud wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!theWorld.isPaused())
        {
            animate();
            shoot();
            move();
        }
    }

    /**
     * Loads the needed images for animation
     */
    public static void loadImages()
    {
        images = new GreenfootImage[6];
        
        for(int i = 0; i < images.length; i++)
            images[i] = new GreenfootImage("Enemy/disk" + i + ".png");  
    }
}
