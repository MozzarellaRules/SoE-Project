package Main;

import javax.swing.*;

//Niente da dire, è il frame principale dove va caricato il GamePanel
public class Game {
	public static void main(String[] args) {
		JFrame window= new JFrame("Project");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
