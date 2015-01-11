import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games start screen.
 * 
 * @author Bretton Murphy
 */
public class Start  extends Screen
{
    private Button newGame;
    private Button howToPlay;
    
    
    private static GreenfootSound menuMusic = new GreenfootSound("menuMusic.mp3");
    
    /**
     * Default constructor
     */
    public Start()
    {
        super(new GreenfootImage("Screens/startScreen.png"));
        
        newGame = new Button("newGameNormal", ".png", 6, new GreenfootImage("Buttons/newGameHL.png"));
        howToPlay = new Button("howToPlayNormal", ".png", 6, new GreenfootImage("Buttons/howToPlayHL.png")); 
            
        
        buttons = new Button[2];
        buttons[0] = newGame;
        buttons[1] = howToPlay;
        
        
        newGame.highlight();
    }
    
    /**
     * Act - do whatever the Start wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!menuMusic.isPlaying())
            menuMusic.playLoop();
            
        checkKeysAnimate();
        checkForSelection();
    }   
    
    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        world.addObject(newGame, world.getWidth()/2, world.getHeight()/2 + 50);
        world.addObject(howToPlay, world.getWidth()/2, world.getHeight()/2);
        
        
        theWorld = (myWorld)world;
    }
    
    //Remove buttons first then self.
    private void removeSelf()
    {
        theWorld.removeObject(newGame);
        theWorld.removeObject(howToPlay);
        
        theWorld.removeObject(this);
    }
    
    //Should a button be selected take action based on which button it was.
    private void checkForSelection()
    {
        if(selectedButton != null)
        {
            if(selectedButton == newGame)
            {
                if(menuMusic.isPlaying())
                    menuMusic.stop();
                theWorld.switchLevel(1, false);
            }
            else if(selectedButton == howToPlay)
            {
                theWorld.addObject(new Help(), theWorld.getWidth()/2, theWorld.getHeight()/2);
                removeSelf();
            }
            
        }
    }
}
