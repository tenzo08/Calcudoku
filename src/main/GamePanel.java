package main;

import static main.CalcudokuGame.GAME_HEIGHT;
import static main.CalcudokuGame.GAME_WIDTH;
import static main.CalcudokuGame.MAX_GAME_HINT;
import static main.CalcudokuGame.hintUsed;
import static main.CalcudokuGame.numberOfGrids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buttons.Button;
import floatingpanels.GameDonePanel;
import floatingpanels.IngameExitPanel;
import floatingpanels.MaxHint;
import floatingpanels.PausePanel;
import gamestate.Gamestate;
import gamestate.Menu;
import gamestate.Options;
import grids.CalcudokuGenerator;
import grids.Grid;

public class GamePanel extends JPanel{
	CalcudokuGame calcudokuGame;
	CalcudokuGenerator calcudokuGenerator;
	
	Menu menu;
	Options options;
	PausePanel pausePanel;
	GameDonePanel gameDonePanel;
	IngameExitPanel ingameExitPanel;
	MaxHint maxHint;
	
	Button[] gameButton = new Button[4];
	JLabel timeLabel;
	
	private static Grid grid[][] = new Grid[numberOfGrids][numberOfGrids];
	
	private boolean gameHasStarted, inMenu, inOptions, paused, notifiedMaxHint, hasExistingPanel;
	
	public GamePanel(CalcudokuGame calcudokuGame) {
		this.calcudokuGame = calcudokuGame;
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.setLayout(null);
		this.requestFocus(gameHasStarted);
		
		menu = new Menu();
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(CalcudokuGame.getSpriteAtlas("background.png"), 0, 0, null);
        
        g2.setColor(new Color(51, 25, 0));
        
        g2.fillRect(26, 86, 548, 548);
    }
	
	public void gameUpdate() {
		if(!isGameHasStarted()) {
			this.removeAll();
			addGrids();
			initilializeGameButtons();
			
			CalcudokuGame.gameDone = false;
			setGameHasStarted(true);
			setPaused(false);
			inMenu = false;
			inOptions = false;
		}
		
		if(CalcudokuGame.gameDoneChecker(grid)) {
			CalcudokuGame.playWin();
			CalcudokuGame.gameHasStarted = false;
			CalcudokuGame.gameDone = true;
			CalcudokuGame.hour = 0;
			CalcudokuGame.minute = 0;
			CalcudokuGame.second = 0;
			CalcudokuGame.time = "00 : 00 : 00";
			CalcudokuGame.resetInputs();
			openGameDonePanel();
		}
	}

	public void menuUpdate() {
		if(!inMenu) {
			this.removeAll();
			this.add(menu);
			
			inMenu = true;
			setPaused(false);
			setGameHasStarted(false);
			inOptions = false;
		}
	}

	public void optionsUpdate() {
		if(!inOptions) {
			options = new Options();
			
			this.removeAll();
			this.add(options);
			
			inMenu = false;
			setPaused(false);
			inOptions = true;
		}
	}
	
	public void pauseUpdate() {
		if(!isPaused()) {
			gamePaused(); 
			removeInputs();
			
			setPaused(true);
			inMenu = false;
			inOptions = false;
			setGameHasStarted(false);
		}
	}
	
	private void addGrids() {
		calcudokuGenerator = new CalcudokuGenerator();
		grid = calcudokuGenerator.getGrid();
		for(int row = 0; row < numberOfGrids; row++) {
	          for(int col = 0; col < numberOfGrids; col++) {
	        	  this.add(grid[row][col]);
	          }
		}
	}
	
	private void gamePaused() {
		pausePanel = new PausePanel(this);
		this.add(pausePanel);
		this.setComponentZOrder(pausePanel, 0);
	}
	
	public void repaintPausePanel() {
		this.remove(pausePanel);
		pausePanel = new PausePanel(this);
		this.add(pausePanel);
		this.setComponentZOrder(pausePanel, 0);
	}
	
