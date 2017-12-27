
/**
 * Simulates a Card in a pack of Cards
 * 
 * @author Stephen Aigbomian
 * @author Nikolai Tukanov
 * @version 0.0.0
 */
public class Card implements Comparable
{
    private String suit;
    private int value;
    
    /**
     * Description: Constructor of the Card Class
     * @param suit The suit of the Card
     * @param value The value of the Card
     */
    public Card(String suit, int value)
    {
        this.suit = suit;
        this.value = value;
    }
    
    /**
     * Description: Get the suit of the Card
     * @return suit 
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**
     * Description: Get the value of the Card
     * @return value
     */
    public int getVal()
    {
        return value;
    }
    
    /**
     * Description: Compare this Card against another Card by their values, since suites have the same value
     * @param card The other Card being compared to
     * @return the difference between the value of this Card and the other card
     */
    public int compareTo(Object card)
    {
      return this.value - ((Card)card).getVal();  
    }
    
    /**
     * Description: Get the contents of the Card
     * @return The suit and the value of the Card
     */
    public String toString()
    {
        return("[" + suit + "  " + value + "]");
    }
      
}
