import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * A class that creates label like objects that are meant to be displayed.
 * 
 * @author Bretton Murphy 
 */
public class Label  extends Actor
{
    private int red;
    private int green;
    private int blue;
    private int size;
    private Color textColor;
    private Color labelColor;

    /**
     * Constructor that creates a label with a specified text and background color.
     * 
     * @param text The string to written inside the label
     * @param size The font size
     * @param textColor The color of the text
     * @param labelColor The background color of the label
     */
    public Label(String text, int size, Color textColor, Color labelColor)
    {
        if(labelColor == null)
            labelColor = new Color(0, 0, 0, 0);
        
        this.size = size;
        this.textColor = textColor;
        this.labelColor = labelColor;
            
        GreenfootImage image = new GreenfootImage(text, size, textColor, labelColor);
        setImage(image);
    }
    
    /**
     * Constructor that creates a label with a specified text and background color.
     * 
     * @param text The string to written inside the label
     * @param size The font size
     * @param r The red value of the text
     * @param g The green value of the text
     * @param b The blue value of the text
     * @param labelColor The background color of the label
     */
    public Label(String text, int size, int r, int g, int b, Color labelColor)
    {
        if(labelColor == null)
            labelColor = new Color(0, 0, 0, 0);
        
        
        red = r;
        green = g;
        blue = b;
        this.size = size;
        this.textColor = null;
        this.labelColor = labelColor;
            
        Color color = new Color(r, g, b);
        
        GreenfootImage image = new GreenfootImage(text, size, color, labelColor);
        setImage(image);
    }
    
    /**
     * Updates the label with new text
     * 
     * @text The string to update the label to
     */
    public void updateLabel(String text)
    {
        GreenfootImage image;
        
        if(textColor != null)
            image = new GreenfootImage(text, size, textColor, labelColor);
        else
        {
            Color color = new Color(red, green, blue);
            image = new GreenfootImage(text, size, color, labelColor);
        }
        
        setImage(image);
            
    }
    
    /**
     * Gets the width of the label
     * 
     * @return The labels width
     */
    public int width()
    {
        return getImage().getWidth();
    }
}
