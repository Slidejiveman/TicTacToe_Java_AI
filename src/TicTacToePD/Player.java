package TicTacToePD;

public enum Player {
	PLAYER_X('X'), PLAYER_O('O'), NOPLAYER('0');
	
    private char mark = '0';
	
	private Player (char mark) {
    	this.mark = mark;
    }
	
	public char asCharacter() {
		return mark;
	}
	
	public int getInt() {
		if(mark != 'X' && mark != 'O'){
			return Integer.parseInt(String.valueOf(mark));
		} else
			return 1;
	}
	
	public String toString() {
		return String.valueOf(mark);
	}
}
