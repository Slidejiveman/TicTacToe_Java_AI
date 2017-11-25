package TicTacToePD;


import java.awt.Point; 
import TicTacToePD.*; 

 public class TicTacToeTester { 
	 
	 /**
	  * Test main
	  * @param args, the command line arguments parameter
	  */
	 public static void main(String[] args) { 
		  new TicTacToeTester().test(); 
   } // end main
    
	/**
	 * The method that runs through code to test if it works. The AI
	 * should place its mark to complete 3 in a row.
	 */
   private void test() { 
     // MoveTest 
     TicTacToeField field = testField(); 
     TicTacToeField copiedField = field.clone(); 
     copiedField.setMark(2,1, Player.PLAYER_O.toString().charAt(0)); 
     System.out.println(field); 
     System.out.println(copiedField); 
      
     // bestTest
     TicTacToeAI ai = new TicTacToeAI(); 	
     Point point; 
     point = ai.play(field, Player.PLAYER_X.toString().charAt(0)); 
     System.out.println("best Move:" + point); 
   } // end test
 

 /** 
  *  Sets up the field for testing
  */ 
   private TicTacToeField testField() { 
     TicTacToeField field = new TicTacToeField(); 
     field.setMark(0, 0, Player.PLAYER_X.toString().charAt(0)); 
     field.setMark(0, 1, Player.PLAYER_O.toString().charAt(0));  
     field.setMark(0, 2, Player.NOPLAYER.toString().charAt(0)); 
     field.setMark(1, 0, Player.PLAYER_O.toString().charAt(0)); 
     field.setMark(1, 1, Player.PLAYER_O.toString().charAt(0));  
     field.setMark(1, 2, Player.PLAYER_X.toString().charAt(0)); 
     field.setMark(2, 0, Player.PLAYER_X.toString().charAt(0)); 
     field.setMark(2, 1, Player.NOPLAYER.toString().charAt(0));  
     field.setMark(2, 2, Player.NOPLAYER.toString().charAt(0)); 
     return field; 
   } // end testfield
 
 } //end TestTicTacToe class