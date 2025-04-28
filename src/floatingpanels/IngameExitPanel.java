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

public class IngameExitPanel extends JPanel{
	GamePanel gamePanel;
	
	Button[] pauseButtons = new Button[2];

	public IngameExitPanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setBounds(25, 130, 550, 300);
		this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(CalcudokuGame.getSpriteAtlas("ingameexitpanel.png"), 0, 0, 550, 300, null);
		
		BufferedImage image = CalcudokuGame.getSpriteAtlas("buttons.png");
		
		pauseButtons[0] = new Button(image.getSubimage(0, 0, 80, 80), 300, 190, 70, 70);
		pauseButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.MENU;
				CalcudokuGame.hour = 0;
				CalcudokuGame.minute = 0;
				CalcudokuGame.second = 0;
				CalcudokuGame.time = "00 : 00 : 00";
				CalcudokuGame.resetInputs();
				gamePanel.setHasExistingPanel(false);
			}
		});
		this.add(pauseButtons[0]);
		
		pauseButtons[1] = new Button(image.getSubimage(320, 80, 80, 80), 180, 190, 70, 70);
		pauseButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.PLAYING;
				gamePanel.removeIngameExitPanel();
				gamePanel.setGameHasStarted(true);
				gamePanel.addInputs();
				gamePanel.setHasExistingPanel(false);

			}
		});
		this.add(pauseButtons[1]);
	}
}
