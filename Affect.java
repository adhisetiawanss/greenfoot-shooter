import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that represents in game effects.  Effects such as smoke or explosions. 
 * 
 * @author Bretton Murphy 
 */
public class Affect  extends Actor
{
    /**
     * The animation images for an affect
     */
    protected GreenfootImage[] images;  

    /**
     * The worlds object reference
     */
    protected myWorld theWorld;
    
    /**
     * The amount this affect moves by every iteration of the game
     */
    protected int moveOffset;

    private int delay;
    private int timer;
    private int index = 0;
    
    private int damage;
    
    private int transparency;

    /**
     * Creates an affect.
     * 
     * @param delayAmount The delay amount of an affect animation.  A 0 will result in no delay.
     */
    public Affect(int delayAmount)
    {
        timer = delay = delayAmount;
        transparency = 255;
    }
    
    /**
     * Creates an affect.
     * 
     * @param delayAmount The delay amount of an affect animation.  A 0 will result in no delay.
     */
    public Affect(int delayAmount, int damage)
    {
        timer = delay = delayAmount;
        this.damage = damage;
    }

    /**
     * Creates an affect that uses a series of images in order to animate itself and an amount by which to delay the animations by.  If the delay is set to 0
     * then there is no delay.
     * 
     * @param baseName The base image name for the weapons animation.
     * @param numOfImages The number of images that are required for the animation.
     * @param suffix The images suffix in the form of ".png" 
     * @param delayAmount The delay amount of an affect animation.  A 0 will result in no delay.
     */
    public Affect(String baseName, int numOfImages, String suffix, int delayAmount)
    {
        timer = delay = delayAmount;
        loadImages(baseName, numOfImages, suffix);    
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
     * Animates an affect by cycling through it's images.
     */
    protected void animate()
    {
        if(timer == 0)
        {
            index = (index + 1) % images.length;
            setImage(images[index]);
        }

        timer = (timer + 1) % delay;
    }

    /**
     * Animates an affect by cycling through it's images only one time.  Once the animation completes the affect removes itself from the world.
     */
    protected void animateOnce()
    {
        if(timer <= 0)
        {
            if(index >= images.length-1)
                removeSelf();
            else
                setImage(images[index]);            

            index++;
            timer = delay;
        }
        else
            timer--;
    }
    
    /**
     * Animates an affect by making the image more and more transparent as time progresses.
     * Removes itself once it's transparency reaches 0.
     */
    protected void animateDissapear()
    {
        if(timer <= 0)
        {
            transparency -= 50;
            
            if(transparency <= 0)
                removeSelf();
            else
                getImage().setTransparency(transparency);

            timer = delay;
        }
        else
            timer--;
    }
    
    protected void move()
    {
        setLocation(getX() - moveOffset, getY());
    }

    /**
     * Checks for a collision between this affect and an enemy.
     */
    protected void checkCollision()
    {
        Actor enemy = getOneIntersectingObject(Enemy.class);

        if(enemy != null)
            ((Enemy)enemy).decreaseHealth(damage);
    }

    //Load the required images for animation purposes.
    private void loadImages(String baseName, int numOfImages, String suffix)
    {
        images = new GreenfootImage[numOfImages];

        for(int i = 0; i < numOfImages; i++)
            images[i] = new GreenfootImage("Affects/" + baseName + i + suffix);
    }
    //Remove this affect from the world.
    private void removeSelf()
    {
        getWorld().removeObject(this);
    }
}
