import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.LinkedList;
/**
 * A class that can parse text files for either enemy waves or for enemy movements for the game.
 * 
 * @author Bretton Murphy
 */
public class Parser  
{
    private static enum EnemyType{disk, fight, launcher, stealth}

    //Parameter section 
    private static final int PARAMETER = 0;
    private static final int VALUE = 1;

    private static final int ROW = 0;
    private static final int COLUMN = 1;

    private static final int MOVE = 0;

    //Wave section
    private static final int ENEMY = 0;
    private static final int BULLETSPEED = 1;
    private static final int XPOSE = 2;
    private static final int YPOSE = 3;

    private static final int XOFFSET = 0;
    private static final int YOFFSET = 1;
    private static final int HOWMANY = 2;

    private static int theEnemyDelay = 0;
    private static int theWaveDelay = 0;

    private static int experience = 0;

    /**
     * Returns the enemies delay amount that was parsed from parseWave()
     * 
     * @return The enemy delay
     */
    public static int enemyDelay()
    {
        return theEnemyDelay;
    }

    /**
     * Returns the waves delay amount that was parsed from parseWave()
     * 
     * @return The wave delay
     */
    public static int waveDelay()
    {
        return theWaveDelay;
    }

    /**
     * Parses a text file into a wave of enemies for the game.  The wave file must conform to the following specific format:
     * 
     * [parameters]
     * wave delay = value
     * enemy delay = value
     * experience = value
     * [/parameters]
     * 
     * [wave]
     * moveType, enemy bulletSpeed startingXPosition startingYPosition, enemy bulletSpeed startingXPosition startingYPosition, ...
     * moveType, enemy bulletSpeed startingXPosition startingYPosition, enemy bulletSpeed startingXPosition startingYPosition, ...
     * .
     * .
     * .
     * [/wave]
     * 
     * Should the entire wave section be left out then the file will still be parsed but simply as empty.  This means one can put a wave delay if a buffer between other waves
     * is desired.  Also the parameters are optional and will default to 0 if that parameter is missing.  
     * 
     * @param location The location of the wave file to be parsed
     * @param whichWave The text file that is to be parsed
     * @return A linked list of linked lists containing a wave of enemies
     */
    public static LinkedList parseWave(String location, String whichWave)
    {
        File waveFile = new File(location + whichWave + ".txt");
        LinkedList<LinkedList> wave = new LinkedList();

        try
        {
            Scanner scan;

            if(waveFile.exists())
            {
                scan = new Scanner(waveFile);

                //Scan through the "sections" of the wave file
                while(scan.hasNextLine())
                {
                    String section = scan.nextLine();

                    //Determine which section we scanned then scan the inside of that section for stuff
                    if(section.equals("[parameters]"))
                        parseParameters(scan);
                    else if(section.equals("[wave]"))
                        parseEnemies(scan, wave);
                }
                return wave;
            }
            else
                System.err.println(whichWave + " does not exist!!");
        }
        catch(IOException e)
        {
            System.err.println("Error reading in file" + whichWave + ".txt");
        }

        return null;
    }

    private static void parseParameters(Scanner scan)
    {
        String parameters = null;

        if(scan.hasNextLine())
            parameters = scan.nextLine();

        //Scan through the parameters section 
        while(scan.hasNextLine() && !parameters.equals("[/parameters]"))
        {
            String[] tokens = parameters.split("\\s\\=\\s");

            //Determine which parameter was scanned in and store it's value
            if(tokens[PARAMETER].equals("enemy delay"))
                theEnemyDelay = Integer.parseInt(tokens[VALUE]);
            else if(tokens[PARAMETER].equals("wave delay"))
                theWaveDelay = Integer.parseInt(tokens[VALUE]);
            else if(tokens[PARAMETER].equals("experience"))
                experience = Integer.parseInt(tokens[VALUE]);
            else
                System.err.println(tokens[PARAMETER] + " is not a valid parameter!!");

            parameters = scan.nextLine();
        }
    }

