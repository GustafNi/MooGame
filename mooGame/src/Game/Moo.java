package Game;

import DataBase.DataBaseDAO;
import DataBase.JDBCHandler;
import Gui.SimpleWindow;

import javax.swing.JOptionPane;
import Gui.UserInterface;


public class Moo {
	
	static UserInterface gameWindow;
	static LogicContent mooGameLogic;
        static DataBaseDAO dataBaseDAO;

	public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
		gameWindow = new SimpleWindow("Moo");
                dataBaseDAO = new JDBCHandler();
                mooGameLogic = new MooGameLogic();
		GameHandler gameHandler = new GameHandler(gameWindow, mooGameLogic, dataBaseDAO);
		int id = gameHandler.loginWithName();
		gameHandler.gameStarter(id);
	}

}