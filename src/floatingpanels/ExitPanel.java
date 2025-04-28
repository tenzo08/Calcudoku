package floatingpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import buttons.Button;
import gamestate.Menu;
import main.CalcudokuGame;

public class ExitPanel extends JPanel{
	Menu menu;
	
	Button[] pauseButtons = new Button[2];

	public ExitPanel(Menu menu) {
		this.menu = menu;
		this.setBounds(25, 130, 550, 300);
		this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(CalcudokuGame.getSpriteAtlas("exitpanel.png"), 0, 0, 550, 300, null);
		
		BufferedImage image = CalcudokuGame.getSpriteAtlas("buttons.png");
		
		pauseButtons[0] = new Button(image.getSubimage(0, 0, 80, 80), 300, 190, 70, 70);
		pauseButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				System.exit(0);
			}
		});
		this.add(pauseButtons[0]);
		
		pauseButtons[1] = new Button(image.getSubimage(320, 80, 80, 80), 180, 190, 70, 70);
		pauseButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				menu.removeExitPanel();
			}
			
		});
		this.add(pauseButtons[1]);
	}
}
