import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Moo {
	
	static SimpleWindow gw;
	static GameLogic gameLogic;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		gw = new SimpleWindow("Moo");
		int answer = JOptionPane.YES_OPTION;
		ClientHandler dbLogin = new ClientHandler(gw, gameLogic);
		int id = dbLogin.login();
		dbLogin.execute(answer, id);
	}

}