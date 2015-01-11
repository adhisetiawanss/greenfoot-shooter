import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games Special power up
 * 
 * @author Bretton Murphy
 */
public class Special  extends PowerUp
{
    private static GreenfootImage images[];
    
    /**
     * Default constructor
     */
    public Special()
    {
        super(images.length * 2);
        super.images = this.images;
        setImage("PowerUps/special0.png");
    }
    
    /**
     * Act - do whatever the Special wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!theWorld.isPaused())
            animate();
    }    
    
    /**
     * Loads images used for animation
     */
    public static void loadAssets()
    {
        images = new GreenfootImage[4];
        
        for(int i = 0; i < images.length; i++)
            images[i] = new GreenfootImage("PowerUps/special" + i + ".png");
    }
}
