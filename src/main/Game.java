package main;

import javax.swing.*;

public class Game {

	/**
	 * This is the entry point of the application
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame window=new JFrame("Project");
		window.setContentPane(new GamePanelController().getPanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
