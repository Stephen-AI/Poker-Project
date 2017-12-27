import java.util.*;
import javax.swing.*;
/**
 *Simulates the Texas Hold-em poker game
 * 
 * @author stephen Aigbomian
 * @author Nikolai Tukanov
 * @version 0.0.0
 */
public class Game
{
   String[] suits = {"H","S","D","C"};
   private LinkedList<Player> table = new LinkedList();
   private LinkedList<Card> deck = new LinkedList();
   private LinkedList<Card> community = new LinkedList();
   private int pot, sidepot;
   public Player psidePot = null;
   private int minBlind = -1;
   private int maxBlind = -1;
   Player highbet = null;
   boolean blinds;
   
   /**
    * Description: Constructor for the Game class
    * @param players A list of Players
    * @param blinds Boolean value that determine if the players want blinds or not
    */
   public Game(LinkedList<Player> players, boolean blinds)
   {
       table = players;
       //create 52 cards
       for(String s : suits)
       {
           for(int i = 2; i <= 14; ++i)
           deck.push(new Card(s, i));
       }
       Collections.shuffle(deck);
       
       //set dealer
       Collections.shuffle(table);
       Player temp = table.pop();
       temp.setDealer(true);
       table.addLast(temp);
       
       sidepot = 0;
       //option for blinds
       if(blinds)
       {
          this.blinds = true;
          while(minBlind < 0)
          {
           minBlind = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the minimum blind: ")); 
           maxBlind = 2 * minBlind;
          }  
          
          temp = table.pop();
          temp.bet(minBlind);
          addPot(minBlind);
          table.addLast(temp);
        
          temp = table.pop();
          temp.bet(maxBlind);
          addPot(maxBlind);
          table.addLast(temp);
          setMinBlind(maxBlind);
      }     
      else
      {
          this.blinds = false;
          while(minBlind < 0)
          {
          minBlind = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the ante amount: ")); 
          maxBlind = 2 * minBlind;
          }
      }
   }
   
   /**
    * Description: Check each Player balance to see if at least 2 Players have an balance > 0
    * @return true if there are at least 2 Players with a balance > 0. Returns false otherwise
    */
   public boolean enoughPlayers()
   {
       int count = 0;
       for(Player p : table)
       {
           if(p.getBal() != 0)
           count++;
       }
       
       if(count >= 2)
       return true;
       return false;
   }
   
   /**
    * Description: Sets a side pot for Players who go all in balances less than the minimum amount
    * @param amount The amount of money bet by the Player
    * @param player The player whose all-in created the side pot
    */
   public void setSidePot(int amount, Player player)
   {
       sidepot = amount;
       psidePot = player;
   }
   
   /**
    * Description: Get the amount in the sidepot
    * @return sidepot
    */
   public int getSidePot()
   {
       return sidepot;
   }
    
   /**
     * Description: Save the Player who made the highest bet so far
     * @param player Player with the highest bet
     */
   public void setHighBet(Player player)
   {
       highbet = player;
   }
   
   /**
    * Description: Sets all the Players dealer status to false
    */
   public void resetDealer()
   {
       for(Player p : table)
       p.setDealer(false);
   }
   
   /**
    * Description: Set all the Players fold status to false and also remove Players who have no money left
    */
   public void unfold()
   {
       for(Player p : table)
       {
       if(p.getBal() > 0)
       p.fold(false);
       else
       table.remove(p);
       }
   }
   
   /**
    * Description: Get the minimum bet to be made by the next Player
    * @return minBlind
    */
   public int getMinBlind()
   {
       return minBlind;
   }
   
    /**
    * Description: Get the minimum raise amount to be made by the next Player
    * @return maxBlind
    */
   public int getMaxBlind()
   {
       return maxBlind;
   }
   
    /**
    * Description: Set the minimum raise amount to be made by the next Player
    * @param amount - The minimum amount to be bet
    */
   public void setMinBlind(int amount)
   {
       minBlind = amount;
       maxBlind = amount * 2;
   }
   
   /**
    * Description: add money bet by a Player to the pot
    * @param amount The amount bet
    */
   public void addPot(int amount)
   {
       pot = pot + amount;
   }
   
   /**
    * Description: Get the list of Players
    * @return table - The list of Players
    */
   public LinkedList<Player> table()
   {
       return table;
   }
   
   /**
    * Description: simulates dealing of cards. Gives each player 2 cards dealt one by one
    * 
    */
   public void deal()
   {
       Collections.shuffle(deck); 
       for(int i = 0; i < table.size() * 2; i++)
       {
       Player temp = table.pop();
       temp.setHand(deck.pop());
       table.addLast(temp);
       }
   } 
   
  /**
   * Description: Get the community Cards
   * @return community
   */
  public LinkedList<Card> getCommunity()
  {
      return community;
  }
  
  /**
   * Description: add a number of Cards to the community usually after a betting round
   * @param num The number of Cards to be added
   */
  public void addCommunity(int num)
  {
      Collections.shuffle(deck); 
      int prev = community.size();
      while(community.size() != prev + num)
      {
      deck.addLast(deck.pop());
      Card c = deck.pop();
      if(c != null)
       community.add(c);
      }
  }
  
  /**
   * Description: Determine a winner based on the combination of Cards each player in the table has
   */
  public void winner()
  {
     Player winner = table.iterator().next();
     System.out.println(community + "\n");
     
     for(Player p : table)
     {
         System.out.println(p);
         if(comboRank(winner) < comboRank(p))
         winner = p;
         else if(comboRank(winner) == comboRank(p))
         {
             winner = tiebreak(p, winner, comboRank(winner));
         }
     }
     
     winner.addCash(pot);
     pot = 0;
     JOptionPane.showMessageDialog(null, winner.getName() + " wins this round! with " + comboRank(winner));
  }
  
