
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private String name;
    private int balance;
    private Card[] hand;
    boolean fold;
    
    public Player(String name, int bal)
    {
        this.name = name;
        this.balance = bal;
        hand = new Card[2];
        fold = false;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getBal()
    {
        return balance;
    }
    
    public void setHand(Card card)
    {
        if(hand.length == 0)
        this.hand[0] = card;
        else
        this.hand[1] = card;
    }
    
    public void bet(int amount)
    {
        balance -= amount;
    }
    
    public void fold()
    {
        fold = true;
    }
    
    public Card[] getHand()
    {
        return hand;
    }
}
