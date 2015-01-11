import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that represents a players ship.  The ship can fire weapons and move about the screen.  Should a collision occur between the ship and 
 * an enemy the ship will destroy itself.
 * 
 * @author Bretton Murphy  
 */
public class Ship  extends Actor
{   
    private enum weapon{BULLET, MISSILE, BLADEMISSILE, FIREBALL, FIREBALLBULLET, TWIN, CIRCLE, MINILASER, BOOMLASER};
    
    private boolean checkTerrain;
    
    //Ships x and y location
    private static int xPose; 
    private static int yPose;
    
    //Ships snap shot for restoring it's state upon retry of a level
    private static int healthState;
    private static int expState;
    private static int skillPointState;
    
    //The edges of the world.  Used to keep the ship from partially going off screen
    private int bottomBounds;
    private int topBounds;
    private int rightBounds;
    private int leftBounds;
    
    //The ship's speed
    private int speed = 5;
    
    //Slows down the ships shooting speed
    private static int shootDelay = 8;
    private int shootSlow;
    
    //Secondary slow down for ships shooting.  
    private int bulletDelay = 2;
    private int bulletSlow;

    //Which weapon is equiped right now
    private static weapon currentWeapon;
    private Weapon theWeapon = null;
    
    //Key checkers
    private Greenfoot horizontal, vertical, shoot;
    
    //Our world that was created
    private myWorld theWorld;
    
    //Ships health and experience stuff
    private Experience exp;
    private Health health;
    
    //Current level and current number of skill points
    private static int skillPoints = 0;
    
    private Level level;

    /**
     * Constructor that records it's x and y locations
     */
    public Ship(int x, int y, Level currentLevel)
    {
        //Get and store the edges of the world
        bottomBounds = theWorld.getWorldHeight() - (getImage().getHeight() / 2);
        topBounds = getImage().getHeight() / 2;
        rightBounds = theWorld.getWorldWidth() - (getImage().getWidth() / 2);
        leftBounds = getImage().getWidth() / 2;
        
        checkTerrain = false;
        
        loadAssets();
        
        level = currentLevel;
        
        shootSlow = shootDelay;
        bulletSlow = bulletDelay;
        
        exp = new Experience();
        health = new Health();
        
        checkTerrain = false;
        
        xPose = x;
        yPose = y;
    }

    /**
     * Act - (test) do whatever the Ship wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!theWorld.isPaused())
        {
            checkKeys();
            checkCollision();
        }
    }
    
    /**
     * Returns the ships current x position
     * 
     * @return The ships x coordinate
     */
    public static int xPose()
    {
        return xPose;
    }
    
    /**
     * Returns the ships current y position
     * 
     * @return The ships y coordinate
     */
    public static int yPose()
    {
        return yPose;
    }
    
    /**
     * Returns the ships current number of skill points
     * 
     * @return The ships skill points
     */
    public static int skillPoints()
    {
        return skillPoints;
    }
    
    public static void decreaseSkillPoints()
    {
        skillPoints--;
    }

    /**
     * Set's the xPose of the ship in the world.
     * 
     * @param x The x position in the world.
     */
    public void setXPose(int x)
    {
        xPose = x;
    }

    /**
     * Set's the yPose of the ship in the world.
     * 
     * @param y The y position in the world.
     */
    public void setYPose(int y)
    {
        yPose = y;
    }

    /**
     * Set's the speed of the ship.
     * 
     * @param s The speed the ship will fly at.
     */
    public void setSpeed(int s)
    {
        speed = s;
    }
    
    /**
     * Sets the checkTerrain value.
     * 
     * @param value The new value to set checkTerrain to
     */
    public void setCheckTerrain(boolean value)
    {
        checkTerrain = value;
    }
    
    /**
     * Set's the level value
     * 
     * @param value The value to set level to
     */
    public void setLevel(Level value)
    {
        level = value;
    }
    
