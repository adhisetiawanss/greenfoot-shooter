import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents a skill that the skill screen utilizes.  It also is responsible for equiping the 
 * players ship with weapons.
 * 
 * @author Bretton Murphy
 */
public class SkillButton  extends Actor
{
    private enum Skill{BULLET, MISSILE, BLADEMISSILE, FIREBALL, FIREBALLBULLET, TWIN, CIRCLE, MINILASER, BOOMLASER};
    
    private myWorld theWorld;
    
    private GreenfootImage skillHL;
    private GreenfootImage skillLocked;
    private GreenfootImage skillUnlocked;
    private GreenfootImage skillEquiped;
    private GreenfootImage skillHLEquiped;
    
    private String skillName;
    private int slowDown;
    
    private boolean locked = true;
    private boolean equiped = false;
    
    private SkillButton parent;
    private SkillButton[] children;
    
    private Label name;
    private Label damage;
    private Label speed;
    private Label properties;
    
    /**
     * Constructor that takes a weapon name and creates a set of images needed for the skills screen
     * 
     * @param name The weapon name
     */
    public SkillButton(String name, int fireRate)
    {
        skillHL = new GreenfootImage("Icons/" + name + "IconHL.png"); 
        skillHLEquiped = new GreenfootImage("Icons/" + name + "IconHLEquiped.png"); 
        skillLocked = new GreenfootImage("Icons/" + name + "IconLocked.png"); 
        skillUnlocked = new GreenfootImage("Icons/" + name + "IconUnlocked.png");
        skillEquiped = new GreenfootImage("Icons/" + name + "IconEquiped.png"); 
        setImage(skillLocked);
        
        skillName = name.toUpperCase();
        
        slowDown = fireRate;
        
        loadDescription();
    }
    
    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld) world;    
    }
    
    /**
     * Sets references so that a skill knows whose it's parent reference and children
     * references are in a skill tree.
     * 
     * @param parent The skills parent skill
     * @param rightChild The skills left child skill
     * @param leftChild The skills right child skill
     */
    public void setReferences(SkillButton parent, SkillButton leftChild, SkillButton rightChild)
    {
        children = new SkillButton[2];
        children[0] = rightChild;
        children[1] = leftChild;
        this.parent = parent;
    }
    
    /**
     * Check to see if this skill is still locked or not
     * 
     * @return True if the skill is locked and false otherwise
     */
    public boolean isLocked()
    {
        return locked;
    }
    
    /**
     * Checks to see if this skill can be unlocked or not
     * 
     * @return True if skill is unlockable and false otherwise
     */
    public boolean canBeUnlocked()
    {
        if(parent != null && !parent.isLocked())
            return true;
        return false;
    }
    
    /**
     * Check to see if this skill is equiped or not
     * 
     * @return True if the skill is equiped and false otherwise
     */
    public boolean isEquiped()
    {
        return equiped;
    }
    
    /**
     * Unlocks a skill so that it may be used
     */
    public void unlock()
    {
        locked = false;
        setImage(skillUnlocked);
        
        switch(Skill.valueOf(skillName))
        {    
            case TWIN:
                break;
            case BULLET:
                break;   
             
            
                
           
                
           
                
            case FIREBALLBULLET:
                break;
                
           
                
            
                
           
                
            default:
                System.err.println("Wrong weapon to unlock");
        }
    }
    
    /**
     * Equips an unlocked skill 
     */
    public void equip()
    {
        equiped = true;
        setImage(skillHLEquiped);
        
        Ship.equipWeapon(skillName, slowDown);
    }
    
    /**
     * Unequips the currently equiped skill
     */
    public void unEquip()
    {
        equiped = false;
        setImage(skillUnlocked);
        
    }
    
    /**
     * Highlights a skill in the skill tree for cursor movement
     */
    public void highlight()
    {
        if(!equiped)
            setImage(skillHL);
        else
            setImage(skillHLEquiped);
    }
    
    /**
     * Unhighlights a skill in the skill tree for cursor movement
     */
    public void unHighlight()
    {
        if(locked)
            setImage(skillLocked);
        else if(equiped)
            setImage(skillEquiped);
        else
            setImage(skillUnlocked);
    }
    
    /**
     * Displays the skills descriptions onto the screen at predeteremined places
     */
    public void displayDescription()
    {
        theWorld.addObject(name, theWorld.getWidth() / 2, theWorld.getHeight() - 100);
        theWorld.addObject(damage, 310, theWorld.getHeight() - 80);
        theWorld.addObject(speed, 310, theWorld.getHeight() - 50);
        theWorld.addObject(properties, properties.width()/2 + 325, theWorld.getHeight() - 20);
    }
    
    /**
     * Removes the skills descriptions from the screen
     */
    public void removeDescription()
    {
        theWorld.removeObject(name);
        theWorld.removeObject(damage);
        theWorld.removeObject(speed);
        theWorld.removeObject(properties);
    }
    
    private void loadDescription()
    {
        switch(Skill.valueOf(skillName))
        {
            case BULLET:
                name = new Label("Bullet", 40, 207, 22, 22, null);
                damage = new Label("" + Bullet.damage(), 25, 207, 22, 22, null);
                speed = new Label("" + Bullet.speed(), 25, 207, 22, 22, null);
                properties = new Label("Basic bullet weapon", 25, 207, 22, 22, null);
                break;
            
            case TWIN:
                name = new Label("Twin Laser", 40, 207, 22, 22, null);
                damage = new Label("" + Twin.damage(), 25, 207, 22, 22, null);
                speed = new Label("" + Twin.speed(), 25, 207, 22, 22, null);
                properties = new Label("Basic laser weapon", 25, 207, 22, 22, null);
                break;
                
           
                
            
                
            
                
            
                
           
                
            
                
            
                
            default:
                System.err.println("Wrong weapon description!!");
        }
    }
}