    private static void parseEnemies(Scanner scan, LinkedList<LinkedList> wave)
    {
        String wavePart = null;

        if(scan.hasNextLine())
            wavePart = scan.nextLine();

        //Scan through the wave section 
        while(scan.hasNextLine() && !wavePart.equals("[/wave]"))
        {
            String[] waveTokens = wavePart.split("\\,\\s+");
            int[][] moveType = parseMove(waveTokens[MOVE]);

            //Iterate through each line in the wave and parse each enemy with their info
            for(int i = 1; i < waveTokens.length; i++)
            {
                String[] enemyTokens = waveTokens[i].split("\\s");

                EnemyType type  = EnemyType.valueOf(enemyTokens[ENEMY]);
                Enemy theEnemy = null;
                
                int bulletSpeed = Integer.parseInt(enemyTokens[BULLETSPEED]);
                int x = Integer.parseInt(enemyTokens[XPOSE]);
                int y = Integer.parseInt(enemyTokens[YPOSE]);
                
                switch(type)
                {
                    case disk:
                    
                        if(i == waveTokens.length - 1 && experience > 0)
                        {
                            theEnemy = new Disk(x, y, bulletSpeed, moveType, new Exp());
                            experience--;
                        }
                        else 
                            theEnemy = new Disk(x, y, bulletSpeed, moveType);
                        break;
        
                    case fight:
                        
                        if(i == waveTokens.length - 1 && experience > 0)
                        {
                            theEnemy = new Fight(x, y, bulletSpeed, moveType, new Exp());
                            experience--;
                        }
                        else 
                            theEnemy = new Fight(x, y, bulletSpeed, moveType);
                        break;
                        
                    case launcher:
                        
                        if(i == waveTokens.length - 1 && experience > 0)
                        {
                            theEnemy = new Launcher(x, y, bulletSpeed, moveType, new Exp());
                            experience--;
                        }
                        else 
                            theEnemy = new Launcher(x, y, bulletSpeed, moveType);
                        break;
                        
                    case stealth:
                        
                        if(i == waveTokens.length - 1 && experience > 0)
                        {
                            theEnemy = new Stealth(x, y, bulletSpeed, moveType, new Exp());
                            experience--;
                        }
                        else 
                            theEnemy = new Stealth(x, y, bulletSpeed, moveType);
                        break;

                    default:
                        System.err.println(type + " is not an enemy type!!");
                }
                
                if(wave.size() <= i - 1 || wave.get(i - 1) == null)
                {
                    LinkedList<Enemy> enemies = new LinkedList(); 
                    enemies.add(theEnemy);
                    wave.add(enemies);
                }
                else
                    wave.get(i - 1).add(theEnemy);   
            }

            wavePart = scan.nextLine();
        }
    }

    private static int[][] parseMove(String whichMove)
    {
        File moveFile = new File("moves/" + whichMove + ".txt");
        int[][] movePattern = null;
        int index = 0;

        try
        {
            Scanner scan;

            if(moveFile.exists())
            {
                scan = new Scanner(moveFile);

                int row = 3;
                int col;
                
                //Scan through each line in move file
                while(scan.hasNextLine())
                {
                    String line = scan.nextLine();
                    String[] tokens = line.split("\\s\\=\\s+");
                    String[] numbers = tokens[VALUE].split("\\,\\s+");
                    
                    //Create 2D array if have not done so yet
                    if(movePattern == null)
                    {
                        col = numbers.length;
                        movePattern = new int[row][col];
                    }
                    
                    //Verify which parameter we're adding numbers from then add the numbers to the 2D array
                    if(tokens[PARAMETER].equals("x offset"))
                    {
                        for(int i = 0; i < numbers.length; i++)
                            movePattern[XOFFSET][i] = Integer.parseInt(numbers[i]);
                    }
                    else if(tokens[PARAMETER].equals("y offset"))
                    {
                        for(int i = 0; i < numbers.length; i++)
                            movePattern[YOFFSET][i] = Integer.parseInt(numbers[i]);
                    }
                    else if(tokens[PARAMETER].equals("how many"))
                    {
                        for(int i = 0; i < numbers.length; i++)
                            movePattern[HOWMANY][i] = Integer.parseInt(numbers[i]);
                    }
                }
                
                return movePattern;
            }
            else
                System.err.println("Move file does not exists!!");
        }
        catch(IOException e)
        {
            System.err.println("Error reading in file" + whichMove + ".txt");
        }

        return null;
    }
}
