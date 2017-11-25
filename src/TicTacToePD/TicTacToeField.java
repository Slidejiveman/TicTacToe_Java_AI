package TicTacToePD;

import java.awt.Point; 
import java.util.ArrayList;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import TicTacToePD.*;

 
 /** 
  *  
  * @author  
  */ 
 public class TicTacToeField implements Cloneable {  
   
   /**
    * The win conditions are all held here as a three dimensional array of
    * value coordinate value pairs. Since these conditions cannot change, they
    * are made final.
    */
   final static int [][][] WIN_CONDITIONS = 
	   { 
	           // row 
	           {{0, 0}, {0, 1}, {0, 2}}, 
	           {{1, 0}, {1, 1}, {1, 2}}, 
	           {{2, 0}, {2, 1}, {2, 2}}, 
	           // column 
	           {{0, 0}, {1, 0}, {2, 0}}, 
	           {{0, 1}, {1, 1}, {2, 1}}, 
	           {{0, 2}, {1, 2}, {2, 2}}, 
	           // diagonal 
	           {{0, 0}, {1, 1}, {2, 2}}, 
	           {{2, 0}, {1, 1}, {0, 2}} 
	         }; 
    
  /**
   * The TTT board
   */
   char [][] field; 
    
   /**
    * The constructor initializes the 3x3 field with no particular values.
    */
  public TicTacToeField() { 
       field = new char[3][3]; 
   } // end constructor
  
  public TicTacToeField(TicTacToeField field, Point point, char player) {
	  this.field = new char[3][3];
	  
	  for(int i = 0; i < 3; i++) {
		  for(int j = 0; j < 3; j++) {
			  this.field[i][j] = field.field[i][j]; //Same class, can access private members
		  }
	  }
	  this.field[point.x][point.y] = player;
  }
    
  /**
   * Passing the row and the column into the getMark method returns the player mark that
   * is in that cell of the TTT field.
   * @param row
   * @param column
   * @return
   */
   public char getMark(int row, int column) { 
      return field[row][column]; 
   } // end getMark
 
 /**
  * The setMark function sets a particular cell in the TTT field with the given player's mark.
  * This function is overloaded in that it allows either two integers to be passed to determine the
  * point's location, or the other version simply accepts a point.
  * @param row
  * @param column
  * @param player
  */
   public void setMark(int row, int column, char player) { 
       field[row][column] = player; 
   } // end setMark
   public void setMark(Point move, char player) { 
      setMark(move.x, move.y, player);   
   } // end setMark
    
    /**
     * The isfull() method checks to see if there are any available spaces left for play.
     * This method is used to determine whether or not the game should end, among other things.
     * @return boolean representing whether or not the field is filled with marks.
     */
   public boolean isFull() { 
     int filled = 0; 
     for(int row = 0; row < 3; row ++) { 
         for(int column = 0; column < 3; column ++) { 
             if(0 != getMark(row, column)) { 
                 filled = filled + 1; 
             } 
         } 
     } 
     return 9 == filled;  
   } // end isFull
        
   /** 
    * isWin() is a second instance of overloaded functions that determine whether or not the
    * computer of the human player is the victor. The boolean version of the function 
    * determines whether or not there is a winner by iterating through the win conditions 
    * and comparing a 2D array of the different possible "three in a row" formations that exist.
    * 
    * The char version returns the enumerators that represent the player who makes either X or O marks
    * or a 0 if no player wins.
    */ 
   public char isWin() { 
      if(isWin(Player.PLAYER_X.toString().charAt(0))) return Player.PLAYER_X.toString().charAt(0); 
      if(isWin(Player.PLAYER_O.toString().charAt(0))) return Player.PLAYER_O.toString().charAt(0); 
      return Player.NOPLAYER.toString().charAt(0); 
   } // end isWin
   public boolean isWin(char player) { 
     for(int[][] winningRow : WIN_CONDITIONS) { 
         if(playerWinsWithRow(winningRow, player)) { 
             return true; 
         } 
      } 
      return false; 
   } // end isWin
    
   /**
    * Used as a helper function to determine whether there is a win. The winningRow array holds possible
    * victories on the TTT field. If all of the p0 variables equal the same thing, then there is a winner.
    * Essentially, it converts the 3D array of WIN_CONDITIONS and makes it into a 2D array that can be tested
    * against the field. It's a little trick I picked up online ;)
    * @param winningRow, a 2D array that iterates through the win conditions
    * @param mark, a character representing a mark in a field cell
    * @return boolean, for whether or not we have a winner
    */
   boolean playerWinsWithRow(int[][] winningRow, char mark) { 
     char p0 = field[winningRow[0][0]][winningRow[0][1]]; 
     char p1 = field[winningRow[1][0]][winningRow[1][1]]; 
     char p2 = field[winningRow[2][0]][winningRow[2][1]]; 
     return p0 == mark && p1 == mark && p2 == mark; 
   } // end playerWinsWithRow
    
   /**
    * clone() makes good on the promise to implement the Cloneable interface. Clones
    * are used to hold temporary TTT fields so they can be safely manipulated.
    */
   @Override 
   public TicTacToeField clone() { 
    TicTacToeField copy = new TicTacToeField(); 
     for(int row = 0; row < 3; row++) { 
       for (int column = 0; column < 3; column++) { 
         copy.field[row][column] = this.field[row][column]; 
       } 
     } 
     return copy; 
   } // end clone
    
   /**
    * Makes an ArrayList out of the board and adds a point to the board in a given location.
    * The point in said location functions like a node in a tree would, which stores the needed information
    * for the program to interact with the board, get and set values, and determine outcomes.
    * @return all of the available moves that can be taken.
    */
   public ArrayList<Point> getAllMoves() { 
     ArrayList<Point> allMoves; 
     allMoves = new ArrayList<Point>(); 
     for(int row = 0; row < 3; row ++) { 
       for (int column = 0; column < 3; column++) { 
         if(0 == this.field[row][column]){ 				// A zero represents an open square to move into
             allMoves.add(new Point(row, column)); 
         } 
       } 
     } 
     return allMoves; 
   } // end getAllMoves
 
   /**
    * randomMove() generates a random move as long as the TTT field isn't filled. It grabs
    * a random point using Math.random(), which returns a random number between 0 and 1. 
    * Multiplying this by the size of the possibleMoves array list allows it to randomly select
    * a position held within it.
    * @return 
    */
  public Point randomMove() { 
     if(isFull()) return null; // no more moves 
     ArrayList<Point> possibleMoves; 
     possibleMoves = getAllMoves(); 	//All moves represents all open squares one could move into
     int randomPosition = (int) (Math.random() 
                             * possibleMoves.size()); 
     return possibleMoves.get(randomPosition); 
   } // end randomMove
 
 
   /** 
    * A simple function used to switch the active player.
    * @param player, a player's character mark, which represents said player
    * @return returns the opposite player or no player to cover all the bases
    */ 
   char other(char player) { 
     if(Player.PLAYER_X.toString().charAt(0) == player) return Player.PLAYER_O.toString().charAt(0); 
     if(Player.PLAYER_O.toString().charAt(0) == player) return Player.PLAYER_X.toString().charAt(0); 
     return Player.NOPLAYER.toString().charAt(0); 
   } // end other
    
  /**
   * toString returns a string representation of the TTT field used for testing the
   * methods prior to the inclusion of a GUI
   * @return a string representation of a TTT field 
   */
   public String toString() { 
     String string =  "+-------+"; 
     for(int row = 0; row < 3; row ++) { 
        string = string + "\n|"; 
        for(int column = 0; column < 3; column ++) 
        { 
            char mark = field[row][column]; 
            if(0 != mark) { 
              string = string + " " + mark;  
            } else { 
              string = string + "  "; 
            } 
        } 
        string = string + " |"; 
        if(row < 2) { 
            string = string + "\n|-------|"; 
        } 
     } 
     string = string +  "\n+-------+\n"; 
     return string; 
   } // end toString
 } // end TicTacToeField class
