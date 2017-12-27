import javax.swing.*;
/**
 * Simulates the properties of a Card Player
 * 
 * @author Stephen Aigbomian 
 * @author Nikolai Tukanov
 * @version 0.0.0
 */
public class Player
{
    private String name;
    private int balance;
    private Card[] hand;
    private boolean fold;
    private boolean dealer;
    
    /**
     * Description: Constructor of the Player Class
     * @param name The name of the Player
     * @param bal The initial amount of money given to the Player
     */
    public Player(String name, int bal)
    {
        this.name = name;
        this.balance = bal;
        hand = new Card[2];
        fold = false;
        dealer = false;
    }
    
    /**
     * Description: Access to the Player name field
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
     /**
     * Description: Access to the Player balance field
     * @return balance - The amount of money a Player has left
     */
    public int getBal()
    {
        return balance;
    }
    
    /**
     * Description: Adds a card to a Player's hand
     * @param card A Card
     */
    public void setHand(Card card)
    {
        if(hand[0] == null)
        this.hand[0] = card;
        else
        this.hand[1] = card;
    }
    
   /**
     * Description: Takes money from a Player's balance for a bet (call, raise or all in),if he has enough money
     * @param amount The amount of money the Player has chosen to bet
     * @return true if the Player has enough money in his/her balance, false otherwise
     */
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
    
    /**
     * Description: Adds an amount of money to the Players balance, usually when the player wins a game
     * @param amount The amount of money being added to the Player balance
     */
    public void addCash(int amount)
    {
        balance += amount;
    }
    
    /**
     * Description: Returns the higher of the Players two Cards
     * @return value of the higher ranked of the Player's Cards
     */
    public int highcard()
    {
        return Math.max(hand[0].getVal(), hand[1].getVal());
    }
    
    /**
     * Description: Folds or unfolds a Player by changing the fold field of the Player
     * @param has Boolean value for whether or not a Player has folded.
     */    
    public void fold(boolean has)
    {
        fold = has;
    }
    
    /**
     * Description: Returns the fold status of the Player
     * @return fold - The Player field that tells if a Player has folded or not
     */
    public boolean folded()
    {
        return fold;
    }
    
    /**
     * Description: Sets the Player to be or not to be the dealer by changing the dealer field of the Player
     * @param is Boolean value for whether or not a Player is a value
     */
    public void setDealer(boolean is)
    {
        dealer = is;
    }
    
     /**
     * Description: Returns the dealer status of the Player
     * @return fold - The Player field that tells if a Player is a dealer or not
     */
    public boolean isDealer()
    {
        return dealer;
    }
    
    /**
     * Description: Returns the hand of the Player
     * @return hand - The Players hand as an array of 2 Cards
     */
    public Card[] getHand()
    {
        return hand;
    }
    
    /**
     * Description: Returns the Players fields as String
     * @return Player
     */
    public String toString()
    {
        return "Name: " + name + "\n" + "Balance: " + balance + "\n" + "Hand: " + "(" + hand[0] + hand[1] + ") \n";
    }
}
