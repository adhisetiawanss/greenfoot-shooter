import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games various power ups that the player can gather.
 * Power ups can move and animate themselves.
 * 
 * @author Bretton Murphy
 */
public class PowerUp  extends Actor
{
    /**
     * The images used for animation
     */
    protected GreenfootImage images[];
    
    /**
     * The worlds reference
     */
    protected myWorld theWorld;
    
    private int timer;
    private int delay;
    
    private int moveSpeed;
    
    private int index = 0;
    
    /**
     * Constructor that creates a new power up with a specified amount used as a 
     * delay for animations.
     * 
     * @param delayAmount The amount by which to delay animations
     */
    public PowerUp(int delayAmount)
    {
        timer = delay = delayAmount;
        moveSpeed = Level.scrollSpeed();
    }
    
    /**
    * This method is called by the Greenfoot system when the object has been inserted into the world.
    * 
    * @param world The world this object was added to.
    */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld) world;    
    }
    
    /**
     * Removes self from the world
     */
    protected void removeSelf()
    {
        getWorld().removeObject(this);
    }
    
    /**
     * Moves across the screen from right to left 
     */
    protected void move()
    {
        setLocation(getX() - moveSpeed, getY());
        
        if(getX() < 0)
            removeSelf();
    }

    /**
     * Animates one self by cycling though it's own images
     */
    protected void animate()
    {
        //Slows down animation so it doesn't execute too fast
        if(timer == 0)
        {
            index = (index + 1) % images.length;
            setImage(images[index]);
        }

        timer = (timer + 1) % delay;
    }
}
