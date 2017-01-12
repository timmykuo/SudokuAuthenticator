public class SudokuPuzzle {

	protected String[][] board; // table that holds the values
	protected boolean[][] mutable; // Table to determine if a slot is mutable
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String[] VALIDVALUES; // array that holds valid values for the
										// puzzle

	public SudokuPuzzle(int rows, int columns, int boxWidth, int boxHeight, String[] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		initializeBoard();

		// create a random sudoku puzzle using an algorithm
		SudokuGenerate g = new SudokuGenerate();
		int[][] newVals = g.nextBoard(35);

		// convert the int board to a string board
		String[][] stringVals = stringValuesFromIntValues(newVals);

		// set those string values to the board
		newGame(stringVals);
	}

	public String[][] stringValuesFromIntValues(int[][] values) {
		String[][] stringVals = new String[values.length][values[0].length];
		for (int r = 0; r < values.length; r++) {
			for (int c = 0; c < values[r].length; c++) {
				if (values[r][c] == 0) {
					stringVals[r][c] = "";
				} else {
					stringVals[r][c] = Integer.toString(values[r][c]);
				}
			}
		}
		return stringVals;
	}

	public void newGame(String[][] newValues) {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				board[r][c] = newValues[r][c];
			}
		}
		initializeMutableSlots();
	}

	public int getNumRows() {
		return this.ROWS;
	}

	public int getNumColumns() {
		return this.COLUMNS;
	}

	public int getBoxWidth() {
		return this.BOXWIDTH;
	}

	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}

	public String[] getValidValues() {
		return this.VALIDVALUES;
	}

	public String[][] getBoard() {
		return this.board;
	}

	public void makeMove(int row, int col, String value, boolean isMutable) {
		if (this.isValidValue(value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value;
			this.mutable[row][col] = isMutable;
		}
	}

	public boolean isSlotAvailable(int row, int col) {
		return (this.inRange(row, col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}

	public boolean isSlotMutable(int row, int col) {
		return this.mutable[row][col];
	}

	public String getValue(int row, int col) {
		if (this.inRange(row, col)) {
			return this.board[row][col];
		}
		return "";
	}

	private boolean isValidValue(String value) {
		for (String str : this.VALIDVALUES) {
			if (str.equals(value))
				return true;
		}
		return false;
	}

	public boolean inRange(int row, int col) {
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}

	@Override
	public String toString() {
		String str = "Game Board:\n";
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLUMNS; col++) {
				str += this.board[row][col] + " ";
			}
			str += "\n";
		}
		return str + "\n";
	}

	// sets the entire board to be an empty string
	private void initializeBoard() {
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLUMNS; col++) {
				this.board[row][col] = "";
			}
		}
	}

	// only allows slots that aren't in the constants to be initialized
	private void initializeMutableSlots() {
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLUMNS; col++) {
				if (this.board[row][col] == "") {
					this.mutable[row][col] = true;
				} else {
					this.mutable[row][col] = false;
				}
			}
		}
	}
}