package floatingpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CalcudokuGame;
import main.GamePanel;

public class MaxHint extends JPanel{
	JLabel label;
	GamePanel gamePanel;

	public MaxHint(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setLayout(null);
		this.setBounds(150, 180, 300, 300);
		this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(CalcudokuGame.getSpriteAtlas("maxhintnotif.png"),0, 0, 300, 300, null);
	}
}
