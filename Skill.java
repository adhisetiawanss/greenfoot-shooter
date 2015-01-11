import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * This class represents the skills screen of the game.  It displays various labels and other pieces of information to the
 * player.
 * 
 * @author Bretton Murphy
 */
public class Skill  extends Screen
{
    //private static SkillButton[] laserState;
    //private static SkillButton[] bulletState;
    //private static SkillButton equipedState;

    //Labels that show basic text such as the skill points etc
    private Label escLabel1;
    private Label escLabel2;
    private Label skillPointLabel;
    private Label skillPoint;
    private Label damage;
    private Label speed;
    private Label properties;

    //The skill trees
    private static SkillButton[] laserTree;
    private static SkillButton[] bulletTree;

    private static SkillButton[] cursorTree;
    private static int currentSelection = 0;

    private static SkillButton equiped;
    
    private boolean canCheck;

    /**
     * Default constructor
     */
    public Skill()
    {
        super(new GreenfootImage("Screens/skillsScreen.png"));  

        escLabel1 = new Label("Press Esc", 45, 207, 22, 22, null);
        escLabel2 = new Label("to exit", 45, 207, 22, 22, null);
        skillPointLabel = new Label("Skill Points", 45, 207, 22, 22, null);
        skillPoint = new Label("" + Ship.skillPoints(), 45, 207, 22, 22, null);
        damage = new Label("Damage:", 30, 207, 22, 22, null);
        speed = new Label("Speed:", 30, 207, 22, 22, null);
        properties = new Label("Properties:", 30, 207, 22, 22, null);
        
        canCheck = false;
    }

    /**
     * Act - do whatever the Skill wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(canCheck)
            checkKeyPress();
    }    

    /**
     * This method is called by the Greenfoot system when the object has been inserted into the world.
     * 
     * @param world The world this object was added to.
     */
    public void addedToWorld(World world)
    {
        theWorld = (myWorld)world;

        //Add all the laser skills
        theWorld.addObject(laserTree[0], 200, 50);
        theWorld.addObject(laserTree[1], 100, 150);
        theWorld.addObject(laserTree[2], 300, 150);
        theWorld.addObject(laserTree[3], 200, 250);

        //Add all the bullet skills
        theWorld.addObject(bulletTree[0], 600, 50);
        theWorld.addObject(bulletTree[1], 500, 150);
        theWorld.addObject(bulletTree[2], 700, 150);
        theWorld.addObject(bulletTree[3], 400, 250);
        theWorld.addObject(bulletTree[4], 700, 250);

        equiped.displayDescription();
        
        //Add the labels into the world in their proper places
        theWorld.addObject(escLabel1, theWorld.getWidth() - 100, theWorld.getHeight() - 90);
        theWorld.addObject(escLabel2, theWorld.getWidth() - 100, theWorld.getHeight() - 40);
        theWorld.addObject(skillPointLabel, 95, theWorld.getHeight() - 90);
        theWorld.addObject(skillPoint, 95, theWorld.getHeight() - 40);
        theWorld.addObject(damage, 250, theWorld.getHeight() - 80);
        theWorld.addObject(speed, 240, theWorld.getHeight() - 50);
        theWorld.addObject(properties, 260, theWorld.getHeight() - 20);

        //Draw lines to make the trees look like trees
        drawLines();
        
        canCheck = true;
    }
    /*
    public static void saveState()
    {
        laserState = laserTree;
        bulletState = bulletTree;
        equipedState = equiped;
    }
    
    public static void restoreState()
    {
        laserTree = laserTree;
        bulletTree = bulletTree;
        equiped = equipedState;
    }
*/
    /**
     * Loads the skills in the skill tree.  Used by preloaders to prevent lag in the game.  Only needs to be called once
     * per game.
     */
    public static void loadSkills()
    {
        //Create the laser tree
        laserTree = new SkillButton[4];
        laserTree[0] = new SkillButton("twin", Twin.fireRate());
        
        
        

        //Set parent and child references for laser tree
        

        //Create bullet tree
        

        //Set parent and child references for bullet tree
       
        
        //Unlock the two basic weapons
       

        //Equip the twin laser by default
        equipSkill(laserTree[0]);
        cursorTree = laserTree;
    }

    private void removeSelf()
    {
        theWorld.removeObject(escLabel1);
        theWorld.removeObject(escLabel2);
        theWorld.removeObject(skillPointLabel);
        theWorld.removeObject(skillPoint);
        theWorld.removeObject(damage);
        theWorld.removeObject(speed);
        theWorld.removeObject(properties);

        cursorTree[currentSelection].removeDescription();

        for(int i = 0; i < laserTree.length; i++)
            theWorld.removeObject(laserTree[i]);

        for(int i = 0; i < bulletTree.length; i++)
            theWorld.removeObject(bulletTree[i]);

        theWorld.removeObject(this); 
    }

    private static void equipSkill(SkillButton whichSkill)
    {
        if(equiped != null)
            equiped.unEquip();
            
        whichSkill.equip();
        equiped = whichSkill;
    }

