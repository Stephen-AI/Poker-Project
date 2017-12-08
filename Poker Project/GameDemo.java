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
   
public static void preflop(Game game)
{
     Player temp = game.table().pop();
      int input = -1;
        if(game.blinds)  
        {
             if(!temp.folded() || temp.lastbet != game.getMinBlind())
             {
                  if(temp.lastbet == game.getMinBlind())
                  return;
                  else if(temp.folded())
                  {
                  game.table().addLast(temp);
                  preflop(game);
                  }
                  else
                  {
                  while(input < 0 && temp.lastbet != game.getMinBlind())
                  {
                    input = Integer.parseInt(JOptionPane.showInputDialog(null,"Choose an action " + temp.getName() + "\n 1. Call (" + game.getMinBlind() + ") \n 2. Raise (" + game.getMaxBlind() + ")\n 3. Go all in \n 4. Fold \n" ));
                    switch(input)
                    {
                          //call
                          case 1:
                          //making sure player has enough money to bet
                       if(temp.bet(game.getMinBlind()))
                      {
                     JOptionPane.showMessageDialog(null, temp.getName() + " calls!");
                     game.table().addLast(temp);
                     preflop(game);
                     break;
                     }
                     else//no mo money
                     {
                     JOptionPane.showMessageDialog(null, temp.getName() + " is out of the game due to lack of funds");
                     temp.fold();
                     game.table().addLast(temp);
                     preflop(game);
                     break;
                     }
                     //raise
                     case 2: 
                     if(temp.bet(game.getMaxBlind()))
                     {
                     JOptionPane.showMessageDialog(null, temp.getName() + " raises!");
                     //update minimum blind
                     game.setMinBlind(game.getMaxBlind());
                     game.table().addLast(temp);
                     preflop(game);
                     break;
                     }
                     else
                     {
                     //choose another action
                     input = -1;
                     break;
                     }
                     //all-in
                     case 3:
                     //make sure player has at least the minimum blind
                     if(temp.bet(game.getMinBlind()))
                     {
                      JOptionPane.showMessageDialog(null, temp.getName() + " goes ALL IN!");
                      game.table().addLast(temp);
                      preflop(game);
                      break;
                     }
                     else
                     {
                     //chose another action
                     input = -1;
                     break;
                     }
                     //fold
                     case 4:
                     temp.fold();
                     JOptionPane.showMessageDialog(null, temp.getName() + " folds!");
                     game.table().addLast(temp);
                     preflop(game);
                     break;
                     default:
                     input = -1;
                     JOptionPane.showMessageDialog(null,"Please enter a number between 1 and 4");
                     break;
                 }
                 }
                 
                }
            
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
        poker = new Game(players, true);
        else
        poker = new Game(players, false);
        preflop(poker);
    }
 }

