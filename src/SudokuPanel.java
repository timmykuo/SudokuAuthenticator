import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {

	private SudokuPuzzle puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;

	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new SudokuPuzzle(9, 9, 3, 3, new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}

	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}

	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public SudokuPuzzle getPuzzle() {
		return this.puzzle;
	}

	// paints the sudoku puzzle
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f, 1.0f, 1.0f));

		int slotWidth = this.getWidth() / puzzle.getNumColumns();
		int slotHeight = this.getHeight() / puzzle.getNumRows();

		usedWidth = (this.getWidth() / puzzle.getNumColumns()) * puzzle.getNumColumns();
		usedHeight = (this.getHeight() / puzzle.getNumRows()) * puzzle.getNumRows();

		g2d.fillRect(0, 0, usedWidth, usedHeight);

		g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
		for (int x = 0; x <= usedWidth; x += slotWidth) {
			if ((x / slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			} else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}

		for (int y = 0; y <= usedHeight; y += slotHeight) {
			if ((y / slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			} else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}

		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for (int row = 0; row < puzzle.getNumRows(); row++) {
			for (int col = 0; col < puzzle.getNumColumns(); col++) {
				if (!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col * slotWidth) + ((slotWidth / 2) - (textWidth / 2)),
							(row * slotHeight) + ((slotHeight / 2) + (textHeight / 2)));
				}
			}
		}
		if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth, currentlySelectedRow * slotHeight, slotWidth, slotHeight);
		}
	}

	// inserts the selected value into the puzzle
	public void messageFromNumActionListener(String buttonValue) {
		if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
			repaint();
		}
	}

	// action listener for button selection panel with valid values
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());
		}
	}

	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth / puzzle.getNumColumns();
				int slotHeight = usedHeight / puzzle.getNumRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
}