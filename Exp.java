import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games experience for the player
 * 
 * @author Bretton Murphy
 */
public class Exp  extends PowerUp
{
    private static GreenfootImage images[];

    private int expAmount = 25;
    
    /**
     * Default constructor
     */
    public Exp()
    {
        super(images.length * 2);
        super.images = this.images;
        setImage("PowerUps/power0.png");
    }
    
    /**
     * Act - do whatever the Exp wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!theWorld.isPaused())
        {
            animate();
            move();
        }
    }
    
    /**
     * Let's the Exp know that the player's ship came in contact with this object
     */
    public void contact()
    {
        removeSelf();
    }
    
    /**
     * Loads images used for animation
     */
    public static void loadAssets()
    {
        images = new GreenfootImage[4];
        
        for(int i = 0; i < images.length; i++)
            images[i] = new GreenfootImage("PowerUps/power" + i + ".png");
    }
    
    /**
     * Get's the amount of experience this object is worth
     * 
     * @return The experience amount
     */
    public int expAmount()
    {
        return expAmount;
    }
}
