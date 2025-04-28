package buttons;

import static main.CalcudokuGame.BOARD_SIZE;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gamestate.NumberOfGridOption;
import main.CalcudokuGame;

public class NumberOfGridButton extends JButton implements ActionListener{
	NumberOfGridOption numberOfGridOption;
	
	private int value;
	
	public NumberOfGridButton(NumberOfGridOption numberOfGridOption,int value, int x, int y) {
		this.numberOfGridOption = numberOfGridOption;
		this.value = value;
		
		this.setBounds(x, y, 100, 50);
		this.setText(String.valueOf(value));
		this.setFocusPainted(false);
		this.setBorder(null);
		this.setLayout(null);
		this.setFont(new Font("Sansarif Sans", Font.BOLD, 25));
		this.setForeground(new Color(153, 76, 0));
		this.setBackground(new Color(253, 215, 128));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this != numberOfGridOption.getChosenNumber() && !numberOfGridOption.isHasChosen()) {
			numberOfGridOption.setChosenNumber(this);
			this.setBackground(Color.green);
			numberOfGridOption.setHasChosen(true);
			CalcudokuGame.numberOfGrids = value;
			CalcudokuGame.GRID_SIZE = BOARD_SIZE / CalcudokuGame.numberOfGrids;
		} else if(this != numberOfGridOption.getChosenNumber() && numberOfGridOption.isHasChosen()) {
			numberOfGridOption.getChosenNumber().setBackground(new Color(253, 215, 128));
			numberOfGridOption.setChosenNumber(this);
			this.setBackground(Color.green);
			CalcudokuGame.numberOfGrids = value;
			CalcudokuGame.GRID_SIZE = BOARD_SIZE / CalcudokuGame.numberOfGrids;
		}
		CalcudokuGame.playTap();
		numberOfGridOption.showStartButton();
	}
}








