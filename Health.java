import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the players health in the game.
 * 
 * @author Bretton Murphy
 */
public class Health  extends Actor
{
    private static GreenfootImage healthFrame = new GreenfootImage("healthFrame.png");
    private static GreenfootImage healthBar = new GreenfootImage("healthBar.png");
    private GreenfootImage temp;
    
    private int healthCap = 20;
    private int currentHealth = 20;
    
    private int frameWidth;
    
    /**
     * Default constructor
     */
    public Health()
    {
        temp = new GreenfootImage("healthBar.png");
        temp.drawImage(healthFrame, 0, 0);
        
        setImage(temp);
        
        frameWidth = healthFrame.getWidth();
    }
    
    /**
     * Checks to see if the health has reached 0 or not.
     * 
     * @return True if health is at 0 and false otherwise
     */
    public boolean depleted()
    {
        if(currentHealth <= 0)
            return true;
        return false;
    }
   
    /**
     * Decreases the health by a specified amount.
     * 
     * @param amount The amount of health to be taken away
     */
    public void recieveDamage(int amount)
    {   
        if(currentHealth - amount < 0)
            currentHealth = 0;
        else 
            currentHealth -= amount;
        
        drawBar();
    }
    
    /**
     * Increases the health by a specified amount.
     * 
     * @param amount The amount of health to be added to the current health
     */
    public void recieveHealth(int amount)
    {
        if(currentHealth + amount > healthCap)
            currentHealth = healthCap;
        else 
            currentHealth += amount;
            
        drawBar();
    }
    
    /**
     * Set's the health to a specified amount. 
     * 
     * @param amount The amount to set the health to
     */
    public void setHealth(int amount)
    {
        if(amount <= healthCap)
            currentHealth = amount;
            
        drawBar();
    }
    
    /**
     * Returns the amount of health the player currently has
     * 
     * @return The current health of the player
     */
    public int health()
    {
        return currentHealth;
    }
    
    /**
     * Set's the health to the maximum amount that is allowed.
     */
    public void refill()
    {
        currentHealth = healthCap;
        drawBar();
    }
    
    private void drawBar()
    { 
        temp.clear();
        temp.drawImage(healthBar, (frameWidth/healthCap)*currentHealth-frameWidth, 0);
        temp.drawImage(healthFrame, 0, 0);
    }
}
