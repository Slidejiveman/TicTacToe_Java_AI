package TicTacToePD;

//TRY AND WRAP POINT REFERENCES IN A NODE
//REPLACE ARRAYLISTS WITH TREE OF SAID NODES
//EVALUATE LEAF NODE IN RECURSION (MAKE SURE THIS IS THE CASE)

import java.awt.Point; 
import static TicTacToePD.TicTacToeField.*; 
import TicTacToePD.*;
 
public class TicTacToeAI { 
	 	 
   /**
    * The point class represents a location in 2D space. play() determines the action
    * that the computer should take in response to a player's move. The player always
    * goes first in this version of TTT, so the computer plays reactively. This also 
    * explains why evaluate() is always passed false here. The turn order is handled between
    * the two functions. If bestMove should be null, control is transfered to the randomMove() 
    * method in the field class.
    * @param field, a TTT board
    * @param player, a character representing a player's move
    * @return bestMove, which is a Cartesian coordinate point
    */
	 public Point play(TicTacToeField field, char player) { 
     int bestValue  = -1; 				// The Human Player always goes first, so -1 becomes the default value
     Point bestMove = null; 
     for(Point move: field.getAllMoves()) { 
        TicTacToeField tempField = field.clone(); 
        tempField.setMark(move, player); 	// In the tempField, set the player's mark at the desired location
        int actionValue = evaluate(tempField, player, false); //Determines the value of program's next move
        if(actionValue > bestValue) { 
            bestValue = actionValue; 
            bestMove = move; 
        } 
     } 
     // If unable to determine best move, transfer control over to randomMove(). Shouldn't happen.
     System.out.println("BestMove: " + bestMove);
     if(null == bestMove) { 
         return field.randomMove(); 	
     } 
     return bestMove; 
   } // end play
 
 
 /** 
  *  The evaluate method first determines whether or not the game is over via
  *  win, lose, or draw. If the game has not ended, then the mini-max algorithm
  *  runs. Coupling this method with findBestMove drives the entirety of the AI.
  *  The method returns different integer values to determine whether the game is
  *  over or which move is best.
  * @param field, a TTT board 
  * @param player, a character representing a player's action             
  * @param maximize, a boolean used to drive the mini-max algorithm's recursion
  * @return an integer
  */ 
   private int evaluate(TicTacToeField field, char player, boolean maximize) { 
     if(field.isWin(Player.PLAYER_X.toString().charAt(0))) { 
       if(Player.PLAYER_X.toString().charAt(0) == player) { 
         return  1;  
      } else { 
         return -1; 
       } 
     } 
     if(field.isWin(Player.PLAYER_O.toString().charAt(0))) { 
       if(Player.PLAYER_O.toString().charAt(0) == player) { 
         return  1;  
       } else { 
         return -1; 
       }  
     } 
     if(field.isFull()) { 
      return 0;  
     } 
      
     // Recursive evaluation
     int bestValue; 
     if(maximize) {  
         bestValue = -2; 
     } else { 
         bestValue =  2; 
     } 
     for(Point move: field.getAllMoves()) { 
       TicTacToeField tempField = field.clone(); 
       char nextPlayer = maximize ? 			// Depending on the value of maximize, the algorithm makes choices
                     player    : 
                     field.other(player); 
       tempField.setMark(move, nextPlayer); 
       int actionValue = evaluate(tempField, player, !maximize); // Handles the opposite case from the original call
       if(maximize) { 
           if(actionValue > bestValue) { 
               bestValue = actionValue; 
           } 
       } else { // minimize  
           if(actionValue < bestValue) { 
               bestValue = actionValue; 
           } 
       } 
     } 
     return bestValue; 
   } // end of recursive evaluate function

 } // end of class TicTacToeAI 
