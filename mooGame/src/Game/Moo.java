package Game;

import DataBase.DataBaseDAO;
import DataBase.DbHandler;
import Gui.SimpleWindow;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Moo {
	
	static SimpleWindow gw;
	static GameLogic gameLogic;
        static DataBaseDAO dBDAO;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		gw = new SimpleWindow("Moo");
                dBDAO = new DbHandler();
                gameLogic = new GameLogic();
		int answer = JOptionPane.YES_OPTION;
		GameHandler dbLogin = new GameHandler(gw, gameLogic, dBDAO);
		int id = dbLogin.loginWithName();
		dbLogin.execute(answer, id);
	}

}