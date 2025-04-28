package floatingpanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import buttons.Button;
import gamestate.Gamestate;
import main.CalcudokuGame;
import main.GamePanel;

public class GameDonePanel extends JPanel implements MouseMotionListener, MouseListener{
	private int initialX, initialY;
    private int offsetX, offsetY;
    private int panelWidth = 550, panelHeight = 300;
    private int originalX = 25, originalY = 130;
	
	GamePanel gamePanel;
	
	Button[] pauseButtons = new Button[2];

	public GameDonePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setBounds(originalX, originalY, panelWidth, panelHeight);
		this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(CalcudokuGame.getSpriteAtlas("gamedonepanel.png"), 0, 0, 550, 300, null);
		
		pauseButtons[0] = new Button(CalcudokuGame.getSpriteAtlas("exit.png"), 270, 195, 150, 60);
		pauseButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.MENU;
				gamePanel.setHasExistingPanel(false);
			}
		});
		this.add(pauseButtons[0]);
		
		pauseButtons[1] = new Button(CalcudokuGame.getSpriteAtlas("newgame.png"), 100, 195, 150, 60);
		pauseButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				gamePanel.setGameHasStarted(false);
				gamePanel.setHasExistingPanel(false);
			}
		});
		this.add(pauseButtons[1]);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		 initialX = e.getX();
	     initialY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Rectangle panelBounds = this.getBounds();

	    Rectangle parentBounds = this.getParent().getBounds();

	    if (!parentBounds.contains(panelBounds)) {
	        this.setBounds(originalX, originalY, panelWidth, panelHeight);
	        gamePanel.repaint();
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		offsetX = e.getX() - initialX;
        offsetY = e.getY() - initialY;

        int newX = this.getX() + offsetX;
        int newY = this.getY() + offsetY;

        this.setBounds(newX, newY, panelWidth, panelHeight);
        gamePanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
