
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
    
    //combines call and raise
    public boolean bet(int amount)
    {
        //make sure player has enough money to bet
        if(amount > balance)
        {
        System.out.println("You do not have enough money for this action!");
        return false;
        }
        else
        {
        balance -= amount;
        return false;
        }
    }
        
    public void fold()
    {
        fold = true;
    }
    
    public Card[] getHand()
    {
        return hand;
    }
    
    public String toString()
    {
        return "Name: " + name + "\n" + "Balance: " + balance + "\n" + "Hand: " + "(" + hand[0] + hand[1] + ") \n";
    }
}
