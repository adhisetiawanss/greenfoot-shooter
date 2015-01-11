import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A general game screen that can take an array of buttons and check for keys presses
 * and then highlight the appropriate button based on which key was pressed.  Also keeps
 * track of which button is highlighted when the enter key is pressed so that some action
 * may occur.
 * 
 * @author Bretton Murphy
 */
public class Screen  extends Actor
{
    private GreenfootImage background;
    private int currentButton = 0;
    

    /**
     * An array of Button objects for the screen.
     */
    protected Button[] buttons;

    /**
     * The button that is highlighted when the enter key is pressed.
     */
    protected Button selectedButton = null;

    /**
     * A reference to the game world.
     */
    protected myWorld theWorld;
    
    /**
     * A constructor that takes an image as a background.
     * 
     * @param bg A GreenfootImage that is to be the background from this screen.
     */
    public Screen(GreenfootImage bg)
    {
        background = bg;
        setImage(background);
    }
    
    /**
     * Checks to see if a key press was done by the user and for which key it was.  If the 
     * user presses the "w" or "s" keys then buttons on this screen will highlight and 
     * unhighlight.  If a user presses the "enter" key then whatever button was highlighted is
     * stored within selectedButton.
     */
    protected void checkKeys()
    {
        String key = Greenfoot.getKey();
        
        if(key != null)
        {
            if(key.equals("s") && currentButton < buttons.length - 1 && selectedButton == null)
            {
                currentButton++;
                buttons[currentButton].highlight();
                buttons[currentButton - 1].unHighlight();
                
                Greenfoot.playSound("menuButton.mp3");
            }
            else if(key.equals("w") && currentButton > 0 && selectedButton == null)
            {
                currentButton--;
                buttons[currentButton].highlight();
                buttons[currentButton + 1].unHighlight();
                
                Greenfoot.playSound("menuButton.mp3");
            }
            else if(key.equals("enter") && buttons[currentButton].isHighlighted())
                selectedButton = buttons[currentButton];
        }
    }

    /**
     * Checks to see if a key press was done by the user and for which key it was.  If the 
     * user presses the "w" or "s" keys then buttons on this screen will highlight and 
     * unhighlight while animating.  If a user presses the "enter" key then whatever button was
     * highlighted is stored within selectedButton.
     */
    protected void checkKeysAnimate()
    {
        String key = Greenfoot.getKey();

        if((Greenfoot.isKeyDown("w") || (key != null && key.equals("w"))) && selectedButton == null)
        {
            if(buttons[0].isStill() && buttons[1].isStill() )
            {
                int previousButton = currentButton;
                currentButton = (currentButton + 1) % buttons.length;
                int otherButton = (currentButton + 1) % buttons.length;

                buttons[previousButton].setMoveState(Button.MOVEDOWNUHL, 50, 5);
                buttons[currentButton].setMoveState(Button.MOVEDOWNHL, 50, 5);
                buttons[otherButton].setMoveState(Button.MOVEUP, 100, 10);
                
                Greenfoot.playSound("menuButton.mp3");
            }
        }
        else if((Greenfoot.isKeyDown("s") || (key != null && key.equals("s"))) && selectedButton == null)
        {
            if(buttons[0].isStill() && buttons[1].isStill())
            {
                int otherButton;
                int previousButton = currentButton;
                currentButton--; 

                if(currentButton < 0)
                    currentButton = buttons.length - 1;
                if(previousButton == buttons.length - 1)
                    otherButton = 0;
                else
                    otherButton = previousButton + 1;

                buttons[previousButton].setMoveState(Button.MOVEUPUHL, 50, 5);
                buttons[currentButton].setMoveState(Button.MOVEUPHL, 50, 5);
                buttons[otherButton].setMoveState(Button.MOVEDOWN, 100, 10);
                
                Greenfoot.playSound("menuButton.mp3");
            }
        }
        else if(key != null && key.equals("enter") && buttons[currentButton].isHighlighted())
            selectedButton = buttons[currentButton];
    }
}
