package buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class Button extends JButton{
	private BufferedImage image;
	private int width, height;
	
	public Button(BufferedImage image, int x, int y, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
		
		this.setBounds(x, y, width, height);
		this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, width, height, null);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		this.repaint();
	}
}