    /**
     * Equips the ship with a given weapon type.
     * 
     * @param whichWeapon The weapon the ship is to equip
     */
    public static void equipWeapon(String whichWeapon, int slowDown)
    {
        currentWeapon = weapon.valueOf(whichWeapon);
        shootDelay = slowDown;
    }

    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld) world;    
        theWorld.addObject(exp, 150, 20);
        theWorld.addObject(health, 150, 10);
    }
    
    /**
     * Decreases the ships health by the specified amount
     * 
     * @param amount The amount of health to decrease by
     */
    public void decreaseHealth(int amount)
    {
        health.recieveDamage(amount);
        
        if(health.depleted())
            removeSelf();
    }
    
    /**
     * Restores the ship to a previously save state
     */
    public void restoreState()
    {
        skillPoints = skillPointState;
        health.setHealth(healthState);
        exp.setExp(expState);
        //Skill.restoreState();
    }
    
    /**
     * Stores the ships current health, experience, and level state
     */
    public void storeState()
    {
        skillPointState = skillPoints;
        healthState = health.health();
        expState = exp.exp();
        //Skill.saveState();
    }

    //Removes "this" from the world
    private void removeSelf()
    {
        theWorld.addObject(new ShipD(), getX(), getY());
        theWorld.removeObject(this);
        level.defeated();
    }

    //Check for key presses that control the ship
    private void checkKeys()
    {   
        String pause = Greenfoot.getKey();
        
        
            
        
        //Move ship right or left if right or left arrows are held down
        if(horizontal.isKeyDown("d") && xPose <= rightBounds)
            setLocation(xPose += speed, yPose);
        else if(horizontal.isKeyDown("a") && xPose >= leftBounds)
            setLocation(xPose -= speed, yPose);

        //Move ship up or down if up or down arrows are held down
        if(vertical.isKeyDown("w") && yPose >= topBounds)
            setLocation(xPose, yPose -= speed);
        else if(vertical.isKeyDown("s") && yPose <= bottomBounds)
            setLocation(xPose, yPose += speed);

        //Shoot ship's gun if j is held down
        if(shootSlow <= 0)
        {
            if(shoot.isKeyDown("space"))
            {
                fire();
                shootSlow = shootDelay;
            }
            else if(theWeapon != null)
            {
                theWorld.removeObject(theWeapon);
                theWeapon = null;
            }
        }
        else
            shootSlow--;
    }
    
    //Fires the ships weapon.  Which weapon depends on what is passed to String parameter whichWeapon
    private void fire()
    {
        switch(currentWeapon)
        {
            case TWIN:
                theWorld.addObject(new Twin(xPose+20, yPose, 0, this), xPose+20, yPose);
                break;
                
            case BULLET:
                theWorld.addObject(new Bullet(xPose+20, yPose, 0, this), xPose+20, yPose);
                break;
                
            
                
            
                
           
                
            
                
            
                
            
                
          
                
            default:
                System.err.println("Wrong weapon type equiped!!");
        }
    }

    private void loadAssets()
    {
        ShipD.loadAssets();
        Exp.loadAssets();
        Skill.loadSkills();
    }
    
    
    /**
     * Checks for a collision between the ship and other stuff in the world.
     */
    protected void checkCollision()
    {   
        Actor enemy = getOneIntersectingObject(Enemy.class);
        Actor experience = getOneIntersectingObject(Exp.class);
        
        if(enemy !=  null)
        {
            ((Enemy)enemy).decreaseHealth(1);
            decreaseHealth(2);
        }
        else if(checkTerrain)
        {
            if(level.isCollidable(getX(), getY())==true)
            {
                decreaseHealth(4);
            }
        }
        
        if(experience != null)
        {
            exp.recieveExp(((Exp)experience).expAmount());   
            ((Exp)experience).contact();
            
            if(exp.levelUp())
            {
                skillPoints += 1;
                health.refill();
            }
        }
    }
}
