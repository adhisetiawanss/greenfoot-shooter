import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the games help screen.
 * 
 * @author Bretton Murphy
 */
public class Help  extends Screen
{
    private Button back;

    /**
     * Default constructor
     */
    public Help()
    {
        super(new GreenfootImage("Screens/helpScreen.png"));    

        back = new Button(new GreenfootImage("Buttons/backNormal.png"), new GreenfootImage("Buttons/backHL.png"));

        buttons = new Button[1];
        buttons[0] = back;

        back.highlight();
    }

    /**
     * Act - do whatever the Help wants to do. This method is called whenever
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
        world.addObject(back, world.getWidth()/2, world.getHeight() - world.getHeight() / 4);
        theWorld = (myWorld)world;
    }

    //Remove buttons first then self.
    private void removeSelf()
    {
        theWorld.removeObject(back);
        theWorld.removeObject(this);
    }

    //Should a button be selected take action based on which button it was.
    private void checkForSelection()
    {
        if(selectedButton != null && selectedButton == back)
        {
            theWorld.addObject(new Start(), theWorld.getWidth()/2, theWorld.getHeight()/2);
            removeSelf();
        }
    }
}
