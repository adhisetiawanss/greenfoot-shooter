import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Generic button that can have an image and a highlighted image that be switched between.
 * 
 * @author Bretton Murphy
 */
public class Button  extends Actor
{
    private GreenfootImage normals[];
    private GreenfootImage normal;
    private GreenfootImage highlighted;
    
    private enum imageState{NORM, HIGHLIGHT};
    
    private imageState imagestate;
    private int movestate;
    
    private int index;
    
    private int moveSpeed = 5;
    
    private int destinationY;
    
    /**
     * State in which button is not moving.
     */
    public static final int STILL = 0;
    
    /**
     * State in which button is moving downwards.
     */
    public static final int MOVEDOWN = 1;
    
    /**
     * State in which button is moving upwards.
     */
    public static final int MOVEUP = 2;
    
    /**
     * State in which button is moving downwards and is to become highlighted when done moving.
     */
    public static final int MOVEDOWNHL = 3;
    
    /**
     * State in which button is moving upwards and is to become highlighted when done moving.
     */
    public static final int MOVEUPHL = 4;
    
    /**
     * State in which button is moving downwards and is to become unhighlighted at the begining of movement.
     */
    public static final int MOVEDOWNUHL = 5;
    
    /**
     * State in which button is moving upwards and is to become unhighlighted at the begining of movement.
     */
    public static final int MOVEUPUHL = 6;
    
    /**
     * Constructor that set's the button too an image and stores a highlighted version to be used later.
     * 
     * @param norm The image to be displayed by the button upon creation.
     * @param highlight The highlighted version of norm that can be used later.
     */
    public Button(GreenfootImage norm, GreenfootImage highlight)
    {
        normal = norm;
        highlighted = highlight;
        setImage(normal);   
        
        imagestate = imageState.NORM;
        movestate = STILL;
    }
    
    /**
     * Constructor that set's the button too an image and stores highlighted version to be used later.
     * Also get's ready for use a set of animation images for the button to transition through.
     * 
     * @param baseName The set of images base name.  All images must use the same base name.
     * @param suffix The set of images suffix that is used.  All images must use the same suffix.
     * @param howMany The number of images that are to be in the set.
     * @param highlight The highlighted version of norm that can be used later.
     */
    public Button(String baseName, String suffix, int howMany, GreenfootImage highlight)
    {
        normals = new GreenfootImage[howMany];
        highlighted = highlight;
        
        loadImages(baseName, suffix, howMany);
        
        index = howMany - 1;
        setImage(normals[index]);
        
        imagestate = imageState.NORM;
        movestate = STILL;
    }
    
    /**
     * This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        switch(movestate)
        {
            case STILL:
                break;
                
            case MOVEDOWN:
                moveDown();
                
                break;
                
            case MOVEUP:
                moveUp();
                
                break;
                
            case MOVEDOWNHL:
                moveDownHL();
                
                break;
                
            case MOVEUPHL:
                moveUpHL();
                
                break;
                
            case MOVEDOWNUHL:       
                moveDownUHL();
                
                break;
                
            case MOVEUPUHL:
                moveUpUHL();
                
                break;
                
            default:
                System.err.println(movestate + " is not a button move state!!");
        }
    }
    
    /**
     * Makes the button switch it's image too the stored highlight image
     */
    public void highlight()
    {
        setImage(highlighted);
        imagestate = imageState.HIGHLIGHT;
        index = 0;
    }
    
    /**
     * Makes the button switch it's image too the normal image
     */
    public void unHighlight()
    {
        setImage(normal);
        imagestate = imageState.NORM;
    }
    
    /**
     * Checks to see if button is highlighted or not.
     * 
     * @return True if highlighted and false otherwise.
     */
    public boolean isHighlighted()
    {
        if(imagestate == imageState.HIGHLIGHT)
            return true;
        return false;
    }
    
    /**
     * Checks to see if button is moving or not.
     * 
     * @return True if button is not moving and false otherwise.
     */
    public boolean isStill()
    {
        if(movestate == STILL)
            return true;
        return false;
    }
    
    /**
     * Sets the buttons state so that the button knows how to move and act.
     * 
     * @param whichMove The state that the button should act upon.
     * @param moveAmountY The amount of pixels the button should move total.
     * @param speed The speed at which the button should move by in pixels per ac
     */
    public void setMoveState(int whichMove, int moveAmountY, int speed)
    {
        movestate = whichMove;
        destinationY = moveAmountY;
        moveSpeed = speed;
    }
    
    private void loadImages(String baseName, String suffix, int howMany)
    {
        for(int i = 0; i < howMany; i++)
            normals[i] = new GreenfootImage("Buttons/" + baseName + i + suffix);
    }
    
    private void moveDown()
    {
        if(destinationY <= 0)
            movestate = STILL;
        else
        {
            setLocation(getX(), getY() + moveSpeed);
            destinationY -= moveSpeed;
        }
    }
    
    private void moveUp()
    {
        if(destinationY <= 0)
            movestate = STILL;
        else
        {
            setLocation(getX(), getY() - moveSpeed);
            destinationY -= moveSpeed;
        }
    }
    
    private void moveDownUHL()
    {
        if(destinationY <= 0)
            movestate = STILL;
        else
        {
            setLocation(getX(), getY() + moveSpeed);
            destinationY -= moveSpeed;
            
            if(index < normals.length - 1)
            {
                index++;
                setImage(normals[index]);
            }
        }
    }
    
    private void moveUpUHL()
    {
        if(destinationY <= 0)
            movestate = STILL;
        else
        {  
            setLocation(getX(), getY() - moveSpeed);
            destinationY -= moveSpeed;
            
            if(index < normals.length - 1)
            {
                index++;
                setImage(normals[index]);
            }
        }
    }
    
    private void moveDownHL()
    {
        if(destinationY <= 0)
        {
            movestate = STILL;
            highlight();
        }
        else
        {
            setLocation(getX(), getY() + moveSpeed);
            destinationY -= moveSpeed;

            if(index > 0)
            {
                index--;
                setImage(normals[index]);
            }
        }
    }
    
    private void moveUpHL()
    {
        if(destinationY <= 0)
        {
            movestate = STILL;
            highlight();
        }
        else
        {
            setLocation(getX(), getY() - moveSpeed);
            destinationY -= moveSpeed;
            
            if(index > 0)
            {
                index--;
                setImage(normals[index]);
            }
        }
    }
}
