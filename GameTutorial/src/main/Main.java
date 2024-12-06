package main;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Game Tutorial");
		
		GamePanel gamePanel = new GamePanel();//Create a GamePanel which is the screen where the game is run
		window.add(gamePanel);
		
		window.pack();//Sets it to the size of the gamePanel
		
		window.setLocationRelativeTo(null);//Opens the JFrame in the middle of the screen
		window.setVisible(true);
		gamePanel.setupGame();
		gamePanel.startGameLoop();
	}
}