    private void drawLines()
    {
        getImage().setColor(Color.WHITE);

        getImage().drawLine(laserTree[0].getX() - (laserTree[0].getImage().getWidth()/2 - 8), laserTree[0].getY() + (laserTree[0].getImage().getHeight()/2 - 8), 
                            laserTree[1].getX() + (laserTree[1].getImage().getWidth()/2 - 8), laserTree[1].getY() - (laserTree[1].getImage().getHeight()/2 - 8));
        getImage().drawLine(laserTree[0].getX() + (laserTree[0].getImage().getWidth()/2 - 8), laserTree[0].getY() + (laserTree[0].getImage().getHeight()/2 - 8), 
                            laserTree[2].getX() - (laserTree[2].getImage().getWidth()/2 - 8), laserTree[2].getY() - (laserTree[2].getImage().getHeight()/2 - 8));
        getImage().drawLine(laserTree[1].getX() + (laserTree[1].getImage().getWidth()/2 - 8), laserTree[1].getY() + (laserTree[1].getImage().getHeight()/2 - 8), 
                            laserTree[3].getX() - (laserTree[3].getImage().getWidth()/2 - 8), laserTree[3].getY() - (laserTree[3].getImage().getHeight()/2 - 8));

        getImage().drawLine(bulletTree[0].getX() - (bulletTree[0].getImage().getWidth()/2 - 8), bulletTree[0].getY() + (bulletTree[0].getImage().getHeight()/2 - 8), 
                            bulletTree[1].getX() + (bulletTree[1].getImage().getWidth()/2 - 8), bulletTree[1].getY() - (bulletTree[1].getImage().getHeight()/2 - 8));
        getImage().drawLine(bulletTree[0].getX() + (bulletTree[0].getImage().getWidth()/2 - 8), bulletTree[0].getY() + (bulletTree[0].getImage().getHeight()/2 - 8), 
                            bulletTree[2].getX() - (bulletTree[2].getImage().getWidth()/2 - 8), bulletTree[2].getY() - (bulletTree[2].getImage().getHeight()/2 - 8));
        getImage().drawLine(bulletTree[1].getX() - (bulletTree[1].getImage().getWidth()/2 - 8), bulletTree[1].getY() + (bulletTree[1].getImage().getHeight()/2 - 8), 
                            bulletTree[3].getX() + (bulletTree[3].getImage().getWidth()/2 - 8), bulletTree[3].getY() - (bulletTree[3].getImage().getHeight()/2 - 8));
        getImage().drawLine(bulletTree[2].getX(), bulletTree[2].getY() + (bulletTree[2].getImage().getHeight()/2 - 8), 
                            bulletTree[4].getX(), bulletTree[4].getY() - (bulletTree[4].getImage().getHeight()/2 - 8));
    }

    private void selectUp()
    {
        cursorTree[currentSelection].unHighlight();
        cursorTree[currentSelection].removeDescription();

        currentSelection = (currentSelection - 1) % cursorTree.length;

        if(currentSelection < 0)
            currentSelection = cursorTree.length-1;

        cursorTree[currentSelection].highlight();
        cursorTree[currentSelection].displayDescription();
    }

    private void selectDown()
    {
        cursorTree[currentSelection].unHighlight();
        cursorTree[currentSelection].removeDescription();

        currentSelection = (currentSelection + 1) % cursorTree.length;

        cursorTree[currentSelection].highlight();
        cursorTree[currentSelection].displayDescription();
    }

    private void selectRight()
    {
        cursorTree[currentSelection].unHighlight();
        cursorTree[currentSelection].removeDescription();

        //Switch trees
        cursorTree = bulletTree;
        currentSelection = 0;

        cursorTree[currentSelection].highlight();
        cursorTree[currentSelection].displayDescription();
    }

    private void selectLeft()
    {
        cursorTree[currentSelection].unHighlight();
        cursorTree[currentSelection].removeDescription();

        //Switch trees
        cursorTree = laserTree;
        currentSelection = 0;

        cursorTree[currentSelection].highlight();
        cursorTree[currentSelection].displayDescription();
    }

    private void checkKeyPress()
    {
        String keyPress = Greenfoot.getKey();

        if(keyPress != null)
        {
            
            if(keyPress.equals("s"))
                selectDown();
            else if(keyPress.equals("w"))
                selectUp();
            else if(keyPress.equals("d"))
                selectRight();
            else if(keyPress.equals("a"))
                selectLeft();
            else if(keyPress.equals("enter"))
            {
                //If we select an unlocked skill then equip it.  Otherwise if we select a locked skill and have a skill point
                //then unlock that skill and equip it.
                if(!cursorTree[currentSelection].isLocked())
                    equipSkill(cursorTree[currentSelection]);
                else if(Ship.skillPoints() > 0 && cursorTree[currentSelection].canBeUnlocked())
                {
                    //Update skill points
                    Ship.decreaseSkillPoints();
                    skillPoint.updateLabel("" + Ship.skillPoints());

                    //Update equiped weapon
                    cursorTree[currentSelection].unlock();
                    equipSkill(cursorTree[currentSelection]);
                }
            }
        }
    }
}
