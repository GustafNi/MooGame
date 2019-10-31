package Game;

import DataBase.DataBaseDAO;
import DataBase.JDBCHandler;
import Gui.SimpleWindow;
import Gui.UserInterfaceDAO;

import javax.swing.JOptionPane;


public class Moo {
	
	static UserInterfaceDAO gameWindow;
	static GameLogicDAO gameLogicDAO;
        static DataBaseDAO dataBaseDAO;

	public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
		gameWindow = new SimpleWindow("Moo");
                dataBaseDAO = new JDBCHandler();
                gameLogicDAO = new GameLogic();
		GameHandler gameHandler = new GameHandler(gameWindow, gameLogicDAO, dataBaseDAO);
		int id = gameHandler.loginWithName();
		gameHandler.gameStarter(id);
	}

}