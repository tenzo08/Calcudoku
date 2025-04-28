package gamestate;

public enum Gamestate {
	PLAYING, MENU, OPTIONS, PAUSED, QUIT;
	
	public static Gamestate state = MENU;
}
