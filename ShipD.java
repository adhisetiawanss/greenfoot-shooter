import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An affect that is used for when the players ship explodes.
 * 
 * @author Bretton Murphy 
 */
public class ShipD  extends Affect
{
    private static GreenfootImage images[];    

    /**
     * Default constructor
     */
    public ShipD()
    {
        super(images.length);
        super.images = this.images;
        setImage("Affects/shipD0.png");
        Greenfoot.playSound("sounds/shipD.mp3");
    }
    
    /**
     * Act - do whatever the shipD wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!theWorld.isPaused())
            animateOnce();
    }    
    
    /**
     * Loads the images needed for animation
     */
    public static void loadAssets()
    {
        images = new GreenfootImage[10];
        
        for(int i = 0; i < images.length; i++)
            images[i] = new GreenfootImage("Affects/shipD" + i + ".png");
    }
}
