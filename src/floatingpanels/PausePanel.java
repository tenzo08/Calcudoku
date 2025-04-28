package floatingpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import buttons.Button;
import gamestate.Gamestate;
import main.CalcudokuGame;
import main.GamePanel;

public class PausePanel extends JPanel{
	GamePanel gamePanel;
	
	Button[] pauseButtons = new Button[3];

	public PausePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setBounds(25, 130, 550, 300);
		this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(CalcudokuGame.getSpriteAtlas("pausepanel.png"), 0, 0, 550, 300, null);
		
		BufferedImage image = CalcudokuGame.getSpriteAtlas("buttons.png");
		BufferedImage Ximage = CalcudokuGame.getSpriteAtlas("nomusic.png");
		
		BufferedImage musicOn = image.getSubimage(80, 80, 80, 80);
		BufferedImage musicOff = Ximage.getSubimage(80, 80, 80, 80);
		
		BufferedImage sfxOn = image.getSubimage(160, 80, 80, 80);
		BufferedImage sfxOff = Ximage.getSubimage(160, 80, 80, 80);
		
		pauseButtons[0] = new Button(image.getSubimage(160, 240, 80, 80), 300, 160, 90, 90);
		pauseButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.PLAYING;
				gamePanel.setPaused(false);
				gamePanel.setGameHasStarted(true);
				gamePanel.addInputs();
				gamePanel.removePausePanel();
				gamePanel.setHasExistingPanel(false);
			}
		});
		this.add(pauseButtons[0]);
		
		if(CalcudokuGame.musicOn)
			pauseButtons[1] = new Button(musicOn, 210, 160, 90, 90);
		else
			pauseButtons[1] = new Button(musicOff, 210, 160, 90, 90);
		pauseButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CalcudokuGame.musicOn) {
					CalcudokuGame.muteMusic();
					pauseButtons[1].setImage(musicOff);
					CalcudokuGame.musicOn = false;
				} else {
					CalcudokuGame.playMusic();
					pauseButtons[1].setImage(musicOn);
					CalcudokuGame.musicOn = true;
				}
				
				CalcudokuGame.playTap();
				gamePanel.setHasExistingPanel(false);
				gamePanel.repaintPausePanel();
			}
			
		});
		this.add(pauseButtons[1]);
		
		if(CalcudokuGame.sfxOn)
			pauseButtons[2] = new Button(sfxOn, 120, 160, 90, 90);
		else
			pauseButtons[2] = new Button(sfxOff, 120, 160, 90, 90);
		pauseButtons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CalcudokuGame.sfxOn) {
					pauseButtons[2].setImage(sfxOff);
					CalcudokuGame.sfxOn = false;
				} else {
					CalcudokuGame.playMusic();
					pauseButtons[2].setImage(sfxOn);
					CalcudokuGame.sfxOn = true;
				}
				
				CalcudokuGame.playTap();
				gamePanel.setHasExistingPanel(false);
				gamePanel.repaintPausePanel();
			}
			
		});
		this.add(pauseButtons[2]);
	}
}
