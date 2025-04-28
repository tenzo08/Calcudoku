package gamestate;

import static main.CalcudokuGame.GAME_HEIGHT;
import static main.CalcudokuGame.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import buttons.Button;
import floatingpanels.ExitPanel;
import main.CalcudokuGame;

public class Menu extends JPanel{
	Button menuButton[] = new Button[3];
	JLabel gameTitle = new JLabel();
	
	NumberOfGridOption numberOfGridOption;
	ExitPanel exitPanel;
	
	public Menu() {
		this.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
		this.setLayout(null);
		addButtons();
	}

	public void addButtons() {
		menuButton[0] = new Button(CalcudokuGame.getSpriteAtlas("start.png"), 200, 330, 200, 80);
		menuButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				removeMenuPanel();
				openNumberOfGridOptionPanel();
			}
		});
		this.add(menuButton[0]);
		
		menuButton[1] = new Button(CalcudokuGame.getSpriteAtlas("options.png"), 200, 410, 200, 80);
		menuButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				Gamestate.state = Gamestate.OPTIONS;
			}
		});
		this.add(menuButton[1]);
		
		menuButton[2] = new Button(CalcudokuGame.getSpriteAtlas("exit.png"), 200, 490, 200, 80);
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalcudokuGame.playTap();
				openExitPanel();
			}
		});
		this.add(menuButton[2]);
	}
	
	public void start() {
		this.removeAll();
		this.addButtons();
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(CalcudokuGame.getSpriteAtlas("background.png"), 0, 0, null);
    }
	
	private void openExitPanel() {
		exitPanel = new ExitPanel(this);
		this.add(exitPanel);
		this.setComponentZOrder(exitPanel, 0);
	}
	
	private void openNumberOfGridOptionPanel() {
		numberOfGridOption = new NumberOfGridOption(this);
		this.add(numberOfGridOption);
	}
	
	public void removeExitPanel() {
		this.remove(exitPanel);
	}
	
	private void removeMenuPanel() {
		this.removeAll();
	}
}
