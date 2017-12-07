
/**
 * Write a description of class Card here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card
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
    public int compare(Card card)
    {
      return this.value - card.getVal();  
    }
    
    public String toString()
    {
        return("[" + suit + "  " + value + "]");
    }
      
}
