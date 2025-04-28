package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buttons.Button;
import buttons.NumberOfGridButton;
import main.CalcudokuGame;

public class NumberOfGridOption extends JPanel{
	Menu menu;
	NumberOfGridButton[] numberOfGridButton = new NumberOfGridButton[5];
	
	JLabel label;
	JButton startButton;
	private NumberOfGridButton chosenNumber;
	
	private boolean hasChosenANumber, hasChosen;

	public NumberOfGridOption(Menu menu) {
		this.menu = menu;
		this.setBounds(100, 250, 400, 360);
		this.setOpaque(false);
        this.setBackground(new Color(255, 0, 0, 0));
        setChosenNumber(null);
        
        addLabel();
        addButtons();
	}

	private void addLabel() {
		label = new JLabel("No. of Grids");
		label.setFont(new Font("Sansarif Sans", Font.BOLD, 40));
		label.setForeground(new Color(153, 76, 0));
		label.setBounds(0, 0, 400, 50);
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label);
	}

	private void addButtons() {
		numberOfGridButton[0] = new NumberOfGridButton(this, 5, 25, 75);
		numberOfGridButton[1] = new NumberOfGridButton(this, 6, 150, 75);
		numberOfGridButton[2] = new NumberOfGridButton(this, 7, 275, 75);
		numberOfGridButton[3] = new NumberOfGridButton(this, 8, 82, 150);
		numberOfGridButton[4] = new NumberOfGridButton(this, 9, 207, 150);
		this.add(numberOfGridButton[0]);
		this.add(numberOfGridButton[1]);
		this.add(numberOfGridButton[2]);
		this.add(numberOfGridButton[3]);
		this.add(numberOfGridButton[4]);
	}
	
	public void showStartButton() {
		startButton = new Button(CalcudokuGame.getSpriteAtlas("start.png"), 100, 225, 200, 80);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasChosen) {
					CalcudokuGame.playTap();
					Gamestate.state = Gamestate.PLAYING;
					CalcudokuGame.gameDone = false;
					CalcudokuGame.hour = 0;
					CalcudokuGame.minute = 0;
					CalcudokuGame.second = 0;
					CalcudokuGame.time = "00 : 00 : 00";
				}
				menu.removeAll();
				menu.addButtons();
			}
		});
		this.add(startButton);
	}

	public boolean isHasChosenANumber() {
		return hasChosenANumber;
	}

	public void setHasChosenANumber(boolean hasChosenANumber) {
		this.hasChosenANumber = hasChosenANumber;
	}

	public NumberOfGridButton getChosenNumber() {
		return chosenNumber;
	}

	public void setChosenNumber(NumberOfGridButton chosenNumber) {
		this.chosenNumber = chosenNumber;
	}

	public boolean isHasChosen() {
		return hasChosen;
	}

	public void setHasChosen(boolean hasChosen) {
		this.hasChosen = hasChosen;
	}
}	
