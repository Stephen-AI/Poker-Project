import java.util.*;
import javax.swing.*;
/**
 * Write a description of class GameDemo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameDemo
{
   final static String prefloptions = "1. Call  \n 2. Raise \n 3. Go all in \n 4. Fold \n";
   final static String postfloptions = "1. Bet  \n 2. Go all in \n 3. Fold \n 4. Check \n";
public static boolean confirm(int input, String options)
{
     int answer = JOptionPane.showConfirmDialog(null,"The options were: \n" + options + "You entered " + input + "\n Are you sure of your choice",
    "Confirm answer", JOptionPane.YES_NO_OPTION);
    if(answer == JOptionPane.YES_OPTION)
    return true;
    else
    return false;    
}
   
public static void preflop(Game game)
{
     Player temp = game.table().pop();
      int input = -1;
       if(temp != game.highbet)
          {             
              
              while(input < 0 )
              {
                input = Integer.parseInt(JOptionPane.showInputDialog(null,"Choose an action " + temp + "community : " + game.getCommunity() + "\n 1. Call (" + game.getMinBlind() + ") \n 2. Raise (" + game.getMaxBlind() +
                ")\n 3. Go all in \n 4. Fold \n " ));
                switch(input)
                {
                 //call
                 case 1:
                 //making sure player has enough money to bet
                  if(temp.bet(game.getMinBlind()))
                  {
                   //highbet guy decided to bet again
                   if(game.highbet == null)
                   game.setHighBet(temp); 
                   game.addPot(game.getMinBlind());
                  JOptionPane.showMessageDialog(null, temp.getName() + " calls!");
                  game.table().addLast(temp);
                  preflop(game);
                  return;
                 }
                 else//no mo money
                 {
                 JOptionPane.showMessageDialog(null, temp.getName() + " is out of the game due to lack of funds");
                 temp.fold(true);
                 game.table().addLast(temp);
                 preflop(game);
                 return;
                 }
                 //raise
                 case 2: 
                 if(temp.bet(game.getMaxBlind()))
                 {
                 game.addPot(game.getMaxBlind());
                 game.setHighBet(temp);
                 JOptionPane.showMessageDialog(null, temp.getName() + " raises!");
                 //update minimum blind
                 game.setMinBlind(game.getMaxBlind());
                 game.table().addLast(temp);
                 preflop(game);
                 return;
                 }
                 else
                 {
                 //choose another action
                 input = -1;
                 return;
                 }
                 //all-in
                 case 3:
                 if(confirm(input, prefloptions))
                 {
                 //make sure player has at least the minimum blind
                 if(temp.bet(game.getMinBlind()))
                 {
                  JOptionPane.showMessageDialog(null, temp.getName() + " goes ALL IN!");
                  game.table().addLast(temp);
                  preflop(game);
                  return;
                 }
                 else
                 {
                 //chose another action
                 input = -1;
                 return;
                 }
                }
                else
                input = -1;
                break;
                 //fold
                 case 4:
                 temp.fold(true);
                 JOptionPane.showMessageDialog(null, temp.getName() + " folds!");
                 game.table().addLast(temp);
                 preflop(game);
                 return;
                 //invalid input
                 default:
                 input = -1;
                 JOptionPane.showMessageDialog(null,"Please enter a number between 1 and 4");
                 break;
             }
             }
             
            }        
           else
            {
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to make another bet, " + temp.getName() + "?",
               "Confirm answer", JOptionPane.YES_NO_OPTION);
               if(answer == JOptionPane.YES_OPTION)
               {
               game.setHighBet(null);
               game.table().push(temp);
               preflop(game);
               return;
               }
               else
               game.table().addLast(temp);
               return;
               
           }
    
}

public static void ante(Game game)
{
    //game.table().peek().setDealer(true);
    for(Player p : game.table())
     {
         p.bet(game.getMinBlind());
         game.addPot(game.getMinBlind());
     }
     
     JOptionPane.showMessageDialog(null, "Everyone bets " + game.getMinBlind());
}

public static void postflop(Game game)
{
  Player temp = game.table().pop();
  int input = -1;
  while(input < 0)
  {
      //if no bet has been made, option for checking exists         
      input = Integer.parseInt(JOptionPane.showInputDialog(null,"Choose an action \n" + temp + game.getCommunity() + "\n 1. Bet \n 2. Go all in \n 3. Fold \n 4. Check \n" ));
      switch(input)
      {
          //bet of the users choice
          case 1: 
          int wager = Integer.parseInt(JOptionPane.showInputDialog(null,temp + "How much do you want to bet?" ));
          if(temp.bet(wager))
          {
          JOptionPane.showMessageDialog(null, temp.getName() + " calls!");
          game.addPot(wager);
          game.setMinBlind(wager);
          game.setHighBet(temp);
          game.table().addLast(temp);
          preflop(game);
          return;
          }
          else
          input = -1;
          return;  
          
          //go all in
          case 2:
          if(confirm(input, postfloptions))
          {
          temp.bet(temp.getBal());
          JOptionPane.showMessageDialog(null, temp.getName() + " goes ALL-IN!");
          game.addPot(temp.getBal());
          game.setMinBlind(temp.getBal());
          game.setHighBet(temp);
          game.table().addLast(temp);
          preflop(game);
          return;
           }
          else
          input = -1;
          break;
          
          //fold
          case 3:
          temp.fold(true);
          JOptionPane.showMessageDialog(null, temp.getName() + " folds!");
          if(temp.isDealer())
          {
          game.table().addLast(temp);
          return;
          }
          else
          {
          game.table().addLast(temp);
          postflop(game);
          return;
          }
          
          //check
          case 4:
          if(temp.isDealer())
          return;
          else
          {
          game.table().addLast(temp);
          postflop(game);
          return;
          }
          //invalid input
          default:
          input = -1;
          JOptionPane.showMessageDialog(null, "Please type in a valid number as shown");
          break;
      }
  }
}
     
    
public static void main(String[] args)
{
    int blinds;
    int numPlayers = -1;
    String name = "";
    LinkedList<Player> players = new LinkedList();
    Game poker;
    
    while(numPlayers < 3)
    {
    numPlayers = Integer.parseInt(JOptionPane.showInputDialog(null,"How many players will be playing today?(At least 3 players): ")); 
    }
    
    for(int i = 0; i < numPlayers; i++)
    {
       name = JOptionPane.showInputDialog("Name of Player " + (i + 1) + ": " );    
       players.push(new Player(name, 200));
    }
    
     blinds = JOptionPane.showConfirmDialog(null,"Would you like to use Blinds in your game?",
    "Blinds or Nah", JOptionPane.YES_NO_OPTION);
     
    if(blinds == JOptionPane.YES_OPTION)
    {
    poker = new Game(players, true);
    poker.deal();
    preflop(poker);
    }
    else
    {
    poker = new Game(players, false);
    poker.deal();
    ante(poker);
    }
    
    //flop 
    poker.addCommunity(3);
    poker.setMinBlind(0);
    JOptionPane.showMessageDialog(null, poker);
    
    //post flop betting
    poker.resetDealer();
    poker.unfold();
    Player temp = poker.table().pop();  
    temp.setDealer(true);
    poker.table().addLast(temp);
    postflop(poker);
    
    //turn round
    poker.addCommunity(1);
    poker.setMinBlind(0);
    JOptionPane.showMessageDialog(null, poker);
    
    //post turn betting
    poker.resetDealer();
    poker.unfold();
    temp = poker.table().pop();  
    temp.setDealer(true);
    poker.table().addLast(temp);
    postflop(poker);
    
    //river
    poker.addCommunity(1);
    JOptionPane.showMessageDialog(null, poker);
    
    //show down
    poker.resetDealer();
    poker.unfold();
    temp = poker.table().pop();  
    temp.setDealer(true);
    poker.table().addLast(temp);
    postflop(poker);
    
   //final card compare
   poker.winner();
   
}
 }

