
/**
 * Write a description of class Card here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card implements Comparable
{
    private String suit;
    private int value;
    
    public Card(String suit, int value)
    {
        this.suit = suit;
        this.value = value;
    }
    
    public String getSuit()
    {
        return suit;
    }
    
    public int getVal()
    {
        return value;
    }
    
    //compare two cards by their values, since suites have the same value
    public int compareTo(Object card)
    {
      return this.value - ((Card)card).getVal();  
    }
    
    public String toString()
    {
        return("[" + suit + "  " + value + "]");
    }
      
}
