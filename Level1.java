import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.LinkedList;
/**
 * This class represents the first level in the game.  Adding an instance of this class will instantiate everything needed 
 * for the level and it will run on it's own.
 * 
 * @author Bretton Murphy
 */
public class Level1  extends Level
{
    private int rapeWaveDelay = 15;
    private int rapeWaveTimer;
    
    private int rapeTimer = 300;
    
    private int levelDelay = 10;
    private int levelTimer;
    
    private static GreenfootSound levelMusic = new GreenfootSound("level1Music.mp3");
    private static GreenfootImage test;
    private static final int numOfWaves = 13;

    /**
     * Default constructor
     */
    public Level1(boolean tryAgain)
    {
        super();
        loadImages();
        
        levelTimer = levelDelay;
        
        ship = new Ship(20, 200, this);
        
        if(!tryAgain)
            ship.storeState();
        else
            ship.restoreState();
            
        waves = new LinkedList();
        //Create wave nodes and add them to list
        for(int i = 1; i <= numOfWaves; i++)
            waves.add(new Node("levels/level1/", "wave" + i));
            
        theState = State.PREWAVE;
    }
    
    /**
     * Do whatever the Level1 wants to do. 
     */
    public void act() 
    {
        if(!theWorld.isPaused())
        {
            if(!levelMusic.isPlaying())
                levelMusic.playLoop();
                
            scrollBackground();
            
            setLocation(getX()-2,getY());
            
            switch(theState)
            {   
                //When the level is waiting in between waves for pacing
                case PREWAVE:

                    if(getNextWave())
                        theState = State.WAVE;
                    else
                        theState = State.VICTORY;
                        
                    break;
                    
                //When the level is inserting enemies into level  
                case WAVE:
                    sendNextEnemy();
                    break;
                    
                //When all waves for this level are done.
                case VICTORY:
                    if(theWorld.getObjects(Victory.class).isEmpty())
                        theWorld.addObject(new Victory(), theWorld.getWidth()/2, theWorld.getHeight()/2);
                        
                    if(levelTimer <= 0)
                    {
                        theWorld.switchLevel(2, false);
                    }
                    else
                        levelTimer--;
                    break;
                    
                case DEFEAT:
                    if(theWorld.getObjects(Defeat.class).isEmpty())
                        theWorld.addObject(new Defeat(), theWorld.getWidth()/2, theWorld.getHeight()/2);
                    break;
                    
                default:
                    System.err.println("Wrong level state!!");
            }
        }
        else
        {
            if(levelMusic.isPlaying())
                levelMusic.pause();
        }
    }    
    
    /**
     * This method is called by the Greenfoot system when this object has been inserted into the world.  Add's the players ship to the world,
     * set's the background, and stores the ships current x and y values.  
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld) world;   
        theWorld.addObject(ship, ship.xPose(), ship.yPose());
        
        theWorld.setBackground(background);
        getImage().setTransparency(0);
    }

    private void loadImages()
    {
        Disk.loadImages();
        Fight.loadImages();
        Stealth.loadImages();
       
        
        background = new GreenfootImage("Backgrounds/space1.jpg");
        bg = new GreenfootImage("Backgrounds/space1.jpg");
        background = getSpaceBG();
        bg = getSpaceBG();
    
    }
    
    //Returns a greenfootImage that contains the space background for level1
    private GreenfootImage getSpaceBG()
    {
        //Temp variable to draw our background to and return with the worlds dimensions
        GreenfootImage space = new GreenfootImage(theWorld.getWorldWidth(), theWorld.getWorldHeight());
        //Loops by amounts equal to the space1.jpg image dimensions and draws it over greenfootImage space
        for(int i = 0; i <= theWorld.getWorldWidth(); i += background.getWidth())
        {
            for(int j = 0; j <= theWorld.getWorldHeight(); j += background.getHeight())
            {
                space.drawImage(background, i, j);
            }
        }
        
        //Return space
        return space;
    }
}
