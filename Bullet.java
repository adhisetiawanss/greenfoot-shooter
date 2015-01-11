import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This represents the bullet weapon type the ship and enemies can fire.
 * 
 * @author Bretton Murphy
 */
public class Bullet  extends Weapon
{
    private static int damage = 1;
    private static int speed = 10;
    private static int fireRate = 8;

    /**
     * Create a bullet weapon with the specified location, and direction.
     * 
     * @param x The x location within the world
     * @param y The y location within the world
     * @param direction The direction the weapon will travel
     * @param ship The ship reference that fired this weapon
     */
    public Bullet(int x, int y, double direction, Ship ship)
    {
        super(speed, damage, x, y, direction);
        setImage("Weapons/bullet.png");
        Greenfoot.playSound("Bullet.mp3");
        
        shipRef = ship;
    }
    
    /**
     * Create a bullet weapon with the specified location, speed, and direction.
     * 
     * @param speed The speed of the weapon
     * @param x The x location within the world
     * @param y The y location within the world
     * @param direction The direction the weapon will travel
     * @param enemy The enemy reference that fired this weapon
     */
    public Bullet(int speed, int x, int y, double direction, Enemy enemy)
    {
        super(speed, damage, x, y, direction);
        super.damage = this.damage;
        setImage("Weapons/bullet.png");
        
        enemyRef = enemy;
    }

    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!theWorld.isPaused())
        {
            moveWeapon();
            checkCollisions();
        }
    }  
    
    /**
     * Get's the weapons damage
     * 
     * @return The damage this weapon inflicts
     */
    public static int damage()
    {
        return damage;
    }
    
    /**
     * Get's the weapons speed
     * 
     * @return The speed that this weapon travels at
     */
    public static int speed()
    {
        return speed;
    }
    
    /**
     * Get's how fast the weapon is fired
     * 
     * @return The speed that this weapon is fired at
     */
    public static int fireRate()
    {
        return fireRate;
    }
}
