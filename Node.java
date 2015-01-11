import java.util.LinkedList;
/**
 * Acts as a container for waves of enemies and is considered a wave in the game.  Can check to see if there are enemies left in the wave and can returnt he next enemy in the wave.
 * 
 * @author Bretton Murphy
 */
public class Node  
{
    private int enemyDelay;
    private int waveDelay;
    
    private LinkedList<LinkedList> enemies; 

    /**
     * Constructor receives a name of a wave file to parse.
     * 
     * @param whichWave The name of the wave file to look for.
     */
    public Node(String location, String whichWave)
    {
        enemies = Parser.parseWave(location, whichWave);

        enemyDelay = Parser.enemyDelay();
        waveDelay = Parser.waveDelay();
    }
    
    /**
     * Returns the enemies delay.
     * 
     * @return The enemy delay
     */
    public int enemyDelay()
    {
        return enemyDelay;
    }
    
    /**
     * Returns the waves delay.
     * 
     * @return The wave delay
     */
    public int waveDelay()
    {
        return waveDelay;
    }

    /**
     * Check to see if the wave has any more enemies.
     * 
     * @return True if there are enemies left and false otherwise
     */
    public boolean hasEnemies()
    {
        if(enemies.isEmpty())
            return false;
        return true;
    }
    
    /**
     * Returns the next enemy within the wave.
     * 
     * @return The next enemy in the wave
     */
    public LinkedList<Enemy> nextEnemy()
    {
        LinkedList<Enemy> enemy = enemies.pop();
        return enemy;
    }
}
