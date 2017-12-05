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
   private static LinkedHashSet<Card> community;
   private int pot;
   private int minBlind;
   private int maxBlind;
   Scanner input = new Scanner(System.in);
   public Game(LinkedList<Player> players, boolean blinds)
   {
       table = players;
       for(String s : suits)
       {
           for(int i = 2; i <= 14; i++)
           deck.push(new Card(s, i));
       }
       Collections.shuffle(deck);
       if(blinds)
       {
           System.out.print("What is the Small Blind: ");
           minBlind = input.nextInt();
           maxBlind = 2 * minBlind;
       }             
   }
   
   public void deal()
   {
       for(int i = 0; i < table.size() * 2; i++)
       {
       Player temp = table.pop();
       temp.setHand(deck.pop());
       table.addLast(temp);
       }
   } 
   
  public void addCommunity(int num)
  {
      for(int i = num; i <= 0; i--)
      community.add(deck.pop());
  }
  
  public static boolean isFlush(LinkedHashSet<Card> share, Card[] pHand)
  {
      share.add(pHand[0]);
      share.add(pHand[1]);     
      String suit = share.iterator().next().getSuit();
      for(Card c : share)
      {
          if(!suit.equals(c.getSuit()))
          return false;
      }
      return true;
  }
  
  public static boolean sequence(LinkedHashSet<Card> share, Card[] pHand)
  {
      share.add(pHand[0]);
      share.add(pHand[1]); 
      int expected = 5 * (share.iterator().next().getVal() + 2);
      int sum = 0;
      for(Card c : share)
      sum = sum + c.getVal();
      if(sum == expected)
      return true;
      return false;
  }
  
  public static int comboRank(Player p)
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
         if(!sequence(community, p.getHand()))
         return 6;
         else if(sequence(community, p.getHand()))
         return 9;
         else
         return 10;
     }
  }
}
