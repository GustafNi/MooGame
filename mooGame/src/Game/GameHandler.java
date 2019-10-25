package Game;



import DataBase.DataBaseDAO;
import DataBase.JDBCHandler;
import Game.GameLogic;
import Gui.SimpleWindow;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class GameHandler {
    SimpleWindow simplewindow;
    GameLogic gameLogic;
    DataBaseDAO dataBaseDAO;
    static Connection connection;
    static Statement stmt;
    static ResultSet rs;

    public GameHandler(SimpleWindow simplewindow, GameLogic gameLogic, DataBaseDAO dataBaseDAO) {
        this.simplewindow = simplewindow;
        this.gameLogic = gameLogic;
        this.dataBaseDAO = dataBaseDAO;
    }

    public int loginWithName() throws ClassNotFoundException, SQLException, InterruptedException {
        // login
        simplewindow.addString("Enter your user name:\n");
        String name = simplewindow.getString();
        int id = dataBaseDAO.getPlayerId(name);
        if(id == 0) {
            simplewindow.addString("User not in database, please register with admin");
            Thread.sleep(5000);
            simplewindow.exit();
        }
        return id;
    }

    public void execute(int answer, int id) throws SQLException {
        while (answer == JOptionPane.YES_OPTION) {
            String goal = gameLogic.makeGoal();
            simplewindow.clear();
            simplewindow.addString("New game:\n");
            //comment out or remove next line to play real games!
            simplewindow.addString("For practice, number is: " + goal + "\n");
            String guess = simplewindow.getString();
            simplewindow.addString(guess +"\n");
            int nGuess = 1;
            String bbcc = gameLogic.checkBC(goal, guess);
            simplewindow.addString(bbcc + "\n");
            while ( ! bbcc.equals("BBBB,")) {
                nGuess++;
                guess = simplewindow.getString();
                simplewindow.addString(guess +"\n");
                bbcc = gameLogic.checkBC(goal, guess);
                simplewindow.addString(bbcc + "\n");
            }
            dataBaseDAO.insertIntoResults(nGuess, id);
             showTopTen(id);
            answer = JOptionPane.showConfirmDialog(null, "Correct, it took " + nGuess
                    + " guesses\nContinue?");

        }
        simplewindow.exit();
    }

    public void showTopTen(int id) throws SQLException {
        ArrayList<GameLogic.PlayerAverage> topList = new ArrayList<>();
        int nGames = dataBaseDAO.getResults();
        if (nGames > 0) {
            String playerName = dataBaseDAO.getPlayerName(id);
            int noOfGuesses = dataBaseDAO.noOfGuesses(id);
            int noOfPlayedGames = dataBaseDAO.noOfPlayedGames(id);
                topList.add(new GameLogic.PlayerAverage(playerName, (double)noOfGuesses/noOfPlayedGames));
            }
        simplewindow.addString("Top Ten List\n    Player     Average\n");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (GameLogic.PlayerAverage p : topList) {
            simplewindow.addString(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }

    }
}
