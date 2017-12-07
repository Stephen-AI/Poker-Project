import java.util.*;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game
{
   String[] suits = {"H","S","D","C"};
   private LinkedList<Player> table;
   private LinkedList<Card> deck;
   private TreeSet<Card> community;
   private int pot;
   private int minBlind;
   private int maxBlind;
   Scanner input = new Scanner(System.in);
   public Game(LinkedList<Player> players, boolean blinds)
   {
       table = players;
       //create 52 cards
       for(String s : suits)
       {
           for(int i = 2; i <= 14; i++)
           deck.push(new Card(s, i));
       }
       Collections.shuffle(deck);
       //option for blinds
       if(blinds)
       {
           System.out.print("What is the Small Blind: ");
           minBlind = input.nextInt();
           maxBlind = 2 * minBlind;
       }             
   }
   
   //simulates dealing of cards. Gives each player 2 cards dealt one by one
   public void deal()
   {
       for(int i = 0; i < table.size() * 2; i++)
       {
       Player temp = table.pop();
       temp.setHand(deck.pop());
       table.addLast(temp);
       }
   } 
  
  //adds num cards to the community set of cards, where num is the number of cards 
  public void addCommunity(int num)
  {
      for(int i = num; i <= 0; i--)
      {
      deck.addLast(deck.pop());
      community.add(deck.pop());
      }
  }
  
  //checks if a set of cards are a flush
  public static boolean isFlush(TreeSet<Card> share, Card[] pHand)
  {
      TreeSet<Card> shared = new TreeSet(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
      String suit = shared.iterator().next().getSuit();
      //all cards must be equal to the suit
      for(Card c : shared)
      {
          if(!suit.equals(c.getSuit()))
          return false;
      }
      return true;
  }
  
  //checks to see if a set of cards are in sequence
  public static boolean sequence(TreeSet<Card> share, Card[] pHand)
  {
      TreeSet<Card> shared = new TreeSet(share);
      shared.add(pHand[0]);
      shared.add(pHand[1]); 
      //using sum of arithmetic series
      int expected = 5 * (shared.iterator().next().getVal() + 2);
      int sum = 0;
      for(Card c : shared)
      sum = sum + c.getVal();
      if(sum == expected)
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
             case 5:
             if(sequence(community, p.getHand()))
             return 5;
             else
             return 1;
             case 4:
             return 2;
             case 3:
             if(Collections.max(hm.values()) == 2)
             return 3;
             else
             return 4;
             case 2:
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
