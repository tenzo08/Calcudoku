package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import gamestate.Gamestate;
import gamestate.Menu;
import grids.Grid;

public class CalcudokuGame extends JFrame implements Runnable{
	private Thread gameThread;
	private GamePanel gamePanel;
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	public final static int GAME_WIDTH = 600;
	public final static int GAME_HEIGHT = 660;
	public final static int BOARD_SIZE = 540;
	public final static int MAX_GAME_HINT = 3;
	
	public static int numberOfGrids = 9;
	public static int hintUsed = 0;
	public static int GRID_SIZE = BOARD_SIZE / numberOfGrids;
	
	public static Grid chosenGrid;
	public static int[][] input;
	
	public static int hour = 0, minute = 0, second = 0;
	public static String time = "00 : 00 : 00";
	public static boolean gameDone, gameHasStarted;
	public static boolean musicOn = true, sfxOn = true;
	
	public static Clip tap, win, gameMusic;
	
	public CalcudokuGame() {
		gamePanel = new GamePanel(this);
		input = new int[numberOfGrids][numberOfGrids];
		chosenGrid = new Grid(0, -1, -1);
		chosenGrid = null;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(gamePanel);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		initializeGameMusic();
		startGameLoop();
	}

	public void update() {
		switch (Gamestate.state) {
			case MENU:
				gamePanel.menuUpdate();
				break;
			case PLAYING:
				gamePanel.gameUpdate();
				break;
			case OPTIONS:
				gamePanel.optionsUpdate();
				break;
			case QUIT:
				System.exit(0);
				break;
			case PAUSED:
				gamePanel.pauseUpdate();
				break;
			default:
				break;
		}
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	//This method was based on the code of a youtube channel called Kaarin Gaming
	//The link of the video is here: https://youtu.be/aFS9Whsoecc?si=NhJl0tCEL15_o1ev
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		long lastCheck = System.currentTimeMillis();
		long timerLastCheck = System.currentTimeMillis();
		long elapsedTime = 0;

		double deltaU = 0;
		double deltaF = 0;
		
		boolean elapsedHasCounted = false;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				deltaU--;
			}
			
