package gamestate;

import static main.CalcudokuGame.GAME_HEIGHT;
import static main.CalcudokuGame.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import buttons.Button;
import main.CalcudokuGame;

public class Options extends JPanel{
	Button optionButton[] = new Button[3];
	
	public Options() {
		this.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
		this.setLayout(null);
		addButtons();
	}

	public void addButtons() {
		BufferedImage image = CalcudokuGame.getSpriteAtlas("onandoffbutton.png");
		BufferedImage on = image.getSubimage(0, 250, 500, 250);
		BufferedImage off = image.getSubimage(0, 0, 500, 250);
		
		if(CalcudokuGame.musicOn)
			optionButton[0] = new Button(on, 300, 280, 200, 100);
		else 
			optionButton[0] = new Button(off, 300, 280, 200, 100);
		optionButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				if(CalcudokuGame.musicOn) {
					optionButton[0].setImage(off);
					CalcudokuGame.musicOn = false;
					CalcudokuGame.muteMusic();
				} else {
					optionButton[0].setImage(on);
					CalcudokuGame.musicOn = true;
					CalcudokuGame.playMusic();
				}
			}
		});
		this.add(optionButton[0]);
		
		if(CalcudokuGame.sfxOn)
			optionButton[1] = new Button(on, 300, 375, 200, 100);
		else 
			optionButton[1] = new Button(off, 300, 375, 200, 100);
		optionButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				if(CalcudokuGame.sfxOn) {
					optionButton[1].setImage(off);
					CalcudokuGame.sfxOn = false;
				} else{
					optionButton[1].setImage(on);
					CalcudokuGame.sfxOn = true;
				}
			}
		});
		this.add(optionButton[1]);
		
		optionButton[2] = new Button(CalcudokuGame.getSpriteAtlas("exit.png"), 200, 490, 200, 80);
		optionButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.MENU;
			}
		});
		this.add(optionButton[2]);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(CalcudokuGame.getSpriteAtlas("optionspanel.png"), 0, 0, null);
    }
}
