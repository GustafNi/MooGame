package DataBase;

import Game.GameLogic;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBaseDAO {

    ArrayList getTopTenResults(ArrayList topList) throws SQLException;

    int getPlayerId(String name) throws ClassNotFoundException, SQLException;
    
    String getPlayerName(int id) throws SQLException;
    
    int noOfGuesses(int id) throws SQLException;
    
    int noOfPlayedGames(int id) throws SQLException;
    
    void insertIntoResults(int nGuess, int id)throws SQLException;
    
    int getResults() throws SQLException;
}
