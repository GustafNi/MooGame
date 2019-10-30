package Game;

import DataBase.DataBaseDAO;
import DataBase.JDBCHandler;
import Gui.SimpleWindow;
import Gui.UserInterfaceDAO;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Moo {
	
	static UserInterfaceDAO gw;
	static GameLogic gameLogic;
        static DataBaseDAO dBDAO;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		gw = new SimpleWindow("Moo");
                dBDAO = new JDBCHandler();
                gameLogic = new GameLogic();
		int answer = JOptionPane.YES_OPTION;
		GameHandler dbLogin = new GameHandler(gw, gameLogic, dBDAO);
		int id = dbLogin.loginWithName();
		dbLogin.execute(answer, id);
	}

}