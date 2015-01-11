import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a generic enemy that can move, shoot and animate itself
 * 
 * @author Bretton Murphy 
 */
public class Enemy  extends Actor
{
    protected enum State{OPEN, SHOOT, CLOSE};
    
    protected State state;
    
    /**
     * Images used for animation
     */
    protected GreenfootImage[] images;
    
    /**
     * Enemies read from in order to move correctly
     */
    protected int[][] movePattern;
    
    /**
     * The worlds reference
     */
    protected myWorld theWorld;
    
    private static final int X = 0;
    private static final int Y = 1;
    private static final int AMOUNT = 2;
    private int mIndex = 0;
    private int patternAmount;

    /**
     * An enemies health
     */
    protected int health;

    /**
     * The enemies x position
     */
    protected int xPose;
    
    /**
     * The enemies y position
     */
    protected int yPose;
    
    /**
     * The enemies bullet speed for shooting
     */
    protected int bulletSpeed;
    
    /**
     * Default is null.  If not null then when dead drop the expDrop object into
     * the world
     */
    protected Exp expDrop = null;

    private int index = 0;
    private int delay;
    private int timer;

    private int sDelay;
    private int sTimer;

    /**
     * Constructor that takes an amount that represents the amount of delay the
     * enemy uses for animations
     * 
     * @param theDelay The amount to delay animations by
     */
    public Enemy(int theDelay)
    {
        int number = Greenfoot.getRandomNumber(75) + 50;
        delay = timer = theDelay;
        sTimer = sDelay = number;
    }

    /**
     * Returns the enemies x position
     * 
     * @return The x position
     */
    public int xPose()
    {
        return xPose;
    }

    /**
     * Returns the enemies y position
     * 
     * @return The y position
     */
    public int yPose()
    {
        return yPose;
    }

    /**
     * Decreases this enemy's health equal to amount.
     * 
     * @param amount The amount of health subtracted from this enemy
     */
    public void decreaseHealth(int amount)
    {
        health -= amount;
        if(health <= 0)
            destruct();
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
    
    private void removeSelf()
    {
        getWorld().removeObject(this);
    }

    /**
     * Determines whether or not to destroy this based on health.  If health is less than one this will be removed.
     */
    protected void destruct()
    {
      
        
        if(expDrop != null)
            theWorld.addObject(expDrop, getX(), getY());
            
        removeSelf();
    }

    /**
     * Animates this enemy 
     */
    protected void animate()
    {
        //Slows down the animation so it's not too fast
        if(timer == 0)
        {
            index = (index + 1) % images.length;  
            setImage(images[index]);  
        }
        //Check the moveSlowDown factor and either decrement it or reset it
        timer = (timer + 1) % delay;
    }

    /**
     * Tells the enemy to shoot it's weapon
     */
    protected void shoot()
    {
        if(sTimer <= 0)
        {
            int x = getX();
            int y = getY();

            getWorld().addObject(new Bullet(bulletSpeed, x, y, Math.atan2((Ship.yPose()-y) , (Ship.xPose()-x)), this), x, y);
            sTimer = sDelay;
        }
        else
            sTimer--;
    }

    /**
     * Tells the enemy to move on the screen
     */
    protected void move()
    {
        if(patternAmount <= 0)
        {
            patternAmount = movePattern[AMOUNT][mIndex];
            mIndex++;
        }
        else
        {
            setLocation(getX() + movePattern[X][mIndex - 1], getY() + movePattern[Y][mIndex - 1]);
            patternAmount--;
        }

        if(mIndex >= movePattern[X].length && patternAmount <= 0 || getX() < 0 || getX() > theWorld.getWidth() || getY() < 0 || getY() > theWorld.getHeight())
            removeSelf();
    }
}
