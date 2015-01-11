import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games defeated screen.
 * 
 * @author Bretton Murphy
 */
public class Defeat  extends Screen
{
    private Button tryAgain;

    /**
     * Default constructor
     */
    public Defeat()
    {
        super(new GreenfootImage("Screens/defeatScreen.png"));
        
        tryAgain = new Button(new GreenfootImage("Buttons/tryAgainNormal.png"), new GreenfootImage("Buttons/tryAgainHL.png"));
        
        buttons = new Button[1];
        buttons[0] = tryAgain;
        
        tryAgain.highlight();
    }
    
    /**
     * Act - do whatever the Defeat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeys();
        checkForSelection();
    }
    
    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        world.addObject(tryAgain, world.getWidth()/2, world.getHeight() - world.getHeight() / 4);
        theWorld = (myWorld)world;
    }
    
    //Remove buttons first then self.
    private void removeSelf()
    {
        theWorld.removeObject(tryAgain);
        theWorld.removeObject(this);
    }
    
    //Should a button be selected take action based on which button it was.
    private void checkForSelection()
    {
        if(super.selectedButton != null)
        {
            if(super.selectedButton == tryAgain)
            {
                theWorld.tryLevelAgain();
                removeSelf();
            }
        }
    }
}
