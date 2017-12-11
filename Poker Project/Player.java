import javax.swing.*;
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
    boolean dealer;
        
    public Player(String name, int bal)
    {
        this.name = name;
        this.balance = bal;
        hand = new Card[2];
        fold = false;
        dealer = false;
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
        if(hand[0] == null)
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
        JOptionPane.showMessageDialog(null, "You do not have enough money for this action!");
        return false;
        }
        else
        {
        balance -= amount;
        return true;
        }
    }
        
    public void fold(boolean has)
    {
        fold = has;
    }
    
    public boolean folded()
    {
        return fold;
    }
    
    public void setDealer(boolean is)
    {
        dealer = is;
    }
    
    public boolean isDealer()
    {
        return dealer;
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
