package TreeFiles;

import java.awt.Point;

import TicTacToePD.Player;
import TicTacToePD.TicTacToeAI;
import TicTacToePD.TicTacToeField;
import TreeFiles.GameTree.Node;

public class TreeTester {

	public static void main(String[] args) {
		TicTacToeField field = new TicTacToeField();
		field.setMark(0, 1, 'O');
		Point move = new GameTree(field).getLastMove();
		System.out.println(move);
		field.setMark(move, 'X');
		System.out.println(field.toString());
		field.setMark(1, 2, 'O');
		move = new GameTree(field).getLastMove();
		System.out.println(move);
		field.setMark(move, 'X');
		System.out.println(field.toString());
		
		field.setMark(1, 1, 'O');
		move = new GameTree(field).getLastMove();
		System.out.println(move);
		field.setMark(move, 'X');
		System.out.println(field.toString());
	}

}