	private void initilializeGameButtons() {
		BufferedImage image = CalcudokuGame.getSpriteAtlas("buttons.png");
		
		gameButton[0] = new Button(image.getSubimage(320, 80, 80, 80), 505, 10, 70, 70);
		gameButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isHasExistingPanel()) {
					CalcudokuGame.playTap();
					Gamestate.state = Gamestate.PAUSED;
					setPaused(true);
					openIngameExitPanel();
					setHasExistingPanel(true);
				}
			}
		});
		this.add(gameButton[0]);
		
		gameButton[1] = new Button(image.getSubimage(240, 80, 80, 80), 435, 10, 70, 70);
		gameButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isHasExistingPanel()) {
					CalcudokuGame.playTap();
					if(hintUsed < MAX_GAME_HINT ) {
						calcudokuGame.hint(getGrid());
						hintUsed++;
					} else if(!isNotifiedMaxHint()){
						openMaxHint();
						setNotifiedMaxHint(true);
					}
				}
			}
			
		});
		this.add(gameButton[1]);
		
		gameButton[2] = new Button(image.getSubimage(240, 240, 80, 80), 365, 10, 70, 70);
		gameButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isPaused() && !isHasExistingPanel()) {
					CalcudokuGame.playTap();
					Gamestate.state = Gamestate.PAUSED;
					setPaused(false);
					setHasExistingPanel(true);
				}
			}
		});
		this.add(gameButton[2]);
		
		gameButton[3] = new Button(image.getSubimage(160, 320, 80, 80), 295, 10, 70, 70);
		gameButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isHasExistingPanel()) {
					CalcudokuGame.playTap();
					calcudokuGame.solution(getGrid());
					CalcudokuGame.hour = 0;
					CalcudokuGame.minute = 0;
					CalcudokuGame.second = 0;
					CalcudokuGame.time = "00 : 00 : 00";
					setHasExistingPanel(true);
				}
			}
			
		});
		this.add(gameButton[3]);
		
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Sansarif Sans", Font.BOLD, 35));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setBorder(BorderFactory.createLineBorder(new Color(51, 25, 0), 5));
		timeLabel.setForeground(new Color(153, 76, 0));
		timeLabel.setBackground(new Color(253, 215, 128));
		timeLabel.setBounds(25, 10, 210, 70);
		CalcudokuGame.time = "";
		if(CalcudokuGame.hour >= 10)
			CalcudokuGame.time = CalcudokuGame.time + CalcudokuGame.hour + " : ";
		else 
			CalcudokuGame.time = CalcudokuGame.time + "0" + CalcudokuGame.hour + " : ";
		if(CalcudokuGame.minute >= 10)
			CalcudokuGame.time = CalcudokuGame.time + CalcudokuGame.minute + " : ";
		else 
			CalcudokuGame.time = CalcudokuGame.time + "0" + CalcudokuGame.minute + " : ";
		if(CalcudokuGame.second >= 10)
			CalcudokuGame.time = CalcudokuGame.time + CalcudokuGame.second;
		else 
			CalcudokuGame.time = CalcudokuGame.time + "0" + CalcudokuGame.second;
		timeLabel.setText(CalcudokuGame.time);
		this.add(timeLabel);
	}
	
	public void addInputs() {
		for(int row = 0; row < numberOfGrids; row++) {
	        for(int col = 0; col < numberOfGrids; col++) {
	        	getGrid()[row][col].setResult();
	        	if(CalcudokuGame.input[row][col] != 0) {
	        		getGrid()[row][col].setText(String.valueOf(CalcudokuGame.input[row][col]));
	        	}
	        }
		}
	}
	
	public void removeInputs() {
		for(int row = 0; row < numberOfGrids; row++) {
	         for(int col = 0; col < numberOfGrids; col++) {
	        	 getGrid()[row][col].setText("");
	         }
		}
	}
	
	private void openIngameExitPanel() {
		ingameExitPanel = new IngameExitPanel(this);
		this.add(ingameExitPanel);
		this.setComponentZOrder(ingameExitPanel, 0);
	}
	
	private void openGameDonePanel() {
		gameDonePanel = new GameDonePanel(this);
		this.add(gameDonePanel);
		this.setComponentZOrder(gameDonePanel, 0);
	}
	
	private void openMaxHint() {
		maxHint = new MaxHint(this);
		this.add(maxHint);
		this.setComponentZOrder(maxHint, 0);
	}
	
	public void removeIngameExitPanel() {
		this.remove(ingameExitPanel);
	}
	
	public void removePausePanel() {
		this.remove(pausePanel);
	}
	
	public void removeGameDonePanel() {
		this.remove(gameDonePanel);
	}
	
	public void removeMaxHint() {
		this.remove(maxHint);
	}

	public boolean isGameHasStarted() {
		return gameHasStarted;
	}

	public void setGameHasStarted(boolean gameHasStarted) {
		this.gameHasStarted = gameHasStarted;
	}

	public static Grid[][] getGrid() {
		return grid;
	}

	public static void setGrid(Grid grid[][]) {
		GamePanel.grid = grid;
	}

	public boolean isNotifiedMaxHint() {
		return notifiedMaxHint;
	}

	public void setNotifiedMaxHint(boolean notifiedMaxHint) {
		this.notifiedMaxHint = notifiedMaxHint;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isHasExistingPanel() {
		return hasExistingPanel;
	}

	public void setHasExistingPanel(boolean hasExistingPanel) {
		this.hasExistingPanel = hasExistingPanel;
	}
}