  /**
   * Description: Add Cards into a HashMap
   * @param hm The HashMap to add Cards into
   * @param c The Card to be added
   */
  public static void hashAdd(HashMap<Integer, Integer> hm, Card c)
  {
      if(hm.containsKey(c.getVal()))
      hm.put(c.getVal(), hm.get(c.getVal()) + 1);
      else
      hm.put(c.getVal(), 0);
  }
  
  /**
   * Description: break a tie if 2 players have the same card rank
   * @param p1 a Player 
   * @param p2 second Player
   * @param rank shared rank of both Players
   * @return The Player with the better hand
   */
  public Player tiebreak(Player p1, Player p2, int rank)
  {
      HashMap<Integer, Integer> hm1 =  new HashMap();
      HashMap<Integer, Integer> hm2 =  new HashMap();
      //count the # of distinct cards each player has
      for(Card c : community)
      {
          hashAdd(hm1, c);
          hashAdd(hm2, c);
      }
      
      for(Card c : p1.getHand())
       hashAdd(hm1, c);
      for(Card d : p2.getHand())
       hashAdd(hm2, d);  
       
      switch(rank)
      {
      //flushes and sequences
      case 1:
      case 5:
      case 6:
      case 9:
      int max1 = Math.max(p1.getHand()[0].getVal() , p1.getHand()[1].getVal());
      int max2 = Math.max(p2.getHand()[0].getVal() , p2.getHand()[1].getVal());
      if(max1 > max2)
      return p1;
      else
      return p2;
      //other pairs and # of kinds
      case 2:
      case 3:
      case 4:
      case 7:
      case 8:
      //compare the values of the pairs of each player
      int max = Collections.max(hm1.values());
      int val1 = 0; int val2 = 0;
      for(Integer i : hm1.keySet())
      {
          if(hm1.get(i) == max)
          {
              val1 = i;
              break;
          }
      }      
      for(Integer i : hm2.keySet())
      {
          if(hm2.get(i) == max)
          {
              val2 = i;
              break;
          }
      }
      
      if(val1 > val2)
      return p1;
      else
      return p2;
      default:
      return null;
    }
    
  }  
  
  public String toString()
  {
      return "pot: " + pot + "\n community cards: " + community;
  }
  
  /**
   * Description: checks if a Player's set of cards are a flush
   * @param share The communtiy Cards 
   * @pHand The Player's Cards
   * @return true if it's a flush. false otherwise
   */
  public static boolean isFlush(LinkedList<Card> share, Card[] pHand)
  {
      LinkedList<Card> shared = new LinkedList(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
      Collections.sort(shared);
      int count1 = 0; int count2 = 0; 
      
      //all cards must be equal to the suit
      for(Card c : shared)
      {
          if(pHand[0].getSuit().equals(c.getSuit()))
          count1++;
          else if(pHand[1].getSuit().equals(c.getSuit()))
          count2++;
      }
      
      if(count1 >= 5 || count2 >= 5)
      return true;
      return false;
  }
  
/**
   * Description: checks if a Player's set of cards are a sequence
   * @param share The communtiy Cards 
   * @pHand The Player's Cards
   * @return true if it's a sequence. false otherwise
   */
  public static boolean sequence(LinkedList<Card> share, Card[] pHand)
  {
      LinkedList<Card> shared = new LinkedList(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
      Collections.sort(shared);
      int count = 0;
      int prev = shared.iterator().next().getVal();
      for(Card c : shared)
      {
          if(prev == c.getVal() + 1)
          count++;
          else
          count = 0;
          prev = c.getVal();
      }
      
      if(count >= 5)
      return true;
      return false;
  }
  
   /**
     Description: determines the combination a player has and returns the rank using the number of distinct numbers the player's hand and community set have
     @param p The Player whose hand's are to be compared
     @return The rank of the combination of cards. (10 for a Royal Flush and so on..)
  */
  public int comboRank(Player p)
  {
     HashMap<Integer,Integer> hm = new HashMap();
     hm.put(p.getHand()[0].getVal(), 1);
     if(hm.containsKey(p.getHand()[1].getVal()))
     hm.put(p.getHand()[1].getVal(), hm.get(p.getHand()[1].getVal())+ 1);
     else
     hm.put(p.getHand()[1].getVal(),1);
     for(Card c : community) 
     {
         if(hm.containsKey(c.getVal()))
         hm.put(c.getVal(), hm.get(c.getVal()) + 1);
         else
         hm.put(c.getVal(), 1);
     }
     
     if(!isFlush(community, p.getHand()) && !sequence(community, p.getHand()))
     {
         if(Collections.max(hm.values()) == 4)
         return 8;
         else if(Collections.max(hm.values()) == 3)
         {
             if(hm.size() <= 4)
             return 7;
             else
             return 4;
         }
         else if(Collections.max(hm.values()) == 2)
         {
             if(hm.size() <= 5)
             return 3;
             else
             return 2;
         }
         else
         return 1;
     }
     
     else
     {
         if(sequence(community, p.getHand()) && isFlush(community, p.getHand()) && hm.containsKey(14))
         return 10;
         else if(sequence(community, p.getHand()) && isFlush(community, p.getHand()) && !hm.containsKey(14))
         return 9;
         else
         return 5;         
     }
  }
}
