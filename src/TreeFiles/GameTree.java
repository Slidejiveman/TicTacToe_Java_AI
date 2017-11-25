package TreeFiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import TicTacToePD.*;

public class GameTree {
	
	//-------------------Nested Node Class-----------------
	protected static class Node { 
		private TicTacToeField field;
		private Node parent;
		private int minimaxValue = -2; //indicates not having been set
		private char playerChar = 'O';
		private Node[] children;
		
		// Constructor
		public Node(TicTacToeField field, char player) {
			setParent(parent); //Create root then add it as the parent to the first set of nodes
			setField(field);
			setPlayerChar();
			playerChar = player;
			ArrayList<Point> moves = field.getAllMoves();
			this.children = new Node[moves.size()];
			
			// Recursive generation of the tree
			for(int i = 0; i < moves.size(); i++) {
				// Switches player by subtracting the player value from the sum of their ASCII values
				this.children[i] = new Node(new TicTacToeField(field, moves.get(i), player), (char)(167 - player));
			}
		}
		
		// accessor methods
		public TicTacToeField getField() {return this.field;}
		public Node getParent() {return this.parent;}
		public Node[] getChildren() {return children;}
		public int getMinimax() {return this.minimaxValue;}
		public char getPlayerChar() {return this.playerChar;}
		public int getNumChildren() {return this.children.length;}
		// update methods
		public void setField(TicTacToeField field) {this.field = field;}
		public void setParent(Node parentNode) {this.parent = parentNode;}
		public void addChild(int i, Node node) {
			children[i] = node;
			size++;
		}
		
		// HANDLE SETTING THE MINIMAX VALUE IN THE EVALUATION FUNCTION
		public void setMinimax(int minimax) {
		   this.minimaxValue = minimax;
		}
		
		// UNTESTED PLAYER SWITCH METHOD
		public void setPlayerChar() {
			if(getParent() == null) {
				this.playerChar = 'O';
			} else if (getParent().getPlayerChar() == 'O') {
				this.playerChar = 'X';
			} else {
				this.playerChar = 'O';
			}
		}
		
		//To String method
		public String toString() {
			return field.toString();
		}
	} //---------------End nested Node Class----------------

	
	protected Node root = null;
	protected static int size = 0;
	
	protected Node createNode(TicTacToeField field, char player) {
		return new Node(field, player);
	}
	
	// Constructor does nothing by default
	public GameTree() {
		root = new Node(new TicTacToeField(), 'O');
	}
	public GameTree(TicTacToeField field) {
		root = new Node(field, 'X');
	}
	
	// THE GAME PROLONGING ISSUE MIGHT BE HERE SINCE IT CAN'T DIFFERENTIATE
	// BETWEEN TWO WINNING PATHS
	// WIN YOU GETLASTMOVE AND THERE ARE TWO ADJACENT Xs IT NEEDS TO WIN THEN
	public Point getLastMove() {
	    
	    ArrayList<Point> moves = root.field.getAllMoves();
	    int max = -2;
	    int maxIndex = -1;
	    for(int i = 0; i < moves.size(); i++) {
	    	int evalValue = evaluate(root.children[i]);
	    	if(max < evalValue || root.children[i].field.isWin() == 'X'){
	    		max = evalValue;
	    		maxIndex = i;
	    	}
	    }
	    return moves.get(maxIndex);
	}
	
	// Validator checks to make sure the node is viable
	protected Node validate(Node n) throws IllegalArgumentException {
		if(!(n instanceof Node)) {
			throw new IllegalArgumentException("Not valid position type");
		}
		Node node = (Node) n;
		if(node.getParent() == node) {
			throw new IllegalArgumentException("p is no longer in the tree");
		}
		return node;
	}
	
	// Accessor methods
	public int size() {
		return size;
	}
	
	public Node root() {
		return root;
	}
	
	public boolean isInternal(Node node) {return node.getChildren() != null && node != root();}
    public boolean isLeaf(Node node) {return node.getChildren()[0] == null;}
    public boolean isRoot(Node node) {return node == root();}
    public boolean isEmpty() {return size() == 0;}
	
	public Node parent(Node p) throws IllegalArgumentException {
		Node node = validate(p);
		return node.getParent();
	}
	
    public Node[] getChildren(Node p) {
    	Node node = validate(p);
    	return node.getChildren();
    }
    
    // update methods
    public Node addRoot(TicTacToeField field, char player) throws IllegalStateException {
    	if (!isEmpty()) throw new IllegalStateException("Tree is not emtpy");
    	root = createNode(field, player);
    	size = 1;
    	root.setPlayerChar();
    	return root;
    }
      
    public TicTacToeField set(Node n, TicTacToeField field) throws IllegalArgumentException {
    	Node node = validate(n);
    	TicTacToeField temp = node.getField();
    	node.setField(field);
    	return temp;
    }
    
    // Traverse the tree breadth first
    public void breadthFirst() {
    	
    	Queue<Node> breadthQueue = new LinkedList<Node>();
    	if(root == null) {
    		System.err.println("I AM ERROR");
    	}
     	breadthQueue.add(this.root);
    	while(!breadthQueue.isEmpty()){
    		Node node = (Node)breadthQueue.remove();
    		if(node != null) {
    			System.out.println("Node: \n" + node);
    		
	    		//PERFORM THE VISIT ACTION FOR node HERE. EVALUATE if the node has a win (potentially if it isLeaf())
	    		evaluate(node);
	    		
	    		System.out.println(node.getMinimax());
	    		for(Node child : getChildren(node)) {
	    			System.out.println("Child: \n" + child);
	    			breadthQueue.add(child);
	    		}
    		}
    	}
    }
    
    // Current issue, if it has two paths to certain victory, it will take the first path, even if
    // that prolongs the game...
    public int evaluate(Node node) {
    	System.out.print(node.playerChar);
    	
    	int min = 1;
    	int max = -1;
    	
    	if(node.getMinimax() != -2) {
    		//System.out.println(node.field.toString() + ": " + node.getMinimax());
    		return node.getMinimax();
    	}
    	
    	//Check for win conditions.
    	if(node.getField().isWin() == 'O') {
			node.setMinimax(-1);
			return -1;
		} else if (node.getField().isWin() == 'X') { // issue crops up when there are two 1 paths
			node.setMinimax(1);
			return 1;
		} else {
			if(node.children.length == 0) {
				node.setMinimax(0);
				return 0;
			} else {
				for(int i = 0; i < node.children.length; i++) {
					int k = evaluate(node.children[i]);
					min = Math.min(min, k);
					max = Math.max(max, k);		//returns larger of the two
				}
				if(node.playerChar == 'O') {
					node.setMinimax(min);
					return min;
				} else {
					node.setMinimax(max);
					return max;
				}
			}
					
		}
    }
    
}
