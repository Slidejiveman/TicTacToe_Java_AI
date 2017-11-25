package TicTacToeHI;

import java.awt.*; 
import java.awt.event.*;  
import javax.swing.*; 
import TicTacToePD.*;
import TreeFiles.GameTree;

 
 
 public class TicTacToeGUI extends JFrame implements ActionListener { 
     
	 /**
	  * A button is made to represent every cell in the TTT field to create
	  * an easy to use, clickable interface.
	  */
	 JButton[][] buttons  = new JButton[3][3]; 
     TicTacToeField field = new TicTacToeField(); 
     //TicTacToeAI ai = new TicTacToeAI(); 
 
     /**
      * The main GUI.
      * @param args
      */
     public static void main(String[] args) { 
        new TicTacToeGUI(); 
     } // end main
 
      /**
      * The constructor creates the GUI and drives the program.
      */
     public TicTacToeGUI(){ 
         super("TicTacToe"); 
         JPanel panel = new JPanel(); 
         makeButtons(panel); 
         panel.setLayout(new GridLayout(3, 3)); 
         add(panel); 
         setSize(180, 180); 
         setDefaultCloseOperation(EXIT_ON_CLOSE); 
         setVisible(true); 
     } // end constructor
 
     /**
      * Sets up all of the buttons and adds the listeners to them
      * @param mp
      */
     void makeButtons(JPanel panel) { 
         for (int row = 0; row < 3; row++) { 
             for (int column = 0; column < 3; column++) { 
                 JButton button = new JButton("?"); 
                 buttons[row][column] = button; 
                 panel.add(button); 
                 button.addActionListener(this); 
             } 
         } 
     } // end makeButtons
 
 
     /**
      * This function moves the player and then the computer in response to the click 
      * provided by the human user.
      */
     public void actionPerformed(ActionEvent e) { 
         JButton source = (JButton) e.getSource(); 		//getSource returns a reference to the object we came from
         movePlayerThenAI(source); 
         redrawAll(); 
     } // end actionPerformed
 
     /**
      * This method is so named because it describes the programmed sequence of events.
      * The player selects a square, and then the computer selects a square. The goal of this
      * program is to see if the AI will make the appropriate choice based on input that it is
      * fed. So, as it is now, the player must go first.
      * @param source, the button that is the source of the action
      */
     private void movePlayerThenAI(JButton source) { 
         for (int row = 0; row < 3; row++) { 
             for (int column = 0; column < 3; column++) { 
                 clickedButton(source, row, column); 
             }  
         } 
     }  // end movePlayerThenAI
 
     /**
      * clickedButton also handles the non-clicked buttons, which is necessary to prevent the
      * program from crashing. This is the section of the GUI that ties in to whether or not
      * the the computer wins, the player wins, or there is a draw. Whenever one of these events
      * occurs, an alert pops up displaying the appropriate message.
      * @param source
      * @param row
      * @param column
      */
     private void clickedButton(JButton source, int row, int column) { 
         // handle all of the not clicked buttons
         if (buttons[row][column] != source) { 
             return; 
         } 
         field.setMark(row, column, Player.PLAYER_O.toString().charAt(0));
         
         // This is literally only here for completeness. You can't win!
         if (field.isWin(Player.PLAYER_O.toString().charAt(0))) {  //CHECK ISWIN FOR ERROR
             JOptionPane.showMessageDialog(this, "Humanity survives! (Not really...)"); 
         } 
         if (field.isFull()) {
        	 JOptionPane.showMessageDialog(this, "Tie, but it's still Game Over!");
         } 
         if (!field.isFull()) { 
             //Point move = ai.play(field, Player.PLAYER_X.toString().charAt(0)); 
        	 
        	 
        	 Point move = new GameTree(field).getLastMove();
             field.setMark(move, Player.PLAYER_X.toString().charAt(0)); 
             if (field.isWin(Player.PLAYER_X.toString().charAt(0))) { 
                 redrawAll(); 
                 JOptionPane.showMessageDialog(this, "The Machines have won..."); 
             } 
        
         }
 
     } // end clickedButton
 
     /**
      * Updates the appearance of the GUI to represent the current state of the board.
      */
     void redrawAll() { 
         for (int row = 0; row < 3; row++) { 
             for (int column = 0; column < 3; column++) { 
                 JButton button = buttons[row][column]; 
                 if (0 != field.getMark(row, column)) { 
                     button.setText("" + field.getMark(row, column)); 
                     button.setEnabled(false); 
                 } 
             } 
         } 
     } // end redrawAll 
      
 } // end of class TicGui 
