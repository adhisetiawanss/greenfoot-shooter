import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.LinkedList;
import java.awt.Color;

/**
 * This class represents a general level within the game.  If background and bg are instantiated with images then Level is 
 * able to scroll bg via scrollBackground.
 * 
 * @author Bretton Murphy
 */
public class Level extends Actor
{
    /**
     * Set of states for various levels
     */
    protected enum State
    {
        /**
         * State for the level to setup up stuff
         */
        SETUP, 
        
        /**
         * State before a enemy wave occurs
         */
        PREWAVE, 
        
        /**
         * State during which enemy wave is occuring
         */
        WAVE, 
        
        /**
         * State when a level is at an end
         */
        VICTORY,
        
        /**
         * State when the player fails a level
         */
        DEFEAT
    };

    /**
     * GreenfootImage to hold the background image used for the level
     */
    protected GreenfootImage background;
    
    /**
     * GreenfootImage to hold the redrawn background each time we scroll
     */
    protected GreenfootImage bg;

    /**
     * List of Nodes that represent our waves of enemies
     */
    protected LinkedList<Node> waves;

    /**
     * The current wave that the level is inserting into the world
     */
    protected Node currentWave;

    /**
     * The current state of the level
     */
    protected State theState;

    /**
     * The world reference 
     */
    protected myWorld theWorld;

    /**
     * The ship reference
     */
    protected static Ship ship;
    
   

    private int waveDelay;
    private int waveTimer;

    private int enemyDelay;
    private int enemyTimer;

    //The current scrolling position in game
    private int scrollPos;
    private static int scrollSpeed = 1;

    /**
     * Default constructor
     */
    public Level()
    {
        scrollPos = myWorld.getWorldWidth();
    }
    
    /**
     * Allows the player to announce defeat.
     */
    public void defeated()
    {
        theState = State.DEFEAT;
    }
    
    /**
     * Returns the scrolling speed of the current level.
     * 
     * @return The speed of the current level's scrolling.
     */
    public static int scrollSpeed()
    {
        return scrollSpeed;
    }

    /**
     * Redraws the background image onto bg to give the scrolling effect. 
     */
    protected void scrollBackground()
    {
        scrollPos -= scrollSpeed;

        bg.drawImage(background, scrollPos - myWorld.getWorldWidth(), 0);  //Redraw background onto bg.  Makes the new addition move from right to left
        bg.drawImage(background, scrollPos, 0);
        getWorld().setBackground(bg);  //set the worlds background to new background
        //Redraw what was already on bg plus the new addition to bg to get the whole image scrolling 
        if(scrollPos <= 0) 
            scrollPos = myWorld.getWorldWidth();
    }

    /**
     * Checks if the next wave is available for retrieval or not.  If it is then 
     * the next wave is retrieved and stored for later usage.
     * 
     * @return True if the next wave has been retrieved and false otherwise.
     */
    protected boolean getNextWave()
    {
        if(!waves.isEmpty())
        {
            currentWave = waves.pop();
            enemyTimer = enemyDelay = currentWave.enemyDelay();
            waveTimer = waveDelay = currentWave.waveDelay();
            return true;
        }

        return false;
    }

    /**
     * Checks if the next enemies in the current wave are ready to be inserted
     * into the world.  If so then the next enemies are added to the world.
     * 
     * @return True if the next batch of enemies have been added to the world and
     * false otherwise.
     */
    protected boolean sendNextEnemy()
    {
        if(waveTimer <= 0)
        {
            if(enemyTimer <= 0)
            {
                enemyTimer = enemyDelay;

                if(currentWave.hasEnemies())
                {
                    LinkedList<Enemy> enemies = currentWave.nextEnemy();
                    int size = enemies.size();
                    for(int i = 0; i < size; i++)
                    {
                        Enemy enemy = enemies.pop();
                        theWorld.addObject(enemy, enemy.xPose(), enemy.yPose());
                    }
                    return true;
                }
                else
                    theState = State.PREWAVE;
            }
            else
                enemyTimer--;
        }
        else
            waveTimer--;    
        return false;
    }
    
    
    /*
     * Checks collision with ship and a black collision mask
     */   
    public boolean isCollidable(int xPos, int yPos){

        int scrollVal = -(getX() - bg.getWidth()/2);

        if(Color.black.equals(bg.getColorAt(xPos+scrollVal,yPos)))
        {
            //System.out.println(xPos + scrollVal);
            return true;
        }
        else
        {
            //System.out.println("returned false");
            return false;
        }
    }
    
    
}
