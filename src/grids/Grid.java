package grids;

import static main.CalcudokuGame.GRID_SIZE;
import static main.CalcudokuGame.numberOfGrids;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.CalcudokuGame;
import main.GamePanel;

public class Grid extends JButton implements ActionListener, KeyListener{
	private JLabel resultLabel;
	
	private int indexX, indexY, x, y, gridSizeX, gridSizeY;
	private int value, groupIndex, groupResult = 0;
	private boolean isGrouped, isChosen;
	
	public Grid(int value, int y, int x) {
		this.indexX = x;
		this.indexY = y;
		this.x = x * GRID_SIZE + 30;
		this.y = y * GRID_SIZE + 90;
		this.gridSizeX = GRID_SIZE - 1;
		this.gridSizeY = GRID_SIZE - 1;
		this.value = value;
		
		this.setFocusPainted(false);
		this.setBorder(null);
		this.setLayout(null);
		this.setFont(new Font("Sansarif Sans", Font.BOLD, 25));
		this.setForeground(new Color(153, 76, 0));
		this.setBackground(new Color(253, 215, 128));
		this.addActionListener(this);
		this.addKeyListener(this);
	}
	
	public void setBounds() {
		this.setBounds(x, y, gridSizeX, gridSizeY);
	}
	
	public void setResult() {
		if(groupResult != 0) {
			resultLabel = new JLabel(String.valueOf(groupResult));
			resultLabel.setFont(new Font("Sansarif Sans", Font.BOLD, 13));
			resultLabel.setBounds(3, 3, 20, 13);
			this.add(resultLabel);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!this.isChosen && CalcudokuGame.chosenGrid == null) {
			CalcudokuGame.playTap();
			CalcudokuGame.chosenGrid = this;
			
			this.setBackground(Color.green);
			this.isChosen = true;
			
		} else if(this.isChosen && CalcudokuGame.chosenGrid == this) {
			CalcudokuGame.playTap();
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
			
		} else if(!this.isChosen && CalcudokuGame.chosenGrid != null) {
			CalcudokuGame.playTap();
			CalcudokuGame.chosenGrid.setBackground(new Color(253, 215, 128));
			CalcudokuGame.chosenGrid.isChosen = false;
			
			CalcudokuGame.chosenGrid = this;
			
			this.setBackground(Color.green);
			this.isChosen = true;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		CalcudokuGame.playTap();
		if(e.getKeyCode() == KeyEvent.VK_1 && numberOfGrids >= 1 && this.isChosen) {
			this.setText("1");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 1;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_2 && numberOfGrids >= 2 && this.isChosen) {
			this.setText("2");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 2;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_3 && numberOfGrids >= 3 && this.isChosen) {
			this.setText("3");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 3;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_4 && numberOfGrids >= 4 && this.isChosen) {
			this.setText("4");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 4;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_5 && numberOfGrids >= 5 && this.isChosen) {
			this.setText("5");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 5;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_6 && numberOfGrids >= 6 && this.isChosen) {
			this.setText("6");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 6;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_7 && numberOfGrids >= 7 && this.isChosen) {
			this.setText("7");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 7;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_8  && numberOfGrids >= 8 && this.isChosen) {
			this.setText("8");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 8;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_9 && numberOfGrids >= 9 && this.isChosen) {
			this.setText("9");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 9;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE  && this.isChosen) {
			this.setText("");
			CalcudokuGame.input[this.getIndexY()][this.getIndexX()] = 0;
			CalcudokuGame.chosenGrid = null;
			
			this.setBackground(new Color(253, 215, 128));
			this.isChosen = false;
		}
		CalcudokuGame.checkSimilarity(GamePanel.getGrid());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isGrouped() {
		return isGrouped;
	}

	public void setGrouped(boolean isGrouped) {
		this.isGrouped = isGrouped;
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getGridSizeX() {
		return gridSizeX;
	}

	public void setGridSizeX(int gridSizeX) {
		this.gridSizeX = gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public void setGridSizeY(int gridSizeY) {
		this.gridSizeY = gridSizeY;
	}

	public int getGroupResult() {
		return groupResult;
	}

	public void setGroupResult(int groupResult) {
		this.groupResult = groupResult;
	}

	public JLabel getResultLabel() {
		return resultLabel;
	}

	public void setResultLabel(JLabel resultLabel) {
		this.resultLabel = resultLabel;
	}

	public int getIndexX() {
		return indexX;
	}

	public void setIndexX(int indexX) {
		this.indexX = indexX;
	}

	public int getIndexY() {
		return indexY;
	}

	public void setIndexY(int indexY) {
		this.indexY = indexY;
	}
}