			if(Gamestate.state == Gamestate.PLAYING && !gameDone) {
				if(!gameHasStarted) {
					timerLastCheck = System.currentTimeMillis() - elapsedTime;
					gameHasStarted = true;
					elapsedTime = 0;
					elapsedHasCounted = false;
				}
				if (System.currentTimeMillis() - timerLastCheck >= 1000) {
					time = "";
					timerLastCheck = System.currentTimeMillis();
					second++;
					if(second == 60) {
						minute++;
						second = 0;
						if(minute == 60) {
							hour++;
							minute = 0;
						}
					}
					if(hour < 10) {
						time += "0" + hour + " : ";
					} else {
						time += hour + " : ";
					}
					if(minute < 10) {
						time += "0" + minute + " : ";
					} else {
						time += minute + " : ";
					}
					if(second < 10) {
						time += "0" + second;
					} else {
						time += second;
					}
					gamePanel.timeLabel.setText(time);
				} 
			} else if(Gamestate.state == Gamestate.PAUSED && !gameDone){
				if(!elapsedHasCounted) {
					elapsedTime = System.currentTimeMillis() - timerLastCheck;
					gameHasStarted = false;
					elapsedHasCounted = true;
				}
			} else if(!gameDone){
				gameHasStarted = false;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				
				try {
					gamePanel.removeMaxHint();
					gamePanel.setNotifiedMaxHint(false);
				} catch (Exception e) {}
			}
		}
	}
	
	private void initializeGameMusic() {
		tap = getClip("tap.wav");
		win = getClip("win.wav");
		gameMusic = getClip("gamemusic.wav");
		
		playMusic();
	}
	
	private static void setVolume(Clip clip, double volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
	
	public static void muteMusic() {
		gameMusic.stop();
	}
	
	public static void playMusic() {
		setVolume(gameMusic, 0.2);
		gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void playTap() {
		if(sfxOn) {
			CalcudokuGame.tap.setMicrosecondPosition(0);
			CalcudokuGame.tap.start();
		}
	}
	
	public static void playWin() {
		if(sfxOn) {
			CalcudokuGame.win.setMicrosecondPosition(0);
			CalcudokuGame.win.start();
		}
	}
	
	//This code was based on the code of a youtube channel called Kaarin Gaming
	//The link of the video is here: https://youtu.be/BT2jm74lLlg?si=KaeF7JsPiYhHQIEQ
	public static BufferedImage getSpriteAtlas(String filename) {
		BufferedImage image = null;
		
		InputStream is = Menu.class.getResourceAsStream("/pictures/" + filename);
		try {
			image = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return image;
	}
	
	//This code was based on the code of a youtube channel called Kaarin Gaming
	//The link of the video is here: https://youtu.be/afOcsF2-xbg?si=8uPWN-m7GUIguwyu
	private Clip getClip(String name) {
		URL url = getClass().getResource("/music/" + name);
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void hint(Grid[][] grid) {
    	Random random = new Random();
    	
    	int randomX = random.nextInt(numberOfGrids);
    	int randomY = random.nextInt(numberOfGrids);
    	
    	while(input[randomX][randomY] != 0) {
    		randomX = random.nextInt(numberOfGrids);
        	randomY = random.nextInt(numberOfGrids);
    	}
    	
    	grid[randomX][randomY].setText(String.valueOf(grid[randomX][randomY].getValue()));
    	grid[randomX][randomY].setForeground(Color.blue);
    	input[randomX][randomY] = grid[randomX][randomY].getValue();
    }
	
	public static void checkSimilarity(Grid[][] grid) {
		for(int row = 0; row < numberOfGrids; row++) {
			for (int col = 0; col < numberOfGrids; col++) {
				if(grid[row][col].getForeground() != Color.blue) {
            		grid[row][col].setForeground(new Color(153, 76, 0));
            	}
			}
		}
		for(int row = 0; row < numberOfGrids; row++) {
			for (int col = 0; col < numberOfGrids; col++) {
				for (int i = 0; i < numberOfGrids; i++) {
			        if (i != col && input[row][i] == input[row][col] && 
			        		input[row][i] != 0 && input[row][col] != 0) {
			        	if(grid[row][col].getForeground() != Color.blue) {
		            		grid[row][col].setForeground(Color.red);
		            	}
			        	if(grid[row][i].getForeground() != Color.blue) {
		            		grid[row][i].setForeground(Color.red);
		            	}
			        }
			    }
			    for (int i = 0; i < numberOfGrids; i++) {
			        if (i != row && input[i][col] == input[row][col] && 
			        		input[i][col] != 0 && input[row][col] != 0) {
			        	if(grid[row][col].getForeground() != Color.blue) {
		            		grid[row][col].setForeground(Color.red);
		            	}
			        	if(grid[i][col].getForeground() != Color.blue) {
		            		grid[i][col].setForeground(Color.red);
		            	}
			        }
			    }
			}
		}
	}
	
	public void solution(Grid[][] grid) {
    	for (int row = 0; row < numberOfGrids; row++) {
            for (int col = 0; col < numberOfGrids; col++) {
            	if(grid[row][col].getForeground() != Color.blue) {
            		grid[row][col].setForeground(Color.black);
            	}
            	grid[row][col].setText(String.valueOf(grid[row][col].getValue()));
            	if(input[row][col] != grid[row][col].getValue() || input[row][col] == 0) {
            		grid[row][col].setForeground(Color.red);
            	}
            	input[row][col] = grid[row][col].getValue();
            }
        }
    }
	
	public static boolean gameDoneChecker(Grid[][] grid) {
		for (int row = 0; row < numberOfGrids; row++) {
            for (int col = 0; col < numberOfGrids; col++) {
            	if(grid[row][col].getValue() != input[row][col]) {
            		return false;
            	}
            }
        }
		return true;
	}
	
	public static void resetInputs() {
		for (int row = 0; row < numberOfGrids; row++) {
            for (int col = 0; col < numberOfGrids; col++) {
            	input[row][col] = 0;
            }
        }
	}
}
