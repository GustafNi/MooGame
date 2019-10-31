package Game;

import DataBase.DataBaseDAO;
import DataBase.JDBCHandler.PlayerAverage;
import Gui.UserInterfaceDAO;
import javax.swing.*;
import java.util.ArrayList;

public class GameHandler {
    UserInterfaceDAO gameWindow;
    GameLogicDAO gameLogic;
    DataBaseDAO dataBaseDAO;

    public GameHandler(UserInterfaceDAO userInterfaceDAO, GameLogicDAO gameLogicDAO, DataBaseDAO dataBaseDAO) {
        this.gameWindow = userInterfaceDAO;
        this.gameLogic = gameLogicDAO;
        this.dataBaseDAO = dataBaseDAO;
    }

    public int loginWithName() throws IllegalArgumentException, InterruptedException {
        gameWindow.addString("Enter your user name:\n");
        String name = gameWindow.getString();
        int id = dataBaseDAO.getPlayerId(name);
        if(id == 0) {
            gameWindow.addString("User not in database, please register with admin");
            Thread.sleep(5000);
            gameWindow.exit();
        }
        return id;
    }

    public void gameStarter(int id) {
        int answer = JOptionPane.YES_OPTION;
        while (answer == JOptionPane.YES_OPTION) {
            String goal = gameLogic.makeGoal();
            startString(goal);
            int nGuess = answerCheck(goal);
            dataBaseDAO.insertIntoResults(nGuess, id);
            showHiScore(dataBaseDAO.getTopTenResults());
            answer = JOptionPane.showConfirmDialog(null, "Correct, it took " + nGuess
                    + " guesses\nContinue?");
        }
        gameWindow.exit();
    }

    public void showHiScore(ArrayList<PlayerAverage> topList) {
        gameWindow.addString("Top Ten List\n    Player     Average\n");
        int pos = 1;
        for (PlayerAverage p : topList) {
            gameWindow.addString(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }
    }
    
    public void startString(String goal) {
        gameWindow.clear();
        gameWindow.addString("New game:\n");
        //comment out or remove next line to play real games!
        gameWindow.addString("For practice, number is: " + goal + "\n");
    }
    
    public int answerCheck(String goal) {
        String guess = gameWindow.getString();
        gameWindow.addString(guess +"\n");
        int nGuess = 1;
        String bbcc = gameLogic.checkBC(goal, guess);
        gameWindow.addString(bbcc + "\n");
        while ( ! bbcc.equals("BBBB,")) {
            nGuess++;
            guess = gameWindow.getString();
            gameWindow.addString(guess +"\n");
            bbcc = gameLogic.checkBC(goal, guess);
            gameWindow.addString(bbcc + "\n");
        }
        return nGuess;
    }
}
