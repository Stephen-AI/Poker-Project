import java.util.*;
import javax.swing.*;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game
{
   String[] suits = {"H","S","D","C"};
   private LinkedList<Player> table = new LinkedList();
   private LinkedList<Card> deck = new LinkedList();
   private TreeSet<Card> community = new TreeSet();
   private int pot;
   private int minBlind = -1;
   private int maxBlind = -1;
   Player highbet = null;
   boolean blinds;
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
    
    //the player who made the highest bet
   public void setHighBet(Player player)
   {
       highbet = player;
   }
   
   public void resetDealer()
   {
       for(Player p : table)
       p.setDealer(false);
   }
   
   public void unfold()
   {
       for(Player p : table)
       p.fold(false);
   }
     
   public int getMinBlind()
   {
       return minBlind;
   }
   
   public int getMaxBlind()
   {
       return maxBlind;
   }
   
   public void setMinBlind(int amount)
   {
       minBlind = amount;
       maxBlind = amount * 2;
   }
   
   public void addPot(int amount)
   {
       pot = pot + amount;
   }
   public LinkedList<Player> table()
   {
       return table;
   }
   //simulates dealing of cards. Gives each player 2 cards dealt one by one
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
   
  public TreeSet<Card> getCommunity()
  {
      return community;
  }
  
  //adds num cards to the community set of cards, where num is the number of cards 
  public void addCommunity(int num)
  {
      Collections.shuffle(deck); 
      for(int i = num; i > 0; i--)
      {
      deck.addLast(deck.pop());
      community.add(deck.pop());
      }
  }
  
  public void winner()
  {
     Player winner = table.iterator().next();
     System.out.println(community + "\n");
     
     for(Player p : table)
     {
         System.out.println(p);
         if(comboRank(winner) < comboRank(p))
         winner = p;
     }
     
     JOptionPane.showMessageDialog(null, winner.getName() + " wins this round! with " + comboRank(winner));
  }
   
  
  public String toString()
  {
      return "pot: " + pot + "\n community cards: " + community;
  }
  
  //checks if a set of cards are a flush
  public static boolean isFlush(TreeSet<Card> share, Card[] pHand)
  {
      TreeSet<Card> shared = new TreeSet(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
      String suit = shared.iterator().next().getSuit();
      int count = 0;
      //all cards must be equal to the suit
      for(Card c : shared)
      {
          if(suit.equals(c.getSuit()))
          count++;
      }
      if(count >= 5)
      return true;
      return false;
  }
  
  //checks to see if a set of cards are in sequence
  public static boolean sequence(TreeSet<Card> share, Card[] pHand)
  {
      TreeSet<Card> shared = new TreeSet(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
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
  
   
  //determines the combination a player has and returns the rank using the number of distinct numbers the player's hand and community set have
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
     
     if(!isFlush(community, p.getHand()))
     {
         switch(hm.size())
         {
             case 7:
             if(sequence(community, p.getHand()))
             return 5;
             else
             return 1;
             case 6:
             return 2;
             case 5:
             if(Collections.max(hm.values()) == 2)
             return 3;
             else
             return 4;
             case 4:
             if(Collections.max((hm.values())) == 3)
             return 7;
             else
             return 8;
             default:
             return -1;
         }
     }
     
     else
     {
         if(!sequence(community, p.getHand()) && !hm.containsKey(14))
         return 6;
         else if(sequence(community, p.getHand()))
         return 9;
         else
         return 10;
     }
  }
}
