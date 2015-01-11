import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that represents a ships weapons.  A weapon can move foward, animate as well as check for a collision with an enemy.  Weapons will also
 * clean up after themselves so there is no need to manually remove the weapon from the game after a collision of any kind.
 * 
 * @author Bretton Murphy
 */
public class Weapon  extends Actor
{   
    /**
     * The various states of a weapon
     */
    protected enum State
    {
        /**
         * State for a weapons entrance into the world
         */
        ENTRANCE, 
        
        /**
         * State for a weapon moving
         */
        MOVE, 
        
        /**
         * State for a weapon making contact with something
         */
        CONTACT,
        
        /**
         * State for a weapon when it goes out of bounds
         */
        BOUNDS
    };
    
    /**
     * The current state a weapon is in
     */
    protected State state;
    
    /**
     * Array of greenfootImages for weapons animation
     */
    protected GreenfootImage[] images;

    /**
     * Weapons x position in the world
     */
    protected int xPose;

    /**
     * Weapons y position in the world
     */
    protected int yPose;

    /**
     * Weapons travel speed
     */
    protected int speed;

    /**
     * Weapon direction
     */
    protected double direction;
    
    /**
     * World reference
     */
    protected myWorld theWorld;
    
    /**
     * Weapons damage factor
     */
    protected int damage;  
    
    /**
     * A sound effect that can be used by the weapon
     */
    protected GreenfootSound sound;
    
    //Index for the images array.
    private int index = 0;
    //Slow down factor for weapon animation
    private int timer;
    //Temp slow down factor for weapon animation
    private int delay;
    
    /**
     * Default is null.  Contains player ship reference should a weapon be fired
     * by the ship.
     */
    protected Ship shipRef = null;
    
    /**
     * Default is null.  Contains enemy reference should a weapon be fired
     * by the enemy
     */
    protected Enemy enemyRef = null;
    
    /**
     * Create a weapon with specified position, speed, and images used for animation of the weapon.
     * Note that images used for animation must be in the form "nameOfImage0.png", "nameOfImage1.png" where the first image
     * must start with a 0 after the base name and continue in sequential order.
     * 
     * @param s Speed at which weapon travels.
     * @param x The x position of the weapon within the world.
     * @param y The y position of the weapon within the world.
     * @param weaponDelay The delay amount of a weapons animation.  A 0 will result in no delay.
     * @param baseName The base image name for the weapons animation.
     * @param numOfImages The number of images that are required for the animation.
     * @param suffix The images suffix in the form of ".png" 
     */
    public Weapon(int s, int x, int y, int weaponDelay, String baseName, int numOfImages, String suffix)
    {
        speed = s;
        xPose = x;
        yPose = y;
        delay = timer = weaponDelay;
        loadImages(baseName, numOfImages, suffix);
    }

    /**
     * Create a weapon with specified position and speed.  
     * 
     * @param s Speed at which weapon travels.
     * @param x The x position of weapon within the world.
     * @param y The y position of weapon within the world.
     */
    public Weapon(int s, int damage, int x, int y, double d)
    {
        speed = s;
        this.damage = damage;
        xPose = x;
        yPose = y;
        direction = d;
    }

    /**
     * Create a weapon with specified position, speed, slow down factor, and images for animation.  
     * 
     * @param s Speed at which weapon travels.
     * @param x The x position of weapon within the world.
     * @param y The y position of weapon within the world.
     * @param slow The slow down factor for weapon or how slow the animation will execute.
     */
    public Weapon(int s, int damage, int x, int y, double d, int slow)
    {
        speed = s;
        this.damage = damage;
        xPose = x;
        yPose = y;
        direction = d;
        delay = timer = slow;
    }
    
    /**
     * Default constructor
     */
    public Weapon()
    {
        
    }
    
    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld)world;
    }
    
    //Load animation images for weapon into weapon array.  baseName - base name of image files. numOfImages - number of images 
    //suffix - image file extensions
    private void loadImages(String baseName, int numOfImages, String suffix)
    {
        //Initialize weapon array
        images = new GreenfootImage[numOfImages];
        //Store aniamtion images into wave array
        for(int i = 0; i < numOfImages; i++)
            images[i] = new GreenfootImage("Weapons/" + baseName + i + suffix);
    }
    
    /**
     * Removes this object from the game
     */
    protected void removeSelf()
    {
        getWorld().removeObject(this);
    }

    /**
     * Checks for a collision between this weapon and an enemy or the edge of screen.  If so, this weapon will remove itself from the world.
     */
    protected void checkCollisions()
    {
        if(xPose >= getWorld().getWidth() || xPose <= 0 || yPose >= getWorld().getHeight() || yPose <= 0)
        {
            if(sound != null)
                sound.stop();
                
            if(state != null)
                state = State.BOUNDS;
            else
                removeSelf();
                
        }
        else if(shipRef != null)
        {
            Actor enemy = getOneIntersectingObject(Enemy.class);
            
            if(enemy != null)
            {
                ((Enemy)enemy).decreaseHealth(damage);
                
                if(state != null)
                    state = State.CONTACT;
                else
                    removeSelf();
                    
            }
        }
        else if(enemyRef != null)
        {
            Actor ship = getOneIntersectingObject(Ship.class);
            
            if(ship != null)
            {
                ((Ship)ship).decreaseHealth(damage);
                removeSelf();
            }
        }
    }

    /**
     * Moves weapon forward.
     */
    protected void moveWeapon()
    {
        double dx = speed*Math.cos(direction);//+0.5 * Math.PI);
        double dy = speed*Math.sin(direction);//+0.5 * Math.PI);
        
        xPose += Math.round(dx);
        yPose += Math.round(dy);
        
        setLocation(xPose, yPose);
    }

    /**
     * Animates weapon using images from images array.
     */
    protected void animate()
    {
        //Slows down weapon animation so it doesn't execute too fast
        if(timer <= 0)
        {
            index = (index + 1) % images.length;
            setImage(images[index]);
            timer = delay;
        }
        else
            timer--;
    }
}
