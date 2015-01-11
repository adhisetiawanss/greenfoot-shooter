import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the world in which the game lives in.  Switching between levels happens here.
 * 
 * @author Bretton Murphy
 */
public class myWorld  extends World
{
    //private enum worldState{START, INGAME, PAUSED, VICTORY, GAMEOVER};

    //The worlds diminsions 
    private static final int WORLD_WIDTH = 850;
    private static final int WORLD_HEIGHT = 500;
    private static final int CELL_SIZE = 1;
    
    private static GreenfootSound level1Music;

    
    /**
     * State in which the game is at the start menu
     */
    public static final int START = 0;
    
    /**
     * State in which the game is at the help menu
     */
    public static final int HELP = 1;
    
    /**
     * State in which the game is re-trying the current level
     */
    public static final int TRY_AGAIN = 2;
    
    /**
     * State in which the game is currently being played by the player
     */
    public static final int IN_GAME = 3;
    
    /**
     * State in which the game is paused
     */
    public static final int PAUSED = 4;
    
    /**
     * State in which the game is at the victory screen
     */
    public static final int VICTORY = 5;
    
    /**
     * State in which the game is at the game over screen
     */
    public static final int GAMEOVER = 6;
    
    /**
     * State in which the game is at the credits screen
     */
    public static final int CREDIT = 7;
    
    private int state;
    
    private int currentLevel = 1;

    /**
     * Default constructor
     */
    public myWorld()
    {    
        super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE, false); 
        
        level1Music = new GreenfootSound("level1Music.mp3");
        
        setPaintOrder(Label.class, SkillButton.class, Skill.class, Button.class, Screen.class, Ship.class, Health.class, Experience.class);
        setActOrder(Ship.class, Level.class);
        
        addObject(new Start(), WORLD_WIDTH/2, WORLD_HEIGHT/2);
    }
    
    public void tryLevelAgain()
    {
        switchLevel(currentLevel, true);
    }
    
    /**
     * Switches the games level by adding a level object of the appropriate level number.
     * 
     * @param whichLvl The level number to switch to.
     */
    public void switchLevel(int whichLvl, boolean tryAgain)
    {
        cleanWorld();
        
        switch(whichLvl)
        {
            case 1: //Level one
                currentLevel = 1;
                addObject(new Level1(tryAgain), 0, 0); 
                break;   
            
            

            default: 
                System.err.println("Error switching to level " + whichLvl);
        }
    }

    /**
     * Returns the width of the world.
     * 
     * @return The width of the world.
     */
    public static int getWorldWidth()
    {
        return WORLD_WIDTH;
    }

    /**
     * Returns the height of the world
     * 
     * @return The height of the world.
     */
    public static int getWorldHeight()
    {
        return WORLD_HEIGHT;
    }
    
    /**
     * Checks to see if the game is paused or not.
     * 
     * @return True if game is paused and false otherwise.
     */
    public boolean isPaused()
    {
        if(state == PAUSED)
            return true;
        return false;
    }

    /**
     * Unpauses the game.
     */
    public void unPause()
    {
        state = IN_GAME;
    }
    
    /**
     * Pauses the game.
     */
    public void pause()
    {
        state = PAUSED;
    }

    private void cleanWorld()
    {
        removeObjects(getObjects(Button.class));
        removeObjects(getObjects(Screen.class));
        removeObjects(getObjects(PowerUp.class));
        removeObjects(getObjects(Level.class));
        
    }
}
