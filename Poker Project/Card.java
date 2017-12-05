
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
    
    public int compare(Card card)
    {
      return this.value - card.getVal();  
    }
    
    public boolean equals(Card card)
    {
        if(this.suit.equals(card.getSuit()) && this.value == card.getVal())
        return true;
        else 
        return false;
    }
   
}
